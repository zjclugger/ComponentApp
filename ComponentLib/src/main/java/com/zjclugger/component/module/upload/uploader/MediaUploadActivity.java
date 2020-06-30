package com.zjclugger.component.module.upload.uploader;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.zjclugger.component.R;
import com.zjclugger.component.module.upload.uploader.entity.UploadLocalMedia;
import com.zjclugger.component.picture.selector.FullyGridLayoutManager;
import com.zjclugger.component.picture.selector.GridMediaAdapter;
import com.zjclugger.lib.base.BaseActivity;
import com.zjclugger.lib.ui.widget.JAlertDialog;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.FileUtils;
import com.zjclugger.router.utils.ARouterUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 如果有必要，可放置到ERPLibrary[King zi]
 */
public abstract class MediaUploadActivity extends BaseActivity {
    private final static String TAG = "MediaSelector";
    private List<LocalMedia> mSelectedList = new ArrayList<>();
    private TextView mUploadButton;
    private RecyclerView mRecyclerView;
    private RecyclerView mUploadRecyclerView;
    private GridMediaAdapter mMediaAdapter;
    private int mMaxSelectNum = 9;
    private int mThemeId = R.style.picture_default_style;
    private int mChooseMode = PictureMimeType.ofImage();
    private int mGridSpanCount = 4;
    private String mTitle;
    protected List<UploadLocalMedia> mUploadList = new ArrayList<>();
    protected GridMediaUploadAdapter mUploadAdapter;
    protected TextView mReasonEditView;
    private JAlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarStyle(R.color.transparent, false);
        setContentView(R.layout.activity_media_upload);
        mTitle = getString(R.string.text_pic_select);

        mUploadButton = findViewById(R.id.tv_right_text);
        mUploadButton.setText(R.string.text_confirm);
        mUploadButton.setOnClickListener(setOnclickListener());

        mReasonEditView = findViewById(R.id.letv_reason);
        mReasonEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouterUtils.openDetailFragment(MediaUploadActivity.this,
                        BillReasonSelectFragment.class.getName(), null, R.color.white, false);
            }
        });
        LiveEventBus.get(Constants.Keywords.KEY_BILL_REASON, String.class)
                .observe(this, new androidx.lifecycle.Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String data) {
                        mReasonEditView.setText(data);
                    }
                });
        mRecyclerView = findViewById(R.id.recycler);
        mUploadRecyclerView = findViewById(R.id.recycler_upload);

        bindRecyclerView();
        bindUploadRecyclerView();

        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(MediaUploadActivity.this);
                } else {
                    Toast.makeText(MediaUploadActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void bindRecyclerView() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(MediaUploadActivity.this,
                mGridSpanCount, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mMediaAdapter = new GridMediaAdapter(MediaUploadActivity.this, onAddPicClickListener);
        mMediaAdapter.setList(mSelectedList);
        mMediaAdapter.setSelectMax(mMaxSelectNum);
        mRecyclerView.setAdapter(mMediaAdapter);

        mMediaAdapter.setOnItemClickListener(new GridMediaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (mSelectedList.size() > 0) {
                    LocalMedia media = mSelectedList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            PictureSelector.create(MediaUploadActivity.this).themeStyle(mThemeId).openExternalPreview(position, mSelectedList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(MediaUploadActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(MediaUploadActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    protected void bindUploadRecyclerView() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(MediaUploadActivity.this,
                mGridSpanCount, GridLayoutManager.VERTICAL, false);
        mUploadRecyclerView.setLayoutManager(manager);
        mUploadAdapter = new GridMediaUploadAdapter(R.layout.item_upload_grid_image, mUploadList);
        mUploadRecyclerView.setAdapter(mUploadAdapter);
    }

    protected void notifyUploadListChange() {
        mRecyclerView.setVisibility(View.GONE);
        mUploadButton.setText(R.string.picture_done);
        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mUploadRecyclerView.setVisibility(View.VISIBLE);
        mUploadAdapter.notifyDataChanged();
    }

    private GridMediaAdapter.onAddPicClickListener onAddPicClickListener =
            new GridMediaAdapter.onAddPicClickListener() {
                @Override
                public void onAddPicClick() {
                    // 不需要的api可以不写
                    PictureSelector.create(MediaUploadActivity.this)
                            .openGallery(mChooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()
                            // 、视频 .ofVideo()、音频.ofAudio()
                            .theme(mThemeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture
                            // .white.style
                            .maxSelectNum(mMaxSelectNum)// 最大图片选择数量
                            .minSelectNum(1)// 最小选择数量
                            .imageSpanCount(4)// 每行显示个数
                            .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                            .previewImage(true)// 是否可预览图片
                            .previewVideo(false)// 是否可预览视频
                            .enablePreviewAudio(false) // 是否可播放音频
                            .isCamera(true)// 是否显示拍照按钮
                            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                            //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                            //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                            .enableCrop(false)// 是否裁剪
                            .compress(true)// 是否压缩
                            .synOrAsy(true)//同步true或异步false 压缩 默认同步
                            //.compressSavePath(getPath())//压缩图片保存地址
                            //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                            .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                            //.withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2
                            // 3:4 1:1
                            // 可自定义
                            .hideBottomControls(false)//
                            // 是否显示uCrop工具栏，默认不显示
                            .isGif(true)// 是否显示gif图片
                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                            .showCropFrame(true)// 是否显示裁剪矩形边框
                            // 圆形裁剪时建议设为false
                            .showCropGrid(true)// 是否显示裁剪矩形网格
                            // 圆形裁剪时建议设为false
                            //.openClickSound(cb_voice.isChecked())// 是否开启点击声音
                            .selectionMedia(mSelectedList)// 是否传入已选图片
                            //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                            //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                            //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                            .minimumCompressSize(100)// 小于100kb的图片不压缩
                            //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                            //.rotateEnabled(true) // 裁剪是否可旋转图片
                            //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                            //.videoQuality()// 视频录制质量 0 or 1
                            //.videoSecond()//显示多少秒以内的视频or音频也可适用
                            //.recordVideoSecond()//录制视频秒数 默认60s
                            .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    mSelectedList = PictureSelector.obtainMultipleResult(data);
                    bindUploadData();
                    mMediaAdapter.setList(mSelectedList);
                    mMediaAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private void bindUploadData() {
        if (mUploadList != null && mUploadList.size() > 0) {
            mUploadList.clear();
        }

        if (mSelectedList != null && mSelectedList.size() > 0) {
            UploadLocalMedia uploadLocalMedia;
            for (LocalMedia media : mSelectedList) {
                uploadLocalMedia = new UploadLocalMedia();
                uploadLocalMedia.setPath(media.getPath());
                uploadLocalMedia.setFileName(FileUtils.getFileName(media.getPath()));
                mUploadList.add(uploadLocalMedia);
            }
        }
    }

    @Override
    protected boolean isDetailActivity() {
        return true;
    }

    @Override
    protected String getDetailActivityTitle() {
        return mTitle;
    }

    protected void setTitleText(String titleText) {
        mTitle = titleText;
    }

    /**
     * 可选择的最大数量，默认为9
     *
     * @param selectMaxCount
     */
    protected void setSelectMaxCount(int selectMaxCount) {
        mMaxSelectNum = selectMaxCount;
    }

    /**
     * 选择器样式
     *
     * @param themeId
     */
    protected void setThemeId(@IdRes int themeId) {
        mThemeId = themeId;
    }

    /**
     * 设置多媒体类型，取值请参考 {@link PictureMimeType}，默认为图片(PictureMimeType.ofImage())
     *
     * @param mimeType
     */
    protected void setMimeType(int mimeType) {
        mChooseMode = mimeType;
    }

    /**
     * 设置列表一行个数
     *
     * @param count
     */
    protected void setGridViewSpanCount(int count) {
        mGridSpanCount = count;
    }

    /**
     * 获取已选择的多媒体列表
     *
     * @return
     */
    protected List<LocalMedia> getSelectedList() {
        return mSelectedList;
    }

    public abstract View.OnClickListener setOnclickListener();

    /**
     * 清除缓存,包括裁剪和压缩后的缓存
     */
    protected void clearCache() {
        PictureFileUtils.deleteCacheDirFile(MediaUploadActivity.this);
    }

    private boolean isAllSuccessful() {
        if (mUploadList != null && mUploadList.size() > 0) {
            for (UploadLocalMedia localMedia : mUploadList) {
                if("failure".equalsIgnoreCase(localMedia.getStatus())) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (isAllSuccessful()) {
            clearCache();
            super.onBackPressed();
        } else {
            mSelectedList.clear();
            mMediaAdapter.setList(mSelectedList);
            mMediaAdapter.notifyDataSetChanged();
            //取消就退出当前页，继续就留在当前页
            if (mAlertDialog == null) {
                mAlertDialog = new JAlertDialog(this)
                        .setMessage("检测到您有未上传成功或未识别成功的票据，继续操作将不会对该票据进行处理，是否继续？")
                        .setConfirmText("继续")
                        .setCancelText("取消")
                        .setCanceledOnTouchOutside(true)
                        .setOnClickListener(
                                v -> {
                                    clearCache();
                                    mAlertDialog.dismiss();
                                    super.onBackPressed();
                                },
                                v -> {
                                    //finish();
                                    mAlertDialog.dismiss();
                                });
            }
            mAlertDialog.show();
        }
    }
}

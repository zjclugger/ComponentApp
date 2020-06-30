package com.zjclugger.component.picture.selector;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.zjclugger.component.R;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.common.JMediaItem;
import com.zjclugger.lib.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public abstract class MediaSelectorFragment extends BaseFragment {
    public static final int INVALID_VALUE = -1;
    private final static String TAG = "FMediaSelector";
    public static final int SELECT_MAX_NUMBER = 9;
    private List<LocalMedia> mSelectedList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private GridMediaAdapter mMediaAdapter;
    private int mMaxSelectNum = SELECT_MAX_NUMBER;
    private int mThemeId = R.style.picture_default_style;
    private int mChooseMode = PictureMimeType.ofImage();
    private int mGridSpanCount = 4;
    ArrayList<JMediaItem> mSelectPictureArray = new ArrayList<>();
    private LinearLayout mRootLayoutView;

    @Override
    public int getLayout() {
        return R.layout.fragment_pic_selector;
    }

    @Override
    public void initViews() {
        initView();
        initPermission();
    }

    /**
     * 是否为独立的页面布局
     *
     * @return
     */
    public abstract boolean isLayout();

    public abstract boolean isSmall();

    public abstract boolean isReadOnly();

    public abstract boolean isHideAddView();

    public abstract boolean isOnlySubLayout();

    public abstract int layoutBackground();

    public abstract String getTitle();

    public abstract @LayoutRes
    int subLayout();

    public abstract @IdRes
    int customAddViewId();

    protected void setGridSpanCount(int count) {
        if (count > 0) {
            mGridSpanCount = count;
        }
    }

    protected void setMaxSelectNumber(int maxSelectNumber) {
        mMaxSelectNum = maxSelectNumber <= 0 || maxSelectNumber > SELECT_MAX_NUMBER ?
                SELECT_MAX_NUMBER : maxSelectNumber;
    }

    protected int getMaxSelectNumber() {
        return mMaxSelectNum;
    }

    protected void setSelectedList(List<LocalMedia> selectedList) {
        mSelectedList = selectedList;
    }

    @Override
    public <T> T getPostBackData() {
        return null;
    }

    private void initView() {
        mRootLayoutView = mView.findViewById(R.id.media_selector_layout);
        if (INVALID_VALUE != layoutBackground()) {
            mRootLayoutView.setBackgroundResource(layoutBackground());
        }
        if (isOnlySubLayout()) {
            mView.findViewById(R.id.recycler).setVisibility(View.GONE);
        }

        if (!isLayout()) {
            //隐藏头部和按钮，只剩下列表区域
            mView.findViewById(R.id.nav_layout).setVisibility(View.GONE);
            mView.findViewById(R.id.upload_button).setVisibility(View.GONE);
        } else {
            initDetailTitleViews(getTitle());
        }

        if (INVALID_VALUE != subLayout()) {
            View subView = mLayoutInflater.inflate(subLayout(), null, false);
            if (INVALID_VALUE != customAddViewId()) {
                subView.findViewById(customAddViewId()).setOnClickListener(v -> {
                    pictureSelectorCreate();
                });
            }
            mRootLayoutView.addView(subView, 1);
        }

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), mGridSpanCount
                , GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mMediaAdapter = new GridMediaAdapter(getActivity(), onAddPicClickListener, isSmall(),
                isReadOnly(), isHideAddView());
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
                            PictureSelector.create(MediaSelectorFragment.this).themeStyle(mThemeId).openExternalPreview(position, mSelectedList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(MediaSelectorFragment.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(MediaSelectorFragment.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    private void initPermission() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(getActivity());
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(getActivity());
                } else {
                    Toast.makeText(getActivity(), getString(R.string.picture_jurisdiction),
                            Toast.LENGTH_SHORT).show();
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

    private GridMediaAdapter.onAddPicClickListener onAddPicClickListener =
            new GridMediaAdapter.onAddPicClickListener() {
                @Override
                public void onAddPicClick() {
                    pictureSelectorCreate();
                }
            };

    private void pictureSelectorCreate() {
        // 不需要的api可以不写
        PictureSelector.create(MediaSelectorFragment.this)
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
                .enableCrop(true)// 是否裁剪
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    mSelectedList = PictureSelector.obtainMultipleResult(data);
                    mMediaAdapter.setList(mSelectedList);
                    mMediaAdapter.notifyDataSetChanged();
                    setParams();
                    break;
            }
        }
    }


    /**
     * 获取已选择的多媒体列表
     *
     * @return
     */
    protected List<LocalMedia> getSelectedList() {
        return mSelectedList;
    }

    /**
     * 获取文件原始文件名
     *
     * @param media
     * @return
     */
    protected String getOriginalName(LocalMedia media) {
        if (!TextUtils.isEmpty(media.getPath())) {
            return new File(media.getPath()).getName();
        }
        return "";
    }

    protected void resetGridView() {
        mSelectedList.clear();
        mMediaAdapter.setList(mSelectedList);
        mMediaAdapter.notifyDataSetChanged();
    }

    public abstract View.OnClickListener setOnclickListener();

    /**
     * 清除缓存,包括裁剪和压缩后的缓存
     */
    protected void clearCache() {
        PictureFileUtils.deleteCacheDirFile(getActivity());
    }

    private void setParams() {
        if (mSelectPictureArray != null) {
            mSelectPictureArray.clear();
        }

        JMediaItem item;
        if (getSelectedList() != null && getSelectedList().size() > 0) {
            for (int i = 0; i < getSelectedList().size(); i++) {
                item = new JMediaItem();
                item.setFileName(getOriginalName(getSelectedList().get(i)));
                item.setFilePath(getSelectedList().get(i).getCutPath());
                mSelectPictureArray.add(item);
            }
        }

        Bundle data = new Bundle();
        data.putParcelableArrayList(Constants.Keywords.KEY_SELECTED_PICTURE, mSelectPictureArray);
        setArguments(data);
    }

    public Map<String, RequestBody> getUploadParams() {
        if (getSelectedList() != null && getSelectedList().size() > 0) {
            String mediaType = "multipart/form-data";
            File file;
            String key;
            Map<String, RequestBody> params = new HashMap<>();
            //params.put("companyId", RequestBody.create(MediaType.parse(mediaType), companyId));
            for (int i = 0; i < getSelectedList().size(); i++) {
                file = new File(getSelectedList().get(i).getCutPath());
                if (file.exists()) {
                    //必须为这种格式，Content-Disposition: form-data; name="file1";filename="q5.png"
                    //如果form-data值为空，则不能成功上传
                    key = "file" + i + "\";filename=\"" + getOriginalName(getSelectedList().get(i));
                    params.put(key, RequestBody.create(MediaType.parse(mediaType), file));
                }
            }

            return params;
//            mViewModel.uploadBillImage(params).observe(this,
//                    response -> {
//                        if (response != null && response.isSuccess()) {
//                            WidgetUtils.toast(mContext, R.string.upload_success);
//                            resetGridView();
//                            clearCache();
//                        }
//                    });
        } else {
            return null;
        }
    }
}

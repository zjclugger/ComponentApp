package com.zjclugger.component.picture;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zjclugger.component.R;
import com.zjclugger.lib.base.BaseActivity;
import com.zjclugger.lib.entity.common.JMediaItem;
import com.zjclugger.router.ARouterConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片视频预览<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
@Route(path = ARouterConstants.Path.COM_PREVIEW_MEDIA)
public class MediaPreviewActivity extends BaseActivity {
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_LIST_POSITION)
    int position;
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_LIST)
    List<JMediaItem> mediaItemList;
    List<LocalMedia> mMediaSourceList = new ArrayList<>();
    private boolean mShouldExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);

        previewMedia();
    }

    private void previewMedia() {
        if (mediaItemList != null && mediaItemList.size() > 0) {
            LocalMedia localMedia;
            for (int i = 0; i < mediaItemList.size(); i++) {
                localMedia = new LocalMedia();
                localMedia.setPosition(mediaItemList.get(i).getPosition());
                localMedia.setChecked(mediaItemList.get(i).isChecked());
                localMedia.setCompressed(mediaItemList.get(i).isCompressed());
                localMedia.setCut(mediaItemList.get(i).isCut());
                localMedia.setCompressPath(mediaItemList.get(i).getCompressPath());
                localMedia.setCutPath(mediaItemList.get(i).getCutPath());
                localMedia.setPath(mediaItemList.get(i).getFilePath());
                localMedia.setDuration(mediaItemList.get(i).getDuration());
                localMedia.setMimeType(mediaItemList.get(i).getMimeType());
                localMedia.setHeight(mediaItemList.get(i).getHeight());
                localMedia.setWidth(mediaItemList.get(i).getWidth());
                localMedia.setPictureType(mediaItemList.get(i).getPictureType());
                localMedia.setNum(mediaItemList.get(i).getNum());
                mMediaSourceList.add(localMedia);
            }

            position = position > mMediaSourceList.size() - 1 ? mMediaSourceList.size() - 1 :
                    position;
            localMedia = mMediaSourceList.get(position);
            int mediaType = PictureMimeType.pictureToVideo(localMedia.getPictureType());
            switch (mediaType) {
                case 1:
                    // 预览图片 可自定长按保存路径
                    PictureSelector.create(MediaPreviewActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, mMediaSourceList);
                    break;
                case 2:
                    // 预览视频
                    PictureSelector.create(MediaPreviewActivity.this).externalPictureVideo(localMedia.getPath());
                    break;
                case 3:
                    // 预览音频
                    PictureSelector.create(MediaPreviewActivity.this).externalPictureAudio(localMedia.getPath());
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mShouldExit) {
            finish();
        } else {
            mShouldExit = true;
        }
    }
}

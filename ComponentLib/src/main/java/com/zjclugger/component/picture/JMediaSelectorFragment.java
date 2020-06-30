package com.zjclugger.component.picture;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.luck.picture.lib.entity.LocalMedia;
import com.zjclugger.basiclib.Log;
import com.zjclugger.component.picture.selector.MediaSelectorFragment;
import com.zjclugger.lib.entity.common.JMediaItem;
import com.zjclugger.router.ARouterConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
@Route(path = ARouterConstants.Path.COM_SELECT_MEDIA)
public class JMediaSelectorFragment extends MediaSelectorFragment {
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_TITLE_TEXT)
    String title;
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_IS_SINGLE_LAYOUT)
    boolean isLayout;
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_IS_PICTURE_SMALL)
    boolean isSmall;
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_IS_READ_ONLY)
    boolean isReadOnly;
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_IS_HIDE_ADD_VIEW)
    boolean isHideAddView;
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_GRID_SPAN_COUNT)
    int gridSpanCount;
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_MAX_NUM)
    int maxNumber;
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_PARAMETERS)
    Bundle parameterData;
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_BACKGROUND_COLOR)
    @ColorRes
    int layoutBackground;
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_SUB_LAYOUT)
    @LayoutRes
    int subLayout;
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_CUSTOM_ADD_VIEW_ID)
    @IdRes
    int customAddViewId;
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_IS_ONLY_SUB_LAYOUT)
    boolean isOnlySubLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        Log.d("KING", "count=" + gridSpanCount + ",small" + isSmall);
        setGridSpanCount(gridSpanCount);
        setMaxSelectNumber(maxNumber);
        if (null != parameterData) {
            ArrayList<JMediaItem> selectedList =
                    parameterData.getParcelableArrayList(ARouterConstants.KeyWord.KEY_MEDIA_SELECTED_LIST);
            if (null != selectedList && selectedList.size() > 0) {
                List<LocalMedia> localMediaList = new ArrayList<>();
                for (int i = 0; i < selectedList.size() && i < getMaxSelectNumber(); i++) {
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setMimeType(selectedList.get(i).getMimeType());
                    localMedia.setPath(selectedList.get(i).getFilePath());
                    localMediaList.add(localMedia);
                }

                setSelectedList(localMediaList);
            }
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public int layoutBackground() {
        try {
            mContext.getResources().getResourceName(layoutBackground);
        } catch (Resources.NotFoundException e) {
            return INVALID_VALUE;
        }
        return layoutBackground;
    }

    @Override
    public int subLayout() {
        try {
            mContext.getResources().getResourceName(subLayout);
        } catch (Resources.NotFoundException e) {
            return INVALID_VALUE;
        }
        return subLayout;
    }

    @Override
    public boolean isOnlySubLayout() {
        return isOnlySubLayout;
    }

    @Override
    public boolean isLayout() {
        return isLayout;
    }

    @Override
    public boolean isSmall() {
        return isSmall;
    }

    @Override
    public boolean isReadOnly() {
        return isReadOnly;
    }

    @Override
    public boolean isHideAddView() {
        return isHideAddView;
    }

    @Override
    public int customAddViewId() {
        try {
            mContext.getResources().getResourceName(customAddViewId);
        } catch (Resources.NotFoundException e) {
            return INVALID_VALUE;
        }
        return customAddViewId;
    }

    @Override
    public View.OnClickListener setOnclickListener() {
        return null;
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}
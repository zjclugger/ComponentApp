package com.zjclugger.router.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zjclugger.lib.R;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.common.JMediaItem;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.router.ARouterConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 工具类<br>
 * Created by King.Zi on 2016/10/8.<br>
 */
public class ARouterUtils {
    private static final String TAG = "ARouterUtils";

    private ARouterUtils() {
    }

    /**
     * 打开Fragment
     *
     * @param activity
     * @param fragmentClassName
     * @param params
     */
    public static void openDetailFragment(Activity activity, String fragmentClassName,
                                          Bundle params) {
        openDetailFragment(activity, fragmentClassName, params, -1, false);
    }

    /**
     * 打开Fragment
     *
     * @param activity
     * @param fragmentClassName Fragment类名
     * @param params            Fragment参数
     * @param barColor          状态栏颜色
     * @param isLightMode       状态栏字体是否为白色
     */
    public static void openDetailFragment(Activity activity, String fragmentClassName,
                                          Bundle params, int barColor, boolean isLightMode) {
        ARouter.getInstance().build(ARouterConstants.Path.COM_FRAGMENT_ACTIVITY)
                .withString(ARouterConstants.KeyWord.KEY_FSA_CLASS_NAME, fragmentClassName)
                .withBundle(ARouterConstants.KeyWord.KEY_FSA_FRAGMENT_PARAMS, params)
                .withInt(ARouterConstants.KeyWord.KEY_FSA_FRAGMENT_COLOR, barColor)
                .withBoolean(ARouterConstants.KeyWord.KEY_FSA_FRAGMENT_LIGHT, isLightMode)
                .navigation(activity, new NavCallback() {
                    @Override
                    public void onLost(Postcard postcard) {
                        WidgetUtils.toastMessage(activity, R.string.developing);
                    }

                    @Override
                    public void onArrival(Postcard postcard) {

                    }
                });
    }

    public static View.OnClickListener getPreviewImageListener(Context context, String path) {
        List<String> pathList = new ArrayList<>();
        pathList.add(path);
        return getPreviewImageListener(context, pathList);
    }

    public static View.OnClickListener getPreviewImageListener(Context context,
                                                               List<String> pathList) {
        return v -> {
            List<JMediaItem> mediaItemList = new ArrayList<>();
            JMediaItem mediaItem;
            for (String path : pathList) {
                mediaItem = new JMediaItem();
                mediaItem.setFilePath(path);
                mediaItem.setMimeType(1);
                mediaItem.setPictureType("image/jpeg");
                mediaItemList.add(mediaItem);
            }
            ARouter.getInstance().build(ARouterConstants.Path.COM_PREVIEW_MEDIA)
                    .withInt(ARouterConstants.KeyWord.KEY_MEDIA_LIST_POSITION, 0)
                    .withObject(ARouterConstants.KeyWord.KEY_MEDIA_LIST, mediaItemList)
                    .navigation(context, new NavCallback() {
                        @Override
                        public void onLost(Postcard postcard) {
                            WidgetUtils.toastMessage(context, R.string.developing);
                        }

                        @Override
                        public void onArrival(Postcard postcard) {

                        }
                    });
        };
    }

    public static void navigateTo(Context context, String url) {
        ARouter.getInstance().build(url).navigation(context,
                new NavCallback() {
                    @Override
                    public void onLost(Postcard postcard) {
                        WidgetUtils.toastMessage(context, R.string.developing);
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                    }
                });
    }

    public static void navigateBillUpload(Activity activity, String companyId, String title) {
        ARouter.getInstance().build(ARouterConstants.Path.COM_UPLOAD_BILL)
                .withString(ARouterConstants.KeyWord.KEY_COMPANY_ID, companyId)
                .withString(ARouterConstants.KeyWord.KEY_MEDIA_TITLE_TEXT, title)
                .navigation(activity, Constants.KeyCode.REQUEST_CODE_UPLOAD,
                        new NavCallback() {
                            @Override
                            public void onLost(Postcard postcard) {
                                WidgetUtils.toastMessage(activity, R.string.developing);
                            }

                            @Override
                            public void onArrival(Postcard postcard) {

                            }
                        });
    }

    /**
     * 获取选择图片的Fragment
     *
     * @param params          参数
     * @param title           标题
     * @param isSmallPicture  是否为小图片
     * @param isSingleLayout  是否为单独的布局，或为嵌入到其他布局中的子视图
     * @param isReadOnly      是否为只读，不可删除
     * @param isHideAddView   是否隐藏添加控件
     * @param isOnlySubLayout 是否只显示子视图
     * @param subLayout       子视图
     * @param customAddViewId 子视图中自定义的添加控件ID
     * @param gridSpanCount   一行显示几张
     * @param maxNumber       最大的图片数
     * @param backgroundColor 布局背景色
     * @return
     */
    public static BaseFragment getSelectPictureFragment(Bundle params, String title,
                                                        boolean isSmallPicture,
                                                        boolean isSingleLayout,
                                                        boolean isReadOnly,
                                                        boolean isHideAddView,
                                                        boolean isOnlySubLayout,
                                                        @LayoutRes int subLayout,
                                                        @IdRes int customAddViewId,
                                                        int gridSpanCount,
                                                        int maxNumber,
                                                        @ColorRes int backgroundColor) {
        return (BaseFragment) ARouter.getInstance().build(ARouterConstants.Path.COM_SELECT_MEDIA)
                .withString(ARouterConstants.KeyWord.KEY_MEDIA_TITLE_TEXT, title)
                .withBundle(ARouterConstants.KeyWord.KEY_MEDIA_PARAMETERS, params)
                .withBoolean(ARouterConstants.KeyWord.KEY_MEDIA_IS_PICTURE_SMALL, isSmallPicture)
                .withBoolean(ARouterConstants.KeyWord.KEY_MEDIA_IS_SINGLE_LAYOUT, isSingleLayout)
                .withBoolean(ARouterConstants.KeyWord.KEY_MEDIA_IS_READ_ONLY, isReadOnly)
                .withBoolean(ARouterConstants.KeyWord.KEY_MEDIA_IS_HIDE_ADD_VIEW, isHideAddView)
                .withBoolean(ARouterConstants.KeyWord.KEY_MEDIA_IS_ONLY_SUB_LAYOUT, isOnlySubLayout)
                .withInt(ARouterConstants.KeyWord.KEY_MEDIA_SUB_LAYOUT, subLayout)
                .withInt(ARouterConstants.KeyWord.KEY_MEDIA_CUSTOM_ADD_VIEW_ID, customAddViewId)
                .withInt(ARouterConstants.KeyWord.KEY_MEDIA_GRID_SPAN_COUNT, gridSpanCount)
                .withInt(ARouterConstants.KeyWord.KEY_MEDIA_MAX_NUM, maxNumber)
                .withInt(ARouterConstants.KeyWord.KEY_MEDIA_BACKGROUND_COLOR, backgroundColor)
                .navigation();
    }

    /**
     * 获取选择图片的列表
     *
     * @param fragment 选择图片的Fragment
     * @return 图片列表
     */
    public static ArrayList<JMediaItem> getSelectedPictureList(BaseFragment fragment) {
        ArrayList<JMediaItem> mediaList = null;
        if (fragment != null) {
            Bundle data = fragment.getArguments();
            if (null != data) {
                mediaList = data.getParcelableArrayList(Constants.Keywords.KEY_SELECTED_PICTURE);
            }
            Log.d("king", "the result media list size is " + (mediaList == null ? "0" :
                    mediaList.size()));
        } else {
            Log.e("king", "the fragment is null");
        }
        return mediaList;
    }
}
package com.zjclugger.lib.base;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zjclugger.basiclib.Log;
import com.zjclugger.lib.R;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.business.UserPermission;
import com.zjclugger.lib.ui.widget.JProgressDialog;
import com.zjclugger.lib.utils.Monitor;
import com.zjclugger.lib.utils.Monitor.FragmentOnKeyListener;
import com.zjclugger.lib.utils.StatusBarUtil;
import com.zjclugger.lib.utils.WidgetUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public abstract class BaseFragment extends Fragment implements FragmentOnKeyListener,
        View.OnTouchListener, SearchViewTitleListener, ISetStatusBarStyle,
        IRequestPermissionListener {
    protected JProgressDialog mProgressDialog;
    protected LayoutInflater mLayoutInflater;
    protected View mView;
    protected Context mContext;
    @ColorRes
    protected int mStatusBarColor = NO_SET_STATUS_BAR_COLOR;
    protected boolean mIsLightMode = false;
    protected Bundle mArguments;
    protected List<UserPermission> mPermissionList = new ArrayList<>();
    private Monitor.OnCloseListener mCloseListener;
    protected TextView mTitleView;
    protected ImageView mTitleBackView;
    protected TextView mTitleRightView;

    /**
     * 布局文件
     *
     * @return
     */
    public abstract int getLayout();

    /**
     * 初始化View及获取数据
     */
    public abstract void initViews();

    /**
     * 是否执行返回按键操作
     *
     * @return
     */
    public abstract boolean doBackPress();

    @Override
    public List<UserPermission> getPermissionList() {
        return new ArrayList<>();
    }

    @Override
    public boolean isPublicPermission() {
        return true;
    }

    @Override
    public final boolean noPermission() {
        if (null != mPermissionList && mPermissionList.size() > 0) {
            for (UserPermission permission : mPermissionList) {
                if (permission.isOwn()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public final boolean isAllPermission() {
        if (null != mPermissionList && mPermissionList.size() > 0) {
            for (UserPermission permission : mPermissionList) {
                if (permission.isAll()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public final void controlViewsByPermission() {
        if (null != mPermissionList && mPermissionList.size() > 0) {
            for (int i = 0; i < mPermissionList.size(); i++) {
                mPermissionList.get(i).controlView(mView);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mArguments = getArguments();
        mPermissionList =
                UserManager.getInstance().getCurrentUser().hasPermission(getPermissionList());
        if (isPublicPermission() || (null != mPermissionList && mPermissionList.size() > 0 && !noPermission())) {
            mProgressDialog = new JProgressDialog(getActivity(), false);
        } else {
            Log.e("no permission for " + this + " so close!");
            close();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        mView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, mView);
        setStatusBar();
        initSearchView();
        initViews();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //根据权限显示或隐藏view
        controlViewsByPermission();
    }

    private void initSearchView() {
        if (isSearchViewTitle()) {
            mView.findViewById(R.id.iv_search_back).setOnClickListener(view -> {
                onBackViewClick();
            });

            TextView searchView = mView.findViewById(R.id.tv_search_view);
            searchView.setOnClickListener(view -> {
                onSearchViewClick();
            });

            searchView.setHint(getSearchViewHitText());

            mView.findViewById(R.id.iv_search_more).setOnClickListener(view -> {
                onFilterViewClick();
            });
        }
    }

    protected void initDetailTitleViews(String titleText) {
        initDetailTitleViews(titleText, null, null, true);
    }

    /**
     * 初始化详情标题栏
     *
     * @param titleText
     * @param rightText
     */
    protected void initDetailTitleViews(String titleText, String rightText) {
        initDetailTitleViews(titleText, rightText, null, true);
    }

    protected void initDetailTitleViews(String titleText, String rightText,
                                        View.OnClickListener rightViewListener,
                                        boolean hasBackArrow) {
        mTitleBackView = mView.findViewById(R.id.iv_title_back);
        mTitleView = mView.findViewById(R.id.tv_title_text);
        mTitleRightView = mView.findViewById(R.id.tv_right_text);

        if (hasBackArrow) {
            mTitleBackView.setOnClickListener(v -> close());
        } else {
            mTitleBackView.setVisibility(View.INVISIBLE);
        }
        if (!TextUtils.isEmpty(titleText)) {
            mTitleView.setText(titleText);
        }

        if (!TextUtils.isEmpty(rightText)) {
            mTitleRightView.setText(rightText);
        }
        mTitleRightView.setOnClickListener(rightViewListener);
    }

    public void close() {
     /*  getFragmentManager().popBackStack();
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            getActivity().finish();
        }*/

        if (mCloseListener != null) {
            mCloseListener.onClose(getPostBackData());
        }

        if (getFragmentManager().getBackStackEntryCount() == 0) {
            getActivity().finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    /**
     * 返回数据
     *
     * @param <T>
     * @return
     */
    public <T> T getPostBackData() {
        return null;
    }

    public void setOnCloseListener(Monitor.OnCloseListener closeListener) {
        mCloseListener = closeListener;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onHidden();
        } else {
            onShown();
        }
    }

    public void onHidden() {
    }

    public void onShown() {
        setStatusBar();
    }

    /**
     * 设置状态栏样式
     *
     * @param statusBarColor 状态栏颜色
     * @param isLightMode    字体是否为白色
     */
    @Override
    public void setStatusBarStyle(int statusBarColor, boolean isLightMode) {
        mStatusBarColor = statusBarColor;
        mIsLightMode = isLightMode;
    }

    @Override
    public void setStatusBar() {
        if (mStatusBarColor != NO_SET_STATUS_BAR_COLOR) {
            StatusBarUtil.setStatusBarStyle(getActivity(), mStatusBarColor, mIsLightMode);
        }
    }

    public void showProgressDialog(int stringResId) {
        WidgetUtils.showProgressDialog(mProgressDialog, stringResId, false);
    }

    public void showProgressDialog(String message) {
        WidgetUtils.showProgressDialog(mProgressDialog, message, false);
    }

    public void showWaiting() {
        WidgetUtils.showProgressDialog(mProgressDialog, R.string.waiting, false);
    }

    public void closeProgressDialog() {
        WidgetUtils.closeProgressDialog(mProgressDialog);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public boolean isSearchViewTitle() {
        return false;
    }

    @Override
    public void onSearchViewClick() {
    }

    @Override
    public String getSearchViewHitText() {
        return getActivity().getString(R.string.info_input_query);
    }

    @Override
    public void onBackViewClick() {
        close();
    }

    @Override
    public void onFilterViewClick() {
    }

    public void closeFloatView() {
    }

    public void showMessage(String message) {
        WidgetUtils.toast(mContext, message, false);
    }
}
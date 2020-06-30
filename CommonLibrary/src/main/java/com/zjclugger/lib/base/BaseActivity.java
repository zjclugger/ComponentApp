package com.zjclugger.lib.base;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zjclugger.basiclib.Log;
import com.zjclugger.basiclib.permission.PermissionDispatcherManager;
import com.zjclugger.lib.R;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.business.UserPermission;
import com.zjclugger.lib.ui.widget.JProgressDialog;
import com.zjclugger.lib.utils.Monitor;
import com.zjclugger.lib.utils.StatusBarUtil;
import com.zjclugger.lib.utils.WidgetUtils;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
@RuntimePermissions
public abstract class BaseActivity extends AppCompatActivity implements SearchViewTitleListener,
        ISetStatusBarStyle, IRequestPermissionListener {
    private JProgressDialog mProgressDialog;
    protected Context mContext;
    @ColorRes
    protected int mStatusBarColor = NO_SET_STATUS_BAR_COLOR;
    protected boolean mIsLightMode = false;
    protected List<UserPermission> mPermissionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (savedInstanceState != null) {
            UserManager.getInstance().setCurrentUser(savedInstanceState.getParcelable("USER"));
        }
        mPermissionList =
                UserManager.getInstance().getCurrentUser().hasPermission(getPermissionList());

        if (isPublicPermission() || (null != mPermissionList && mPermissionList.size() > 0 && !noPermission())) {
            mProgressDialog = new JProgressDialog(this, false);
        } else {
            Log.e("no permission for " + this + "so finish!");
            finish();
        }
    }

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
                mPermissionList.get(i).controlView(getWindow().getDecorView());
            }
        }
    }

    /**
     * 权限控制菜单显示
     *
     * @param permissionList
     * @param bottomMenu
     */
    protected void controlMenuByPermission(List<UserPermission> permissionList, Menu bottomMenu) {
        if (null != bottomMenu && null != permissionList && permissionList.size() > 0) {
            for (int i = 0; i < permissionList.size(); i++) {
                permissionList.get(i).controlMenu(bottomMenu);
            }
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initHeaderView();
        setStatusBar();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isDetailActivity()) {
            finish();
        }
    }

    /**
     * 是否为详情页
     *
     * @return
     */
    protected boolean isDetailActivity() {
        return false;
    }

    /**
     * 详情页标题
     *
     * @return
     */
    protected String getDetailActivityTitle() {
        return getString(R.string.title_detail);
    }

    @Override
    public boolean isSearchViewTitle() {
        return false;
    }

    @Override
    public String getSearchViewHitText() {
        return getString(R.string.info_input_query);
    }

    @Override
    public void onBackViewClick() {
        onBackPressed();
    }

    @Override
    public void onSearchViewClick() {
    }

    @Override
    public void onFilterViewClick() {
    }

    /**
     * 指定页面布局前(setContentView)调用
     *
     * @param statusBarColor
     * @param isLightMode
     */
    @Override
    public void setStatusBarStyle(int statusBarColor, boolean isLightMode) {
        mStatusBarColor = statusBarColor;
        mIsLightMode = isLightMode;
    }

    @Override
    public void setStatusBar() {
        if (mStatusBarColor != NO_SET_STATUS_BAR_COLOR) {
            StatusBarUtil.setStatusBarStyle(this, mStatusBarColor, mIsLightMode);
        }
    }

    protected void showProgressDialog(int stringResId) {
        WidgetUtils.showProgressDialog(mProgressDialog, stringResId, false);
    }

    protected void showProgressDialog(int stringResId, Monitor.TimeoutListener timeoutListener) {
        WidgetUtils.showProgressDialog(mProgressDialog, stringResId, false, timeoutListener, 30);
    }

    protected void showProgressDialog(int stringResId, Monitor.TimeoutListener timeoutListener,
                                      int timeout) {
        WidgetUtils.showProgressDialog(mProgressDialog, stringResId, false, timeoutListener,
                timeout);
    }

    protected void showWaiting() {
        WidgetUtils.showProgressDialog(mProgressDialog, R.string.waiting, false);
    }

    protected void closeProgressDialog() {
        WidgetUtils.closeProgressDialog(mProgressDialog);
    }

    private void initHeaderView() {
        if (isDetailActivity()) {
            ImageView backImageView = findViewById(R.id.iv_title_back);
            backImageView.setOnClickListener(view -> {
                onBackViewClick();
            });

            TextView titleView = findViewById(R.id.tv_title_text);
            titleView.setText(getDetailActivityTitle());
        }

        if (isSearchViewTitle()) {
            findViewById(R.id.iv_search_back).setOnClickListener(view -> {
                onBackViewClick();
            });

            TextView searchView = findViewById(R.id.tv_search_view);
            searchView.setOnClickListener(view -> {
                onSearchViewClick();
            });

            searchView.setHint(getSearchViewHitText());

            findViewById(R.id.iv_search_more).setOnClickListener(view -> {
                onFilterViewClick();
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //解决activity被停掉后，UserManager会重建的问题
        outState.putParcelable("USER", UserManager.getInstance().getCurrentUser());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PermissionDispatcherManager.getInstance().isDialogShowing()) {
            BaseActivityPermissionsDispatcher.requestStoragePermissionWithPermissionCheck(BaseActivity.this);
        }

        controlViewsByPermission();
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void requestStoragePermission() {
        //申请权限
        Log.d("已取得授权");
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForStorage(PermissionRequest request) {
        PermissionDispatcherManager.getInstance().showRationaleDialog(this, "",
                mContext.getString(R.string.msg_permission_storage), request, true);
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onStoragePermissionNeverAskAgain() {
        //被拒绝并勾选不在提醒授权时调用
        PermissionDispatcherManager.getInstance().showAskAgainDialog(this, "",
                mContext.getString(R.string.msg_permission_storage_require), true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }

    /**
     * 关闭二级详情页
     */
    protected void closeDetailFragment() {
        for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
            if (!(getSupportFragmentManager().getFragments().get(i) instanceof BaseFragment)) {
                getSupportFragmentManager().popBackStack();
            }
        }
    }
}
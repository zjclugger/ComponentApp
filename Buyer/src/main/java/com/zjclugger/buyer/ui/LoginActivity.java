package com.zjclugger.buyer.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zjclugger.buyer.R;
import com.zjclugger.buyer.business.PreferencesUtil;
import com.zjclugger.buyer.ui.vm.BuyerViewModel;
import com.zjclugger.buyer.webapi.response.UserPermissionResult;
import com.zjclugger.lib.api.ApiResponseUtils;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.ui.uc.BaseLoginActivity;
import com.zjclugger.lib.ui.widget.JProgressDialog;
import com.zjclugger.lib.utils.StatusBarUtil;
import com.zjclugger.router.ARouterConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 买家版-登录<br>
 * Created by King.Zi on 2020/4/21.<br>
 * Copyright (c) 2020 zjclugger.com
 */
@Route(path = ARouterConstants.Path.USER_LOGIN)
public class LoginActivity extends BaseLoginActivity {
    private BuyerViewModel mViewModel;
    private JProgressDialog mProgressDialog;

    @Override
    public int getImageId() {
        return R.mipmap.ic_eshop_buyer;
    }

    @Override
    public void login(String userName, String password) {
        //for test
        PreferencesUtil.getInstance().putLoginParameters(userName, password, "buyer");
        toWorkbench();
/*
        mViewModel.userLogin(userName, password).observe(this,
                response -> {
                    WidgetUtils.closeProgressDialog(mProgressDialog);

                    if (ApiResponseUtils.isSuccess(response, "登录失败，请重试！")) {
                        BaseWrapperEntity<UserLoginResult> wrapperEntity = response.body;
                        if (wrapperEntity.getResult() != null) {
                            UserLoginResult result = wrapperEntity.getResult();
                            User user = new User();
                            user.setId(result.getUserInfo().getId());
                            user.setUid(userName);
                            user.setUserName(result.getUserInfo().getName());
                            user.setUserPassword(password);
                            user.setAuthCode(result.getAuthCode());
                            user.setToken(result.getSession());
                            user.setHasAccountSet(result.hasAccountSet());
                            user.setCompanyId(result.getUserInfo().getOrgId());
                            UserManager.getInstance().setCurrentUser(user);

                            PreferencesUtil.getInstance().putLoginParameters(userName
                                    , password, result.getAuthCode());
                            getPermission();
                        }
                    }
                });
                */
    }

    @Override
    public List<String> getCacheUserInfo() {
        List<String> userInfoList = new ArrayList<>();
        userInfoList.add(PreferencesUtil.getInstance().getLoginUser());
        userInfoList.add(PreferencesUtil.getInstance().getLoginUserPassword());
        return userInfoList;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new JProgressDialog(this, false);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.bg_white));
        mViewModel = ViewModelProviders.of(this).get(BuyerViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserManager.getInstance().clear();
    }

    private void getPermission() {
        mViewModel.getPermissionList().observe(this,
                response -> {
                    UserManager.getInstance().getCurrentUser().clearPermissionMap();

                    if (ApiResponseUtils.isSuccess(response, "获取用户权限失败")) {
                        List<UserPermissionResult> permissionResultList = response.body.getResult();
                        if (null != permissionResultList && permissionResultList.size() > 0) {
                            for (int i = 0; i < permissionResultList.size(); i++) {
                                UserManager.getInstance().getCurrentUser().getPermissionMap().put(permissionResultList.get(i).getPermission(), permissionResultList.get(i).getPermissionDescription());
                            }
                        }

                        //to workbench
                        toWorkbench();
                    }
                });
    }

    private void toWorkbench() {
        Intent intent = new Intent(LoginActivity.this, BuyerMainActivity.class);
        startActivity(intent);
        finish();
    }
}
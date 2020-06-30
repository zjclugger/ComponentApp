package com.zjclugger.seller.ui.uc;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.seller.R;

import butterknife.BindView;

/**
 * 个人信息<br>
 * Created by King.Zi on 2020/5/13.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class UserInfoFragment extends BaseFragment {
    private final static String TAG = "UserInfo";

    @BindView(R.id.user_name)
    TextView mUserNameView;
    @BindView(R.id.user_password)
    TextView mPasswordView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarStyle(R.color.bg_white, false);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_user_info;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("个人信息");

        mUserNameView.setOnClickListener(v -> {
            showMessage("点击了-用户名");
        });
        mPasswordView.setOnClickListener(v -> {
            showMessage("点击了-密码");
        });
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}
package com.zjclugger.buyer.ui.uc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zjclugger.buyer.R;
import com.zjclugger.lib.base.BaseApplication;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.router.ARouterConstants;
import com.zjclugger.router.utils.ARouterUtils;

import butterknife.BindView;

/**
 * 个人中心<br>
 * Created by King.Zi on 2020/4/26.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class UserProfileFragment extends BaseFragment {
    private final static String TAG = "UserProfile";

    @BindView(R.id.user_name)
    TextView mUserNameView;
    @BindView(R.id.user_logout_button)
    Button mLogoutView;

    @BindView(R.id.user_pay_mode)
    TextView mPayModeView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarStyle(R.color.bg_white, false);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_user_profile;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("个人中心", null, null, false);
        mPayModeView.setOnClickListener(v -> ARouterUtils.openDetailFragment(getActivity(),
                PayModeFragment.class.getName(), null, R.color.bg_white, false));


        if (UserManager.getInstance().getCurrentUser() != null) {
            mUserNameView.setText(UserManager.getInstance().getCurrentUser().getUserName());
            //mUserPostView.setText(UserManager.getInstance().getCurrentUser().getUid());
        }
     /*   mPasswordView.setOnClickListener(v -> ARouterUtils.openDetailFragment(getActivity(),
                UserPasswordFragment.class.getName(), null));
        mCheckUpgradeView.setOnClickListener(v -> WidgetUtils.toastMessage(mContext,
                R.string.developing));*/
        mLogoutView.setOnClickListener(v -> {
            //WidgetUtils.toastMessage(mContext, "正在退出...");
            showProgressDialog("正在退出...");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //todo:模拟注销-没接口
                        Thread.sleep(1000);
                        closeProgressDialog();
                        ARouter.getInstance()
                                .build(ARouterConstants.Path.USER_LOGIN)
                                .navigation(BaseApplication.getInstance());
                        BaseApplication.getInstance().killAllActivity();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
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
package com.zjclugger.seller.ui.uc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zjclugger.lib.base.BaseApplication;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.utils.StatusBarUtil;
import com.zjclugger.router.ARouterConstants;
import com.zjclugger.router.utils.ARouterUtils;
import com.zjclugger.seller.R;
import com.zjclugger.seller.ui.shop.ShopSettingFragment;
import com.zjclugger.seller.utils.SellerConstants;

import butterknife.BindView;

/**
 * 个人中心<br>
 * Created by King.Zi on 2020/4/26.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class UserProfileFragment extends BaseFragment {
    private final static String TAG = "UserProfile";

    @BindView(R.id.shop_name)
    TextView mShopNameView;
    @BindView(R.id.shop_status)
    TextView mShopStatusView;
    @BindView(R.id.user_logout_button)
    Button mLogoutView;

    @BindView(R.id.shop_setting)
    TextView mShopSettingView;
    @BindView(R.id.user_no_manage)
    TextView mUserNOManageView;

    @Override
    public void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), 0, null);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_user_profile;
    }

    @Override
    public void initViews() {
        // initDetailTitleViews("个人中心", null, null, false);
      /*  mShopSettingView.setOnClickListener(v -> ARouterUtils.openDetailFragment(getActivity(),
                PayModeFragment.class.getName(), null, R.color.bg_white, false));
*/

        if (UserManager.getInstance().getCurrentUser() != null) {
            mShopNameView.setText("店铺名");
            mShopStatusView.setText("营业中");
        }
     /*   mPasswordView.setOnClickListener(v -> ARouterUtils.openDetailFragment(getActivity(),
                UserPasswordFragment.class.getName(), null));
        mCheckUpgradeView.setOnClickListener(v -> WidgetUtils.toastMessage(mContext,
                R.string.developing));*/

        mShopSettingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putBoolean(SellerConstants.Keywords.KEY_IS_JOINED, true);
                ARouterUtils.openDetailFragment(getActivity(),
                        ShopSettingFragment.class.getName(), params, R.color.bg_white, false);
            }
        });

        mUserNOManageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* ARouterUtils.openDetailFragment(getActivity(),
                        UserInfoFragment.class.getName(), null, R.color.bg_white, false);*/
                Bundle params = new Bundle();
                params.putBoolean(SellerConstants.Keywords.KEY_IS_JOINED, false);
                ARouterUtils.openDetailFragment(getActivity(),
                        ShopSettingFragment.class.getName(), params, R.color.bg_white, false);
                //startActivity(new Intent(LoadingActivity.this, ShopJoinActivity.class));
            }
        });

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
package com.zjclugger.seller.ui.uc;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zjclugger.seller.R;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.view.LabelValueView;

import butterknife.BindView;

/**
 * 个人中心<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class UserCompanyInfoFragment extends BaseFragment {
    private final static String TAG = "UserCompany";

    @BindView(R.id.company_logo)
    ImageView mCompanyLogo;
    @BindView(R.id.company_name)
    TextView mCompanyName;
    @BindView(R.id.company_full_name)
    TextView mCompanyFullName;
    @BindView(R.id.user_company_detail)
    TextView mCompanyDetail;
    @BindView(R.id.company_address)
    LabelValueView mCompanyAddress;
    @BindView(R.id.company_phone)
    LabelValueView mCompanyContact;
    @BindView(R.id.company_url)
    LabelValueView mCompanyWebsite;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_user_company_info;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("关于公司");
     /*   mCompany = UserManager.getInstance().getCurrentUser().getCompany();
        mTitleView.setText("关于公司");
        mCompanyLogo.setBackgroundResource(R.mipmap.img_picture_default);
        mCompanyName.setText(mCompany.getName());
        mCompanyFullName.setText(mCompany.getName());
        mCompanyDetail.setText(mCompany.getDescription());
        mCompanyAddress.setValueText(mCompany.getCompanyAddress());
        mCompanyContact.setValueText(mCompany.getContact());
        mCompanyWebsite.setValueText(mCompany.getContactDetails());*/
    }

    @Override
    public boolean doBackPress() {
        return false;
    }

    @Override
    public <T> T getPostBackData() {
        return null;
    }
}
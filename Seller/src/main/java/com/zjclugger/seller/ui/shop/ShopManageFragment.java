package com.zjclugger.seller.ui.shop;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.view.ImageTextView;
import com.
        zjclugger.router.utils.ARouterUtils;
import com.zjclugger.seller.R;
import com.zjclugger.seller.ui.goods.GoodsListManageFragment;

import butterknife.BindView;

/**
 * 门店运管<br>
 * Created by King.Zi on 2020/5/13.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ShopManageFragment extends BaseFragment {
    private final static String TAG = "ShopManage";
    @BindView(R.id.goods_manage)
    ImageTextView mGoodsManageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarStyle(R.color.bg_white, false);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_shop_manage;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("门店运营", null, null, false);
        mGoodsManageView.setOnClickListener(v -> {
            ARouterUtils.openDetailFragment(getActivity(),
                    GoodsListManageFragment.class.getName(), null, R.color.bg_white, false);
        });
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        //return mIsJoined ? true : false;
        return false;
    }
}
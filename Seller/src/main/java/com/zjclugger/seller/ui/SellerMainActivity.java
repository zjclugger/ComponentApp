package com.zjclugger.seller.ui;

import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.base.BaseMainActivity;
import com.zjclugger.seller.R;
import com.zjclugger.seller.ui.order.OrderManageFragment;
import com.zjclugger.seller.ui.shop.ShopManageFragment;
import com.zjclugger.seller.ui.uc.UserProfileFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * ERP portal<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class SellerMainActivity extends BaseMainActivity {
    @Override
    public int getMenu() {
        return R.menu.menu_seller;
    }

    @Override
    public int getMenuCheckedIndex() {
        return 0;
    }

    @Override
    public Map<Integer, BaseFragment> getFragmentList() {
        Map<Integer, BaseFragment> fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.erp_menu_order, new OrderManageFragment());
        fragmentMap.put(R.id.erp_menu_shop, new ShopManageFragment());
        fragmentMap.put(R.id.erp_menu_my, new UserProfileFragment());
        return fragmentMap;
    }

    @Override
    protected boolean isTranslucentForImageViewInFragment() {
        return true;
    }
}
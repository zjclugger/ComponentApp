package com.zjclugger.buyer.ui;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.ui.im.MessageListFragment;
import com.zjclugger.buyer.ui.order.OrderManageFragment;
import com.zjclugger.buyer.ui.shopcart.ShopCartFragment;
import com.zjclugger.buyer.ui.uc.UserProfileFragment;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.base.BaseMainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 买家版-主页<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class BuyerMainActivity extends BaseMainActivity {
    @Override
    public int getMenu() {
        return R.menu.menu_portal;
    }

    @Override
    public int getMenuCheckedIndex() {
        return 0;
    }

    @Override
    public Map<Integer, BaseFragment> getFragmentList() {
        Map<Integer, BaseFragment> fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.buyer_menu_home, new HomeFragment());
        fragmentMap.put(R.id.buyer_menu_order, new OrderManageFragment());
        fragmentMap.put(R.id.buyer_menu_service, new MessageListFragment());
        fragmentMap.put(R.id.buyer_menu_shopping_cart, new ShopCartFragment());
        fragmentMap.put(R.id.buyer_menu_my, new UserProfileFragment());
        return fragmentMap;
    }
}
package com.zjclugger.lib.base;

import android.os.Bundle;
import android.util.SparseArray;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.zjclugger.lib.R;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.business.UserPermission;
import com.zjclugger.lib.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ERP portal<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public abstract class BaseMainActivity extends BaseActivity {
    private BottomNavigationView mBottomNavigation;
    private SparseArray<BaseFragment> mFragments = new SparseArray<>();
    private BaseFragment mLastFragment;
    private Map<Integer, BaseFragment> mFragmentMap = new HashMap<>();

    public abstract int getMenu();

    public abstract int getMenuCheckedIndex();

    public abstract Map<Integer, BaseFragment> getFragmentList();

    protected List<UserPermission> getMenuPermissionList() {
        return new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_main);
        if (isTranslucentForImageViewInFragment()) {
            findViewById(R.id.base_main_layout).setFitsSystemWindows(false);
            StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
        }
        mBottomNavigation = findViewById(R.id.base_bottom_navigation);
        mBottomNavigation.inflateMenu(getMenu());
        mBottomNavigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        mFragmentMap = getFragmentList();

        mBottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            setFragmentIndicator(menuItem.getItemId());
            return true;
        });
        mBottomNavigation.getMenu().getItem(getMenuCheckedIndex()).setChecked(true);

        setFragmentIndicator(mBottomNavigation.getMenu().getItem(getMenuCheckedIndex()).getItemId());
    }

    private void setFragmentIndicator(int tag) {
        closeDetailFragment();
        BaseFragment newFragment = mFragments.get(tag);
        FragmentManager fmManager = getSupportFragmentManager();
        //开启一个fragment事务
        FragmentTransaction transaction = fmManager.beginTransaction();
        if (mLastFragment != null) {
            //隐藏正在显示的fragment ，这里也可以用 transaction.detach(mLastFragment)
            // 会将view从UI中移除，执行onDestroyView()
            mLastFragment.doBackPress();
            transaction.hide(mLastFragment);
            //transaction.detach(mLastFragment);  //对应transaction.attach(newInfo)
        }
        if (newFragment == null) {
            // 如果newInfo为空，则创建一个并添加到界面上
            boolean isFound = false;
            if (mFragmentMap != null) {
                for (Map.Entry<Integer, BaseFragment> entry : mFragmentMap.entrySet()) {
                    if (entry.getKey().intValue() == tag) {
                        newFragment = entry.getValue();
                        isFound = true;
                        break;
                    }
                }
            }
            mFragments.put(tag, newFragment);
            transaction.add(R.id.content_fragment, newFragment, String.valueOf(tag));
        } else {
            // 如果newInfo不为空，则直接将它显示出来 ，对应的这里可以用 transaction.attach(newInfo)
            // 重建view视图，附加到UI上并显示，会重新执行onActivityView()
            transaction.show(newFragment);
            //transaction.attach(newFragment);
        }
        mLastFragment = newFragment;
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<UserPermission> userPermissionList =
                UserManager.getInstance().getCurrentUser().hasMenuPermission(getMenuPermissionList());
        controlMenuByPermission(userPermissionList, mBottomNavigation.getMenu());
    }

    @Override
    public void onBackPressed() {
        if (mLastFragment != null) {
            if (mLastFragment.doBackPress()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isTranslucentForImageViewInFragment() {
        return false;
    }
}
package com.zjclugger.lib.view.viewpager;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zjclugger.lib.base.BaseFragment;

import java.util.List;

/**
 * item页ViewPager的内容适配器 <br>
 * Created by King.Zi on 2020/4/28.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ItemTitlePagerAdapter extends FragmentPagerAdapter {
    private String[] titleArray;
    private List<BaseFragment> fragmentList;

    public ItemTitlePagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList,
                                 String[] titleArray) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleArray = titleArray;
    }

    public void setFragmentData(List<BaseFragment> fragmentList) {
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    public void setTitleData(String[] titleArray) {
        this.titleArray = titleArray;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }

    @Override
    public int getCount() {
        return titleArray.length;
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragmentList.get(position);
    }
}

package com.zjclugger.lib.utils;

import android.text.TextUtils;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @title <br>
 * Created by King.Zi on 2020/1/3.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class FragmentUtils {
    private FragmentUtils() {
    }

    public static void addFragment(FragmentActivity activity, Fragment fragment,
                                   @IdRes int containerViewId, String backStackValue) {
        FragmentManager fmManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fmManager.beginTransaction();
        ft.add(containerViewId, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (!TextUtils.isEmpty(backStackValue)) {
            ft.addToBackStack(backStackValue);
        }
        ft.commit();
    }

    public static void replaceFragment(FragmentActivity activity, Fragment fragment,
                                       @IdRes int containerViewId, String backStackValue) {
        FragmentManager fmManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fmManager.beginTransaction();
        ft.replace(containerViewId, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(backStackValue);
        ft.commit();
    }

    public static void replaceChildFragment(Fragment fragment, Fragment childFragment,
                                       @IdRes int containerViewId, String backStackValue) {
        FragmentManager fmManager = fragment.getChildFragmentManager();
        FragmentTransaction ft = fmManager.beginTransaction();
        ft.replace(containerViewId, childFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(backStackValue);
        ft.commit();
    }

    public static void addFragment(FragmentActivity activity, Fragment fragment,
                                   @IdRes int containerViewId) {
        addFragment(activity, fragment, containerViewId, null);
    }

    public static void replaceFragment(FragmentActivity activity, Fragment fragment,
                                       @IdRes int containerViewId) {
        replaceFragment(activity, fragment, containerViewId, null);
    }

    public static void addFirstFragment(FragmentActivity activity, Fragment fragment,
                                        @IdRes int containerViewId) {
        FragmentManager fmManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fmManager.beginTransaction();
        ft.replace(containerViewId, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}

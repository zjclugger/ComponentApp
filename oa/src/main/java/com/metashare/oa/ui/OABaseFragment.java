package com.zjclugger.oa.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.zjclugger.lib.base.BaseFragment;

/**
 * @title <br>
 * Created by King.Zi on 2020/1/2.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public abstract class OABaseFragment extends BaseFragment {
    protected OAViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(OAViewModel.class);
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}

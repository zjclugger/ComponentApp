package com.zjclugger.finance.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.zjclugger.finance.R;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.ui.widget.ErpAlertDialog;

/**
 * 票据详情页（二级页面）<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public abstract class BaseBillDetailFragment extends BaseFragment {
    private final static String TAG = "BaseDetail";
    protected FinanceViewModel mViewModel;
    protected TextView mTitleView;
    protected TextView mTitleRightView;
    protected Button mLeftButton;
    protected Button mCenterButton;
    protected Button mRightButton;
    protected ErpAlertDialog mAlertDialog;

    @Override
    public boolean isPublicPermission() {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(FinanceViewModel.class);
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initViews() {
    }

    protected void initButtonLayout(View buttonLayout) {
        if (null != buttonLayout) {
            mLeftButton = buttonLayout.findViewById(R.id.layout_left_button);
            mCenterButton = buttonLayout.findViewById(R.id.layout_center_button);
            mRightButton = buttonLayout.findViewById(R.id.layout_right_button);
        }
    }

    protected void showDialog(String message, View.OnClickListener confirmListener) {
        if (mAlertDialog == null) {
            mAlertDialog = new ErpAlertDialog(mContext)
                    .setMessage(message)
                    .setConfirmText(getString(R.string.text_continue))
                    .setCancelText(getString(R.string.text_cancel))
                    .setCanceledOnTouchOutside(true)
                    .setOnClickListener(confirmListener,
                            v -> {
                                mAlertDialog.dismiss();
                            });
        }
        mAlertDialog.setMessage(message);
        mAlertDialog.show();
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return true;
    }
}
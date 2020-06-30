package com.zjclugger.buyer.ui.order;

import android.view.View;

import androidx.annotation.NonNull;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.PayModeResult;
import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

/**
 * 付款方式<br>
 * Created by King.Zi on 2020/5/8<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class CashierPayModeAdapter extends JBaseQuickAdapter<PayModeResult, JBaseViewHolder> {

    public CashierPayModeAdapter(int layoutResId, List<PayModeResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, PayModeResult item) {
        ExtendLabelValueView payModeView = holder.getView(R.id.elvv_pay_mode);
        if (!item.isDefault()) {
            payModeView.setLabelText(item.getName());
            payModeView.setRightImage(item.isChecked() ? R.mipmap.ic_circle_checkbox_checked :
                    R.mipmap.ic_circle_chk_normal_small);
        } else {
            payModeView.setVisibility(View.GONE);
            holder.getView(R.id.cashier_pay_list_layout).setVisibility(View.GONE);
        }

       /* if (!TextUtils.isEmpty(item.getUrl())) {
            ImageUtils.loadImage(mContext, payModeView.getRightImageView(), item.getUrl());
        }

        payModeView.getLabelView().setText(item.getName());
        if (item.isDefault()) {
            payModeView.getRightImageView().setVisibility(View.VISIBLE);
            payModeView.getRightImageView().setImageResource(R.mipmap.drop_down_checked);
        } else {
            payModeView.getRightImageView().setVisibility(View.INVISIBLE);
        }*/
    }
}
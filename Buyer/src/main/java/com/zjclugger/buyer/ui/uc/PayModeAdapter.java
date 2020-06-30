package com.zjclugger.buyer.ui.uc;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.PayModeResult;
import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

/**
 * 附近商家<br>
 * Created by King.Zi on 2020/4/26.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class PayModeAdapter extends JBaseQuickAdapter<PayModeResult, JBaseViewHolder> {

    public PayModeAdapter(int layoutResId, List<PayModeResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, PayModeResult item) {
        android.util.Log.d("KING", "name=" + item.getName() + ",default=" + item.isDefault());
        ExtendLabelValueView payModeView = holder.getView(R.id.elvv_pay_mode_view);
        if (!TextUtils.isEmpty(item.getUrl())) {
            ImageUtils.loadImage(mContext, payModeView.getRightImageView(), item.getUrl());
        }

        payModeView.getLabelView().setText(item.getName());
        if (item.isDefault()) {
            payModeView.getRightImageView().setVisibility(View.VISIBLE);
            payModeView.getRightImageView().setImageResource(R.mipmap.ic_drop_down_checked);
        } else {
            payModeView.getRightImageView().setVisibility(View.INVISIBLE);
        }
    }
}
package com.zjclugger.finance.ui.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zjclugger.finance.R;
import com.zjclugger.finance.webapi.entity.response.OriginalBillResult;
import com.zjclugger.lib.ui.adapter.ERPBaseQuickAdapter;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;

import java.util.List;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OriginalBillAdapter extends ERPBaseQuickAdapter<OriginalBillResult,
        ERPBaseViewHolder> {

    public OriginalBillAdapter(int layoutResId, List<OriginalBillResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull ERPBaseViewHolder holder, OriginalBillResult item) {
        final ImageView billImage = holder.getView(R.id.bill_image);
        if (!TextUtils.isEmpty(item.getUrl())) {
            ImageUtils.loadImage(mContext, billImage, item.getUrl());
        }
        holder.setLabelText(R.id.bill_company_name, item.getPurchaserName() == null ? "" :
                item.getPurchaserName().trim());
        holder.setValueText(R.id.bill_amount, String.format("%.2f", item.getTotalAmount()));
        holder.setLabelText(R.id.bill_upload_date, ERPUtils.getDate(item.getUploadDate()));
        if (!TextUtils.isEmpty(item.getBillType())) {
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_money);
            drawable.setBounds(0, 0, 40, 40);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
            TextView billTypeView = holder.getView(R.id.bill_type);
            billTypeView.setCompoundDrawables(drawable, null, null, null);
            holder.setText(R.id.bill_type, item.getBillType());
        }
    }
}
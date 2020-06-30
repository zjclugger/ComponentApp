package com.zjclugger.finance.ui.adapter;

import androidx.annotation.NonNull;

import com.zjclugger.finance.R;
import com.zjclugger.finance.webapi.entity.response.ReportNameAmountEntity;
import com.zjclugger.lib.ui.adapter.ERPBaseQuickAdapter;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;

import java.util.List;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ReportNameAmountAdapter extends ERPBaseQuickAdapter<ReportNameAmountEntity,
        ERPBaseViewHolder> {

    public ReportNameAmountAdapter(List<ReportNameAmountEntity> data) {
        super(R.layout.item_report_name_amount, data);
    }

    @Override
    protected void convert(@NonNull ERPBaseViewHolder holder, ReportNameAmountEntity item) {
        holder.setLabelValueText(R.id.report_item_view, item.getName(),
                ERPUtils.getDouble(item.getAmount(), null));
    }
}
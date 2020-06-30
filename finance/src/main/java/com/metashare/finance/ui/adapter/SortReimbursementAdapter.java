package com.zjclugger.finance.ui.adapter;

import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zjclugger.finance.R;
import com.zjclugger.lib.entity.common.ERPListItem;
import com.zjclugger.lib.ui.adapter.ERPBaseQuickAdapter;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;

import java.util.List;

/**
 * 本月报销排行<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class SortReimbursementAdapter extends ERPBaseQuickAdapter<ERPListItem<Float>,
        ERPBaseViewHolder> {

    public SortReimbursementAdapter(int layoutResId, List<ERPListItem<Float>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull ERPBaseViewHolder holder, ERPListItem<Float> item) {
        holder.setText(R.id.label_text, item.getText());
        TextView view = holder.getView(R.id.label_value);
        view.setGravity(Gravity.RIGHT);
        view.setText(ERPUtils.getFloat(item.getValue(), "", mContext.getString(R.string.default_rmb_string)));
    }
}
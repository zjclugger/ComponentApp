package com.zjclugger.finance.ui.adapter;

import androidx.annotation.NonNull;

import com.zjclugger.finance.R;
import com.zjclugger.lib.entity.common.ERPListItem;
import com.zjclugger.lib.ui.adapter.ERPBaseQuickAdapter;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;
import com.warkiz.widget.IndicatorSeekBar;

import java.util.List;

/**
 * 个人报销<br>
 * Created by King.Zi on 2020/02/06.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ReimbursementAdapter extends ERPBaseQuickAdapter<ERPListItem<Float>,
        ERPBaseViewHolder> {

    private float mTotal;

    public ReimbursementAdapter(float total, int layoutResId,
                                List<ERPListItem<Float>> data) {
        super(layoutResId, data);
        mTotal = total;
    }

    public void notifyDataChanged(float total) {
        mTotal = total;
        notifyDataChanged();
    }

    @Override
    protected void convert(@NonNull ERPBaseViewHolder holder, ERPListItem<Float> item) {
        IndicatorSeekBar seekBar = holder.getView(R.id.personal_seek_bar);
        seekBar.setMax(mTotal);
        seekBar.setProgress(item.getValue());

        holder.setText(R.id.personal_name_view, item.getText());
        holder.setText(R.id.personal_percentage,
                ERPUtils.getPercentage(item.getValue() / mTotal, ""));
    }
}

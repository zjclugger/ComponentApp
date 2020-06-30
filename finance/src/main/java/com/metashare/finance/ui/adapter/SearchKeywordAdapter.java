package com.zjclugger.finance.ui.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zjclugger.finance.R;
import com.zjclugger.lib.ui.adapter.ERPBaseSectionQuickAdapter;
import com.zjclugger.lib.ui.adapter.ERPSectionEntity;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;

import java.util.List;

/**
 * <br>
 * Created by King.Zi on 2020/4/9<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class SearchKeywordAdapter extends ERPBaseSectionQuickAdapter<ERPSectionEntity,
        ERPBaseViewHolder> {

    public SearchKeywordAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(ERPBaseViewHolder helper, ERPSectionEntity item) {
        helper.setText(R.id.header_text, item.header);
        helper.setVisible(R.id.iv_header_more, item.isMore());
        helper.addOnClickListener(R.id.iv_header_more);
    }

    @Override
    protected void convert(@NonNull ERPBaseViewHolder holder, ERPSectionEntity item) {
        TextView itemView = holder.getView(R.id.item_text);
        itemView.setText(item.t.getText());
    }
}

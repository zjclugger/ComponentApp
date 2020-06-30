package com.zjclugger.seller.ui.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zjclugger.seller.R;
import com.zjclugger.lib.ui.adapter.JBaseSectionQuickAdapter;
import com.zjclugger.lib.ui.adapter.JSectionEntity;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

/**
 * <br>
 * Created by King.Zi on 2020/4/9<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class SubSystemAdapter extends JBaseSectionQuickAdapter<JSectionEntity,
        JBaseViewHolder> {

    public SubSystemAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(JBaseViewHolder helper, JSectionEntity item) {
        helper.setText(R.id.header_text, item.header);
        helper.setVisible(R.id.iv_header_more, item.isMore());
        helper.addOnClickListener(R.id.iv_header_more);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, JSectionEntity item) {
        TextView itemView = holder.getView(R.id.item_text);
        itemView.setText(item.t.getText());
    }
}

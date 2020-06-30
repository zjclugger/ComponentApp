package com.zjclugger.component.module.upload.uploader;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zjclugger.component.R;
import com.zjclugger.lib.ui.adapter.JBaseSectionQuickAdapter;
import com.zjclugger.lib.ui.adapter.JSectionEntity;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

/**
 * <br>
 * Created by King.Zi on 2020/4/9<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class BillReasonSelectAdapter extends JBaseSectionQuickAdapter<JSectionEntity,
        JBaseViewHolder> {
    private boolean isNotFirst;

    public BillReasonSelectAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(JBaseViewHolder helper, JSectionEntity item) {
        TextView headerView = helper.getView(R.id.header_text);
        headerView.setTextSize(14);
        headerView.setTextColor(mContext.getResources().getColor(R.color.primary_gray));
        headerView.setText(item.header);
        helper.setVisibility(R.id.header_line, !item.isFirstHeader());
        helper.setVisible(R.id.iv_header_more, item.isMore());
        helper.addOnClickListener(R.id.iv_header_more);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, JSectionEntity item) {
        TextView itemView = holder.getView(R.id.item_text);
        itemView.setText(item.t.getText());
    }
}

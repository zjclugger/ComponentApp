package com.zjclugger.seller.ui.adapter;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zjclugger.lib.entity.common.JListItem;
import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;
import com.zjclugger.seller.R;

import java.util.List;

/**
 * 商品分类<br>
 * Created by King.Zi on 2020/4/17.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class CategoryAdapter extends JBaseQuickAdapter<JListItem<Integer>, JBaseViewHolder> {
    public CategoryAdapter(int layoutResId, List<JListItem<Integer>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, JListItem<Integer> result) {
       /* holder.setLabelText(R.id.hr_status_view, String.valueOf(result.getCount()));
        holder.setValueText(R.id.hr_status_view, result.getName());*/
        holder.setText(R.id.label_text, result.getText());
        TextView view = holder.getView(R.id.label_value);
        int padding = ViewUtils.dp2px(mContext, 10);
        view.setPadding(padding, padding, padding, padding);
        view.setGravity(Gravity.RIGHT);
        view.setText(String.valueOf(result.getValue()) +"件商品");
        ImageView rightView = holder.getView(R.id.label_image_right);
        rightView.setVisibility(View.VISIBLE);
        rightView.setImageResource(R.mipmap.ic_arrow_right);
    }
}

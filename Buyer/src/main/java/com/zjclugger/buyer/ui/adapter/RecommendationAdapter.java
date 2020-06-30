package com.zjclugger.buyer.ui.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.GoodsResult;
import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

/**
 * <br>
 * Created by King.Zi on 2020/4/10.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class RecommendationAdapter extends JBaseQuickAdapter<GoodsResult, JBaseViewHolder> {

    public RecommendationAdapter(int layoutResId, List<GoodsResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, GoodsResult item) {
        final ImageView imageView = holder.getView(R.id.image_view);
        if (!TextUtils.isEmpty(item.getUrl())) {
            ImageUtils.loadImage(mContext, imageView, item.getUrl());
        }
        holder.setText(R.id.image_text, item.getName());
        TextView sendText = holder.getView(R.id.image_second_text);
        sendText.setVisibility(View.VISIBLE);
        sendText.setTextColor(Color.RED);
        //CommonUtils.getString(mContext, R.string.goods_price,
        //                CommonUtils.getDouble(item.getPrice()))
        sendText.setText(String.format(mContext.getString(R.string.value_price),item.getPrice()));
    }
}
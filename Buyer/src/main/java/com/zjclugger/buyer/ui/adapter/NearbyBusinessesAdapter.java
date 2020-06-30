package com.zjclugger.buyer.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.GoodsResult;
import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.utils.CommonUtils;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 附近商家<br>
 * Created by King.Zi on 2020/4/10.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class NearbyBusinessesAdapter extends JBaseQuickAdapter<GoodsResult, JBaseViewHolder> {

    public NearbyBusinessesAdapter(int layoutResId, List<GoodsResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, GoodsResult item) {
        final ImageView imageView = holder.getView(R.id.image_left);
        if (!TextUtils.isEmpty(item.getUrl())) {
            ImageUtils.loadImage(mContext, imageView, item.getUrl());
        }

        holder.setText(R.id.text_right, CommonUtils.getString(mContext,
                R.string.start_price, item.getFloorPrice()));
        holder.setText(R.id.goods_name, item.getName());
        holder.setText(R.id.goods_business_name, item.getBusinessName());
        MaterialRatingBar ratingBar = holder.getView(R.id.goods_score);
        ratingBar.setRating(item.getScore());
    }
}
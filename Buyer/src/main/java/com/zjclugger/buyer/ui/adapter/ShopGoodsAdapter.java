package com.zjclugger.buyer.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.GoodsResult;
import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.utils.CommonUtils;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

/**
 * 商铺商品<br>
 * Created by King.Zi on 2020/4/22.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ShopGoodsAdapter extends JBaseQuickAdapter<GoodsResult, JBaseViewHolder> {

    public ShopGoodsAdapter(int layoutResId, List<GoodsResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, GoodsResult item) {
        final ImageView imageView = holder.getView(R.id.image_left);
        if (!TextUtils.isEmpty(item.getUrl())) {
            ImageUtils.loadImage(mContext, imageView, item.getUrl());
        }

        holder.setExtendLabelText(R.id.shop_goods_name, item.getName());
        holder.setExtendLabelViewText(R.id.shop_goods_des_month_sale, item.getDescription(), "",
                CommonUtils.getString(mContext, R.string.month_volume, item.getMonthSalesVolume()));
        holder.setText(R.id.shop_goods_price, CommonUtils.getString(mContext,
                R.string.goods_price, item.getPrice()));
        holder.getView(R.id.shop_goods_subtraction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetUtils.toast(mContext, "sub", false);
            }
        });

        holder.getView(R.id.shop_goods_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetUtils.toast(mContext, "add", false);
            }
        });
       /*
        MaterialRatingBar ratingBar = holder.getView(R.id.goods_score);
        ratingBar.setRating(item.getScore());*/
    }
}
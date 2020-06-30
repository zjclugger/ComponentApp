package com.zjclugger.seller.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;
import com.zjclugger.seller.R;
import com.zjclugger.seller.webapi.response.GoodsResult;

import java.util.List;

/**
 * 商品<br>
 * Created by King.Zi on 2020/4/18.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class GoodsAdapter extends JBaseQuickAdapter<GoodsResult, JBaseViewHolder> {
    public GoodsAdapter(int layoutResId, List<GoodsResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, GoodsResult item) {
        ImageView goodsImage = holder.getView(R.id.goods_image);
        ImageUtils.loadImage(mContext, goodsImage, item.getGoodsImageUrl().get(0));
        holder.setExtendLabelText(R.id.goods_name, item.getName());
        holder.setExtendLabelText(R.id.goods_price, String.valueOf(item.getPrice()));
        holder.setExtendValueText(R.id.goods_stock, String.valueOf(item.getStock()));
        holder.setExtendValueText(R.id.goods_sales_volume, String.valueOf(item.getSalesVolume()));
    }
}

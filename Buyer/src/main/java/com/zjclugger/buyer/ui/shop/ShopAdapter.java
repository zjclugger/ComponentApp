package com.zjclugger.buyer.ui.shop;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.ShopResult;
import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.utils.CommonUtils;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 商品<br>
 * Created by King.Zi on 2020/4/18.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ShopAdapter extends JBaseQuickAdapter<ShopResult, JBaseViewHolder> {
    public ShopAdapter(int layoutResId, List<ShopResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, ShopResult item) {
        ImageView imageView = holder.getView(R.id.image_view);
        ImageUtils.loadImage(mContext, imageView, item.getImageUrl().get(0));

        ExtendLabelValueView foodShopName = holder.getView(R.id.food_shop_name);
        foodShopName.setText(item.getName(), item.getCategoryName(), item.getStatus());
        foodShopName.getMiddleView().setPadding(ViewUtils.dp2px(mContext, 20), 0, 0, 0);

        MaterialRatingBar ratingBar = holder.getView(R.id.food_shop_score);
        ratingBar.setRating(item.getScore());

        ExtendLabelValueView priceAddressView = holder.getView(R.id.food_shop_price_address);
        priceAddressView.setText(CommonUtils.getString(mContext, R.string.start_price,
                item.getFloorPrice()), item.getAddress(), CommonUtils.getString(mContext,
                R.string.month_volume, item.getMonthSalesVolume()));
        priceAddressView.getMiddleView().setPadding(ViewUtils.dp2px(mContext, 20), 0, 0, 0);
    }
}

package com.zjclugger.seller.ui.order;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;
import com.zjclugger.seller.R;
import com.zjclugger.seller.webapi.response.InvoiceResult;
import com.zjclugger.seller.webapi.response.OrderGoodsResult;

import java.util.List;

/**
 * 适配器 <br>
 * Created by King.Zi on 2020/5/19.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class InvoiceListAdapter extends JBaseQuickAdapter<InvoiceResult, JBaseViewHolder> {

    public InvoiceListAdapter(int layoutResId, @Nullable List<InvoiceResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, InvoiceResult item) {
        if (item.getOrderInfo().getGoodsList() != null) {
            OrderGoodsResult orderGoodsResult = item.getOrderInfo().getGoodsList().get(0);
            if (orderGoodsResult.getGoods() != null) {
                holder.setImageView(R.id.goods_image, mContext,
                        orderGoodsResult.getGoods().getGoodsImageUrl().get(0));
                holder.setText(R.id.goods_name, orderGoodsResult.getGoods().getName());

                ExtendLabelValueView goodsPrice = holder.getView(R.id.elvv_goods_price);
                goodsPrice.setLabelText(orderGoodsResult.getTotalAmount().toString());
                goodsPrice.setValueText(orderGoodsResult.getTransactionPrice() + " x" + orderGoodsResult.getCount());
            }
        }
        holder.setValueText(R.id.lvv_address_delivery, item.getShopInfo().getAddress());
        holder.setValueText(R.id.lvv_address_receive, item.getOrderInfo().getCustomerAddress());

        holder.setValueText(R.id.lvv_deliverer, item.getShopInfo().getName());
        holder.setValueText(R.id.lvv_receiver, item.getOrderInfo().getCustomerName());
    }
}

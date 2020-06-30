package com.zjclugger.seller.ui.order;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;
import com.zjclugger.router.utils.ARouterUtils;
import com.zjclugger.seller.R;
import com.zjclugger.seller.webapi.response.OrderResult;

import java.util.List;

/**
 * 适配器 <br>
 * Created by King.Zi on 2020/5/18.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OrderListAdapter extends JBaseQuickAdapter<OrderResult, JBaseViewHolder> {

    Activity mActivity;

    public OrderListAdapter(Activity activity, int layoutResId, @Nullable List<OrderResult> data) {
        super(layoutResId, data);
        mActivity = activity;
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, OrderResult item) {
        ExtendLabelValueView orderNOView = holder.getView(R.id.elvv_order_status);
        orderNOView.getMiddleView().setText(item.getOrderNO());
        if (R.string.text_to_handle == item.getStatus()) {
            orderNOView.setValueText("用户已支付");
            holder.getView(R.id.function_layout).setVisibility(View.VISIBLE);
            holder.getView(R.id.order_receive).setOnClickListener(v -> {
                WidgetUtils.toast(mContext, "开始接单啰~！", false);
            });
            holder.getView(R.id.order_delete).setOnClickListener(v -> {
                WidgetUtils.toast(mContext, "删除订单", false);
            });
        } else {
            if (R.string.text_delivered == item.getStatus()) {
                orderNOView.getValueView().setOnClickListener(v -> {
                    ARouterUtils.openDetailFragment(mActivity,
                            InvoiceListFragment.class.getName(), null, R.color.bg_white, false
                    );
                });
            }
            holder.getView(R.id.function_layout).setVisibility(View.GONE);
            orderNOView.setValueText(mContext.getString(item.getStatus()));
        }
        holder.setExtendLabelViewText(R.id.elvv_customer_name, item.getCustomerName(),
                item.getCustomerPhone());
        holder.setText(R.id.customer_address, item.getCustomerAddress());
        holder.getView(R.id.iv_customer_detail).setOnClickListener(v -> {
            WidgetUtils.toast(mContext, "查看用户详情", false);
        });

        ExtendLabelValueView titleView = holder.getView(R.id.elvv_goods_title);
        LinearLayout detailLayout = holder.getView(R.id.order_detail_layout);
        titleView.getValueView().setOnClickListener(v -> {
            detailLayout.setVisibility(detailLayout.getVisibility() == View.VISIBLE ? View.GONE :
                    View.VISIBLE);
        });

        //todo:UI设计可能有问题，这个商品应该是个列表，目前先这样
       /* holder.setExtendLabelViewText(R.id.elvv_goods_name, item.getGoodsList().get(0).getName(),
                item.getGoodsList().get(0).getPrice() + "x3");*/
    }
}

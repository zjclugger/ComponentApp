package com.zjclugger.seller.ui.goods;

import android.widget.CheckBox;

import androidx.annotation.NonNull;

import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;
import com.zjclugger.seller.R;
import com.zjclugger.seller.webapi.response.GoodsResult;

import java.util.List;

/**
 * 商品批量管理适配器<br>
 * Created by King.Zi on 2020/5/20.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class GoodsBatchManageAdapter extends JBaseQuickAdapter<GoodsResult, JBaseViewHolder> {
    private boolean mIsBinding;

    public GoodsBatchManageAdapter(List<GoodsResult> data) {
        super(R.layout.item_goods_batch_manage, data);
    }

    @Override
    protected void convert(@NonNull final JBaseViewHolder holder, final GoodsResult item) {
        holder.setImageView(R.id.goods_image, mContext, item.getGoodsImageUrl().get(0));
        holder.setExtendLabelText(R.id.goods_name, item.getName());
        holder.setExtendLabelText(R.id.goods_price, String.valueOf(item.getPrice()));
        holder.setExtendValueText(R.id.goods_sales_volume, String.valueOf(item.getSalesVolume()));
        holder.setExtendValueText(R.id.goods_stock, String.valueOf(item.getStock()));

        CheckBox selectView = holder.getView(R.id.goods_choice_view);
        mIsBinding = true;
        selectView.setChecked(item.isSelected());
        mIsBinding = false;
        selectView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!mIsBinding) {
               /* for (GoodsResult choiceItem : getData()) {
                    choiceItem.setSelected(false);
                }*/
                item.setSelected(isChecked);
                setOnItemClickListener(selectView, holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }
}
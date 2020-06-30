package com.zjclugger.seller.ui.goods;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;
import com.zjclugger.seller.R;
import com.zjclugger.seller.webapi.response.GoodsCategoryResult;

import java.util.List;

/**
 * 选择适配器<br>
 * Created by King.Zi on 2020/5/20.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class CategoryAdapter extends JBaseQuickAdapter<GoodsCategoryResult, JBaseViewHolder> {
    private boolean mIsMultiple;
    private boolean mIsClickedClose = true;    //点击选择后是否关闭窗口，不关闭则需要刷新数据集
    private boolean mIsBinding;

    public CategoryAdapter(List<GoodsCategoryResult> data, boolean isMultiple) {
        super(R.layout.item_goods_category_manage, data);
        mIsMultiple = isMultiple;
    }

    @Override
    protected void convert(@NonNull final JBaseViewHolder holder,
                           final GoodsCategoryResult item) {
        CheckBox selectView = holder.getView(R.id.choice_view);
        holder.setText(R.id.category_name, item.getName());
        holder.setText(R.id.category_count, String.valueOf(item.getGoodsCount()));

        if (!mIsMultiple) {
            mIsBinding = true;
            selectView.setChecked(item.isSelected());
            mIsBinding = false;
            selectView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!mIsBinding) {
                        for (GoodsCategoryResult choiceItem : getData()) {
                            choiceItem.setSelected(false);
                        }
                        item.setSelected(isChecked);
                        setOnItemClickListener(selectView, holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                }
            });
        } else {
            selectView.setChecked(item.isSelected());
            selectView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.setSelected(isChecked);
                    setOnItemClickListener(selectView, holder.getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public boolean isMultiple() {
        return mIsMultiple;
    }

    public void clickedCloseView(boolean close) {
        mIsClickedClose = close;
    }
}
package com.zjclugger.lib.ui.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.R;
import com.zjclugger.lib.entity.common.JChoiceItem;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

/**
 * 选择适配器<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class JChoiceAdapter extends BaseQuickAdapter<JChoiceItem, JBaseViewHolder> {
    private int mChoiceViewId;
    private boolean mIsMultiple;
    private boolean mIsClickedClose = true;    //点击选择后是否关闭窗口，不关闭则需要刷新数据集
    private boolean mIsBinding;

    public JChoiceAdapter(List<JChoiceItem> data) {
        super(R.layout.item_single_choice, data);
        mChoiceViewId = R.id.single_choice_view;
    }

    public JChoiceAdapter(List<JChoiceItem> data, boolean isMultiple) {
        super(isMultiple ? R.layout.item_multiple_choice : R.layout.item_single_choice, data);
        mIsMultiple = isMultiple;
        mChoiceViewId = isMultiple ? R.id.multiple_choice_view : R.id.single_choice_view;
    }

    @Override
    protected void convert(@NonNull final JBaseViewHolder holder, final JChoiceItem item) {
        CheckBox selectView = holder.getView(mChoiceViewId);
        selectView.setText(item.getText());

        if (!mIsClickedClose) {
            mIsBinding = true;
            selectView.setChecked(item.isSelected());
            mIsBinding = false;
            selectView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!mIsBinding) {
                        for (JChoiceItem choiceItem : getData()) {
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
                }
            });
        }
    }

    private void setOnItemClickListener(View view, int position) {
        if (getOnItemClickListener() != null) {
            getOnItemClickListener().onItemClick(this, view, position);
        }
    }

    public boolean isMultiple() {
        return mIsMultiple;
    }

    public void clickedCloseView(boolean close) {
        mIsClickedClose = close;
    }
}
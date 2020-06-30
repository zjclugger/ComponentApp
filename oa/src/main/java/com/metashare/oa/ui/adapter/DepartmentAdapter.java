package com.zjclugger.oa.ui.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;
import com.zjclugger.oa.R;

import java.util.List;

/**
 * 部门适配器<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class DepartmentAdapter extends BaseQuickAdapter<Department, ERPBaseViewHolder> {

    private boolean mIsSelected;
    private boolean mIsChooseMode;

    public DepartmentAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    public DepartmentAdapter(int layoutResId, List data, boolean isChooseMode) {
        this(layoutResId, data);
        mIsChooseMode = isChooseMode;
    }

    @Override
    protected void convert(@NonNull final ERPBaseViewHolder holder, final Department item) {
        final CheckBox selectView = holder.getView(R.id.chk_dept_name);
        if (mIsChooseMode) {
            selectView.setChecked(mIsSelected);
            selectView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    selectView.setTag(isChecked);
                    setOnItemClickListener(selectView, holder.getAdapterPosition());
                }
            });
        } else {
            selectView.setVisibility(View.GONE);
        }

        //TODO:递归获取人数，或在接口中直接给出人数
        final TextView departNameView = holder.getView(R.id.dept_name_view);
        departNameView.setText(item.getName() + "(" + item.getStaffTotal() + ")");
        departNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsChooseMode) {
                    selectView.setChecked(!selectView.isChecked());
                }
                setOnItemClickListener(departNameView, holder.getAdapterPosition());
            }
        });

        final ImageView moreView = holder.getView(R.id.iv_contacts_more);
        moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener(moreView, holder.getAdapterPosition());
            }
        });
    }

    public void setSelected(boolean isSelected) {
        mIsSelected = isSelected;
        notifyDataSetChanged();
    }

    private void setOnItemClickListener(View view, int position) {
        if (getOnItemClickListener() != null) {
            getOnItemClickListener().onItemClick(DepartmentAdapter.this, view, position);
        }
    }
}
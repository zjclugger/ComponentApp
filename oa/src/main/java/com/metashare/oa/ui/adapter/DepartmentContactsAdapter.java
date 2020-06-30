package com.zjclugger.oa.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.view.CircleImageTextView;
import com.zjclugger.lib.view.LabelValueView;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;
import com.zjclugger.oa.R;
import com.zjclugger.oa.utils.OAUtils;

import java.util.List;

/**
 * 部门通讯录适配器<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class DepartmentContactsAdapter extends BaseQuickAdapter<Staff, ERPBaseViewHolder> {

    private boolean mIsSelected;
    private String mStaffPosition;
    private boolean mIsChooseMode;

    public DepartmentContactsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    public DepartmentContactsAdapter(int layoutResId, List data, boolean isChooseMode) {
        this(layoutResId, data);
        mIsChooseMode = isChooseMode;
    }

    @Override
    protected void convert(@NonNull ERPBaseViewHolder holder, Staff item) {
        final CheckBox selectView = holder.getView(R.id.chk_contacts_staff);
        final int position = holder.getAdapterPosition();
        if (mIsChooseMode) {
            selectView.setChecked(mIsSelected);
            selectView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    selectView.setTag(isChecked);
                    setOnItemClickListener(selectView, position);
                }
            });
        } else {
            selectView.setVisibility(View.GONE);
        }

        //TODO：如果有头像则显示头像，没有显示姓名
        final CircleImageTextView staffPhoto = holder.getView(R.id.citv_staff_photo);
        OAUtils.setPhotoView(staffPhoto,null,item.getName(),4);
        setItemChildClickListener(staffPhoto, position, selectView);

        //文本
        final LabelValueView staffView = holder.getView(R.id.lvv_staff);
        staffView.setLabelText(item.getName());
        //TODO：职位
        mStaffPosition = item.getPost() == null ? "" : item.getPost().getPostName();
        if (TextUtils.isEmpty(mStaffPosition)) {
            staffView.setValueTextVisibility(View.GONE);
        } else {
            staffView.setValueText(mStaffPosition);
        }
        setItemChildClickListener(staffView, position, selectView);
    }

    private void setItemChildClickListener(final View childView, final int position,
                                           final CheckBox selectView) {
        childView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectView.setChecked(!selectView.isChecked());
                setOnItemClickListener(childView, position);
            }
        });
    }

    public void setSelected(boolean isSelected) {
        mIsSelected = isSelected;
        notifyDataSetChanged();
    }

    private void setOnItemClickListener(View view, int position) {
        if (getOnItemClickListener() != null) {
            getOnItemClickListener().onItemClick(DepartmentContactsAdapter.this, view, position);
        }
    }
}
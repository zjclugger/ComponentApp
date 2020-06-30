package com.zjclugger.oa.ui.adapter;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;
import com.zjclugger.oa.R;
import com.zjclugger.oa.webapi.entity.response.AttendanceGroupResult;

import java.util.List;

/**
 * 考勤组<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceGroupListAdapter extends BaseQuickAdapter<AttendanceGroupResult,
        ERPBaseViewHolder> {

    public AttendanceGroupListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull final ERPBaseViewHolder holder,
                           final AttendanceGroupResult item) {
        final ExtendLabelValueView deleteView = holder.getView(R.id.elvv_attendance_group_name);
        deleteView.setLabelText(item.getName());
        deleteView.getRightImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener(deleteView, holder.getAdapterPosition());
            }
        });

        holder.setValueText(R.id.lvv_group_member, String.valueOf(item.getMemberCount()));
        holder.setValueText(R.id.lvv_shifts, item.getShiftsName());
        holder.setValueText(R.id.lvv_place, item.getShiftsPlace());
        final Button ruleSetting = holder.getView(R.id.btn_rule_setting);
        ruleSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener(ruleSetting, holder.getAdapterPosition());
            }
        });

        final Button memberSetting = holder.getView(R.id.btn_member_setting);
        memberSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener(memberSetting, holder.getAdapterPosition());
            }
        });
    }

    private void setOnItemClickListener(View view, int position) {
        if (getOnItemClickListener() != null) {
            getOnItemClickListener().onItemClick(this, view, position);
        }
    }
}

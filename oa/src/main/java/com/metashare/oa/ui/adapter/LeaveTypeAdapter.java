package com.zjclugger.oa.ui.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;
import com.zjclugger.oa.R;
import com.zjclugger.oa.webapi.entity.response.LeaveTypeResult;

import java.util.List;

/**
 * 请假类型<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LeaveTypeAdapter extends BaseQuickAdapter<LeaveTypeResult, ERPBaseViewHolder> {

    public LeaveTypeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull ERPBaseViewHolder holder, LeaveTypeResult item) {
        TextView textView = holder.getView(R.id.leave_type);
        textView.setText(item.getLeaveName());
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(item.getLeftImage(), null, null,
                null);

        if (item.getLeaveRemain() > -1) {
            holder.setText(R.id.leave_type_remain, "剩余" + item.getLeaveBalance());
        } else {
            holder.setGone(R.id.leave_type_remain, false);
        }

        if (item.getGrantType() > 0) {
            holder.setText(R.id.leave_type_grant, item.getGrantType());
        }
    }
}

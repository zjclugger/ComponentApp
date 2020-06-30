package com.zjclugger.oa.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.view.CircleImageTextView;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;
import com.zjclugger.oa.R;
import com.zjclugger.oa.utils.OAUtils;
import com.zjclugger.oa.webapi.entity.response.LeaveHistoryResult;

import java.util.List;

/**
 * 请假类型<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LeaveHistoryAdapter extends BaseQuickAdapter<LeaveHistoryResult, ERPBaseViewHolder> {

    public LeaveHistoryAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull ERPBaseViewHolder holder, LeaveHistoryResult item) {
        CircleImageTextView photo = holder.getView(R.id.citv_request_photo);
        //TODO:如果有头像用头像，无则使用名称
        OAUtils.setPhotoView(photo, null, item.getLeaveRequestName(), 4);

        holder.setText(R.id.leave_history_desc, item.getLeaveRequestName());
        holder.setText(R.id.tv_request_date, item.getLeaveRequestDate());
        holder.setValueText(R.id.llv_request_type, item.getLeaveName());
        holder.setValueText(R.id.llv_leave_start, item.getStartDateTime());
        holder.setValueText(R.id.llv_leave_end, item.getEndDateTime());
        holder.setText(R.id.llv_request_result, item.getLeaveResult());
    }
}

package com.zjclugger.oa.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;
import com.zjclugger.oa.R;
import com.zjclugger.oa.webapi.entity.response.AttendancePlaceResult;

import java.util.List;

/**
 * 考勤地点<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendancePlaceListAdapter extends BaseQuickAdapter<AttendancePlaceResult,
        ERPBaseViewHolder> {

    public AttendancePlaceListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull final ERPBaseViewHolder holder,
                           final AttendancePlaceResult item) {
        final ImageView deleteView = holder.getView(R.id.right_view);
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener(deleteView, holder.getAdapterPosition());
            }
        });
        holder.setLabelValueText(R.id.lvv_list_place, item.getName(), item.getPlace());
    }

    private void setOnItemClickListener(View view, int position) {
        if (getOnItemClickListener() != null) {
            getOnItemClickListener().onItemClick(this, view, position);
        }
    }
}

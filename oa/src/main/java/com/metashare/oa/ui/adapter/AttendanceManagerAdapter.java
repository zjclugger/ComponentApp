package com.zjclugger.oa.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.zjclugger.basiclib.Log;
import com.zjclugger.lib.view.CircleImageTextView;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;
import com.zjclugger.oa.R;
import com.zjclugger.oa.utils.OaConstants;

import java.util.List;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceManagerAdapter extends BaseSectionQuickAdapter<SectionAttendance,
        ERPBaseViewHolder> {

    public AttendanceManagerAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(ERPBaseViewHolder helper, SectionAttendance item) {
        helper.setText(R.id.header_text, item.header);
        helper.setVisible(R.id.iv_header_more, item.isMore());
        helper.addOnClickListener(R.id.iv_header_more);
    }

    @Override
    protected void convert(@NonNull ERPBaseViewHolder holder, SectionAttendance item) {
        Log.d("KING", "-------------itema=" + item.t);
        CircleImageTextView citView = holder.getView(R.id.itv_attendance);
        if(item.t.getType() == OaConstants.AttendanceType.STATISTICS ){
            //TODO:打卡统计数
            citView.setCircleImageCenterText("12");
        }else {
            citView.setImage(mContext.getResources().getDrawable(item.t.getItemIcon()));
        }
        citView.getTextView().setText(item.t.getItemText());
    }
}

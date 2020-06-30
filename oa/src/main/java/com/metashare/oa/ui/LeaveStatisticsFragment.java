package com.zjclugger.oa.ui;

import com.zjclugger.oa.R;
import com.zjclugger.oa.webapi.entity.response.PayrollDetailResult;

/**
 * 请假统计<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LeaveStatisticsFragment extends OABaseFragment {
    private final static String TAG = "leaveType";

    @Override
    public int getLayout() {
        return R.layout.fragment_leave_request;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("请假统计");
    }

    private void bindData(PayrollDetailResult payrollDetailResult) {

    }

    @Override
    public Boolean getPostBackData() {
        return false;
    }
}
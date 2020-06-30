package com.zjclugger.oa.ui;

import com.zjclugger.oa.R;

/**
 * 请假销假管理<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LeaveRequestFragment extends OABaseFragment {
    private final static String TAG = "leaveType";

    @Override
    public void initViews() {
        initDetailTitleViews("请假");
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_leave_request;
    }

    @Override
    public <T> T getPostBackData() {
        return null;
    }
}
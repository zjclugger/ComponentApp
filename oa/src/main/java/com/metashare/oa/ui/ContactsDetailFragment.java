package com.zjclugger.oa.ui;

import com.zjclugger.oa.R;
import com.zjclugger.oa.ui.adapter.Staff;

/**
 * 考勤统计<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ContactsDetailFragment extends OABaseFragment {
    private final static String TAG = "StatisticsReport";
    private OAViewModel mViewModel;
    private String mPageTitle;
    /*@BindView(R2.id.report_start_time)
    LabelValueView mDateStartView;
    @BindView(R2.id.report_end_time)
    LabelValueView mDateEndView;*/
    private String mDateTimeFormat = "yyyy-MM-dd";
    private Staff mStaff;


    @Override
    public int getLayout() {
        return R.layout.fragment_contacts_detail;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("统计-报表");
    }

    @Override
    public Boolean getPostBackData() {
        return false;
    }
}
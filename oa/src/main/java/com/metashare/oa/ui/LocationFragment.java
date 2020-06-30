package com.zjclugger.oa.ui;

import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.jview.DateTimePickerView;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.utils.DateTimeFormat;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.lib.view.DialProgressView;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;

import java.util.Date;

import butterknife.BindView;

/**
 * 定位<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LocationFragment extends OABaseFragment {
    private final static String TAG = "attendance";
    @BindView(R2.id.llv_attendance_date)
    ExtendLabelValueView mAttendanceDataView;
    @BindView(R2.id.personal_report_view)
    TextView mPersonalReportView;
    @BindView(R2.id.attendance_dial_progress)
    DialProgressView mStatisticsView;
    DateTimePickerView mStartDatePickerDialog;
    private String mDateTimeFormat = "yyyy-MM-dd";
    private AttendanceStatisticsReportFragment mReportFragment;

    @Override
    public int getLayout() {
        return R.layout.fragment_attendance_statistics_personal;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("统计-管理", "个人", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

        mAttendanceDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartDatePickerDialog == null) {
                    mStartDatePickerDialog = new DateTimePickerView(getActivity(),
                            new OnTimeSelectListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {
                                    mAttendanceDataView.setValueText(ERPUtils.getDateTime(date,
                                            mDateTimeFormat));
                                }
                            }, DateTimeFormat.YMD);
                }
                mStartDatePickerDialog.show();
            }
        });

        mPersonalReportView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReportFragment == null) {
                    mReportFragment = new AttendanceStatisticsReportFragment();
                }
                FragmentUtils.addFragment(getActivity(), mReportFragment, R.id.content_fragment);
            }
        });
    }

    @Override
    public Boolean getPostBackData() {
        return false;
    }
}
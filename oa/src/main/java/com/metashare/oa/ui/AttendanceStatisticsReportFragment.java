package com.zjclugger.oa.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.bigkoo.pickerview.jview.DateTimePickerView;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.utils.DateTimeFormat;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.view.LabelValueView;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;

import java.util.Date;

import butterknife.BindView;

/**
 * 考勤统计<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceStatisticsReportFragment extends OABaseFragment {
    private final static String TAG = "StatisticsReport";
    private OAViewModel mViewModel;
    private String mPageTitle;
    @BindView(R2.id.report_start_time)
    LabelValueView mDateStartView;
    @BindView(R2.id.report_end_time)
    LabelValueView mDateEndView;
    DateTimePickerView mStartDatePickerDialog;
    DateTimePickerView mEndDatePickerDialog;
    private String mDateTimeFormat = "yyyy-MM-dd";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(OAViewModel.class);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_attendance_statistics_report;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("统计-报表");
        mDateStartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartDatePickerDialog == null) {
                    mStartDatePickerDialog = new DateTimePickerView(getActivity(),
                            new OnTimeSelectListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {
                                    mDateStartView.setValueText(ERPUtils.getDateTime(date,
                                            mDateTimeFormat));
                                }
                            }, DateTimeFormat.YMD);
                }
                mStartDatePickerDialog.show();
            }
        });

        mDateEndView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEndDatePickerDialog == null) {
                    mEndDatePickerDialog = new DateTimePickerView(getActivity(),
                            new OnTimeSelectListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {
                                    mDateEndView.setValueText(ERPUtils.getDateTime(date,
                                            mDateTimeFormat));
                                }
                            }, DateTimeFormat.YMD);
                }
                mEndDatePickerDialog.show();
            }
        });
    }

    @Override
    public Boolean getPostBackData() {
        return false;
    }
}
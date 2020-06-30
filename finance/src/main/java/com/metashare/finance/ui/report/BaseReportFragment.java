package com.zjclugger.finance.ui.report;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.bigkoo.pickerview.jview.DateTimePickerView;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.utils.DateTimeFormat;
import com.zjclugger.finance.R;
import com.zjclugger.finance.ui.FinanceViewModel;
import com.zjclugger.lib.base.BaseFragment;

import java.util.Calendar;
import java.util.Date;

/**
 * 报表基类 <br>
 * Created by King.Zi on 2020/1/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public abstract class BaseReportFragment extends BaseFragment {
    protected static final String VALUE_DATE_FORMAT = "yyyy-MM";
    protected DateTimePickerView mDatePickerView;
    protected String mEndDate;
    protected FinanceViewModel mViewModel;

    public abstract OnTimeSelectListener getOnTimeSelectListener();

    public abstract void getDataFromServer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(FinanceViewModel.class);
    }

    protected String getDateFormat(){
        return getString(R.string.year_month);
    }

    @Override
    public void initViews() {
        mEndDate = DateTimeFormat.parseDateTime(new Date(), VALUE_DATE_FORMAT);
        mDatePickerView = new DateTimePickerView(getActivity(), getOnTimeSelectListener(),
                DateTimeFormat.YM);
        Calendar startDate = Calendar.getInstance();
        startDate.set(2016, 0, 1);
        mDatePickerView.setRangDate(startDate, DateTimeFormat.dataToCalendar(new Date()));
        mDatePickerView.getBuilder().setDividerColor(mContext.getResources().getColor(R.color.lighter_gray));
        mDatePickerView.displayClassicLayout(false, 200);
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}

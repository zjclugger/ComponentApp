package com.zjclugger.finance.ui;

import android.os.Bundle;
import android.view.View;

import com.bigkoo.pickerview.jview.DateTimePickerView;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.utils.DateTimeFormat;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.zjclugger.finance.R;
import com.zjclugger.finance.R2;
import com.zjclugger.finance.utils.FinanceConstants;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.view.ExtendLabelValueView;

import java.util.Date;

import butterknife.BindView;

/**
 * 筛选条件查询<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class SearchFilterFragment extends BaseFragment {
    private final static String TAG = "SearchFilter";
    @BindView(R2.id.elvv_filter_title)
    ExtendLabelValueView mFilterTitleView;
    @BindView(R2.id.elvv_start_date)
    ExtendLabelValueView mStartDateView;
    @BindView(R2.id.elvv_end_date)
    ExtendLabelValueView mEntryDateView;
    String mDateFormat = "yyyy-MM-dd";
    DateTimePickerView mStartDatePickerDialog;
    Date mStartDate;
    DateTimePickerView mEntryDatePickerDialog;
    Date mEntryDate;
    Bundle mCondition;

    @Override
    public int getLayout() {
        return R.layout.fragment_search_filter;
    }

    @Override
    public void initViews() {
        mCondition = new Bundle();
        initDetailTitleViews(getString(R.string.filter_condition),
                getString(R.string.text_confirm), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCondition != null) {
                    mCondition.clear();
                }

                if (mStartDate != null) {
                    mCondition.putString(FinanceConstants.Keywords.KEY_SEARCH_START_DATE,
                            ERPUtils.getDateTime(mStartDate, mDateFormat));
                }
                if (mEntryDate != null) {
                    mCondition.putString(FinanceConstants.Keywords.KEY_SEARCH_ENTRY_DATE,
                            ERPUtils.getDateTime(mEntryDate, mDateFormat));
                }
                if (mCondition != null) {
                    LiveEventBus.get(FinanceConstants.Keywords.KEY_SEARCH_CONDITION).post(mCondition);
                }
                close();
            }
        });

        mFilterTitleView.getValueView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartDate = null;
                mEntryDate = null;
                mStartDateView.setValueText("");
                mEntryDateView.setValueText("");
            }
        });

        mStartDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartDatePickerDialog == null) {
                    mStartDatePickerDialog = new DateTimePickerView(getActivity(),
                            new OnTimeSelectListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {
                                    mStartDate = date;
                                    mStartDateView.setValueText(ERPUtils.getDateTime(date,
                                            getString(R.string.year_month_day)));
                                }
                            }, DateTimeFormat.YMD);
                }
                mStartDatePickerDialog.show();
            }
        });

        mEntryDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEntryDatePickerDialog == null) {
                    mEntryDatePickerDialog = new DateTimePickerView(getActivity(),
                            new OnTimeSelectListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {
                                    mEntryDate = date;
                                    mEntryDateView.setValueText(ERPUtils.getDateTime(date,
                                            getString(R.string.year_month_day)));
                                }
                            });
                }
                mEntryDatePickerDialog.show();
            }
        });
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}
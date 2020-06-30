package com.zjclugger.oa.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.oa.R;

/**
 * 考勤设置<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceSettingsFragment extends OABaseFragment {
    private final static String TAG = "attendance";
    private OAViewModel mViewModel;
    private String mPageTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(OAViewModel.class);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_attendance_settings;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("考勤打卡", "设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttendanceGroupFragment groupFragment = new AttendanceGroupFragment();
                /*Bundle data = new Bundle();
                data.putParcelableArrayList(OaConstants.Keywords.KEY_APPROVE_LIST, mContactList);
                approveFragment.setArguments(data);*/
                FragmentUtils.addFragment(getActivity(), groupFragment, R.id.content_fragment);
            }
        });
    }

    private void initAttendanceShiftsView() {
        //班次
       /* mShiftsDialog = new ErpAlertDialog(getActivity());
        mShiftsList = new ArrayList<>();
        AttendanceShiftsResult shiftsResult = new AttendanceShiftsResult();
        shiftsResult.setId(3001);
        shiftsResult.setName("08:30-18:00");
        mShiftsList.add(shiftsResult);

        shiftsResult = new AttendanceShiftsResult();
        shiftsResult.setId(3002);
        shiftsResult.setName("09:00-18:00 午休90分钟");
        mShiftsList.add(shiftsResult);

        shiftsResult = new AttendanceShiftsResult();
        shiftsResult.setId(30031);
        shiftsResult.setName("09:00-18:00 午休1小时");
        mShiftsList.add(shiftsResult);

        shiftsResult = new AttendanceShiftsResult();
        shiftsResult.setId(3004);
        shiftsResult.setName("休息");
        mShiftsList.add(shiftsResult);

        mAttendanceShiftsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:打开选择框，并接收返回值
                mShiftsAdapter = new AttendanceShiftsListAdapter(R.layout.item_single_choice,
                        mShiftsList,
                        new Monitor.OnCloseListener<AttendanceShiftsResult>() {
                            @Override
                            public void onClose(AttendanceShiftsResult result) {
                                WidgetUtils.toastMessage(getActivity(),
                                        "您选择了" + result.getName());
                                //bindView();
                                //TODO:重新绑定数据，确认保存方式，是perf?
                                mAttendanceShiftsView.setValueText(result.getName());
                                mShiftsDialog.dismiss();
                            }
                        });

                mAttendanceShiftsView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mShiftsDialog.setData(null, false, mShiftsAdapter);
                    }
                });
            }
        });*/
    }

    @Override
    public Boolean getPostBackData() {
        return false;
    }
}
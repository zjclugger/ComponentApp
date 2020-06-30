package com.zjclugger.oa.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.zjclugger.lib.entity.common.ERPListItem;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;
import com.zjclugger.oa.ui.adapter.AttendanceStatisticsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 考勤统计<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceStatisticsFragment extends OABaseFragment {
    private final static String TAG = "attendance";
    private OAViewModel mViewModel;
    private String mPageTitle;
    @BindView(R2.id.elv_attendance_list)
    ExpandableListView mAttendanceListView;
    private List<ERPListItem<String>> mDataList = new ArrayList<>();
    @BindView(R2.id.rg_nav)
    RadioGroup mNavRadioGroup;
    AttendancePersonalStatisticsFragment mPersonalStatisticsFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(OAViewModel.class);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_attendance_statistics;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("考勤统计","管理", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPersonalStatisticsFragment == null) {
                    mPersonalStatisticsFragment = new AttendancePersonalStatisticsFragment();
                }
                FragmentUtils.addFragment(getActivity(), mPersonalStatisticsFragment,
                        R.id.content_fragment);
            }
        });

        mNavRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.rb_week == checkedId) {
                    WidgetUtils.toastMessage(getActivity(), "周");
                } else if (R.id.rb_month == checkedId) {
                    WidgetUtils.toastMessage(getActivity(), "月");
                }
            }
        });
        initData();
        bindListView();
    }

    private void initData() {
        ERPListItem itemHeader;
        itemHeader = new ERPListItem("平均工时", "9.8");
        itemHeader.getChildItemList().add(new ERPListItem("星期一", "9.8"));
        itemHeader.getChildItemList().add(new ERPListItem("星期二", "9.8"));
        itemHeader.getChildItemList().add(new ERPListItem("星期三", "9.8"));
        itemHeader.getChildItemList().add(new ERPListItem("星期四", "9.8"));
        itemHeader.getChildItemList().add(new ERPListItem("星期五", "9.8"));
        mDataList.add(itemHeader);

        itemHeader = new ERPListItem("出勤天数", "5");
        itemHeader.getChildItemList().add(new ERPListItem("星期一", "1天"));
        itemHeader.getChildItemList().add(new ERPListItem("星期二", "1天"));
        itemHeader.getChildItemList().add(new ERPListItem("星期三", "1天"));
        itemHeader.getChildItemList().add(new ERPListItem("星期四", "1天"));
        itemHeader.getChildItemList().add(new ERPListItem("星期五", "1天"));
        mDataList.add(itemHeader);

        itemHeader = new ERPListItem("出勤班次", "5");
        itemHeader.getChildItemList().add(new ERPListItem("班次：8：00-18：00", "4"));
        itemHeader.getChildItemList().add(new ERPListItem("班次：9：00-18：00", "1"));
        mDataList.add(itemHeader);
    }

    private void bindListView() {
        mAttendanceListView.setAdapter(new AttendanceStatisticsAdapter(mDataList));
        //设置分组的监听
        mAttendanceListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                                        long id) {
                WidgetUtils.toastMessage(getActivity(), mDataList.get(groupPosition).getValue());
                return false;
            }
        });
        //设置子项布局监听
        mAttendanceListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                WidgetUtils.toastMessage(getActivity(),
                        mDataList.get(groupPosition).getChildItemList().get(childPosition).getValue());
                return true;

            }
        });
        //控制他只能打开一个组
        mAttendanceListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = mDataList.size();
                for (int i = 0; i < count; i++) {
                    if (i != groupPosition) {
                        mAttendanceListView.collapseGroup(i);
                    }
                }
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
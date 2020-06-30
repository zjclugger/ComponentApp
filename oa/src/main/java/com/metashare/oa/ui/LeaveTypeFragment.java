package com.zjclugger.oa.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;
import com.zjclugger.oa.ui.adapter.LeaveHistoryAdapter;
import com.zjclugger.oa.ui.adapter.LeaveTypeAdapter;
import com.zjclugger.oa.utils.OaConstants;
import com.zjclugger.oa.webapi.entity.response.LeaveHistoryResult;
import com.zjclugger.oa.webapi.entity.response.LeaveTypeResult;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 假期类型<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LeaveTypeFragment extends OABaseFragment {
    private final static String TAG = "leaveMain";
    private String mPageTitle;
    private ArrayList<LeaveTypeResult> mLeaveTypeList = new ArrayList<>();
    private ArrayList<LeaveHistoryResult> mLeaveHistoryList = new ArrayList<>();

    @BindView(R2.id.leave_type_view)
    RecyclerView mTypeRecyclerView;
    LeaveTypeAdapter mTypeAdapter;
    @BindView(R2.id.leave_records_view)
    RecyclerView mHistoryRecyclerView;
    LeaveHistoryAdapter mHistoryAdapter;
    @BindView(R2.id.oa_leave_navigation)
    BottomNavigationView mNavigationView;
    @BindView(R2.id.leave_request_layout)
    RelativeLayout mLeaveRequestLayout;
    @BindView(R2.id.leave_records_layout)
    RelativeLayout mLeaveRecordsLayout;

    @Override
    public int getLayout() {
        return R.layout.fragment_leave_type;
    }

    @Override
    public void initViews() {
      /*  Bundle params = getArguments();
        int recordId = params.getInt(Constants.Keywords.KEY_PERSONAL_PAYROLL_ID, 0);
        if (recordId > 0) {
            showWaiting();
            mViewModel.getPersonalPayrollRecordDetail(String.valueOf(recordId)).observe(this,
                    new Observer<ApiResponse<BaseWrapperEntity<PayrollDetailResult>>>() {
                        @Override
                        public void onChanged(ApiResponse<BaseWrapperEntity<PayrollDetailResult>>
                         baseWrapperEntityApiResponse) {
                            closeProgressDialog();
                            if (baseWrapperEntityApiResponse != null &&
                            baseWrapperEntityApiResponse.isSuccess()) {
                                BaseWrapperEntity<PayrollDetailResult> wrapperEntity =
                                        baseWrapperEntityApiResponse.body;
                                if (wrapperEntity != null) {
                                    bindData(wrapperEntity.getResult());
                                }
                            }
                        }
                    });
        } else {
            closeProgressDialog();
            Log.e(TAG, "payroll id is " + recordId);
            WidgetUtils.toastErrorMessage(getActivity(), "未查询到结果");
        }*/

        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (R.id.oa_leave_request == menuItem.getItemId()) {
                    showLeaveTypeData();
                    return true;
                } else if (R.id.oa_leave_records == menuItem.getItemId()) {
                    showLeaveRecords();
                    return true;
                } else {
                    Log.e(TAG, "you click item " + menuItem.getTitle());
                }
                initDetailTitleViews(mPageTitle);
                return false;
            }
        });

        mNavigationView.setSelectedItemId(R.id.oa_leave_request);
    }

    private void showLeaveTypeData() {
        mLeaveRequestLayout.setVisibility(View.VISIBLE);
        mLeaveRecordsLayout.setVisibility(View.GONE);
        mPageTitle = "请假";
        initData();
        bindData();
    }

    private void showLeaveRecords() {
        mLeaveRequestLayout.setVisibility(View.GONE);
        mLeaveRecordsLayout.setVisibility(View.VISIBLE);
        mPageTitle = "请假记录";
        initHistoryData();
        bindHistoryData();
        //TODO:show records
    }

    private void initData() {
        if (mLeaveTypeList != null) {
            mLeaveTypeList.clear();
        }
        LeaveTypeResult result = new LeaveTypeResult();
        result.setLeaveType(1);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_circle_orange_small));
        result.setLeaveName("年假");
        result.setGrantType(OaConstants.LeaveGrantType.MANUAL);//1：手动，0：自动
        result.setLeaveRemain(0.5d);
        result.setLeaveUnit(OaConstants.LeaveUnit.HOUR); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(2);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_circle_orange_small));
        result.setLeaveName("事假");
        result.setGrantType(OaConstants.LeaveGrantType.HOUR);//1：手动，0：自动，-1：无
        result.setLeaveRemain(-1); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.HOUR); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(3);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_circle_orange_small));
        result.setLeaveName("病假");
        result.setGrantType(OaConstants.LeaveGrantType.HOUR);//1：手动，0：自动，-1：无
        result.setLeaveRemain(-1); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.HOUR); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(4);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_circle_orange_small));
        result.setLeaveName("调休");
        result.setGrantType(OaConstants.LeaveGrantType.MANUAL);//1：手动，0：自动，-1：无
        result.setLeaveRemain(0); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.HOUR); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(5);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_circle_blue_small));
        result.setLeaveName("产假");
        result.setGrantType(OaConstants.LeaveGrantType.MANUAL);//1：手动，0：自动，-1：无
        result.setLeaveRemain(0); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.DAY); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(6);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_circle_blue_small));
        result.setLeaveName("陪产假");
        result.setGrantType(OaConstants.LeaveGrantType.MANUAL);//1：手动，0：自动，-1：无
        result.setLeaveRemain(0); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.DAY); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(7);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_circle_blue_small));
        result.setLeaveName("婚假");
        result.setGrantType(OaConstants.LeaveGrantType.DAY);//1：手动，0：自动，-1：无
        result.setLeaveRemain(-1); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.DAY); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(8);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_circle_blue_small));
        result.setLeaveName("丧假");
        result.setGrantType(OaConstants.LeaveGrantType.DAY);//1：手动，0：自动，-1：无
        result.setLeaveRemain(-1); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.DAY); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(9);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_circle_orange_small));
        result.setLeaveName("产检假");
        result.setGrantType(OaConstants.LeaveGrantType.MANUAL);//1：手动，0：自动，-1：无
        result.setLeaveRemain(0); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.HOUR); //0:小时，1：天
        mLeaveTypeList.add(result);
    }

    private void bindData() {
        mTypeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTypeAdapter = new LeaveTypeAdapter(R.layout.item_leave_type, mLeaveTypeList);
        mTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //TODO:打开请假填写界面
                LeaveRequestFragment requestFragment = new LeaveRequestFragment();
                //set data
                Bundle data = new Bundle();
                data.putParcelable(OaConstants.Keywords.KEY_LEAVE_TYPE,
                        mLeaveTypeList.get(position));
                data.putParcelableArrayList(OaConstants.Keywords.KEY_LEAVE_TYPE_LIST,
                        mLeaveTypeList);
                requestFragment.setArguments(data);
                FragmentUtils.addFragment(getActivity(), requestFragment, R.id.content_fragment);
            }
        });
        mTypeRecyclerView.setAdapter(mTypeAdapter);
    }

    private void initHistoryData() {
        if (mLeaveHistoryList != null) {
            mLeaveHistoryList.clear();
        }
        LeaveHistoryResult historyResult = new LeaveHistoryResult();
        historyResult.setLeaveType(2);
        historyResult.setLeaveName("事假");
        historyResult.setLeaveRequestId(100);
        historyResult.setLeaveRequestName("张三丰");
        historyResult.setStartDateTime("2020-09-06 09:00");
        historyResult.setEndDateTime("2020-09-06 18:00");
        historyResult.setLeaveRequestDate("2020-09-05");
        historyResult.setLeaveResult("审核通过");
        mLeaveHistoryList.add(historyResult);

        historyResult = new LeaveHistoryResult();
        historyResult.setLeaveType(2);
        historyResult.setLeaveName("事假");
        historyResult.setLeaveRequestId(100);
        historyResult.setLeaveRequestName("张三丰");
        historyResult.setStartDateTime("2020-09-06 06:00");
        historyResult.setEndDateTime("2020-09-06 16:00");
        historyResult.setLeaveRequestDate("2020-09-05");
        historyResult.setLeaveResult("审核未通过");
        mLeaveHistoryList.add(historyResult);

        historyResult = new LeaveHistoryResult();
        historyResult.setLeaveType(2);
        historyResult.setLeaveName("事假");
        historyResult.setLeaveRequestId(100);
        historyResult.setLeaveRequestName("张三丰");
        historyResult.setStartDateTime("2020-09-04 08:30");
        historyResult.setEndDateTime("2020-09-04 18:00");
        historyResult.setLeaveRequestDate("2020-09-03");
        historyResult.setLeaveResult("审核通过");
        mLeaveHistoryList.add(historyResult);
    }

    private void bindHistoryData() {
        mHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHistoryAdapter = new LeaveHistoryAdapter(R.layout.item_leave_record, mLeaveHistoryList);
        mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LeaveHistoryDetailFragment requestFragment = new LeaveHistoryDetailFragment();
                //set data
                Bundle data = new Bundle();
                data.putParcelable(OaConstants.Keywords.KEY_LEAVE_HISTORY,
                        mLeaveHistoryList.get(position));
                requestFragment.setArguments(data);
                FragmentUtils.addFragment(getActivity(), requestFragment, R.id.content_fragment);
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL);
        //dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.bg_list_devider));
        mHistoryRecyclerView.addItemDecoration(dividerItemDecoration);
        mHistoryRecyclerView.setAdapter(mHistoryAdapter);
    }

    @Override
    public Boolean getPostBackData() {
        return true;
    }
}
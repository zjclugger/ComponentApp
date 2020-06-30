package com.zjclugger.oa.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.business.User;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.view.recyclerview.ERPGridLayoutManager;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;
import com.zjclugger.oa.ui.adapter.AttendanceManagerAdapter;
import com.zjclugger.oa.ui.adapter.ItemAttendance;
import com.zjclugger.oa.ui.adapter.SectionAttendance;
import com.zjclugger.oa.utils.OaConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 工作台<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class WorkbenchFragment extends BaseFragment {
    private final static String TAG = "Workbench";
    private User mCurrentUser;
    @BindView(R2.id.attendance_recycler)
    RecyclerView mAttendanceRecyclerView;
    AttendanceManagerAdapter mAttendanceAdapter;
    List<SectionAttendance> mAttendanceList = new ArrayList<>();
    @BindView(R2.id.tv_attendance_manager)
    TextView mAttendanceManagerView;

    @Override
    public void initViews() {
        initAttendanceItems();
        mAttendanceManagerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAttendanceRecyclerView.setVisibility(mAttendanceRecyclerView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_oa_workbench;
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mViewModel = ViewModelProviders.of(getActivity()).get(OAViewModel.class);
        mCurrentUser = UserManager.getInstance().getCurrentUser();
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * get data()
     */
    private void getData() {
       /* mPayrollRecordList = new ArrayList<>();
        mPayrollStatusValue = STATUS_DEFAULT;
        mQueryText = "";
        mIsLoaded = false;
        mLoadedSize = new AtomicInteger(0);
        getPayrollRecordList();
        getRecordList();
        getSettlementList();*/

    }

    private void initAttendanceItems() {
       /* ItemAttendance attendance = new ItemAttendance();
        attendance.setItemIcon(R.mipmap.ic_report);
        attendance.setItemText("考勤打卡");
        attendance.setFragment(new AttendanceFragment());
        mAttendanceList.add(attendance);

        attendance = new ItemAttendance();
        attendance.setItemIcon(R.mipmap.ic_report);
        attendance.setItemText("考勤打卡2");
        attendance.setFragment(new AttendanceFragment());
        mAttendanceList.add(attendance);*/

        // mAttendanceList.add(new SectionAttendance(true, "Section 1", true));

        mAttendanceList.add(new SectionAttendance(new ItemAttendance(R.mipmap.ic_location,
                R.string.oa_attendance_sign,
                OaConstants.AttendanceType.SIGN)));
        mAttendanceList.add(new SectionAttendance(new ItemAttendance(R.mipmap.ic_leave_request,
                R.string.oa_request_leave,
                OaConstants.AttendanceType.LEAVE)));
        mAttendanceList.add(new SectionAttendance(new ItemAttendance(R.mipmap.ic_leave_revoke,
                R.string.oa_cancel_leave,
                OaConstants.AttendanceType.LEAVE)));
        mAttendanceList.add(new SectionAttendance(new ItemAttendance(R.mipmap.ic_overtime,
                R.string.oa_overtime,
                OaConstants.AttendanceType.LEAVE)));
        mAttendanceList.add(new SectionAttendance(new ItemAttendance(R.mipmap.ic_work_out,
                R.string.oa_out,
                OaConstants.AttendanceType.LEAVE)));
        mAttendanceList.add(new SectionAttendance(new ItemAttendance(R.mipmap.ic_business_trip,
                R.string.oa_evection,
                OaConstants.AttendanceType.LEAVE)));
        mAttendanceList.add(new SectionAttendance(new ItemAttendance(0,
                R.string.oa_statistics,
                OaConstants.AttendanceType.STATISTICS)));

        mAttendanceRecyclerView.setLayoutManager(new ERPGridLayoutManager(getActivity(), 4));
        mAttendanceAdapter = new AttendanceManagerAdapter(R.layout.item_attendance,
                R.layout.tile_item_header,
                mAttendanceList);
        mAttendanceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //TODO:打开界面
               /* FragmentUtils.addFragment(getActivity(), mAttendanceList.get(position).t.getFragment(),
                        R.id.content_fragment);*/
                String type = mAttendanceList.get(position).t.getType();
                if (OaConstants.AttendanceType.SIGN.equalsIgnoreCase(type)) {
                    //TODO:打卡
                    Intent intent = new Intent(getActivity(), AttendanceActivity.class);
                    startActivity(intent);
                } else if (OaConstants.AttendanceType.LEAVE.equalsIgnoreCase(type)) {
                    Intent intent = new Intent(getActivity(), LeaveManagerActivity.class);
                    intent.putExtra(OaConstants.Keywords.KEY_ATTENDANCE_TYPE,
                            mAttendanceList.get(position).t.getItemText());
                    startActivity(intent);
                } else if (OaConstants.AttendanceType.STATISTICS.equalsIgnoreCase(type)) {
                    Intent intent = new Intent(getActivity(), AttendanceLocationActivity.class);
                    startActivity(intent);
                }
            }
        });
        mAttendanceRecyclerView.setAdapter(mAttendanceAdapter);
    }

    private void getPayrollRecordList() {
        showWaiting();
       /* mViewModel.getPayrollRecordList(mPayrollStatusValue,
                UserManager.getInstance().getCurrentCompanyId(),
                mQueryText).observe(this,
                new Observer<ApiResponse<BaseWrapperEntities<PayrollRecordResult>>>() {
                    @Override
                    public void onChanged(ApiResponse<BaseWrapperEntities<PayrollRecordResult>>
                    baseWrapperEntityApiResponse) {
                        mPayrollRecordList.clear();
                        if (baseWrapperEntityApiResponse != null && baseWrapperEntityApiResponse
                                .isSuccess()) {
                            BaseWrapperEntities<PayrollRecordResult> wrapperEntity =
                                    baseWrapperEntityApiResponse.body;
                            if (wrapperEntity != null && wrapperEntity.getResult() != null) {
                                //TODO:使用DiffUtil进行局部刷新
                                mPayrollRecordList.addAll(wrapperEntity.getResult());
                            }
                        }
                        mRecyclerViewAdapter.notifyDataSetChanged();
                        Log.d(TAG, "payroll list is " + (mPayrollRecordList != null ?
                                mPayrollRecordList.size() : 0));
                        mDropDownView.setNoDataViewVisible(mPayrollRecordList == null ||
                        mPayrollRecordList.size() == 0);

                        loadSizeIncrement();
                    }
                });*/
    }
}
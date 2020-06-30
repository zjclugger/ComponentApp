package com.zjclugger.oa.ui;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.zjclugger.lib.base.BaseActivity;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.lib.utils.Monitor;
import com.zjclugger.oa.R;
import com.zjclugger.oa.utils.OaConstants;

/**
 * 请假单管理<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LeaveManagerActivity extends BaseActivity {
    private final static String TAG = "LeaveType";
    private int mAttendanceType;
    private OAViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAttendanceType = getIntent().getIntExtra(OaConstants.Keywords.KEY_ATTENDANCE_TYPE, 0);
        setContentView(R.layout.activity_leave_manager);
        mViewModel = ViewModelProviders.of(this).get(OAViewModel.class);

        initViews();
    }

    @Override
    protected boolean isDetailActivity() {
        return true;
    }

    @Override
    protected String getDetailActivityTitle() {
        return getString(mAttendanceType);
    }

    public void initViews() {

        if (R.string.oa_request_leave == mAttendanceType) {
            LeaveTypeFragment leaveTypeFragment = new LeaveTypeFragment();
            leaveTypeFragment.setOnCloseListener(new Monitor.OnCloseListener<Boolean>() {
                @Override
                public void onClose(Boolean postBackData) {
                    finish();
                }
            });
            //请假，显示类型选择页面
            FragmentUtils.addFirstFragment(this, leaveTypeFragment, R.id.content_fragment);
        } else {
            //加载其他fragment

         /*   Bundle bundle = new Bundle();
            bundle.putInt(Constants.Keywords.KEY_PERSONAL_PAYROLL_ID,
                    mSalaryRecordResultList.get(position).getId());
            mPayrollDetailFragment.setArguments(bundle);

            ERPUtils.addFragment(SalaryStaffDetailActivity.this, mPayrollDetailFragment,
                    R.id.content_fragment);*/

            //BaseLeaveFragment
          /*  ERPUtils.replaceFragment(this, new BaseLeaveFragment(),
                    R.id.content_fragment);*/
        }
    }
}
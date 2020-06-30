package com.zjclugger.oa.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Observer;

import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.oa.R;
import com.zjclugger.oa.webapi.entity.response.PayrollDetailResult;

/**
 * 加班<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OvertimeFragment extends OABaseFragment {
    private final static String TAG = "leaveType";

    @Override
    public int getLayout() {
        return R.layout.fragment_leave_request;
    }

    @Override
    public void initViews() {
        Bundle params = getArguments();
        int recordId = params.getInt(Constants.Keywords.KEY_PERSONAL_PAYROLL_ID, 0);
        if (recordId > 0) {
            showWaiting();
            mViewModel.getPersonalPayrollRecordDetail(String.valueOf(recordId)).observe(this,
                    new Observer<ApiResponse<BaseWrapperEntity<PayrollDetailResult>>>() {
                        @Override
                        public void onChanged(ApiResponse<BaseWrapperEntity<PayrollDetailResult>> baseWrapperEntityApiResponse) {
                            closeProgressDialog();
                            if (baseWrapperEntityApiResponse != null && baseWrapperEntityApiResponse.isSuccess()) {
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
        }
    }

    private void bindData(PayrollDetailResult payrollDetailResult) {
        if (payrollDetailResult != null) {
            initDetailTitleViews(payrollDetailResult.getEmployeeName() + "的工资单");
        }
    }

    @Override
    public Boolean getPostBackData() {
        return false;
    }
}
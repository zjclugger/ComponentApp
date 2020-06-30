package com.zjclugger.oa.repository;

import androidx.lifecycle.LiveData;

import com.zjclugger.lib.BuildConfig;
import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.BaseWebApi;
import com.zjclugger.lib.api.entity.BaseWrapperEntities;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;
import com.zjclugger.oa.webapi.OaApi;
import com.zjclugger.oa.webapi.entity.response.PayrollDetailResult;
import com.zjclugger.oa.webapi.entity.response.PayrollRecordHeaderResult;
import com.zjclugger.oa.webapi.entity.response.PayrollRecordResult;
import com.zjclugger.oa.webapi.entity.response.PayrollRecordStatusResult;
import com.zjclugger.oa.webapi.entity.response.OaRecordResult;

public class OaRepository {
    private static OaRepository mInstance;

    public static OaRepository getInstance() {
        if (mInstance == null) {
            mInstance = new OaRepository();
        }
        return mInstance;
    }

    private OaApi getApi() {
        return BaseWebApi.instance().getApi(OaApi.class, BuildConfig.URL_SALARY);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<OaRecordResult>>> getPersonalPayrollRecord(String staffId) {
        return getApi().getPersonalPayrollRecord(staffId);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<PayrollDetailResult>>> getPersonalPayrollRecordDetail(String recordId) {
        return getApi().getPersonalPayrollRecordDetail(recordId);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<PayrollRecordResult>>> getPayrollRecordList(int status, String companyId, String statusName) {
        return getApi().getPayrollRecordList(status, companyId, statusName);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<PayrollRecordHeaderResult>>> getPayrollRecordHeader(String id) {
        return getApi().getPayrollRecordHeader(id);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<OaRecordResult>>> getPayrollRecordList(String payrollRecordId) {
        return getApi().getPayrollRecordList(payrollRecordId);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<PayrollRecordStatusResult>>> getPayrollStatus() {
        return getApi().getPayrollStatus();
    }
}


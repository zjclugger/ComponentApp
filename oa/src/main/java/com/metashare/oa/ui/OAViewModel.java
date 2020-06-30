package com.zjclugger.oa.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.entity.BaseWrapperEntities;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;
import com.zjclugger.oa.repository.OaRepository;
import com.zjclugger.oa.webapi.entity.response.PayrollDetailResult;
import com.zjclugger.oa.webapi.entity.response.PayrollRecordHeaderResult;
import com.zjclugger.oa.webapi.entity.response.PayrollRecordResult;
import com.zjclugger.oa.webapi.entity.response.PayrollRecordStatusResult;
import com.zjclugger.oa.webapi.entity.response.OaRecordResult;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OAViewModel extends ViewModel {

    public LiveData<ApiResponse<BaseWrapperEntities<OaRecordResult>>> getPersonalPayrollRecord(String staffId) {
        return OaRepository.getInstance().getPersonalPayrollRecord(staffId);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<PayrollDetailResult>>> getPersonalPayrollRecordDetail(String personalRecordId) {
        return OaRepository.getInstance().getPersonalPayrollRecordDetail(personalRecordId);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<PayrollRecordResult>>> getPayrollRecordList(int status, String companyId, String statusName) {
        return OaRepository.getInstance().getPayrollRecordList(status, companyId, statusName);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<PayrollRecordHeaderResult>>> getPayrollRecordHeader(String id) {
        return OaRepository.getInstance().getPayrollRecordHeader(id);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<OaRecordResult>>> getPayrollRecordList(String payrollRecordId) {
        return OaRepository.getInstance().getPayrollRecordList(payrollRecordId);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<PayrollRecordStatusResult>>> getPayrollStatus() {
        return OaRepository.getInstance().getPayrollStatus();
    }

}

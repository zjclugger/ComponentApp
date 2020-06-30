package com.zjclugger.finance.repository;

import androidx.lifecycle.LiveData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zjclugger.finance.webapi.FinanceApi;
import com.zjclugger.finance.webapi.entity.response.AccountBillDetailResult;
import com.zjclugger.finance.webapi.entity.response.AccountBillListResult;
import com.zjclugger.finance.webapi.entity.response.BillTypeResult;
import com.zjclugger.finance.webapi.entity.response.DepartReimbursementResult;
import com.zjclugger.finance.webapi.entity.response.OrganizationUserResult;
import com.zjclugger.finance.webapi.entity.response.OriginalBillListResult;
import com.zjclugger.finance.webapi.entity.response.OriginalBillResult;
import com.zjclugger.finance.webapi.entity.response.OriginalDetailResult;
import com.zjclugger.finance.webapi.entity.response.ReimbursementResult;
import com.zjclugger.finance.webapi.entity.response.ReportAssetsLiabilitiesResult;
import com.zjclugger.finance.webapi.entity.response.ReportProfitResult;
import com.zjclugger.lib.BuildConfig;
import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.BaseWebApi;
import com.zjclugger.lib.api.entity.BaseWrapper;
import com.zjclugger.lib.api.entity.BaseWrapperEntities;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FinanceRepository {
    private static FinanceRepository mInstance;

    public static FinanceRepository getInstance() {
        if (mInstance == null) {
            mInstance = new FinanceRepository();
        }
        return mInstance;
    }

    private FinanceApi getApi() {
        return BaseWebApi.instance().getApi(FinanceApi.class, BuildConfig.URL_FINANCE);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<OriginalBillResult>>> uploadBillImage(Map<String,
            Object> params) {
        return getApi().uploadBillImage(params);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<OriginalBillResult>>> getCompanyBill(String companyId) {
        return getApi().getCompanyBill(companyId);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<OriginalBillResult>>> getBill(String id) {
        return getApi().getBill(id);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<OriginalBillListResult>>> getOriginalList(Map<String,
            Object> params) {
        return getApi().getOriginalList(params);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<AccountBillListResult>>> getAccountBillList(Map<String,
            Object> params) {
        // return getApi().getAccountBillList(params);
        return BaseWebApi.instance().setNullDefaultStringValue("暂无").getApi(FinanceApi.class,
                BuildConfig.URL_FINANCE).getAccountBillList(params);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<BillTypeResult>>> getBillTypeAll() {
        return getApi().getBillTypeAll();
    }

    public LiveData<ApiResponse<BaseWrapperEntity<OriginalDetailResult>>> getOriginalDetail(String id) {
        return getApi().getOriginalDetail(id);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<AccountBillDetailResult>>> getAccountDetail(String id) {
        return BaseWebApi.instance().setNullDefaultStringValue("暂无").getApi(FinanceApi.class,
                BuildConfig.URL_FINANCE).getAccountDetail(id);
    }

    public LiveData<ApiResponse<BaseWrapper<String>>> updateOriginalBill(String originalJson) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;" +
                "charset=UTF-8"), originalJson);
        return getApi().updateOriginalBill(requestBody);
    }

    public LiveData<ApiResponse<BaseWrapper<String>>> submitToCheck(String originalId) {
        return getApi().submitToCheck(originalId);
    }

    public LiveData<ApiResponse<BaseWrapper<String>>> checkOriginal(String originalId,
                                                                    boolean isPass) {
        return isPass ? getApi().checkPass(originalId) : getApi().checkFail(originalId);
    }

    public LiveData<ApiResponse<BaseWrapper<String>>> createAccountBill(String originalId) {
        return getApi().createAccountBill(originalId);
    }

    public LiveData<ApiResponse<BaseWrapper<String>>> submitApprove(String accountId) {
        return getApi().submitApprove(accountId);
    }

    public LiveData<ApiResponse<BaseWrapper<String>>> passApprove(String accountId) {
        return getApi().passApprove(accountId);
    }

    public LiveData<ApiResponse<BaseWrapper<String>>> notPassApprove(String accountId,
                                                                     String reason) {
        return getApi().notPassApprove(accountId, reason);
    }

    public LiveData<ApiResponse<BaseWrapper<String>>> posting(String accountId) {
        return getApi().posting(accountId);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<ReportProfitResult>>> getProfitReport(String endDate) {
        return getApi().getProfitReport(endDate);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<ReportAssetsLiabilitiesResult>>> getAssetsLiabilitiesReport(String endDate) {
        return getApi().getAssetsLiabilitiesReport(endDate);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<ReimbursementResult>>> getReportForm(Map<String
            , Object> params) {
        return getApi().getReportForm(params);
    }

    public LiveData<ApiResponse<BaseWrapper<JsonArray>>> getOrganizationDeparts() {
        return getApi().getOrganizationDeparts();
    }

    public LiveData<ApiResponse<BaseWrapperEntities<OrganizationUserResult>>> getOrganizationUsers(String orgId) {
        return getApi().getOrganizationUsers(orgId);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<DepartReimbursementResult>>> getDepartmentReimbursement() {
        return getApi().getDepartmentReimbursement();
    }

    public LiveData<ApiResponse<BaseWrapper<JsonArray>>> getReasonReimbursement() {
        return getApi().getReasonReimbursement();
    }

    public LiveData<ApiResponse<BaseWrapper<JsonObject>>> getMonthPercentage() {
        return getApi().getMonthPercentage();
    }

    public LiveData<ApiResponse<BaseWrapper<JsonArray>>> getSortReimbursement() {
        return getApi().getSortReimbursement();
    }
}
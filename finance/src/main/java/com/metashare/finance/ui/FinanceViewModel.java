package com.zjclugger.finance.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zjclugger.finance.repository.FinanceRepository;
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
import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.entity.BaseWrapper;
import com.zjclugger.lib.api.entity.BaseWrapperEntities;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;

import java.util.Map;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class FinanceViewModel extends ViewModel {

    public LiveData<ApiResponse<BaseWrapperEntity<OriginalBillResult>>> uploadBillImage(Map<String,
            Object> params) {
        return FinanceRepository.getInstance().uploadBillImage(params);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<OriginalBillResult>>> getCompanyBill(String companyId) {
        return FinanceRepository.getInstance().getCompanyBill(companyId);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<OriginalBillResult>>> getBill(String id) {
        return FinanceRepository.getInstance().getBill(id);
    }

    /**
     * 获取原始凭证列表
     *
     * @param params
     * @return
     */
    public LiveData<ApiResponse<BaseWrapperEntity<OriginalBillListResult>>> getOriginalList(Map<String,
            Object> params) {
        return FinanceRepository.getInstance().getOriginalList(params);
    }

    /**
     * 获取原始凭证详情
     *
     * @param id
     * @return
     */
    public LiveData<ApiResponse<BaseWrapperEntity<OriginalDetailResult>>> getOriginalDetail(String id) {
        return FinanceRepository.getInstance().getOriginalDetail(id);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<AccountBillDetailResult>>> getAccountDetail(String id) {
        return FinanceRepository.getInstance().getAccountDetail(id);
    }

    public LiveData<ApiResponse<BaseWrapper<String>>> updateOriginalBill(String originalJson) {
        return FinanceRepository.getInstance().updateOriginalBill(originalJson);
    }

    /**
     * 获取记账凭证
     *
     * @param params
     * @return
     */
    public LiveData<ApiResponse<BaseWrapperEntity<AccountBillListResult>>> getAccountBillList(Map<String,
            Object> params) {
        return FinanceRepository.getInstance().getAccountBillList(params);
    }

    /**
     * 获取凭证类型
     *
     * @return
     */
    public LiveData<ApiResponse<BaseWrapperEntities<BillTypeResult>>> getBillTypeAll() {
        return FinanceRepository.getInstance().getBillTypeAll();
    }

    /**
     * 提交校对
     *
     * @param originalId
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<String>>> submitToCheck(String originalId) {
        return FinanceRepository.getInstance().submitToCheck(originalId);
    }

    /**
     * 确认校对是否通过
     *
     * @param originalId
     * @param isPass
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<String>>> checkOriginal(String originalId,
                                                                    boolean isPass) {
        return FinanceRepository.getInstance().checkOriginal(originalId, isPass);
    }

    /**
     * 生成记账凭证
     *
     * @param originalId
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<String>>> createAccountBill(String originalId) {
        return FinanceRepository.getInstance().createAccountBill(originalId);
    }

    /**
     * 提交记账凭证审核
     *
     * @param accountId
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<String>>> submitApprove(String accountId) {
        return FinanceRepository.getInstance().submitApprove(accountId);
    }

    /**
     * 记账凭证审核通过
     *
     * @param accountId
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<String>>> passApprove(String accountId) {
        return FinanceRepository.getInstance().passApprove(accountId);
    }

    /**
     * 记账凭证过账打回（审核未通过）
     *
     * @param accountId
     * @param reason
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<String>>> rejectedAccount(String accountId,
                                                                      String reason) {
        return FinanceRepository.getInstance().notPassApprove(accountId, reason);
    }

    /**
     * 记账凭证过账
     *
     * @param accountId
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<String>>> passAccount(String accountId) {
        return FinanceRepository.getInstance().posting(accountId);
    }

    /**
     * 利润表
     *
     * @param endDate
     * @return
     */
    public LiveData<ApiResponse<BaseWrapperEntities<ReportProfitResult>>> getProfitReport(String endDate) {
        return FinanceRepository.getInstance().getProfitReport(endDate);
    }

    /**
     * 资产负债表
     *
     * @param endDate
     * @return
     */
    public LiveData<ApiResponse<BaseWrapperEntity<ReportAssetsLiabilitiesResult>>> getAssetsLiabilitiesReport(String endDate) {
        return FinanceRepository.getInstance().getAssetsLiabilitiesReport(endDate);
    }

    /**
     * 输出统计报表
     *
     * @param params
     * @return
     */
    public LiveData<ApiResponse<BaseWrapperEntity<ReimbursementResult>>> getReportForm(Map<String,
            Object> params) {
        return FinanceRepository.getInstance().getReportForm(params);
    }

    /**
     * 查询当前企业所有部门
     *
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<JsonArray>>> getOrganizationDeparts() {
        return FinanceRepository.getInstance().getOrganizationDeparts();
    }

    /**
     * 查询企业用户
     *
     * @param orgId
     * @return
     */
    public LiveData<ApiResponse<BaseWrapperEntities<OrganizationUserResult>>> getOrganizationUsers(String orgId) {
        return FinanceRepository.getInstance().getOrganizationUsers(orgId);
    }

    /**
     * 本月按部门分类的报销
     *
     * @return
     */
    public LiveData<ApiResponse<BaseWrapperEntity<DepartReimbursementResult>>> getDepartmentReimbursement() {
        return FinanceRepository.getInstance().getDepartmentReimbursement();
    }

    /**
     * 本月按报销事由分类的报销
     *
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<JsonArray>>> getReasonReimbursement() {
        return FinanceRepository.getInstance().getReasonReimbursement();
    }

    /**
     * 本月报销占比
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<JsonObject>>> getMonthPercentage() {
        return FinanceRepository.getInstance().getMonthPercentage();
    }

    /**
     * 本月报销排行
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<JsonArray>>> getSortReimbursement() {
        return FinanceRepository.getInstance().getSortReimbursement();
    }
}
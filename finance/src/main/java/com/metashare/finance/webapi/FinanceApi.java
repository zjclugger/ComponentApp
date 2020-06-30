package com.zjclugger.finance.webapi;


import androidx.lifecycle.LiveData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface FinanceApi {

    @Multipart
    @POST("UploadImages")
    LiveData<ApiResponse<BaseWrapperEntity<OriginalBillResult>>> uploadBillImage(@PartMap Map<String,
            Object> params);

    @GET("SelectByCompanyId")
    LiveData<ApiResponse<BaseWrapperEntities<OriginalBillResult>>> getCompanyBill(@Query(
            "companyId") String companyId);

    @GET("selectById")
    LiveData<ApiResponse<BaseWrapperEntity<OriginalBillResult>>> getBill(@Query("id") String id);

    @GET("increment/list")
    LiveData<ApiResponse<BaseWrapperEntity<OriginalBillListResult>>> getOriginalList(@QueryMap Map<String,
            Object> params);

    @GET("increment/detail/{id}")
    LiveData<ApiResponse<BaseWrapperEntity<OriginalDetailResult>>> getOriginalDetail(@Path("id") String id);

    //@Headers({"Content-Type: application/json"})
    @HTTP(method = "PUT", path = "increment", hasBody = true)
    LiveData<ApiResponse<BaseWrapper<String>>> updateOriginalBill(@Body RequestBody original);

    @GET("increment/billtypeall")
    LiveData<ApiResponse<BaseWrapperEntities<BillTypeResult>>> getBillTypeAll();

    /**
     * 提交校对
     *
     * @param originalId 原始凭证id
     * @return
     */
    @Multipart
    @POST("increment/submitproofread")
    LiveData<ApiResponse<BaseWrapper<String>>> submitToCheck(@Part("id") String originalId);

    @Multipart
    @POST("increment/proofreadnotpass")
    LiveData<ApiResponse<BaseWrapper<String>>> checkFail(@Part("id") String originalId);

    @Multipart
    @POST("increment/proofreadpass")
    LiveData<ApiResponse<BaseWrapper<String>>> checkPass(@Part("id") String originalId);

    @Multipart
    @POST("accountvoucher/generateamultiplebyapp")
    LiveData<ApiResponse<BaseWrapper<String>>> createAccountBill(@Part("incrementIds") String originalId);

    @GET("accountvoucher/list")
    LiveData<ApiResponse<BaseWrapperEntity<AccountBillListResult>>> getAccountBillList(@QueryMap Map<String,
            Object> params);

    @GET("accountvoucher/detail")
    LiveData<ApiResponse<BaseWrapperEntity<AccountBillDetailResult>>> getAccountDetail(@Query("id"
    ) String id);

    /**
     * 提交审核
     *
     * @param accountId
     * @return
     */
    @Multipart
    @POST("accountvoucher/submitaudit")
    LiveData<ApiResponse<BaseWrapper<String>>> submitApprove(@Part("id") String accountId);

    /**
     * 审核通过
     *
     * @param accountId
     * @return
     */
    @Multipart
    @POST("accountvoucher/auditpass")
    LiveData<ApiResponse<BaseWrapper<String>>> passApprove(@Part("id") String accountId);

    /**
     * 过账打回（审核未通过）
     *
     * @param accountId
     * @param reason
     * @return
     */
    @Multipart
    @POST("accountvoucher/auditnotpass")
    LiveData<ApiResponse<BaseWrapper<String>>> notPassApprove(@Part("id") String accountId,
                                                              @Part("repulseReason") String reason);

    /**
     * 过账
     *
     * @param accountId
     * @return
     */
    @Multipart
    @POST("accountvoucher/posting")
    LiveData<ApiResponse<BaseWrapper<String>>> posting(@Part("id") String accountId);

    /**
     * 利润表
     *
     * @param endDate
     * @return
     */
    @GET("accountvoucher/profitsheet")
    LiveData<ApiResponse<BaseWrapperEntities<ReportProfitResult>>> getProfitReport(@Query(
            "date") String endDate);

    /**
     * 资产负债表
     *
     * @param endDate
     * @return
     */
    @GET("accountvoucher/assetliabilitysheetapp")
    LiveData<ApiResponse<BaseWrapperEntity<ReportAssetsLiabilitiesResult>>> getAssetsLiabilitiesReport(@Query(
            "date") String endDate);

    /**
     * 输出统计报表
     *
     * @param params
     * @return
     */
    @GET("increment/reportform")
    LiveData<ApiResponse<BaseWrapperEntity<ReimbursementResult>>> getReportForm(@QueryMap Map<String,
            Object> params);

    /**
     * 查询当前企业所有部门
     * @return
     */
    @GET("increment/findorg")
    LiveData<ApiResponse<BaseWrapper<JsonArray>>> getOrganizationDeparts();

    /**
     * 查询企业用户
     * @param orgId
     * @return
     */
    @GET("increment/finduserbyorg")
    LiveData<ApiResponse<BaseWrapperEntities<OrganizationUserResult>>> getOrganizationUsers(@Query(
            "orgId") String orgId);

    /**
     * 本月按部门分类的报销
     * @return
     */
    @GET("increment/statisticsbyorgofmonth")
    LiveData<ApiResponse<BaseWrapperEntity<DepartReimbursementResult>>> getDepartmentReimbursement();


    /**
     * 本月按报销事由分类的报销
     * @return
     */
    @GET("increment/statisticsbyreason")
    LiveData<ApiResponse<BaseWrapper<JsonArray>>> getReasonReimbursement();

    /**
     * 本月报销占比
     * @return
     */
    @GET("increment/findratio")
    LiveData<ApiResponse<BaseWrapper<JsonObject>>> getMonthPercentage();

    /**
     * 本月报销排行
     * @return
     */
    @GET("increment/statisticsbyuserofmonth")
    LiveData<ApiResponse<BaseWrapper<JsonArray>>> getSortReimbursement();
}
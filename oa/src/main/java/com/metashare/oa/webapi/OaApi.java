package com.zjclugger.oa.webapi;

import androidx.lifecycle.LiveData;

import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.entity.BaseWrapperEntities;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;
import com.zjclugger.oa.webapi.entity.response.PayrollDetailResult;
import com.zjclugger.oa.webapi.entity.response.PayrollRecordHeaderResult;
import com.zjclugger.oa.webapi.entity.response.PayrollRecordResult;
import com.zjclugger.oa.webapi.entity.response.PayrollRecordStatusResult;
import com.zjclugger.oa.webapi.entity.response.OaRecordResult;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OaApi {

    /**
     * 员工个人工资记录
     *
     * @param staffId
     * @return
     */
    @GET("PayrollManagement/PersonalSalaryRecordApi/SelectByEmployeeId")
    LiveData<ApiResponse<BaseWrapperEntities<OaRecordResult>>> getPersonalPayrollRecord(@Query(
            "id") String staffId);

    /**
     * 员工个人工资记录详情
     *
     * @param recordId
     * @return
     */
    @GET("PayrollManagement/PersonalSalaryRecordApi/SelectByIdInfo")
    LiveData<ApiResponse<BaseWrapperEntity<PayrollDetailResult>>> getPersonalPayrollRecordDetail(@Query("id") String recordId);

    /**
     * 根据发放状态获取某公司工资记录列表
     *
     * @param status
     * @param companyId
     * @return
     */
    @GET("PayrollManagement/PayrollRecordApi/SelectByPayrollstatus")
    LiveData<ApiResponse<BaseWrapperEntities<PayrollRecordResult>>> getPayrollRecordList(@Query(
            "payrollstatus") int status, @Query("companyId") String companyId, @Query("name") String statusName);

    /**
     * 根据工资记录ID返回工资记录详情
     *
     * @param payrollRecordId
     * @return
     */
    @GET("PayrollManagement/PersonalSalaryRecordApi/SelectByPayrollRecord")
    LiveData<ApiResponse<BaseWrapperEntities<OaRecordResult>>> getPayrollRecordList(@Query(
            "payrollRecordId") String payrollRecordId);

    /**
     * 根据ID返回工资记录详情(工资记录详情页面表头接口)
     *
     * @param id
     * @return
     */
    @GET("PayrollManagement/PayrollRecordApi/SelectById")
    LiveData<ApiResponse<BaseWrapperEntity<PayrollRecordHeaderResult>>> getPayrollRecordHeader(@Query(
            "id") String id);

    @GET("PayrollManagement/PersonalSalaryRecordApi/SelectAllPayrollStatus")
    LiveData<ApiResponse<BaseWrapperEntities<PayrollRecordStatusResult>>> getPayrollStatus();
}

package com.zjclugger.component.module.department;

import androidx.lifecycle.LiveData;

import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;

import retrofit2.http.GET;

public interface DepartApi {

    /**
     * 获取人员花名册
     *
     * @param staffRequest
     * @return
     */
   /* @FormUrlEncoded
    @POST("PersonnelManagement/EmployeeProfileApi/SelectEmployeeProfileByConditions")
    LiveData<ApiResponse<BaseWrapperEntity<StaffResult>>> getStaffList(@FieldMap Map<String,
            Object> staffRequest);

    *//**
     * 部门列表
     *
     * @param companyId
     * @return
     *//*
    @GET("OrganizationManagement/DepartmentApi/SelectByCompany")
    LiveData<ApiResponse<BaseWrapperEntities<DepartmentResult>>> getDepartmentList(@Query(
            "companyId") String companyId);

    *//**
     * 岗位列表
     *
     * @param companyId
     * @return
     *//*
    @GET("PositionManagement/PostApi/SelectByCompany")
    LiveData<ApiResponse<BaseWrapperEntities<GearPositionResult>>> getGearPositionList(@Query(
            "companyId") String companyId);

    *//**
     * 员工性质列表
     *
     * @return
     *//*
    @GET("PersonnelManagement/EmployeeProfileApi/GetEmployeeNatures")
    LiveData<ApiResponse<BaseWrapperEntities<StaffNaturesResult>>> getStaffNatureList();

    *//**
     * 在职员工状态列表
     *
     * @param isInServiceStatus
     * @return
     *//*
    @GET("PersonnelManagement/EmployeeProfileApi/GetEmployeeStatus")
    LiveData<ApiResponse<BaseWrapperEntities<StaffStatusResult>>> getStaffStatus(@Query(
            "isInServiceStatus") String isInServiceStatus);

    *//**
     * 人员详情
     *
     * @param id
     * @param isAggregatedChildren
     * @return
     *//*
    @GET("PersonnelManagement/EmployeeProfileApi/SelectByIdWithChild")
    LiveData<ApiResponse<BaseWrapperEntity<StaffDetailInfoResult>>> getStaffDetail(@Query("id") int id, @Query("isAggregatedChildren") String isAggregatedChildren);

    *//*  "name": "",
              "pageIndex": 1,
              "pageSize" 10:
  *//*

    *//**
     * 入职管理
     *
     * @param request
     * @return
     *//*
    @GET("PersonnelManagement/PendingEmployeeApi/GetPendingEmoployeesByName")
    LiveData<ApiResponse<BaseWrapperEntities<StaffPendingResult>>> getPendingEmployeesByName(@FieldMap Map<String, Object> request);
*/
    @GET("post/listtree")
    LiveData<ApiResponse<BaseWrapperEntity<DepartmentPostResult>>> getDepartmentPost();
}

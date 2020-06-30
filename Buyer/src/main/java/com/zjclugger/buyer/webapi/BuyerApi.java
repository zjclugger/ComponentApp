package com.zjclugger.buyer.webapi;

import androidx.lifecycle.LiveData;

import com.zjclugger.buyer.webapi.response.LoginResult;
import com.zjclugger.buyer.webapi.response.UserLoginResult;
import com.zjclugger.buyer.webapi.response.UserPermissionResult;
import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.entity.BaseWrapper;
import com.zjclugger.lib.api.entity.BaseWrapperEntities;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface BuyerApi {

    /**
     * @param userName
     * @param password
     * @param companyName
     * @return
     */
    @FormUrlEncoded
    @POST("Login")
    LiveData<ApiResponse<BaseWrapperEntity<LoginResult>>> userLogin(@Field("UserName") String userName, @Field("PassWord") String password, @Field("CompanyName") String companyName);

    @FormUrlEncoded
    @POST("login")
    LiveData<ApiResponse<BaseWrapperEntity<UserLoginResult>>> userLogin(@Field("username") String userName, @Field("password") String password);

    @Multipart
    @POST("user/changePwd")
    LiveData<ApiResponse<BaseWrapper<Boolean>>> changePassword(@Part("password") String newPassword);

    @Multipart
    @POST("user/checkemail")
    LiveData<ApiResponse<BaseWrapper<String>>> checkEmail(@Part("email") String email);

    @Multipart
    @POST("regist")
    LiveData<ApiResponse<BaseWrapper<Boolean>>> register(@Part("email") String email, @Part(
            "password") String password);

    @GET("permission/findpermissionbyuser")
    LiveData<ApiResponse<BaseWrapperEntities<UserPermissionResult>>> getPermissionList();
}

package com.zjclugger.seller.repository;

import androidx.lifecycle.LiveData;

import com.zjclugger.seller.webapi.SellerApi;
import com.zjclugger.seller.webapi.response.LoginResult;
import com.zjclugger.seller.webapi.response.UserLoginResult;
import com.zjclugger.seller.webapi.response.UserPermissionResult;
import com.zjclugger.lib.BuildConfig;
import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.BaseWebApi;
import com.zjclugger.lib.api.entity.BaseWrapper;
import com.zjclugger.lib.api.entity.BaseWrapperEntities;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;

public class SellerRepository {
    private static SellerRepository mInstance;

    public static SellerRepository getInstance() {
        if (mInstance == null) {
            mInstance = new SellerRepository();
        }
        return mInstance;
    }

    public LiveData<ApiResponse<BaseWrapperEntity<LoginResult>>> userLogin(String userName,
                                                                           String password,
                                                                           String companyName) {
        return BaseWebApi.instance().getApi(SellerApi.class, BuildConfig.URL_BASE + "UserApi/").userLogin(userName, password, companyName);
        //return LoginWebApi.instance().getApi().userLogin(userName, password, companyName);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<UserLoginResult>>> userLogin(String userName,
                                                                               String password) {
        return BaseWebApi.instance().getApi(SellerApi.class, BuildConfig.URL_BASE, true).userLogin(userName, password);
    }

    public LiveData<ApiResponse<BaseWrapper<Boolean>>> changePassword(String password) {
        return BaseWebApi.instance().getApi(SellerApi.class, BuildConfig.URL_SYSTEM).changePassword(password);
    }

    public LiveData<ApiResponse<BaseWrapper<String>>> checkEmail(String email) {
        return BaseWebApi.instance().getApi(SellerApi.class, BuildConfig.URL_SYSTEM, true).checkEmail(email);
    }

    public LiveData<ApiResponse<BaseWrapper<Boolean>>> register(String email,String password) {
        return BaseWebApi.instance().getApi(SellerApi.class, BuildConfig.URL_BASE, true).register(email,password);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<UserPermissionResult>>> getPermissionList() {
        return BaseWebApi.instance().getApi(SellerApi.class, BuildConfig.URL_SYSTEM).getPermissionList();
    }
}
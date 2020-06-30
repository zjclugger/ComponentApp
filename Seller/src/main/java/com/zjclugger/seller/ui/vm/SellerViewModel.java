package com.zjclugger.seller.ui.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zjclugger.seller.repository.SellerRepository;
import com.zjclugger.seller.webapi.response.LoginResult;
import com.zjclugger.seller.webapi.response.UserLoginResult;
import com.zjclugger.seller.webapi.response.UserPermissionResult;
import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.entity.BaseWrapper;
import com.zjclugger.lib.api.entity.BaseWrapperEntities;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class SellerViewModel extends ViewModel {

    public LiveData<ApiResponse<BaseWrapperEntity<LoginResult>>> userLogin(String userName,
                                                                           String password,
                                                                           String companyName) {
        return SellerRepository.getInstance().userLogin(userName, password, companyName);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<UserLoginResult>>> userLogin(String userName,
                                                                               String password) {
        return SellerRepository.getInstance().userLogin(userName, password);
    }

    /**
     * 修改密码
     *
     * @param password
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<Boolean>>> changePassword(String password) {
        return SellerRepository.getInstance().changePassword(password);
    }

    /**
     * 检查邮件地址是否合法
     *
     * @param email
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<String>>> checkEmail(String email) {
        return SellerRepository.getInstance().checkEmail(email);
    }

    public LiveData<ApiResponse<BaseWrapper<Boolean>>> register(String email, String password) {
        return SellerRepository.getInstance().register(email, password);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<UserPermissionResult>>> getPermissionList() {
        return SellerRepository.getInstance().getPermissionList();
    }
}
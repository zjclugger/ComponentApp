package com.zjclugger.buyer.ui.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zjclugger.buyer.persistence.entity.Shopcart;
import com.zjclugger.buyer.repository.BuyerRepository;
import com.zjclugger.buyer.webapi.response.LoginResult;
import com.zjclugger.buyer.webapi.response.UserLoginResult;
import com.zjclugger.buyer.webapi.response.UserPermissionResult;
import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.entity.BaseWrapper;
import com.zjclugger.lib.api.entity.BaseWrapperEntities;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class BuyerViewModel extends ViewModel {

    public LiveData<ApiResponse<BaseWrapperEntity<LoginResult>>> userLogin(String userName,
                                                                           String password,
                                                                           String companyName) {
        return BuyerRepository.getInstance().userLogin(userName, password, companyName);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<UserLoginResult>>> userLogin(String userName,
                                                                               String password) {
        return BuyerRepository.getInstance().userLogin(userName, password);
    }

    /**
     * 修改密码
     *
     * @param password
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<Boolean>>> changePassword(String password) {
        return BuyerRepository.getInstance().changePassword(password);
    }

    /**
     * 检查邮件地址是否合法
     *
     * @param email
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<String>>> checkEmail(String email) {
        return BuyerRepository.getInstance().checkEmail(email);
    }

    public LiveData<ApiResponse<BaseWrapper<Boolean>>> register(String email, String password) {
        return BuyerRepository.getInstance().register(email, password);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<UserPermissionResult>>> getPermissionList() {
        return BuyerRepository.getInstance().getPermissionList();
    }


    //todo: local data
    public LiveData<List<Shopcart>> getShopCarts() {
        return BuyerRepository.getInstance().getShopCarts();
    }

    public Completable addToShopCart(Shopcart shopcart) {
        return BuyerRepository.getInstance().addToShopCart(shopcart);
    }

    public Single<Long> saveShopCart(Shopcart shopcart) {
        return BuyerRepository.getInstance().saveShopCart(shopcart);
    }

    public Flowable<List<Shopcart>> getShopCartList(){
        return BuyerRepository.getInstance().getShopCartList();
    }
}
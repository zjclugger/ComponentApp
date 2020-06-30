package com.zjclugger.buyer.repository;

import androidx.lifecycle.LiveData;

import com.zjclugger.buyer.persistence.database.BuyerDatabase;
import com.zjclugger.buyer.persistence.entity.Shopcart;
import com.zjclugger.buyer.webapi.BuyerApi;
import com.zjclugger.buyer.webapi.response.LoginResult;
import com.zjclugger.buyer.webapi.response.UserLoginResult;
import com.zjclugger.buyer.webapi.response.UserPermissionResult;
import com.zjclugger.lib.BuildConfig;
import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.BaseWebApi;
import com.zjclugger.lib.api.entity.BaseWrapper;
import com.zjclugger.lib.api.entity.BaseWrapperEntities;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class BuyerRepository {
    private static BuyerRepository mInstance;

    public static BuyerRepository getInstance() {
        if (mInstance == null) {
            mInstance = new BuyerRepository();
        }
        return mInstance;
    }

    public LiveData<ApiResponse<BaseWrapperEntity<LoginResult>>> userLogin(String userName,
                                                                           String password,
                                                                           String companyName) {
        return BaseWebApi.instance().getApi(BuyerApi.class, BuildConfig.URL_BASE + "UserApi/").userLogin(userName, password, companyName);
        //return LoginWebApi.instance().getApi().userLogin(userName, password, companyName);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<UserLoginResult>>> userLogin(String userName,
                                                                               String password) {
        return BaseWebApi.instance().getApi(BuyerApi.class, BuildConfig.URL_BASE, true).userLogin(userName, password);
    }

    public LiveData<ApiResponse<BaseWrapper<Boolean>>> changePassword(String password) {
        return BaseWebApi.instance().getApi(BuyerApi.class, BuildConfig.URL_SYSTEM).changePassword(password);
    }

    public LiveData<ApiResponse<BaseWrapper<String>>> checkEmail(String email) {
        return BaseWebApi.instance().getApi(BuyerApi.class, BuildConfig.URL_SYSTEM, true).checkEmail(email);
    }

    public LiveData<ApiResponse<BaseWrapper<Boolean>>> register(String email, String password) {
        return BaseWebApi.instance().getApi(BuyerApi.class, BuildConfig.URL_BASE, true).register(email, password);
    }

    public LiveData<ApiResponse<BaseWrapperEntities<UserPermissionResult>>> getPermissionList() {
        return BaseWebApi.instance().getApi(BuyerApi.class, BuildConfig.URL_SYSTEM).getPermissionList();
    }

    //todo: local data
    public LiveData<List<Shopcart>> getShopCarts() {
        return BuyerDatabase.getInstance().getShopCartDao().getShopCart();
    }

    public Completable addToShopCart(Shopcart shopcart) {
        return BuyerDatabase.getInstance().getShopCartDao().insertItem(shopcart);
    }

    public Flowable<List<Shopcart>> getShopCartList() {
        return BuyerDatabase.getInstance().getShopCartDao().getShopCartList();
    }

    public Single<Long> saveShopCart(Shopcart shopcart) {
        return BuyerDatabase.getInstance().getShopCartDao().insert(shopcart);
    }
}
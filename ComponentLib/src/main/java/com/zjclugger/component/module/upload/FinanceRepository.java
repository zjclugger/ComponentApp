package com.zjclugger.component.module.upload;

import android.util.ArrayMap;

import androidx.lifecycle.LiveData;

import com.zjclugger.lib.BuildConfig;
import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.BaseWebApi;
import com.zjclugger.lib.api.entity.BaseWrapper;

import io.reactivex.Observable;
import retrofit2.http.Body;

public class FinanceRepository {
    private static FinanceRepository mInstance;
    private FinanceApi mApi;

    public static FinanceRepository getInstance() {
        if (mInstance == null) {
            mInstance = new FinanceRepository();
        }
        return mInstance;
    }

    private FinanceApi getApi() {
        if (mApi == null) {
            //mApi = BaseWebApi.instance().getApi(FinanceApi.class, BuildConfig.URL_FINANCE);
            mApi = BaseWebApi.instance()
                    .setReadTimeout(100)
                    .setWriteTimeout(50)
                    .setConnectTimeout(60)
                    .setForceRebuild(true)
                    .getApi(FinanceApi.class, BuildConfig.URL_FINANCE);
        }
        return mApi;
    }

    public Observable<Object> uploadOriginal(@Body ArrayMap<String, Object> params) {
        return getApi().uploadOriginal(params);
    }

    public LiveData<ApiResponse<BaseWrapper<String>>> deleteOriginal(String id) {
        return getApi().deleteOriginal(id);
    }

    public LiveData<ApiResponse<BaseWrapper<String>>> retryIdentification(String id) {
        return getApi().retryIdentification(id);
    }
}
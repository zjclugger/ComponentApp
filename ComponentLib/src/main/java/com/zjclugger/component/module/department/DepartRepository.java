package com.zjclugger.component.module.department;

import androidx.lifecycle.LiveData;

import com.zjclugger.lib.BuildConfig;
import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.BaseWebApi;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;

public class DepartRepository {
    private static DepartRepository mInstance;
    private DepartApi mApi;

    public static DepartRepository getInstance() {
        if (mInstance == null) {
            mInstance = new DepartRepository();
        }
        return mInstance;
    }

    private DepartApi getApi() {
        if (null == mApi) {
            mApi = BaseWebApi.instance().getApi(DepartApi.class, BuildConfig.URL_HR);
        }
        return mApi;
    }

    public LiveData<ApiResponse<BaseWrapperEntity<DepartmentPostResult>>> getDepartmentPost(String departmentId) {
        return getApi().getDepartmentPost();
    }
}
package com.zjclugger.component.module.department;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class DepartViewModel extends ViewModel {
    //TODO: for ERP 2.0
    public LiveData<ApiResponse<BaseWrapperEntity<DepartmentPostResult>>> getDepartmentPost(String departmentId) {
        return DepartRepository.getInstance().getDepartmentPost(departmentId);
    }
}

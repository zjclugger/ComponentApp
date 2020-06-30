package com.zjclugger.component.module.upload;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.entity.BaseWrapper;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class FinanceViewModel extends ViewModel {

    /**
     * 删除原始凭证
     *
     * @param id
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<String>>> deleteOriginal(String id) {
        return FinanceRepository.getInstance().deleteOriginal(id);
    }

    /**
     * 重新识别
     *
     * @param id
     * @return
     */
    public LiveData<ApiResponse<BaseWrapper<String>>> retryIdentification(String id) {
        return FinanceRepository.getInstance().retryIdentification(id);
    }
}
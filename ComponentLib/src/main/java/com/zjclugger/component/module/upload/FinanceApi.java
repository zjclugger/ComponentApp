package com.zjclugger.component.module.upload;

import android.util.ArrayMap;

import androidx.lifecycle.LiveData;

import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.entity.BaseWrapper;
import com.zjclugger.lib.business.uploader.UploadFileType;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FinanceApi {

    /**
     * 多图片上传 方式一 （参数注解：@Body；参数类型：MultipartBody）
     *
     * @param params
     * @return
     */
    @UploadFileType
    @POST("increment/upload")
    Observable<Object> uploadOriginal(@Body ArrayMap<String, Object> params);

    @POST("increment/removeIncrement/{id}")
    LiveData<ApiResponse<BaseWrapper<String>>> deleteOriginal(@Path("id") String id);

    @POST("increment/distinguish/{id}")
    LiveData<ApiResponse<BaseWrapper<String>>> retryIdentification(@Path("id") String id);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "increment", hasBody = true)
    LiveData<ApiResponse<BaseWrapper<String>>> deleteOriginal3(@Field("ids") String ids);
}
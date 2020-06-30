package com.zjclugger.buyer.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UploadHelper {
    private volatile static UploadHelper mInstance;
    public static Map<String, RequestBody> params;

    private UploadHelper() {
    }

    //单例模式
    public static UploadHelper getInstance() {
        if (mInstance == null) {
            synchronized (UploadHelper.class) {
                if (mInstance == null)
                    mInstance = new UploadHelper();
                params = new HashMap<>();
            }
        }
        return mInstance;
    }

    //根据传进来的Object对象来判断是String还是File类型的参数
    public UploadHelper addParameter(String key, Object o) {
        RequestBody body = null;
        if (o instanceof String) {
            body = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), (String) o);
        } else if (o instanceof File) {
            body = RequestBody.create(MediaType.parse("multipart/form-data;charset=UTF-8"),
                    (File) o);
        }
        params.put(key, body);
        return this;
    }

    public Map<String, RequestBody> builder() {
        return params;
    }

    /**
     * 清除参数
     */
    public void clear() {
        params.clear();
    }
}
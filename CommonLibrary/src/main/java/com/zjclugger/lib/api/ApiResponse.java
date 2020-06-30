package com.zjclugger.lib.api;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import retrofit2.Response;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ApiResponse<T> {
    private static final String TAG = "ApiResponse";
    public final int code;
    @Nullable
    public final T body;
    @Nullable
    public final String errorMessage;

    public ApiResponse(@NonNull Throwable error) {
        code = 500;
        body = null;
        errorMessage = String.valueOf(error);
    }

    public ApiResponse(@NonNull Response<T> response) {
        code = response.code();
        if (response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            body = null;
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    Log.e(TAG, "error while parsing response");
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            Log.e(TAG, "response error message is " + errorMessage);
        }
    }

    public boolean isSuccess() {
        return code >= 200 && code < 300 && null != body;
    }
}


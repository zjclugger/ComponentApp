package com.zjclugger.lib.api.entity;

import com.google.gson.annotations.SerializedName;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class WrapperEntity {
    public static final int CODE_SUCCESS = 0;
    public static final String MSG_SUCCESS = "成功";

    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return CODE_SUCCESS == code;
    }
}

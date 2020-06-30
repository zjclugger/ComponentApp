package com.zjclugger.lib.api.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 包装JAVA数据类型<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class BaseWrapper<T> extends WrapperEntity {
    @SerializedName("data")
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseWrapperEntity{" +
                " code=" + getCode() +
                ", message='" + getMessage() + '\'' +
                ", result=" + result +
                '}';
    }
}

package com.zjclugger.lib.api.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class BaseWrapperEntities<T extends BaseEntity> extends WrapperEntity {
    @SerializedName("data")
    private List<T> result;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
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

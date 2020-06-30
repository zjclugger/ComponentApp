package com.zjclugger.seller.webapi.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 快递公司 <br>
 * Created by King.Zi on 2020/5/19.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ExpressCompanyResult {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;

    @SerializedName("expressmanList")
    private List<ExpressmanResult> expressmanList=new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ExpressmanResult> getExpressmanList() {
        return expressmanList;
    }

    public void setExpressmanList(List<ExpressmanResult> expressmanList) {
        this.expressmanList = expressmanList;
    }
}

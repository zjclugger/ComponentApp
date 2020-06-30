package com.zjclugger.seller.webapi.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

import java.util.List;

/**
 * 店铺信息<br>
 * Created by King.Zi on 2020/5/14.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ShopInfoResult extends BaseEntity {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("categoryId")
    private int categoryId;
    @SerializedName("categoryName")
    private String categoryName;
    @SerializedName("imageUrl")
    private String shopImageUrl;
    @SerializedName("description")
    private String description;
    @SerializedName("status")
    private String status;
    @SerializedName("shopPhone")
    private String phone;
    @SerializedName("workTime")
    private String workTime;
    @SerializedName("address")
    private String address;
    @SerializedName("qualificationUrls")
    private List<String> qualificationUrls;
    @SerializedName("adImageUrls")
    private List<String> adImageUrls;

    public ShopInfoResult() {
    }

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getShopImageUrl() {
        return shopImageUrl;
    }

    public void setShopImageUrl(String shopImageUrl) {
        this.shopImageUrl = shopImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getQualificationUrls() {
        return qualificationUrls;
    }

    public void setQualificationUrls(List<String> qualificationUrls) {
        this.qualificationUrls = qualificationUrls;
    }

    public List<String> getAdImageUrls() {
        return adImageUrls;
    }

    public void setAdImageUrls(List<String> adImageUrls) {
        this.adImageUrls = adImageUrls;
    }
}
package com.zjclugger.seller.webapi.response;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品<br>
 * Created by King.Zi on 2020/4/15.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class GoodsResult extends BaseEntity {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("categoryId")
    private int categoryId;
    @SerializedName("categoryName")
    private String categoryName;
    @SerializedName("imageUrl")
    private List<String> goodsImageUrl=new ArrayList<>();
    @SerializedName("description")
    private String description;
    @SerializedName("price")
    private double price;
    @SerializedName("stock")
    private int stock;
    @SerializedName("salesVolume")
    private int salesVolume;

    private boolean isSelected;

    public GoodsResult() {
    }

    protected GoodsResult(Parcel in) {
        id = in.readString();
        name = in.readString();
        categoryId = in.readInt();
        categoryName = in.readString();
        goodsImageUrl = in.createStringArrayList();
        description = in.readString();
        price = in.readDouble();
        stock = in.readInt();
        salesVolume = in.readInt();
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(categoryId);
        dest.writeString(categoryName);
        dest.writeStringList(goodsImageUrl);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeInt(stock);
        dest.writeInt(salesVolume);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GoodsResult> CREATOR = new Creator<GoodsResult>() {
        @Override
        public GoodsResult createFromParcel(Parcel in) {
            return new GoodsResult(in);
        }

        @Override
        public GoodsResult[] newArray(int size) {
            return new GoodsResult[size];
        }
    };

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

    public List<String> getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(List<String> goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

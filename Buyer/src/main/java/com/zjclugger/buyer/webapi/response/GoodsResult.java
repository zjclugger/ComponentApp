package com.zjclugger.buyer.webapi.response;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

import java.io.Serializable;

public class GoodsResult extends BaseEntity implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("GoodsImage")
    private String url;
    @SerializedName("Name")
    private String name;
    @SerializedName("Description")
    private String description;
    @SerializedName("price")
    private double price;
    //todo:价格换成BigDecimal
    @SerializedName("floorPrice")
    private double floorPrice;
    @SerializedName("businessName")
    private String businessName;
    @SerializedName("score")
    private float score;   //1位小数
    @SerializedName("monthSalesVolume")
    private int monthSalesVolume;

    //状态
    private boolean isChecked;
    //
    private double primePrice;
    private int count;
    //商品属性
    private String properties;

    public GoodsResult() {
    }

    protected GoodsResult(Parcel in) {
        id = in.readString();
        url = in.readString();
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
        floorPrice = in.readDouble();
        businessName = in.readString();
        score = in.readFloat();
        isChecked = in.readByte() != 0;
        primePrice = in.readDouble();
        count = in.readInt();
        properties = in.readString();
        monthSalesVolume = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeDouble(floorPrice);
        dest.writeString(businessName);
        dest.writeFloat(score);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeDouble(primePrice);
        dest.writeInt(count);
        dest.writeString(properties);
        dest.writeInt(monthSalesVolume);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(double floorPrice) {
        this.floorPrice = floorPrice;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public double getPrimePrice() {
        return primePrice;
    }

    public void setPrimePrice(double primePrice) {
        this.primePrice = primePrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public int getMonthSalesVolume() {
        return monthSalesVolume;
    }

    public void setMonthSalesVolume(int monthSalesVolume) {
        this.monthSalesVolume = monthSalesVolume;
    }
}

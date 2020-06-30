package com.zjclugger.buyer.webapi.response;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 商品分类
 */
public class GoodsCategoryResult extends BaseEntity {
    @SerializedName("id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Description")
    private String description;

    public GoodsCategoryResult() {
    }

    protected GoodsCategoryResult(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
    }

    public static final Creator<GoodsCategoryResult> CREATOR = new Creator<GoodsCategoryResult>() {
        @Override
        public GoodsCategoryResult createFromParcel(Parcel in) {
            return new GoodsCategoryResult(in);
        }

        @Override
        public GoodsCategoryResult[] newArray(int size) {
            return new GoodsCategoryResult[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
    }
}

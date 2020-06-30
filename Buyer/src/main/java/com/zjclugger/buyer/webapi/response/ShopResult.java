package com.zjclugger.buyer.webapi.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class ShopResult extends BaseEntity implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("categoryId")
    private int categoryId;
    @SerializedName("categoryName")
    private String categoryName;
    @SerializedName("imageUrl")
    private List<String> imageUrl;
    @SerializedName("description")
    private String description;
    @SerializedName("floorPrice")
    private double floorPrice;
    @SerializedName("monthSalesVolume")
    private int monthSalesVolume;
    @SerializedName("score")
    private float score;   //1位小数
    @SerializedName("address")
    private String address;
    @SerializedName("detailAddress")
    private String detailAddress;
    @SerializedName("status")
    private String status;
    @SerializedName("Goods")
    private List<GoodsResult> goodsList = new ArrayList<>();
    //Business Hours
    @SerializedName("BusinessHours")
    private String businessHours;
    @SerializedName("GoodsCategory")
    private List<GoodsCategoryResult> goodsCategoryResultList = new ArrayList<>();

    //状态相关
    private boolean isChoosed;
    private boolean isEditor; //自己对该组的编辑状态
    private boolean ActionBarEditor;// 全局对该组的编辑状态
    private int flag;

    public ShopResult() {
    }

    public ShopResult(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected ShopResult(Parcel in) {
        id = in.readString();
        name = in.readString();
        categoryId = in.readInt();
        categoryName = in.readString();
        imageUrl = in.createStringArrayList();
        description = in.readString();
        floorPrice = in.readDouble();
        monthSalesVolume = in.readInt();
        score = in.readFloat();
        address = in.readString();
        detailAddress = in.readString();
        status = in.readString();
        goodsList = in.createTypedArrayList(GoodsResult.CREATOR);
        businessHours = in.readString();
        goodsCategoryResultList = in.createTypedArrayList(GoodsCategoryResult.CREATOR);
        isChoosed = in.readByte() != 0;
        isEditor = in.readByte() != 0;
        ActionBarEditor = in.readByte() != 0;
        flag = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(categoryId);
        dest.writeString(categoryName);
        dest.writeStringList(imageUrl);
        dest.writeString(description);
        dest.writeDouble(floorPrice);
        dest.writeInt(monthSalesVolume);
        dest.writeFloat(score);
        dest.writeString(address);
        dest.writeString(detailAddress);
        dest.writeString(status);
        dest.writeTypedList(goodsList);
        dest.writeString(businessHours);
        dest.writeTypedList(goodsCategoryResultList);
        dest.writeByte((byte) (isChoosed ? 1 : 0));
        dest.writeByte((byte) (isEditor ? 1 : 0));
        dest.writeByte((byte) (ActionBarEditor ? 1 : 0));
        dest.writeInt(flag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopResult> CREATOR = new Creator<ShopResult>() {
        @Override
        public ShopResult createFromParcel(Parcel in) {
            return new ShopResult(in);
        }

        @Override
        public ShopResult[] newArray(int size) {
            return new ShopResult[size];
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

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(double floorPrice) {
        this.floorPrice = floorPrice;
    }

    public int getMonthSalesVolume() {
        return monthSalesVolume;
    }

    public void setMonthSalesVolume(int monthSalesVolume) {
        this.monthSalesVolume = monthSalesVolume;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GoodsResult> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsResult> goodsList) {
        this.goodsList = goodsList;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public List<GoodsCategoryResult> getGoodsCategoryResultList() {
        return goodsCategoryResultList;
    }

    public void setGoodsCategoryResultList(List<GoodsCategoryResult> goodsCategoryResultList) {
        this.goodsCategoryResultList = goodsCategoryResultList;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public boolean isEditor() {
        return isEditor;
    }

    public void setEditor(boolean editor) {
        isEditor = editor;
    }

    public boolean isActionBarEditor() {
        return ActionBarEditor;
    }

    public void setActionBarEditor(boolean actionBarEditor) {
        ActionBarEditor = actionBarEditor;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}

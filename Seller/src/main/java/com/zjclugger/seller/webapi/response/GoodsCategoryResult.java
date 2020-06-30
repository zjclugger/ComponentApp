package com.zjclugger.seller.webapi.response;

import com.contrarywind.interfaces.IPickerViewData;
import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 商品分类<br>
 * Created by King.Zi on 2020/4/15.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class GoodsCategoryResult extends BaseEntity implements IPickerViewData {
    @SerializedName("name")
    private String name;
    @SerializedName("Id")
    private int id;
    @SerializedName("description")
    private String description;
    @SerializedName("goodsCount")
    private int goodsCount;

    private boolean isSelected;

    public GoodsCategoryResult() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int categoryId) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}

package com.zjclugger.seller.webapi.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * 订单中的商品<br>
 * Created by King.Zi on 2020/4/15.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OrderGoodsResult extends BaseEntity {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("goods")
    private GoodsResult goods;
    @SerializedName("count")
    private int count;
    @SerializedName("transactionPrice")
    private BigDecimal transactionPrice;
    @SerializedName("totalAmount")
    private BigDecimal totalAmount;
    @SerializedName("remark")
    private String remark;

    public OrderGoodsResult() {
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

    public GoodsResult getGoods() {
        return goods;
    }

    public void setGoods(GoodsResult goods) {
        this.goods = goods;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(BigDecimal transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

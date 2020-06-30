package com.zjclugger.buyer.persistence.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 购物车@title <br>
 * Created by King.Zi on 2020/5/26.<br>
 * Copyright (c) 2020 zjclugger.com
 */
@Entity(tableName = "shopcart", indices = {@Index(value = "id", unique = true)})
public class Shopcart {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "goods_id")
    private String goodsId;
    @ColumnInfo(name = "goods_name")
    private String goodsName;
    @ColumnInfo(name = "goods_count")
    private int count;
    @ColumnInfo(name = "transaction_price")
    private String transactionPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(String transactionPrice) {
        this.transactionPrice = transactionPrice;
    }
}

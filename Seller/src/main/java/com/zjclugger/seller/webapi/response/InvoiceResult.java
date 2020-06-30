package com.zjclugger.seller.webapi.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

import java.io.Serializable;

/**
 * 发货单
 */
public class InvoiceResult extends BaseEntity implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("orderResult")
    private OrderResult orderInfo;
    @SerializedName("shopInfo")
    private ShopInfoResult shopInfo;
    @SerializedName("ExpressResult")
    private ExpressCompanyResult expressCompany;
    @SerializedName("Expressman")
    private ExpressCompanyResult expressman;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderResult getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderResult orderInfo) {
        this.orderInfo = orderInfo;
    }

    public ShopInfoResult getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfoResult shopInfo) {
        this.shopInfo = shopInfo;
    }

    public ExpressCompanyResult getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(ExpressCompanyResult expressCompany) {
        this.expressCompany = expressCompany;
    }

    public ExpressCompanyResult getExpressman() {
        return expressman;
    }

    public void setExpressman(ExpressCompanyResult expressman) {
        this.expressman = expressman;
    }
}

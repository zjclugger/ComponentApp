package com.zjclugger.seller.webapi.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单信息
 */
public class OrderResult extends BaseEntity implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("orderNO")
    private String orderNO;
    @SerializedName("orderTime")
    private String orderTime;
    @SerializedName("waitPayTime")
    private String waitPayTime;
    @SerializedName("GoodsList")
    private List<OrderGoodsResult> goodsList = new ArrayList<>();
    @SerializedName("customerName")
    private String customerName;
    @SerializedName("customerPhone")
    private String customerPhone;
    @SerializedName("customerAddress")
    private String customerAddress;
    //todo:价格换成BigDecimal
    @SerializedName("totalAmount")
    private double totalAmount;
    @SerializedName("status")
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getWaitPayTime() {
        return waitPayTime;
    }

    public void setWaitPayTime(String waitPayTime) {
        this.waitPayTime = waitPayTime;
    }

    public List<OrderGoodsResult> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OrderGoodsResult> goodsList) {
        this.goodsList = goodsList;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

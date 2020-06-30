package com.zjclugger.finance.webapi.entity.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 购销信息(原始凭证包含X个)
 */
public class OriginalServiceInfoResult extends BaseEntity {

    @SerializedName("id")
    private String id;   //ID
    @SerializedName("serviceName")
    private String serviceName; //货物或应税劳务、服务名称
    @SerializedName("specification")
    private String specification;   //规格型号
    @SerializedName("unit")
    private String unit;    //单位
    @SerializedName("number")
    private int number;
    @SerializedName("unitPrice")
    private double unitPrice;   //单价
    @SerializedName("amount")
    private double amount;
    @SerializedName("taxRate")
    private String taxRate; //税率
    @SerializedName("taxAmount")
    private double taxAmount;   //税额
    @SerializedName("incrementId")
    private String incrementId;

    public OriginalServiceInfoResult() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getIncrementId() {
        return incrementId;
    }

    public void setIncrementId(String incrementId) {
        this.incrementId = incrementId;
    }
}

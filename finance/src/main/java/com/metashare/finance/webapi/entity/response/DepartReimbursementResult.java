package com.zjclugger.finance.webapi.entity.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;
import com.zjclugger.lib.entity.common.ERPListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 本月部门报销统计
 */
public class DepartReimbursementResult extends BaseEntity {
    @SerializedName("name")
    private String orgName;
    @SerializedName("value")
    private Float totalAmount;

    //ordinate
    @SerializedName("ordinate")
    List<DepartReimbursementResult> dataList = new ArrayList<>();

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<DepartReimbursementResult> getDataList() {
        return dataList;
    }

    public void setDataList(List<DepartReimbursementResult> dataList) {
        this.dataList = dataList;
    }

    public List<ERPListItem<Float>> getERPItemList() {
        List<ERPListItem<Float>> erpItemList = new ArrayList<>();
        if (null != dataList && dataList.size() > 0) {
            for (DepartReimbursementResult result : dataList) {
                erpItemList.add(new ERPListItem<Float>(result.getOrgName(),
                        result.getTotalAmount()));
            }
        }
        return erpItemList;
    }
}
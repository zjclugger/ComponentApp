
package com.zjclugger.finance.webapi.entity.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

import java.util.List;

public class ReportAssetsLiabilitiesResult extends BaseEntity {
    @SerializedName("assetsTotal")
    private Double mAssetsTotal;
    @SerializedName("flowAsset")
    private List<ReportNameAmountEntity> mFlowAsset;
    @SerializedName("flowAssetTotal")
    private Double mFlowAssetTotal;
    @SerializedName("flowLiability")
    private List<ReportNameAmountEntity> mFlowLiability;
    @SerializedName("flowLiabilityTotal")
    private Double mFlowLiabilityTotal;
    @SerializedName("liabilityAndOwnerEquityTotal")
    private Double mLiabilityAndOwnerEquityTotal;
    @SerializedName("liabilityTotal")
    private Double mLiabilityTotal;
    @SerializedName("noFlowAsset")
    private List<ReportNameAmountEntity> mNoFlowAsset;
    @SerializedName("noFlowAssetTotal")
    private Double mNoFlowAssetTotal;
    @SerializedName("noFlowLiability")
    private List<ReportNameAmountEntity> mNoFlowLiability;
    @SerializedName("noFlowLiabilityTotal")
    private Double mNoFlowLiabilityTotal;
    @SerializedName("ownerEquity")
    private List<ReportNameAmountEntity> mOwnerEquity;
    @SerializedName("ownerEquityTotal")
    private Double mOwnerEquityTotal;

    public ReportAssetsLiabilitiesResult() {
    }

    public Double getAssetsTotal() {
        return mAssetsTotal;
    }

    public void setAssetsTotal(Double assetsTotal) {
        mAssetsTotal = assetsTotal;
    }

    public List<ReportNameAmountEntity> getFlowAsset() {
        return mFlowAsset;
    }

    public void setFlowAsset(List<ReportNameAmountEntity> flowAsset) {
        mFlowAsset = flowAsset;
    }

    public Double getFlowAssetTotal() {
        return mFlowAssetTotal;
    }

    public void setFlowAssetTotal(Double flowAssetTotal) {
        mFlowAssetTotal = flowAssetTotal;
    }

    public List<ReportNameAmountEntity> getFlowLiability() {
        return mFlowLiability;
    }

    public void setFlowLiability(List<ReportNameAmountEntity> flowLiability) {
        mFlowLiability = flowLiability;
    }

    public Double getFlowLiabilityTotal() {
        return mFlowLiabilityTotal;
    }

    public void setFlowLiabilityTotal(Double flowLiabilityTotal) {
        mFlowLiabilityTotal = flowLiabilityTotal;
    }

    public Double getLiabilityAndOwnerEquityTotal() {
        return mLiabilityAndOwnerEquityTotal;
    }

    public void setLiabilityAndOwnerEquityTotal(Double liabilityAndOwnerEquityTotal) {
        mLiabilityAndOwnerEquityTotal = liabilityAndOwnerEquityTotal;
    }

    public Double getLiabilityTotal() {
        return mLiabilityTotal;
    }

    public void setLiabilityTotal(Double liabilityTotal) {
        mLiabilityTotal = liabilityTotal;
    }

    public List<ReportNameAmountEntity> getNoFlowAsset() {
        return mNoFlowAsset;
    }

    public void setNoFlowAsset(List<ReportNameAmountEntity> noFlowAsset) {
        mNoFlowAsset = noFlowAsset;
    }

    public Double getNoFlowAssetTotal() {
        return mNoFlowAssetTotal;
    }

    public void setNoFlowAssetTotal(Double noFlowAssetTotal) {
        mNoFlowAssetTotal = noFlowAssetTotal;
    }

    public List<ReportNameAmountEntity> getNoFlowLiability() {
        return mNoFlowLiability;
    }

    public void setNoFlowLiability(List<ReportNameAmountEntity> noFlowLiability) {
        mNoFlowLiability = noFlowLiability;
    }

    public Double getNoFlowLiabilityTotal() {
        return mNoFlowLiabilityTotal;
    }

    public void setNoFlowLiabilityTotal(Double noFlowLiabilityTotal) {
        mNoFlowLiabilityTotal = noFlowLiabilityTotal;
    }

    public List<ReportNameAmountEntity> getOwnerEquity() {
        return mOwnerEquity;
    }

    public void setOwnerEquity(List<ReportNameAmountEntity> ownerEquity) {
        mOwnerEquity = ownerEquity;
    }

    public Double getOwnerEquityTotal() {
        return mOwnerEquityTotal;
    }

    public void setOwnerEquityTotal(Double ownerEquityTotal) {
        mOwnerEquityTotal = ownerEquityTotal;
    }
}

package com.zjclugger.finance.webapi.entity.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

public class AccountDetailListResult extends BaseEntity {
    @SerializedName("createTime")
    private String mCreateTime;
    @SerializedName("createUser")
    private String mCreateUser;
    @SerializedName("debtor")
    private Double mDebtor;
    @SerializedName("deleted")
    private Boolean mDeleted;
    @SerializedName("id")
    private String mId;
    @SerializedName("lender")
    private Double mLender;
    @SerializedName("originalCoin")
    private Double mOriginalCoin;
    @SerializedName("subjectCode")
    private String mSubjectCode;
    @SerializedName("subjectId")
    private String mSubjectId;
    @SerializedName("subjectName")
    private String mSubjectName;
    @SerializedName("summary")
    private String mSummary;
    @SerializedName("updateTime")
    private String mUpdateTime;
    @SerializedName("updateUser")
    private String mUpdateUser;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("voucherId")
    private String mVoucherId;

    private int mItemIndex;

    public int getItemIndex() {
        return mItemIndex;
    }

    public void setItemIndex(int itemIndex) {
        mItemIndex = itemIndex;
    }

    public String getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(String createTime) {
        mCreateTime = createTime;
    }

    public String getCreateUser() {
        return mCreateUser;
    }

    public void setCreateUser(String createUser) {
        mCreateUser = createUser;
    }

    public Double getDebtor() {
        return mDebtor;
    }

    public void setDebtor(Double debtor) {
        mDebtor = debtor;
    }

    public Double getLender() {
        return mLender;
    }

    public void setLender(Double lender) {
        mLender = lender;
    }

    public Boolean getDeleted() {
        return mDeleted;
    }

    public void setDeleted(Boolean deleted) {
        mDeleted = deleted;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Double getOriginalCoin() {
        return mOriginalCoin;
    }

    public void setOriginalCoin(Double originalCoin) {
        mOriginalCoin = originalCoin;
    }

    public String getSubjectCode() {
        return mSubjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        mSubjectCode = subjectCode;
    }

    public String getSubjectId() {
        return mSubjectId;
    }

    public void setSubjectId(String subjectId) {
        mSubjectId = subjectId;
    }

    public String getSubjectName() {
        return mSubjectName;
    }

    public void setSubjectName(String subjectName) {
        mSubjectName = subjectName;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        mUpdateTime = updateTime;
    }

    public String getUpdateUser() {
        return mUpdateUser;
    }

    public void setUpdateUser(String updateUser) {
        mUpdateUser = updateUser;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getVoucherId() {
        return mVoucherId;
    }

    public void setVoucherId(String voucherId) {
        mVoucherId = voucherId;
    }
}
package com.zjclugger.buyer.webapi.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

public class UserInfoResult extends BaseEntity {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("orgId")
    private String mOrgId;
    @SerializedName("orgName")
    private String mOrgName;
    @SerializedName("phoneNumber")
    private String mPhoneNumber;
    @SerializedName("sexName")
    private String mSexName;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getOrgId() {
        return mOrgId;
    }

    public void setOrgId(String mOrgId) {
        this.mOrgId = mOrgId;
    }

    public String getOrgName() {
        return mOrgName;
    }

    public void setOrgName(String mOrgName) {
        this.mOrgName = mOrgName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getSexName() {
        return mSexName;
    }

    public void setSexName(String mSexName) {
        this.mSexName = mSexName;
    }
}

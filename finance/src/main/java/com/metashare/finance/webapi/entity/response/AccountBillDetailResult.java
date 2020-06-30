package com.zjclugger.finance.webapi.entity.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 记账凭证详情
 */
public class AccountBillDetailResult extends BaseEntity {

    @SerializedName("auditor")
    private String mAuditor;
    @SerializedName("bookkeeper")
    private String mBookkeeper;
    @SerializedName("credential")
    private String mCredential;
    @SerializedName("credentialNumber")
    private Long mCredentialNumber;
    @SerializedName("credentialWord")
    private String mCredentialWord;
    @SerializedName("id")
    private String mId;
    @SerializedName("period")
    private String mPeriod;
    @SerializedName("periodDate")
    private String mPeriodDate;
    @SerializedName("repulseReason")
    private String mRepulseReason;
    @SerializedName("repulseTime")
    private String mRepulseTime;
    @SerializedName("repulseUser")
    private String mRepulseUser;
    @SerializedName("totalDebtor")
    private Double mTotalDebtor;
    @SerializedName("totalLender")
    private Double mTotalLender;
    @SerializedName("voucherStatus")
    private int mVoucherStatus;
    @SerializedName("detail")
    private List<AccountDetailListResult> detailList = new ArrayList<>();
    @SerializedName("urls")
    private List<String> mUrls = new ArrayList<>();

    public String getAuditor() {
        return mAuditor;
    }

    public void setAuditor(String auditor) {
        mAuditor = auditor;
    }

    public String getBookkeeper() {
        return mBookkeeper;
    }

    public void setBookkeeper(String bookkeeper) {
        mBookkeeper = bookkeeper;
    }

    public String getCredential() {
        return mCredential;
    }

    public void setCredential(String credential) {
        mCredential = credential;
    }

    public Long getCredentialNumber() {
        return mCredentialNumber;
    }

    public void setCredentialNumber(Long credentialNumber) {
        mCredentialNumber = credentialNumber;
    }

    public String getCredentialWord() {
        return mCredentialWord;
    }

    public void setCredentialWord(String credentialWord) {
        mCredentialWord = credentialWord;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getPeriod() {
        return mPeriod;
    }

    public void setPeriod(String period) {
        mPeriod = period;
    }

    public String getPeriodDate() {
        return mPeriodDate;
    }

    public void setPeriodDate(String periodDate) {
        mPeriodDate = periodDate;
    }

    public String getRepulseReason() {
        return mRepulseReason;
    }

    public void setRepulseReason(String repulseReason) {
        mRepulseReason = repulseReason;
    }

    public String getRepulseTime() {
        return mRepulseTime;
    }

    public void setRepulseTime(String repulseTime) {
        mRepulseTime = repulseTime;
    }

    public String getRepulseUser() {
        return mRepulseUser;
    }

    public void setRepulseUser(String repulseUser) {
        mRepulseUser = repulseUser;
    }

    public Double getTotalDebtor() {
        return mTotalDebtor;
    }

    public void setTotalDebtor(Double totalDebtor) {
        mTotalDebtor = totalDebtor;
    }

    public Double getTotalLender() {
        return mTotalLender;
    }

    public void setTotalLender(Double totalLender) {
        mTotalLender = totalLender;
    }

    public int getVoucherStatus() {
        return mVoucherStatus;
    }

    public void setVoucherStatus(int voucherStatus) {
        mVoucherStatus = voucherStatus;
    }

    public List<AccountDetailListResult> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<AccountDetailListResult> detailList) {
        this.detailList = detailList;
    }

    public List<String> getUrls() {
        return mUrls;
    }

    public void setUrls(List<String> urls) {
        mUrls = urls;
    }
}

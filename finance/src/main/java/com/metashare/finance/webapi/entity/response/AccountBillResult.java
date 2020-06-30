package com.zjclugger.finance.webapi.entity.response;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 记账凭证
 */
public class AccountBillResult extends BaseEntity {
    @SerializedName("auditor")
    private String mAuditor;
    @SerializedName("bookkeeper")
    private String mBookkeeper;
    @SerializedName("createTime")
    private String mCreateTime;
    @SerializedName("createUser")
    private String mCreateUser;
    @SerializedName("credentialNumber")
    private Long mCredentialNumber;
    @SerializedName("credentialWord")
    private String mCredentialWord;
    @SerializedName("credential")
    private String mCredential;
    @SerializedName("deleted")
    private Boolean mDeleted;
    @SerializedName("headId")
    private String mHeadId;
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
    @SerializedName("updateTime")
    private String mUpdateTime;
    @SerializedName("updateUser")
    private String mUpdateUser;
    @SerializedName("voucherStatus")
    private int mVoucherStatus;

    protected AccountBillResult(Parcel in) {
        super(in);
        mAuditor = in.readString();
        mBookkeeper = in.readString();
        mCreateTime = in.readString();
        mCreateUser = in.readString();
        if (in.readByte() == 0) {
            mCredentialNumber = null;
        } else {
            mCredentialNumber = in.readLong();
        }
        mCredentialWord = in.readString();
        mCredential = in.readString();
        byte tmpMDeleted = in.readByte();
        mDeleted = tmpMDeleted == 0 ? null : tmpMDeleted == 1;
        mHeadId = in.readString();
        mId = in.readString();
        mPeriod = in.readString();
        mPeriodDate = in.readString();
        mRepulseReason = in.readString();
        mRepulseTime = in.readString();
        mRepulseUser = in.readString();
        if (in.readByte() == 0) {
            mTotalDebtor = null;
        } else {
            mTotalDebtor = in.readDouble();
        }
        if (in.readByte() == 0) {
            mTotalLender = null;
        } else {
            mTotalLender = in.readDouble();
        }
        mUpdateTime = in.readString();
        mUpdateUser = in.readString();
        mVoucherStatus = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mAuditor);
        dest.writeString(mBookkeeper);
        dest.writeString(mCreateTime);
        dest.writeString(mCreateUser);
        if (mCredentialNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mCredentialNumber);
        }
        dest.writeString(mCredentialWord);
        dest.writeString(mCredential);
        dest.writeByte((byte) (mDeleted == null ? 0 : mDeleted ? 1 : 2));
        dest.writeString(mHeadId);
        dest.writeString(mId);
        dest.writeString(mPeriod);
        dest.writeString(mPeriodDate);
        dest.writeString(mRepulseReason);
        dest.writeString(mRepulseTime);
        dest.writeString(mRepulseUser);
        if (mTotalDebtor == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mTotalDebtor);
        }
        if (mTotalLender == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mTotalLender);
        }
        dest.writeString(mUpdateTime);
        dest.writeString(mUpdateUser);
        dest.writeInt(mVoucherStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccountBillResult> CREATOR = new Creator<AccountBillResult>() {
        @Override
        public AccountBillResult createFromParcel(Parcel in) {
            return new AccountBillResult(in);
        }

        @Override
        public AccountBillResult[] newArray(int size) {
            return new AccountBillResult[size];
        }
    };

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

    public String getCredential() {
        return mCredential;
    }

    public void setCredential(String credential) {
        mCredential = credential;
    }

    public Boolean getDeleted() {
        return mDeleted;
    }

    public void setDeleted(Boolean deleted) {
        mDeleted = deleted;
    }

    public String getHeadId() {
        return mHeadId;
    }

    public void setHeadId(String headId) {
        mHeadId = headId;
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

    public int getVoucherStatus() {
        return mVoucherStatus;
    }

    public void setVoucherStatus(int voucherStatus) {
        mVoucherStatus = voucherStatus;
    }
}

package com.zjclugger.finance.webapi.entity.response;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 利润表 <br>
 * Created by King.Zi on 2020/1/8.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ReportProfitResult extends BaseEntity {
    @SerializedName("project")
    private String projectName;
    @SerializedName("number")
    private String number;
    @SerializedName("amount")
    private Double amount;

    protected ReportProfitResult(Parcel in) {
        projectName = in.readString();
        number = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(projectName);
        dest.writeString(number);
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(amount);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReportProfitResult> CREATOR = new Creator<ReportProfitResult>() {
        @Override
        public ReportProfitResult createFromParcel(Parcel in) {
            return new ReportProfitResult(in);
        }

        @Override
        public ReportProfitResult[] newArray(int size) {
            return new ReportProfitResult[size];
        }
    };

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}


package com.zjclugger.oa.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

public class PayrollRecordHeaderResult extends BaseEntity implements Parcelable {
    @SerializedName("ActualAmount")
    private Double actualAmount;
    @SerializedName("Name")
    private String name;
    @SerializedName("SettlementStartTime")
    private String settlementStartTime;
    @SerializedName("SettlementStopTime")
    private String settlementStopTime;
    @SerializedName("TotalAmountDue")
    private Double totalAmountDue;

    protected PayrollRecordHeaderResult(Parcel in) {
        super(in);
        if (in.readByte() == 0) {
            actualAmount = null;
        } else {
            actualAmount = in.readDouble();
        }
        name = in.readString();
        settlementStartTime = in.readString();
        settlementStopTime = in.readString();
        if (in.readByte() == 0) {
            totalAmountDue = null;
        } else {
            totalAmountDue = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        if (actualAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(actualAmount);
        }
        dest.writeString(name);
        dest.writeString(settlementStartTime);
        dest.writeString(settlementStopTime);
        if (totalAmountDue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(totalAmountDue);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PayrollRecordHeaderResult> CREATOR =
            new Creator<PayrollRecordHeaderResult>() {
        @Override
        public PayrollRecordHeaderResult createFromParcel(Parcel in) {
            return new PayrollRecordHeaderResult(in);
        }

        @Override
        public PayrollRecordHeaderResult[] newArray(int size) {
            return new PayrollRecordHeaderResult[size];
        }
    };

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSettlementStartTime() {
        return settlementStartTime;
    }

    public void setSettlementStartTime(String settlementStartTime) {
        this.settlementStartTime = settlementStartTime;
    }

    public String getSettlementStopTime() {
        return settlementStopTime;
    }

    public void setSettlementStopTime(String settlementStopTime) {
        this.settlementStopTime = settlementStopTime;
    }

    public Double getTotalAmountDue() {
        return totalAmountDue;
    }

    public void setTotalAmountDue(Double totalAmountDue) {
        this.totalAmountDue = totalAmountDue;
    }
}

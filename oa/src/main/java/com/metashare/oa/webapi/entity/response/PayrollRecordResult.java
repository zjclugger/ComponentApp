
package com.zjclugger.oa.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

public class PayrollRecordResult extends BaseEntity implements Parcelable {

    @SerializedName("ActualAmount")
    private Double actualAmount;
    @SerializedName("CompanyId")
    private int companyId;
    @SerializedName("Description")
    private String description;
    @SerializedName("EntityStatus")
    private int entityStatus;
    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("OwnerId")
    private int ownerId;
    @SerializedName("PayrollStatus")
    private int payrollStatus;
    @SerializedName("SettlementStartTime")
    private String settlementStartTime;
    @SerializedName("SettlementStopTime")
    private String settlementStopTime;
    @SerializedName("TotalAmountDue")
    private Double totalAmountDue;

    protected PayrollRecordResult(Parcel in) {
        super(in);
        if (in.readByte() == 0) {
            actualAmount = null;
        } else {
            actualAmount = in.readDouble();
        }
        companyId = in.readInt();
        description = in.readString();
        entityStatus = in.readInt();
        id = in.readInt();
        name = in.readString();
        ownerId = in.readInt();
        payrollStatus = in.readInt();
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
        dest.writeInt(companyId);
        dest.writeString(description);
        dest.writeInt(entityStatus);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(ownerId);
        dest.writeInt(payrollStatus);
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

    public static final Creator<PayrollRecordResult> CREATOR = new Creator<PayrollRecordResult>() {
        @Override
        public PayrollRecordResult createFromParcel(Parcel in) {
            return new PayrollRecordResult(in);
        }

        @Override
        public PayrollRecordResult[] newArray(int size) {
            return new PayrollRecordResult[size];
        }
    };

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(int entityStatus) {
        this.entityStatus = entityStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getPayrollStatus() {
        return payrollStatus;
    }

    public void setPayrollStatus(int payrollStatus) {
        this.payrollStatus = payrollStatus;
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

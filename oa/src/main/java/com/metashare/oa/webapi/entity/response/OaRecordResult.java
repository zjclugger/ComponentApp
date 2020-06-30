package com.zjclugger.oa.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

public class OaRecordResult extends BaseEntity implements Parcelable {
    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("PayableWages")
    private double payableWages;
    @SerializedName("RealWage")
    private double realWages;

    protected OaRecordResult(Parcel in) {
        super(in);
        id = in.readInt();
        name = in.readString();
        payableWages = in.readDouble();
        realWages = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(payableWages);
        dest.writeDouble(realWages);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OaRecordResult> CREATOR = new Creator<OaRecordResult>() {
        @Override
        public OaRecordResult createFromParcel(Parcel in) {
            return new OaRecordResult(in);
        }

        @Override
        public OaRecordResult[] newArray(int size) {
            return new OaRecordResult[size];
        }
    };

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

    public double getPayableWages() {
        return payableWages;
    }

    public void setPayableWages(double payableWages) {
        this.payableWages = payableWages;
    }

    public double getRealWages() {
        return realWages;
    }

    public void setRealWages(double realWages) {
        this.realWages = realWages;
    }
}

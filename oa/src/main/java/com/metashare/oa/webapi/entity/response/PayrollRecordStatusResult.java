package com.zjclugger.oa.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

public class PayrollRecordStatusResult extends BaseEntity implements Parcelable {
    @SerializedName("Selected")
    private String selected;
    @SerializedName("Text")
    private String text;
    @SerializedName("Value")
    private String value;

    protected PayrollRecordStatusResult(Parcel in) {
        super(in);
        selected = in.readString();
        text = in.readString();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(selected);
        dest.writeString(text);
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PayrollRecordStatusResult> CREATOR =
            new Creator<PayrollRecordStatusResult>() {
                @Override
                public PayrollRecordStatusResult createFromParcel(Parcel in) {
                    return new PayrollRecordStatusResult(in);
                }

                @Override
                public PayrollRecordStatusResult[] newArray(int size) {
                    return new PayrollRecordStatusResult[size];
                }
            };

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

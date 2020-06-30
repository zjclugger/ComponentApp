
package com.zjclugger.oa.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 班次<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceShiftsResult extends BaseEntity implements Parcelable {
    private int id;
    private String name;

    public AttendanceShiftsResult() {
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

    protected AttendanceShiftsResult(Parcel in) {
        super(in);
        id = in.readInt();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AttendanceShiftsResult> CREATOR =
            new Creator<AttendanceShiftsResult>() {
                @Override
                public AttendanceShiftsResult createFromParcel(Parcel in) {
                    return new AttendanceShiftsResult(in);
                }

                @Override
                public AttendanceShiftsResult[] newArray(int size) {
                    return new AttendanceShiftsResult[size];
                }
            };
}

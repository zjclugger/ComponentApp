
package com.zjclugger.oa.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 有效范围<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceRangeResult extends BaseEntity implements Parcelable {
    private int id;
    private String name;

    public AttendanceRangeResult() {
    }

    protected AttendanceRangeResult(Parcel in) {
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

    public static final Creator<AttendanceRangeResult> CREATOR =
            new Creator<AttendanceRangeResult>() {
                @Override
                public AttendanceRangeResult createFromParcel(Parcel in) {
                    return new AttendanceRangeResult(in);
                }

                @Override
                public AttendanceRangeResult[] newArray(int size) {
                    return new AttendanceRangeResult[size];
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
}

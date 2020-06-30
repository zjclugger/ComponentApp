
package com.zjclugger.oa.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 考勤班次<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceDateResult extends BaseEntity implements Parcelable {
    private int id;
    private String name;
    private boolean selected;

    public AttendanceDateResult() {
    }

    protected AttendanceDateResult(Parcel in) {
        super(in);
        id = in.readInt();
        name = in.readString();
        selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AttendanceDateResult> CREATOR =
            new Creator<AttendanceDateResult>() {
                @Override
                public AttendanceDateResult createFromParcel(Parcel in) {
                    return new AttendanceDateResult(in);
                }

                @Override
                public AttendanceDateResult[] newArray(int size) {
                    return new AttendanceDateResult[size];
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

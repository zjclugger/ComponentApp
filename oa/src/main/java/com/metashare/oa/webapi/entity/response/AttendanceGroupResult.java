
package com.zjclugger.oa.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 考勤组<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceGroupResult extends BaseEntity implements Parcelable {
    private int id;
    private String name;
    private int memberCount;
    private String shiftsName;
    private String shiftsPlace;

    public AttendanceGroupResult() {
    }

    protected AttendanceGroupResult(Parcel in) {
        super(in);
        id = in.readInt();
        name = in.readString();
        memberCount = in.readInt();
        shiftsName = in.readString();
        shiftsPlace = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(memberCount);
        dest.writeString(shiftsName);
        dest.writeString(shiftsPlace);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AttendanceGroupResult> CREATOR =
            new Creator<AttendanceGroupResult>() {
                @Override
                public AttendanceGroupResult createFromParcel(Parcel in) {
                    return new AttendanceGroupResult(in);
                }

                @Override
                public AttendanceGroupResult[] newArray(int size) {
                    return new AttendanceGroupResult[size];
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

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public String getShiftsName() {
        return shiftsName;
    }

    public void setShiftsName(String shiftsName) {
        this.shiftsName = shiftsName;
    }

    public String getShiftsPlace() {
        return shiftsPlace;
    }

    public void setShiftsPlace(String shiftsPlace) {
        this.shiftsPlace = shiftsPlace;
    }
}

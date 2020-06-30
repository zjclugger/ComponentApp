
package com.zjclugger.oa.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 考勤地点<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendancePlaceResult extends BaseEntity implements Parcelable {
    private int id;
    private String name;
    private String place;

    public AttendancePlaceResult() {
    }

    protected AttendancePlaceResult(Parcel in) {
        super(in);
        id = in.readInt();
        name = in.readString();
        place = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(place);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AttendancePlaceResult> CREATOR =
            new Creator<AttendancePlaceResult>() {
                @Override
                public AttendancePlaceResult createFromParcel(Parcel in) {
                    return new AttendancePlaceResult(in);
                }

                @Override
                public AttendancePlaceResult[] newArray(int size) {
                    return new AttendancePlaceResult[size];
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}

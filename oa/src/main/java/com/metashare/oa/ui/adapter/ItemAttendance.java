
package com.zjclugger.oa.ui.adapter;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ItemAttendance implements Parcelable {
    private int itemIcon;
    private int itemText;
    private String type;

    public ItemAttendance(@DrawableRes int icon, @StringRes int text, String attendanceType) {
        itemIcon = icon;
        itemText = text;
        type = attendanceType;
    }

    protected ItemAttendance(Parcel in) {
        itemIcon = in.readInt();
        itemText = in.readInt();
        type = in.readString();
    }

    public static final Creator<ItemAttendance> CREATOR = new Creator<ItemAttendance>() {
        @Override
        public ItemAttendance createFromParcel(Parcel in) {
            return new ItemAttendance(in);
        }

        @Override
        public ItemAttendance[] newArray(int size) {
            return new ItemAttendance[size];
        }
    };

    public int getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(@DrawableRes int itemIcon) {
        this.itemIcon = itemIcon;
    }

    public int getItemText() {
        return itemText;
    }

    public void setItemText(@StringRes int itemText) {
        this.itemText = itemText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(itemIcon);
        dest.writeInt(itemText);
        dest.writeString(type);
    }
}

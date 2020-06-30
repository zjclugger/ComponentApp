package com.zjclugger.lib.entity.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class JChoiceItem implements Parcelable {
    private String text;
    private boolean selected;

    public JChoiceItem() {
    }

    public JChoiceItem(String text) {
        this.text = text;
    }

    public JChoiceItem(String text, boolean isSelected) {
        this.text = text;
        this.selected = isSelected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    protected JChoiceItem(Parcel in) {
        text = in.readString();
        selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JChoiceItem> CREATOR = new Creator<JChoiceItem>() {
        @Override
        public JChoiceItem createFromParcel(Parcel in) {
            return new JChoiceItem(in);
        }

        @Override
        public JChoiceItem[] newArray(int size) {
            return new JChoiceItem[size];
        }
    };
}

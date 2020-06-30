package com.zjclugger.oa.ui.adapter;

import android.os.Parcel;
import android.os.Parcelable;

import com.zjclugger.lib.entity.common.ERPListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceStatistics implements Parcelable {
    private int id;
    private String text;
    private String value;
    private boolean selected;
    private List<ERPListItem> itemList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<ERPListItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ERPListItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}

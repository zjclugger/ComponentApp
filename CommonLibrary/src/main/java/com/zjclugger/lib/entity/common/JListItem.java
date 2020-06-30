package com.zjclugger.lib.entity.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.ArrayList;
import java.util.List;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class JListItem<T> implements Parcelable, IPickerViewData {
    private int id;
    private String text;
    private T value;
    private boolean selected;
    private int icon = -1;
    private Object tag;
    private List<JListItem<T>> childItemList = new ArrayList<>();

    public JListItem(int id, String text, T value, boolean selected, int icon, Object tag) {
        this.id = id;
        this.text = text;
        this.value = value;
        this.icon = icon;
        this.tag = tag;
    }

    public JListItem(int id, String text, T value, int icon, Object tag) {
        this(id, text, value, false, icon, tag);
    }

    public JListItem(int id, String text, T value, boolean selected) {
        this(id, text, value, selected, -1, null);
    }

    public JListItem(int id, String text, T value) {
        this(id, text, value, false, -1, null);
    }

    public JListItem(String text, T value) {
        this(-1, text, value, false, -1, null);
    }

    public JListItem(String text) {
        this.text = text;
    }

    public JListItem() {
    }

    public JListItem(int id, String text) {
        this.id = id;
        this.text = text;
    }

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

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public List<JListItem<T>> getChildItemList() {
        return childItemList;
    }

    public void setChildItemList(List<JListItem<T>> childItemList) {
        this.childItemList = childItemList;
    }

    protected JListItem(Parcel in) {
        id = in.readInt();
        text = in.readString();
        selected = in.readByte() != 0;
        icon = in.readInt();
    }

    public static final Creator<JListItem> CREATOR = new Creator<JListItem>() {
        @Override
        public JListItem createFromParcel(Parcel in) {
            return new JListItem(in);
        }

        @Override
        public JListItem[] newArray(int size) {
            return new JListItem[size];
        }
    };

    @Override
    public String getPickerViewText() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeInt(icon);
        dest.writeTypedList(childItemList);
    }
}

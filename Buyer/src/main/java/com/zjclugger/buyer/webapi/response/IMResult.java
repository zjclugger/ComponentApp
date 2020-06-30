package com.zjclugger.buyer.webapi.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zjclugger.buyer.ui.im.SlideView;
import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 聊天记录<br>
 * Created by King.Zi on 2020/5/11.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class IMResult extends BaseEntity implements Parcelable {
    private String id;
    private String headImageUrl;
    private String name;
    private String dateTime;
    private int newCount;
    private String message;
    private SlideView slideView;
    private boolean isUserMessage;

    public IMResult() {
    }

    protected IMResult(Parcel in) {
        id = in.readString();
        headImageUrl = in.readString();
        name = in.readString();
        dateTime = in.readString();
        newCount = in.readInt();
        message = in.readString();
        isUserMessage = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(headImageUrl);
        dest.writeString(name);
        dest.writeString(dateTime);
        dest.writeInt(newCount);
        dest.writeString(message);
        dest.writeByte((byte) (isUserMessage ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IMResult> CREATOR = new Creator<IMResult>() {
        @Override
        public IMResult createFromParcel(Parcel in) {
            return new IMResult(in);
        }

        @Override
        public IMResult[] newArray(int size) {
            return new IMResult[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SlideView getSlideView() {
        return slideView;
    }

    public void setSlideView(SlideView slideView) {
        this.slideView = slideView;
    }

    public boolean isUserMessage() {
        return isUserMessage;
    }

    public void setUserMessage(boolean userMessage) {
        isUserMessage = userMessage;
    }
}
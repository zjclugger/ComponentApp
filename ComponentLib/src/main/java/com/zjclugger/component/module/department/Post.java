package com.zjclugger.component.module.department;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @title <br>
 * Created by King.Zi on 2020/3/5.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class Post implements Parcelable {
    @SerializedName("postName")
    private String postName;
    @SerializedName("postLevel")
    private String postLevel;
    @SerializedName("remark")
    private String remark;
    @SerializedName("orgId")
    private String orgId;

    public Post() {
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostLevel() {
        return postLevel;
    }

    public void setPostLevel(String postLevel) {
        this.postLevel = postLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    protected Post(Parcel in) {
        postName = in.readString();
        postLevel = in.readString();
        remark = in.readString();
        orgId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postName);
        dest.writeString(postLevel);
        dest.writeString(remark);
        dest.writeString(orgId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}

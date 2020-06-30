package com.zjclugger.component.module.upload.uploader.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 原始凭证
 */
public class UploadOriginalResult extends BaseEntity implements Parcelable {

    @SerializedName("file")
    private String fileName; //票据名称
    @SerializedName("status")
    private String status;
    @SerializedName("id")
    private String id;

    public UploadOriginalResult() {

    }

    protected UploadOriginalResult(Parcel in) {
        super(in);
        fileName = in.readString();
        status = in.readString();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(fileName);
        dest.writeString(status);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UploadOriginalResult> CREATOR =
            new Creator<UploadOriginalResult>() {
                @Override
                public UploadOriginalResult createFromParcel(Parcel in) {
                    return new UploadOriginalResult(in);
                }

                @Override
                public UploadOriginalResult[] newArray(int size) {
                    return new UploadOriginalResult[size];
                }
            };

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

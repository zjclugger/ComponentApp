package com.zjclugger.lib.business.uploader.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 上传文件对象 <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class UploadFile implements Parcelable {
    private String fileName;
    private String filePath;
    private int progress;

    public UploadFile(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    protected UploadFile(Parcel in) {
        fileName = in.readString();
        filePath = in.readString();
        progress = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileName);
        dest.writeString(filePath);
        dest.writeInt(progress);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UploadFile> CREATOR = new Creator<UploadFile>() {
        @Override
        public UploadFile createFromParcel(Parcel in) {
            return new UploadFile(in);
        }

        @Override
        public UploadFile[] newArray(int size) {
            return new UploadFile[size];
        }
    };

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}

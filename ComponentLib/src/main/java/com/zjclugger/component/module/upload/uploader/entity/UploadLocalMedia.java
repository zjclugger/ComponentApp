package com.zjclugger.component.module.upload.uploader.entity;

import android.text.TextUtils;

import com.luck.picture.lib.entity.LocalMedia;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class UploadLocalMedia extends LocalMedia {
    public static final String RESULT_SUCCESS = "success";
    private String fileName;
    private String fileId;
    private String status;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIdentified() {
        return !TextUtils.isEmpty(status) && status.equalsIgnoreCase(RESULT_SUCCESS);
    }

}

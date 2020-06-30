package com.zjclugger.component.module.upload.uploader.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.zjclugger.lib.api.entity.BaseWrapperEntities;

/**
 * 原始凭证
 */
public class UploadOriginalResultWrapper extends BaseWrapperEntities<UploadOriginalResult> implements Parcelable {

    protected UploadOriginalResultWrapper(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UploadOriginalResultWrapper> CREATOR = new Creator<UploadOriginalResultWrapper>() {
        @Override
        public UploadOriginalResultWrapper createFromParcel(Parcel in) {
            return new UploadOriginalResultWrapper(in);
        }

        @Override
        public UploadOriginalResultWrapper[] newArray(int size) {
            return new UploadOriginalResultWrapper[size];
        }
    };
}

package com.zjclugger.lib.api.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.InverseMethod;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;

import com.zjclugger.lib.utils.CommonUtils;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class BaseEntity implements Parcelable, Observable {

    private transient PropertyChangeRegistry mCallbacks;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public BaseEntity() {
    }

    protected BaseEntity(Parcel in) {
    }

    public static final Creator<BaseEntity> CREATOR = new Creator<BaseEntity>() {
        @Override
        public BaseEntity createFromParcel(Parcel in) {
            return new BaseEntity(in);
        }

        @Override
        public BaseEntity[] newArray(int size) {
            return new BaseEntity[size];
        }
    };

    @Override
    public void addOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.remove(callback);
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    public void notifyChange() {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, 0, null);
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with {@link Bindable} to generate a field in
     * <code>BR</code> to be used as <code>fieldId</code>.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    public void notifyPropertyChanged(int fieldId) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, fieldId, null);
    }

    @InverseMethod("convertDoubleToString")
    public double convertToDouble(String value) {
        return TextUtils.isEmpty(value) ? 0 : Double.parseDouble(value);
    }

    public String convertDoubleToString(double value) {
        return String.valueOf(value);
    }

    @InverseMethod("convertIntToString")
    public int convertToInt(String value) {
        return TextUtils.isEmpty(value) ? 0 : Integer.parseInt(value);
    }

    public String convertIntToString(int value) {
        return String.valueOf(value);
    }

    @InverseMethod("convertDateToString")
    public String convertToDate(String value) {
        return CommonUtils.getDate(value);
    }

    public String convertDateToString(String value) {
        return CommonUtils.getDate(value);
    }
}

package com.zjclugger.finance.webapi.entity.response;

import com.contrarywind.interfaces.IPickerViewData;
import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

public class BillTypeResult extends BaseEntity implements IPickerViewData {
    @SerializedName("createTime")
    private String mCreateTime;
    @SerializedName("createUser")
    private String mCreateUser;
    @SerializedName("deleted")
    private Boolean mDeleted;
    @SerializedName("dictCode")
    private String mDictCode;
    @SerializedName("dictDesc")
    private String mDictDesc;
    @SerializedName("dictName")
    private String mDictName;
    @SerializedName("id")
    private String mId;
    @SerializedName("showOrNot")
    private String mShowOrNot;
    @SerializedName("sort")
    private String mSort;
    @SerializedName("typeId")
    private String mTypeId;
    @SerializedName("updateTime")
    private String mUpdateTime;
    @SerializedName("updateUser")
    private String mUpdateUser;

    public String getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(String createTime) {
        mCreateTime = createTime;
    }

    public String getCreateUser() {
        return mCreateUser;
    }

    public void setCreateUser(String createUser) {
        mCreateUser = createUser;
    }

    public Boolean getDeleted() {
        return mDeleted;
    }

    public void setDeleted(Boolean deleted) {
        mDeleted = deleted;
    }

    public String getDictCode() {
        return mDictCode;
    }

    public void setDictCode(String dictCode) {
        mDictCode = dictCode;
    }

    public String getDictDesc() {
        return mDictDesc;
    }

    public void setDictDesc(String dictDesc) {
        mDictDesc = dictDesc;
    }

    public String getDictName() {
        return mDictName;
    }

    public void setDictName(String dictName) {
        mDictName = dictName;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getShowOrNot() {
        return mShowOrNot;
    }

    public void setShowOrNot(String showOrNot) {
        mShowOrNot = showOrNot;
    }

    public String getSort() {
        return mSort;
    }

    public void setSort(String sort) {
        mSort = sort;
    }

    public String getTypeId() {
        return mTypeId;
    }

    public void setTypeId(String typeId) {
        mTypeId = typeId;
    }

    public String getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        mUpdateTime = updateTime;
    }

    public String getUpdateUser() {
        return mUpdateUser;
    }

    public void setUpdateUser(String updateUser) {
        mUpdateUser = updateUser;
    }

    @Override
    public String getPickerViewText() {
        return mDictName;
    }
}

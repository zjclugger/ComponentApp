package com.zjclugger.finance.webapi.entity.response;

import com.contrarywind.interfaces.IPickerViewData;
import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 企业用户
 */
public class OrganizationUserResult extends BaseEntity implements IPickerViewData {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("loginName")
    private String loginName;

    public OrganizationUserResult(String id, String name, String loginName) {
        this.id = id;
        this.name = name;
        this.loginName = loginName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String getPickerViewText() {
        return loginName;
    }
}

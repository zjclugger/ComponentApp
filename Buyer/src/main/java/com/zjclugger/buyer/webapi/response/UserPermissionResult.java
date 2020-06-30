package com.zjclugger.buyer.webapi.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

public class UserPermissionResult extends BaseEntity {
    @SerializedName("id")
    private String id;
    @SerializedName("permissionStr")
    private String permission;
    @SerializedName("permissionDesc")
    private String permissionDescription;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }
}

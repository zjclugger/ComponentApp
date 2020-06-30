package com.zjclugger.lib.business;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Parcelable {
    private String id;
    private String uid;
    private String userName;
    private String userPassword;
    private String companyId;
    private String companyName;
    private List<String> roles;
    private String authCode = "";
    private String token = "";
    private boolean hasAccountSet;
    private Map<String, String> permissionMap = new HashMap<>();

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isHasAccountSet() {
        return hasAccountSet;
    }

    public void setHasAccountSet(boolean hasAccountSet) {
        this.hasAccountSet = hasAccountSet;
    }

    public Map<String, String> getPermissionMap() {
        return permissionMap;
    }

    public void clearPermissionMap() {
        if (null != permissionMap && permissionMap.size() > 0) {
            permissionMap.clear();
        }
    }

    public void setPermissionMap(Map<String, String> permissionMap) {
        this.permissionMap = permissionMap;
    }

    public boolean hasPermission(String permission) {
        return null != permissionMap && permissionMap.containsKey(permission);
    }

    public List<UserPermission> hasPermission(List<UserPermission> permissionList) {
        if (null != permissionMap && null != permissionList && permissionList.size() > 0) {
            for (int i = 0; i < permissionList.size(); i++) {
                permissionList.get(i).setOwn(permissionMap.containsKey(permissionList.get(i).getName()));
            }
        }
        return permissionList;
    }

    /**
     * 暂时用是否【包含】相关的权限字符串来判断是否有相应的权限，
     * 因后台并未给出准确的菜单权限字符串，如果给出则可以使用【hasPermission】判断
     *
     * @param permissionList
     * @return
     */
    public List<UserPermission> hasMenuPermission(List<UserPermission> permissionList) {
        //TODO:暂时用是否【包含】相关的权限字符串来判断是否有相应的权限，
        if (null != permissionMap && null != permissionList && permissionList.size() > 0) {
            boolean isOwn;
            for (int i = 0; i < permissionList.size(); i++) {
                isOwn = false;
                for (String permission : permissionMap.keySet()) {
                  if (permission.indexOf(permissionList.get(i).getName()) > -1) {
                        isOwn = true;
                        break;
                    }
                }
                permissionList.get(i).setOwn(isOwn);
            }
        }

        return permissionList;
    }

    protected User(Parcel in) {
        id = in.readString();
        uid = in.readString();
        userName = in.readString();
        userPassword = in.readString();
        companyId = in.readString();
        companyName = in.readString();
        roles = in.createStringArrayList();
        authCode = in.readString();
        token = in.readString();
        hasAccountSet = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(uid);
        dest.writeString(userName);
        dest.writeString(userPassword);
        dest.writeString(companyId);
        dest.writeString(companyName);
        dest.writeStringList(roles);
        dest.writeString(authCode);
        dest.writeString(token);
        dest.writeByte((byte) (hasAccountSet ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
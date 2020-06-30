package com.zjclugger.seller.webapi.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class UserLoginResult extends BaseEntity {
    @SerializedName("userInfo")
    private UserInfoResult userInfo;
    @SerializedName("authCode")
    private String authCode;
    @SerializedName("session")
    private String session;
    @SerializedName("accountSet")
    private boolean accountSet;

    public UserInfoResult getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoResult userInfo) {
        this.userInfo = userInfo;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public boolean hasAccountSet() {
        return accountSet;
    }

    public void setAccountSet(boolean accountSet) {
        this.accountSet = accountSet;
    }
}

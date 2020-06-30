package com.zjclugger.seller.business;

import com.zjclugger.lib.base.BaseApplication;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.SharedPreferencesUtils;

public class PreferencesUtil {
    private static final String FILE_NAME = "erp_portal";
    private SharedPreferencesUtils mSharedPreferences;

    private static class SingletonHolder {
        private static PreferencesUtil instance = new PreferencesUtil();
    }

    public static PreferencesUtil getInstance() {
        return SingletonHolder.instance;
    }

    private PreferencesUtil() {
        if (mSharedPreferences == null) {
            mSharedPreferences = new SharedPreferencesUtils(BaseApplication.getInstance(),
                    FILE_NAME);
        }
    }

    public void put(String key, Object value) {
        mSharedPreferences.put(key, value);
    }

    public Object get(String key, Object defaultValue) {
        return mSharedPreferences.get(key, defaultValue);
    }

    public void putLoginParameters(String username, String password, String token) {
        put(Constants.Preferences.KEY_LOGIN_TOKEN, token);
        put(Constants.Preferences.KEY_LOGIN_USER, username);
        put(Constants.Preferences.KEY_LOGIN_PASSWORD, password);
    }

    public String getToken() {
        return (String) get(Constants.Preferences.KEY_LOGIN_TOKEN, "");
    }

    public String getLoginCompany() {
        return (String) get(Constants.Preferences.KEY_LOGIN_COMPANY, "");
    }

    public String getLoginUser() {
        return (String) get(Constants.Preferences.KEY_LOGIN_USER, "");
    }

    public String getLoginUserPassword() {
        return (String) get(Constants.Preferences.KEY_LOGIN_PASSWORD, "");
    }
}

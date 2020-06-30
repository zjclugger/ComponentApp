
package com.zjclugger.lib.business;

/**
 * 用户管理器<br>
 * 实现所有与用户相关的操作
 *
 * @author King.Zi
 * @Title: UserManager
 * @Description:
 */
public class UserManager {
    private User mCurrentUser;

    private static class SingletonHolder {
        private static UserManager instance = new UserManager();
    }

    private UserManager() {
        if (null == mCurrentUser) {
            mCurrentUser = new User();
        }
    }

    public static UserManager getInstance() {
        return SingletonHolder.instance;
    }

    public void setCurrentUser(User user) {
        mCurrentUser = null;
        mCurrentUser = user;
    }

    public void clear() {
        mCurrentUser = new User();
    }

    public User getCurrentUser() {
        return mCurrentUser;
    }

    public String getCurrentUserId() {
        if (mCurrentUser != null) {
            return mCurrentUser.getUid();
        }
        return "";
    }

    public String getCurrentCompanyId() {
        if (mCurrentUser != null) {
            return mCurrentUser.getCompanyId();
        }
        return "";
    }

    public String getToken() {
        if (mCurrentUser != null) {
            return mCurrentUser.getToken();
        }

        return "";
    }

    public boolean hasAccountSet() {
        if (mCurrentUser != null) {
            return mCurrentUser.isHasAccountSet();
        }

        return false;
    }

    public String getAuthorizationCode() {
        if (mCurrentUser != null) {
            return mCurrentUser.getAuthCode();
        }

        return "";
    }


    /**
     * 刷新当前用户
     *
     * @param user
     * @param loginStatus
     */
    public void refreshUser(User user, boolean loginStatus) {
        if (null == mCurrentUser) {
            mCurrentUser = new User();
        }
/*
        mCurrentUser.setUserId(user.getUserId());
        mCurrentUser.setUserName(user.getUserName());
        mCurrentUser.setPassword(user.getPassword());
        mCurrentUser.setLoginStatus(loginStatus);
      */
    }

}
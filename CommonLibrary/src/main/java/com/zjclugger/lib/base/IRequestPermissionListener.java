package com.zjclugger.lib.base;

import com.zjclugger.lib.business.UserPermission;

import java.util.List;

/**
 * @title <br>
 * Created by King.Zi on 2020/3/3.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public interface IRequestPermissionListener {
    /**
     * (当前页面)权限列表
     *
     * @return
     */
    List<UserPermission> getPermissionList();

    /**
     * 是否为公有界面
     * @return
     */
    boolean isPublicPermission();

    boolean noPermission();

    boolean isAllPermission();

    void controlViewsByPermission();
}

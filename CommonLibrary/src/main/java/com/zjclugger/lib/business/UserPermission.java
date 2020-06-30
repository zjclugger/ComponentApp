package com.zjclugger.lib.business;

import android.view.Menu;
import android.view.View;

import androidx.annotation.IdRes;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户权限<br>
 * Created by King.Zi on 2020/3/2.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class UserPermission {
    private String name;
    private String description;
    private boolean isOwn;
    private boolean isAll;
    @IdRes
    private List<Integer> viewIdList=new ArrayList<>();

    public UserPermission() {
    }

    public UserPermission(String name) {
        this.name = name;
    }

    public UserPermission(String name, @IdRes int... viewIds) {
        this.name = name;
        if (null != viewIdList) {
            viewIdList.clear();
        }
        for (int i = 0; i < viewIds.length; i++) {
            viewIdList.add(viewIds[i]);
        }
    }

    public UserPermission(String name, @IdRes List<Integer> viewIdList) {
        this.name = name;
        this.viewIdList = viewIdList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOwn() {
        return isOwn;
    }

    public void setOwn(boolean own) {
        isOwn = own;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    public List<Integer> getViewIdList() {
        return viewIdList;
    }

    public void setViewIdList(List<Integer> viewIdList) {
        this.viewIdList = viewIdList;
    }

    public void controlView(View rootView) {
        if (null != viewIdList && viewIdList.size() > 0) {
            for (int i = 0; i < viewIdList.size(); i++) {
                rootView.findViewById(viewIdList.get(i)).setVisibility(isOwn ? View.VISIBLE :
                        View.GONE);
            }
        }
    }

    public void controlMenu(Menu menu) {
        if (null != viewIdList && viewIdList.size() > 0) {
            for (int i = 0; i < viewIdList.size(); i++) {
                for(int y=0;y<menu.size();y++){
                    if(viewIdList.get(i)==menu.getItem(y).getItemId()){
                        menu.getItem(y).setVisible(isOwn);
                    }
                }
            }
        }
    }
}

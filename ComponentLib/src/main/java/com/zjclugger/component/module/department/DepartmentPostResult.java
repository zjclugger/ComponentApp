package com.zjclugger.component.module.department;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @title <br>
 * Created by King.Zi on 2020/3/5.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class DepartmentPostResult extends BaseEntity {
    @SerializedName("id")
    private String id;
    @SerializedName("parentId")
    private String parentId;
    @SerializedName("orgName")
    private String orgName;
    @SerializedName("children")
    private List<DepartmentPostResult> childrenList;
    @SerializedName("post")
    private List<Post> postList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<DepartmentPostResult> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<DepartmentPostResult> childrenList) {
        this.childrenList = childrenList;
    }

    public List<Post> getPostList() {
        if (null == postList) {
            postList = new ArrayList<>();
        }
        return postList;
    }

    public boolean hasDepartmentOrPost() {
        return (null != childrenList && childrenList.size() > 0) || (null != postList && postList.size() > 0);
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }
}

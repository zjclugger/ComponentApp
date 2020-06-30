package com.zjclugger.component.module.department;

import android.os.Parcel;
import android.os.Parcelable;

public class DepartPostEntity implements Parcelable {
    private String id;
    private String name;
    private String parentId;   // 0,0表示父节点
    private boolean isNode;     //0,是否是叶子节点   1为叶子节点
    //private int displayOrder; //1,同一个级别的显示顺序
    private boolean isExpand = false;  //是否展开了
    private boolean isSelected = false; //是否选中了
    private boolean hasDepartment;   //是否有子部门

    public DepartPostEntity(String id, String name, String parentId, boolean isNode,
                            boolean hasDepartment) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.isNode = isNode;
        this.hasDepartment = hasDepartment;
    }

    protected DepartPostEntity(Parcel in) {
        id = in.readString();
        name = in.readString();
        parentId = in.readString();
        isNode = in.readByte() != 0;
        isExpand = in.readByte() != 0;
        isSelected = in.readByte() != 0;
        hasDepartment = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(parentId);
        dest.writeByte((byte) (isNode ? 1 : 0));
        dest.writeByte((byte) (isExpand ? 1 : 0));
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeByte((byte) (hasDepartment ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DepartPostEntity> CREATOR = new Creator<DepartPostEntity>() {
        @Override
        public DepartPostEntity createFromParcel(Parcel in) {
            return new DepartPostEntity(in);
        }

        @Override
        public DepartPostEntity[] newArray(int size) {
            return new DepartPostEntity[size];
        }
    };

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isNode() {
        return isNode;
    }

    public void setNode(boolean node) {
        isNode = node;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isHasDepartment() {
        return hasDepartment;
    }

    public void setHasDepartment(boolean hasDepartment) {
        this.hasDepartment = hasDepartment;
    }
}
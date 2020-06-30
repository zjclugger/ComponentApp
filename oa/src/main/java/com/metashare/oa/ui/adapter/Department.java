
package com.zjclugger.oa.ui.adapter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Department implements Parcelable {
    @SerializedName("Company")
    private Company company;
    @SerializedName("Description")
    private String description;
    @SerializedName("EntityStatus")
    private int entityStatus;
    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("OwnerId")
    private int ownerId;

    private int staffTotal;
    private List<Department> subDepartmentList;
    private List<Staff> staffList;

    public Department() {

    }

    protected Department(Parcel in) {
        company = in.readParcelable(Company.class.getClassLoader());
        description = in.readString();
        entityStatus = in.readInt();
        id = in.readInt();
        name = in.readString();
        ownerId = in.readInt();
        staffList = in.createTypedArrayList(Staff.CREATOR);
        subDepartmentList = in.createTypedArrayList(Department.CREATOR);
        staffTotal = in.readInt();
    }

    public static final Creator<Department> CREATOR = new Creator<Department>() {
        @Override
        public Department createFromParcel(Parcel in) {
            return new Department(in);
        }

        @Override
        public Department[] newArray(int size) {
            return new Department[size];
        }
    };

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(int entityStatus) {
        this.entityStatus = entityStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getStaffTotal() {
        return staffTotal;
    }

    public void setStaffTotal(int staffTotal) {
        this.staffTotal = staffTotal;
    }

    public List<Staff> getStaffList() {
        if (staffList == null) {
            staffList = new ArrayList<>();
        }
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public List<Department> getSubDepartmentList() {
        if (subDepartmentList == null) {
            subDepartmentList = new ArrayList<>();
        }
        return subDepartmentList;
    }

    public void setSubDepartmentList(List<Department> subDepartmentList) {
        this.subDepartmentList = subDepartmentList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(company, flags);
        dest.writeString(description);
        dest.writeInt(entityStatus);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(ownerId);
        dest.writeTypedList(staffList);
        dest.writeTypedList(subDepartmentList);
        dest.writeInt(staffTotal);
    }
}
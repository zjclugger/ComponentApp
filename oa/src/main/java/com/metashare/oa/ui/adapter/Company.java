
package com.zjclugger.oa.ui.adapter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Company implements Parcelable {
    @SerializedName("CompanyAddress")
    private String companyAddress;
    @SerializedName("Contact")
    private String contact;
    @SerializedName("ContactDetails")
    private String contactDetails;
    @SerializedName("Departments")
    private List<Department> departments;
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

    public Company() {
    }

    protected Company(Parcel in) {
        companyAddress = in.readString();
        contact = in.readString();
        contactDetails = in.readString();
        departments = in.createTypedArrayList(Department.CREATOR);
        description = in.readString();
        entityStatus = in.readInt();
        id = in.readInt();
        name = in.readString();
        ownerId = in.readInt();
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public List<Department> getDepartments() {
        if(departments == null){
            departments = new ArrayList<>();
        }
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(companyAddress);
        dest.writeString(contact);
        dest.writeString(contactDetails);
        dest.writeTypedList(departments);
        dest.writeString(description);
        dest.writeInt(entityStatus);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(ownerId);
    }
}

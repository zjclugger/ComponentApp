package com.zjclugger.finance.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

import java.util.List;

/**
 * 记账凭证列表
 */
public class AccountBillListResult extends BaseEntity implements Parcelable {
    @SerializedName("records")
    List<AccountBillResult> billResultList; //票据列表
    @SerializedName("pageNumber")
    int pageIndex;
    @SerializedName("pages")
    int pageCount;
    @SerializedName("total")
    int total;

    public AccountBillListResult() {
    }

    protected AccountBillListResult(Parcel in) {
        super(in);
        billResultList = in.createTypedArrayList(AccountBillResult.CREATOR);
        pageIndex = in.readInt();
        pageCount = in.readInt();
        total = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(billResultList);
        dest.writeInt(pageIndex);
        dest.writeInt(pageCount);
        dest.writeInt(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccountBillListResult> CREATOR =
            new Creator<AccountBillListResult>() {
                @Override
                public AccountBillListResult createFromParcel(Parcel in) {
                    return new AccountBillListResult(in);
                }

                @Override
                public AccountBillListResult[] newArray(int size) {
                    return new AccountBillListResult[size];
                }
            };

    public List<AccountBillResult> getBillResultList() {
        return billResultList;
    }

    public void setBillResultList(List<AccountBillResult> billResultList) {
        this.billResultList = billResultList;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

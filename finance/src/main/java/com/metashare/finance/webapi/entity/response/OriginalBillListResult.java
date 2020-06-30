package com.zjclugger.finance.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

import java.util.List;

/**
 * 原始凭证列表
 */
public class OriginalBillListResult extends BaseEntity implements Parcelable {

    @SerializedName("records")
    List<OriginalBillResult> billResultList; //票据列表
    @SerializedName("pageNumber")
    int pageIndex;
    @SerializedName("pages")
    int pageCount;
    @SerializedName("total")
    int total;

    public OriginalBillListResult() {
    }

    protected OriginalBillListResult(Parcel in) {
        super(in);
        billResultList = in.createTypedArrayList(OriginalBillResult.CREATOR);
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

    public static final Creator<OriginalBillListResult> CREATOR =
            new Creator<OriginalBillListResult>() {
                @Override
                public OriginalBillListResult createFromParcel(Parcel in) {
                    return new OriginalBillListResult(in);
                }

                @Override
                public OriginalBillListResult[] newArray(int size) {
                    return new OriginalBillListResult[size];
                }
            };

    public List<OriginalBillResult> getBillResultList() {
        return billResultList;
    }

    public void setBillResultList(List<OriginalBillResult> billResultList) {
        this.billResultList = billResultList;
    }

    public int getBillResultListSize() {
        return billResultList == null ? 0 : billResultList.size();
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

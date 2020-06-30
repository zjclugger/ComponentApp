
package com.zjclugger.oa.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 假期类型及剩余数<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LeaveHistoryResult extends BaseEntity implements Parcelable {
    private int leaveType;
    private String leaveName;
    private String leaveRequestName;
    private int leaveRequestId;
    private String startDateTime;
    private String endDateTime;
    private String leaveRequestDate;
    private String leaveResult;

    public LeaveHistoryResult() {
    }

    protected LeaveHistoryResult(Parcel in) {
        super(in);
        leaveType = in.readInt();
        leaveName = in.readString();
        leaveRequestName = in.readString();
        leaveRequestId = in.readInt();
        startDateTime = in.readString();
        endDateTime = in.readString();
        leaveRequestDate = in.readString();
        leaveResult = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(leaveType);
        dest.writeString(leaveName);
        dest.writeString(leaveRequestName);
        dest.writeInt(leaveRequestId);
        dest.writeString(startDateTime);
        dest.writeString(endDateTime);
        dest.writeString(leaveRequestDate);
        dest.writeString(leaveResult);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LeaveHistoryResult> CREATOR = new Creator<LeaveHistoryResult>() {
        @Override
        public LeaveHistoryResult createFromParcel(Parcel in) {
            return new LeaveHistoryResult(in);
        }

        @Override
        public LeaveHistoryResult[] newArray(int size) {
            return new LeaveHistoryResult[size];
        }
    };

    public int getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(int leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public String getLeaveRequestName() {
        return leaveRequestName;
    }

    public void setLeaveRequestName(String leaveRequestName) {
        this.leaveRequestName = leaveRequestName;
    }

    public int getLeaveRequestId() {
        return leaveRequestId;
    }

    public void setLeaveRequestId(int leaveRequestId) {
        this.leaveRequestId = leaveRequestId;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLeaveRequestDate() {
        return leaveRequestDate;
    }

    public void setLeaveRequestDate(String leaveRequestDate) {
        this.leaveRequestDate = leaveRequestDate;
    }

    public String getLeaveResult() {
        return leaveResult;
    }

    public void setLeaveResult(String leaveResult) {
        this.leaveResult = leaveResult;
    }
}

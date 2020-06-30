
package com.zjclugger.oa.webapi.entity.response;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import com.zjclugger.lib.api.entity.BaseEntity;
import com.zjclugger.oa.R;
import com.zjclugger.oa.utils.OaConstants;

/**
 * 假期类型及剩余数<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LeaveTypeResult extends BaseEntity implements Parcelable {
    private int leaveType;
    private String leaveName;
    private int grantType;  //发放类型
    private double leaveRemain; //剩余
    private int leaveUnit;
    private Drawable leftImage;

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

    public int getGrantType() {
        switch (grantType) {
            case OaConstants.LeaveGrantType.MANUAL:
                return R.string.grant_manual;
            case OaConstants.LeaveGrantType.HOUR:
                return R.string.grant_hour;
            case OaConstants.LeaveGrantType.DAY:
                return R.string.grant_day;
        }
        return grantType;
    }

    public void setGrantType(int grantType) {
        this.grantType = grantType;
    }

    public double getLeaveRemain() {
        return leaveRemain;
    }

    public void setLeaveRemain(double leaveRemain) {
        this.leaveRemain = leaveRemain;
    }

    public int getLeaveUnit() {
        return leaveUnit;
    }

    public String getLeaveBalance() {
        if (leaveRemain < 0) {
            return "无";
        }
        return (leaveRemain == 0 ? "0" : leaveRemain) + getLeaveUnitText();
    }

    public String getLeaveUnitText() {
        return leaveUnit == 0 ? "小时" : "天";
    }

    public void setLeaveUnit(int leaveUnit) {
        this.leaveUnit = leaveUnit;
    }

    public Drawable getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(Drawable leftImage) {
        this.leftImage = leftImage;
    }
}

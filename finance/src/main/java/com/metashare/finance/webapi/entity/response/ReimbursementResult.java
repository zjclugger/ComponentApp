package com.zjclugger.finance.webapi.entity.response;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * 报销
 */
public class ReimbursementResult extends ReportChartsResult<Float> {
    @SerializedName("total")
    private String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public float getTotalValue() {
        if (TextUtils.isEmpty(total)) {
            return 0f;
        }
        return Float.parseFloat(total);
    }
}
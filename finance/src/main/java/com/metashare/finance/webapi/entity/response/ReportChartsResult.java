package com.zjclugger.finance.webapi.entity.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;
import com.zjclugger.lib.entity.common.ERPListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表图表结果
 */
public class ReportChartsResult<T> extends BaseEntity {
    @SerializedName("abscissa")
    private List<String> nameList = new ArrayList<>();
    @SerializedName("ordinate")
    private List<T> valueList = new ArrayList<>();

    List<ERPListItem<T>> dataList = new ArrayList<>();

    public List<ERPListItem<T>> getDataList() {
        if (nameList != null && nameList.size() > 0) {
            for (int i = 0; i < nameList.size(); i++) {
                dataList.add(new ERPListItem(nameList.get(i), valueList.get(i)));
            }
        }

        return dataList;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public List<T> getValueList() {
        return valueList;
    }

    public void setValueList(List<T> valueList) {
        this.valueList = valueList;
    }
}
package com.zjclugger.oa.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.zjclugger.lib.entity.common.ERPListItem;
import com.zjclugger.lib.view.LabelValueView;
import com.zjclugger.oa.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择适配器<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceStatisticsAdapter extends BaseExpandableListAdapter {
    private List<ERPListItem<String>> mDataList = new ArrayList<>();

    public AttendanceStatisticsAdapter(List<ERPListItem<String>> datas) {
        mDataList = datas;
    }

    @Override
    // 获取分组的个数
    public int getGroupCount() {
        return mDataList.size();
    }

    //获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataList.get(groupPosition).getChildItemList().size();
    }

    //        获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return mDataList.get(groupPosition);
    }

    //获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDataList.get(groupPosition).getChildItemList().get(childPosition);
    }

    //获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded    该组是展开状态还是伸缩状态
     * @param convertView   重用已有的视图对象
     * @param parent        返回的视图对象始终依附于的视图组
     */
// 获取显示指定分组的视图
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance_statistics_header,
                            parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle = convertView.findViewById(R.id.lvv_attendance_header);
            groupViewHolder.indicatorView = convertView.findViewById(R.id.iv_indicator);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.tvTitle.setLabelText(mDataList.get(groupPosition).getText());
        groupViewHolder.tvTitle.setValueText(mDataList.get(groupPosition).getValue());
        if (isExpanded) {
            groupViewHolder.indicatorView.setImageResource(R.mipmap.ic_arrow_down_selected);
        } else {
            groupViewHolder.indicatorView.setImageResource(R.mipmap.ic_arrow_down_normal);
        }
        return convertView;
    }

    /**
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild   子元素是否处于组中的最后一个
     * @param convertView   重用已有的视图(View)对象
     * @param parent        返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
     * android.view.ViewGroup)
     */

    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance_statistics_child,
                            parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tvChild = convertView.findViewById(R.id.lvv_attendance_child);
            convertView.setTag(childViewHolder);

        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tvChild.setLabelText(mDataList.get(groupPosition).getChildItemList().get(childPosition).getText());
        childViewHolder.tvChild.setValueText(mDataList.get(groupPosition).getChildItemList().get(childPosition).getValue());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        LabelValueView tvTitle;
        ImageView indicatorView;
    }

    static class ChildViewHolder {
        LabelValueView tvChild;
    }
}
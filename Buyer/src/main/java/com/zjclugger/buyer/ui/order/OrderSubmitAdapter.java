package com.zjclugger.buyer.ui.order;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.GoodsResult;
import com.zjclugger.buyer.webapi.response.ShopResult;
import com.zjclugger.lib.utils.ImageUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 适配器 <br>
 * Created by King.Zi on 2020/5/7.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OrderSubmitAdapter extends BaseExpandableListAdapter {
    private List<ShopResult> mGroupList;
    //这个String对应着ShopResult的Id，也就是店铺的Id
    private Map<String, List<GoodsResult>> mChildrenList;
    private Context mContext;
    private CheckBoxListener mCheckInterface;

    public OrderSubmitAdapter(Context context, List<ShopResult> groupList, Map<String,
            List<GoodsResult>> childrenList) {
        mGroupList = groupList;
        mChildrenList = childrenList;
        mContext = context;
    }

    public void setData(List<ShopResult> groupList, Map<String, List<GoodsResult>> childrenList) {
        mGroupList = groupList;
        mChildrenList = childrenList;
    }

    @Override
    public int getGroupCount() {
        return null == mGroupList ? 0 : mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (null != mGroupList && null != mChildrenList) {
            String groupId = mGroupList.get(groupPosition).getId();
            return mChildrenList.get(groupId).size();
        }
        return 0;
    }

    @Override
    public ShopResult getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<GoodsResult> childs = mChildrenList.get(mGroupList.get(groupPosition).getId());
        return childs.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_order_group, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        final ShopResult group = (ShopResult) getGroup(groupPosition);
        groupViewHolder.storeName.setText(group.getName());
        groupViewHolder.storeCheckBox.setVisibility(View.GONE);
        groupViewHolder.alertView.setVisibility(View.GONE);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_order_goods, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        final GoodsResult child = (GoodsResult) getChild(groupPosition, childPosition);
        if (child != null) {
            childViewHolder.goodsName.setText(child.getName());
            childViewHolder.goodsPrice.setText("￥" + child.getPrice() * child.getCount() + "");

            childViewHolder.goodsImage.setImageResource(R.mipmap.img_picture_default);
            if (!TextUtils.isEmpty(child.getUrl())) {
                ImageUtils.loadImage(mContext, childViewHolder.goodsImage, child.getUrl());
            }

            childViewHolder.goodsProperty.setText("属性:" + child.getProperties());
            childViewHolder.goodsUnitPrice.setText("￥" + child.getPrice() + "");
            childViewHolder.goodsBuyNum.setText("x" + child.getCount() + "");

        }
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public CheckBoxListener getCheckInterface() {
        return mCheckInterface;
    }

    public void setCheckInterface(CheckBoxListener mCheckInterface) {
        this.mCheckInterface = mCheckInterface;
    }

    static class GroupViewHolder {
        @BindView(R.id.store_checkBox)
        CheckBox storeCheckBox;
        @BindView(R.id.store_name)
        TextView storeName;
        @BindView(R.id.order_cancel_alert)
        TextView alertView;

        public GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 店铺的复选框
     */
    public interface CheckBoxListener {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素的位置
         * @param isChecked     组元素的选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变触发的事件
         *
         * @param groupPosition 组元素的位置
         * @param childPosition 子元素的位置
         * @param isChecked     子元素的选中与否
         */
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * 监听编辑状态
     */
    public interface GroupEditorListener {
        void groupEditor(int groupPosition);
    }

    static class ChildViewHolder {
        @BindView(R.id.goods_image)
        ImageView goodsImage;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_pro)
        TextView goodsProperty;
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.goods_unit_price)
        TextView goodsUnitPrice;
        @BindView(R.id.goods_buy_num)
        TextView goodsBuyNum;
        @BindView(R.id.goods_data)
        RelativeLayout goodsData;
        @BindView(R.id.right_button)
        TextView mRightButton;
        @BindView(R.id.function_layout)
        LinearLayout mFunctionLayout;
        @BindView(R.id.evaluate_view)
        TextView mEvaluateView;

        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
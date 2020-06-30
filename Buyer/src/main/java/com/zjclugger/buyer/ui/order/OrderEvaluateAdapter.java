package com.zjclugger.buyer.ui.order;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品评价适配器 <br>
 * Created by King.Zi on 2020/5/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OrderEvaluateAdapter extends BaseExpandableListAdapter {
    private List<ShopResult> mGroupList;
    //这个String对应着ShopResult的Id，也就是店铺的Id
    private Map<String, List<GoodsResult>> mChildrenList;
    private Activity mActivity;

    private ArrayList<ShopResult> mShopList = new ArrayList<>(); //TODO:[商铺]目前只有一个
    private Map<String, List<GoodsResult>> mShopGoodsMap = new HashMap<>(); //商铺商品的列表[商品]


    public OrderEvaluateAdapter(Activity activity, List<ShopResult> groupList, Map<String,
            List<GoodsResult>> childrenList) {
        mGroupList = groupList;
        mChildrenList = childrenList;
        mActivity = activity;
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
            convertView = View.inflate(mActivity, R.layout.item_order_group, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        final ShopResult group = (ShopResult) getGroup(groupPosition);
        groupViewHolder.storeName.setText(group.getName());
        groupViewHolder.alertView.setVisibility(View.INVISIBLE);
        groupViewHolder.storeCheckBox.setVisibility(View.GONE);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mActivity, R.layout.item_order_goods, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        final GoodsResult child = (GoodsResult) getChild(groupPosition, childPosition);

        if (child != null) {
            childViewHolder.goodsName.setText(child.getName());
            childViewHolder.goodsPrice.setText("￥" + child.getPrice());

            childViewHolder.goodsImage.setImageResource(R.mipmap.img_picture_default);
            if (!TextUtils.isEmpty(child.getUrl())) {
                ImageUtils.loadImage(mActivity, childViewHolder.goodsImage, child.getUrl());
            }

            childViewHolder.goodsProperty.setText("属性:" + child.getProperties());
            childViewHolder.mPriceCountLayout.setVisibility(View.GONE);

        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
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
        @BindView(R.id.left_button)
        TextView mLeftButton;
        @BindView(R.id.function_layout)
        LinearLayout mFunctionLayout;
        @BindView(R.id.evaluate_view)
        TextView mEvaluateView;
        @BindView(R.id.price_layout)
        LinearLayout mPriceCountLayout;

        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

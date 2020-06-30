package com.zjclugger.buyer.ui.order;

import android.app.Activity;
import android.os.Bundle;
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
import com.zjclugger.buyer.utils.BuyerConstants;
import com.zjclugger.buyer.webapi.response.GoodsResult;
import com.zjclugger.buyer.webapi.response.ShopResult;
import com.zjclugger.lib.entity.SerializableMap;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.router.utils.ARouterUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 适配器 <br>
 * Created by King.Zi on 2020/5/6.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OrderListAdapter extends BaseExpandableListAdapter {
    private List<ShopResult> mGroupList;
    //这个String对应着ShopResult的Id，也就是店铺的Id
    private Map<String, List<GoodsResult>> mChildrenList;
    private Activity mActivity;
    private CheckBoxListener mCheckInterface;
    private int mCurrentTab;
    private double mOrderTotalAmount;   //单个订单的总金额

    private ArrayList<ShopResult> mShopList = new ArrayList<>(); //TODO:[商铺]目前只有一个
    private Map<String, List<GoodsResult>> mShopGoodsMap = new HashMap<>(); //商铺商品的列表[商品]
    private Bundle mParams;


    public OrderListAdapter(Activity activity, List<ShopResult> groupList, Map<String,
            List<GoodsResult>> childrenList, int currentTab) {
        mGroupList = groupList;
        mChildrenList = childrenList;
        mActivity = activity;
        mCurrentTab = currentTab;
    }

    public void setData(List<ShopResult> groupList, Map<String, List<GoodsResult>> childrenList,
                        int currentTab) {
        mGroupList = groupList;
        mChildrenList = childrenList;
        mCurrentTab = currentTab;
    }

    public void setCurrentTab(int currentTab) {
        mCurrentTab = currentTab;
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
        if (R.string.text_to_payment == mCurrentTab) {
            groupViewHolder.alertView.setVisibility(View.VISIBLE);
            groupViewHolder.storeCheckBox.setVisibility(View.VISIBLE);
            groupViewHolder.storeCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    group.setChoosed(((CheckBox) v).isChecked());
                    mCheckInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());
                }
            });
            groupViewHolder.storeCheckBox.setChecked(group.isChoosed());
        } else {
            groupViewHolder.alertView.setVisibility(View.INVISIBLE);
            groupViewHolder.storeCheckBox.setVisibility(View.GONE);
        }
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
            mParams = new Bundle();
            mOrderTotalAmount += child.getPrice() * child.getCount();
            childViewHolder.goodsName.setText(child.getName());
            childViewHolder.goodsPrice.setText("￥" + child.getPrice() * child.getCount() + "");
            childViewHolder.goodsImage.setImageResource(R.mipmap.img_picture_default);
            if (!TextUtils.isEmpty(child.getUrl())) {
                ImageUtils.loadImage(mActivity, childViewHolder.goodsImage, child.getUrl());
            }

            childViewHolder.goodsProperty.setText("属性:" + child.getProperties());
            childViewHolder.goodsUnitPrice.setText("￥" + child.getPrice() + "");
            childViewHolder.goodsBuyNum.setText("x" + child.getCount() + "");

            if (isLastChild) {
                childViewHolder.mViewDetailView.setOnClickListener(v -> {
                    WidgetUtils.toast(mActivity, "查看订单详情", false);
                    //TODO:应该根据订单状态来判断，现在暂时传递tab
                    mParams = getParams(groupPosition);
                    mParams.putInt(BuyerConstants.Keywords.KEY_ORDER_STATUS, mCurrentTab);
                    ARouterUtils.openDetailFragment(mActivity,
                            OrderDetailFragment.class.getName(), mParams, R.color.white, false);
                    mOrderTotalAmount = 0d;
                });
                if (R.string.text_to_payment == mCurrentTab) {
                    childViewHolder.mFunctionView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouterUtils.openDetailFragment(mActivity,
                                    OrderSubmitFragment.class.getName(), getParams(groupPosition)
                                    , R.color.white, false);
                            mOrderTotalAmount = 0d;
                        }
                    });
                }
            }

            if (R.string.text_completed != mCurrentTab) {
                childViewHolder.mEvaluateView.setVisibility(View.GONE);
                if (isLastChild) {
                    childViewHolder.mFunctionLayout.setVisibility(View.VISIBLE);
                    childViewHolder.mFunctionView.setVisibility(View.VISIBLE);
                    setFunctionView(childViewHolder.mFunctionView,
                            getGroup(groupPosition).getName());
                } else {
                    childViewHolder.mFunctionLayout.setVisibility(View.GONE);
                }
            } else {
                if (isLastChild) {
                    childViewHolder.mFunctionLayout.setVisibility(View.VISIBLE);
                    childViewHolder.mViewDetailView.setVisibility(View.VISIBLE);
                    childViewHolder.mFunctionView.setVisibility(View.GONE);
                } else {
                    childViewHolder.mFunctionLayout.setVisibility(View.GONE);
                }

                childViewHolder.mEvaluateView.setVisibility(View.VISIBLE);
                childViewHolder.mEvaluateView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouterUtils.openDetailFragment(mActivity,
                                OrderEvaluateFragment.class.getName(), getParams(groupPosition),
                                R.color.white, false);
                    }
                });
            }
        }
        return convertView;
    }

    /**
     * 获取传递的参数
     *
     * @param groupPosition
     * @return
     */
    private Bundle getParams(int groupPosition) {
        if (null != mShopList) {
            mShopList.clear();
        }
        mShopList.add(mGroupList.get(groupPosition));

        if (null != mShopGoodsMap) {
            mShopGoodsMap.clear();
        }
        mShopGoodsMap.put(mGroupList.get(groupPosition).getId(),
                mChildrenList.get(mGroupList.get(groupPosition).getId()));
        //TODO:view order detail
        Bundle params = new Bundle();
        params.putParcelableArrayList(BuyerConstants.Keywords.KEY_ORDER_SHOP_LIST,
                mShopList);
        SerializableMap<String, List<GoodsResult>> goodsList =
                new SerializableMap<>();
        //TODO:应该是选中的
        goodsList.setMap(mShopGoodsMap);
        params.putSerializable(BuyerConstants.Keywords.KEY_ORDER_SHOP_GOODS_LIST,
                goodsList);
        params.putDouble(BuyerConstants.Keywords.KEY_ORDER_TOTAL_PRICE,
                mOrderTotalAmount);
        return params;
    }

    private void setFunctionView(TextView view, String id) {
        switch (mCurrentTab) {
            case R.string.text_to_payment:
                view.setText("付款");
                /*view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WidgetUtils.toast(mActivity, "付款" + id, false);
                    }
                });*/
                break;
            case R.string.text_to_delivery:
                view.setText("提醒发货");
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WidgetUtils.toast(mActivity, "提醒发货" + id, false);
                    }
                });
                break;
            case R.string.text_to_receive:
                view.setText("确认收货");
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WidgetUtils.toast(mActivity, "确认收货" + id, false);
                    }
                });
                break;
            case R.string.text_completed:
               /* view.setText("评价");
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WidgetUtils.toast(mContext, "已完成-评价" + id, false);
                    }
                });*/
                break;
            default:
                break;
        }
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
        TextView mFunctionView;
        @BindView(R.id.left_button)
        TextView mViewDetailView;
        @BindView(R.id.function_layout)
        LinearLayout mFunctionLayout;
        @BindView(R.id.evaluate_view)
        TextView mEvaluateView;

        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

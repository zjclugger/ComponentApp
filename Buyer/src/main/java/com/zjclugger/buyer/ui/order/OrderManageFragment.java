package com.zjclugger.buyer.ui.order;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.zjclugger.basiclib.Log;
import com.zjclugger.buyer.R;
import com.zjclugger.buyer.utils.BuyerConstants;
import com.zjclugger.buyer.webapi.response.GoodsResult;
import com.zjclugger.buyer.webapi.response.ShopResult;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.SerializableMap;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.router.utils.ARouterUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单管理 <br>
 * Created by King.Zi on 2020/5/6.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OrderManageFragment extends BaseFragment {
    private static final String TAG = "OrderFragment";
    private Map<String, Object> mQueryCondition;
    private boolean mIsPagingQuery;

    @BindView(R.id.tab_list_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.listView)
    ExpandableListView mExpandableListView;
    @BindView(R.id.all_checkBox)
    CheckBox mAllCheckBoxView;
    @BindView(R.id.total_price)
    TextView mTotalPriceView;
    @BindView(R.id.go_settlement)
    TextView mSettlementView;
    @BindView(R.id.order_info)
    LinearLayout mOrderInfoLayout;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.empty_view)
    TextView mEmptyView;
    @BindView(R.id.settlement_bar)
    LinearLayout mSettlementBarLayout;

    private double mTotalPrice = 0.00d;
    private int mTotalCount = 0;
    private OrderListAdapter mToPaymentAdapter;    //待付款
    private List<ShopResult> mShopResultList; //组元素的列表[商铺]
    private Map<String, List<GoodsResult>> mShopCartGoodsMap; //子元素的列表[商品]
    private int mCurrentTab;
    //待付款-已选择的
    private ArrayList<ShopResult> mShopSelectedList = new ArrayList<>(); //组元素的列表[商铺]
    private Map<String, List<GoodsResult>> mGoodsSelectedMap = new HashMap<>(); //子元素的列表[商品]

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarStyle(R.color.bg_white, false);
    }

    @Override
    public boolean isPublicPermission() {
        return true;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_order_manage;
    }

    @Override
    public void initViews() {
        mShopResultList = new ArrayList<>();
        mShopCartGoodsMap = new HashMap<>();

        mQueryCondition = new HashMap<>();
        resetQueryCondition();

        initRefresh();
        initTabLayout();

        initListView();
        bindViewData();
    }

    private void resetQueryCondition() {
        mQueryCondition.put(Constants.QueryParameter.PAGE_INDEX, 1);
        mQueryCondition.put(Constants.QueryParameter.PAGE_SIZE, 10);
    }

    @Override
    public void onResume() {
        super.onResume();
        changeShopCartNumber();
    }

    private void initRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //TODO:刷新
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //TODO:刷新
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void initTabLayout() {
        initDetailTitleViews("订单管理", "", null, false);
        // mTabLayout.addTab(mTabLayout.newTab().setText(R.string.all), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.text_to_payment), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.text_to_delivery));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.text_to_receive));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.text_completed));
        mCurrentTab = R.string.text_to_payment;
        mSettlementBarLayout.setVisibility(View.VISIBLE);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                resetQueryCondition();
                mSettlementBarLayout.setVisibility(tab.getText().equals(getString(R.string.text_to_payment)) ? View.VISIBLE : View.GONE);
                if (tab.getText().equals(getString(R.string.text_to_delivery))) {
                    mCurrentTab = R.string.text_to_delivery;
                   /* mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_CHECK);*/
                } else if (tab.getText().equals(getString(R.string.text_to_payment))) {
                    mCurrentTab = R.string.text_to_payment;
                   /* mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_CHECK);*/
                } else if (tab.getText().equals(getString(R.string.text_to_receive))) {
                    mCurrentTab = R.string.text_to_receive;
                   /* mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_CHECK);*/
                } else if (tab.getText().equals(getString(R.string.text_completed))) {
                    mCurrentTab = R.string.text_completed;
                   /* mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_CHECK);*/
                }

                getDataFromServer(false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initListView() {
        //init adapter
        mToPaymentAdapter = new OrderListAdapter(getActivity(), mShopResultList, mShopCartGoodsMap,
                mCurrentTab);
        //关键步骤1：设置复选框的接口
        mToPaymentAdapter.setCheckInterface(new OrderListAdapter.CheckBoxListener() {
            @Override
            public void checkGroup(int groupPosition, boolean isChecked) {
                ShopResult shopResult = mShopResultList.get(groupPosition);
                List<GoodsResult> child = mShopCartGoodsMap.get(shopResult.getId());
                for (int i = 0; i < child.size(); i++) {
                    child.get(i).setChecked(isChecked);
                }
                if (isCheckAll()) {
                    mAllCheckBoxView.setChecked(true);//全选
                } else {
                    mAllCheckBoxView.setChecked(false);//反选
                }
                mToPaymentAdapter.notifyDataSetChanged();
                calculate();
            }

            @Override
            public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
                boolean allChildSameState = true; //判断该组下面的所有子元素是否处于同一状态
                ShopResult group = mShopResultList.get(groupPosition);
                List<GoodsResult> child = mShopCartGoodsMap.get(group.getId());
                for (int i = 0; i < child.size(); i++) {
                    //不选全中
                    if (child.get(i).isChecked() != isChecked) {
                        allChildSameState = false;
                        break;
                    }
                }

                if (allChildSameState) {
                    group.setChoosed(isChecked);//如果子元素状态相同，那么对应的组元素也设置成这一种的同一状态
                } else {
                    group.setChoosed(false);//否则一律视为未选中
                }

                if (isCheckAll()) {
                    mAllCheckBoxView.setChecked(true);//全选
                } else {
                    mAllCheckBoxView.setChecked(false);//反选
                }

                mToPaymentAdapter.notifyDataSetChanged();
                calculate();
            }
        });

        //关键步骤3:监听组列表的编辑状态

        //init list view
        mExpandableListView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头
       /* mExpandableListView.setDivider(getResources().getDrawable(R.drawable.sharp_line_5));
        mExpandableListView.setChildDivider(getResources().getDrawable(R.drawable.sharp_line_1));*/

        mExpandableListView.setAdapter(mToPaymentAdapter);

        //关键步骤4:初始化，将ExpandableListView以展开的方式显示
        for (int i = 0; i < mToPaymentAdapter.getGroupCount(); i++) {
            mExpandableListView.expandGroup(i);
        }
        mExpandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                int firstVisiablePostion = view.getFirstVisiblePosition();
                int top = -1;
                View firstView = view.getChildAt(firstVisibleItem);
                Log.i("childCount=" + view.getChildCount());//返回的是显示层面上的所包含的子view的个数
                if (firstView != null) {
                    top = firstView.getTop();
                }
                Log.i("firstVisiableItem=" + firstVisibleItem + ",fistVisiablePosition=" + firstVisiablePostion + ",firstView=" + firstView + ",top=" + top);
                if (firstVisibleItem == 0 && top == 0) {
                    //mPtrFrame.setEnabled(true);
                } else {
                    //mPtrFrame.setEnabled(false);
                }
            }
        });
    }

    /**
     * @return 判断组元素是否全选
     */
    private boolean isCheckAll() {
        for (ShopResult group : mShopResultList) {
            if (!group.isChoosed()) {
                return false;
            }
        }
        return true;
    }

    @OnClick({R.id.all_checkBox, R.id.go_settlement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all_checkBox:
                doCheckAll();
                break;
            case R.id.go_settlement:
                if (mTotalCount == 0) {
                    WidgetUtils.toast(mContext, "请选择要付款的订单", false);
                    return;
                }
                //todo: open submit fragment
                Bundle params = new Bundle();
                params.putParcelableArrayList(BuyerConstants.Keywords.KEY_ORDER_SHOP_LIST,
                        mShopSelectedList);
                SerializableMap<String, List<GoodsResult>> goodsList = new SerializableMap<>();
                //TODO:应该是选中的
                goodsList.setMap(mGoodsSelectedMap);
                params.putSerializable(BuyerConstants.Keywords.KEY_ORDER_SHOP_GOODS_LIST,
                        goodsList);
                params.putDouble(BuyerConstants.Keywords.KEY_ORDER_TOTAL_PRICE, mTotalPrice);
                ARouterUtils.openDetailFragment(getActivity(),
                        OrderSubmitFragment.class.getName(), params, R.color.white, false);
                break;
        }
    }

    /**
     * 全选和反选
     * 错误标记：在这里出现过错误
     */
    private void doCheckAll() {
        for (int i = 0; i < mShopResultList.size(); i++) {
            ShopResult group = mShopResultList.get(i);
            group.setChoosed(mAllCheckBoxView.isChecked());
            List<GoodsResult> child = mShopCartGoodsMap.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                child.get(j).setChecked(mAllCheckBoxView.isChecked());//这里出现过错误
            }
        }
        mToPaymentAdapter.notifyDataSetChanged();
        calculate();
    }

    /**
     * 计算商品总价格，操作步骤
     * 1.先清空全局计价,计数
     * 2.遍历所有的子元素，只要是被选中的，就进行相关的计算操作
     * 3.给textView填充数据
     */
    private void calculate() {
        mTotalPrice = 0.00;
        mTotalCount = 0;
        if (null != mShopSelectedList) {
            mShopSelectedList.clear();
        }
        if (null != mGoodsSelectedMap) {
            mGoodsSelectedMap.clear();
        }

        boolean isAdded;
        List<GoodsResult> goodsList;
        for (int i = 0; i < mShopResultList.size(); i++) {
            isAdded = false;
            goodsList = new ArrayList<>();
            ShopResult group = mShopResultList.get(i);
            List<GoodsResult> child = mShopCartGoodsMap.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                GoodsResult goods = child.get(j);
                if (goods.isChecked()) {
                    if (!isAdded) {
                        mShopSelectedList.add(mShopResultList.get(i));
                        isAdded = true;
                    }
                    goodsList.add(goods);
                    mTotalCount++;
                    mTotalPrice += goods.getPrice() * goods.getCount();
                }
            }
            if (null != goodsList) {
                mGoodsSelectedMap.put(mShopResultList.get(i).getId(), goodsList);
            }
        }
        
        mTotalPriceView.setText("￥" + mTotalPrice + "");
        mSettlementView.setText("去支付(" + mTotalCount + ")");
        if (mTotalCount == 0) {
            changeShopCartNumber();
        }
    }

    /**
     * 设置购物车的数量
     */
    private void changeShopCartNumber() {
        int count = 0;
        for (int i = 0; i < mShopResultList.size(); i++) {
            ShopResult group = mShopResultList.get(i);
            group.setChoosed(mAllCheckBoxView.isChecked());
            List<GoodsResult> Childs = mShopCartGoodsMap.get(group.getId());
            for (GoodsResult mShopCartGoodsMap : Childs) {
                count++;
            }
        }

        //购物车已经清空
        if (count == 0) {
            resetView();
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    private void resetView() {
        mRefreshLayout.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }


    private void bindViewData() {
        //show module
        initData();
    }

    public void getDataFromServer(boolean isPagingQuery) {
        initData();
    }

    /**
     * 模拟数据<br>
     * 遵循适配器的数据列表填充原则，组元素被放在一个list中，对应着组元素的下辖子元素被放在Map中
     * 其Key是组元素的Id
     */
    private void initData() {
        if (mShopResultList != null) {
            mShopResultList.clear();
        }

        if (mShopCartGoodsMap != null) {
            mShopCartGoodsMap.clear();
        }

        for (int i = 0; i < 5; i++) {
            mShopResultList.add(new ShopResult(i + "", "第" + (i + 1) + "号当铺"));
            List<GoodsResult> goods = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                //i-j 就是商品的id， 对应着第几个店铺的第几个商品，1-1 就是第一个店铺的第一个商品
                GoodsResult goodsResult = new GoodsResult();
                goodsResult.setId(i + "-" + j);
                goodsResult.setName((i + 1) + "号商铺第" + (j + 1) + "商品");
                goodsResult.setUrl("http://img.qqzhi.com/uploads/2019-04-03/060630691.jpg");
                goodsResult.setPrice(255.00 + new Random().nextInt(1500));
                goodsResult.setPrimePrice(1555 + new Random().nextInt(3000));
                goodsResult.setProperties("商品属性-颜色-大小等");
                goodsResult.setCount(new Random().nextInt(50));
                goods.add(goodsResult);
            }
            mShopCartGoodsMap.put(mShopResultList.get(i).getId(), goods);
        }

        //fill data
        mToPaymentAdapter.setData(mShopResultList, mShopCartGoodsMap, mCurrentTab);
        mToPaymentAdapter.notifyDataSetChanged();
        for (int i = 0; i < mToPaymentAdapter.getGroupCount(); i++) {
            mExpandableListView.expandGroup(i);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mToPaymentAdapter = null;
        mShopCartGoodsMap.clear();
        mShopResultList.clear();
        mTotalPrice = 0.00;
        mTotalCount = 0;
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}

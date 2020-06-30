package com.zjclugger.seller.ui.order;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.tabs.TabLayout;
import com.zjclugger.lib.base.BaseListFragment;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.view.ExtendsRecyclerView;
import com.zjclugger.lib.view.JSearchView;
import com.zjclugger.seller.R;
import com.zjclugger.seller.webapi.response.OrderGoodsResult;
import com.zjclugger.seller.webapi.response.OrderResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 订单管理 <br>
 * Created by King.Zi on 2020/5/18.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OrderManageFragment extends BaseListFragment {
    private static final String TAG = "OrderFragment";

    @BindView(R.id.tab_list_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.order_recycler_view)
    ExtendsRecyclerView mOrderRecyclerView;
    @BindView(R.id.search_view)
    JSearchView mSearchView;

    private List<OrderResult> mOrderList; //组元素的列表[商铺]
    private int mCurrentTab;
    //待付款-已选择的

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarStyle(R.color.bg_white, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchView.clearFocus();
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
        mOrderList = new ArrayList<>();
        mQueryCondition = new HashMap<>();
        initSearchView();
        resetQueryCondition();

        super.initViews();
    }

    private void initSearchView() {
        mSearchView.init(getActivity());
        mSearchView.getBackView().setVisibility(View.GONE);
        mSearchView.setQueryHint("商品 | 订单号 | 手机号 | 姓名 | 物流单");
        mSearchView.getBackView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        mSearchView.getSearchView().onActionViewExpanded();
        mSearchView.getSearchView().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //mQueryCondition.put(HRConstants.StaffQueryCondition.STAFF_NAME, query);
                //getStaffList(false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Log.d("KING", "text change " + newText);
                //fixed 空值时点击搜索不查询，还是监听搜索按键的好
                // mQueryText = newText;
                return false;
            }
        });
        mSearchView.setOnSoftKeyBoardChangeListener(new JSearchView.OnSoftKeyboardChangeListener() {
            @Override
            public void show(int height) {
            }

            @Override
            public void hide(int height) {
                /*if (!mQueryText.equals(mQueryCondition.get(HRConstants.StaffQueryCondition
                .STAFF_NAME))) {
                    mQueryCondition.put(HRConstants.StaffQueryCondition.STAFF_NAME, mQueryText);
                    getStaffList(false);
                }*/
            }
        });
    }

    @Override
    protected void resetQueryCondition() {
        super.resetQueryCondition();
        mQueryCondition.put(Constants.QueryParameter.PAGE_INDEX, 1);
        mQueryCondition.put(Constants.QueryParameter.PAGE_SIZE, 10);
    }

    @Override
    public void initTabLayout() {
        initDetailTitleViews("订单管理", "", null, false);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.text_to_handle), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.text_to_delivery));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.text_delivered));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.text_completed));
        mCurrentTab = R.string.text_to_handle;

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                resetQueryCondition();
                if (tab.getText().equals(getString(R.string.text_to_delivery))) {
                    mCurrentTab = R.string.text_to_delivery;
                   /* mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_CHECK);*/
                } else if (tab.getText().equals(getString(R.string.text_to_handle))) {
                    mCurrentTab = R.string.text_to_handle;
                   /* mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_CHECK);*/
                } else if (tab.getText().equals(getString(R.string.text_delivered))) {
                    mCurrentTab = R.string.text_delivered;
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

    @Override
    public void bindData() {
        mAdapter = new OrderListAdapter(getActivity(), R.layout.item_order_list, mOrderList);
    }

    @Override
    public boolean isShowLastLine() {
        return true;
    }

    @Override
    public ExtendsRecyclerView getRecyclerView() {
        return mOrderRecyclerView;
    }

    @Override
    public void getDataFromServer(boolean isPagingQuery) {
        initData();
    }

    /**
     * 模拟数据<br>
     * 遵循适配器的数据列表填充原则，组元素被放在一个list中，对应着组元素的下辖子元素被放在Map中
     * 其Key是组元素的Id
     */
    private void initData() {
        if (mOrderList != null) {
            mOrderList.clear();
        }

        //todo: init data
        OrderResult result;
        OrderGoodsResult goodsResult;
        for (int i = 0; i < 20; i++) {
            result = new OrderResult();
            result.setId("3000" + i);
            result.setOrderNO("0023209981" + i);
            result.setOrderTime("2020-05-18: 17:16");
            result.setCustomerName("张三" + i);
            result.setCustomerAddress("山东省青岛市市北区抚顺路98" + i + "号");
            for (int y = 0; y < 5; y++) {
                goodsResult = new OrderGoodsResult();
                goodsResult.setName("精美商品" + y);
                goodsResult.setTransactionPrice(new BigDecimal(120));
                goodsResult.setCount(5);
                goodsResult.setTotalAmount(new BigDecimal(600));
                result.getGoodsList().add(goodsResult);
            }

            result.setStatus(mCurrentTab);
            result.setTotalAmount(5000d);
            mOrderList.add(result);
        }
        mAdapter.notifyDataChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mOrderList.clear();
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}

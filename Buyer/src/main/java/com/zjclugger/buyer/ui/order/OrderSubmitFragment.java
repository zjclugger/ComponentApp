package com.zjclugger.buyer.ui.order;

import android.view.View;
import android.widget.TextView;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.utils.BuyerConstants;
import com.zjclugger.buyer.webapi.response.GoodsResult;
import com.zjclugger.buyer.webapi.response.ShopResult;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.SerializableMap;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.lib.view.LabelEditTextView;
import com.zjclugger.lib.view.LabelValueView;
import com.zjclugger.lib.view.NestedExpandableListView;
import com.zjclugger.router.utils.ARouterUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 订单提交 <br>
 * Created by King.Zi on 2020/5/7.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OrderSubmitFragment extends BaseFragment {
    private static final String TAG = "OrderSubmit";

    @BindView(R.id.expand_list_view)
    NestedExpandableListView mExpandableListView;
    @BindView(R.id.bottom_pay_amount)
    LabelValueView mTotalPriceView;
    @BindView(R.id.bottom_pay_button)
    TextView mPayView;
    @BindView(R.id.elvv_address)
    ExtendLabelValueView mAddressView;
    @BindView(R.id.lvv_address_detail)
    LabelValueView mAddressDetailView;
    @BindView(R.id.lvv_total_price)
    LabelValueView mGoodsTotalPriceView;
    @BindView(R.id.lvv_tax)
    LabelValueView mTaxView;
    @BindView(R.id.lvv_pay_amount)
    LabelValueView mPayAmountView;
    @BindView(R.id.elvv_pay_mode)
    ExtendLabelValueView mPayModeView;
    @BindView(R.id.elvv_phone)
    ExtendLabelValueView mPhoneView;
    @BindView(R.id.order_remark_view)
    LabelEditTextView mRemarkView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.empty_view)
    TextView mEmptyView;

    private double mOrderTotalAmount;
    private OrderSubmitAdapter mOrderSubmitAdapter;    //提交订单
    private List<ShopResult> mShopResultList; //组元素的列表[商铺]
    private Map<String, List<GoodsResult>> mShopGoodsMap; //子元素的列表[商品]

    @Override
    public boolean isPublicPermission() {
        return true;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_order_submit;
    }

    @Override
    public void initViews() {
        mShopResultList = new ArrayList<>();
        mShopGoodsMap = new HashMap<>();
        if (null == mArguments) {
            close();
        } else {
            //1.init view
            initDetailTitleViews("提交订单");
            initRefresh();
            mPayView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouterUtils.openDetailFragment(getActivity(),
                            CashierFragment.class.getName(), mArguments, R.color.white, false);
                }
            });

            //2.get data from argument
            mShopResultList =
                    mArguments.getParcelableArrayList(BuyerConstants.Keywords.KEY_ORDER_SHOP_LIST);
            SerializableMap<String, List<GoodsResult>> goodsList = (SerializableMap<String,
                    List<GoodsResult>>) mArguments.getSerializable(BuyerConstants.Keywords.KEY_ORDER_SHOP_GOODS_LIST);
            if (null != goodsList) {
                mShopGoodsMap = goodsList.getMap();
            }
            mOrderTotalAmount = mArguments.getDouble(BuyerConstants.Keywords.KEY_ORDER_TOTAL_PRICE);
            mPayAmountView.setValueText(String.valueOf(mOrderTotalAmount));
            //TODO:减去或加上税
            mTotalPriceView.setValueText(String.valueOf(mOrderTotalAmount - 0));

            mPayModeView.getValueView().setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_alipay_small), null,
                    null,null);
            //3.fill data
            initListView();
        }
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

    private void initListView() {
        //init adapter
        mOrderSubmitAdapter = new OrderSubmitAdapter(mContext, mShopResultList, mShopGoodsMap);

        //init list view
        mExpandableListView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头
        mExpandableListView.setAdapter(mOrderSubmitAdapter);

        //关键步骤4:初始化，将ExpandableListView以展开的方式显示
        for (int i = 0; i < mOrderSubmitAdapter.getGroupCount(); i++) {
            mExpandableListView.expandGroup(i);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mOrderSubmitAdapter = null;
        mShopGoodsMap.clear();
        mShopResultList.clear();
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}
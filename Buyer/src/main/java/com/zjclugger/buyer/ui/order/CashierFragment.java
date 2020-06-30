package com.zjclugger.buyer.ui.order;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.buyer.R;
import com.zjclugger.buyer.utils.BuyerConstants;
import com.zjclugger.buyer.webapi.response.PayModeResult;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.utils.CommonUtils;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.lib.view.LabelValueView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 收银台<br>
 * Created by King.Zi on 2020/5/8.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class CashierFragment extends BaseFragment {
    private final static String TAG = "Cashier";
    @BindView(R.id.elvv_default_pay)
    ExtendLabelValueView mDefaultPayView;
    @BindView(R.id.pay_recycler_view)
    RecyclerView mPayListView;
    @BindView(R.id.cashier_amount_view)
    TextView mAmountView;
    @BindView(R.id.lvv_cashier_goods_name)
    LabelValueView mGoodsView;

    private CashierPayModeAdapter mAdapter;
    private List<PayModeResult> mPayModeList;
    private String mDefaultId;

    @Override
    public int getLayout() {
        return R.layout.fragment_cashier;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("收银台");
        double amount = mArguments.getDouble(BuyerConstants.Keywords.KEY_ORDER_TOTAL_PRICE);
        mAmountView.setText(CommonUtils.getString(mContext, R.string.goods_price, amount));

        getDataFromServer();
        mAdapter = new CashierPayModeAdapter(R.layout.item_cashier_pay_list, mPayModeList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // mDefaultPayView.setValueText(mPayModeList.get(position).getName());
                mDefaultPayView.setRightImage(R.mipmap.ic_circle_chk_normal_small);
                setChecked(mPayModeList.get(position).getId());
            }
        });
        //TODO:adapter ?
        mPayListView.setLayoutManager(new LinearLayoutManager(mContext));
        mPayListView.setAdapter(mAdapter);

        mDefaultPayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDefaultPayView.setRightImage(R.mipmap.ic_circle_checkbox_checked);
                setChecked(mDefaultId);
            }
        });
    }

    private void setChecked(String checkedId) {
        for (int i = 0; i < mPayModeList.size(); i++) {
            mPayModeList.get(i).setChecked(mPayModeList.get(i).getId().equalsIgnoreCase(checkedId));
        }

        mAdapter.notifyDataChanged();
    }

    private void getDataFromServer() {
        mPayModeList = new ArrayList<>();
        PayModeResult result;
        result = new PayModeResult();
        result.setId("3200");
        result.setName("招商银行");
        result.setDefault(true);
        result.setChecked(true);
        mPayModeList.add(result);
        mDefaultId = result.getId();
        mDefaultPayView.setLabelText(result.getName());

        result = new PayModeResult();
        result.setId("3201");
        result.setName("支付宝");
        mPayModeList.add(result);

        result = new PayModeResult();
        result.setId("3202");
        result.setName("微信");
        mPayModeList.add(result);

        result = new PayModeResult();
        result.setId("3203");
        result.setName("建设银行");
        result.setDefault(false);
        mPayModeList.add(result);

        result = new PayModeResult();
        result.setId("3204");
        result.setName("中国银行");
        mPayModeList.add(result);

        result = new PayModeResult();
        result.setId("3205");
        result.setName("工商银行");
        mPayModeList.add(result);
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}
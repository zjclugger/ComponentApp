package com.zjclugger.buyer.ui.uc;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.PayModeResult;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.ui.widget.JDialog;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.ExtendLabelValueView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 支付方式<br>
 * Created by King.Zi on 2020/4/26.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class PayModeFragment extends BaseFragment {
    private final static String TAG = "PayMode";
    @BindView(R.id.elvv_default_pay)
    ExtendLabelValueView mDefaultPayView;

    private PayModeAdapter mAdapter;
    private List<PayModeResult> mPayModeList;
    private RecyclerView mPayListView;
    private JDialog mDialog;

    @Override
    public int getLayout() {
        return R.layout.fragment_pay_mode;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("支付方式", "添加银行卡");
        mTitleRightView.setTextColor(getResources().getColor(R.color.text_selected));
        mTitleRightView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.text_size_small));
        mTitleRightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetUtils.toast(mContext, "添加银行卡", false);
            }
        });

        getDataFromServer();
        mAdapter = new PayModeAdapter(R.layout.item_pay_list, mPayModeList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mDefaultPayView.setValueText(mPayModeList.get(position).getName());
                //TODO:更新到服务器
                for (int i = 0; i < mPayModeList.size(); i++) {
                    mPayModeList.get(i).setDefault(i == position);
                }

                mAdapter.notifyDataChanged();

                for (int i = 0; i < mPayModeList.size(); i++) {
                    android.util.Log.d("KING", "name=" + mPayModeList.get(i).getName() + "," +
                            "default=" + mPayModeList.get(i).isDefault());
                }
                mDialog.close();
            }
        });
        mDialog = new JDialog(mContext, R.layout.dialog_pay_list);
        mDialog.getDialogView().findViewById(R.id.title_right_view).setOnClickListener(v -> mDialog.close());
        mDialog.setGravity(Gravity.BOTTOM)
                .setCanceledOnTouchOutside(false)
                .setMargin(1, 1, 1, -5);
        mPayListView = mDialog.getDialogView().findViewById(R.id.pay_mode_recycler_view);
        //TODO:adapter ?
        mPayListView.setLayoutManager(new LinearLayoutManager(mContext));
        mPayListView.setAdapter(mAdapter);

        mDefaultPayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });
    }

    private void getDataFromServer() {
        mPayModeList = new ArrayList<>();
        PayModeResult result;
        result = new PayModeResult();
        result.setId("3200");
        result.setName("招商银行");
        result.setDefault(true);
        mPayModeList.add(result);

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
package com.zjclugger.finance.ui.report;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.utils.DateTimeFormat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.zjclugger.basiclib.Log;
import com.zjclugger.finance.R;
import com.zjclugger.finance.R2;
import com.zjclugger.finance.ui.adapter.ReportNameAmountAdapter;
import com.zjclugger.finance.webapi.entity.response.ReportAssetsLiabilitiesResult;
import com.zjclugger.lib.api.ApiResponseUtils;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.view.LabelValueView;

import java.util.Date;

import butterknife.BindView;

/**
 * 资产负债表 <br>
 * Created by King.Zi on 2020/1/8.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AssetsLiabilitiesReportFragment extends BaseReportFragment {
    @BindView(R2.id.assets_label_date)
    TextView mAssetsDateView;
    @BindView(R2.id.assets_label_currency)
    TextView mCurrencyView;
    @BindView(R2.id.assets_tab_layout)
    TabLayout mTabLayout;
    @BindView(R2.id.list_layout)
    LinearLayout mListLayout;
    @BindView(R2.id.assets_list_layout)
    LinearLayout mAssetsListLayout;
    @BindView(R2.id.liabilities_list_layout)
    LinearLayout mLiabilitiesListLayout;
    //recycler view
    @BindView(R2.id.current_assets_recycler_view)
    RecyclerView mCurrentAssetsRecyclerView;
    @BindView(R2.id.no_current_assets_recycler_view)
    RecyclerView mNoCurrentAssetsRecyclerView;
    @BindView(R2.id.current_liabilities_recycler_view)
    RecyclerView mCurrentLiabilitiesRecyclerView;
    @BindView(R2.id.no_current_liabilities_recycler_view)
    RecyclerView mNoCurrentLiabilitiesRecyclerView;
    @BindView(R2.id.owner_equity_recycler_view)
    RecyclerView mOwnerEquityRecyclerView;
    @BindView(R2.id.current_assets_total)
    LabelValueView mCurrentAssetsTotal;
    @BindView(R2.id.not_current_assets_total)
    LabelValueView mNoCurrentAssetsTotal;
    @BindView(R2.id.assets_total)
    LabelValueView mAssetsTotal;
    @BindView(R2.id.current_liabilities_total)
    LabelValueView mCurrentLiabilitiesTotal;
    @BindView(R2.id.not_current_liabilities_total)
    LabelValueView mNoCurrentLiabilitiesTotal;
    @BindView(R2.id.liabilities_total)
    LabelValueView mLiabilitiesTotal;
    @BindView(R2.id.owner_equity_total)
    LabelValueView mOwnerEquityTotal;
    @BindView(R2.id.owner_equity_liabilities_total)
    LabelValueView mOwnerEquityLiabilitiesTotal;
    private ReportAssetsLiabilitiesResult mReportResult;
    //adapter
    private ReportNameAmountAdapter mCurrentAssetsAdapter;
    private ReportNameAmountAdapter mNoCurrentAssetsAdapter;
    private ReportNameAmountAdapter mCurrentLiabilitiesAdapter;
    private ReportNameAmountAdapter mNoCurrentLiabilitiesAdapter;
    private ReportNameAmountAdapter mOwnerEquityAdapter;
    private RecyclerView.ItemDecoration mRecyclerViewDivider = null;

    @Override
    public int getLayout() {
        return R.layout.fragment_report_assets_liabilities;
    }

    @Override
    public OnTimeSelectListener getOnTimeSelectListener() {
        return new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mEndDate = DateTimeFormat.parseDateTime(date, VALUE_DATE_FORMAT);
                mAssetsDateView.setText(DateTimeFormat.parseDateTime(date, getDateFormat()));
                //todo:读取接口
                getDataFromServer();
            }
        };
    }

    @Override
    public void initViews() {
        super.initViews();
        mReportResult = new ReportAssetsLiabilitiesResult();

        initDetailTitleViews(getString(R.string.assets_liabilities));
        mAssetsDateView.setText(DateTimeFormat.parseDateTime(new Date(), getDateFormat()));
        mAssetsDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerView.show();
            }
        });

        initTabLayout();

        //init list view
        mRecyclerViewDivider = ViewUtils.getListDivider(mContext, 2f, true);
        mCurrentAssetsAdapter = new ReportNameAmountAdapter(mReportResult.getFlowAsset());
        mNoCurrentAssetsAdapter = new ReportNameAmountAdapter(mReportResult.getNoFlowAsset());
        mCurrentLiabilitiesAdapter = new ReportNameAmountAdapter(mReportResult.getFlowLiability());
        mNoCurrentLiabilitiesAdapter =
                new ReportNameAmountAdapter(mReportResult.getNoFlowLiability());
        mOwnerEquityAdapter = new ReportNameAmountAdapter(mReportResult.getOwnerEquity());

        //流动资产
        initRecyclerView(mCurrentAssetsAdapter, mCurrentAssetsRecyclerView);
        //非流动资产
        initRecyclerView(mNoCurrentAssetsAdapter, mNoCurrentAssetsRecyclerView);
        //流动负债
        initRecyclerView(mCurrentLiabilitiesAdapter, mCurrentLiabilitiesRecyclerView);
        //非流动负债
        initRecyclerView(mNoCurrentLiabilitiesAdapter, mNoCurrentLiabilitiesRecyclerView);
        //所有者权益
        initRecyclerView(mOwnerEquityAdapter, mOwnerEquityRecyclerView);

        getDataFromServer();
    }

    private void initRecyclerView(ReportNameAmountAdapter adapter, RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(mRecyclerViewDivider);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.bindToRecyclerView(recyclerView);
    }

    private void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.assets), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.liabilities));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals(getString(R.string.assets))) {
                    mAssetsListLayout.setVisibility(View.VISIBLE);
                    mLiabilitiesListLayout.setVisibility(View.GONE);
                } else if (tab.getText().equals(getString(R.string.liabilities))) {
                    mAssetsListLayout.setVisibility(View.GONE);
                    mLiabilitiesListLayout.setVisibility(View.VISIBLE);
                }

                Log.d("TAB selected " + tab.getText());
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
    public void getDataFromServer() {
        showWaiting();
        mViewModel.getAssetsLiabilitiesReport(mEndDate).observe(this, response -> {
            closeProgressDialog();
            mReportResult = null;
            if (ApiResponseUtils.isSuccess(response, getString(R.string.error_get_profit_data))) {
                mListLayout.setVisibility(View.VISIBLE);
                mReportResult = response.body.getResult();
            }

            mCurrentAssetsAdapter.setNewData(mReportResult == null ? null :
                    mReportResult.getFlowAsset());
            mNoCurrentAssetsAdapter.setNewData(mReportResult == null ? null :
                    mReportResult.getNoFlowAsset());
            mCurrentLiabilitiesAdapter.setNewData(mReportResult == null ? null :
                    mReportResult.getFlowLiability());
            mNoCurrentLiabilitiesAdapter.setNewData(mReportResult == null ? null :
                    mReportResult.getNoFlowLiability());
            mOwnerEquityAdapter.setNewData(mReportResult == null ? null :
                    mReportResult.getOwnerEquity());
        });
    }
}

package com.zjclugger.finance.ui.report;

import android.view.View;
import android.widget.TextView;

import com.zjclugger.finance.R;
import com.zjclugger.finance.R2;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.router.utils.ARouterUtils;

import butterknife.BindView;

/**
 * 财务报表 <br>
 * Created by King.Zi on 2020/1/8.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class MainReportFragment extends BaseFragment {
    @BindView(R2.id.label_assets_liabilities)
    TextView mAssetsView;
    @BindView(R2.id.label_profit)
    TextView mProfitView;
    @BindView(R2.id.label_bill_statistics)
    TextView mBillStatistics;
    @BindView(R2.id.label_personal_statistics)
    TextView mPersonalStatistics;

    @Override
    public int getLayout() {
        return R.layout.fragment_report_main;
    }

    @Override
    public void initViews() {
        initDetailTitleViews(getString(R.string.report_finance));
        mAssetsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouterUtils.openDetailFragment(getActivity(),
                        AssetsLiabilitiesReportFragment.class.getName(), null, R.color.white,
                        false);
            }
        });
        mProfitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouterUtils.openDetailFragment(getActivity(),
                        ProfitReportFragment.class.getName(), null, R.color.white, false);
            }
        });

        mBillStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouterUtils.openDetailFragment(getActivity(),
                        BillReimbursementFragment.class.getName(), null, R.color.white, false);
            }
        });

        mPersonalStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouterUtils.openDetailFragment(getActivity(),
                        PersonalReimbursementFragment.class.getName(), null, R.color.white, false);
            }
        });
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}

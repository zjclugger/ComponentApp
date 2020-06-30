package com.zjclugger.finance.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Pie;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.zjclugger.finance.ChartsTestActivity;
import com.zjclugger.finance.R;
import com.zjclugger.finance.R2;
import com.zjclugger.finance.ui.adapter.SortReimbursementAdapter;
import com.zjclugger.finance.ui.original.OriginalManageFragment;
import com.zjclugger.finance.ui.report.BillReimbursementFragment;
import com.zjclugger.finance.ui.report.PersonalReimbursementFragment;
import com.zjclugger.finance.webapi.entity.response.DepartReimbursementResult;
import com.zjclugger.lib.api.ApiResponseUtils;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.entity.common.ERPListItem;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.view.CircleProgressView;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.lib.view.ImageTextView;
import com.zjclugger.lib.view.echart.EChartWebView;
import com.zjclugger.router.utils.ARouterUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class FinanceWorkbenchFragment extends BaseFragment {
    private static final String TAG = "FinanceMain";

    @BindView(R2.id.image_text_upload)
    ImageTextView mBillUploadView;
    @BindView(R2.id.image_text_statistics)
    ImageTextView mBillStatisticsView;
    @BindView(R2.id.image_text_bill)
    ImageTextView mBillUpdateView;

    @BindView(R2.id.depart_bill_chart)
    EChartWebView mDepartBillChart;

    @BindView(R2.id.reason_bill_chart)
    EChartWebView mReasonBillChart;

    @BindView(R2.id.month_percentage_progress)
    CircleProgressView mMonthPercentageView;

    @BindView(R2.id.title_depart_bill_total)
    ExtendLabelValueView mTitleDepartTotal;

    @BindView(R2.id.year_reimbursement)
    ExtendLabelValueView mYearReimbursementView;

    @BindView(R2.id.month_reimbursement)
    ExtendLabelValueView mMonthReimbursementView;

    @BindView(R2.id.reimbursement_sort_recycler_view)
    RecyclerView mSortReimbursementRecyclerView;
    SortReimbursementAdapter mSortReimbursementAdapter;
    List<ERPListItem<Float>> mSortReimbursementResultList = new ArrayList<>();
    @BindView(R2.id.reimbursement_sort_title)
    ExtendLabelValueView mSortReimbursementTitleView;
    @BindView(R2.id.reimbursement_percentage_title)
    ExtendLabelValueView mPercentageReimbursementTitleView;
    @BindView(R2.id.title_reason_bill_total)
    ExtendLabelValueView mReasonBillTotalTitleView;
    private FinanceViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarStyle(R.color.white, false);
        mViewModel = ViewModelProviders.of(getActivity()).get(FinanceViewModel.class);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_finance_workbench;
    }

    @Override
    public void initViews() {
        initSearchView();
        //for test
        mTitleDepartTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ChartsTestActivity.class));
            }
        });

        mSortReimbursementRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mSortReimbursementAdapter = new SortReimbursementAdapter(R.layout.item_common_list,
                mSortReimbursementResultList);
        mSortReimbursementRecyclerView.setAdapter(mSortReimbursementAdapter);

        View.OnClickListener moreListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouterUtils.openDetailFragment(getActivity(),
                        BillReimbursementFragment.class.getName(), null, R.color.white, false);

            }
        };

        mSortReimbursementTitleView.getValueView().setOnClickListener(moreListener);
        mPercentageReimbursementTitleView.getValueView().setOnClickListener(moreListener);
        mTitleDepartTotal.getValueView().setOnClickListener(moreListener);
        mReasonBillTotalTitleView.getValueView().setOnClickListener(moreListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        bindViewData();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != mMonthPercentageView) {
            mMonthPercentageView.stopAnimation();
        }
    }

    private void bindViewData() {
        mBillUploadView.setOnClickListener(v -> {
            ARouterUtils.navigateBillUpload(getActivity(),
                    UserManager.getInstance().getCurrentUser().getCompanyId(),
                    getString(R.string.title_bill_upload));
        });
        mBillStatisticsView.setOnClickListener(v -> {
            ARouterUtils.openDetailFragment(getActivity(),
                    PersonalReimbursementFragment.class.getName(), null, R.color.white, false);
        });
        mBillUpdateView.setOnClickListener(v -> {
            ARouterUtils.openDetailFragment(getActivity(),
                    OriginalManageFragment.class.getName(), null, R.color.white, false);

        });

        //pie chart
        mDepartBillChart.setOnAddEChartActionListener(new EChartWebView.OnAddEChartActionListener() {
            @Override
            public void actionHandlerResponseResult(String result) {
            }

            @Override
            public void onWebViewLoadFinished(WebView view, String url) {
                getDepartmentReimbursement();
            }
        });

        mReasonBillChart.setOnAddEChartActionListener(new EChartWebView.OnAddEChartActionListener() {
            @Override
            public void actionHandlerResponseResult(String result) {
            }

            @Override
            public void onWebViewLoadFinished(WebView view, String url) {
                getReasonReimbursement();
            }
        });

        getDataFromServer();
    }

    private void initSearchView() {
      /*  mStaffSearchView.onActionViewExpanded();
        mStaffSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        mStaffSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/
    }

    /**
     * 获取按部门分类的报销数据
     */
    private void getDepartmentReimbursement() {
        mViewModel.getDepartmentReimbursement().observe(this,
                response -> {
                    if (ApiResponseUtils.isSuccess(response, getString(R.string.error_get_data))) {
                        BaseWrapperEntity<DepartReimbursementResult> wrapperEntity =
                                response.body;
                        if (wrapperEntity != null) {
                            mDepartBillChart.refreshEChartsWithOption(getPieOption(getString(R.string.depart_reimbursement),
                                    wrapperEntity.getResult().getERPItemList()));
                        }
                    }
                });
    }

    private void getReasonReimbursement() {
        mViewModel.getReasonReimbursement().observe(this,
                response -> {
                    if (ApiResponseUtils.isSuccess(response, getString(R.string.error_get_data))) {
                        JsonArray jsonArray = response.body.getResult().getAsJsonArray();
                        if (null != jsonArray) {
                            List<ERPListItem<Float>> erpItemList = new ArrayList<>();
                            for (JsonElement org : jsonArray) {
                                erpItemList.add(new ERPListItem(org.getAsJsonObject().get(
                                        "reason").getAsString(), org.getAsJsonObject().get(
                                        "totalAmount").getAsFloat()));
                            }

                            mReasonBillChart.refreshEChartsWithOption(getPieOption(getString(R.string.reason_reimbursement),
                                    erpItemList));
                        }
                    }
                });
    }

    /**
     * 获取饼状图的option
     *
     * @param chartName
     * @param dataList
     * @return
     */
    public GsonOption getPieOption(String chartName, List<ERPListItem<Float>> dataList) {
        if (null == dataList || dataList.size() == 0) {
            return null;
        }

        GsonOption option = new GsonOption();
        option.calculable(true);
        option.tooltip().trigger(Trigger.item).formatter("{a}<br/>{b}: {c} ({d}%)");

        Pie pie = new Pie();
        pie.name(chartName);
        for (ERPListItem item : dataList) {
            pie.data(new PieData(item.getText(), item.getValue()));
        }

        pie.label().normal().formatter("{b}\\n{c}");    //换行符前面需要加两个\进行转义，不然传递到JS中会出错
        pie.top(20);
        pie.bottom(20);
        pie.radius(30, 50).avoidLabelOverlap(true);
        option.series(pie);
        return option;
    }

    private void getDataFromServer() {
        //本月占比
        mViewModel.getMonthPercentage().observe(this,
                response -> {
                    float monthValue = 0f;
                    float yearValue = 0f;
                    if (ApiResponseUtils.isSuccess(response,
                            getString(R.string.error_get_reimbursement_percentage))) {
                        JsonObject jsonObject = response.body.getResult();
                        if (null != jsonObject) {
                            if (jsonObject.has("amountMonth")) {
                                monthValue = JsonNull.INSTANCE.equals(jsonObject.get("amountMonth"
                                )) ? 0f : jsonObject.get("amountMonth").getAsFloat();
                            }
                            if (jsonObject.has("amountYear")) {
                                yearValue = jsonObject.get("amountYear").getAsFloat();
                            }
                        }
                    }

                    mMonthReimbursementView.setValueText(ERPUtils.getFloat(monthValue, "",
                            "元"));
                    mYearReimbursementView.setValueText(ERPUtils.getFloat(yearValue, "", "元"));
                    mMonthPercentageView.setProgress(monthValue / yearValue * 100);
                    mMonthPercentageView.startAnimation();
                });


        //本月报销排行
        mViewModel.getSortReimbursement().observe(this,
                response -> {
                    if (null != mSortReimbursementResultList) {
                        mSortReimbursementResultList.clear();
                    }

                    if (ApiResponseUtils.isSuccess(response,
                            getString(R.string.error_get_reimbursement_sort))) {
                        JsonArray jsonArray = response.body.getResult().getAsJsonArray();
                        if (null != jsonArray) {
                            int len = jsonArray.size() > 2 ? 3 : jsonArray.size();
                            for (int i = 0; i < len; i++) {
                                mSortReimbursementResultList.add(new ERPListItem(jsonArray.get(i).getAsJsonObject().get(
                                        "user").getAsString(),
                                        jsonArray.get(i).getAsJsonObject().get("totalAmount").getAsFloat()));
                            }
                        }
                    }

                    mSortReimbursementTitleView.getMiddleView().setText("(" + (null == mSortReimbursementResultList ? "0" : mSortReimbursementResultList.size()) + ")");
                    mSortReimbursementAdapter.notifyDataChanged();
                });
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return true;
    }
}
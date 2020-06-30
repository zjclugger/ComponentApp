package com.zjclugger.finance.ui.report;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.jview.OptionsPickerView;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.finance.R;
import com.zjclugger.finance.R2;
import com.zjclugger.finance.ui.FinanceViewModel;
import com.zjclugger.finance.ui.adapter.ReimbursementAdapter;
import com.zjclugger.finance.utils.FinanceConstants;
import com.zjclugger.finance.webapi.entity.response.OrganizationUserResult;
import com.zjclugger.finance.webapi.entity.response.ReimbursementResult;
import com.zjclugger.lib.api.ApiResponseUtils;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.entity.common.ERPListItem;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.view.ExtendLabelValueView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 个人报销统计<br>
 * Created by King.Zi on 2020/02/04.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class PersonalReimbursementFragment extends BaseFragment {
    private final static String TAG = "Statistical";

    @BindView(R2.id.statistical_title)
    ExtendLabelValueView statisticalTitleView;
    @BindView(R2.id.statistical_dimensionality)
    ExtendLabelValueView dimensionalityView;
    @BindView(R2.id.statistical_class)
    ExtendLabelValueView classView;
    @BindView(R2.id.title_statistical_total)
    ExtendLabelValueView totalView;
    @BindView(R2.id.statistical_class_recycler_view)
    RecyclerView classRecyclerView;
    @BindView(R2.id.statistical_datetime)
    ExtendLabelValueView dateTimeView;
    private OptionsPickerView mDimensionalityPickerView;
    private List<ERPListItem> mDimensionalityList = new ArrayList<>();
    private OptionsPickerView mClassPickerView;
    private List<ERPListItem> mClassList = new ArrayList<>();
    private OptionsPickerView mDateTimePickerView;
    private List<ERPListItem> mDateTimeList = new ArrayList<>();

    private ReimbursementAdapter mAdapter;
    private List<ERPListItem<Float>> mDataList = new ArrayList<>();
    protected Map<String, Object> mQueryCondition;
    protected FinanceViewModel mViewModel;
    private List<OrganizationUserResult> mUsersResultList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(FinanceViewModel.class);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_personal_reimbursement;
    }

    @Override
    public void initViews() {
        initDetailTitleViews(getString(R.string.personal_reimbursement), null);
        statisticalTitleView.setLabelText(R.string.personal_reimbursement);
        mQueryCondition = new HashMap<>();
        mQueryCondition.put(FinanceConstants.QueryParameter.STATISTICAL_TYPE, 0);
        mQueryCondition.put(FinanceConstants.QueryParameter.STATISTICAL_TIME, 0);
        // mQueryCondition.put(Constants.QueryParameter.COMPANY_ID, "");
        mQueryCondition.put(Constants.QueryParameter.USER_ID, "");

        mAdapter = new ReimbursementAdapter(0, R.layout.item_reimbursement, mDataList);
        initRecyclerView();

        //纬度
        mDimensionalityPickerView = new OptionsPickerView(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                dimensionalityView.setValueText(String.valueOf(mDimensionalityList.get(options1).getText()));
                mQueryCondition.put(FinanceConstants.QueryParameter.STATISTICAL_TYPE,
                        mDimensionalityList.get(options1).getId());
                getDataFromServer(true);
            }
        });
        mDimensionalityPickerView.getBuilder().setTitleText(getString(R.string.statistical_dimension));
        dimensionalityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDimensionalityPickerView.show();
            }
        });

        //统计对象
        mClassPickerView = new OptionsPickerView(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                classView.setValueText(mUsersResultList.get(options1).getLoginName());
                mQueryCondition.put(Constants.QueryParameter.USER_ID,
                        mUsersResultList.get(options1).getId());
                getDataFromServer(true);
            }
        });
        mClassPickerView.getBuilder().setTitleText(getString(R.string.statistical_object));
        classView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClassPickerView.show();
            }
        });

        //统计时间
        mDateTimePickerView = new OptionsPickerView(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                dateTimeView.setValueText(String.valueOf(mDateTimeList.get(options1).getText()));
                mQueryCondition.put(FinanceConstants.QueryParameter.STATISTICAL_TIME,
                        mDateTimeList.get(options1).getId());
                getDataFromServer(true);
            }
        });
        mDateTimePickerView.getBuilder().setTitleText(getString(R.string.statistical_time));
        dateTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateTimePickerView.show();
            }
        });

        initDataListView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        classRecyclerView.setLayoutManager(layoutManager);
        classRecyclerView.addItemDecoration(ViewUtils.getListDivider(mContext, 2f, true));
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.bindToRecyclerView(classRecyclerView);
    }

    private void initDataListView() {
        initObjectListView();

        //0/最近一年，1/最近半年，2/最近一月，3/最近一周
        mDateTimeList.add(new ERPListItem(0, "最近一年"));
        mDateTimeList.add(new ERPListItem(1, "最近半年"));
        mDateTimeList.add(new ERPListItem(2, "最近一月"));
        mDateTimeList.add(new ERPListItem(3, "最近一周"));
        mDateTimePickerView.bindData(mDateTimeList);

        //TODO:获取维度、对象列表
        //报销事由、票据类型、报销金额
        mDimensionalityList.add(new ERPListItem(0, "报销事由"));
        mDimensionalityList.add(new ERPListItem(1, "票据类型"));
        mDimensionalityList.add(new ERPListItem(2, "报销金额"));
        mDimensionalityPickerView.bindData(mDimensionalityList);
    }

    private void initObjectListView() {
        //从接口获取对象列表
        showWaiting();
        mViewModel.getOrganizationUsers(UserManager.getInstance().getCurrentUser().getCompanyId()).observe(this,
                response -> {
                    if (mUsersResultList != null) {
                        mUsersResultList.clear();
                    }

                    if (ApiResponseUtils.isSuccess(response, getString(R.string.error_get_org_user_failed))) {
                        mUsersResultList = response.body.getResult();
                        mUsersResultList.add(0, new OrganizationUserResult("", "", getString(R.string.all)));
                        mClassPickerView.bindData(mUsersResultList);
                        classView.setValueText(mUsersResultList.get(0).getLoginName());
                        mQueryCondition.put(Constants.QueryParameter.USER_ID, "");
                    }
                    getDataFromServer(false);
                });
    }

    private void getDataFromServer(boolean isRefresh) {
        if (isRefresh) {
            showWaiting();
        }
        mViewModel.getReportForm(mQueryCondition).observe(this,
                response -> {
                    closeProgressDialog();
                    //非分页查询先清空数据
                    if (mDataList != null) {
                        mDataList.clear();
                    }

                    float total = 0f;
                    if (ApiResponseUtils.isSuccess(response, getString(R.string.error_get_data))) {
                        BaseWrapperEntity<ReimbursementResult> wrapperEntity = response.body;
                        if (wrapperEntity != null) {
                            mDataList.addAll(wrapperEntity.getResult().getDataList());
                            total = wrapperEntity.getResult().getTotalValue();
                            totalView.setValueText(String.valueOf(total));
                        }
                    }
                    mAdapter.notifyDataChanged(total);
                });
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}
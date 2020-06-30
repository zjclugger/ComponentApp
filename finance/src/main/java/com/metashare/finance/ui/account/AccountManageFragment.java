package com.zjclugger.finance.ui.account;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.zjclugger.finance.R;
import com.zjclugger.finance.R2;
import com.zjclugger.finance.ui.BaseBillFragment;
import com.zjclugger.finance.ui.adapter.AccountBillAdapter;
import com.zjclugger.finance.utils.FinanceConstants;
import com.zjclugger.finance.webapi.entity.response.AccountBillListResult;
import com.zjclugger.finance.webapi.entity.response.AccountBillResult;
import com.zjclugger.lib.api.ApiResponseUtils;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;
import com.zjclugger.lib.business.UserPermission;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.view.FloatButtonView;
import com.zjclugger.router.utils.ARouterUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 记账凭证 <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AccountManageFragment extends BaseBillFragment {
    private final static String TAG = "account";
    @BindView(R2.id.fbv_add_view)
    FloatButtonView mAddView;
    List<AccountBillResult> mBillResultList;

    @Override
    public List<UserPermission> getPermissionList() {
        List<UserPermission> permissionList = new ArrayList<>();
        /*
        finance:voucher:subject",
        finance:voucher:view",
        finance:voucher:update",
        finance:voucher:*",
        finance:voucher:posting",
        finance:voucher:carryforwardofyear",
        finance:voucher:auditnotpass",
        finance:voucher:create",
        finance:voucher:carryforwardofmonth",
        finance:voucher:auditpass",
        finance:voucher:delete",
         */
        permissionList.add(new UserPermission("finance:voucher:*"));
        permissionList.add(new UserPermission("finance:voucher:view", R.id.bill_recycler_view));
        return permissionList;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_account_manager;
    }

    @Override
    protected void resetQueryCondition() {
        super.resetQueryCondition();
        mQueryCondition.put(FinanceConstants.QueryParameter.VOUCHER_STATUS, 0);   //0待制单
        mQueryCondition.put(FinanceConstants.QueryParameter.PERIOD, "");
    }

    @Override
    public boolean isShowLastLine() {
        return false;
    }

    @Override
    public void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_to_document), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_to_approve));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_to_account));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_accounted));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                resetQueryCondition();
                mBillRecyclerView.setForceTop(true);
                if (tab.getText().equals(getString(R.string.tab_to_document))) {
                    mQueryCondition.put(FinanceConstants.QueryParameter.VOUCHER_STATUS, 0);
                } else if (tab.getText().equals(getString(R.string.tab_to_approve))) {
                    mQueryCondition.put(FinanceConstants.QueryParameter.VOUCHER_STATUS, 1);
                } else if (tab.getText().equals(getString(R.string.tab_to_account))) {
                    mQueryCondition.put(FinanceConstants.QueryParameter.VOUCHER_STATUS, 2);
                } else if (tab.getText().equals(getString(R.string.tab_accounted))) {
                    mQueryCondition.put(FinanceConstants.QueryParameter.VOUCHER_STATUS, 3);
                }

                getDataFromServer(false);
                Log.d(TAG, "TAB selected " + tab.getText());
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
    public void initViews() {
        mBillResultList = new ArrayList<>();
        mQueryCondition = new HashMap<>();
        resetQueryCondition();
        mAddView.setVisibility(View.GONE);
        super.initViews();
    }

    @Override
    public void bindData() {
        mAdapter = new AccountBillAdapter(R.layout.item_account_bill, mBillResultList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mBillResultList != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FinanceConstants.Keywords.KEY_ORIGINAL_BILL_ID,
                            mBillResultList.get(position).getId());

                    ARouterUtils.openDetailFragment(getActivity(),
                            AccountDetailFragment.class.getName(), bundle, R.color.white, false);
                }
            }
        });
    }

    @Override
    public void getDataFromServer(boolean isPagingQuery) {
        mIsPagingQuery = isPagingQuery;
        showWaiting();
        mViewModel.getAccountBillList(mQueryCondition).observe(this,
                response -> {
                    closeProgressDialog();
                    boolean isSuccess = false;
                    boolean isNeedToTop = false;
                    boolean isHasChange = false;

                    if (!mIsPagingQuery) {
                        //非分页查询先清空数据
                        if (mBillResultList != null) {
                            mBillResultList.clear();
                        }
                        isHasChange = true;
                    }

                    if (ApiResponseUtils.isSuccess(response,
                            getString(R.string.error_get_list_failed))) {
                        isSuccess = true;
                        BaseWrapperEntity<AccountBillListResult> wrapperEntity = response.body;
                        Log.d(TAG, "size is " + wrapperEntity.getResult().getBillResultList
                                ().size() + ",page index=" + wrapperEntity.getResult()
                                .getPageIndex() + ",total=" + wrapperEntity.getResult().getTotal());

                        if (!ERPUtils.isEmpty(wrapperEntity.getResult().getBillResultList())) {
                            if (mBillResultList != null) {
                                mBillResultList.clear();
                            }
                            mBillResultList.addAll(wrapperEntity.getResult().getBillResultList());
                            mCurrentPageIndex = wrapperEntity.getResult().getPageIndex();
                            mPageCount = wrapperEntity.getResult().getPageCount();
                            isNeedToTop = true;
                            isHasChange = true;
                        } else {
                            mQueryCondition.put(Constants.QueryParameter.PAGE_INDEX,
                                    mCurrentPageIndex);
                            if (mCurrentPageIndex != 1 || (wrapperEntity.getResult().getTotal() != 0 && wrapperEntity.getResult().getTotal() < mPageSize)) {
                                isNeedToTop = false;
                                toastMessage(mContext, getString(R.string.info_last_page));
                            }
                        }
                    }

                    mBillRecyclerView.refreshList(isSuccess, isNeedToTop, isHasChange);

                    if (!isSuccess) {
                        mAdapter.setEmptyView(false);
                    }
                });
    }
}
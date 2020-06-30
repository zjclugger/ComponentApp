package com.zjclugger.finance.ui.original;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.zjclugger.basiclib.Log;
import com.zjclugger.finance.R;
import com.zjclugger.finance.R2;
import com.zjclugger.finance.ui.BaseBillFragment;
import com.zjclugger.finance.ui.adapter.OriginalBillAdapter;
import com.zjclugger.finance.utils.FinanceConstants;
import com.zjclugger.finance.webapi.entity.response.OriginalBillListResult;
import com.zjclugger.finance.webapi.entity.response.OriginalBillResult;
import com.zjclugger.lib.api.ApiResponseUtils;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.business.UserPermission;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.view.FloatButtonView;
import com.zjclugger.router.utils.ARouterUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 原始凭证<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OriginalManageFragment extends BaseBillFragment {
    private final static String TAG = "original";

    @BindView(R2.id.fbv_add_view)
    FloatButtonView mAddView;

    List<OriginalBillResult> mBillResultList;

    @Override
    public List<UserPermission> getPermissionList() {
        List<UserPermission> permissionList = new ArrayList<>();
        permissionList.add(new UserPermission("finance:increment:*"));
        permissionList.add(new UserPermission("finance:increment:view", R.id.bill_recycler_view));
        return permissionList;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_original_manager;
    }

    @Override
    protected void resetQueryCondition() {
        super.resetQueryCondition();
        mQueryCondition.put(FinanceConstants.QueryParameter.STATUS, 0);   //0/已识别，1/未识别
        mQueryCondition.put(FinanceConstants.QueryParameter.UPLOAD_DATE, ""); //可以为空
        mQueryCondition.put(FinanceConstants.QueryParameter.REASON, ""); //可以为空
        mQueryCondition.put(FinanceConstants.QueryParameter.BILL_TYPE_ID, ""); //可以为空
        mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS, 0); //可以为空
    }

    @Override
    public boolean isShowLastLine() {
        return true;
    }

    @Override
    public void initViews() {
        mBillResultList = new ArrayList<>();
        mQueryCondition = new HashMap<>();
        resetQueryCondition();

        //上传
        mAddView.setOnClickListener(v -> ARouterUtils.navigateBillUpload(getActivity(),
                UserManager.getInstance().getCurrentUser().getCompanyId(),
                getString(R.string.title_bill_upload)));

        super.initViews();
    }

    @Override
    public void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_to_perfect), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_to_check));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_account_entry));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                resetQueryCondition();
                mBillRecyclerView.setForceTop(true);
                if (tab.getText().equals(getString(R.string.tab_to_perfect))) {
                    mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_PERFECT);
                } else if (tab.getText().equals(getString(R.string.tab_to_check))) {
                    mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_CHECK);
                } else if (tab.getText().equals(getString(R.string.tab_account_entry))) {
                    mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_ACCOUNT);
                }

                getDataFromServer(false);
                Log.d("TAB selected " + tab.getText() + ",status=" + mQueryCondition.get(FinanceConstants.QueryParameter.PROOF_STATUS));
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
        mAdapter = new OriginalBillAdapter(R.layout.item_original_bill, mBillResultList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mBillResultList != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FinanceConstants.Keywords.KEY_ORIGINAL_BILL_ID,
                            mBillResultList.get(position).getId());
                    bundle.putString(FinanceConstants.Keywords.KEY_ORIGINAL_UPLOADER,
                            mBillResultList.get(position).getUploader());
                    bundle.putString(FinanceConstants.Keywords.KEY_ORIGINAL_UPLOAD_DATE,
                            mBillResultList.get(position).getUploadDate());

                    ARouterUtils.openDetailFragment(getActivity(),
                            OriginalDetailFragment.class.getName(), bundle, R.color.white, false);
                }
            }
        });
    }

    @Override
    public void getDataFromServer(boolean isPagingQuery) {
        mIsPagingQuery = isPagingQuery;
        showWaiting();
        mViewModel.getOriginalList(mQueryCondition).observe(this,
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
                        BaseWrapperEntity<OriginalBillListResult> wrapperEntity = response.body;
                        Log.d("size is " + wrapperEntity.getResult().getBillResultList
                                ().size() + ",page index=" + wrapperEntity.getResult()
                                .getPageIndex() + ",total=" + wrapperEntity.getResult().getTotal());

                        if (wrapperEntity.getResult().getBillResultListSize() > 0) {
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
                });
    }
}
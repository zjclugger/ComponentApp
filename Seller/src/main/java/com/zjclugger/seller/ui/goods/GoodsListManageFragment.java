package com.zjclugger.seller.ui.goods;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.router.utils.ARouterUtils;
import com.zjclugger.seller.R;
import com.zjclugger.seller.ui.adapter.GoodsAdapter;
import com.zjclugger.seller.utils.SellerConstants;
import com.zjclugger.seller.webapi.response.GoodsResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 商品列表<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class GoodsListManageFragment extends BaseGoodsFragment {
    private final static String TAG = "original";
    @BindView(R.id.layout_left_button)
    Button mGoodsAddButton;
    @BindView(R.id.layout_center_button)
    Button mBatchManageButton;
    @BindView(R.id.layout_right_button)
    Button mCategoryManageButton;
    List<GoodsResult> mGoodsResultList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarStyle(R.color.bg_white, false);
    }

    /*
    @Override
    public List<UserPermission> getPermissionList() {
        List<UserPermission> permissionList = new ArrayList<>();
        permissionList.add(new UserPermission("finance:increment:*"));
        permissionList.add(new UserPermission("finance:increment:view", R.id.bill_recycler_view));
        return permissionList;
    }
*/

    @Override
    public int getLayout() {
        return R.layout.fragment_goods_list;
    }

    @Override
    protected void resetQueryCondition() {
        super.resetQueryCondition();
        mQueryCondition.put(SellerConstants.QueryParameter.STATUS, 0);   //0/已识别，1/未识别
        mQueryCondition.put(SellerConstants.QueryParameter.UPLOAD_DATE, ""); //可以为空
        mQueryCondition.put(SellerConstants.QueryParameter.REASON, ""); //可以为空
        mQueryCondition.put(SellerConstants.QueryParameter.BILL_TYPE_ID, ""); //可以为空
        mQueryCondition.put(SellerConstants.QueryParameter.PROOF_STATUS, 0); //可以为空
    }

    @Override
    public boolean isShowLastLine() {
        return true;
    }

    @Override
    public void initViews() {
        mGoodsResultList = new ArrayList<>();
        mQueryCondition = new HashMap<>();
        resetQueryCondition();
        initDetailTitleViews("商品管理");

        ViewUtils.setVisibility(true, mGoodsAddButton, mBatchManageButton, mCategoryManageButton);
        mGoodsAddButton.setText("添加商品");
        mGoodsAddButton.setOnClickListener(v -> {
            GoodsResult goodsResult = new GoodsResult();
            goodsResult.setName("");
            Bundle params = new Bundle();
            params.putParcelable(SellerConstants.Keywords.KEY_GOODS_MANAGE, goodsResult);
            ARouterUtils.openDetailFragment(getActivity(),
                    GoodsManagerFragment.class.getName(), params, R.color.bg_white, false);
        });
        mBatchManageButton.setText("批量管理");
        mBatchManageButton.setOnClickListener(v -> {
            ARouterUtils.openDetailFragment(getActivity(),
                    GoodsBatchManageFragment.class.getName(), null, R.color.bg_white, false);
        });
        mCategoryManageButton.setText("分类管理");
        mCategoryManageButton.setOnClickListener(v -> {
            ARouterUtils.openDetailFragment(getActivity(),
                    CategoryManageFragment.class.getName(), null, R.color.bg_white, false);
        });
        super.initViews();
    }

    @Override
    public void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.goods_to_shelves), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.goods_on_shelves));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                resetQueryCondition();
                mGoodsRecyclerView.setForceTop(true);
                if (tab.getText().equals(getString(R.string.goods_on_shelves))) {
                    /*mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_PERFECT);*/
                } else {
                   /* mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_CHECK);*/
                }

                getDataFromServer(false);
                // Log.d("TAB selected " + tab.getText() + ",status=" + mQueryCondition.get
                // (FinanceConstants.QueryParameter.PROOF_STATUS));
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
        mAdapter = new GoodsAdapter(R.layout.item_goods_list, mGoodsResultList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mGoodsResultList != null) {

                    Bundle params = new Bundle();
                    params.putParcelable(SellerConstants.Keywords.KEY_GOODS_MANAGE,
                            mGoodsResultList.get(position));
                    params.putBoolean(SellerConstants.Keywords.KEY_GOODS_MANAGE_READ_ONLY, true);
                    ARouterUtils.openDetailFragment(getActivity(),
                            GoodsManagerFragment.class.getName(), params, R.color.bg_white, false);
                  /*  Bundle bundle = new Bundle();
                    bundle.putString(FinanceConstants.Keywords.KEY_ORIGINAL_BILL_ID,
                            mGoodsResultList.get(position).getId());
                    bundle.putString(FinanceConstants.Keywords.KEY_ORIGINAL_UPLOADER,
                            mGoodsResultList.get(position).getUploader());
                    bundle.putString(FinanceConstants.Keywords.KEY_ORIGINAL_UPLOAD_DATE,
                            mGoodsResultList.get(position).getUploadDate());

                    ARouterUtils.openDetailFragment(getActivity(),
                            OriginalDetailFragment.class.getName(), bundle, R.color.white, false);*/
                }
            }
        });
    }

    @Override
    public void getDataFromServer(boolean isPagingQuery) {
        initListData();
     /*   mIsPagingQuery = isPagingQuery;
        showWaiting();
        mViewModel.getOriginalList(mQueryCondition).observe(this,
                response -> {
                    closeProgressDialog();
                    boolean isSuccess = false;
                    boolean isNeedToTop = false;
                    boolean isHasChange = false;

                    if (!mIsPagingQuery) {
                        //非分页查询先清空数据
                        if (mGoodsResultList != null) {
                            mGoodsResultList.clear();
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
                            if (mGoodsResultList != null) {
                                mGoodsResultList.clear();
                            }
                            mGoodsResultList.addAll(wrapperEntity.getResult().getBillResultList());
                            mCurrentPageIndex = wrapperEntity.getResult().getPageIndex();
                            mPageCount = wrapperEntity.getResult().getPageCount();
                            isNeedToTop = true;
                            isHasChange = true;
                        } else {
                            mQueryCondition.put(Constants.QueryParameter.PAGE_INDEX,
                                    mCurrentPageIndex);
                            if (mCurrentPageIndex != 1 || (wrapperEntity.getResult().getTotal()
                            != 0 && wrapperEntity.getResult().getTotal() < mPageSize)) {
                                isNeedToTop = false;
                                toastMessage(mContext, getString(R.string.info_last_page));
                            }
                        }
                    }

                    mGoodsRecyclerView.refreshList(isSuccess, isNeedToTop, isHasChange);
                });*/
    }

    private void initListData() {
        //TODO：内容不一样
        List<String> urlList = new ArrayList<>();
        urlList.add("http://img.qqzhi.com/uploads/2019-04-06/103454404.jpg");
        urlList.add("http://img.qqzhi.com/uploads/2019-03-26/203922802.jpg");
        urlList.add("http://img.qqzhi.com/uploads/2019-03-25/153128925.jpg");
        urlList.add("http://img.qqzhi.com/uploads/2019-04-03/060630691.jpg");
        urlList.add("http://img.qqzhi.com/uploads/2019-03-25/121825762.jpg");

        GoodsResult result;
        for (int i = 0; i < 10; i++) {
            result = new GoodsResult();
            result.setId(String.valueOf(400 + i));
            result.setName("商品" + result.getId());
            result.setDescription("附近商家" + result.getId());
            result.setGoodsImageUrl(urlList);
            result.setCategoryId(333);
            result.setCategoryName("分类3");
            result.setSalesVolume(3000);
            result.setStock(800);
            result.setPrice(400 + i);
            mGoodsResultList.add(result);
        }
        mAdapter.notifyDataChanged();
    }
}
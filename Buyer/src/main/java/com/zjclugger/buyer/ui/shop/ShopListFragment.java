package com.zjclugger.buyer.ui.shop;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabLayout;
import com.zjclugger.buyer.R;
import com.zjclugger.buyer.ui.vm.BuyerViewModel;
import com.zjclugger.buyer.utils.BuyerConstants;
import com.zjclugger.buyer.webapi.response.GoodsCategoryResult;
import com.zjclugger.buyer.webapi.response.GoodsResult;
import com.zjclugger.buyer.webapi.response.ShopResult;
import com.zjclugger.lib.base.BaseListFragment;
import com.zjclugger.lib.view.ExtendsRecyclerView;
import com.zjclugger.router.utils.ARouterUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 商品列表<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ShopListFragment extends BaseListFragment {
    private final static String TAG = "FoodShopList";

    @BindView(R.id.food_list_tabs)
    TabLayout mTabLayout;
    List<ShopResult> mShopList;
    @BindView(R.id.recycler_view)
    ExtendsRecyclerView mRecyclerView;
    BuyerViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(BuyerViewModel.class);
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
        return R.layout.fragment_shop_list;
    }

    @Override
    public ExtendsRecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    protected void resetQueryCondition() {
        super.resetQueryCondition();
       /* mQueryCondition.put(SellerConstants.QueryParameter.STATUS, 0);   //0/已识别，1/未识别
        mQueryCondition.put(SellerConstants.QueryParameter.PROOF_STATUS, 0); //可以为空*/
    }

    @Override
    public boolean isShowLastLine() {
        return true;
    }

    @Override
    public void initViews() {
        mShopList = new ArrayList<>();
        mQueryCondition = new HashMap<>();
        resetQueryCondition();

        super.initViews();
    }

    @Override
    public void initTabLayout() {
        initDetailTitleViews("美食");
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.all), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.food_lunch));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.food_home_cooking));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.food_porridge_shop));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.food_japanese_korean_cuisine));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.text_other));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                resetQueryCondition();
                mRecyclerView.setForceTop(true);
                if (tab.getText().equals(getString(R.string.all))) {
                    /*mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_PERFECT);*/
                } else if (tab.getText().equals(getString(R.string.food_lunch))) {
                   /* mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_CHECK);*/
                } else if (tab.getText().equals(getString(R.string.food_home_cooking))) {
                   /* mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_CHECK);*/
                } else if (tab.getText().equals(getString(R.string.food_porridge_shop))) {
                   /* mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_CHECK);*/
                } else if (tab.getText().equals(getString(R.string.food_japanese_korean_cuisine))) {
                   /* mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_CHECK);*/
                } else if (tab.getText().equals(getString(R.string.text_other))) {
                   /* mQueryCondition.put(FinanceConstants.QueryParameter.PROOF_STATUS,
                            FinanceConstants.ProofStatus.TO_CHECK);*/
                }

                getDataFromServer(false);
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
        mAdapter = new ShopAdapter(R.layout.item_shop_list, mShopList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mShopList != null) {
                Bundle params = new Bundle();
                params.putParcelable(BuyerConstants.Keywords.KEY_SHOP_INFO,
                        mShopList.get(position));
                ARouterUtils.openDetailFragment(getActivity(),
                        ShopDetailFragment.class.getName(), params, R.color.white, false);

           /*     Bundle params = new Bundle();
                params.putParcelable(SellerConstants.Keywords.KEY_GOODS_MANAGE,
                        mFoodShopList.get(position));
                params.putBoolean(SellerConstants.Keywords.KEY_GOODS_MANAGE_READ_ONLY, true);
                ARouterUtils.openDetailFragment(getActivity(),
                        GoodsManagerFragment.class.getName(), params, R.color.bg_white,
                        false);*/
              /*  Bundle bundle = new Bundle();
                bundle.putString(FinanceConstants.Keywords.KEY_ORIGINAL_BILL_ID,
                        mFoodShopList.get(position).getId());
                bundle.putString(FinanceConstants.Keywords.KEY_ORIGINAL_UPLOADER,
                        mFoodShopList.get(position).getUploader());
                bundle.putString(FinanceConstants.Keywords.KEY_ORIGINAL_UPLOAD_DATE,
                        mFoodShopList.get(position).getUploadDate());

                ARouterUtils.openDetailFragment(getActivity(),
                        OriginalDetailFragment.class.getName(), bundle, R.color.white, false);*/
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
                        if (mFoodShopList != null) {
                            mFoodShopList.clear();
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
                            if (mFoodShopList != null) {
                                mFoodShopList.clear();
                            }
                            mFoodShopList.addAll(wrapperEntity.getResult().getBillResultList());
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

        ShopResult result;
        for (int i = 0; i < 10; i++) {
            result = new ShopResult();
            result.setId(String.valueOf(400 + i));
            result.setName("店铺名" + result.getId());
            result.setDescription("商家" + result.getId());
            result.setImageUrl(urlList);
            result.setCategoryId(222);
            result.setCategoryName("快餐" + i);
            result.setFloorPrice(15 + i);
            result.setScore(3.5f);
            result.setAddress("朝阳区" + i);
            result.setStatus("正常营业");
            result.setBusinessHours("7X24小时");
            result.setMonthSalesVolume(335 + i);
            GoodsResult goodsResult;
            for (int y = 0; y < 10; y++) {
                goodsResult = new GoodsResult();
                goodsResult.setId(String.valueOf(400 + y));
                goodsResult.setName("商品" + result.getId());
                goodsResult.setBusinessName(result.getName());
                goodsResult.setUrl("http://img.qqzhi.com/uploads/2018-11-29/120313953.jpg");
                goodsResult.setFloorPrice(0.01d);
                goodsResult.setScore(3.5f);
                goodsResult.setPrice(400 + y);
                result.getGoodsList().add(goodsResult);
            }
            GoodsCategoryResult goodsCategoryResult;
            for (int m = 0; m < 12; m++) {
                goodsCategoryResult = new GoodsCategoryResult();
                goodsCategoryResult.setId(600 + m);
                goodsCategoryResult.setName(result.getName() + "-分类" + m);
                result.getGoodsCategoryResultList().add(goodsCategoryResult);
            }
            mShopList.add(result);
        }
        mAdapter.notifyDataChanged();
    }
}
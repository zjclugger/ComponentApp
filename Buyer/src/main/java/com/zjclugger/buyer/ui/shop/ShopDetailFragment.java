package com.zjclugger.buyer.ui.shop;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.zjclugger.buyer.R;
import com.zjclugger.buyer.utils.BuyerConstants;
import com.zjclugger.buyer.webapi.response.ShopResult;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.common.JListItem;
import com.zjclugger.lib.ui.adapter.JImageTextAdapter;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.view.recyclerview.JGridLayoutManager;
import com.zjclugger.lib.view.recyclerview.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商铺详情<br>
 * Created by King.Zi on 2020/4/22.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ShopDetailFragment extends BaseFragment {
    private final static String TAG = "ShopDetail";

    @BindView(R.id.shop_detail_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.shop_info_layout)
    NestedScrollView mShopInfoLayout;
    @BindView(R.id.shop_image_recycler_view)
    RecyclerView mShopImageRecyclerView;
    @BindView(R.id.shop_introduction)
    TextView mShopIntroductionView;
    @BindView(R.id.shop_business_hours)
    TextView mShopTimeView;
    @BindView(R.id.shop_telephone)
    TextView mShopTelephoneView;
    @BindView(R.id.shop_address)
    TextView mShopAddressView;
    //category layout
    @BindView(R.id.shop_category_layout)
    NestedScrollView mCategoryListLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mCategoryRecyclerView;
    ShopResult mShopInfo;
    @BindView(R.id.content_fragment)
    FrameLayout mShopGoodsLayout;
    @BindView(R.id.shop_image)
    ImageView mShopImageView;

    @Override
    public int getLayout() {
        return R.layout.fragment_shop_detail;
    }

    @Override
    public void initViews() {
        if (null != mArguments) {
            mShopInfo = mArguments.getParcelable(BuyerConstants.Keywords.KEY_SHOP_INFO);
        }

        Log.d("KZ", "mShopInfo====" + mShopInfo);
        if (null != mShopInfo) {
            initTabLayout();

            //TODO:初始化数据
            //init shop info layout
            initDetailTitleViews(mShopInfo.getName());
            ImageUtils.loadImage(mContext, mShopImageView, mShopInfo.getImageUrl().get(0));
            mShopImageRecyclerView.setLayoutManager(new JGridLayoutManager(mContext, 3,
                    GridLayoutManager.VERTICAL, false));
            mShopImageRecyclerView.addItemDecoration(new GridItemDecoration.Builder(mContext)
                    .setHorizontalSpan(R.dimen.margin_smallest)
                    .setVerticalSpan(R.dimen.margin_smallest)
                    .setColorResource(R.color.bg_white)
                    .setShowLastLine(false)
                    .build());
            List<JListItem<String>> imageTextList = new ArrayList<>();
            if (null != mShopInfo.getImageUrl()) {
                for (int i = 0; i < mShopInfo.getImageUrl().size(); i++) {
                    imageTextList.add(new JListItem<>("", mShopInfo.getImageUrl().get(i)));
                }
            }
            JImageTextAdapter imageTextAdapter = new JImageTextAdapter(imageTextList);
            imageTextAdapter.setPreview(true);
            imageTextAdapter.setLayoutSize(ViewUtils.dp2px(mContext, 80),
                    ViewUtils.dp2px(mContext, 80));
            imageTextAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            imageTextAdapter.bindToRecyclerView(mShopImageRecyclerView);


            //init shop category list layout
            // CategoryDetailFragment
            CategoryGoodsFragment fragment = new CategoryGoodsFragment();
            mArguments.putParcelable(BuyerConstants.Keywords.KEY_SHOP_DETAIL, mShopInfo);
            fragment.setArguments(mArguments);
            FragmentUtils.addFirstFragment(getActivity(), fragment, R.id.content_fragment);

            getDataFromServer(false);
        } else {
            close();
        }
    }

    private void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.text_goods), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.text_introduction));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals(getString(R.string.text_goods))) {
                    mShopInfoLayout.setVisibility(View.GONE);
                    mShopGoodsLayout.setVisibility(View.VISIBLE);
                    //mCategoryListLayout.setVisibility(View.VISIBLE);
                } else if (tab.getText().equals(getString(R.string.text_introduction))) {
                    mShopInfoLayout.setVisibility(View.VISIBLE);
                    mShopGoodsLayout.setVisibility(View.GONE);
                    //mCategoryListLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void bindData() {
       /* mAdapter = new FoodShopAdapter(R.layout.item_food_shop_list, mFoodShopList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mFoodShopList != null) {


               *//*     Bundle params = new Bundle();
                    params.putParcelable(SellerConstants.Keywords.KEY_GOODS_MANAGE,
                            mFoodShopList.get(position));
                    params.putBoolean(SellerConstants.Keywords.KEY_GOODS_MANAGE_READ_ONLY, true);
                    ARouterUtils.openDetailFragment(getActivity(),
                            GoodsManagerFragment.class.getName(), params, R.color.bg_white,
                            false);*//*
         *//*  Bundle bundle = new Bundle();
                    bundle.putString(FinanceConstants.Keywords.KEY_ORIGINAL_BILL_ID,
                            mFoodShopList.get(position).getId());
                    bundle.putString(FinanceConstants.Keywords.KEY_ORIGINAL_UPLOADER,
                            mFoodShopList.get(position).getUploader());
                    bundle.putString(FinanceConstants.Keywords.KEY_ORIGINAL_UPLOAD_DATE,
                            mFoodShopList.get(position).getUploadDate());

                    ARouterUtils.openDetailFragment(getActivity(),
                            OriginalDetailFragment.class.getName(), bundle, R.color.white, false)
                            ;*//*
                }
            }
        });*/
    }

    private void getDataFromServer(boolean isPagingQuery) {
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
      /*  //TODO：内容不一样
        List<String> urlList = new ArrayList<>();
        urlList.add("http://img.qqzhi.com/uploads/2019-04-06/103454404.jpg");
        urlList.add("http://img.qqzhi.com/uploads/2019-03-26/203922802.jpg");
        urlList.add("http://img.qqzhi.com/uploads/2019-03-25/153128925.jpg");
        urlList.add("http://img.qqzhi.com/uploads/2019-04-03/060630691.jpg");
        urlList.add("http://img.qqzhi.com/uploads/2019-03-25/121825762.jpg");

        FoodShopResult result;
        for (int i = 0; i < 10; i++) {
            result = new FoodShopResult();
            result.setId(String.valueOf(400 + i));
            result.setName("美食店铺名" + result.getId());
            result.setDescription("商家" + result.getId());
            result.setImageUrl(urlList);
            result.setCategoryId(222);
            result.setCategoryName("快餐" + i);
            result.setFloorPrice(15 + i);
            result.setScore(3.5f);
            result.setAddress("朝阳区" + i);
            result.setStatus("正常营业");
            result.setMonthSalesVolume(335 + i);
            mFoodShopList.add(result);
        }
        mAdapter.notifyDataChanged();*/
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}
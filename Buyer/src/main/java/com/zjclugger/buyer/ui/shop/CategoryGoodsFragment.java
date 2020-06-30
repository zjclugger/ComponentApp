package com.zjclugger.buyer.ui.shop;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.buyer.R;
import com.zjclugger.buyer.ui.adapter.ShopGoodsAdapter;
import com.zjclugger.buyer.ui.goods.GoodsActivity;
import com.zjclugger.buyer.ui.shopcart.ShopCartFragment;
import com.zjclugger.buyer.utils.BuyerConstants;
import com.zjclugger.buyer.webapi.response.GoodsCategoryResult;
import com.zjclugger.buyer.webapi.response.GoodsResult;
import com.zjclugger.buyer.webapi.response.ShopResult;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.router.utils.ARouterUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 分类显示商品<br>
 * Created by King.Zi on 2020/4/22.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class CategoryGoodsFragment extends BaseFragment {
    private final static String TAG = "CategoryGoods";

    @BindView(R.id.category_detail_recycler_view)
    RecyclerView mDetailRecyclerView;
    @BindView(R.id.category_scrollview)
    ScrollView mCategoryListScrollView;
    @BindView(R.id.category_layout)
    LinearLayout mCategoryLayout;
    @BindView(R.id.shopping_cart_layout)
    RelativeLayout mShoppingCartLayout;
    @BindView(R.id.shopping_cart_settlement)
    TextView mSettlementView;

    private TextView[] mTextViewList;
    //装ScrollView的item的数组
    private View[] mViewList;
    private int mScrollViewWidth = 0;
    private int mScrollViewMiddle = 0;

    private ShopResult mShopInfo;
    private String mCategoryName;
    private List<GoodsCategoryResult> mCategoryResultList;
    private List<GoodsResult> mGoodsList = new ArrayList<>();
    private ShopGoodsAdapter mGoodsAdapter;

    @Override
    public int getLayout() {
        return R.layout.fragment_category_goods;
    }

    @Override
    public void initViews() {
        mCategoryListScrollView.setLayoutParams(new LinearLayout.LayoutParams(ViewUtils.getScreenWidth(mContext) / 4, ViewGroup.LayoutParams.MATCH_PARENT));
        if (null != mArguments) {
            mShopInfo = mArguments.getParcelable(BuyerConstants.Keywords.KEY_SHOP_DETAIL);
        }

        Log.d("KZ", "mShopInfo====" + mShopInfo);
        if (null != mShopInfo) {
            mCategoryResultList = mShopInfo.getGoodsCategoryResultList();
            mCategoryName = mCategoryResultList.get(0).getName();
            mTextViewList = new TextView[mCategoryResultList.size()];
            mViewList = new View[mCategoryResultList.size()];
            addView();
            changeTextColor(0);
            mShoppingCartLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WidgetUtils.toast(mContext, "点击了购物车", false);

                    ARouterUtils.openDetailFragment(getActivity(),
                            ShopCartFragment.class.getName(), null, R.color.white, false);
                }
            });

            mSettlementView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WidgetUtils.toast(mContext, "点击了结算", false);
                }
            });

            //TODO:初始化数据
            //init shop info layout
            mDetailRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mDetailRecyclerView.addItemDecoration(ViewUtils.getListDivider(mContext, 1f, true));
            mGoodsAdapter = new ShopGoodsAdapter(R.layout.item_goods_list, mGoodsList);
            mGoodsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            mGoodsAdapter.bindToRecyclerView(mDetailRecyclerView);
            mGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(mContext, GoodsActivity.class));
                }
            });

            //init shop category list layout
            getDataFromServer(false);
        } else {
            close();
        }
    }

    /**
     * category_detail_recycler_view
     * 给ScrollView添加子View
     */
    private void addView() {
        View view;
        for (int x = 0; x < mCategoryResultList.size(); x++) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_category_list, null);
            view.setId(x);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mGoodsList) {
                        mGoodsList.clear();
                    }
                    //todo:点击分类
                    changeTextColor(v.getId());
                    changeTextLocation(v.getId());
                    mCategoryName = mCategoryResultList.get(v.getId()).getName();
                    //TODO:根据ID从接口获取分类下的商品列表
                    getDataFromServer(false);
                }
            });
            TextView tv = view.findViewById(R.id.category_name);
            tv.setText(mCategoryResultList.get(x).getName());
            mCategoryLayout.addView(view);

            mTextViewList[x] = tv;
            mViewList[x] = view;
        }
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
        //for test
        GoodsResult goodsResult;
        for (int i = 0; i < 30; i++) {
            goodsResult = new GoodsResult();
            goodsResult.setId(String.valueOf(700 + i));
            goodsResult.setName(mCategoryName + "-商品名" + goodsResult.getId());
            goodsResult.setDescription("这是商品描述" + i);
            goodsResult.setUrl("http://img.qqzhi.com/uploads/2018-11-29/120313953.jpg");
            goodsResult.setFloorPrice(0.01d);
            goodsResult.setScore(3.5f);
            goodsResult.setPrice(400 + i);
            mGoodsList.add(goodsResult);
        }

        mGoodsAdapter.notifyDataChanged();

      /*  //TODO：内容不一样
        List<String> urlList = new ArrayList<>();
        urlList.add("http://img.qqzhi.com/uploads/2019-04-06/103454404.jpg");
        urlList.add("http://img.qqzhi.com/uploads/2019-03-26/203922802.jpg");
        urlList.add("http://img.qqzhi.com/uploads/2019-03-25/153128925.jpg");
        urlList.add("http://img.qqzhi.com/uploads/2019-04-03/060630691.jpg");
        urlList.add("http://img.qqzhi.com/uploads/2019-03-25/121825762.jpg");

        FoodShopResult goodsResult;
        for (int i = 0; i < 10; i++) {
            goodsResult = new FoodShopResult();
            goodsResult.setId(String.valueOf(400 + i));
            goodsResult.setName("美食店铺名" + goodsResult.getId());
            goodsResult.setDescription("商家" + goodsResult.getId());
            goodsResult.setImageUrl(urlList);
            goodsResult.setCategoryId(222);
            goodsResult.setCategoryName("快餐" + i);
            goodsResult.setFloorPrice(15 + i);
            goodsResult.setScore(3.5f);
            goodsResult.setAddress("朝阳区" + i);
            goodsResult.setStatus("正常营业");
            goodsResult.setMonthSalesVolume(335 + i);
            mFoodShopList.add(goodsResult);
        }
        mAdapter.notifyDataChanged();*/
    }

    @Override
    public boolean doBackPress() {
        return false;
    }


    /**
     * 改变textView的颜色
     *
     * @param id
     */
    private void changeTextColor(int id) {
        for (int i = 0; i < mTextViewList.length; i++) {
            if (i != id) {
                mTextViewList[i].setBackgroundResource(R.color.lighter_gray);
                mTextViewList[i].setTextColor(getResources().getColor(R.color.black));
            } else {
                mTextViewList[id].setBackgroundResource(R.color.bg_white);
                mTextViewList[id].setTextColor(getResources().getColor(R.color.text_selected));
            }
        }
    }

    /**
     * 改变栏目位置
     */
    private void changeTextLocation(int index) {
        int x = (mViewList[index].getTop() - getScrollViewMiddle() + (getViewHeight(mViewList[index]) / 2));
        mCategoryListScrollView.smoothScrollTo(0, x);
    }

    /**
     * 返回scrollview的中间位置
     *
     * @return
     */
    private int getScrollViewMiddle() {
        if (mScrollViewMiddle == 0)
            mScrollViewMiddle = getScrollViewHeight() / 2;
        return mScrollViewMiddle;
    }

    /**
     * 返回ScrollView的宽度
     *
     * @return
     */
    private int getScrollViewHeight() {
        if (mScrollViewWidth == 0)
            mScrollViewWidth =
                    mCategoryListScrollView.getBottom() - mCategoryListScrollView.getTop();
        return mScrollViewWidth;
    }

    /**
     * 返回view的宽度
     *
     * @param view
     * @return
     */
    private int getViewHeight(View view) {
        return view.getBottom() - view.getTop();
    }
}
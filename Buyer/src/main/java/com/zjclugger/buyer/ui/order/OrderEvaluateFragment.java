package com.zjclugger.buyer.ui.order;

import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zjclugger.buyer.R;
import com.zjclugger.buyer.utils.BuyerConstants;
import com.zjclugger.buyer.webapi.response.GoodsResult;
import com.zjclugger.buyer.webapi.response.ShopResult;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.SerializableMap;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.LabelEditTextView;
import com.zjclugger.lib.view.NestedExpandableListView;
import com.zjclugger.router.ARouterConstants;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 商品评论 <br>
 * Created by King.Zi on 2020/5/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OrderEvaluateFragment extends BaseFragment {
    private static final String TAG = "GoodsEvaluate";

    @BindView(R.id.expand_list_view)
    NestedExpandableListView mExpandableListView;
    @BindView(R.id.goods_score)
    MaterialRatingBar mGoodsScoreBar;
    @BindView(R.id.logistics_score)
    MaterialRatingBar mLogisticsScoreBar;
    @BindView(R.id.service_score)
    MaterialRatingBar mServiceScoreBar;
    @BindView(R.id.evaluate_detail_view)
    LabelEditTextView mEvaluateDetailView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.empty_view)
    TextView mEmptyView;

    private OrderEvaluateAdapter mOrderEvaluateAdapter;    //评价
    private List<ShopResult> mShopResultList; //组元素的列表[商铺]
    private Map<String, List<GoodsResult>> mShopGoodsMap; //子元素的列表[商品]
    private BaseFragment mPictureFragment;

    @Override
    public boolean isPublicPermission() {
        return true;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_goods_evaluate;
    }

    @Override
    public void initViews() {
        mShopResultList = new ArrayList<>();
        mShopGoodsMap = new HashMap<>();
        if (null == mArguments) {
            close();
        } else {
            //1.init view
            initDetailTitleViews("商品评价");
            initRefresh();

            //2.get data from argument
            mShopResultList =
                    mArguments.getParcelableArrayList(BuyerConstants.Keywords.KEY_ORDER_SHOP_LIST);
            SerializableMap<String, List<GoodsResult>> goodsList = (SerializableMap<String,
                    List<GoodsResult>>) mArguments.getSerializable(BuyerConstants.Keywords.KEY_ORDER_SHOP_GOODS_LIST);
            if (null != goodsList) {
                mShopGoodsMap = goodsList.getMap();
            }

            mPictureFragment =
                    (BaseFragment) ARouter.getInstance().build(ARouterConstants.Path.COM_SELECT_MEDIA)
                            .withBoolean(ARouterConstants.KeyWord.KEY_MEDIA_IS_PICTURE_SMALL, false)
                            .withBoolean(ARouterConstants.KeyWord.KEY_MEDIA_IS_SINGLE_LAYOUT, false)
                            .withInt(ARouterConstants.KeyWord.KEY_MEDIA_GRID_SPAN_COUNT, 3)
                            .withInt(ARouterConstants.KeyWord.KEY_MEDIA_MAX_NUM, 6)
                            .withInt(ARouterConstants.KeyWord.KEY_MEDIA_BACKGROUND_COLOR,
                                    R.color.bg_white)
                            .navigation();

            FragmentUtils.addFirstFragment(getActivity(), mPictureFragment, R.id.picture_content);

            mGoodsScoreBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
                @Override
                public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                    WidgetUtils.toast(mContext, rating + "分", false);
                }
            });
            //3.fill data
            initListView();
        }
    }

    private void initRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //TODO:刷新
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //TODO:刷新
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void initListView() {
        //init adapter
        mOrderEvaluateAdapter = new OrderEvaluateAdapter(getActivity(), mShopResultList,
                mShopGoodsMap);

        //init list view
        mExpandableListView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头
        mExpandableListView.setAdapter(mOrderEvaluateAdapter);

        //关键步骤4:初始化，将ExpandableListView以展开的方式显示
        for (int i = 0; i < mOrderEvaluateAdapter.getGroupCount(); i++) {
            mExpandableListView.expandGroup(i);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mOrderEvaluateAdapter = null;
        mShopGoodsMap.clear();
        mShopResultList.clear();
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}
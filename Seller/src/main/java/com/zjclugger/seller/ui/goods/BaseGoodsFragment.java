package com.zjclugger.seller.ui.goods;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabLayout;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.zjclugger.basiclib.Log;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.business.User;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.ExtendsRecyclerView;
import com.zjclugger.seller.R;
import com.zjclugger.seller.ui.vm.SellerViewModel;
import com.zjclugger.seller.utils.SellerConstants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Map;

import butterknife.BindView;

/**
 * 票据列表基类<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public abstract class BaseGoodsFragment extends BaseFragment {
    private final static String TAG = "Goods";
    protected User mCurrentUser;
    protected SellerViewModel mViewModel;
    protected String mCompanyName;
    protected String mStartDate;
    protected String mEntryDate;
    protected Bundle mSearchCondition;
    protected Map<String, Object> mQueryCondition;
    //分页参数
    protected int mCurrentPageIndex = 1;
    protected int mPageCount;
    protected int mPageSize = 10;

    @BindView(R.id.goods_list_tabs)
    protected TabLayout mTabLayout;
    @BindView(R.id.goods_recycler_view)
    protected ExtendsRecyclerView mGoodsRecyclerView;

    protected JBaseQuickAdapter mAdapter;
    protected boolean mIsPagingQuery;

    public abstract void getDataFromServer(boolean isPagingQuery);

    public abstract void initTabLayout();

    public abstract void bindData();

    public abstract boolean isShowLastLine();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarStyle(R.color.white, false);
        mViewModel = ViewModelProviders.of(getActivity()).get(SellerViewModel.class);
        mCurrentUser = UserManager.getInstance().getCurrentUser();
        mSearchCondition = new Bundle();
        LiveEventBus.get(SellerConstants.Keywords.KEY_SEARCH_CONDITION, Bundle.class)
                .observe(this, new Observer<Bundle>() {
                    @Override
                    public void onChanged(@Nullable Bundle data) {
                        mSearchCondition = data;
                    }
                });
    }

    @Override
    public boolean isPublicPermission() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden()) {
            if (mSearchCondition != null && !mSearchCondition.isEmpty()) {
                //获取查询条件并发起查询请求，然后清空condition
                mCompanyName = mSearchCondition.getString(SellerConstants.Keywords.KEY_SEARCH_TEXT,
                        "");
                mStartDate =
                        mSearchCondition.getString(SellerConstants.Keywords.KEY_SEARCH_START_DATE, "");
                mEntryDate =
                        mSearchCondition.getString(SellerConstants.Keywords.KEY_SEARCH_ENTRY_DATE, "");
                mSearchCondition = null;
            }
            //查询条件非空，自动获取数据(TODO: 把查询条件也加到参数中)
            getDataFromServer(mIsPagingQuery);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoodsRecyclerView.setForceTop(false);
    }

    @Override
    public void onShown() {
        super.onShown();
    }

    @Override
    public void initViews() {
        //1. init tab list
        initTabLayout();
        //2. bind data
        bindData();

        //先要初始化Adapter
        mGoodsRecyclerView.setRefreshLayoutListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉顶部刷新
                if (mCurrentPageIndex != 1) {
                    mQueryCondition.put(Constants.QueryParameter.PAGE_INDEX, mCurrentPageIndex - 1);
                    mGoodsRecyclerView.setForceTop(true);
                    getDataFromServer(true);
                } else {
                    toastMessage(mContext, getString(R.string.info_first_page));
                    refreshLayout.finishRefresh();
                }
            }
        }, new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.d("page count=" + mPageCount + ",page index=" + mCurrentPageIndex);
                //上滑底部加载更多
                //load more
                mQueryCondition.put(Constants.QueryParameter.PAGE_INDEX, mCurrentPageIndex + 1);
                mGoodsRecyclerView.setForceTop(true);
                getDataFromServer(true);
            }
        }).create(true, 3, isShowLastLine(), mAdapter, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromServer(mIsPagingQuery);
            }
        });
    }

    protected void resetQueryCondition() {
        mQueryCondition.put(Constants.QueryParameter.PAGE_INDEX, 1);
        mQueryCondition.put(Constants.QueryParameter.PAGE_SIZE, mPageSize);
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return true;
    }

    protected void toastMessage(Context context, String message) {
        WidgetUtils.toast(mContext, message, false);
    }

    @Override
    public void onSearchViewClick() {
     /*   ARouterUtils.openDetailFragment(getActivity(), SearchKeywordFragment.class.getName(),
                null, R.color.white, false);*/
    }

    @Override
    public boolean isSearchViewTitle() {
        return false;
    }

    @Override
    public void onFilterViewClick() {
       /* ARouterUtils.openDetailFragment(getActivity(), SearchFilterFragment.class.getName(), null
                , R.color.white, false);*/
    }
}
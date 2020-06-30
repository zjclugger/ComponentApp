package com.zjclugger.lib.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.zjclugger.basiclib.Log;
import com.zjclugger.lib.R;
import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.ExtendsRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Map;

/**
 * 列表基类<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public abstract class BaseListFragment extends BaseFragment {
    private final static String TAG = "BaseList";
    protected Bundle mSearchCondition;
    protected Map<String, Object> mQueryCondition;
    //分页参数
    protected int mCurrentPageIndex = 1;
    protected int mPageCount;
    protected int mPageSize = 10;

    protected JBaseQuickAdapter mAdapter;
    protected boolean mIsPagingQuery;

    public abstract ExtendsRecyclerView getRecyclerView();

    public abstract void getDataFromServer(boolean isPagingQuery);

    public abstract void initTabLayout();

    public abstract void bindData();

    public abstract boolean isShowLastLine();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchCondition = new Bundle();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden()) {
            if (mSearchCondition != null && !mSearchCondition.isEmpty()) {
              /*  //获取查询条件并发起查询请求，然后清空condition
                mCompanyName = mSearchCondition.getString(SellerConstants.Keywords.KEY_SEARCH_TEXT,
                        "");
                mStartDate =
                        mSearchCondition.getString(SellerConstants.Keywords.KEY_SEARCH_START_DATE
                                , "");
                mEntryDate =
                        mSearchCondition.getString(SellerConstants.Keywords.KEY_SEARCH_ENTRY_DATE
                                , "");
                mSearchCondition = null;*/
            }
            //查询条件非空，自动获取数据(TODO: 把查询条件也加到参数中)
            getDataFromServer(mIsPagingQuery);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getRecyclerView().setForceTop(false);
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
        getRecyclerView().setRefreshLayoutListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉顶部刷新
                if (mCurrentPageIndex != 1) {
                    mQueryCondition.put(Constants.QueryParameter.PAGE_INDEX, mCurrentPageIndex - 1);
                    getRecyclerView().setForceTop(true);
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
                getRecyclerView().setForceTop(true);
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

  /*  @Override
    public void onSearchViewClick() {
     *//*   ARouterUtils.openDetailFragment(getActivity(), SearchKeywordFragment.class.getName(),
                null, R.color.white, false);*//*
    }

    @Override
    public boolean isSearchViewTitle() {
        return true;
    }

    @Override
    public void onFilterViewClick() {
       *//* ARouterUtils.openDetailFragment(getActivity(), SearchFilterFragment.class.getName(), null
                , R.color.white, false);*//*
    }*/
}
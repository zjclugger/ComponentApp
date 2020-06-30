package com.zjclugger.lib.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.ColorRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.R;
import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.view.recyclerview.GridItemDecoration;
import com.zjclugger.lib.view.recyclerview.LinearItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * 列表<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ExtendsRecyclerView extends RelativeLayout {
    private final static String TAG = "ERPRecyclerView";
    protected final static int MSG_LIST_TO_TOP = 60000;
    private View mView;
    private Context mContext;
    private FloatButtonView mTopView;
    private RecyclerView mRecyclerView;
    private RefreshLayout mRefreshLayout;
    private JBaseQuickAdapter mAdapter;
    private RecyclerViewHandler mHandler;
    private boolean mIsFirstTime = false;
    private boolean mForceTop = true;    //切换、上拉、下拉时
    private float mSpan = 10f;
    private float mHSpan = 5f;
    private float mVSpan = 5f;
    private @ColorRes
    int mSpanColorRes;

    public class RecyclerViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LIST_TO_TOP:
                    if (mRecyclerView != null) {
                        mRecyclerView.smoothScrollToPosition(0);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public ExtendsRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mSpanColorRes = R.color.list_line;
    }

    public ExtendsRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mHandler = new RecyclerViewHandler();
        mView = LayoutInflater.from(context).inflate(R.layout.layout_extend_recycler_view, this, true);
        mTopView = mView.findViewById(R.id.extend_to_top_view);
        mRecyclerView = mView.findViewById(R.id.extend_recycler_view);
        mRefreshLayout = mView.findViewById(R.id.extend_refresh_Layout);
    }

    public ExtendsRecyclerView setRefreshLayoutListener(OnRefreshListener refreshListener,
                                                        OnLoadMoreListener loadMoreListener) {
        mRefreshLayout.setOnRefreshListener(refreshListener);
        mRefreshLayout.setOnLoadMoreListener(loadMoreListener);
        return this;
    }

    public ExtendsRecyclerView create(boolean isLinearLayout, int spanCount,
                                      JBaseQuickAdapter adapter,
                                      View.OnClickListener errorViewClickListener) {
        return create(isLinearLayout, spanCount, true, adapter, errorViewClickListener);
    }

    public ExtendsRecyclerView create(boolean isLinearLayout, int spanCount, boolean isShowLastLine,
                                      JBaseQuickAdapter adapter,
                                      View.OnClickListener errorViewClickListener) {
        LinearLayoutManager manager = null;
        RecyclerView.ItemDecoration divider = null;
        if (isLinearLayout) {
            manager = new LinearLayoutManager(mContext);
            manager.setOrientation(RecyclerView.VERTICAL);
            divider = new LinearItemDecoration.Builder(mContext)
                    .setSpan(mSpan)
                    .setColorResource(mSpanColorRes)
                    .setShowLastLine(isShowLastLine)
                    .build();
        } else {
            manager = new GridLayoutManager(mContext, spanCount);
            divider = new GridItemDecoration.Builder(mContext)
                    .setHorizontalSpan(mHSpan)
                    .setVerticalSpan(mVSpan)
                    .setColorResource(mSpanColorRes)
                    .setShowLastLine(isShowLastLine)
                    .build();
        }

        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLayoutManager(manager);

        mTopView.bindToRecyclerView(mRecyclerView);
        mAdapter = adapter;
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnErrorViewClickListener(errorViewClickListener);
        return this;
    }

    public void setSpan(float span) {
        mSpan = span;
    }

    public void setColorResource(@ColorRes int resource) {
        mSpanColorRes = resource;
    }

    /**
     * 刷新列表(获取数据后调用)
     *
     * @param isSuccess   获取数据是否成功
     * @param isNeedToTop 是否自动滚动到顶部
     */
    public void refreshList(boolean isSuccess, boolean isNeedToTop, boolean isHasChange) {
        if (isHasChange) {
            notifyBillListChange();
        }

        if (!isSuccess) {
            mAdapter.setEmptyView(false);
        }

        if (!mIsFirstTime) {
            //不是第一次获取数据
            mRefreshLayout.finishLoadMore(isSuccess);
            mRefreshLayout.finishRefresh(isSuccess);

            if (mHandler != null && isNeedToTop && mForceTop) {
                mHandler.removeMessages(MSG_LIST_TO_TOP);
                mHandler.sendEmptyMessageDelayed(MSG_LIST_TO_TOP, 1000);
            }
        } else {
            mIsFirstTime = true;
        }

        requestFocus();
    }

    public void setForceTop(boolean forceTop) {
        mForceTop = forceTop;
    }

    public void notifyBillListChange() {
        mAdapter.notifyDataChanged();
        mTopView.setVisibility(mAdapter.getData().isEmpty() ? View.GONE : View.VISIBLE);
    }
}
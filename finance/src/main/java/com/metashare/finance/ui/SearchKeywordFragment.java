package com.zjclugger.finance.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.zjclugger.finance.R;
import com.zjclugger.finance.R2;
import com.zjclugger.finance.ui.adapter.SearchKeywordAdapter;
import com.zjclugger.finance.utils.FinanceConstants;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.business.User;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.ui.adapter.ERPSectionEntity;
import com.zjclugger.lib.view.ErpSearchView;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.lib.view.FloatButtonView;
import com.zjclugger.lib.view.recyclerview.ERPGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 关键字查询<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class SearchKeywordFragment extends BaseFragment {
    private final static String TAG = "original";
    private User mCurrentUser;
    FinanceViewModel mViewModel;

    @BindView(R2.id.keyword_search_view)
    ErpSearchView mSearchView;
    @BindView(R2.id.to_top_view)
    FloatButtonView mTopView;
    @BindView(R2.id.keyword_recycler_view)
    RecyclerView mHistoryRecyclerView;
    @BindView(R2.id.elvv_history)
    ExtendLabelValueView mTitleView;
    SearchKeywordAdapter mHistoryAdapter;
    List<ERPSectionEntity> mHistoryList;
    Bundle mCondition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(FinanceViewModel.class);
        mCurrentUser = UserManager.getInstance().getCurrentUser();
        mCondition = new Bundle();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_search_view;
    }

    @Override
    public void initViews() {
        mSearchView.init(getActivity());
        mSearchView.getBackView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        mTitleView.getRightImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHistoryList != null) {
                    mHistoryList.clear();
                }

                notifyListChange();
            }
        });
        initData();
        getDataFromServer();
    }

    @Override
    public void closeFloatView() {

    }

    @Override
    public boolean doBackPress() {
        return false;
    }

    public void initData() {
        mHistoryList = new ArrayList<>();

        //todo for test
        //testData();
        mHistoryRecyclerView.setLayoutManager(new ERPGridLayoutManager(mContext, 2));
        mTopView.bindToRecyclerView(mHistoryRecyclerView);
        mHistoryAdapter = new SearchKeywordAdapter(R.layout.tile_item_text,
                R.layout.tile_item_header, mHistoryList);

        mHistoryAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mHistoryAdapter.bindToRecyclerView(mHistoryRecyclerView);

        mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //TODO:查看票据详情
                if (mHistoryList != null) {
                    mCondition.putString(FinanceConstants.Keywords.KEY_SEARCH_TEXT,
                            mHistoryList.get(position).t.getText());
                    LiveEventBus.get(FinanceConstants.Keywords.KEY_SEARCH_CONDITION)
                            .post(mCondition);
                    setArguments(mCondition);
                    close();
                }
            }
        });
    }

    private void testData() {
        if (mHistoryList != null) {
            mHistoryList.clear();
        }

        // TODO: 2020/11/4 模拟数据
        /*mHistoryList.add(new ERPSectionEntity(true,"第一项",false));
        for (int i = 0; i < 3; i++) {
            mHistoryList.add(new ERPSectionEntity(new ERPListItem("第一项" + i)));
        }

        mHistoryList.add(new ERPSectionEntity(true,"第二项",false));
        for (int i = 0; i < 20; i++) {
            mHistoryList.add(new ERPSectionEntity(new ERPListItem("第二项" + i)));
        }*/
        //fot test todo
        //notifyListChange();
    }

    private void getDataFromServer() {
        Log.d(TAG, "load data-----------");
        //showWaiting();
        if (mHistoryList == null || mHistoryList.size() == 0) {
            mHistoryAdapter.setEmptyView(false);
        }
       /* mViewModel.getCompanyBill(UserManager.getInstance().getCurrentUser().getCompanyId())
       .observe(this, new Observer<ApiResponse<BaseWrapperEntities<OriginalBillResult>>>() {
            @Override
            public void onChanged(ApiResponse<BaseWrapperEntities<OriginalBillResult>> response) {
                closeProgressDialog();
                boolean success = false;
                if (mHistoryList != null) {
                    mHistoryList.clear();
                }
                if (response != null && response.isSuccess()) {
                    BaseWrapperEntities<ERPListItem> wrapperEntity = response.body;
                    if (wrapperEntity != null && wrapperEntity.getResult() != null) {
                        success = true;
                        mHistoryList.addAll(wrapperEntity.getResult());
                    }
                }

                notifyListChange();

                if (!success) {
                    mHistoryAdapter.setEmptyView(true);
                }
            }
        });*/
    }

    private void notifyListChange() {
        mHistoryAdapter.notifyDataChanged();
        mTopView.setVisibility(mHistoryAdapter.getData().isEmpty() ? View.GONE : View.VISIBLE);
    }
}
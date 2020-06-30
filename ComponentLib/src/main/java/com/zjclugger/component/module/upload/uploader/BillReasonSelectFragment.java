package com.zjclugger.component.module.upload.uploader;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.zjclugger.component.R;
import com.zjclugger.component.R2;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.common.JListItem;
import com.zjclugger.lib.ui.adapter.JSectionEntity;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.view.FloatButtonView;
import com.zjclugger.lib.view.recyclerview.JGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 关键字查询<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class BillReasonSelectFragment extends BaseFragment {
    private final static String TAG = "original";

    @BindView(R2.id.to_top_view)
    FloatButtonView mTopView;
    @BindView(R2.id.reason_recycler_view)
    RecyclerView mRecyclerView;
    BillReasonSelectAdapter mReasonAdapter;
    List<JSectionEntity> mReasonList;

    @Override
    public int getLayout() {
        return R.layout.fragment_bill_reason_select;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("报销事由");
        initData();
        getDataFromServer();
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return true;
    }

    public void initData() {
        mReasonList = new ArrayList<>();
        initReasonData();

        mRecyclerView.setLayoutManager(new JGridLayoutManager(mContext, 2));
        mTopView.bindToRecyclerView(mRecyclerView);
        mReasonAdapter = new BillReasonSelectAdapter(R.layout.tile_item_text,
                R.layout.tile_item_header, mReasonList);
        mReasonAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mReasonAdapter.bindToRecyclerView(mRecyclerView);

        mReasonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mReasonList != null) {
                    LiveEventBus.get(Constants.Keywords.KEY_BILL_REASON).post(mReasonList.get(position).t.getText());
                    close();
                }
            }
        });
    }

    private void initReasonData() {
        if (mReasonList != null) {
            mReasonList.clear();
        }
        List<JListItem> reasonHeaderList = new ArrayList<>();
        reasonHeaderList.add(new JListItem(R.array.city_cost, "市内交通费"));
        reasonHeaderList.add(new JListItem(R.array.car_cost, "车辆使用费"));
        reasonHeaderList.add(new JListItem(R.array.network_cost, "通讯网络"));
        reasonHeaderList.add(new JListItem(R.array.business_cost, "业务招待费"));
        reasonHeaderList.add(new JListItem(R.array.meeting_cost, "会务费用"));
        reasonHeaderList.add(new JListItem(R.array.travel_cost, "差旅费"));
        reasonHeaderList.add(new JListItem(R.array.daily_cost, "日常运营费"));
        reasonHeaderList.add(new JListItem(R.array.property_cost, "物业费"));
        reasonHeaderList.add(new JListItem(R.array.others_cost, "其他费用"));

        for (JListItem item : reasonHeaderList) {
            if (item.getText().equalsIgnoreCase("市内交通费")) {
                mReasonList.add(new JSectionEntity(true, item.getText(), false, true));
            } else {
                mReasonList.add(new JSectionEntity(true, item.getText(), false));
            }
            String[] reasonList = getResources().getStringArray(item.getId());
            for (int i = 0; i < reasonList.length; i++) {
                mReasonList.add(new JSectionEntity(new JListItem(reasonList[i])));
            }
        }
    }

    private void getDataFromServer() {
      /*  Log.d(TAG, "load data-----------");
        //showWaiting();
        if (mReasonList == null || mReasonList.size() == 0) {
            mReasonAdapter.setEmptyView(false);
        }*/
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
        mReasonAdapter.notifyDataChanged();
        mTopView.setVisibility(mReasonAdapter.getData().isEmpty() ? View.GONE : View.VISIBLE);
    }
}
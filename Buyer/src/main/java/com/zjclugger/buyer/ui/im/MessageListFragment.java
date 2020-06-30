package com.zjclugger.buyer.ui.im;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.IMResult;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.router.utils.ARouterUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 消息列表<br>
 * Created by King.Zi on 2020/5/11.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class MessageListFragment extends BaseFragment {
    private final static String TAG = "MessageList";

    @BindView(R.id.slv_message)
    SlideListView mMessageListView;
    @BindView(R.id.et_search)
    SearchEditText mSearchView;
    @BindView(R.id.empty_view)
    TextView mEmptyView;

    private List<IMResult> mMessageList = new ArrayList<>();
    private MessageAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.fragment_message_list;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("商铺名", null, null, false);
        mAdapter = new MessageAdapter(mContext);
        mMessageListView.setAdapter(mAdapter);
        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WidgetUtils.toast(mContext, "点击-查询详情", false);
                ARouterUtils.openDetailFragment(getActivity(),
                        ChatFragment.class.getName(), null, R.color.white, false);

            }
        });

        mMessageListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                if (position > -1 && mMessageList != null) {
                    WidgetUtils.toast(mContext, "长按事件", false);
                    // 将文本内容放到系统剪贴板里。
           /* mClip = ClipData.newPlainText("text", mRegCodeList.get(position).getMachineCode() +
            "#" + mRegCodeList.get
                    (position).getRegCode());
            mClipboard.setPrimaryClip(mClip);
            Utils.toast(RegCodeListActivity.this, R.string.copy_result);*/
                }
                return true;
            }
        });
        mAdapter.setDataSource(mMessageList);

        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        bindData();
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
        getDataFromServer(false);
    }

    private void getDataFromServer(boolean isPagingQuery) {
        initListData();

    }

    private void initListData() {
        if (null != mMessageList) {
            mMessageList.clear();
        }

        IMResult result;
        for (int i = 0; i < 30; i++) {
            result = new IMResult();
            result.setId(String.valueOf(4232 + i));
            result.setName("张三" + i);
            result.setNewCount(i + 1);
            result.setMessage("这是新消息？" + result.getId());
            result.setDateTime("15:55");
            mMessageList.add(result);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}
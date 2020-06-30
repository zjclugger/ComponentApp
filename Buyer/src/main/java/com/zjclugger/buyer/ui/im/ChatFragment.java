package com.zjclugger.buyer.ui.im;

import android.widget.EditText;
import android.widget.TextView;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.IMResult;
import com.zjclugger.lib.base.BaseListFragment;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.ExtendsRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 聊天窗口<br>
 * Created by King.Zi on 2020/5/11.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ChatFragment extends BaseListFragment {
    private final static String TAG = "Chat";

    @BindView(R.id.recycler_view)
    ExtendsRecyclerView mRecyclerView;
    @BindView(R.id.send_message)
    TextView mSendMessageView;
    @BindView(R.id.message_edit_view)
    EditText mEditView;

    List<IMResult> mChatMessageList;

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
        return R.layout.fragment_chat;
    }

    @Override
    public void initTabLayout() {
    }

    @Override
    public ExtendsRecyclerView getRecyclerView() {
        mRecyclerView.setSpan(0f);
        mRecyclerView.setColorResource(R.color.bg_white);
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
        return false;
    }

    @Override
    public void initViews() {
        mChatMessageList = new ArrayList<>();
        mQueryCondition = new HashMap<>();
        initDetailTitleViews("商铺名");

        resetQueryCondition();

        mSendMessageView.setOnClickListener(v -> {
            WidgetUtils.toast(mContext, mEditView.getText().toString(), false);
        });
        super.initViews();
    }

    @Override
    public void bindData() {
        mAdapter = new ChatAdapter(R.layout.item_chat, mChatMessageList);
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
        if (null != mChatMessageList) {
            mChatMessageList.clear();
        }

        IMResult result;
        for (int i = 0; i < 30; i++) {
            result = new IMResult();
            result.setId(String.valueOf(4232 + i));
            result.setName("张三" + i);
            result.setNewCount(i + 1);
            result.setMessage(i % 2 != 0 ? "这是客服的新消息这是客服的新消息这是客服的新消息这是客服的新消息？" + result.getId() :
                    "用户的新消息" + result.getId());
            result.setDateTime("15:55");
            result.setUserMessage(i % 2 == 0);
            mChatMessageList.add(result);
        }

        mAdapter.notifyDataChanged();
    }
}
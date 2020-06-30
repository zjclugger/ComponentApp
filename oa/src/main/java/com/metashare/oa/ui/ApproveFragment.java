package com.zjclugger.oa.ui;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zjclugger.basiclib.Log;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.view.recyclerview.ERPGridLayoutManager;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;
import com.zjclugger.oa.ui.adapter.ApproveAdapter;
import com.zjclugger.oa.ui.adapter.Staff;
import com.zjclugger.oa.utils.OaConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 审批人列表<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ApproveFragment extends OABaseFragment {
    private final static String TAG = "leaveMain";
    private String mPageTitle;
    private List<Staff> mContactList = new ArrayList<>();

    @BindView(R2.id.approve_list_view)
    RecyclerView mApproveRecyclerView;
    @BindView(R2.id.total_view)
    TextView mTotalView;
    private int mMaxCount = 15;
    private boolean mReadOnly;
    ApproveAdapter mApproveAdapter;

    @Override
    public int getLayout() {
        return R.layout.fragment_approve;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("抄送人");
      /*  Bundle params = getArguments();
        int recordId = params.getInt(Constants.Keywords.KEY_PERSONAL_PAYROLL_ID, 0);
        if (recordId > 0) {
            showWaiting();
            mViewModel.getPersonalPayrollRecordDetail(String.valueOf(recordId)).observe(this,
                    new Observer<ApiResponse<BaseWrapperEntity<PayrollDetailResult>>>() {
                        @Override
                        public void onChanged(ApiResponse<BaseWrapperEntity<PayrollDetailResult>>
                         baseWrapperEntityApiResponse) {
                            closeProgressDialog();
                            if (baseWrapperEntityApiResponse != null &&
                            baseWrapperEntityApiResponse.isSuccess()) {
                                BaseWrapperEntity<PayrollDetailResult> wrapperEntity =
                                        baseWrapperEntityApiResponse.body;
                                if (wrapperEntity != null) {
                                    bindData(wrapperEntity.getResult());
                                }
                            }
                        }
                    });
        } else {
            closeProgressDialog();
            Log.e(TAG, "payroll id is " + recordId);
            WidgetUtils.toastErrorMessage(getActivity(), "未查询到结果");
        }*/

        mMaxCount = getArguments().getInt(OaConstants.Keywords.KEY_LIST_MAX_SIZE, mMaxCount);
        mReadOnly = getArguments().getBoolean(OaConstants.Keywords.KEY_READ_ONLY, false);
        if (mContactList != null) {
            mContactList.clear();
        }
        if (getArguments().containsKey(OaConstants.Keywords.KEY_APPROVE_LIST)) {
            mContactList =
                    getArguments().getParcelableArrayList(OaConstants.Keywords.KEY_APPROVE_LIST);
        } else {
            Log.d("no keyword " + OaConstants.Keywords.KEY_APPROVE_LIST);
        }
        if (mContactList == null) {
            initData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        bindData();
    }

    private void initData() {
        if (mContactList != null) {
            mContactList.clear();
        }

        Staff staff;
        for (int i = 0; i < 12; i++) {
            staff = new Staff();
            staff.setId(1000 + i);
            staff.setName("张" + i + "丰");
            mContactList.add(staff);
        }
    }

    private void bindData() {
        mTotalView.setText("抄送" + String.valueOf(getCount()) + "人");
        //是否显示ADD图标
        if (mContactList != null && mContactList.size() < mMaxCount && mContactList.get(mContactList.size() - 1).getId() != Constants.KeyCode.FLAG_ADD_NEW) {
            Staff flag = new Staff();
            flag.setId(Constants.KeyCode.FLAG_ADD_NEW);
            mContactList.add(flag);
        }
        mApproveRecyclerView.setLayoutManager(new ERPGridLayoutManager(getActivity(), 5));
        mApproveAdapter = new ApproveAdapter(R.layout.item_approve, mContactList, mMaxCount,
                mReadOnly);

        mApproveRecyclerView.setAdapter(mApproveAdapter);
    }

    private int getCount() {
        return mContactList == null ? 0 : mContactList.size() > mMaxCount ? mMaxCount :
                mContactList.get(mContactList.size() - 1).getId() == Constants.KeyCode.FLAG_ADD_NEW ? mContactList.size() - 1 : mContactList.size();
    }

    @Override
    public Boolean getPostBackData() {
        return true;
    }
}
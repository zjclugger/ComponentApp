package com.zjclugger.oa.ui;

import android.os.Bundle;
import android.view.View;

import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.view.CircleImageTextView;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;
import com.zjclugger.oa.ui.adapter.Staff;
import com.zjclugger.oa.utils.OaConstants;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * 请假历史详情<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LeaveHistoryDetailFragment extends OABaseFragment {
    private final static String TAG = "leaveType";
    @BindView(R2.id.approve_more)
    CircleImageTextView mShowApproveMoreView;
    @BindView(R2.id.approve_1)
    CircleImageTextView mApproveView1;
    @BindView(R2.id.approve_2)
    CircleImageTextView mApproveView2;
    @BindView(R2.id.approve_3)
    CircleImageTextView mApproveView3;
    ArrayList<Staff> mContactList = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.fragment_leave_history_detail;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("请假详情");
        initApprove();
    }

    private void initApprove() {
        if (mContactList != null) {
            mContactList.clear();
        }
        Staff staff;
        for (int i = 0; i < 12; i++) {
            staff = new Staff();
            staff.setId(1000 + i);
            staff.setName("李" + i + "四");
            mContactList.add(staff);
        }
        if (mContactList != null) {
            if (mContactList.size() == 1) {
                ViewUtils.setVisibility(false, mApproveView2, mApproveView3, mShowApproveMoreView);
                showApproveView(mApproveView1, mContactList.get(0));
            } else if (mContactList.size() == 2) {
                ViewUtils.setVisibility(false, mApproveView3, mShowApproveMoreView);
                showApproveView(mApproveView1, mContactList.get(0));
                showApproveView(mApproveView2, mContactList.get(1));
            } else {
                showApproveView(mApproveView1, mContactList.get(0));
                showApproveView(mApproveView2, mContactList.get(1));
                showApproveView(mApproveView3, mContactList.get(2));
                if (mContactList.size() > 3) {
                    mShowApproveMoreView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO:判断审批人来源，是从当前result中来，还是再查询一次接口
                            ApproveFragment approveFragment = new ApproveFragment();
                            Bundle data = new Bundle();
                            data.putParcelableArrayList(OaConstants.Keywords.KEY_APPROVE_LIST,
                                    mContactList);
                            data.putInt(OaConstants.Keywords.KEY_LIST_MAX_SIZE,
                                    mContactList.size());
                            data.putBoolean(OaConstants.Keywords.KEY_READ_ONLY, true);
                            approveFragment.setArguments(data);
                            FragmentUtils.addFragment(getActivity(), approveFragment,
                                    R.id.content_fragment);
                        }
                    });
                } else {
                    ViewUtils.setVisibility(false, mShowApproveMoreView);
                }
            }
        } else {
            ViewUtils.setVisibility(false, mApproveView1, mApproveView2, mApproveView3,
                    mShowApproveMoreView);
        }
    }

    private void showApproveView(CircleImageTextView views, Staff staff) {
        //TODO：如果有头像则显示头像，没有显示姓名
        String name = staff.getName();
        try {
            views.setCircleImageCenterText(ERPUtils.subString(name, 4, "GBK", true));
        } catch (UnsupportedEncodingException e) {
            views.setCircleImageCenterText(name);
        }
        views.getTextView().setText(name);
    }

    @Override
    public <T> T getPostBackData() {
        return null;
    }
}
package com.zjclugger.oa.ui;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.base.BaseActivity;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;
import com.zjclugger.oa.ui.adapter.LeaveTypeAdapter;
import com.zjclugger.oa.utils.OaConstants;
import com.zjclugger.oa.webapi.entity.response.LeaveTypeResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LeaveTypeActivity extends BaseActivity {
    private final static String TAG = "LeaveType";
    private OAViewModel mViewModel;
    private List<LeaveTypeResult> mLeaveTypeList = new ArrayList<>();
    @BindView(R2.id.leave_type_view)
    RecyclerView mTypeRecyclerView;
    LeaveTypeAdapter mTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_manager);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(OAViewModel.class);
        initViews();
    }

    @Override
    protected boolean isDetailActivity() {
        return true;
    }

    @Override
    protected String getDetailActivityTitle() {
        // return getString(R.string.title_payroll_detail);
        return "请假";
    }

    public void initViews() {


        initData();
        bindData();
    }

    private void initData() {
        LeaveTypeResult result = new LeaveTypeResult();
        result.setLeaveType(1);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_green));
        result.setLeaveName("年假");
        result.setGrantType(OaConstants.LeaveGrantType.MANUAL);//1：手动，0：自动
        result.setLeaveRemain(0.5d);
        result.setLeaveUnit(OaConstants.LeaveUnit.HOUR); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(2);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_green));
        result.setLeaveName("事假");
        result.setGrantType(OaConstants.LeaveGrantType.HOUR);//1：手动，0：自动，-1：无
        result.setLeaveRemain(-1); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.HOUR); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(3);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_green));
        result.setLeaveName("病假");
        result.setGrantType(OaConstants.LeaveGrantType.HOUR);//1：手动，0：自动，-1：无
        result.setLeaveRemain(-1); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.HOUR); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(4);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_green));
        result.setLeaveName("调休");
        result.setGrantType(OaConstants.LeaveGrantType.MANUAL);//1：手动，0：自动，-1：无
        result.setLeaveRemain(0); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.HOUR); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(5);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_green));
        result.setLeaveName("产假");
        result.setGrantType(OaConstants.LeaveGrantType.MANUAL);//1：手动，0：自动，-1：无
        result.setLeaveRemain(0); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.DAY); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(6);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_green));
        result.setLeaveName("陪产假");
        result.setGrantType(OaConstants.LeaveGrantType.MANUAL);//1：手动，0：自动，-1：无
        result.setLeaveRemain(0); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.DAY); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(7);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_green));
        result.setLeaveName("婚假");
        result.setGrantType(OaConstants.LeaveGrantType.DAY);//1：手动，0：自动，-1：无
        result.setLeaveRemain(-1); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.DAY); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(8);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_green));
        result.setLeaveName("丧假");
        result.setGrantType(OaConstants.LeaveGrantType.DAY);//1：手动，0：自动，-1：无
        result.setLeaveRemain(-1); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.DAY); //0:小时，1：天
        mLeaveTypeList.add(result);

        result = new LeaveTypeResult();
        result.setLeaveType(9);
        result.setLeftImage(getResources().getDrawable(R.mipmap.ic_green));
        result.setLeaveName("产检假");
        result.setGrantType(OaConstants.LeaveGrantType.MANUAL);//1：手动，0：自动，-1：无
        result.setLeaveRemain(0); //-1 无
        result.setLeaveUnit(OaConstants.LeaveUnit.HOUR); //0:小时，1：天
        mLeaveTypeList.add(result);
    }

    private void bindData() {
        mTypeRecyclerView.setLayoutManager(new LinearLayoutManager(LeaveTypeActivity.this));
        mTypeAdapter = new LeaveTypeAdapter(R.layout.item_leave_type, mLeaveTypeList);
        mTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //TODO:打开请假填写界面
                   /* ERPUtils.addFragment(getActivity(), mTypeAdapter.get(position).t
                   .getFragment(),
                            R.id.content_fragment);*/
            }
        });
        mTypeRecyclerView.setAdapter(mTypeAdapter);
    }
}
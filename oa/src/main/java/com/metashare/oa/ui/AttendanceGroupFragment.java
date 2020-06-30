package com.zjclugger.oa.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.ui.widget.ErpAlertDialog;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;
import com.zjclugger.oa.ui.adapter.AttendanceGroupListAdapter;
import com.zjclugger.oa.webapi.entity.response.AttendanceGroupResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 考勤组<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceGroupFragment extends OABaseFragment {
    private final static String TAG = "attendanceGroup";
    private String mPageTitle;
    @BindView(R2.id.tv_add_group)
    TextView mAddGroupView;
    @BindView(R2.id.group_list_view)
    RecyclerView mGroupRecyclerView;
    private AttendanceGroupListAdapter mGroupAdapter;
    private List<AttendanceGroupResult> mGroupList;
    private ErpAlertDialog mGroupDialog;
    private ErpAlertDialog mAddDialog;
    private View mDialogView;

    @Override
    public int getLayout() {
        return R.layout.fragment_attendance_group;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("考勤组管理");
        //班次
        mAddGroupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddDialog == null || mDialogView == null) {
                    initAddView();
                } else {
                    mAddDialog.show();
                }
            }
        });

        //list view
        mGroupList = new ArrayList<>();
        AttendanceGroupResult groupResult = new AttendanceGroupResult();
        groupResult.setId(500);
        groupResult.setName("考勤组一");
        groupResult.setMemberCount(30);
        groupResult.setShiftsName("08:00-19:00");
        groupResult.setShiftsPlace("中南国际");
        mGroupList.add(groupResult);

        groupResult = new AttendanceGroupResult();
        groupResult.setId(501);
        groupResult.setName("考勤组二");
        groupResult.setMemberCount(15);
        groupResult.setShiftsName("08:00-19:00");
        groupResult.setShiftsPlace("深圳罗湖");
        mGroupList.add(groupResult);

        groupResult = new AttendanceGroupResult();
        groupResult.setId(502);
        groupResult.setName("考勤组三");
        groupResult.setMemberCount(100);
        groupResult.setShiftsName("08:00-19:00");
        groupResult.setShiftsPlace("国际空间站");
        mGroupList.add(groupResult);

        mGroupRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mGroupAdapter = new AttendanceGroupListAdapter(R.layout.item_attendance_group, mGroupList);
        mGroupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.elvv_attendance_group_name) {
                    WidgetUtils.toastMessage(getActivity(),
                            "即将删除" + mGroupList.get(position).getName());
                } else if (view.getId() == R.id.btn_rule_setting) {
                    AttendanceRuleFragment ruleFragment = new AttendanceRuleFragment();
                    FragmentUtils.replaceFragment(getActivity(), ruleFragment, R.id.content_fragment);
                } else if (view.getId() == R.id.btn_member_setting) {

                }
            }
        });
        mGroupRecyclerView.setAdapter(mGroupAdapter);
    }

    private void initAddView() {
        mAddDialog = new ErpAlertDialog(getActivity());
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDialogView = inflater.inflate(R.layout.dialog_add_group_layout, null);
        TextView titleText = mDialogView.findViewById(R.id.dialog_title);
        titleText.setText("新增考勤组");
     /*   final EditText placeView = mDialogView.findViewById(R.id.et_place);
        final EditText addressView = mDialogView.findViewById(R.id.et_place_address);*/

        mDialogView.findViewById(R.id.dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAddDialog.dismiss();
            }
        });
        mDialogView.findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddDialog.dismiss();
            }
        });

        mGroupDialog = new ErpAlertDialog(getActivity(), mDialogView);
    }

    @Override
    public Boolean getPostBackData() {
        return false;
    }
}
package com.zjclugger.oa.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.jview.DateTimePickerView;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.utils.DateTimeFormat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.basiclib.Log;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.common.ERPChoiceItem;
import com.zjclugger.lib.ui.adapter.ERPChoiceAdapter;
import com.zjclugger.lib.ui.widget.ErpAlertDialog;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.CircleImageTextView;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.lib.view.LabelValueView;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;
import com.zjclugger.oa.ui.adapter.Staff;
import com.zjclugger.oa.utils.OaConstants;
import com.zjclugger.oa.webapi.entity.response.LeaveTypeResult;
import com.zjclugger.router.ARouterConstants;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 请假销假管理<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public abstract class BaseLeaveFragment extends OABaseFragment {
    private final static String TAG = "leaveType";
    protected OAViewModel mViewModel;
    protected BaseFragment mSelectedMediaFragment;

    @BindView(R2.id.leave_balance)
    LabelValueView mLeaveBalanceView;
    @BindView(R2.id.leave_request_type)
    ExtendLabelValueView mLeaveRequestTypeView;
    @BindView(R2.id.leave_start_datetime)
    ExtendLabelValueView mStartDateTimeView;
    @BindView(R2.id.leave_end_datetime)
    ExtendLabelValueView mEndDateTimeView;
    @BindView(R2.id.leave_time)
    ExtendLabelValueView mLeaveTime;
    @BindView(R2.id.leave_reason)
    EditText mLeaveReasonView;

    @BindView(R2.id.approve_more)
    CircleImageTextView mApproveMoreView;   //显示更多审核人
    @BindView(R2.id.approve_2)
    CircleImageTextView mApproveSecondView; //显示最后一个审核人信息
    @BindView(R2.id.approve_add_more)
    CircleImageTextView mApproveAdd;    //增加审核人
    ArrayList<Staff> mContactList = new ArrayList<>();
    DateTimePickerView mStartDatePickerDialog;
    DateTimePickerView mEndDatePickerDialog;
    private String mDateTimeFormat = "yyyy-MM-dd HH:mm";
    private String mDateTimeDefaultValue = "请选择";
    private ErpAlertDialog mTypeListDialog;
    private ERPChoiceAdapter mTypeListAdapter;
    private List<LeaveTypeResult> mTypeList;
    private LeaveTypeResult mCurrentLeaveType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTypeListDialog = new ErpAlertDialog(getActivity());
    }

    @Override
    public void initViews() {
        mCurrentLeaveType = mArguments.getParcelable(OaConstants.Keywords.KEY_LEAVE_TYPE);
        bindView();
        //TODO：mTypeList 应从接口中获取
        if (mArguments.containsKey(OaConstants.Keywords.KEY_LEAVE_TYPE_LIST)) {
            mTypeList = mArguments.getParcelableArrayList(OaConstants.Keywords.KEY_LEAVE_TYPE_LIST);
        }
        List<ERPChoiceItem> typeTextList = new ArrayList<>();
        for (LeaveTypeResult item : mTypeList) {
            typeTextList.add(new ERPChoiceItem(item.getLeaveName() + "(剩余" + (item.getLeaveRemain() == 0 ?
                    "0" : item.getLeaveRemain()) + (item.getLeaveUnit() == 0 ? "小时)" : "天)")));
        }
        mTypeListAdapter = new ERPChoiceAdapter(typeTextList);
        mTypeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WidgetUtils.toastMessage(getActivity(),
                        "您选择了" + mTypeList.get(position).getLeaveName());
                mCurrentLeaveType = mTypeList.get(position);
                bindView();
                //TODO:重新绑定数据
                mTypeListDialog.dismiss();
            }
        });

        mLeaveRequestTypeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTypeListDialog.setData(mTypeListAdapter).show();
            }
        });

        //start
        mStartDateTimeView.getValueView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartDatePickerDialog == null) {
                    mStartDatePickerDialog = new DateTimePickerView(getActivity(),
                            initOnTimeSelectListener(mStartDateTimeView), DateTimeFormat.YMDHM);
                }
                mStartDatePickerDialog.show();
            }
        });
        mStartDateTimeView.getRightImageView().setOnClickListener(initRightViewListener(mStartDateTimeView));

        //end
        mEndDateTimeView.getValueView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEndDatePickerDialog == null) {
                    mEndDatePickerDialog = new DateTimePickerView(getActivity(),
                            initOnTimeSelectListener(mEndDateTimeView), DateTimeFormat.YMDHM);
                }
                mEndDatePickerDialog.show();
            }
        });
        mEndDateTimeView.getRightImageView().setOnClickListener(initRightViewListener(mEndDateTimeView));

        //图片
        mSelectedMediaFragment =
                (BaseFragment) ARouter.getInstance().build(ARouterConstants.Path.COM_SELECT_MEDIA)
                        .withBoolean(ARouterConstants.KeyWord.KEY_PICTURE_SMALL, true)
                        .withBoolean(ARouterConstants.KeyWord.KEY_SINGLE_LAYOUT, false)
                        .withInt(ARouterConstants.KeyWord.KEY_GRID_SPAN_COUNT, 5)
                        .navigation();

        //在部分低端手机，调用单独拍照时可能产生内存不足的问题，导致activity被回收，所以不重复创建fragment
        FragmentUtils.addFirstFragment(getActivity(), mSelectedMediaFragment, R.id.picture_content);
       /* if (savedInstanceState == null) {
            // 添加显示第一个fragment
            fragment = new PhotoFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.tab_content, fragment,
                    PictureConfig.FC_TAG).setOnClickListener(fragment)
                    .commit();
        } else {
            fragment = (PhotoFragment) getSupportFragmentManager()
                    .findFragmentByTag(PictureConfig.FC_TAG);
        }*/

        //审核-抄送
        initApprove();
    }

    private void bindView() {
        mLeaveRequestTypeView.setValueText(mCurrentLeaveType.getLeaveName());
        mLeaveBalanceView.setValueText(mCurrentLeaveType.getLeaveBalance());
        mLeaveTime.setLabelText("时长(" + mCurrentLeaveType.getLeaveUnitText() + ")");
    }

    private OnTimeSelectListener initOnTimeSelectListener(final ExtendLabelValueView dateTimeView) {
        return new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //Toast.makeText(MainActivity.this, "您选择的时间是："+getTime(date),
                // Toast
                // .LENGTH_SHORT).setOnClickListener();
                if (dateTimeView.getValueView().getText().toString().equalsIgnoreCase("请选择")) {
                    dateTimeView.getRightImageView().setImageResource(R.mipmap.ic_delete);
                }
                dateTimeView.setValueText(ERPUtils.getDateTime(date, mDateTimeFormat));
            }
        };
    }

    private View.OnClickListener initRightViewListener(final ExtendLabelValueView dateTimeView) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeView.setValueText(mDateTimeDefaultValue);
                dateTimeView.getRightImageView().setImageResource(R.mipmap.ic_nav_right_gary_small);
            }
        };
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
                mApproveSecondView.setVisibility(View.GONE);
                mApproveMoreView.getTextView().setText(mContactList.get(0).getName());
            } else if (mContactList.size() > 2) {
                //mApproveMoreView.setImage(getResources().getDrawable(R.mipmap.image_head));
                //TODO：如果有头像则显示头像，没有显示姓名
                String name = mContactList.get(mContactList.size() - 1).getName();
                try {
                    mApproveSecondView.setCircleImageCenterText(ERPUtils.subString(name, 4, "GBK"
                            , true));
                } catch (UnsupportedEncodingException e) {
                    mApproveSecondView.setCircleImageCenterText(name);
                }
                mApproveSecondView.getTextView().setText(name);
            }
        } else {
            mApproveMoreView.setVisibility(View.GONE);
            mApproveSecondView.setVisibility(View.GONE);
        }
    }

    @OnClick(R2.id.approve_add_more)
    public void addApprove() {
        Intent intent = new Intent(getActivity(), CompanyContactsActivity.class);
        intent.putExtra(Constants.Keywords.KEY_CONTACTS_MODE, true);
        startActivity(intent);
    }

    @OnClick(R2.id.approve_2)
    //CircleImageTextView mApproveSecondView; //显示最后一个审核人信息
    public void showApproveDetailView() {
        //TODO：显示审核人详情
    }

    @OnClick(R2.id.approve_more)
    public void showApproveListView() {
        ApproveFragment approveFragment = new ApproveFragment();
        Bundle data = new Bundle();
        data.putParcelableArrayList(OaConstants.Keywords.KEY_APPROVE_LIST, mContactList);
        approveFragment.setArguments(data);
        FragmentUtils.addFragment(getActivity(), approveFragment, R.id.content_fragment);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("data=" + data);
    }

    @OnClick(R2.id.request_button)
    public void submit() {
        Intent intent = new Intent(getActivity(), CompanyContactsActivity.class);
        intent.putExtra(Constants.Keywords.KEY_CONTACTS_MODE, false);
        startActivity(intent);
       /* WidgetUtils.toastErrorMessage(getActivity(),"errrrrrrrrrr");
        if (mSelectedMediaFragment != null) {
            Bundle data = mSelectedMediaFragment.getArguments();
            ArrayList<ERPMediaItem> mediaList =
                    data.getParcelableArrayList(Constants.Keywords.KEY_SELECTED_PICTURE);
            Log.d("the result media list is " + (mediaList == null ? "0" : mediaList.size()));
        } else {
            Log.d("mSelectedMediaFragment is null");
        }*/
    }
}
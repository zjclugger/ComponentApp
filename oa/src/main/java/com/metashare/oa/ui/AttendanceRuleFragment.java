package com.zjclugger.oa.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.jview.DateTimePickerView;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.utils.DateTimeFormat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.entity.common.ERPChoiceItem;
import com.zjclugger.lib.ui.adapter.ERPChoiceAdapter;
import com.zjclugger.lib.ui.widget.ErpAlertDialog;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;
import com.zjclugger.oa.ui.adapter.AttendancePlaceListAdapter;
import com.zjclugger.oa.webapi.entity.response.AttendanceDateResult;
import com.zjclugger.oa.webapi.entity.response.AttendancePlaceResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import me.jessyan.autosize.AutoSizeCompat;

/**
 * 考勤规则<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceRuleFragment extends OABaseFragment {
    private final static String TAG = "attendanceRule";
    @BindView(R2.id.tv_add_office_place)
    TextView mAddView;
    @BindView((R2.id.switch_out_attendance_allow))
    Switch mAllowView;
    @BindView(R2.id.elvv_attendance_time)
    ExtendLabelValueView mTimeView;
    @BindView(R2.id.elvv_attendance_date)
    ExtendLabelValueView mDateView;
    @BindView(R2.id.elvv_effective_range)
    ExtendLabelValueView mRangeView;
    @BindView(R2.id.place_list_view)
    RecyclerView mRuleRecyclerView;
    private AttendancePlaceListAdapter mPlaceAdapter;
    private List<AttendancePlaceResult> mPlaceList;
    //date list
    private ErpAlertDialog mDateDialog;
    private ERPChoiceAdapter mDateAdapter;
    private List<AttendanceDateResult> mDateList;
    private List<ERPChoiceItem> mDateSelectedList;
    //range
    private ErpAlertDialog mRangeDialog;
    private ERPChoiceAdapter mRangeAdapter;
    private List<ERPChoiceItem> mRangeList;
    private DateTimePickerView mStartDatePickerDialog;
    //add
    private ErpAlertDialog mAddDialog;
    private View mDialogView;

    @Override
    public int getLayout() {
        return R.layout.fragment_attendance_rule;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("规则设置");
        mAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddDialog == null || mDialogView == null) {
                    initAddView();
                } else {
                    mAddDialog.show();
                }
            }
        });

        mAllowView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                WidgetUtils.toastMessage(getActivity(), isChecked ? "允许" : "不允许");
            }
        });

        initPlaceListView();
        initDateChooseView();
        initRangListView();
        initTimeView();
    }

    private void initPlaceListView() {
        //list view
        mPlaceList = new ArrayList<>();
        AttendancePlaceResult result = new AttendancePlaceResult();
        result.setId(500);
        result.setName("中南金石国际");
        result.setPlace("井冈山路157号");
        mPlaceList.add(result);

        result = new AttendancePlaceResult();
        result.setId(500);
        result.setName("石油大学");
        result.setPlace("长江西路66号");
        mPlaceList.add(result);

        result = new AttendancePlaceResult();
        result.setId(500);
        result.setName("东营");
        result.setPlace("未知名路XX号");
        mPlaceList.add(result);

        mRuleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPlaceAdapter = new AttendancePlaceListAdapter(R.layout.item_attendance_place, mPlaceList);
        mPlaceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.right_view) {
                    WidgetUtils.toastMessage(getActivity(),
                            "即将删除" + mPlaceList.get(position).getName());
                }
            }
        });
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL);
        mRuleRecyclerView.addItemDecoration(divider);
        mRuleRecyclerView.setAdapter(mPlaceAdapter);
    }

    private void initDateChooseView() {
        //日期
        mDateDialog = new ErpAlertDialog(getActivity());
        mDateList = new ArrayList<>();
        mDateSelectedList = new ArrayList<>();
        AttendanceDateResult result = new AttendanceDateResult();
        result.setId(501);
        result.setName("星期一");
        mDateList.add(result);
        result = new AttendanceDateResult();
        result.setId(502);
        result.setName("星期二");
        mDateList.add(result);
        result = new AttendanceDateResult();
        result.setId(503);
        result.setName("星期三");
        mDateList.add(result);
        result = new AttendanceDateResult();
        result.setId(504);
        result.setName("星期四");
        mDateList.add(result);
        result = new AttendanceDateResult();
        result.setId(505);
        result.setName("星期五");
        mDateList.add(result);
        result = new AttendanceDateResult();
        result.setId(506);
        result.setName("星期六");
        mDateList.add(result);
        result = new AttendanceDateResult();
        result.setId(507);
        result.setName("星期七");
        mDateList.add(result);

        for (AttendanceDateResult item : mDateList) {
            mDateSelectedList.add(new ERPChoiceItem(item.getName()));
        }

        mDateAdapter = new ERPChoiceAdapter(mDateSelectedList, true);
        mDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:打开选择框，并接收返回值
                mDateDialog.setData("选择日期", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDateDialog.dismiss();
                        for (int i = 0; i < mDateSelectedList.size(); i++) {
                            mDateList.get(i).setSelected(mDateSelectedList.get(i).isSelected());
                        }
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDateDialog.dismiss();
                        for (int i = 0; i < mDateSelectedList.size(); i++) {
                            mDateSelectedList.get(i).setSelected(false);
                        }
                    }
                }, mDateAdapter).show();
            }
        });
    }

    private void initRangListView() {
        mRangeDialog = new ErpAlertDialog(getActivity());
        mRangeList = new ArrayList<>();
        mRangeList.add(new ERPChoiceItem("100"));
        mRangeList.add(new ERPChoiceItem("200", true));
        mRangeList.add(new ERPChoiceItem("300"));

        mRangeAdapter = new ERPChoiceAdapter(mRangeList);
        mRangeAdapter.clickedCloseView(false);
        mRangeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WidgetUtils.toastMessage(getActivity(),
                        "您点击的是" + mRangeList.get(position).getText() + ",是否选中=" + mRangeList.get(position).isSelected());
            }
        });

        mRangeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoSizeCompat.autoConvertDensity(getActivity().getResources(), 720, true);
                //如果有自定义需求就用这个方法
                mDateDialog.setData("请选择有效范围", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDateDialog.dismiss();

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDateDialog.dismiss();

                    }
                }, mRangeAdapter).show();
            }
        });
    }

    private DateTimePickerView mDatePickerDialog;

    private void initTimeView() {
        //start
        mTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatePickerDialog == null) {
                    if (mDatePickerDialog == null) {
                        mDatePickerDialog = new DateTimePickerView(getActivity(),
                                initOnTimeSelectListener(mTimeView), DateTimeFormat.HM);
                    }
                    mDatePickerDialog.show();
                }

               /* if (mStartDatePickerDialog == null) {
                    mStartDatePickerDialog = new TimePickerDialog(getActivity(),
                            initOnTimeSelectListener(mTimeView), "HH:mm:ss");
                }
                mStartDatePickerDialog.show();*/
               /* if (mStartDatePickerDialog == null) {
                    mStartDatePickerDialog = new TimePickerDialog(getActivity(),
                            new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        }
                    }, 12, 30, false);
                }
                mStartDatePickerDialog.setOnClickListener();*/

              /*  //start
                mStartDateTimeView.getValueView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mStartDatePickerDialog == null) {
                            mStartDatePickerDialog = new DatePickerDialog(getActivity(),
                                    initOnTimeSelectListener(mStartDateTimeView), mDateTimeFormat);
                        }
                        mStartDatePickerDialog.setType(mFormat);
                        mStartDatePickerDialog.show();
                    }
                });*/
            }
        });
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
                dateTimeView.setValueText(ERPUtils.getDateTime(date, "HH:mm:ss"));
            }
        };
    }

    private void initAddView() {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDialogView = inflater.inflate(R.layout.dialog_add_place_layout, null);
        TextView titleText = mDialogView.findViewById(R.id.dialog_title);
        titleText.setText("请输入办公地址");
        final EditText placeView = mDialogView.findViewById(R.id.et_place);
        final EditText addressView = mDialogView.findViewById(R.id.et_place_address);

        mDialogView.findViewById(R.id.dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetUtils.toastMessage(getActivity(),
                        "你输出的是" + placeView.getText() + "-" + addressView.getText());
                mAddDialog.dismiss();
            }
        });
        mDialogView.findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddDialog.dismiss();
            }
        });

       /* @Override
        public Resources getResources() {
            //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
            AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources());//如果没有自定义需求用这个方法
            AutoSizeCompat.autoConvertDensity((super.getResources(), 667, false);//如果有自定义需求就用这个方法
            return super.getResources();
        }*/
        mAddDialog = new ErpAlertDialog(getActivity(), mDialogView);
    }

    @Override
    public Boolean getPostBackData() {
        return false;
    }
}
package com.zjclugger.oa.ui;

import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.base.BaseApplication;
import com.zjclugger.lib.entity.common.ERPChoiceItem;
import com.zjclugger.lib.ui.adapter.ERPChoiceAdapter;
import com.zjclugger.lib.ui.widget.ErpAlertDialog;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;
import com.zjclugger.oa.webapi.entity.response.AttendanceShiftsResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 考勤打卡<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceFragment extends OABaseFragment implements AMapLocationListener {
    private final static String TAG = "attendance";
    private String mPageTitle;
    @BindView(R2.id.llv_attendance_shifts)
    ExtendLabelValueView mAttendanceShiftsView; //班次
    private List<AttendanceShiftsResult> mShiftsList;
    private List<ERPChoiceItem> mShiftsTextList;
    private ERPChoiceAdapter mShiftsAdapter;
    private ErpAlertDialog mShiftsDialog;
    @BindView(R2.id.lvv_attendance_group)
    ExtendLabelValueView mAttendanceGroupView; //考勤组
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    //考勤位置坐标
    private LatLng mAttendanceLatLng = null;
    private LatLng mLocationLatLng = null;
    //todo:获取一个考勤定位对象

    @Override
    public int getLayout() {
        return R.layout.fragment_attendance_sign;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("考勤打卡");
        initAttendanceShiftsView();
        initLocation();
    }

    private void initAttendanceShiftsView() {
        //班次
        mShiftsDialog = new ErpAlertDialog(getActivity());
        mShiftsList = new ArrayList<>();
        mShiftsTextList = new ArrayList<>();

        AttendanceShiftsResult shiftsResult = new AttendanceShiftsResult();
        shiftsResult.setId(3001);
        shiftsResult.setName("08:30-18:00");
        mShiftsList.add(shiftsResult);

        shiftsResult = new AttendanceShiftsResult();
        shiftsResult.setId(3002);
        shiftsResult.setName("09:00-18:00 午休90分钟");
        mShiftsList.add(shiftsResult);

        shiftsResult = new AttendanceShiftsResult();
        shiftsResult.setId(30031);
        shiftsResult.setName("09:00-18:00 午休1小时");
        mShiftsList.add(shiftsResult);

        shiftsResult = new AttendanceShiftsResult();
        shiftsResult.setId(3004);
        shiftsResult.setName("休息");
        mShiftsList.add(shiftsResult);


        for (AttendanceShiftsResult item : mShiftsList) {
            mShiftsTextList.add(new ERPChoiceItem(item.getName()));
        }
   /*     WidgetUtils.toastMessage(getActivity(),
                "您选择了" + result.getName());
        //bindView();
        //TODO:重新绑定数据，确认保存方式，是perf?
        mAttendanceShiftsView.setValueText(result.getName());
        mShiftsDialog.dismiss();
        */
        mShiftsAdapter = new ERPChoiceAdapter(mShiftsTextList);
        mShiftsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WidgetUtils.toastMessage(getActivity(),
                        "您选中的是" + mShiftsTextList.get(position).getText());
                mAttendanceShiftsView.setValueText(mShiftsTextList.get(position).getText());
                mShiftsDialog.dismiss();
            }
        });


        mAttendanceShiftsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:打开选择框，并接收返回值
                mShiftsDialog.setData(mShiftsAdapter).show();
            }
        });
    }

    private void initLocation() {
        //option
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(false);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setLocationCacheEnable(false);
        //client
        mLocationClient = new AMapLocationClient(BaseApplication.getInstance());
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(this);
        mLocationClient.startLocation();

        mAttendanceLatLng = new LatLng(35.95201, 120.18606);
    }

    private void destroyLocation() {
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
        }
    }

    @Override
    public Boolean getPostBackData() {
        return false;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            mLocationLatLng = new LatLng(aMapLocation.getLatitude(),
                    aMapLocation.getLongitude());
            float mDistance = AMapUtils.calculateLineDistance(mLocationLatLng,
                    mAttendanceLatLng);

            Log.d(TAG, "两点距离" + mDistance);
        } else {
            String errText = "定位失败," + aMapLocation.getErrorCode() + ": "
                    + aMapLocation.getErrorInfo();
            Log.e(TAG, errText);
        }
    }
}
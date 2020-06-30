package com.zjclugger.oa.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.AlphaAnimation;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.AnimationSet;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.zjclugger.lib.base.BaseActivity;
import com.zjclugger.oa.R;

import java.util.List;

/**
 * 考勤定位
 */
public class AttendanceLocationActivity extends BaseActivity implements GeoFenceListener,
        LocationSource, AMapLocationListener, AMap.OnMapClickListener {

    private static final String TAG = "KING";
    public static final int STROKE_COLOR = Color.argb(180, 63, 145, 252);
    public static final int FILL_COLOR = Color.argb(163, 118, 212, 243);
    public static final int FILL_COLOR_OUT = Color.argb(163, 243, 212, 118);
    public static final float STROKE_WIDTH = 5F;
    // 地理围栏的广播action
    private static final String FENCE_BROADCAST_ACTION = "com.zjclugger.erp.geofence.round";
    private static final String CUSTOM_ID = "attendance";

    /**
     * 用于显示当前的位置
     * <p>
     * 示例中是为了显示当前的位置，在实际使用中，单独的地理围栏可以不使用定位接口
     * 【TODO：定位和围栏是否只选择使用一个即可？】
     * </p>
     */
    private AMapLocationClient mLocationClient;
    private OnLocationChangedListener mLocationChangedListener;
    private AMapLocationClientOption mLocationOption;

    private MapView mAttendanceMapView;
    private AMap mAttendanceMap;

    //考勤位置坐标
    private LatLng mAttendanceLatLng = null;
    // 实时位置坐标
    private LatLng mLocationLatLng = null;

    // 实时位置marker
    private Marker mLocationMarker;
    //考勤位置
    private Marker mAttendanceMarker;

    // 地理围栏客户端
    private GeoFenceClient mFenceClient = null;
    // 圆形半径
    private Circle mCircle;
    private float mCircleRadius = 300F;
    private float mGeoFenceRadius = 150F;
    private float mDistance;

    // 记录已经添加成功的围栏
    private OAViewModel mViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_location);
        mViewModel = ViewModelProviders.of(this).get(OAViewModel.class);

        mAttendanceMapView = findViewById(R.id.attendance_map);
        mAttendanceMapView.onCreate(savedInstanceState);

        initLocation();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //TODO: for test
        //onLocationChanged(latLng);
        mDistance = AMapUtils.calculateLineDistance(latLng, mAttendanceLatLng);
        mCircle.setFillColor(mDistance > mCircleRadius ? FILL_COLOR : FILL_COLOR_OUT);
        Log.d(TAG, "----onMapClick-------" + (mDistance > mCircleRadius ? "不在考勤范围内" : "已进入考勤范围"));
    }

    @Override
    protected boolean isDetailActivity() {
        return true;
    }

    @Override
    protected String getDetailActivityTitle() {
        return "考勤打卡";
    }

    void initLocation() {
        initMap();

        IntentFilter filter = new IntentFilter();
        filter.addAction(FENCE_BROADCAST_ACTION);
        registerReceiver(mGeoFenceReceiver, filter);

        // 初始化地理围栏
        //TODO:可能不需要围栏，而只通过两点间距离来判断
        mFenceClient = new GeoFenceClient(getApplicationContext());
        mFenceClient.setActivateAction(GeoFenceClient.GEOFENCE_IN | GeoFenceClient.GEOFENCE_OUT);
        mFenceClient.setGeoFenceListener(this);
        mFenceClient.createPendingIntent(FENCE_BROADCAST_ACTION);

        initAttendanceRange();

        if (mLocationMarker == null) {
            mLocationMarker = mAttendanceMap.addMarker(new MarkerOptions().draggable(true));
            mLocationMarker.showInfoWindow();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void initMap() {
        mAttendanceMap = mAttendanceMapView.getMap();
        mAttendanceMap.setOnMapClickListener(this);
        mAttendanceMap.getUiSettings().setRotateGesturesEnabled(false);
        // mAttendanceMap.moveCamera(CameraUpdateFactory.zoomBy(8));
        mAttendanceMap.setLocationSource(this);// 设置定位监听
        mAttendanceMap.getUiSettings().setCompassEnabled(true);
        mAttendanceMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mAttendanceMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false

        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(
                BitmapDescriptorFactory.fromResource(R.mipmap.ic_attendance_location));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 120));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(6);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 240, 0, 0));
        // 将自定义的 myLocationStyle 对象添加到地图上
        mAttendanceMap.setMyLocationStyle(myLocationStyle);

        // 表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        mAttendanceMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

    }

    private void initAttendanceRange() {
        mAttendanceLatLng = new LatLng(35.95201, 120.18606);
        DPoint centerPoint = new DPoint(35.95201, 120.18606);
        mFenceClient.addGeoFence(centerPoint, mCircleRadius, CUSTOM_ID);
        //加圆形
        mCircle = mAttendanceMap.addCircle(new CircleOptions().center(mAttendanceLatLng)
                .radius(mCircleRadius).strokeColor(STROKE_COLOR)
                .fillColor(FILL_COLOR).strokeWidth(STROKE_WIDTH));

        mAttendanceMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mAttendanceLatLng, 16));
        initAttendanceMarker();
    }

    private void initAttendanceMarker() {
        if (mAttendanceMarker == null) {
            LatLng latLng = new LatLng(mAttendanceLatLng.latitude - 0.02,
                    mAttendanceLatLng.longitude - 0.02);
            mAttendanceMarker =
                    mAttendanceMap.addMarker(new MarkerOptions().position(latLng).zIndex(1)
                            .anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_circle_orange_small)));
            mAttendanceMap.addMarker(new MarkerOptions().position(latLng).zIndex(2)
                    .anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_circle_orange_small)));
        }

        mAttendanceMarker.setTitle("考勤地址");
        mAttendanceMarker.setSnippet("中南金石国际广场");
        mAttendanceMarker.showInfoWindow();
        mAttendanceMarker.setPosition(mAttendanceLatLng);

        // 动画执行完成后，默认会保持到最后一帧的状态
        AnimationSet animationSet = new AnimationSet(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(2000);
        // 设置不断重复
        alphaAnimation.setRepeatCount(Animation.INFINITE);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 6f, 1, 6f);
        scaleAnimation.setDuration(2000);
        // 设置不断重复
        scaleAnimation.setRepeatCount(Animation.INFINITE);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setInterpolator(new LinearInterpolator());
        mAttendanceMarker.setAnimation(animationSet);
        mAttendanceMarker.startAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAttendanceMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAttendanceMapView.onPause();
        deactivate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAttendanceMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAttendanceMapView.onDestroy();
        try {
            unregisterReceiver(mGeoFenceReceiver);
        } catch (Throwable e) {
        }

        if (null != mFenceClient) {
            mFenceClient.removeGeoFence();
        }
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    StringBuffer sb = new StringBuffer();
                    sb.append("添加围栏成功");
                    String customId = (String) msg.obj;
                    if (!TextUtils.isEmpty(customId)) {
                        sb.append("customId: ").append(customId);
                    }
                    Toast.makeText(getApplicationContext(), sb.toString(),
                            Toast.LENGTH_SHORT).show();
                    //drawFence2Map();
                    break;
                case 1:
                    int errorCode = msg.arg1;
                    Toast.makeText(getApplicationContext(),
                            "添加围栏失败 " + errorCode, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String statusStr = (String) msg.obj;
                 /*   tvResult.setVisibility(View.VISIBLE);
                    tvResult.append(statusStr + "\n");*/
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onGeoFenceCreateFinished(final List<GeoFence> geoFenceList,
                                         int errorCode, String customId) {
        Log.d(TAG, "-------onGeoFenceCreateFinished-----");
        Message msg = Message.obtain();
        if (errorCode == GeoFence.ADDGEOFENCE_SUCCESS) {
            msg.obj = customId;
            msg.what = 0;
        } else {
            msg.arg1 = errorCode;
            msg.what = 1;
        }
        mHandler.sendMessage(msg);
    }

    /**
     * 接收触发围栏后的广播,当添加围栏成功之后，会立即对所有围栏状态进行一次侦测，如果当前状态与用户设置的触发行为相符将会立即触发一次围栏广播；
     * 只有当触发围栏之后才会收到广播,对于同一触发行为只会发送一次广播不会重复发送，除非位置和围栏的关系再次发生了改变。
     */
    private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "-------onReceive-----" + intent.getAction());
            // 接收广播
            if (intent.getAction().equals(FENCE_BROADCAST_ACTION)) {
                Bundle bundle = intent.getExtras();
                String customId = bundle
                        .getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                String fenceId = bundle.getString(GeoFence.BUNDLE_KEY_FENCEID);
                //status标识的是当前的围栏状态，不是围栏行为
                int status = bundle.getInt(GeoFence.BUNDLE_KEY_FENCESTATUS);
                StringBuffer sb = new StringBuffer();
                switch (status) {
                    case GeoFence.STATUS_LOCFAIL:
                        sb.append("定位失败");
                        break;
                    case GeoFence.STATUS_IN:
                        sb.append("进入围栏 ");
                        break;
                    case GeoFence.STATUS_OUT:
                        sb.append("离开围栏 ");
                        break;
                    case GeoFence.STATUS_STAYED:
                        sb.append("停留在围栏内 ");
                        break;
                    default:
                        break;
                }
                if (status != GeoFence.STATUS_LOCFAIL) {
                    if (!TextUtils.isEmpty(customId)) {
                        sb.append(" customId: " + customId);
                    }
                    sb.append(" fenceId: " + fenceId);
                }
                String str = sb.toString();
                Message msg = Message.obtain();
                msg.obj = str;
                msg.what = 2;
                mHandler.sendMessage(msg);
            }
        }
    };

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mLocationChangedListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mLocationChangedListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                mLocationLatLng = new LatLng(amapLocation.getLatitude(),
                        amapLocation.getLongitude());
                mDistance = AMapUtils.calculateLineDistance(mLocationLatLng, mAttendanceLatLng);
                if (mDistance > mGeoFenceRadius) {
                    mLocationMarker.setTitle("不在考勤范围内");
                    //todo:setPosition后会显示蓝色定位图标
                    //mLocationMarker.setPosition(mLocationLatLng);
                } else {
                    mLocationMarker.setTitle("已进入考勤范围");
                    //mLocationMarker.setPosition(mLocationLatLng);
                }

                //todo:获取两点之前距离
                Log.d(TAG, "两点距离" + mDistance);
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": "
                        + amapLocation.getErrorInfo();
                Log.e(TAG, errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        Log.d(TAG, "--------activate---");
        mLocationChangedListener = listener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
            mLocationOption.setOnceLocation(false);
            mLocationOption.setNeedAddress(true);
            //关闭缓存机制
            mLocationOption.setLocationCacheEnable(false);

            // 设置定位监听
            mLocationClient.setLocationListener(this);
            // 设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动
            mLocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mLocationChangedListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }
}

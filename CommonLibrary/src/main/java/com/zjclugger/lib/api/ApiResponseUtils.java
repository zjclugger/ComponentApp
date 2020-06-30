package com.zjclugger.lib.api;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zjclugger.basiclib.Log;
import com.zjclugger.lib.R;
import com.zjclugger.lib.api.entity.WrapperEntity;
import com.zjclugger.lib.base.BaseApplication;
import com.zjclugger.lib.utils.CommonUtils;
import com.zjclugger.lib.utils.NetworkUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.router.ARouterConstants;

import org.json.JSONObject;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ApiResponseUtils {
    private static final String TAG = "ApiUtils";
    public static final int RESULT_CODE_SUCCESS = 0;
    public static final int RESULT_CODE_UNAUTHORIZED = 401;   //Unauthorized
    public static final int RESULT_CODE_SERVER_ERROR = 40001;   //系统内部错误
    public static final int RESULT_CODE_AUTH_ERROR = 70001;    //无访问权限
    public static final int RESULT_CODE_DATA_EXIST = 50002;    //数据有误:数据库中已存在的记录
    public static final int RESULT_CODE_LOGIN_VALIDATION_ERROR = 30011;    //验证未通过:登录验证失败

    private ApiResponseUtils() {
    }

    /**
     * 接口访问是否成功（只有返回了数据才能认为成功，即使数据为null）
     *
     * @param response
     * @param customErrorMessage
     * @return
     */
    public static boolean isSuccess(ApiResponse response, String customErrorMessage) {
        return isSuccess(response, customErrorMessage, true);
    }

    public static boolean isSuccess(ApiResponse response, String customErrorMessage,
                                    boolean isToast) {
        String errorMessage = customErrorMessage;

        if (response != null) {
            if (response.isSuccess()) {
                //访问成功
                if (response.body != null) {
                    WrapperEntity result = (WrapperEntity) response.body;
                    if (result.isSuccess()) {
                        //成功
                        errorMessage = "";
                    } else {
                        //TODO: 根据code获取对应的字符串资源
                        if (result.getCode() == RESULT_CODE_UNAUTHORIZED) {
                            //跳转登录
                            errorMessage =
                                    BaseApplication.getInstance().getString(R.string.error_authentication);
                            //异步
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                        ARouter.getInstance()
                                                .build(ARouterConstants.Path.USER_LOGIN)
                                                .navigation(BaseApplication.getInstance());
                                        BaseApplication.getInstance().killAllActivity();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else {
                            errorMessage = result.getMessage();
                        }
                    }
                } else {
                    //类型转换失败?
                    Log.e("Data result type conversion failed!");
                }
            } else {
                Log.e("error message is " + errorMessage + ",Other error: " + response.errorMessage);
                //访问失败
                if (!NetworkUtils.isNetworkConnected(BaseApplication.getInstance())) {
                    errorMessage =
                            BaseApplication.getInstance().getResources().getString(R.string.error_network_unreachable);
                } else {
                    //其他待处理场景
                    //是否登录
                    if (!TextUtils.isEmpty(response.errorMessage)) {
                        JSONObject jsonObject = CommonUtils.parseJson(response.errorMessage);
                        if (jsonObject == null) {
                            //非接口返回错误
                            if (response.errorMessage.contains("SocketTimeoutException") || response.errorMessage.contains("timeout")) {
                                errorMessage =
                                        BaseApplication.getInstance().getString(R.string.error_network_timeout);
                            } else if (response.errorMessage.contains("Failed to connect to")) {
                                errorMessage =
                                        BaseApplication.getInstance().getString(R.string.error_server_unreachable);
                            }
                        }
                    }
                }
            }
        } else {
            errorMessage = BaseApplication.getInstance().getString(R.string.error_access_exception);
        }

        if (!TextUtils.isEmpty(errorMessage)) {
            if (isToast) {
                WidgetUtils.toastMessage(BaseApplication.getInstance(), errorMessage);
            }
            return false;
        }

        return true;
    }
}
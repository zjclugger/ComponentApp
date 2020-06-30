package com.zjclugger.lib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络相关的操作方法
 * Created by King.Zi on 2020/4/9.
 */
public class NetworkUtils {
    private NetworkUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 是否网络已连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = connManager.getActiveNetworkInfo();
            if (null != mNetworkInfo) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 是否WIFI网络已经连接
     *
     * @param context
     * @param networkType integer specifying which networkType in which you're interested.[ConnectivityManager.TYPE_*]
     * @return
     */
    public static boolean isConnected(Context context, int networkType) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connManager.getNetworkInfo(networkType);

        return null != netInfo ? netInfo.isAvailable() && netInfo.getState() == NetworkInfo.State.CONNECTED : false;
    }

    /**
     * @param context
     * @return see ConnectivityManager.TYPE_*
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connManager.getActiveNetworkInfo();
            if (null != netInfo && netInfo.isAvailable()) {
                return netInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 获取网络状态
     *
     * @param context
     * @return
     */
    public static NetworkInfo.State getState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return null != connManager.getActiveNetworkInfo() ? connManager.getActiveNetworkInfo().getState() :
                NetworkInfo.State.UNKNOWN;
    }

    /**
     * WIFI网络是否正常
     *
     * @param context
     * @return
     */
    public static boolean isWiFiConnected(Context context) {
        return isConnected(context, ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isNetworkAvailable(Context context) {
        Context appContext = context.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

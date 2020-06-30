package com.zjclugger.lib.utils;

import android.content.Context;

import com.zjclugger.lib.R;
import com.zjclugger.lib.ui.widget.JProgressDialog;
import com.zjclugger.lib.ui.widget.JToast;

/**
 * 控件工具类<br>
 * Created by King.Zi on 2018/7/8.<br>
 */
public class WidgetUtils {
    private static final String TAG = "WidgetUtils";

    private WidgetUtils() {
    }

    public static void toast(Context context, int strResId, boolean longTime) {
        new JToast(context, strResId, longTime);
    }

    public static void toast(Context context, String message, boolean longTime) {
        new JToast(context, message, longTime);
    }

    public static void toast(Context context, int strResId) {
        new JToast(context, strResId, true);
    }

    public static void toastOKMessage(Context context, int strResId) {
        new JToast(context, R.drawable.ic_ok, strResId);
    }

    public static void toastOKMessage(Context context, String message) {
        new JToast(context, R.drawable.ic_ok, message);
    }

    public static void toastErrorMessage(Context context, int strResId) {
        new JToast(context, R.drawable.ic_error, strResId, true);
    }

    public static void toastErrorMessage(Context context, String message) {
        new JToast(context, R.drawable.ic_error, message, true);
    }

    public static void toastMessage(Context context, int strResId) {
        new JToast(context, R.drawable.ic_information, strResId, true);
    }

    public static void toastMessage(Context context, String message) {
        new JToast(context, R.drawable.ic_information, message, true);
    }

    /**
     * 显示等待框
     *
     * @param dialog
     * @param strResId
     * @param cancelable
     */
    public static void showProgressDialog(JProgressDialog dialog, int strResId,
                                          boolean cancelable) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show(strResId, cancelable);
        }
    }

    /**
     * 显示等待框
     *
     * @param dialog
     * @param message
     * @param cancelable
     */
    public static void showProgressDialog(JProgressDialog dialog, String message,
                                          boolean cancelable) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show(message, cancelable);
        }
    }

    /**
     * 显示等待框
     *
     * @param dialog
     * @param strResId
     * @param cancelable
     * @param listener
     * @param time
     */
    public static void showProgressDialog(JProgressDialog dialog, int strResId,
                                          boolean cancelable, Monitor
                                                  .TimeoutListener listener, int time) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show(strResId, cancelable, listener, time);
        }
    }

    /**
     * 关闭等待框
     */
    public static void closeProgressDialog(JProgressDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
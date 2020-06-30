package com.zjclugger.lib.ui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjclugger.lib.R;
import com.zjclugger.lib.utils.Monitor;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class JProgressDialog extends Dialog {

    private static final String TAG = "ErpProgressDialog";
    private Context mContext;
    private static final int MSG_PROGRESS_TIME_OUT = 1111;
    private static final int TIME_OUT = 30 * 1000;
    private Monitor.TimeoutListener mTimeoutListener;
    private Handler mHandler;

    public JProgressDialog(Context context) {
        this(context, true);
    }

    public JProgressDialog(Context context, boolean cancelable) {
        super(context, R.style.CustomProgressDialog);
        this.setCancelable(cancelable);
        this.mContext = context;
        initHandler();
    }

    private void initHandler() {
        mHandler = new Handler(mContext.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_PROGRESS_TIME_OUT:
                        if (null != mTimeoutListener) {
                            mTimeoutListener.onTimeout();
                        }
                        cancel();
                        break;
                    default:
                        Log.e(TAG, "unknown message is " + msg.what);
                        break;
                }
            }
        };
    }

    public void show(int stringId) {
        show(stringId, true);
    }

    public void show(int stringId, boolean cancelable) {
        String message = "";
        if (stringId != 0) {
            message = mContext.getResources().getString(stringId);
        }
        showDialog(message, cancelable);
    }

    public void show(String message, boolean cancelable) {
        showDialog(message, cancelable);
    }

    /**
     * 显示对话框
     *
     * @param stringId
     * @param cancelable
     * @param listener
     * @param time       超时时间,单位秒
     */
    public void show(int stringId, boolean cancelable, Monitor.TimeoutListener listener, int time) {
        show(stringId, cancelable);
        mTimeoutListener = listener;
        mHandler.removeMessages(MSG_PROGRESS_TIME_OUT);
        mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_TIME_OUT, time * 1000);
    }

    private void showDialog(String message, boolean cancelable) {
        setContentView(R.layout.dialog_progress);
        setCancelable(cancelable);
        TextView msgText = (TextView) findViewById(R.id.message);
        ImageView imgProgress = (ImageView) findViewById(R.id.pro_img);
        Animation imgAnimation = AnimationUtils.loadAnimation(mContext, R.anim.progress_img);
        imgProgress.startAnimation(imgAnimation);
        if (TextUtils.isEmpty(message)) {
            msgText.setVisibility(View.GONE);
        } else {
            msgText.setText(message);
        }
        try {
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示圣对话框
     *
     * @param stringId
     * @param cancelable
     * @param listener   超时监听器,默认为30秒
     */
    public void show(int stringId, boolean cancelable, Monitor.TimeoutListener listener) {
        show(stringId, cancelable);
        mTimeoutListener = listener;
        mHandler.removeMessages(MSG_PROGRESS_TIME_OUT);
        mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_TIME_OUT, TIME_OUT);
    }

    @Override
    public void dismiss() {
        try {
            if (mHandler != null) {
                mHandler.removeMessages(MSG_PROGRESS_TIME_OUT);
            }
            if (mContext != null && mContext instanceof Activity
                    && !((Activity) mContext).isFinishing()) {
                super.dismiss();
            }
        } catch (Exception e) {
            Log.e(TAG, "exception e = " + e);
        }
    }
}

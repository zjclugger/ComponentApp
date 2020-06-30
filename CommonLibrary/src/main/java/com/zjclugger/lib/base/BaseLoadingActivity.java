package com.zjclugger.lib.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * 加载页<br>
 * Created by King.zi on 2020/4/9.
 */
public abstract class BaseLoadingActivity extends Activity {
    private static final String TAG = "Loading";
    private boolean mIsInit = false;

    /**
     * 系统开机或重启后初始化
     */
    public abstract void initApp();

    /**
     * 设置启动页
     */
    public abstract void configStartPage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading();
    }

    protected long waitingTime() {
        return 1000l;
    }

    private void loading() {
        if (mIsInit) {
            return;
        }
        mIsInit = true;

        //显示加载页
        new Thread(() -> {
            Log.d(TAG, "App Loading...");
            initApp();

            try {
                //加载太快,等待Xs
                Thread.sleep(waitingTime());
            } catch (InterruptedException e) {
            }

            //如果未进行启动设置，则跳转到设置向导页，否则跳转到主页
            configStartPage();
            finish();
        }).start();
    }
}
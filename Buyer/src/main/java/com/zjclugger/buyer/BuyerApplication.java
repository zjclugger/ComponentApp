package com.zjclugger.buyer;

import android.app.Application;
import android.content.res.Configuration;

import com.google.auto.service.AutoService;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.zjclugger.buyer.ui.LoginActivity;
import com.zjclugger.lib.base.BaseApplication;
import com.zjclugger.lib.base.IComponentApplication;
import com.zxy.recovery.core.Recovery;

import java.util.Map;

/**
 * <br>
 * Created by King.Zi on 2020/04/09.<br>
 * Copyright (c) 2020 zjclugger.com
 */
@AutoService(IComponentApplication.class)
public class BuyerApplication implements IComponentApplication {
    Application mApp;

    @Override
    public void onCreate(BaseApplication baseApplication) {
        mApp = baseApplication;
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(LoginActivity.class)
                .init(baseApplication);
        LiveEventBus.config()
                .supportBroadcast(mApp)
                .lifecycleObserverAlwaysActive(true)
                .autoClear(true);
    }

    @Override
    public void enterBackground() {

    }

    @Override
    public void enterForeground() {

    }

    @Override
    public void receiveRemoteNotification(Map<String, String> value) {

    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onConfigurationChanged(Configuration value) {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int value) {

    }

    @Override
    public Application getApplication() {
        return mApp;
    }
}

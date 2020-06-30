package com.zjclugger.oa;

import android.app.Application;
import android.content.res.Configuration;

import com.zjclugger.lib.base.BaseApplication;
import com.zjclugger.lib.base.IComponentApplication;

import java.util.Map;

public class OAApplication implements IComponentApplication {
    Application mApp;

    @Override
    public void onCreate(BaseApplication baseApplication) {
        mApp = baseApplication;
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

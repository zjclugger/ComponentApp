package com.zjclugger.lib.base;

import android.app.Application;
import android.content.res.Configuration;

import java.util.Map;

public interface IComponentApplication {

    void onCreate(BaseApplication baseApplication);

    void enterBackground();

    void enterForeground();

    void receiveRemoteNotification(Map<String, String> value);

    void onTerminate();

    void onConfigurationChanged(Configuration value);

    void onLowMemory();

    void onTrimMemory(int value);

    Application getApplication();
}

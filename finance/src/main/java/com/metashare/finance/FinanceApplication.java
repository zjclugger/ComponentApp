package com.zjclugger.finance;

import android.app.Application;
import android.content.res.Configuration;

import com.google.auto.service.AutoService;
import com.zjclugger.lib.annotation.ModuleAnnotation;
import com.zjclugger.lib.base.BaseApplication;
import com.zjclugger.lib.base.IComponentApplication;

import java.util.Map;

@ModuleAnnotation(moduleName = "FINANCE", className = FinanceApplication.class)
@AutoService(IComponentApplication.class)
public class FinanceApplication implements IComponentApplication {
    Application mApp;

 /*   static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于
                // %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }*/

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
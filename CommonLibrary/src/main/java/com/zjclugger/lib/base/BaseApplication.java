package com.zjclugger.lib.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zjclugger.basiclib.CrashHandler;
import com.zjclugger.lib.BuildConfig;
import com.zjclugger.lib.annotation.ModuleAnnotation;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ServiceLoader;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.external.ExternalAdaptManager;
import me.jessyan.autosize.onAdaptListener;
import me.jessyan.autosize.utils.LogUtils;

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static BaseApplication sInstance;
    private List<IComponentApplication> mApplicationList;

    private int mCurrentCount = 0;
    private WeakReference<Activity> mCurrentActivity = null;
    private boolean isCurrent = false;
    private List<Activity> mActivityList = new ArrayList<>();
    private List<Activity> mResumeActivity = new ArrayList<>();

    public BaseApplication() {
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        this.mCurrentCount = 0;
        this.registerActivityLifecycleCallbacks(new BaseApplication.ActivityLifecycleCallbacksImpl());

        init();

        //onCreate
        for (IComponentApplication application : mApplicationList) {
            Log.i(TAG, "application=" + application.getClass());
            application.onCreate(this);
        }
    }

    private void init() {
        loadComponentApplication();
        initARouter();
        initLogger();
        initCrashHandler();
        initAutoSize();

        //shape and selector
        //DevShapeUtils.init(this);
    }

    /**
     * 初始化Auto Size
     */
    private void initAutoSize() {
        //多进程时，才是必须配置
        AutoSize.initCompatMultiProcess(this);

        AutoSizeConfig.getInstance()
                .setCustomFragment(true) //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启,
                // 如果没有这个需求建议不开启
                .setOnAdaptListener(new onAdaptListener() {
                    //屏幕适配监听器
                    @Override
                    public void onAdaptBefore(Object target, Activity activity) {
                        //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
                        //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面,
                        // ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
//                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize
//                        (activity)[0]);
//                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize
//                        (activity)[1]);
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!",
                                target.getClass().getName()));
                    }

                    @Override
                    public void onAdaptAfter(Object target, Activity activity) {
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!",
                                target.getClass().getName()));
                    }
                })
                // .setUseDeviceSize(true)  //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false,
                // 在以屏幕高度为基准进行适配时AutoSize 会将屏幕总高度减去状态栏高度来做适配,设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
                // .setBaseOnWidth(false)    //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize
                // 会全局按照高度进行适配
                // .setAutoAdaptStrategy(new AutoAdaptStrategy()) //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
                .setLog(false);   //打印 AutoSize 的内部日志, 默认为 true;
        /**
         * {@link ExternalAdaptManager} 是一个管理外部三方库的适配信息和状态的管理类, 详细介绍请看
         * {@link ExternalAdaptManager} 的类注释
         */
        //AutoSizeConfig.getInstance().getExternalAdaptManager()
        //加入的 Activity 将会放弃屏幕适配, 一般用于三方库的 Activity, 详情请看方法注释
        //如果不想放弃三方库页面的适配, 请用 addExternalAdaptInfoOfActivity 方法, 建议对三方库页面进行适配, 让自己的 App 更完美一点
//                .addCancelAdaptOfActivity(DefaultErrorActivity.class)

        //为指定的 Activity 提供自定义适配参数, AndroidAutoSize 将会按照提供的适配参数进行适配, 详情请看方法注释
        //一般用于三方库的 Activity, 因为三方库的设计图尺寸可能和项目自身的设计图尺寸不一致, 所以要想完美适配三方库的页面
        //就需要提供三方库的设计图尺寸, 以及适配的方向 (以宽为基准还是高为基准?)
        //三方库页面的设计图尺寸可能无法获知, 所以如果想让三方库的适配效果达到最好, 只有靠不断的尝试
        //由于 AndroidAutoSize 可以让布局在所有设备上都等比例缩放, 所以只要您在一个设备上测试出了一个最完美的设计图尺寸
        //那这个三方库页面在其他设备上也会呈现出同样的适配效果, 等比例缩放, 所以也就完成了三方库页面的屏幕适配
        //即使在不改三方库源码的情况下也可以完美适配三方库的页面, 这就是 AndroidAutoSize 的优势
        //但前提是三方库页面的布局使用的是 dp 和 sp, 如果布局全部使用的 px, 那 AndroidAutoSize 也将无能为力
        //经过测试 DefaultErrorActivity 的设计图宽度在 380dp - 400dp 显示效果都是比较舒服的
        // .addExternalAdaptInfoOfActivity(DefaultErrorActivity.class, new ExternalAdaptInfo
        // (true, 400));
    }

    /**
     * 初始化路由
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {
            //若开启InstantRun，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(sInstance);
    }

    /**
     * 初始化日志捕捉器
     */
    private void initLogger() {
        //TODO:debug-console:output; release:output file
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to setOnClickListener thread info or
                // not. Default
                // true
                .methodCount(2)         // (Optional) How many method line to setOnClickListener.
                // Default 2
                .methodOffset(5)        // (Optional) Hides internal method calls up to offset.
                // Default 5
                .logStrategy(new LogcatLogStrategy()) // (Optional) Changes the log strategy to
                // print out. Default LogCat
                .tag("ERP")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        com.zjclugger.basiclib.Log.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    /**
     * 初始化崩溃处理器
     */
    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }

    private void loadComponentApplication() {
        mApplicationList = new ArrayList<>();
        //加载Fragment 类型的ServiceLoader
        ServiceLoader<IComponentApplication> iterator =
                ServiceLoader.load(IComponentApplication.class);

        for (Iterator<IComponentApplication> iterator1 = iterator.iterator(); iterator1.hasNext(); ) {
            final IComponentApplication iApp = iterator1.next();

            //获取ITabPage 注解的类
            ModuleAnnotation property = iApp.getClass().getAnnotation(ModuleAnnotation.class);
            if (property == null) {
                continue;
            }

            Log.d(TAG,
                    "iapp=" + property.moduleName() + "," + "iapp=" + iApp + ",class=" + property.className());
            try {
                Object obj = property.className().newInstance();
                mApplicationList.add((IComponentApplication) obj);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "IllegalAccessException: " + e.getMessage());
            } catch (InstantiationException e) {
                Log.e(TAG, "InstantiationException: " + e.getMessage());
            }
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        Log.d(TAG, "attachBaseContext()");
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (IComponentApplication application : mApplicationList) {
            application.onTerminate();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        for (IComponentApplication application : mApplicationList) {
            application.onConfigurationChanged(configuration);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        for (IComponentApplication application : mApplicationList) {
            application.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        for (IComponentApplication application : mApplicationList) {
            application.onTrimMemory(level);
        }
    }

    public Activity getCurrentActivity() {
        return this.mCurrentActivity != null ? (Activity) this.mCurrentActivity.get() : null;
    }

    public boolean isAppRunningBackground() {
        boolean result = false;
        if (this.mCurrentCount == 0) {
            result = true;
        }
        return result;
    }

    public void killAllActivity() {
        try {
            Iterator activityIterator = this.mActivityList.iterator();

            while (activityIterator.hasNext()) {
                Activity activity = (Activity) activityIterator.next();
                activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void killAllActivityExcept(Class<Activity> exceptActivity) {
        try {
            Iterator activityIterator = this.mActivityList.iterator();

            while (activityIterator.hasNext()) {
                Activity activity = (Activity) activityIterator.next();
                if (!activity.getClass().equals(exceptActivity)) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "kill activity has exception the message is " + e.getMessage());
        }
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private class ActivityLifecycleCallbacksImpl implements ActivityLifecycleCallbacks {
        private ActivityLifecycleCallbacksImpl() {
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            Log.d(TAG, "onActivityCreated --> " + activity.getClass().getName());
            BaseApplication.this.mActivityList.add(0, activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.d(TAG, "onActivityStarted --> " + activity.getClass().getName());
            if (BaseApplication.this.mCurrentCount == 0 && !BaseApplication.this.isCurrent) {
                BaseApplication.this.isCurrent = true;
               /* for (IComponentApplication application : ModuleManager.getInstance()
               .getAppDelegateList()) {
                    application.enterForeground();
                }*/
                Log.d("BaseApplication", "The App go to foreground");
            }
            BaseApplication.this.mCurrentCount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.d(TAG, "onActivityResumed --> " + activity.getClass().getName());
            if (!BaseApplication.this.mResumeActivity.contains(activity)) {
                BaseApplication.this.mResumeActivity.add(activity);
            }
            BaseApplication.this.mCurrentActivity = new WeakReference(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.d(TAG, "onActivityPaused --> " + activity.getClass().getName());
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.d(TAG, "onActivityStopped --> " + activity.getClass().getName());
            BaseApplication.this.mResumeActivity.remove(activity);
            BaseApplication.this.mCurrentCount--;
            if (BaseApplication.this.mCurrentCount == 0 && BaseApplication.this.isCurrent) {
                BaseApplication.this.isCurrent = false;
              /*  for (IComponentApplication application : ModuleManager.getInstance()
              .getAppDelegateList()) {
                    application.enterBackground();
                }*/
                Log.d(TAG, "The App go to background");
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            Log.d(TAG, "onActivitySaveInstanceState --> " + activity.getClass().getName());
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.d(TAG, "onActivityDestroyed --> " + activity.getClass().getName());
            BaseApplication.this.mActivityList.remove(activity);
        }
    }
}

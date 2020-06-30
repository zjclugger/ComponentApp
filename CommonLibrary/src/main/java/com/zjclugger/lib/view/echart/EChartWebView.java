package com.zjclugger.lib.view.echart;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.github.abel533.echarts.json.GsonOption;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.utils.LogUtils;

/**
 * @title <br>
 * Created by King.Zi on 2020/02/12.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class EChartWebView extends WebView {
    private static final String TAG = "EChartView";

    /**
     * 默认false
     * 在AndroidEChart.html和AndroidEChart.js中因开发调试多处调用了showMessage(msg) 可以设置为true开启调试模式
     */
    private boolean isDebug = true;

    /**
     * 存放在第一时间需要Android调用js的function
     * 在第一次AndroidEChart.html和AndroidEChart.js还没有加载完成，而Java代码却是在第一时间调用了
     * 所以需要等到html的标签及js加载成功后再调用，在WebViewClient中的onPageFinished方法中
     */
    private List<String> shouldCallOnPageFinishedJSList = new ArrayList();
    private DataSource dataSource;

    public EChartWebView(Context context) {
        this(context, null);
    }

    public EChartWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EChartWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebViewClient();
        init();
    }

    private void init() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        addJavascriptInterface(new EChartWebView.WebAppEChartInterface(getContext()), "Android");
        loadUrl("file:///android_asset/web/echart/AndroidEChart.html");
    }

    private void initWebViewClient() {
        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    Log.d(TAG, "on progress changed 100");
                }
            }
        });

        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //页面开始加载。一般在此弹出进度对话框
                super.onPageStarted(view, url, favicon);
                Log.d(TAG, "on page started");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //页面加载结束。一般在此关闭进度对话框
                super.onPageFinished(view, url);
                Log.d(TAG, "on page finished-++++--");
                if (null != onAddEChartActionListener) {
                    onAddEChartActionListener.onWebViewLoadFinished(view, url);
                }
                for (String callJs : shouldCallOnPageFinishedJSList) {
                    Log.d(TAG, "on page finished---" + callJs);
                    loadUrl(callJs);
                }
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.d(TAG, "on load res");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                //收到错误信息
                super.onReceivedError(view, request, error);
            }
        });
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public boolean isDebug() {
        return isDebug;
    }

    /**
     * 刷新图表
     * java调用js的refreshEChartsWithOption方法刷新echart
     * 不能在第一时间就用此方法来显示图表，因为第一时间html的标签还未加载完成，不能获取到标签值
     * 需先设置数据源DataSource，后续视具体情况来手动刷新
     *
     * @param option
     */
    public void refreshEChartsWithOption(GsonOption option) {
        if (dataSource == null) {
            assert false : "dataSource == null";
        }
        if (null != option) {
            String optionString = option.toString();
            String call = "javascript:refreshEChartsWithOption('" + optionString + "')";
            loadUrl(call);
        } else {
            Log.e(TAG, "option is null");
        }
    }

    /**
     * 添加图表事件响应监听
     *
     * @param echartActions                                  事件名称数组
     * @param onAddEChartActionHandlerResponseResultListener 事件点击后的echart返回的事件信息
     *                                                       (echart返回的事件信息:http://echarts.baidu
     *                                                       .com/api.html#events) 响应监听给开发者
     */
    public void addEChartActionHandler(EChartConstant.EChartAction[] echartActions,
                                       OnAddEChartActionListener onAddEChartActionHandlerResponseResultListener) {
        this.onAddEChartActionListener =
                onAddEChartActionHandlerResponseResultListener;
        for (EChartConstant.EChartAction echartAction : echartActions) {
            String callString = echartAction.actionValue;
            String call = "javascript:addEChartActionHandler('" + callString + "')";
            shouldCallOnPageFinishedJSList.add(call);
        }
    }

    /**
     * 移除图表事件响应监听
     *
     * @param echartAction 事件名称
     */
    public void removeEChartActionHandler(EChartConstant.EChartAction echartAction) {
        String callString = echartAction.actionValue;
        String call = "javascript:removeEChartActionHandler('" + callString + "')";
        loadUrl(call);
    }

    /**
     * 显示Echart自带的默认样式的Loading
     */
    public void androidChartShowLoading() {
        String call = "javascript:androidChartShowLoading()";
        //loadUrl(call);
        Log.d(TAG, "show chart loading");
        shouldCallOnPageFinishedJSList.add(call);
    }

    /**
     * 隐藏Echart自带的默认样式的Loading
     */
    public void androidChartHideLoading() {
        String call = "javascript:androidChartHideLoading()";
        Log.d(TAG, "on hide loading");
        loadUrl(call);
    }

    ///////////////////////WebAppEChartInterface////////////////////////////////

    /**
     * js 与 Android原生交互接口
     */
    class WebAppEChartInterface {
        Context context;

        public WebAppEChartInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void printDebugMessage(String message) {
            if (isDebug) {
                LogUtils.e(message);
                Log.d(TAG, message);
            }
        }

        @JavascriptInterface
        public void showMessage(String message) {
            if (isDebug) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 获取图表配置JSON数据
         *
         * @return
         */
        @JavascriptInterface
        public String getChartOptions() {
            if (dataSource != null) {
                GsonOption option = dataSource.markChartOptions();
                Log.d(TAG, option.toString());
                return option.toString();
            }
            return null;
        }

        /**
         * 添加图表事件响应监听
         *
         * @param params EChart返回的事件信息 http://echarts.baidu.com/api.html#events
         */
        @JavascriptInterface
        public void addEChartActionHandlerResponseResult(String params) {
            LogUtils.e(params);
            if (onAddEChartActionListener != null) {
                onAddEChartActionListener.actionHandlerResponseResult(params);
            }
        }

        /**
         * 移除图表事件响应监听
         *
         * @param params echart返回的事件信息 http://echarts.baidu.com/api.html#events
         */
        @JavascriptInterface
        public void removeEChartActionHandlerResponseResult(String params) {
            LogUtils.e(params);
        }
    }

    ///////////////////////WebAppEChartInterface////////////////////////////////

    ////////////////////////////数据源 获取图表的JSON配置//////////////////////////////
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        reload();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public interface DataSource {
        GsonOption markChartOptions();
    }
    ////////////////////////////数据源 获取图表的JSON配置//////////////////////////////


    ////////////////////////////添加事件监听echart返回的 事件相关属性（是一个json），将该json返回给开发者使用
    ///////////////////////////echart返回的事件信息:http://echarts.baidu.com/api.html#events

    private OnAddEChartActionListener onAddEChartActionListener;

    public void setOnAddEChartActionListener(OnAddEChartActionListener onAddEchartActionListener) {
        this.onAddEChartActionListener = onAddEchartActionListener;
    }

    public OnAddEChartActionListener getOnAddEChartActionListener() {
        return onAddEChartActionListener;
    }

    public interface OnAddEChartActionListener {
        void actionHandlerResponseResult(String result);

        void onWebViewLoadFinished(WebView view, String url);
    }
}

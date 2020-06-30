package com.zjclugger.buyer.ui.goods;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zjclugger.buyer.R;
import com.zjclugger.lib.base.BaseFragment;

/**
 * 图文详情webview<br>
 * Created by King.Zi on 2020/4/26.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class GoodsDetailWebFragment extends BaseFragment {
    public WebView wv_detail;
    private WebSettings webSettings;

    @Override
    public int getLayout() {
        return R.layout.fragment_item_web;
    }

    @Override
    public void initViews() {

    }

    @Override
    public boolean doBackPress() {
        return false;
    }

    public void initWebView(View rootView) {
        String url = "http://music.taihe.com/";
        wv_detail = (WebView) rootView.findViewById(R.id.wv_detail);
        wv_detail.setFocusable(false);
        wv_detail.loadUrl(url);
        webSettings = wv_detail.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_detail.setWebViewClient(new GoodsDetailWebViewClient());
    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}

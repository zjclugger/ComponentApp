package com.zjclugger.seller.ui;

import android.content.Intent;

import com.zjclugger.lib.base.BaseLoadingActivity;

/**
 * 加载页<br>
 * Created by King.zi on 2020/4/15.
 */
public class LoadingActivity extends BaseLoadingActivity {

    @Override
    public void initApp() {
    }

    @Override
    public void configStartPage() {
        startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
    }
}
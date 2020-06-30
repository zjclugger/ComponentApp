package com.zjclugger.buyer.ui;

import android.content.Intent;

import com.zjclugger.lib.base.BaseLoadingActivity;

/**
 * 加载页<br>
 * Created by King.zi on 2016/8/8.
 */
public class LoadingActivity extends BaseLoadingActivity {

    @Override
    public void initApp() {
    }

    @Override
    public void configStartPage() {
        //BuyerMainActivity
        startActivity(new Intent(LoadingActivity.this, BuyerMainActivity.class));
    }
}
package com.zjclugger.lib.base;

import androidx.annotation.ColorRes;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public interface ISetStatusBarStyle {
    int NO_SET_STATUS_BAR_COLOR = -1;

    void setStatusBarStyle(@ColorRes int statusBarColor, boolean isLightMode);

    void setStatusBar();

}

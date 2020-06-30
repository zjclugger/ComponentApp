package com.zjclugger.lib.base;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public interface SearchViewTitleListener {
    /**
     * 是否为带搜索的标题
     *
     * @return
     */
    boolean isSearchViewTitle();

    /**
     * 搜索提示文本
     *
     * @return
     */
    String getSearchViewHitText();

    /**
     * 点击返回控件
     */
    void onBackViewClick();

    /**
     * 点击搜索控件
     */
    void onSearchViewClick();

    /**
     * 点击搜索视图的过滤控件
     */
    void onFilterViewClick();
}

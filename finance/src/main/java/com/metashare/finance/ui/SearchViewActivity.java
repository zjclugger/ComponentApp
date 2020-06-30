package com.zjclugger.finance.ui;

import android.os.Bundle;

import com.zjclugger.basiclib.Log;
import com.zjclugger.finance.R;
import com.zjclugger.finance.utils.FinanceConstants;
import com.zjclugger.lib.base.BaseActivity;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.utils.FragmentUtils;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class SearchViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        int searchType = getIntent().getIntExtra(FinanceConstants.Keywords.KEY_SEARCH_VIEW_TYPE, 0);
        if (searchType == 0) {
            finish();
        } else {
            int containerViewId = R.id.content_activity_fragment;
            if (FinanceConstants.SearchViewType.SEARCH_KEYWORD == searchType) {
                SearchKeywordFragment searchKeywordFragment = new SearchKeywordFragment();
                FragmentUtils.addFirstFragment(this, searchKeywordFragment, containerViewId);
            } else if (FinanceConstants.SearchViewType.SEARCH_FILTER == searchType) {
                SearchFilterFragment searchFilterFragment = new SearchFilterFragment();
                FragmentUtils.addFirstFragment(this, searchFilterFragment, containerViewId);
            } else {
                Log.d("search type " + searchType + " is unknown");
                finish();
            }
        }
    }
}
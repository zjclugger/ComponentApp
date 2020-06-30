package com.zjclugger.component.ui;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zjclugger.basiclib.Log;
import com.zjclugger.lib.R;
import com.zjclugger.lib.base.BaseActivity;
import com.zjclugger.lib.base.ISetStatusBarStyle;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.router.ARouterConstants;

/**
 * 空白的Activity，用于加载fragment
 *
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
@Route(path = ARouterConstants.Path.COM_FRAGMENT_ACTIVITY)
public class FragmentSupportActivity extends BaseActivity {
    @Autowired(name = ARouterConstants.KeyWord.KEY_FSA_CLASS_NAME)
    String mFragmentClassName;
    @Autowired(name = ARouterConstants.KeyWord.KEY_FSA_FRAGMENT_PARAMS)
    Bundle mParams;
    @Autowired(name = ARouterConstants.KeyWord.KEY_FSA_FRAGMENT_COLOR)
    int mColor = ISetStatusBarStyle.NO_SET_STATUS_BAR_COLOR;
    @Autowired(name = ARouterConstants.KeyWord.KEY_FSA_FRAGMENT_LIGHT)
    boolean mIsLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        String errorMessage = null;
        if (!TextUtils.isEmpty(mFragmentClassName)) {
            try {
                Fragment fragment = (Fragment) Class.forName(mFragmentClassName).newInstance();
                if (fragment != null) {
                    if (ISetStatusBarStyle.NO_SET_STATUS_BAR_COLOR != mColor) {
                        setStatusBarStyle(mColor, mIsLight);
                    }

                    setContentView(R.layout.activity_fragment);
                    fragment.setArguments(mParams);
                    FragmentUtils.addFirstFragment(this, fragment, R.id.content_activity_fragment);
                } else {
                    errorMessage = "fragment name is " + mFragmentClassName + ",but fragment is " +
                            "null";
                }
            } catch (IllegalAccessException e) {
                errorMessage = e.getMessage();
            } catch (InstantiationException e) {
                errorMessage = e.getMessage();
            } catch (ClassNotFoundException e) {
                errorMessage = e.getMessage();
            }
        } else {
            errorMessage = "need fragment class name";
        }

        if (!TextUtils.isEmpty(errorMessage)) {
            Log.e("Call Fragment Activity has error the message is " + errorMessage);
            WidgetUtils.toastErrorMessage(this, R.string.developing);
            finish();
        }
    }
}
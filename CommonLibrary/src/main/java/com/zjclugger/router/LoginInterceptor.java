package com.zjclugger.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 登录拦截器<br>
 * 跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查<br>
 * 拦截器会在跳转之间执行<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
@Interceptor(priority = 8, name = "登录拦截器")
public class LoginInterceptor implements IInterceptor {

    Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

        if (ARouterConstants.KeyWord.NEED_LOGIN != postcard.getExtra()) {
            callback.onContinue(postcard);
            return;
        }

        if (checkNotLogin()) {
            callback.onInterrupt(null);

            ARouter.getInstance()
                    .build(ARouterConstants.Path.USER_LOGIN)
                    .navigation(mContext);
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
        mContext = context;
    }


    /**
     * 判断是否登录
     *
     * @return
     */
    private boolean checkNotLogin() {

        boolean isNotLogin = false;

        //isNotLogin = 登录逻辑


        return isNotLogin;
    }
}
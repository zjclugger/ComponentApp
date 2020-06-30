package com.zjclugger.lib.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.business.uploader.FileConverterFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class BaseWebApi {
    public static final String TAG = "WebAPI";
    private Retrofit mRetrofit;
    private static BaseWebApi mWebApi;
    private boolean mWithoutToken;
    private long mConnectTimeout = 20;
    private long mWriteTimeout = 20;
    private long mReadTimeout = 20;
    private boolean mForceRebuild = false;
    private String mNullDefaultStringValue = "";

    public static BaseWebApi instance() {
        if (mWebApi == null) {
            mWebApi = new BaseWebApi();
        }
        return mWebApi;
    }

    public <T> T getApi(Class<T> service, @NonNull String baseUrl) {
        mWithoutToken = false;
        return create(service, baseUrl);
    }

    /**
     * @param service
     * @param baseUrl
     * @param withoutToken 无须认证及提供Token
     * @param <T>
     * @return
     */
    public <T> T getApi(Class<T> service, @NonNull String baseUrl, boolean withoutToken) {
        mWithoutToken = withoutToken;
        return create(service, baseUrl);
    }

    private <T> T create(Class<T> service, @NonNull String baseUrl) {
        if (mForceRebuild) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(buildHttpClient())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .addConverterFactory(new FileConverterFactory())
                    // .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(new NullValueAdapterFactory(mNullDefaultStringValue)).create()))
                    .build();
            mForceRebuild = false;
            mNullDefaultStringValue = "";
            return retrofit.create(service);
        } else {
            if (mRetrofit == null || !mRetrofit.baseUrl().equals(HttpUrl.parse(baseUrl))) {
                mRetrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(buildHttpClient())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                        .addConverterFactory(new FileConverterFactory())
                        // .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(new NullValueAdapterFactory()).create()))
                        .build();
            }
            return mRetrofit.create(service);
        }
    }

    /**
     * 设置null的默认字符串
     *
     * @param defaultStringValue
     * @return
     */
    public BaseWebApi setNullDefaultStringValue(String defaultStringValue) {
        mForceRebuild = true;
        mNullDefaultStringValue = defaultStringValue;
        return this;
    }

    /**
     * 强制重新创建
     *
     * @param forceRebuild
     * @return
     */
    public BaseWebApi setForceRebuild(boolean forceRebuild) {
        mForceRebuild = forceRebuild;
        return this;
    }

    public BaseWebApi setConnectTimeout(long timeout) {
        mConnectTimeout = timeout;
        return this;
    }

    public BaseWebApi setWriteTimeout(long timeout) {
        mWriteTimeout = timeout;
        return this;
    }

    public BaseWebApi setReadTimeout(long timeout) {
        mReadTimeout = timeout;
        return this;
    }

    protected OkHttpClient buildHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d(TAG, "OK-HTTP3:" + message);
                    }
                });
        //设置日志的显示类别
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addNetworkInterceptor(loggingInterceptor);
        builder.addInterceptor(new LoggingInterceptor());
        builder.connectTimeout(mConnectTimeout, TimeUnit.SECONDS)
                .readTimeout(mReadTimeout, TimeUnit.SECONDS)
                .writeTimeout(mWriteTimeout, TimeUnit.SECONDS);
        if (!mWithoutToken) {
            builder.addInterceptor(new Interceptor() {
                @NotNull
                @Override
                public Response intercept(@NotNull Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization",
                                    UserManager.getInstance().getAuthorizationCode())
                            .header("token", UserManager.getInstance().getToken());//令牌
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        return builder.build();
    }

    class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            //不能直接使用response.body().string()的方式输出日志，
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            Log.d(TAG, responseBody.string());
            return response;
        }
    }
}
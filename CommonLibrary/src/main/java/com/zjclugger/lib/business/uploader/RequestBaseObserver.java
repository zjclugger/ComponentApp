package com.zjclugger.lib.business.uploader;

import com.zjclugger.basiclib.Log;
import com.zjclugger.lib.business.uploader.entity.UploadFile;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 自定义Observer
 */
public abstract class RequestBaseObserver<V> implements Observer<V> {

    public static final String TAG = "Observer";
    private Disposable disposed;

    public RequestBaseObserver() {
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.disposed = d;
    }

    @Override
    public void onNext(V t) {
        Log.e(TAG, "onNext()->>>onSuccess");
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError()");
    }

    @Override
    public void onComplete() {
        Log.e(TAG, "onComplete()");
    }

    /**
     * 正在上传的文件进度
     *
     * @param uploadFile 正在上传的文件
     */
    protected void onProgress(UploadFile uploadFile) {
    }

    /**
     * 请求成功 回调
     *
     * @param t 请求返回的数据
     */
    protected abstract void onSuccess(V t);
}

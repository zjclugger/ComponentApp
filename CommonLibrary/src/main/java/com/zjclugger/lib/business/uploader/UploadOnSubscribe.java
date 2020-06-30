package com.zjclugger.lib.business.uploader;

import android.util.Log;

import com.zjclugger.lib.business.uploader.entity.UploadFile;

import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * 文件上传(进度)观察者
 */
public class UploadOnSubscribe implements ObservableOnSubscribe<UploadFile> {

    public static final String TAG = "Upload";
    private ObservableEmitter<UploadFile> mObservableEmitter;//进度观察者,发射器
    public long mTotalSize = 0L;//总长度
    public AtomicLong mUploadSize = new AtomicLong();//已上传大小
    private int mUploadPercent = 0;//已上传进度百分比
    private UploadFile mUplodFile;

    public UploadOnSubscribe() {
    }

    @Override
    public void subscribe(ObservableEmitter<UploadFile> observableEmitter) throws Exception {
        this.mObservableEmitter = observableEmitter;
    }

    public void onRead(UploadFile uploadFile, long read) {
        mUplodFile = uploadFile;
        mUploadSize.addAndGet(read);

        if (mTotalSize <= 0) {
            onProgress(0);
        } else {
            onProgress((int) (mUploadSize.get() * 100 / mTotalSize));
        }
    }

    private void onProgress(int percent) {
        if (null == mObservableEmitter) return;
        if (percent == mUploadPercent) return;

        mUploadPercent = percent;
        Log.d(TAG, "upload file the progress is " + mUploadPercent);
        mUplodFile.setProgress(mUploadPercent);
        if (percent >= 100) {
            mObservableEmitter.onNext(mUplodFile);
            mObservableEmitter.onComplete();
        } else {
            mObservableEmitter.onNext(mUplodFile);
        }
    }

    public void setTotalSize(long totalSize) {
        this.mTotalSize = totalSize;
    }

    //上传完成 清理进度数据
    public void clean() {
        this.mUploadPercent = 0;
        this.mUploadSize = new AtomicLong();
        this.mTotalSize = 0L;
    }
}

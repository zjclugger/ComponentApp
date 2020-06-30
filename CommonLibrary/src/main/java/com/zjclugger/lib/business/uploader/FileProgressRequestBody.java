package com.zjclugger.lib.business.uploader;

import android.util.Log;

import com.zjclugger.lib.business.uploader.entity.UploadFile;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * 封装RequestBody，计算文件上传进度
 */
public class FileProgressRequestBody extends RequestBody {
    public static final String TAG = "FPRequestBody";
    public static final int SEGMENT_SIZE = 2048;
    private File mFile;
    private String mContentType;
    private UploadOnSubscribe mSubscribe;
    //OkHttp里加入了默认的HttpLoggingInterceptor引起,会导致writeto重复写入数据，因此加个判断标识
    private int mCallTimes;
    private UploadFile mUploadFile;

    protected FileProgressRequestBody() {
    }

    public FileProgressRequestBody(File file, String contentType, UploadOnSubscribe subscribe) {
        this.mFile = file;
        this.mContentType = contentType;
        this.mSubscribe = subscribe;
    }

    @Override
    public long contentLength() {
        return mFile.length();
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(mContentType);
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        mCallTimes++;
        boolean isBuffer = sink instanceof Buffer;//如果传入的 sink 为 Buffer 类型，则直接写入，不进行百分比统计

        Log.d(TAG, "write to the call times is " + mCallTimes + ", and is buffer=" + isBuffer);
        Source source = null;
        try {
            mUploadFile = new UploadFile(mFile.getName(), mFile.getPath());
            source = Okio.source(mFile);

            long read;
            while ((read = source.read(sink.buffer(), SEGMENT_SIZE)) != -1) {
                sink.flush();

                if (!isBuffer && mCallTimes > 0) {
                    if (null != mSubscribe) mSubscribe.onRead(mUploadFile, read);
                }
            }
        } finally {
            Util.closeQuietly(source);
        }
    }
}

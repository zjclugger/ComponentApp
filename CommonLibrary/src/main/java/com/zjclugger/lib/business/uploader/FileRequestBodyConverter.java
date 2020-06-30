package com.zjclugger.lib.business.uploader;

import android.util.ArrayMap;

import com.zjclugger.lib.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * 上传文件转换器
 */
public class FileRequestBodyConverter implements Converter<ArrayMap<String, Object>, RequestBody> {
    private UploadOnSubscribe mUploadOnSubscribe;
    private String mContentType = "multipart/form-data";
    private Map<String, Object> mOtherParamsMap = new HashMap<>();

    public FileRequestBodyConverter() {
    }

    @Override
    public RequestBody convert(ArrayMap<String, Object> params) throws IOException {
        if (null == params) {
            return null;
        }
        mUploadOnSubscribe =
                (UploadOnSubscribe) params.get(Constants.Keywords.KEY_UPLOAD_SUBSCRIBE);
        if (params.containsKey(Constants.Keywords.KEY_UPLOAD_MEDIA_TYPE)) {
            mContentType = params.get(Constants.Keywords.KEY_UPLOAD_MEDIA_TYPE).toString();
        }

        if (params.containsKey(Constants.Keywords.KEY_UPLOAD_FILE_OTHERS)) {
            mOtherParamsMap =
                    (Map<String, Object>) params.get(Constants.Keywords.KEY_UPLOAD_FILE_OTHERS);
        }

        if (params.containsKey(Constants.Keywords.KEY_UPLOAD_FILE_PATH_LIST)) {
            return filesToMultipartBody((List<String>) params.get(Constants.Keywords.KEY_UPLOAD_FILE_PATH_LIST));
        }

        if (params.containsKey(Constants.Keywords.KEY_UPLOAD_FILE_LIST)) {
            return filesToMultipartBody((List<File>) params.get(Constants.Keywords.KEY_UPLOAD_FILE_LIST));
        }
        return null;
    }


    /**
     * 用于把 File集合 或者 File路径集合 转化成 MultipartBody
     *
     * @param files File列表或者 File 路径列表
     * @param <T>   泛型（File 或者 String）
     * @return MultipartBody（retrofit 多文件文件上传）
     */
    public synchronized <T> MultipartBody filesToMultipartBody(List<T> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        long size = 0L;
        File file;
        for (T t : files) {
            if (t instanceof File) file = (File) t;
            else if (t instanceof String)
                file = new File((String) t);//访问手机端的文件资源，保证手机端sdcdrd中必须有这个文件
            else break;

            size += file.length();
            // TODO 为了简单起见，没有判断file的类型
            FileProgressRequestBody requestBody = new FileProgressRequestBody(file, mContentType,
                    mUploadOnSubscribe);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }

        if (mOtherParamsMap != null) {
            for (Map.Entry<String, Object> entry : mOtherParamsMap.entrySet()) {
                builder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        mUploadOnSubscribe.setTotalSize(size);

        return builder.build();
    }
}
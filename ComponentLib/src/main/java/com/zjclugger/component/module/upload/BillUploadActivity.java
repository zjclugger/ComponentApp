package com.zjclugger.component.module.upload;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.component.R;
import com.zjclugger.component.module.upload.uploader.MediaUploadActivity;
import com.zjclugger.component.module.upload.uploader.entity.UploadOriginalResult;
import com.zjclugger.component.module.upload.uploader.entity.UploadOriginalResultWrapper;
import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.ApiResponseUtils;
import com.zjclugger.lib.api.entity.BaseWrapper;
import com.zjclugger.lib.business.uploader.ProgressObserver;
import com.zjclugger.lib.business.uploader.UploadOnSubscribe;
import com.zjclugger.lib.business.uploader.entity.UploadFile;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.CommonUtils;
import com.zjclugger.lib.utils.FileUtils;
import com.zjclugger.lib.utils.Monitor;
import com.zjclugger.lib.utils.NetworkUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.router.ARouterConstants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Route(path = ARouterConstants.Path.COM_UPLOAD_BILL)
public class BillUploadActivity extends MediaUploadActivity {
    public static final String TAG = "BillUpload";
    public static final long MAX_FILE_SIZE = 1024 * 1024 * 10; //10M
    @Autowired(name = ARouterConstants.KeyWord.KEY_COMPANY_ID)
    String companyId;
    @Autowired(name = ARouterConstants.KeyWord.KEY_MEDIA_TITLE_TEXT)
    String titleText;

    private FinanceViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FinanceViewModel.class);

        initUploadListView();
    }

    @Override
    protected boolean isDetailActivity() {
        return true;
    }

    @Override
    protected String getDetailActivityTitle() {
        return titleText;
    }

    @Override
    public View.OnClickListener setOnclickListener() {
        return v -> {
            if (!NetworkUtils.isNetworkConnected(mContext)) {
                WidgetUtils.toastMessage(mContext, R.string.error_network_unreachable);
            } else {
                if (!TextUtils.isEmpty(mReasonEditView.getText())) {
                    uploadOriginalBill();
                } else {
                    WidgetUtils.toast(mContext, "请输入报销事由", false);
                }
            }
        };
    }

    private void uploadOriginalBill() {
        showWaiting();
        if (getSelectedList() != null && getSelectedList().size() > 0) {
            setResult(Constants.KeyCode.RESULT_CODE_REFRESH);
            List<String> files = new ArrayList<>();
            for (int i = 0; i < getSelectedList().size(); i++) {
                if (FileUtils.getFileSize(getSelectedList().get(i).getPath()) > MAX_FILE_SIZE) {
                    WidgetUtils.toastMessage(mContext, "第 " + (i + 1) + " 张图片大小超过10M，不能上传！");
                    return;
                } else {
                    files.add(getSelectedList().get(i).getPath());
                }
            }

            Map<String, Object> otherParams = new HashMap<>();
            otherParams.put("reason", mReasonEditView.getText());

            UploadOnSubscribe uploadOnSubscribe = new UploadOnSubscribe();
            ArrayMap<String, Object> params = new ArrayMap<>();
            params.put(Constants.Keywords.KEY_UPLOAD_FILE_PATH_LIST, files);
            params.put(Constants.Keywords.KEY_UPLOAD_MEDIA_TYPE, "image/png");
            params.put(Constants.Keywords.KEY_UPLOAD_SUBSCRIBE, uploadOnSubscribe);
            params.put(Constants.Keywords.KEY_UPLOAD_FILE_OTHERS, otherParams);

            Observable.merge(Observable.create(uploadOnSubscribe),
                    FinanceRepository.getInstance().uploadOriginal(params))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ProgressObserver<Object>() {
                        @Override
                        protected void onProgress(UploadFile uploadFile) {
                            //根据filename更新图片状态
                            Log.d(TAG, "uploading the progress is " + uploadFile.getProgress());
                            if (uploadFile.getProgress() == 100) {
                                closeProgressDialog();
                                showProgressDialog(R.string.identification_wait,
                                        new Monitor.TimeoutListener() {
                                            @Override
                                            public void onTimeout() {
                                                closeProgressDialog();
                                                WidgetUtils.toastMessage(mContext, "操作超时");
                                            }
                                        }, 100);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "error is ---- " + e.getMessage());
                        }

                        @Override
                        protected void onSuccess(Object t) {
                            if (t != null) {
                                JSONObject jsonObject = CommonUtils.parseJson(t.toString());

                                if (jsonObject.optInt("code", -1) == 0) {
                                    UploadOriginalResultWrapper originalResultWrapper =
                                            CommonUtils.json2Object(t.toString(),
                                                    UploadOriginalResultWrapper.class);
                                    if (originalResultWrapper.getResult() != null && originalResultWrapper.getResult().size() > 0) {
                                        for (UploadOriginalResult result :
                                                originalResultWrapper.getResult()) {
                                            for (int i = 0; i < mUploadList.size(); i++) {
                                                if (result.getFileName().equalsIgnoreCase(mUploadList.get(i).getFileName())) {
                                                    mUploadList.get(i).setFileId(result.getId());
                                                    mUploadList.get(i).setStatus(result.getStatus());
                                                    break;
                                                }
                                            }
                                        }
                                        notifyUploadListChange();
                                    } else {
                                        Log.e(TAG, "upload complete but result list is null");
                                    }
                                } else {
                                    WidgetUtils.toastMessage(mContext, jsonObject.optString("msg"
                                            , getString(R.string.operation_failed)));
                                    Log.e(TAG, "upload failed the return message is " + t);
                                }
                            } else {
                                WidgetUtils.toastMessage(mContext, R.string.operation_failed);
                            }
                            closeProgressDialog();
                        }
                    });
        }
    }

    private void initUploadListView() {
        mUploadAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.upload_retry) {
                    retryIdentification(mUploadList.get(position).getFileId());
                } else if (view.getId() == R.id.upload_trash) {
                    deleteOriginal(mUploadList.get(position).getFileId());
                } else {
                    Log.e(TAG, "Events cannot be processed the view is " + view);
                }
            }
        });
    }

    private void deleteOriginal(String id) {
        if (TextUtils.isEmpty(id)) {
            WidgetUtils.toastMessage(mContext, "操作失败，ID为空！");
            return;
        } else {
            showProgressDialog(R.string.deleting);

            mViewModel.deleteOriginal(id).observe(this,
                    new Observer<ApiResponse<BaseWrapper<String>>>() {
                        @Override
                        public void onChanged(ApiResponse<BaseWrapper<String>> response) {
                            closeProgressDialog();
                            if (ApiResponseUtils.isSuccess(response,
                                    mContext.getResources().getString(R.string.delete_failed))) {
                                BaseWrapper<String> result = response.body;
                                if (result != null && result.isSuccess()) {
                                    if (mUploadList != null && mUploadList.size() > 0) {
                                        for (int i = 0; i < mUploadList.size(); i++) {
                                            if (id.equalsIgnoreCase(mUploadList.get(i).getFileId())) {
                                                mUploadList.remove(i);
                                                break;
                                            }
                                        }
                                        notifyUploadListChange();
                                    }
                                } else {
                                    Log.e(TAG,
                                            "delete is failed the message is " + result.getMessage());
                                }
                            }
                        }
                    });
        }
    }

    private void retryIdentification(String id) {
        if (TextUtils.isEmpty(id)) {
            WidgetUtils.toastMessage(mContext, "操作失败，ID为空！");
            return;
        } else {
            showWaiting();
            mViewModel.retryIdentification(id).observe(this,
                    new Observer<ApiResponse<BaseWrapper<String>>>() {
                        @Override
                        public void onChanged(ApiResponse<BaseWrapper<String>> response) {
                            closeProgressDialog();
                            if (ApiResponseUtils.isSuccess(response,
                                    mContext.getResources().getString(R.string.error_identification))) {
                                BaseWrapper<String> result = response.body;
                                if (result != null && result.isSuccess()) {
                                    if (mUploadList != null && mUploadList.size() > 0) {
                                        for (int i = 0; i < mUploadList.size(); i++) {
                                            if (id.equalsIgnoreCase(mUploadList.get(i).getFileId())) {
                                                mUploadList.get(i).setStatus("success");
                                                break;
                                            }
                                        }
                                    }
                                } else {
                                    Log.e(TAG,
                                            "retry identification is failed the message is " + result.getMessage());
                                }
                                notifyUploadListChange();
                            }
                        }
                    });
        }
    }
}
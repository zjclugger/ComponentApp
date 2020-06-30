package com.zjclugger.finance.ui.original;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.jview.DateTimePickerView;
import com.bigkoo.pickerview.jview.OptionsPickerView;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.utils.DateTimeFormat;
import com.zjclugger.basiclib.Log;
import com.zjclugger.finance.R;
import com.zjclugger.finance.databinding.FragmentOriginalDetailEditBinding;
import com.zjclugger.finance.ui.BaseBillDetailFragment;
import com.zjclugger.finance.ui.adapter.OriginalDetailsEditAdapter;
import com.zjclugger.finance.utils.FinanceConstants;
import com.zjclugger.finance.webapi.entity.response.BillTypeResult;
import com.zjclugger.finance.webapi.entity.response.OriginalDetailResult;
import com.zjclugger.finance.webapi.entity.response.OriginalServiceInfoResult;
import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.entity.BaseWrapper;
import com.zjclugger.lib.api.entity.BaseWrapperEntities;
import com.zjclugger.lib.business.UserPermission;
import com.zjclugger.lib.ui.widget.ErpAlertDialog;
import com.zjclugger.lib.utils;
import com.zjclugger.lib.utils.Monitor;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.router.utils.ARouterUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 编写原型凭证详情页<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OriginalDetailEditFragment extends BaseBillDetailFragment {
    public static final String TAG = "UpdateOriginal";
    private OriginalDetailResult mOriginalDetailResult;
    private OriginalDetailResult mOriginalDetailResultCache;
    private OriginalDetailsEditAdapter mDetailsAdapter;
    private FragmentOriginalDetailEditBinding mBinding;
    private ErpAlertDialog mAlertDialog;
    private int mPosition;
    private LinearLayoutManager mLayoutManager;
    private OptionsPickerView mBillTypePickerView;
    private List<BillTypeResult> mBillTypeList = new ArrayList<>();
    private String mBillTypeId = "";

    @Override
    public List<UserPermission> getPermissionList() {
        List<UserPermission> permissionList = new ArrayList<>();
        permissionList.add(new UserPermission("finance:increment:update", R.id.tv_right_text));
        return permissionList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_original_detail_edit,
                container, false);
        mView = mBinding.getRoot();
        if (mArguments != null) {
            mOriginalDetailResult =
                    mArguments.getParcelable(FinanceConstants.Keywords.KEY_ORIGINAL_BILL_DETAIL);
        }

        if (mOriginalDetailResult != null) {
            mOriginalDetailResultCache = ERPUtils.copyObject(mOriginalDetailResult);
            initView();
            bindData();
        } else {
            close();
        }

        return mView;
    }

    private void initView() {
        //title
        mBinding.navTitleLayout.findViewById(R.id.iv_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        mTitleView = mBinding.navTitleLayout.findViewById(R.id.tv_title_text);
        mTitleView.setText(getString(R.string.detail_original));
        mTitleRightView = mBinding.navTitleLayout.findViewById(R.id.tv_right_text);
        mTitleRightView.setText(R.string.complete);
        mTitleRightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mBillTypeId)) {
                    mOriginalDetailResult.setBillType(mBillTypeId);
                }

                String cacheValue = ERPUtils.object2Json(mOriginalDetailResultCache);
                String currentValue = ERPUtils.object2Json(mOriginalDetailResult);

                if (!cacheValue.equalsIgnoreCase(currentValue)) {
                    updateOriginal(currentValue);
                } else {
                    close();
                }
            }
        });

        //uploader and upload date
        mBinding.billUploader.setValueText(mArguments.getString(FinanceConstants.Keywords.KEY_ORIGINAL_UPLOADER));
        mBinding.billUploadDate.setValueText(ERPUtils.getDate(mArguments.getString(FinanceConstants.Keywords.KEY_ORIGINAL_UPLOAD_DATE)));

        //bill image
        mBinding.billImageLayout.setOnClickListener(ARouterUtils.getPreviewImageListener(mContext,
                mOriginalDetailResult.getUrl()));

        mBinding.fbvItemAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int insertPosition = getDataListSize();
                OriginalServiceInfoResult detailResult = new OriginalServiceInfoResult();
                mOriginalDetailResult.getDetailList().add(detailResult);
                if (insertPosition > 0) {
                    mDetailsAdapter.notifyItemInserted(insertPosition);
                } else {
                    //原来没有数据时，使用notifyItemInserted不能刷新UI
                    mBinding.originalDetailRecyclerView.setVisibility(View.VISIBLE);
                    mDetailsAdapter.notifyDataSetChanged();
                }
                //先定位到最后，再滑动
                mBinding.tvBottom.setFocusable(true);
                mBinding.tvBottom.setFocusableInTouchMode(true);
                mBinding.tvBottom.requestFocus();
                mBinding.originalDetailRecyclerView.smoothScrollToPosition(mDetailsAdapter.getItemCount());
            }
        });

        ViewUtils.setEditTextDigits(mBinding.letvTotalSmall.getEditTextView());
        ViewUtils.setEditTextDigits(mBinding.letvTotal.getEditTextView());

        mBinding.letvInputDate.setReadOnly();
        mBinding.letvInputDate.getEditTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerView mStartDatePickerDialog = new DateTimePickerView(getActivity(),
                        new OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {
                                mBinding.letvInputDate.getEditTextView().setText(ERPUtils.getDateTime(date,
                                        "yyyy-MM-dd"));
                            }
                        }, DateTimeFormat.YMD);
                mStartDatePickerDialog.displayClassicLayout(false);
                mStartDatePickerDialog.show();
            }
        });

        mAlertDialog = new ErpAlertDialog(mContext);

        mBillTypePickerView = new OptionsPickerView(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (!mBillTypeList.get(options1).getDictName().equalsIgnoreCase(mOriginalDetailResultCache.getBillType())) {
                    mBillTypeId = mBillTypeList.get(options1).getId();
                } else {
                    mBillTypeId = "";
                }

                mBinding.letvDocType.setValue(mBillTypeList.get(options1).getDictName());
            }
        });
      /*  mBillTypePickerView.getBuilder().setSubmitColor(R.color.text_selected);
        mBillTypePickerView.getBuilder().setTitleText("凭证类型");*/
        mBillTypePickerView.displayClassicLayout(getString(R.string.document_type), null, getString(R.string.text_confirm));

        getBillTypeList();
        mBinding.letvDocType.setReadOnly();
        mBinding.letvDocType.getEditTextView().setCompoundDrawablePadding(5);
        mBinding.letvDocType.getEditTextView().setCompoundDrawablesWithIntrinsicBounds(null, null,
                mContext.getResources().getDrawable(R.mipmap.ic_dropdown_normal), null);
        mBinding.letvDocType.getEditTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBillTypePickerView.show();
            }
        });
    }

    private int getDataListSize() {
        if (mOriginalDetailResult != null && mOriginalDetailResult.getDetailList() != null) {
            return mOriginalDetailResult.getDetailList().size();
        }

        return 0;
    }

    private void bindData() {
        mBinding.setOriginal(mOriginalDetailResult);
        mDetailsAdapter = new OriginalDetailsEditAdapter(mOriginalDetailResult.getDetailList(),
                R.layout.item_original_bill_detail_edit);
        mLayoutManager = new LinearLayoutManager(mContext);
        //mLayoutManager.setSmoothScrollbarEnabled(true);
        mBinding.originalDetailRecyclerView.setHasFixedSize(true);
        mBinding.originalDetailRecyclerView.setLayoutManager(mLayoutManager);
        mBinding.originalDetailRecyclerView.setAdapter(mDetailsAdapter);

        mDetailsAdapter.setOnItemClickListener(new Monitor.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View view, final int position) {
                //如果position为final，多次快速无序删除时会出错下标越界的异常
                mPosition = position;
                mAlertDialog.setMessage(ERPUtils.getString(mContext,R.string.delete_purchase_sale,
                        ERPUtils.getString(mOriginalDetailResult.getDetailList().get(mPosition).getServiceName())))
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //如果有header，还要+1
                                mOriginalDetailResult.getDetailList().remove(mPosition);
                                mDetailsAdapter.notifyItemRemoved(mPosition);    //刷新被删除的地方
                                mDetailsAdapter.notifyItemRangeChanged(mPosition,
                                        mOriginalDetailResult.getDetailList().size() - mPosition);
                                //刷新被删除数据，以及其后面的数据
                                if (mDetailsAdapter.getItemCount() == 0) {
                                    mDetailsAdapter.notifyDataSetChanged();
                                    mBinding.originalDetailRecyclerView.setVisibility(View.GONE);
                                }
                                mAlertDialog.dismiss();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAlertDialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void updateOriginal(String originalJson) {
        showWaiting();
        mViewModel.updateOriginalBill(originalJson).observe(this,
                new Observer<ApiResponse<BaseWrapper<String>>>() {
                    @Override
                    public void onChanged(ApiResponse<BaseWrapper<String>> response) {
                        boolean success = false;
                        closeProgressDialog();
                        if (response != null && response.isSuccess()) {
                            BaseWrapper<String> result = response.body;
                            if (result != null) {
                                success = true;
                                WidgetUtils.toastMessage(mContext, result.getResult());
                            }
                            Log.D(TAG, "upload original is " + response.isSuccess() + " the " +
                                    "message is " + response.body);
                        }

                        if (!success) {
                            WidgetUtils.toastMessage(mContext, R.string.upload_failed);
                            Log.E(TAG, "upload original is failed the result is " + response);
                        }
                        close();
                    }
                });
    }

    private void getBillTypeList() {
        showWaiting();
        mViewModel.getBillTypeAll().observe(this,
                new Observer<ApiResponse<BaseWrapperEntities<BillTypeResult>>>() {
                    @Override
                    public void onChanged(ApiResponse<BaseWrapperEntities<BillTypeResult>> response) {
                        boolean success = false;
                        closeProgressDialog();
                        if (response != null && response.isSuccess()) {
                            BaseWrapperEntities<BillTypeResult> result = response.body;
                            if (result != null) {
                                success = true;
                                if (mBillTypeList != null) {
                                    mBillTypeList.clear();
                                }
                                mBillTypeList.addAll(result.getResult());
                                mBillTypePickerView.bindData(mBillTypeList);
                            }
                        }

                        if (!success) {
                            WidgetUtils.toastMessage(mContext, R.string.error_get_document_type);
                            Log.E(TAG, "upload original is failed the result is " + response);
                        }
                    }
                });
    }
}
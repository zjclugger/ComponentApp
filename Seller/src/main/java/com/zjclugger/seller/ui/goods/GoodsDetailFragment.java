package com.zjclugger.seller.ui.goods;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.zjclugger.lib.business.UserPermission;
import com.zjclugger.seller.R;
import com.zjclugger.seller.databinding.FragmentGoodsDetailEditBinding;
import com.zjclugger.seller.utils.SellerConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 原型凭证详情页<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class GoodsDetailFragment extends BaseGoodsDetailFragment {
    private String mOriginalId;
  /*  private OriginalDetailResult mOriginalDetailResult;
    private OriginalDetailsAdapter mDetailsAdapter;*/
    private FragmentGoodsDetailEditBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_goods_detail, container
                , false);
        mView = mBinding.getRoot();
        if (mArguments != null) {
            mOriginalId = mArguments.getString(SellerConstants.Keywords.KEY_ORIGINAL_BILL_ID);
           // mOriginalDetailResult = new OriginalDetailResult();

            getDataFromServer();
            initView();
        } else {
            close();
        }

        return mView;
    }

    @Override
    public List<UserPermission> getPermissionList() {
        List<UserPermission> permissionList = new ArrayList<>();
        permissionList.add(new UserPermission("finance:increment:update", R.id.tv_right_text,
                R.id.layout_button));
        return permissionList;
    }

    private void initView() {
     /*   initButtonLayout(mBinding.layoutButton);
        mLeftButton.setText(R.string.check_pass);
        mCenterButton.setText(R.string.check_post);
        mRightButton.setText(R.string.check_not_pass);

        //adapter and recycler view
        mDetailsAdapter = new OriginalDetailsAdapter(mOriginalDetailResult.getDetailList(),
                R.layout.item_original_bill_detail);
        mBinding.originalDetailRecyclerView.setHasFixedSize(true);
        mBinding.originalDetailRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.originalDetailRecyclerView.setAdapter(mDetailsAdapter);

        //init title view
        mBinding.titleBar.findViewById(R.id.iv_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

        mTitleView = mBinding.titleBar.findViewById(R.id.tv_title_text);
        mTitleView.setText(R.string.detail_original);
        mTitleRightView = mBinding.titleBar.findViewById(R.id.tv_right_text);
        mTitleRightView.setText(R.string.text_edit);
        mTitleRightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OriginalDetailEditFragment fragment = new OriginalDetailEditFragment();
                mArguments.putParcelable(FinanceConstants.Keywords.KEY_ORIGINAL_BILL_DETAIL,
                        mOriginalDetailResult);
                fragment.setArguments(mArguments);

                FragmentUtils.replaceFragment(getActivity(), fragment,
                        R.id.content_activity_fragment);
            }
        });*/
    }

    private void setData() {
     /*   setButtonListener();
        mBinding.setOriginal(mOriginalDetailResult);
        mDetailsAdapter.setData(mOriginalDetailResult.getDetailList());
        View.OnClickListener previewListener = ARouterUtils.getPreviewImageListener(mContext,
                mOriginalDetailResult.getUrl());
        mBinding.tvPreviewOriginal.setOnClickListener(previewListener);
        mBinding.ivPreviewOriginal.setOnClickListener(previewListener);*/
    }

    private void setButtonListener() {
      /*  switch (mOriginalDetailResult.getProofStatus()) {
            case FinanceConstants.ProofStatus.TO_PERFECT:
                ViewUtils.setVisibility(View.VISIBLE, mCenterButton);
                mCenterButton.setOnClickListener(centerView -> submitToCheck());
                break;
            case FinanceConstants.ProofStatus.TO_CHECK:
                ViewUtils.setVisibility(View.VISIBLE, mLeftButton, mRightButton);
                mLeftButton.setOnClickListener(leftView -> showDialog(
                        getString(R.string.msg_check_pass),
                        dialogView -> {
                            mAlertDialog.dismiss();
                            checkResult(true);
                        }));
                //点击【继续】则将该原始凭证信息状态修改为待完善，点击【取消】关闭弹窗；
                mRightButton.setOnClickListener(rightView -> showDialog(getString(R.string.ask_to_continue),
                        dialogView -> {
                            mAlertDialog.dismiss();
                            checkResult(false);
                        }));
                break;
            case FinanceConstants.ProofStatus.TO_ACCOUNT:
                ViewUtils.setVisibility(View.VISIBLE, mCenterButton);
                mCenterButton.setText(getString(R.string.create_account));
                mCenterButton.setOnClickListener(centerView -> showDialog(
                        getString(R.string.ask_create_account),
                        dialogView -> {
                            mAlertDialog.dismiss();
                            createAccountBill();
                        }));
                break;
            default:
                break;
        }*/
    }

    /**
     * 提交校对
     */
    private void submitToCheck() {
       /* showProgressDialog(getString(R.string.posting));
        mViewModel.submitToCheck(mOriginalId).observe(this, response -> {
            closeProgressDialog();
            if (ApiResponseUtils.isSuccess(response, getString(R.string.error_check_failed))) {
                WrapperEntity result = response.body;
                if (result.isSuccess()) {
                    ViewUtils.setReadOnly(mCenterButton);
                    WidgetUtils.toastOKMessage(getActivity(), R.string.check_post_success);
                    //close();
                } else {
                    WidgetUtils.toastErrorMessage(getActivity(), R.string.check_post_failed);
                }
            }
        });*/
    }

    /**
     * 校对结果
     *
     * @param isPass
     */
    private void checkResult(boolean isPass) {
       /* showWaiting();
        mViewModel.checkOriginal(mOriginalId, isPass).observe(this,
                new Observer<ApiResponse<BaseWrapper<String>>>() {
                    @Override
                    public void onChanged(ApiResponse<BaseWrapper<String>> response) {
                        closeProgressDialog();
                        if (ApiResponseUtils.isSuccess(response,
                                getString(R.string.error_operation_failed))) {
                            WrapperEntity result = response.body;
                            if (result.isSuccess()) {
                                ViewUtils.setReadOnly(mLeftButton, mRightButton);
                                WidgetUtils.toastOKMessage(getActivity(),
                                        R.string.operation_success);
                                //close();
                            } else {
                                WidgetUtils.toastErrorMessage(getActivity(),
                                        R.string.operation_failed);
                            }
                        }
                    }
                });*/
    }

    /**
     * 生成记账凭证
     */
    private void createAccountBill() {
       /* showWaiting();
        mViewModel.createAccountBill(mOriginalId).observe(this, response -> {
            closeProgressDialog();
            if (ApiResponseUtils.isSuccess(response,
                    getString(R.string.error_create_account_failed))) {
                WrapperEntity result = response.body;
                if (result.isSuccess()) {
                    ViewUtils.setReadOnly(mCenterButton);
                    WidgetUtils.toastOKMessage(getActivity(), R.string.operation_success);
                    //close();
                } else {
                    WidgetUtils.toastErrorMessage(getActivity(), R.string.operation_failed);
                }
            }
        });*/
    }

    private void getDataFromServer() {
       /* showWaiting();
        mViewModel.getOriginalDetail(mOriginalId).observe(this, response -> {
            closeProgressDialog();
            if (mOriginalDetailResult != null) {
                mOriginalDetailResult = null;
            }
            boolean success = false;
            if (ApiResponseUtils.isSuccess(response, getString(R.string.error_get_data))) {
                BaseWrapperEntity<OriginalDetailResult> wrapperEntity = response.body;
                if (wrapperEntity != null && wrapperEntity.getResult() != null) {
                    mOriginalDetailResult = wrapperEntity.getResult();
                    success = true;
                    setData();
                }
            }

            if (!success) {

            }
        });*/
    }
}
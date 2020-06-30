package com.zjclugger.finance.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zjclugger.finance.R;
import com.zjclugger.finance.databinding.FragmentAccountDetailBinding;
import com.zjclugger.finance.ui.BaseBillDetailFragment;
import com.zjclugger.finance.ui.adapter.AccountDetailsAdapter;
import com.zjclugger.finance.utils.FinanceConstants;
import com.zjclugger.finance.webapi.entity.response.AccountBillDetailResult;
import com.zjclugger.lib.api.ApiResponseUtils;
import com.zjclugger.lib.api.entity.BaseWrapperEntity;
import com.zjclugger.lib.api.entity.WrapperEntity;
import com.zjclugger.lib.business.UserPermission;
import com.zjclugger.lib.ui.widget.ErpAlertDialog;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.router.utils.ARouterUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 原型凭证详情页<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AccountDetailFragment extends BaseBillDetailFragment {
    private String mAccountBillId;
    private AccountBillDetailResult mDetailResult;
    private AccountDetailsAdapter mDetailsAdapter;
    private FragmentAccountDetailBinding mBinding;
    private ErpAlertDialog mReasonDialog;

    @Override
    public List<UserPermission> getPermissionList() {
        List<UserPermission> permissionList = new ArrayList<>();
        /*
        finance:voucher:subject",
        finance:voucher:view",
        finance:voucher:update",
        finance:voucher:*",
        finance:voucher:posting",
        finance:voucher:carryforwardofyear",
        finance:voucher:auditnotpass",
        finance:voucher:create",
        finance:voucher:carryforwardofmonth",   //月末结转
        finance:voucher:auditpass",
        finance:voucher:delete",
         */
        //提交审核
        permissionList.add(new UserPermission("finance:voucher:subject", R.id.layout_left_button));
        //审核不通过
        permissionList.add(new UserPermission("finance:voucher:auditnotpass", R.id.layout_right_button));
        //审核通过
        permissionList.add(new UserPermission("finance:voucher:auditpass", R.id.layout_left_button));
        //过账
        return permissionList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_detail, container
                , false);
        mView = mBinding.getRoot();
        if (mArguments != null) {
            mAccountBillId = mArguments.getString(FinanceConstants.Keywords.KEY_ORIGINAL_BILL_ID);
            mDetailResult = new AccountBillDetailResult();

            getDataFromServer();
            initView();
        } else {
            close();
        }

        return mView;
    }

    private void initView() {
        initButtonLayout(mBinding.layoutButton);
        mLeftButton.setText(R.string.audit_pass);
        mCenterButton.setText(R.string.audit_post);
        mRightButton.setText(R.string.repulse);
        mDetailsAdapter = new AccountDetailsAdapter(mDetailResult.getDetailList(),
                R.layout.item_account_bill_detail);
        mBinding.detailAccountRecyclerview.setHasFixedSize(true);
        mBinding.detailAccountRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.detailAccountRecyclerview.addItemDecoration(ViewUtils.getListDivider(mContext,
                true));
        mBinding.detailAccountRecyclerview.setAdapter(mDetailsAdapter);

        //init title view
        mBinding.titleBar.findViewById(R.id.iv_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

        mTitleView = mBinding.titleBar.findViewById(R.id.tv_title_text);
        mTitleView.setText(R.string.detail_account);
        mTitleRightView = mBinding.titleBar.findViewById(R.id.tv_right_text);

        mReasonDialog = new ErpAlertDialog(mContext);
        mReasonDialog.setTitle(getString(R.string.reason_repulse));
        mReasonDialog.setOnClickListener(140, getString(R.string.reason_repulse), v -> {
            reject(String.valueOf(mReasonDialog.getContentView().getText()));
            mReasonDialog.dismiss();
        }, null);
    }

    private void setData() {
        setButtonListener();
        mBinding.setAccount(mDetailResult);
        mDetailsAdapter.setData(mDetailResult.getDetailList());
        View.OnClickListener previewListener = ARouterUtils.getPreviewImageListener(mContext,
                mDetailResult.getUrls());
        mBinding.tvPreviewAccountBill.setOnClickListener(previewListener);
    }

    private void getDataFromServer() {
        showWaiting();
        mViewModel.getAccountDetail(mAccountBillId).observe(this,
                response -> {
                    closeProgressDialog();
                    boolean success = false;
                    if (mDetailResult != null) {
                        mDetailResult = null;
                    }
                    if (response != null && response.isSuccess()) {
                        BaseWrapperEntity<AccountBillDetailResult> wrapperEntity =
                                response.body;
                        if (wrapperEntity != null && wrapperEntity.getResult() != null) {
                            mDetailResult = wrapperEntity.getResult();
                            success = true;
                            setData();
                        }
                    }

                    if (!success) {

                    }
                });
    }

    private void setButtonListener() {
        switch (mDetailResult.getVoucherStatus()) {
            case FinanceConstants.VoucherStatus.TO_ORDER:
                ViewUtils.setVisibility(View.VISIBLE, mBinding.layoutBottom, mCenterButton);
                mCenterButton.setOnClickListener(centerView -> submitApprove());
                break;
            case FinanceConstants.VoucherStatus.TO_APPROVE:
                ViewUtils.setVisibility(View.VISIBLE, mBinding.layoutBottom, mLeftButton,
                        mRightButton);
                mLeftButton.setOnClickListener(leftView -> showDialog(
                        getString(R.string.audit_pass_confirm),
                        dialogView -> {
                            mAlertDialog.dismiss();
                            passApprove();
                        }));
                mRightButton.setOnClickListener(rightView -> mReasonDialog.show());
                break;
            case FinanceConstants.VoucherStatus.TO_ACCOUNT:
                ViewUtils.setVisibility(View.VISIBLE, mBinding.layoutBottom, mLeftButton,
                        mRightButton);
                mLeftButton.setText(getString(R.string.account_posting_confirm));
                mLeftButton.setOnClickListener(leftView -> showDialog(
                        getString(R.string.account_posting_message),
                        dialogView -> {
                            mAlertDialog.dismiss();
                            passAccount();
                        }));
                mRightButton.setOnClickListener(rightView -> mReasonDialog.show());
                break;
            case FinanceConstants.VoucherStatus.ACCOUNTED:
                //ViewUtils.setVisibility(View.GONE, mBinding.lineBottom, mBinding.layoutButton);
                break;
            default:
                break;
        }
    }

    /**
     * 提交审核
     */
    private void submitApprove() {
        showProgressDialog(R.string.posting);
        mViewModel.submitApprove(mAccountBillId).observe(this, response -> {
            closeProgressDialog();
            if (ApiResponseUtils.isSuccess(response, getString(R.string.error_audit_posting))) {
                WrapperEntity result = response.body;
                if (result.isSuccess()) {
                    ViewUtils.setReadOnly(mCenterButton);
                    WidgetUtils.toastOKMessage(getActivity(), R.string.audit_post_success);
                    //close();
                } else {
                    WidgetUtils.toastErrorMessage(getActivity(), R.string.audit_post_failed);
                }
            }
        });
    }

    /**
     * 审核通过
     */
    private void passApprove() {
        showWaiting();
        mViewModel.passApprove(mAccountBillId).observe(this,
                response -> {
                    closeProgressDialog();
                    if (ApiResponseUtils.isSuccess(response,
                            getString(R.string.error_operation_failed))) {
                        WrapperEntity result = response.body;
                        if (result.isSuccess()) {
                            ViewUtils.setReadOnly(mLeftButton, mRightButton);
                            WidgetUtils.toastOKMessage(getActivity(), R.string.operation_success);
                            //close();
                        } else {
                            WidgetUtils.toastErrorMessage(getActivity(), R.string.operation_failed);
                        }
                    }
                });
    }

    private void reject(String reason) {
        showWaiting();
        mViewModel.rejectedAccount(mAccountBillId, reason).observe(this,
                response -> {
                    closeProgressDialog();
                    if (ApiResponseUtils.isSuccess(response,
                            getString(R.string.error_operation_failed))) {
                        WrapperEntity result = response.body;
                        if (result.isSuccess()) {
                            ViewUtils.setReadOnly(mLeftButton, mRightButton);
                            WidgetUtils.toastOKMessage(getActivity(), R.string.operation_success);
                            //close();
                        } else {
                            WidgetUtils.toastErrorMessage(getActivity(), R.string.operation_failed);
                        }
                    }
                });
    }

    /**
     * 记账凭证入账
     */
    private void passAccount() {
        showWaiting();
        mViewModel.passAccount(mAccountBillId).observe(this, response -> {
            closeProgressDialog();
            if (ApiResponseUtils.isSuccess(response,
                    getString(R.string.error_account_post_failed))) {
                WrapperEntity result = response.body;
                if (result.isSuccess()) {
                    ViewUtils.setReadOnly(mLeftButton, mRightButton);
                    WidgetUtils.toastOKMessage(getActivity(), R.string.operation_success);
                    //close();
                } else {
                    WidgetUtils.toastErrorMessage(getActivity(), R.string.operation_failed);
                }
            }
        });
    }
}
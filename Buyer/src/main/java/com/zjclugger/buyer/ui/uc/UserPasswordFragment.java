package com.zjclugger.buyer.ui.uc;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.zjclugger.basiclib.Log;
import com.zjclugger.buyer.R;
import com.zjclugger.buyer.ui.vm.BuyerViewModel;
import com.zjclugger.lib.api.ApiResponse;
import com.zjclugger.lib.api.ApiResponseUtils;
import com.zjclugger.lib.api.entity.BaseWrapper;
import com.zjclugger.lib.api.entity.WrapperEntity;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.utils.WidgetUtils;

import butterknife.BindView;

/**
 * 密码修改<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class UserPasswordFragment extends BaseFragment {
    private final static String TAG = "ModifyPassword";

    @BindView(R.id.til_user_password)
    TextInputLayout mPasswordLayout;
    @BindView(R.id.et_old_password)
    TextInputEditText mPasswordView;
    @BindView(R.id.et_password1)
    TextInputEditText mPasswordNew1View;
    @BindView(R.id.et_password2)
    TextInputEditText mPasswordNew2View;
    @BindView(R.id.til_new_password2)
    TextInputLayout mPasswordNewLayout;
    @BindView(R.id.user_password_modify)
    Button mPasswordChangeButton;
    BuyerViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(BuyerViewModel.class);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_user_password;
    }

    @Override
    public void initViews() {
        initDetailTitleViews("密码修改");
        mPasswordChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change password
                if (verifyEmpty(mPasswordView, mPasswordLayout, "当前密码不能为空")) {
                    if (verifyEquals(mPasswordView.getText().toString(),
                            UserManager.getInstance().getCurrentUser().getUserPassword(),
                            mPasswordLayout
                            , "当前密码错误")) {
                        if (verifyEmpty(mPasswordNew1View, mPasswordNewLayout, "新密码不能为空") && verifyEmpty(mPasswordNew2View, mPasswordNewLayout, "再次输入新密码不能为空")) {
                            if (verifyEquals(mPasswordNew1View.getText().toString(),
                                    mPasswordNew2View.getText().toString(), mPasswordNewLayout,
                                    "两次密码不一致")) {
                                //TODO:提交修改
                                changePassword(mPasswordNew1View.getText().toString());
                            }
                        }
                    }
                }
            }
        });
    }

    private void changePassword(String password) {
        Log.d("password=" + password);
        showWaiting();
        mViewModel.changePassword(password).observe(this,
                new Observer<ApiResponse<BaseWrapper<Boolean>>>() {
                    @Override
                    public void onChanged(ApiResponse<BaseWrapper<Boolean>> response) {
                        closeProgressDialog();
                        if (ApiResponseUtils.isSuccess(response, "密码修改失败")) {
                            WrapperEntity result = response.body;
                            if (result.isSuccess()) {
                                UserManager.getInstance().getCurrentUser().setUserPassword(password);
                                WidgetUtils.toastOKMessage(getActivity(), "密码修改成功！");
                                close();
                            } else {
                                WidgetUtils.toastErrorMessage(getActivity(), "密码修改失败！");
                            }
                        }
                    }
                });
    }

    private boolean verifyEmpty(TextInputEditText inputEditText, TextInputLayout errorLayout,
                                String errorMessage) {
        if (TextUtils.isEmpty(inputEditText.getText())) {
            errorLayout.setErrorEnabled(true);
            errorLayout.setError(errorMessage);
            inputEditText.requestFocus();
            return false;
        } else {
            errorLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean verifyEquals(String value1, String value2, TextInputLayout errorLayout,
                                 String errorMessage) {
        if (!value1.equals(value2)) {
            errorLayout.setErrorEnabled(true);
            errorLayout.setError(errorMessage);
            return false;
        } else {
            errorLayout.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public boolean doBackPress() {
        return false;
    }

    @Override
    public <T> T getPostBackData() {
        return null;
    }
}
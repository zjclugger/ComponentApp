package com.zjclugger.lib.ui.uc;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.zjclugger.lib.R;
import com.zjclugger.lib.base.BaseFragment;

/**
 * 用户注册<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class UserRegisterFragment extends BaseFragment {
    /*@BindView(R.id.til_password)
    TextInputLayout mRegisterMessageLayout;
    @BindView(R.id.et_register_email)
    TextInputEditText mUserNameView;
    @BindView(R.id.et_register_password)
    TextInputEditText mPasswordView;
    @BindView(R.id.et_register_password2)
    TextInputEditText mPasswordView2;
    @BindView(R.id.chk_user_agreement)
    CheckBox mAgreementButton;
    @BindView(R.id.tv_go_login)
    TextView mToLoginView;
    @BindView(R.id.register_button)
    Button mRegisterButton;*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_register_layout;
    }

    @Override
    public void initViews() {
      /*  mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyEmail()) {
                    if (verifyPassword(mPasswordView, mPasswordView2)) {
                        if (verifyEquals(mPasswordView.getText().toString(),
                                mPasswordView2.getText().toString())) {
                            if (mAgreementButton.isChecked()) {
                                // changePassword(mPasswordNew1View.getText().toString());
                                checkEmail();
                            } else {
                                WidgetUtils.toastMessage(getActivity(), "未同意用户协议");
                            }
                        }
                    }
                }
            }
        });

        mToLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });*/
    }

    private void checkEmail() {
        showWaiting();
      /*  mViewModel.checkEmail(String.valueOf(mUserNameView.getText())).observe(this,
                new Observer<ApiResponse<BaseWrapper<String>>>() {
                    @Override
                    public void onChanged(ApiResponse<BaseWrapper<String>> response) {
                        if (ApiResponseUtils.isSuccess(response, "邮箱检测失败，请重试！")) {
                            WrapperEntity result = response.body;
                            if (result.isSuccess()) {
                                //todo：注册
                                register();
                            } else {
                                closeProgressDialog();
                                WidgetUtils.toastErrorMessage(mContext, result.getMessage());
                            }
                        } else {
                            closeProgressDialog();
                        }
                    }
                });*/
    }

    private void register() {
     /*   mViewModel.register(String.valueOf(mUserNameView.getText()),
                String.valueOf(mPasswordView.getText())).observe(this,
                new Observer<ApiResponse<BaseWrapper<Boolean>>>() {
                    @Override
                    public void onChanged(ApiResponse<BaseWrapper<Boolean>> response) {
                        closeProgressDialog();
                        if (ApiResponseUtils.isSuccess(response, "注册失败")) {
                            WrapperEntity result = response.body;
                            if (result.isSuccess()) {
                                WidgetUtils.toastMessage(mContext, "注册成功");
                                getFragmentManager().popBackStack();
                            } else {
                                WidgetUtils.toastErrorMessage(mContext, result.getMessage());
                            }
                        }
                    }
                });*/
    }

    private boolean verifyEmail() {
     /*   String errorMessage = "";
        if (TextUtils.isEmpty(mUserNameView.getText())) {
            errorMessage = "邮箱不能为空";
        } else if (!CommonUtils.isEmail(String.valueOf(mUserNameView.getText()))) {
            errorMessage = "邮箱格式不正确!";
        }

        return verify(mUserNameView, errorMessage);*/
     return false;
    }

    private boolean verifyPassword(TextInputEditText... inputEditTextViews) {
        String errorMessage = "";
        for (TextInputEditText inputEditText : inputEditTextViews) {
            if (inputEditText != null) {
                if (TextUtils.isEmpty(inputEditText.getText())) {
                    errorMessage = "新密码不能为空";
                } else if (String.valueOf(inputEditText.getText()).length() <= 6) {
                    errorMessage = getString(R.string.tip_register_password);
                }

                if (!verify(inputEditText, errorMessage)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean verify(TextInputEditText inputEditText, String errorMessage) {
       /* if (!TextUtils.isEmpty(errorMessage)) {
            mRegisterMessageLayout.setErrorEnabled(true);
            mRegisterMessageLayout.setError(errorMessage);
            inputEditText.requestFocus();
            return false;
        } else {
            mRegisterMessageLayout.setErrorEnabled(false);
            return true;
        }*/
       return false;
    }

    private boolean verifyEquals(String value1, String value2) {
       /* if (!value1.equalsIgnoreCase(value2)) {
            mRegisterMessageLayout.setErrorEnabled(true);
            mRegisterMessageLayout.setError("再次密码不一致");
            return false;
        } else {
            mRegisterMessageLayout.setErrorEnabled(false);
            return true;
        }*/
       return false;
    }

    @Override
    public void closeFloatView() {

    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}
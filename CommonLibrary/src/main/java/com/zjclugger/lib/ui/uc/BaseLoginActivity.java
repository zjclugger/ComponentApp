package com.zjclugger.lib.ui.uc;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.zjclugger.lib.R;
import com.zjclugger.lib.ui.widget.JAlertDialog;
import com.zjclugger.lib.utils.WidgetUtils;

import java.util.List;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public abstract class BaseLoginActivity extends AppCompatActivity {
    TextInputLayout mPasswordLayout;

    EditText mUserNameText;
    EditText mPasswordText;
    LinearLayout mLoginLayout;
    LinearLayout mUserPasswordLayout;
    LinearLayout mPhoneLayout;
    LinearLayout mRecoverPasswordLayout;

    TextView mForgetPasswordView;
    TextView mNoValidationCodeView;
    TextView mLoginModeView;
    TextView mUserRegisterView;
    TextView mValidationCodeView;
    Button mLoginButton;

    String mUserName;
    String mPassword;
    private boolean mIsRegister;    //是否为注册

    public abstract @DrawableRes
    int getImageId();

    public abstract void login(String userName, String password);

    public abstract List<String> getCacheUserInfo();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_login);
        initView();
    }

    private void initView() {
        mLoginButton = findViewById(R.id.login_button);
        ImageView imageView = findViewById(R.id.iv_eshop_view);
        imageView.setImageResource(getImageId());
        mPasswordLayout = findViewById(R.id.til_password);
        mUserNameText = findViewById(R.id.et_username);
        mPasswordText = findViewById(R.id.et_password);
        mLoginLayout = findViewById(R.id.center_layout);
        mUserPasswordLayout = findViewById(R.id.user_password_layout);
        mPhoneLayout = findViewById(R.id.phone_layout);
        mRecoverPasswordLayout = findViewById(R.id.recover_password_layout);
        mForgetPasswordView = findViewById(R.id.tv_forget_password);
        mNoValidationCodeView = findViewById(R.id.tv_not_validation_code);
        mValidationCodeView = findViewById(R.id.verification_code_button);
        mLoginModeView = findViewById(R.id.tv_login_mode);
        mUserRegisterView = findViewById(R.id.tv_register);

        mUserNameText.setText(getCacheUserInfo().get(0));
        mPasswordText.setText(getCacheUserInfo().get(1));

/*
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_password_blue);
        drawable .setBounds(15, 15, 40, 40);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mPasswordText .setCompoundDrawables(drawable , null, null, null);//只放左边*/

        /*mUserNameText.setText(PreferencesUtil.getInstance().getLoginUser());
        mPasswordText.setText(PreferencesUtil.getInstance().getLoginUserPassword());*/
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsRegister) {
                    //login
                    login();
                } else {
                    //TODO:注册
                    WidgetUtils.toast(BaseLoginActivity.this, "新用户注册成功", false);
                }
            }
        });
        mUserRegisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPasswordLayout.setErrorEnabled(false);
                mUserRegisterView.setVisibility(View.GONE);
                mIsRegister = true;
                mPhoneLayout.setVisibility(View.VISIBLE);
                mUserPasswordLayout.setVisibility(View.GONE);
                mForgetPasswordView.setVisibility(View.GONE);
                mNoValidationCodeView.setVisibility(View.VISIBLE);
                mLoginModeView.setText("账号密码登录");
                mLoginButton.setText("新用户注册");

                /*  ARouterUtils.openDetailFragment(LoginActivity.this,
                    UserRegisterFragment.class.getName(), null, R.color.bg_white, false);*/
            }
        });

        mForgetPasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginLayout.setVisibility(View.GONE);
                mRecoverPasswordLayout.setVisibility(View.VISIBLE);
            }
        });

        mValidationCodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValidationCode();
            }
        });

        mNoValidationCodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //no validation code
                JAlertDialog dialog = new JAlertDialog(BaseLoginActivity.this);
                dialog.setTitle("收不到验证码？")
                        .setMessage("1.请确认手机号是否为当前使用的手机号\n2.请检查短信是否被安全软件拦截\n3.由于运营商关系，短信可能存在延迟到达")
                        .setSingleButton(true)
                        .setConfirmText("知道了")
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        }, null);
                dialog.show();
            }
        });

        mLoginModeView.setOnClickListener(v -> {
            mPasswordLayout.setErrorEnabled(false);
            mIsRegister = false;
            mUserRegisterView.setVisibility(View.VISIBLE);
            mLoginButton.setText(R.string.login);
            mPhoneLayout.setVisibility(mPhoneLayout.getVisibility() == View.VISIBLE ? View.GONE :
                    View.VISIBLE);
            mUserPasswordLayout.setVisibility(mUserPasswordLayout.getVisibility() == View.VISIBLE
                    ? View.GONE : View.VISIBLE);
            mForgetPasswordView.setVisibility(mUserPasswordLayout.getVisibility());
            mNoValidationCodeView.setVisibility(mPhoneLayout.getVisibility());
            mLoginModeView.setText(mPhoneLayout.getVisibility() == View.VISIBLE ? "账号密码登录" :
                    "验证码登录");
        });
    }

    private void getValidationCode() {
        //todo:1、发送短信 2、开始计时
        new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mValidationCodeView.setClickable(false);
                mValidationCodeView.setText("重新发送(" + millisUntilFinished / 1000 + ")秒");
            }

            @Override
            public void onFinish() {
                mValidationCodeView.setClickable(true);
                mValidationCodeView.setText("获取验证码");
            }
        }.start();
    }

    private void login() {
        mPasswordLayout.setErrorEnabled(false);
        String errorMessage = verifyInput();
        if (errorMessage.length() == 0) {
            login(mUserName, mPassword);
            finish();
        } else {
            mPasswordLayout.setErrorEnabled(true);
            mPasswordLayout.setError(errorMessage);
        }
    }

    public String verifyInput() {
        mUserName = mUserNameText.getText().toString();
        if (TextUtils.isEmpty(mUserNameText.getText())) {
            mUserNameText.requestFocus();
            return "请输入手机号";
        }

        mPassword = mPasswordText.getText().toString();
        if (TextUtils.isEmpty(mPasswordText.getText())) {
            mPasswordText.requestFocus();
            return "请输入密码";
        }
        return "";
    }

    @Override
    public void onBackPressed() {
        if (mLoginLayout.getVisibility() == View.GONE) {
            mLoginLayout.setVisibility(View.VISIBLE);
            mRecoverPasswordLayout.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
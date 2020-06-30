package com.zjclugger.lib.ui.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.R;

import me.jessyan.autosize.AutoSizeCompat;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class JAlertDialog {
    public static final int MAX_LENGTH_UNLIMITED = -1;
    private static final String TAG = "ErpAlertDialog";

    private Context mContext;
    private AlertDialog mAlertDialog;
    private String mTitle;
    private String mMessage;
    private String mConfirmText;
    private String mCancelText;
    private int mLeftButtonColor = R.color.black;
    private int mRightButtonColor = R.color.black;
    private View.OnClickListener mLeftButtonListener;
    private View.OnClickListener mRightButtonListener;
    private Bitmap mMessageImage;
    private View mDialogView;
    private AlertDialog.Builder mBuilder;
    private boolean mIsCanceledOnTouch;
    private boolean mIsCancelable;
    private boolean mIsSingleButton;
    private LayoutInflater mLayoutInflater;
    private EditText mContentView;

    public JAlertDialog(Context context) {
        this.mContext = context;
        mLeftButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };

        mRightButtonListener = mLeftButtonListener;
        mLayoutInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public JAlertDialog(Context context, View contentView) {
        this(context);
        mDialogView = contentView;
    }

    public JAlertDialog(Context context, @LayoutRes int contentLayout) {
        this(context);
        mDialogView = mLayoutInflater.inflate(contentLayout, null);
    }

    public JAlertDialog setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    public JAlertDialog setMessage(String message) {
        this.mMessage = message;
        return this;
    }

    public JAlertDialog setConfirmText(String confirmText) {
        this.mConfirmText = confirmText;
        return this;
    }

    public JAlertDialog setCancelText(String cancelText) {
        this.mCancelText = cancelText;
        return this;
    }

    public JAlertDialog setMessageImage(Bitmap messageImage) {
        mMessageImage = messageImage;
        return this;
    }

    public JAlertDialog setCanceledOnTouchOutside(boolean cancel) {
        mIsCanceledOnTouch = cancel;
        return this;
    }

    public JAlertDialog setCancelable(boolean cancelable) {
        mIsCancelable = cancelable;
        return this;
    }

    public JAlertDialog setSingleButton(boolean singleButton) {
        mIsSingleButton = singleButton;
        return this;
    }

    public JAlertDialog setLeftButtonColor(@ColorRes int leftButtonColor) {
        this.mLeftButtonColor = leftButtonColor;
        return this;
    }

    public JAlertDialog setRightButtonColor(@ColorRes int rightButtonColor) {
        this.mRightButtonColor = rightButtonColor;
        return this;
    }

    public View.OnClickListener getLeftButtonListener() {
        return mLeftButtonListener;
    }

    public JAlertDialog setLeftButtonListener(View.OnClickListener leftButtonListener) {
        if (null != leftButtonListener) {
            mLeftButtonListener = leftButtonListener;
        }
        return this;
    }

    public View.OnClickListener getRightButtonListener() {
        return mRightButtonListener;
    }

    public JAlertDialog setRightButtonListener(View.OnClickListener rightButtonListener) {
        if (null != rightButtonListener) {
            mRightButtonListener = rightButtonListener;
        }
        return this;
    }

    /**
     * 显示对话框（系统原生）
     *
     * @param confirmListener
     * @param cancelListener
     */
    public JAlertDialog createAlertDialog(DialogInterface.OnClickListener confirmListener,
                                          DialogInterface.OnClickListener cancelListener) {
        if (isShowing()) {
            dismiss();
        }

        if (cancelListener == null && confirmListener == null) {
            throw new RuntimeException("custom event is null");
        }

        mBuilder = new AlertDialog.Builder(mContext);
        mBuilder.setTitle(TextUtils.isEmpty(mTitle) ? mContext.getString(R.string.app_name) :
                mTitle);
        mBuilder.setMessage(mMessage);
        if (cancelListener != null) {
            mBuilder.setNegativeButton(!TextUtils.isEmpty(mCancelText) ? mCancelText :
                    mContext.getString(R.string.cancel), cancelListener);
        }

        if (confirmListener != null) {
            mBuilder.setPositiveButton(!TextUtils.isEmpty(mConfirmText) ? mConfirmText :
                    mContext.getString(R.string.confirm), confirmListener);
        }

        return this;
    }

    /**
     * 设置自定义对话框点击事件
     *
     * @param confirmListener
     * @param cancelListener
     */
    public JAlertDialog setOnClickListener(View.OnClickListener confirmListener,
                                           View.OnClickListener cancelListener) {
        if (isShowing()) {
            dismiss();
        }

        mDialogView = mLayoutInflater.inflate(R.layout.dialog_custom, null);

        setLeftButtonListener(confirmListener);
        setRightButtonListener(cancelListener);
        bindView();

        return this;
    }

    public JAlertDialog setOnClickListener(int maxLength, String hintText,
                                           View.OnClickListener confirmListener,
                                           View.OnClickListener cancelListener) {
        if (isShowing()) {
            dismiss();
        }

        mDialogView = mLayoutInflater.inflate(R.layout.dialog_edit_custom, null);
        TextView counterView = mDialogView.findViewById(R.id.tv_counter);
        TextView maxLengthView = mDialogView.findViewById(R.id.tv_max_length);
        if (MAX_LENGTH_UNLIMITED == maxLength) {
            mDialogView.findViewById(R.id.counter_layout).setVisibility(View.GONE);
        } else {
            maxLengthView.setText(String.valueOf(maxLength));
        }

        mContentView = mDialogView.findViewById(R.id.et_content);
        mContentView.setHint(hintText);
        mContentView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        mContentView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                counterView.setText(String.valueOf(s.length()));
                if (s.length() == maxLength) {
                    counterView.setTextColor(mContext.getResources().getColor(R.color.red));
                } else {
                    counterView.setTextColor(mContext.getResources().getColor(R.color.black));
                }
            }
        });
        setLeftButtonListener(confirmListener);
        setRightButtonListener(cancelListener);
        bindView();

        return this;
    }

    public EditText getContentView() {
        return mContentView;
    }

    private void bindView() {
        TextView titleText = mDialogView.findViewById(R.id.tv_title_text);
        if (TextUtils.isEmpty(mTitle)) {
            mDialogView.findViewById(R.id.dialog_title_layout).setVisibility(View.GONE);
        } else {
            titleText.setText(mTitle);
        }

    /*    if (mMessageImage != null) {
            ImageView imageView = mDialogView.findViewById(R.id.image_message);
            imageView.setImageBitmap(mMessageImage);
        }*/

        TextView messageView = mDialogView.findViewById(R.id.tv_message);
        if (TextUtils.isEmpty(mMessage)) {
            messageView.setVisibility(View.GONE);
        } else {
            messageView.setText(mMessage);
        }

        TextView leftButton = mDialogView.findViewById(R.id.left_button);
        if (null != mLeftButtonListener) {
            leftButton.setTextColor(mContext.getResources().getColor(mLeftButtonColor));
            leftButton.setOnClickListener(mLeftButtonListener);
            if (!TextUtils.isEmpty(mConfirmText)) {
                leftButton.setText(mConfirmText);
            }
        }

        TextView rightButton = mDialogView.findViewById(R.id.right_button);
        if (null != mRightButtonListener) {
            rightButton.setTextColor(mContext.getResources().getColor(mRightButtonColor));
            if (!TextUtils.isEmpty(mCancelText)) {
                rightButton.setText(mCancelText);
            }
        }

        if (mRightButtonListener != null && !mIsSingleButton) {
            rightButton.setVisibility(View.VISIBLE);
            rightButton.setOnClickListener(mRightButtonListener);
        } else {
            mDialogView.findViewById(R.id.middle_line).setVisibility(View.GONE);
            rightButton.setVisibility(View.GONE);
        }
    }

    public JAlertDialog setData(BaseQuickAdapter adapter) {
        return setData(null, null, null, adapter);
    }

    public JAlertDialog setData(String title, BaseQuickAdapter adapter) {
        return setData(title, null, null, adapter);
    }

    public JAlertDialog setData(String title, View.OnClickListener confirmListener,
                                View.OnClickListener cancelListener, BaseQuickAdapter adapter) {
        if (isShowing()) {
            dismiss();
        }

        if (adapter == null) {
            Log.e(TAG, "setOnClickListener list data but the adapter is null");
            return null;
        }

        mDialogView = mLayoutInflater.inflate(R.layout.dialog_list, null);
        if (!TextUtils.isEmpty(title)) {
            TextView titleText = mDialogView.findViewById(R.id.dialog_title);
            titleText.setText(title);
        } else {
            mDialogView.findViewById(R.id.dialog_title_layout).setVisibility(View.GONE);
        }

        if (confirmListener == null && cancelListener == null) {
            mDialogView.findViewById(R.id.dialog_list_footer).setVisibility(View.GONE);
        } else {
            mDialogView.findViewById(R.id.dialog_ok).setOnClickListener(confirmListener);
            mDialogView.findViewById(R.id.dialog_cancel).setOnClickListener(cancelListener);
        }

        if (TextUtils.isEmpty(title) && confirmListener == null && cancelListener == null) {
            mDialogView.findViewById(R.id.dialog_content).setBackgroundResource(R.drawable.shape_corners_border_white);
        }

        RecyclerView dataView = mDialogView.findViewById(R.id.dialog_list_view);
        dataView.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration divider = new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL);
        dataView.addItemDecoration(divider);
        dataView.setAdapter(adapter);
        return this;
    }

    public boolean isShowing() {
        return mAlertDialog == null ? false : mAlertDialog.isShowing();
    }


    /**
     * 显示对话框
     */
    public void show() {
        if (null == mAlertDialog) {
            if (null == mBuilder) {
                mBuilder = new AlertDialog.Builder(mContext);
            }
            mAlertDialog = mBuilder.create();
            //大屏幕，高分辨率时，显示不全
            AutoSizeCompat.autoConvertDensityOfGlobal(mContext.getResources());//如果没有自定义需求用这个方法
            mAlertDialog.show();
            mAlertDialog.setCanceledOnTouchOutside(mIsCanceledOnTouch);  //放在show的后面
            mAlertDialog.setCancelable(mIsCancelable);
            mAlertDialog.getWindow().setContentView(mDialogView);
            mAlertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            mAlertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } else {
            if (mAlertDialog != null && isShowing()) {
                dismiss();
            }
            //大屏幕，高分辨率时，显示不全
            AutoSizeCompat.autoConvertDensityOfGlobal(mContext.getResources());//如果没有自定义需求用这个方法
            mAlertDialog.show();
        }
    }

    /**
     * 关闭显示框
     */
    public void dismiss() {
        if (null != mContentView) {
            mContentView.setText("");
        }
        if (isShowing()) {
            mAlertDialog.cancel();
        }
    }
}
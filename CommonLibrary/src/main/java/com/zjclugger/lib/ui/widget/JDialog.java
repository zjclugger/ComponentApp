package com.zjclugger.lib.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StyleRes;

import com.zjclugger.lib.R;
import com.zjclugger.lib.utils.ViewUtils;

/**
 * <br>
 * Created by King.Zi on 2020/4/26.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class JDialog {
    private static final String TAG = "JDialog";

    protected Context mContext;
    protected View mDialogView;
    private Dialog mDialog;

    public JDialog(Context context, @LayoutRes int layoutRedId) {
        this(context, R.style.CustomDialog, layoutRedId);
    }

    public JDialog(Context context, @StyleRes int themeResId, @LayoutRes int layoutRedId) {
        mContext = context;
        mDialog = new Dialog(mContext, themeResId);
        mDialogView = LayoutInflater.from(mContext).inflate(layoutRedId, null);
        mDialog.setContentView(mDialogView);
        mDialog.setCanceledOnTouchOutside(true);
        //  mDialog.getWindow().setGravity(Gravity.BOTTOM);
        mDialog.getWindow().setWindowAnimations(R.style.Dialog_Animation);
        //mDialog.show();
    }

    public JDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 设置位置
     *
     * @param gravity
     * @return
     */
    public JDialog setGravity(int gravity) {
        mDialog.getWindow().setGravity(gravity);
        return this;
    }

    public View getDialogView() {
        return mDialogView;
    }

    public TextView getTextView(@IdRes int textViewId) {
        TextView textView = mDialogView.findViewById(textViewId);
        return textView;
    }

    public JDialog setMargin(int marginLeft, int marginTop, int marginRight, int marginBottom) {
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) mDialogView.getLayoutParams();
        params.width =
                mContext.getResources().getDisplayMetrics().widthPixels - ViewUtils.dp2px(mContext,
                        marginLeft + marginRight);
        params.setMargins(ViewUtils.dp2px(mContext, marginLeft), ViewUtils.dp2px(mContext,
                marginTop), ViewUtils.dp2px(mContext, marginRight), ViewUtils.dp2px(mContext,
                marginBottom));
        mDialogView.setLayoutParams(params);
        return this;
    }

    public JDialog setWindowAnimations(@StyleRes int resId) {
        mDialog.getWindow().setWindowAnimations(resId);
        return this;
    }

    public boolean isShowing() {
        return mDialog == null ? false : mDialog.isShowing();
    }


    /**
     * 显示
     */
    public void show() {
        if (null != mDialog) {
            mDialog.show();
        }
    }

    /**
     * 关闭
     */
    public void close() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
    }
}
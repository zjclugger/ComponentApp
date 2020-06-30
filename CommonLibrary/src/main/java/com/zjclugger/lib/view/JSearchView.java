package com.zjclugger.lib.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import com.zjclugger.lib.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class JSearchView extends RelativeLayout {
    private final static int THRESHOLD_HEIGHT = 200;    //阀值高度
    private ImageView mBackView;
    private TextView mTextView;
    private TextView mRightView;
    private SearchView mSearchView;
    private View mRootView;
    private int mRootViewVisibleHeight;
    private OnSoftKeyboardChangeListener mOnSoftKeyboardChangeListener;

    public interface OnSoftKeyboardChangeListener {
        void show(int height);

        void hide(int height);
    }

    public JSearchView(Context context) {
        super(context);
    }

    public JSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JSearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(Activity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_search_view, this, true);
        mTextView = view.findViewById(R.id.erp_focus_view);
        mRightView = view.findViewById(R.id.erp_search_right);
        mSearchView = view.findViewById(R.id.erp_search_view);
        mBackView = view.findViewById(R.id.erp_search_back);
        mRootView = activity.getWindow().getDecorView();
        addOnGlobalLayoutListener();
    }

    private void addOnGlobalLayoutListener() {
        //listener change
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //current view size
                Rect r = new Rect();
                mRootView.getWindowVisibleDisplayFrame(r);

                int visibleHeight = r.height();
                if (mRootViewVisibleHeight == 0) {
                    mRootViewVisibleHeight = visibleHeight;
                    return;
                }

                if (mRootViewVisibleHeight == visibleHeight) {
                    return;
                }

                //根视图显示高度变小超过200，看作软键盘显示了
                if (mRootViewVisibleHeight - visibleHeight > THRESHOLD_HEIGHT) {
                    if (mOnSoftKeyboardChangeListener != null) {
                        mOnSoftKeyboardChangeListener.show(mRootViewVisibleHeight - visibleHeight);
                    }
                    mRootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度变大超过200，看作软键盘隐藏了
                if (visibleHeight - mRootViewVisibleHeight > THRESHOLD_HEIGHT) {
                    if (mOnSoftKeyboardChangeListener != null) {
                        mOnSoftKeyboardChangeListener.hide(visibleHeight - mRootViewVisibleHeight);
                    }
                    mRootViewVisibleHeight = visibleHeight;
                    return;
                }
            }
        });
    }

    public SearchView getSearchView() {
        return mSearchView;
    }

    public void clearFocus() {
        mTextView.requestFocus();
    }

    public void setOnSoftKeyBoardChangeListener(OnSoftKeyboardChangeListener listener) {
        mOnSoftKeyboardChangeListener = listener;
    }

    public ImageView getBackView() {
        return mBackView;
    }

    public TextView getRightView() {
        return mRightView;
    }

    public void setReadOnly(boolean readOnly) {
        // mSearchView.setCursorVisible(false);
        mSearchView.setEnabled(false);
        mSearchView.setFocusable(false);
        mSearchView.setFocusableInTouchMode(false);
    }

    public void setRightViewAsMoreView(boolean asMoreView) {
        if (asMoreView) {
            mRightView.setBackgroundResource(R.mipmap.ic_more_filtrate);
            mRightView.setText("");
        }
    }

    public void setQueryHint(String hint) {
        mSearchView.setQueryHint(hint);
    }

    public void hideInput(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        View v = context.getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
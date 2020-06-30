package com.zjclugger.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.zjclugger.lib.R;

/**
 * 带长度限制的输入框<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LengthLimitEditTextView extends LinearLayout {
    private View mView;
    private ExtendLabelValueView mCountView;
    private EditText mEditTextView;
    private int mMaxLength;
    @ColorInt
    private int mCounterDefaultColor;

    public LengthLimitEditTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mView = LayoutInflater.from(context).inflate(R.layout.layout_length_limit_edit_text_view, this,
                true);
        mCountView = mView.findViewById(R.id.elvv_count);
        mEditTextView = mView.findViewById(R.id.et_content);
        mEditTextView.addTextChangedListener(mTextWatcher);
        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.LengthLimitEditTextView);
        if (attributes != null) {
            String text =
                    attributes.getString(R.styleable.LengthLimitEditTextView_lletv_content_text);
            if (!TextUtils.isEmpty(text)) {
                mEditTextView.setText(text);
            }
            mEditTextView.setTextColor(attributes.getColor(R.styleable.LengthLimitEditTextView_lletv_content_text_color, Color.BLACK));
            mEditTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    attributes.getDimensionPixelSize(R.styleable.LengthLimitEditTextView_lletv_content_text_size, 14));
            mMaxLength = attributes.getInt(R.styleable.LengthLimitEditTextView_lletv_max_length,
                    50);
            mEditTextView.setHint(attributes.getString(R.styleable.LengthLimitEditTextView_lletv_content_hint_text));
            mEditTextView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
            mCountView.getValueView().setText(String.valueOf(mMaxLength));
            mCounterDefaultColor =
                    attributes.getColor(R.styleable.LengthLimitEditTextView_lletv_counter_color,
                            Color.BLACK);
            mCountView.getLabelView().setTextColor(mCounterDefaultColor);
            if (attributes.getBoolean(R.styleable.LengthLimitEditTextView_lletv_read_only, false)) {
                setReadOnly();
            }
        }
    }

    TextWatcher mTextWatcher = new TextWatcher() {
  /*      private CharSequence content;
        private int editStart;
        private int editEnd;
        private int inputLen;*/

        public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
         /*   inputLen = arg3;
            content = s;*/
        }

        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
        }

        public void afterTextChanged(Editable s) {
          /*  editStart = mEditTextView.getSelectionStart();
            editEnd = mEditTextView.getSelectionEnd();*/

            mCountView.setLabelText(String.valueOf(s.length()));
            if (s.length() == mMaxLength) {
                mCountView.getLabelView().setTextColor(Color.RED);
                //s.delete(editStart - inputLen, editEnd);
            } else {
                mCountView.getLabelView().setTextColor(mCounterDefaultColor);
            }
        }
    };

    public ExtendLabelValueView getCountView() {
        return mCountView;
    }

    public EditText getEditTextView() {
        return mEditTextView;
    }

    public void setReadOnly() {
        mEditTextView.setCursorVisible(false);
        mEditTextView.setFocusable(false);
        mEditTextView.setFocusableInTouchMode(false);
    }
}
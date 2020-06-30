package com.zjclugger.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zjclugger.lib.R;
import com.zjclugger.lib.utils.ViewUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LabelEditTextView extends LinearLayout {
    private View mView;
    private LinearLayout mLayout;
    private TextView mLabelView;
    private EditText mEditTextView;
    private Map<String, Integer> styleableMap = new HashMap();
    //view
    private static final String VIEW_WEIGHT = "view_weight";


    public LabelEditTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mView = LayoutInflater.from(context).inflate(R.layout.layout_label_edit_text_view, this, true);
        mLabelView = mView.findViewById(R.id.label_text);
        mEditTextView = mView.findViewById(R.id.edit_text);
        mLayout = mView.findViewById(R.id.elvv_layout);

        //setTypeface
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.LabelValueView);
        if (attributes != null) {
            mLayout.setOrientation(attributes.getInt(R.styleable.LabelValueView_layout_orientation, 0));
            //Label text
            styleableMap.put(Styleable.TEXT, R.styleable.LabelValueView_label_text);
            styleableMap.put(Styleable.TEXT_SIZE, R.styleable.LabelValueView_label_text_size);
            styleableMap.put(Styleable.TEXT_COLOR, R.styleable.LabelValueView_label_text_color);
            styleableMap.put(Styleable.TEXT_WIDTH, R.styleable.LabelValueView_label_text_width);
            styleableMap.put(Styleable.TEXT_HEIGHT, R.styleable.LabelValueView_label_text_height);
            styleableMap.put(Styleable.TEXT_GRAVITY, R.styleable.LabelValueView_label_text_gravity);
            styleableMap.put(Styleable.TEXT_STYLE, R.styleable.LabelValueView_label_text_style);
            styleableMap.put(Styleable.TEXT_VISIBILITY,
                    R.styleable.LabelValueView_label_text_visibility);
            styleableMap.put(Styleable.TEXT_BACKGROUND,
                    R.styleable.LabelValueView_label_text_background);
            styleableMap.put(VIEW_WEIGHT, R.styleable.LabelValueView_label_text_weight);
            initTextView(mLabelView, attributes, styleableMap);
            styleableMap.clear();

            //value text
            styleableMap.put(Styleable.TEXT, R.styleable.LabelValueView_value_text);
            styleableMap.put(Styleable.TEXT_SIZE, R.styleable.LabelValueView_value_text_size);
            styleableMap.put(Styleable.TEXT_COLOR, R.styleable.LabelValueView_value_text_color);
            styleableMap.put(Styleable.TEXT_WIDTH, R.styleable.LabelValueView_value_text_width);
            styleableMap.put(Styleable.TEXT_HEIGHT, R.styleable.LabelValueView_value_text_height);
            styleableMap.put(Styleable.TEXT_GRAVITY, R.styleable.LabelValueView_value_text_gravity);
            styleableMap.put(Styleable.TEXT_STYLE, R.styleable.LabelValueView_value_text_style);
            styleableMap.put(Styleable.TEXT_VISIBILITY,
                    R.styleable.LabelValueView_value_text_visibility);
            styleableMap.put(Styleable.TEXT_BACKGROUND,
                    R.styleable.LabelValueView_value_text_background);
            styleableMap.put(VIEW_WEIGHT, R.styleable.LabelValueView_value_text_weight);
            initEditTextView(mEditTextView, attributes, styleableMap);
            styleableMap.clear();


            attributes.recycle();
        }
    }

    private void initTextView(TextView view, TypedArray attributes,
                              Map<String, Integer> styleableMap) {
        String text = attributes.getString(styleableMap.get(Styleable.TEXT));
        if (!TextUtils.isEmpty(text)) {
            view.setText(text);
        }
        view.setTextColor(attributes.getColor(styleableMap.get(Styleable.TEXT_COLOR), Color.BLACK));

        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                attributes.getDimensionPixelSize(styleableMap.get(Styleable.TEXT_SIZE), 14));
        ViewUtils.setWidth(view,
                attributes.getDimensionPixelSize(styleableMap.get(Styleable.TEXT_WIDTH), 0));
        ViewUtils.setHeight(view,
                attributes.getDimensionPixelSize(styleableMap.get(Styleable.TEXT_HEIGHT), 0));
        ViewUtils.setWeight(view,
                attributes.getInt(styleableMap.get(VIEW_WEIGHT), 1));
        ViewUtils.setGravity(view, attributes.getInt(styleableMap.get(Styleable.TEXT_GRAVITY), 0));
        ViewUtils.setTextStyle(view,
                attributes.getInt(styleableMap.get(Styleable.TEXT_STYLE), -1));
        ViewUtils.setVisibility(view,
                attributes.getInt(styleableMap.get(Styleable.TEXT_VISIBILITY), 0));
        //backround
        Drawable background = attributes.getDrawable(styleableMap.get(Styleable.TEXT_BACKGROUND));
        if (background != null) {
            view.setBackground(background);
        }
    }

    private void initEditTextView(EditText view, TypedArray attributes,
                                  Map<String, Integer> styleableMap) {
        String text = attributes.getString(styleableMap.get(Styleable.TEXT));
        if (!TextUtils.isEmpty(text)) {
            view.setText(text);
        }
        view.setTextColor(attributes.getColor(styleableMap.get(Styleable.TEXT_COLOR), Color.BLACK));

        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                attributes.getDimensionPixelSize(styleableMap.get(Styleable.TEXT_SIZE), 14));
        ViewUtils.setWidth(view,
                attributes.getDimensionPixelSize(styleableMap.get(Styleable.TEXT_WIDTH), 0));
        ViewUtils.setHeight(view,
                attributes.getDimensionPixelSize(styleableMap.get(Styleable.TEXT_HEIGHT), 0));
        ViewUtils.setWeight(view,
                attributes.getInt(styleableMap.get(VIEW_WEIGHT), 1));
        ViewUtils.setGravity(view, attributes.getInt(styleableMap.get(Styleable.TEXT_GRAVITY), 0));
        ViewUtils.setTextStyle(view,
                attributes.getInt(styleableMap.get(Styleable.TEXT_STYLE), -1));
        ViewUtils.setVisibility(view,
                attributes.getInt(styleableMap.get(Styleable.TEXT_VISIBILITY), 0));
        //backround
        Drawable background = attributes.getDrawable(styleableMap.get(Styleable.TEXT_BACKGROUND));
        if (background != null) {
            view.setBackground(background);
        }
    }

    public void setValue(String value) {
        mEditTextView.setText(value);
    }

    public String getValue() {
        return mEditTextView.getText().toString();
    }

    public EditText getEditTextView() {
        return mEditTextView;
    }

    public TextView getLabelView(){
        return mLabelView;
    }

    public void setReadOnly() {
        mEditTextView.setCursorVisible(false);
        mEditTextView.setFocusable(false);
        mEditTextView.setFocusableInTouchMode(false);
    }
}
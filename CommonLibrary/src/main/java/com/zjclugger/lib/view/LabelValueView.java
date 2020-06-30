package com.zjclugger.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.zjclugger.lib.R;

import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class LabelValueView extends JTextView {
    protected Context mContext;
    private LinearLayout mLayout;
    protected TextView mLabelView;
    protected TextView mValueView;
    protected View mView;

    public LabelValueView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mView = LayoutInflater.from(context).inflate(R.layout.layout_label_value_view, this, true);
        mLabelView = mView.findViewById(R.id.label_text);
        mValueView = mView.findViewById(R.id.label_value);
        mLayout = mView.findViewById(R.id.lvv_layout);

        Map<String, Integer> styleableMap = new HashMap();
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.LabelValueView);
        if (attributes != null) {
            mLayout.setOrientation(attributes.getInt(R.styleable.LabelValueView_layout_orientation, 0));
            //Label text
            styleableMap.put(Styleable.TEXT, R.styleable.LabelValueView_label_text);
            styleableMap.put(Styleable.TEXT_SIZE, R.styleable.LabelValueView_label_text_size);
            styleableMap.put(Styleable.TEXT_COLOR, R.styleable.LabelValueView_label_text_color);
            styleableMap.put(Styleable.TEXT_WIDTH, R.styleable.LabelValueView_label_text_width);
            styleableMap.put(Styleable.TEXT_HEIGHT, R.styleable.LabelValueView_label_text_height);
            styleableMap.put(Styleable.TEXT_MIN_WIDTH, R.styleable.LabelValueView_label_text_min_width);
            styleableMap.put(Styleable.TEXT_MIN_HEIGHT, R.styleable.LabelValueView_label_text_min_height);
            styleableMap.put(Styleable.TEXT_GRAVITY, R.styleable.LabelValueView_label_text_gravity);
            styleableMap.put(Styleable.TEXT_STYLE, R.styleable.LabelValueView_label_text_style);
            styleableMap.put(Styleable.TEXT_VISIBILITY,
                    R.styleable.LabelValueView_label_text_visibility);
            styleableMap.put(Styleable.TEXT_BACKGROUND,
                    R.styleable.LabelValueView_label_text_background);
            styleableMap.put(Styleable.VIEW_WEIGHT, R.styleable.LabelValueView_label_text_weight);
            //border
            styleableMap.put(Styleable.TEXT_SOLID_COLOR,
                    R.styleable.LabelValueView_label_text_solid_color);
            styleableMap.put(Styleable.TEXT_BORDER_WIDTH,
                    R.styleable.LabelValueView_label_text_border_width);
            styleableMap.put(Styleable.TEXT_BORDER_COLOR,
                    R.styleable.LabelValueView_label_text_border_color);
            styleableMap.put(Styleable.TEXT_CORNER_RADIUS,
                    R.styleable.LabelValueView_label_text_corner_radius);
            styleableMap.put(Styleable.TEXT_TOP_LEFT_RADIUS,
                    R.styleable.LabelValueView_label_text_topLeft_radius);
            styleableMap.put(Styleable.TEXT_TOP_RIGHT_RADIUS,
                    R.styleable.LabelValueView_label_text_topRight_radius);
            styleableMap.put(Styleable.TEXT_BOTTOM_LEFT_RADIUS,
                    R.styleable.LabelValueView_label_text_bottomLeft_radius);
            styleableMap.put(Styleable.TEXT_BOTTOM_RIGHT_RADIUS,
                    R.styleable.LabelValueView_label_text_bottomRight_radius);
            initTextView(mLabelView, attributes, styleableMap);
            styleableMap.clear();

            //value text
            styleableMap.put(Styleable.TEXT, R.styleable.LabelValueView_value_text);
            styleableMap.put(Styleable.TEXT_SIZE, R.styleable.LabelValueView_value_text_size);
            styleableMap.put(Styleable.TEXT_COLOR, R.styleable.LabelValueView_value_text_color);
            styleableMap.put(Styleable.TEXT_WIDTH, R.styleable.LabelValueView_value_text_width);
            styleableMap.put(Styleable.TEXT_HEIGHT, R.styleable.LabelValueView_value_text_height);
            styleableMap.put(Styleable.TEXT_MIN_WIDTH, R.styleable.LabelValueView_value_text_min_width);
            styleableMap.put(Styleable.TEXT_MIN_HEIGHT, R.styleable.LabelValueView_value_text_min_height);
            styleableMap.put(Styleable.TEXT_GRAVITY, R.styleable.LabelValueView_value_text_gravity);
            styleableMap.put(Styleable.TEXT_STYLE, R.styleable.LabelValueView_value_text_style);
            styleableMap.put(Styleable.TEXT_VISIBILITY,
                    R.styleable.LabelValueView_value_text_visibility);
            styleableMap.put(Styleable.TEXT_BACKGROUND,
                    R.styleable.LabelValueView_value_text_background);
            styleableMap.put(Styleable.VIEW_WEIGHT, R.styleable.LabelValueView_value_text_weight);
            //border
            styleableMap.put(Styleable.TEXT_SOLID_COLOR,
                    R.styleable.LabelValueView_value_text_solid_color);
            styleableMap.put(Styleable.TEXT_BORDER_WIDTH,
                    R.styleable.LabelValueView_value_text_border_width);
            styleableMap.put(Styleable.TEXT_BORDER_COLOR,
                    R.styleable.LabelValueView_value_text_border_color);
            styleableMap.put(Styleable.TEXT_CORNER_RADIUS,
                    R.styleable.LabelValueView_value_text_corner_radius);
            styleableMap.put(Styleable.TEXT_TOP_LEFT_RADIUS,
                    R.styleable.LabelValueView_value_text_topLeft_radius);
            styleableMap.put(Styleable.TEXT_TOP_RIGHT_RADIUS,
                    R.styleable.LabelValueView_value_text_topRight_radius);
            styleableMap.put(Styleable.TEXT_BOTTOM_LEFT_RADIUS,
                    R.styleable.LabelValueView_value_text_bottomLeft_radius);
            styleableMap.put(Styleable.TEXT_BOTTOM_RIGHT_RADIUS,
                    R.styleable.LabelValueView_value_text_bottomRight_radius);
            initTextView(mValueView, attributes, styleableMap);
            styleableMap.clear();

            attributes.recycle();
        }
    }

    public void setLabelText(String labelText) {
        if (mLabelView != null) {
            mLabelView.setText(labelText);
        }
    }

    public void setValueText(String valueText) {
        if (mValueView != null) {
            mValueView.setText(valueText);
        }
    }

    public void setLabelText(@StringRes int labelText) {
        if (mLabelView != null) {
            mLabelView.setText(labelText);
        }
    }

    public void setValueText(@StringRes int valueText) {
        if (mValueView != null) {
            mValueView.setText(valueText);
        }
    }

    public void setLabelTextVisibility(int visibility) {
        if (mLabelView != null) {
            mLabelView.setVisibility(visibility);
        }
    }

    public void setValueTextVisibility(int visibility) {
        if (mValueView != null) {
            mValueView.setVisibility(visibility);
        }
    }

    public TextView getLabelView() {
        return mLabelView;
    }

    public TextView getValueView() {
        return mValueView;
    }
}
package com.zjclugger.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class JTextView extends LinearLayout {
    protected static final int GRAVITY_LEFT = 0;
    protected static final int GRAVITY_CENTER = 1;
    protected static final int GRAVITY_RIGHT = 2;
    protected static final int TEXT_STYLE_BOLD = 0;
    protected static final int TEXT_STYLE_ITALIC = 1;
    protected static final int TEXT_STYLE_ITALIC_BOLD = 2;
    protected Context mContext;

    public JTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    protected void setWidth(View view, int width) {
        if (width > 0) {
            LayoutParams layoutParams =
                    (LayoutParams) view.getLayoutParams();
            layoutParams.width = width;
            layoutParams.weight = 0;
            view.setLayoutParams(layoutParams);
        }
    }

    protected void setMinWidth(TextView view, int minWidth) {
        if (minWidth > 0) {
           view.setMinWidth(minWidth);
        }
    }

    protected void setMinHeight(TextView view, int minHeight) {
        if (minHeight > 0) {
           view.setMinHeight(minHeight);
        }
    }

    protected void setMargins(View view, int left, int top, int right, int bottom) {
        LayoutParams layoutParams =
                (LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }

    protected void setHeight(View view, int height) {
        if (height > 0) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }
    }

    protected void setWeight(View view, float weight) {
        if (weight > 0) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.weight = weight;
            view.setLayoutParams(layoutParams);
        }
    }

    protected void setTextStyle(TextView textView, int textStyle) {
        switch (textStyle) {
            case TEXT_STYLE_BOLD:
                textView.setTypeface(null, Typeface.BOLD);
                break;
            case TEXT_STYLE_ITALIC:
                textView.setTypeface(null, Typeface.ITALIC);
                break;
            case TEXT_STYLE_ITALIC_BOLD:
                textView.setTypeface(null, Typeface.BOLD_ITALIC);
                break;
            default:
                textView.setTypeface(null, Typeface.NORMAL);
                break;
        }
    }

    protected void setGravity(TextView textView, int gravity) {
        switch (gravity) {
            case GRAVITY_LEFT:
                textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                break;
            case GRAVITY_CENTER:
                textView.setGravity(Gravity.CENTER);
                break;
            case GRAVITY_RIGHT:
                textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
            default:
                break;
        }
    }

    protected void setVisibility(View view, int visibility) {
        switch (visibility) {
            case 0:
                view.setVisibility(VISIBLE);
                break;
            case 4:
                view.setVisibility(INVISIBLE);
                break;
            case 8:
                view.setVisibility(GONE);
                break;
            default:
                break;
        }
    }

    protected void initTextView(TextView view, TypedArray attributes,
                                Map<String, Integer> styleableMap) {
        String text = attributes.getString(styleableMap.get(Styleable.TEXT));
        if (!TextUtils.isEmpty(text)) {
            view.setText(text);
        }
        if (styleableMap.containsKey(Styleable.TEXT_HINT)) {
            String textHint = attributes.getString(styleableMap.get(Styleable.TEXT_HINT));
            if (!TextUtils.isEmpty(textHint)) {
                view.setHint(textHint);
            }
        }

        view.setTextColor(attributes.getColor(styleableMap.get(Styleable.TEXT_COLOR), Color.BLACK));

        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                attributes.getDimensionPixelSize(styleableMap.get(Styleable.TEXT_SIZE), dp2px(14)));
        setWidth(view, attributes.getDimensionPixelSize(styleableMap.get(Styleable.TEXT_WIDTH), 0));
        setHeight(view, attributes.getDimensionPixelSize(styleableMap.get(Styleable.TEXT_HEIGHT),
                0));
        setMinWidth(view,
                attributes.getDimensionPixelSize(styleableMap.get(Styleable.TEXT_MIN_WIDTH), 0));
        setMinHeight(view,
                attributes.getDimensionPixelSize(styleableMap.get(Styleable.TEXT_MIN_HEIGHT), 0));
        setWeight(view, attributes.getFloat(styleableMap.get(Styleable.VIEW_WEIGHT), -1));
        setGravity(view, attributes.getInt(styleableMap.get(Styleable.TEXT_GRAVITY), 0));
        setTextStyle(view, attributes.getInt(styleableMap.get(Styleable.TEXT_STYLE), -1));
        setVisibility(view, attributes.getInt(styleableMap.get(Styleable.TEXT_VISIBILITY), 0));
        //backround
        Drawable background = attributes.getDrawable(styleableMap.get(Styleable.TEXT_BACKGROUND));
        if (background != null) {
            view.setBackground(background);
        }

        //border
        int borderWidth =
                attributes.getDimensionPixelSize(styleableMap.get(Styleable.TEXT_BORDER_WIDTH), 0);
        int borderColor = attributes.getColor(styleableMap.get(Styleable.TEXT_BORDER_COLOR),
                Color.TRANSPARENT);
        float radius = attributes.getDimension(styleableMap.get(Styleable.TEXT_CORNER_RADIUS), 0);
        float topLeftRadius =
                attributes.getDimension(styleableMap.get(Styleable.TEXT_TOP_LEFT_RADIUS), 0);
        float topRightRadius =
                attributes.getDimension(styleableMap.get(Styleable.TEXT_TOP_RIGHT_RADIUS), 0);
        float bottomLeftRadius =
                attributes.getDimension(styleableMap.get(Styleable.TEXT_BOTTOM_LEFT_RADIUS), 0);
        float bottomRightRadius =
                attributes.getDimension(styleableMap.get(Styleable.TEXT_BOTTOM_RIGHT_RADIUS), 0);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(attributes.getColor(styleableMap.get(Styleable.TEXT_SOLID_COLOR), Color.TRANSPARENT));
        gradientDrawable.setStroke(borderWidth, borderColor);

        if (topLeftRadius > 0 || topRightRadius > 0 || bottomLeftRadius > 0 || bottomRightRadius > 0) {
            float[] radii = new float[]{
                    topLeftRadius, topLeftRadius,
                    topRightRadius, topRightRadius,
                    bottomRightRadius, bottomRightRadius,
                    bottomLeftRadius, bottomLeftRadius
            };
            gradientDrawable.setCornerRadii(radii);
        } else {
            gradientDrawable.setCornerRadius(radius);
        }
        view.setBackground(gradientDrawable);
    }

    public int dp2px(int value) {
        float v = mContext.getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }
}
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.zjclugger.lib.R;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class CircleImageTextView extends RelativeLayout {
    private Context mContext;
    private TextView mTextView;
    private ImageView mDeleteImageView;
    private CircleImageView mAddImageView;
    private CircleImageView mCircleImageView;
    private RelativeLayout mCircleImageViewLayout;
    private RelativeLayout mLeftLayout;

/*
 //for CircleImageView //TODO:先写这一部分
    private int mCircleImageTextColor;
    private float mCircleImageTextSize;
    private String mCircleImageCenterText;
    private float mCircleImageBgRadius;
    private int mCircleImageBgColor;
    private int mCircleImageBgStrokeWidth;
    private int mCircleImageBgStrokeColor;
    private boolean mIsCircleImageShowCircle = true;
*/

    public CircleImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_circle_image_text_view, this,
                true);

        mTextView = view.findViewById(R.id.citv_text);
        mDeleteImageView = view.findViewById(R.id.citv_delete);
        mAddImageView = view.findViewById(R.id.citv_add);
        mCircleImageView = view.findViewById(R.id.citv_image);
        mCircleImageViewLayout = view.findViewById(R.id.citv_image_layout);
        mLeftLayout = view.findViewById(R.id.citv_left_layout);

        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.CircleImageTextView);
        if (attributes != null) {
            initCircleImageView(attributes);
            initTextView(attributes);

            setVisibility(mAddImageView,
                    attributes.getInt(R.styleable.CircleImageTextView_citv_add_visibility, 0));
            setVisibility(mDeleteImageView,
                    attributes.getInt(R.styleable.CircleImageTextView_citv_delete_visibility, 0));
            setVisibility(mTextView,
                    attributes.getInt(R.styleable.CircleImageTextView_citv_text_visibility, 0));

            setLeftLayoutWidth(attributes.getLayoutDimension(R.styleable.CircleImageTextView_citv_left_layout_width, 0));
            attributes.recycle();
        }
    }

    public void setLeftLayoutWidth(int width) {
        if (width != 0) {
            mLeftLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, width));
        }
    }

    private void initCircleImageView(TypedArray attributes) {
        setShowCircle(attributes.getBoolean(R.styleable.CircleImageTextView_citv_civ_show_circle,
                true));
        setCircleImageTextColor(attributes.getColor(R.styleable.CircleImageTextView_citv_civ_text_color, Color.BLACK));
        setCircleImageTextSize(attributes.getDimension(R.styleable.CircleImageTextView_citv_civ_text_size, 14));
        String centerText = attributes.getString(R.styleable.CircleImageTextView_citv_civ_text);
        if (!TextUtils.isEmpty(centerText)) {
            setCircleImageCenterText(centerText);
        }
        setCircleImageBgRadius(attributes.getDimension(R.styleable.CircleImageTextView_citv_civ_background_radius, 0));
        setCircleImageBgStrokeWidth(attributes.getDimensionPixelSize(R.styleable.CircleImageTextView_citv_civ_background_stroke_width, 2));
        setCircleImageBgColor(attributes.getColor(R.styleable.CircleImageTextView_citv_civ_background_color, 0x00000000));
        setCircleImageBgStrokeColor(attributes.getColor(R.styleable.CircleImageTextView_citv_civ_background_stroke_color, 0x00000000));
        Drawable imageSrc = attributes.getDrawable(R.styleable.CircleImageTextView_citv_image_src);
        if (imageSrc != null) {
            mCircleImageView.setImageDrawable(imageSrc);
        }

        Drawable background =
                attributes.getDrawable(R.styleable.CircleImageTextView_citv_background);
        if (background != null) {
            mCircleImageViewLayout.setBackground(background);
        }
        int padding =
                attributes.getDimensionPixelSize(R.styleable.CircleImageTextView_citv_padding, 0);
        mCircleImageViewLayout.setPadding(padding, padding, padding, padding);
    }

    private void initTextView(TypedArray attributes) {
        mTextView.setTextColor(attributes.getColor(R.styleable.CircleImageTextView_citv_text_color, Color.BLACK));
        String text = attributes.getString(R.styleable.CircleImageTextView_citv_text);
        if (!TextUtils.isEmpty(text)) {
            mTextView.setText(text);
        }
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                attributes.getDimensionPixelSize(R.styleable
                        .CircleImageTextView_citv_text_size, 14));
    }

    private void setVisibility(View view, int visibility) {
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

    public TextView getTextView() {
        return mTextView;
    }

    public ImageView getDeleteImageView() {
        return mDeleteImageView;
    }

    public CircleImageView getAddImageView() {
        return mAddImageView;
    }

    public CircleImageView getCircleImageView() {
        return mCircleImageView;
    }

    public int getCircleImageTextColor() {
        return mCircleImageView.getTextColor();
    }

    public void setCircleImageTextColor(@ColorInt int color) {
        mCircleImageView.setTextColor(color);
    }

    public float getCircleImageTextSize() {
        return mCircleImageView.getTextSize();
    }

    public void setCircleImageTextSize(float centerTextSize) {
        mCircleImageView.setTextSize(centerTextSize);
    }

    public String getCircleImageCenterText() {
        return mCircleImageView.getCenterText();
    }

    public void setCircleImageCenterText(String centerText) {
        mCircleImageView.setCenterText(centerText);
    }

    public float getCircleImageBgRadius() {
        return mCircleImageView.getBackgroundRadius();
    }

    public void setCircleImageBgRadius(float backgroundRadius) {
        mCircleImageView.setBackgroundRadius(backgroundRadius);
    }

    public int getCircleImageBgColor() {
        return mCircleImageView.getCircleBackgroundColor();
    }

    public void setCircleImageBgColor(@ColorInt int color) {
        mCircleImageView.setBackgroundColor(color);
    }

    public int getCircleImageBgStrokeWidth() {
        return mCircleImageView.getBackgroundStrokeWidth();
    }

    public void setCircleImageBgStrokeWidth(int strokeWidth) {
        mCircleImageView.setBackgroundStrokeWidth(strokeWidth);
    }

    public int getCircleImageBgStrokeColor() {
        return mCircleImageView.getBackgroundStrokeColor();
    }

    public void setCircleImageBgStrokeColor(@ColorInt int color) {
        mCircleImageView.setBackgroundStrokeColor(color);
    }

    public boolean isShowCircle() {
        return mCircleImageView.isShowCircle();
    }

    public void setShowCircle(boolean isShowCircle) {
        mCircleImageView.setShowCircle(isShowCircle);
    }

    public void setImage(Drawable image) {
        mCircleImageView.setImageDrawable(image);
    }

    public void setBackground(Drawable background) {
        mCircleImageViewLayout.setBackground(background);
    }
}

package com.zjclugger.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.zjclugger.lib.R;

import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ExtendLabelValueView extends JTextView {
    private View mView;
    //标签
    private TextView mLabelView;
    //文本
    private TextView mTextView;
    private TextView mMiddleView;
    private EditText mEditTextView;
    private ImageView mLeftImageView;
    private ImageView mRightImageView;
    private LinearLayout mLayout;
    private Map<String, Integer> styleableMap = new HashMap();

    public ExtendLabelValueView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.layout_extend_label_value_view, this, true);
        mLabelView = mView.findViewById(R.id.elvv_label);
        mTextView = mView.findViewById(R.id.elvv_value);
        mMiddleView = mView.findViewById(R.id.elvv_middle);
        mEditTextView = mView.findViewById(R.id.elvv_edit_text);
        mLeftImageView = mView.findViewById(R.id.elvv_image_left);
        mRightImageView = mView.findViewById(R.id.elvv_image_right);
        mLayout = mView.findViewById(R.id.elvv_layout);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.LabelValueView);
        if (attributes != null) {
            //Label text
            styleableMap.put(Styleable.TEXT, R.styleable.LabelValueView_label_text);
            styleableMap.put(Styleable.TEXT_SIZE, R.styleable.LabelValueView_label_text_size);
            styleableMap.put(Styleable.TEXT_COLOR, R.styleable.LabelValueView_label_text_color);
            styleableMap.put(Styleable.TEXT_WIDTH, R.styleable.LabelValueView_label_text_width);
            styleableMap.put(Styleable.TEXT_HEIGHT, R.styleable.LabelValueView_label_text_height);
            styleableMap.put(Styleable.TEXT_MIN_WIDTH,
                    R.styleable.LabelValueView_label_text_min_width);
            styleableMap.put(Styleable.TEXT_MIN_HEIGHT,
                    R.styleable.LabelValueView_label_text_min_height);
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

            //文本 text
            styleableMap.put(Styleable.TEXT, R.styleable.LabelValueView_value_text);
            styleableMap.put(Styleable.TEXT_SIZE, R.styleable.LabelValueView_value_text_size);
            styleableMap.put(Styleable.TEXT_COLOR, R.styleable.LabelValueView_value_text_color);
            styleableMap.put(Styleable.TEXT_WIDTH, R.styleable.LabelValueView_value_text_width);
            styleableMap.put(Styleable.TEXT_HEIGHT, R.styleable.LabelValueView_value_text_height);
            styleableMap.put(Styleable.TEXT_MIN_WIDTH,
                    R.styleable.LabelValueView_value_text_min_width);
            styleableMap.put(Styleable.TEXT_MIN_HEIGHT,
                    R.styleable.LabelValueView_value_text_min_height);
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
            initTextView(mTextView, attributes, styleableMap);
            styleableMap.clear();

            //middle text
            styleableMap.put(Styleable.TEXT, R.styleable.LabelValueView_middle_text);
            styleableMap.put(Styleable.TEXT_SIZE, R.styleable.LabelValueView_middle_text_size);
            styleableMap.put(Styleable.TEXT_COLOR, R.styleable.LabelValueView_middle_text_color);
            styleableMap.put(Styleable.TEXT_WIDTH, R.styleable.LabelValueView_middle_text_width);
            styleableMap.put(Styleable.TEXT_HEIGHT, R.styleable.LabelValueView_middle_text_height);
            styleableMap.put(Styleable.TEXT_MIN_WIDTH,
                    R.styleable.LabelValueView_middle_text_min_width);
            styleableMap.put(Styleable.TEXT_MIN_HEIGHT,
                    R.styleable.LabelValueView_middle_text_min_height);
            styleableMap.put(Styleable.TEXT_GRAVITY,
                    R.styleable.LabelValueView_middle_text_gravity);
            styleableMap.put(Styleable.TEXT_STYLE, R.styleable.LabelValueView_middle_text_style);
            styleableMap.put(Styleable.TEXT_VISIBILITY,
                    R.styleable.LabelValueView_middle_text_visibility);
            styleableMap.put(Styleable.TEXT_BACKGROUND,
                    R.styleable.LabelValueView_middle_text_background);
            styleableMap.put(Styleable.VIEW_WEIGHT, R.styleable.LabelValueView_middle_text_weight);
            //border
            styleableMap.put(Styleable.TEXT_SOLID_COLOR,
                    R.styleable.LabelValueView_middle_text_solid_color);
            styleableMap.put(Styleable.TEXT_BORDER_WIDTH,
                    R.styleable.LabelValueView_middle_text_border_width);
            styleableMap.put(Styleable.TEXT_BORDER_COLOR,
                    R.styleable.LabelValueView_middle_text_border_color);
            styleableMap.put(Styleable.TEXT_CORNER_RADIUS,
                    R.styleable.LabelValueView_middle_text_corner_radius);
            styleableMap.put(Styleable.TEXT_TOP_LEFT_RADIUS,
                    R.styleable.LabelValueView_middle_text_topLeft_radius);
            styleableMap.put(Styleable.TEXT_TOP_RIGHT_RADIUS,
                    R.styleable.LabelValueView_middle_text_topRight_radius);
            styleableMap.put(Styleable.TEXT_BOTTOM_LEFT_RADIUS,
                    R.styleable.LabelValueView_middle_text_bottomLeft_radius);
            styleableMap.put(Styleable.TEXT_BOTTOM_RIGHT_RADIUS,
                    R.styleable.LabelValueView_middle_text_bottomRight_radius);
            initTextView(mMiddleView, attributes, styleableMap);
            styleableMap.clear();

            //left image
            styleableMap.put(Styleable.IMAGE_SCR, R.styleable.LabelValueView_left_image_src);
            styleableMap.put(Styleable.IMAGE_DRAWABLE,
                    R.styleable.LabelValueView_left_image_drawable);
            styleableMap.put(Styleable.IMAGE_ROTATION,
                    R.styleable.LabelValueView_left_image_rotation);
            styleableMap.put(Styleable.IMAGE_WIDTH,
                    R.styleable.LabelValueView_left_image_width);
            styleableMap.put(Styleable.IMAGE_HEIGHT,
                    R.styleable.LabelValueView_left_image_height);
            styleableMap.put(Styleable.IMAGE_SCALE_TYPE,
                    R.styleable.LabelValueView_left_image_scale_type);
            initImageView(mLeftImageView, attributes, styleableMap);
            styleableMap.clear();

            //right image
            styleableMap.put(Styleable.IMAGE_SCR, R.styleable.LabelValueView_right_image_src);
            styleableMap.put(Styleable.IMAGE_DRAWABLE,
                    R.styleable.LabelValueView_right_image_drawable);
            styleableMap.put(Styleable.IMAGE_ROTATION,
                    R.styleable.LabelValueView_right_image_rotation);
            styleableMap.put(Styleable.IMAGE_WIDTH,
                    R.styleable.LabelValueView_right_image_width);
            styleableMap.put(Styleable.IMAGE_HEIGHT,
                    R.styleable.LabelValueView_right_image_height);
            styleableMap.put(Styleable.IMAGE_SCALE_TYPE,
                    R.styleable.LabelValueView_right_image_scale_type);
            initImageView(mRightImageView, attributes, styleableMap);
            styleableMap.clear();

            //edit value text
            styleableMap.put(Styleable.TEXT, R.styleable.LabelValueView_edit_text);
            styleableMap.put(Styleable.TEXT_HINT, R.styleable.LabelValueView_edit_text_hint);
            styleableMap.put(Styleable.TEXT_SIZE, R.styleable.LabelValueView_edit_text_size);
            styleableMap.put(Styleable.TEXT_COLOR, R.styleable.LabelValueView_edit_text_color);
            styleableMap.put(Styleable.TEXT_WIDTH, R.styleable.LabelValueView_edit_text_width);
            styleableMap.put(Styleable.TEXT_HEIGHT, R.styleable.LabelValueView_edit_text_height);
            styleableMap.put(Styleable.TEXT_MIN_WIDTH,
                    R.styleable.LabelValueView_edit_text_min_width);
            styleableMap.put(Styleable.TEXT_MIN_HEIGHT,
                    R.styleable.LabelValueView_edit_text_min_height);
            styleableMap.put(Styleable.TEXT_GRAVITY, R.styleable.LabelValueView_edit_text_gravity);
            styleableMap.put(Styleable.TEXT_STYLE, R.styleable.LabelValueView_edit_text_style);
            styleableMap.put(Styleable.TEXT_VISIBILITY,
                    R.styleable.LabelValueView_edit_text_visibility);
            styleableMap.put(Styleable.TEXT_BACKGROUND,
                    R.styleable.LabelValueView_edit_text_background);
            styleableMap.put(Styleable.VIEW_WEIGHT, R.styleable.LabelValueView_edit_text_weight);
            //edit border
            styleableMap.put(Styleable.TEXT_SOLID_COLOR,
                    R.styleable.LabelValueView_edit_text_solid_color);
            styleableMap.put(Styleable.TEXT_BORDER_WIDTH,
                    R.styleable.LabelValueView_edit_text_border_width);
            styleableMap.put(Styleable.TEXT_BORDER_COLOR,
                    R.styleable.LabelValueView_edit_text_border_color);
            styleableMap.put(Styleable.TEXT_CORNER_RADIUS,
                    R.styleable.LabelValueView_edit_text_corner_radius);
            styleableMap.put(Styleable.TEXT_TOP_LEFT_RADIUS,
                    R.styleable.LabelValueView_edit_text_topLeft_radius);
            styleableMap.put(Styleable.TEXT_TOP_RIGHT_RADIUS,
                    R.styleable.LabelValueView_edit_text_topRight_radius);
            styleableMap.put(Styleable.TEXT_BOTTOM_LEFT_RADIUS,
                    R.styleable.LabelValueView_edit_text_bottomLeft_radius);
            styleableMap.put(Styleable.TEXT_BOTTOM_RIGHT_RADIUS,
                    R.styleable.LabelValueView_edit_text_bottomRight_radius);
            initTextView(mEditTextView, attributes, styleableMap);
            styleableMap.clear();
            attributes.recycle();
        }
    }

    private void initImageView(ImageView view, TypedArray attributes,
                               Map<String, Integer> styleableMap) {
        Drawable leftImageSrc =
                attributes.getDrawable(styleableMap.get(Styleable.IMAGE_SCR));
        if (leftImageSrc != null) {
            view.setVisibility(VISIBLE);
            view.setImageDrawable(leftImageSrc);
        }
        int leftImageBg =
                attributes.getResourceId(styleableMap.get(Styleable.IMAGE_DRAWABLE), -1);
        if (leftImageBg != -1) {
            view.setVisibility(VISIBLE);
            view.setBackgroundResource(leftImageBg);
        }

        float rotation = attributes.getFloat(styleableMap.get(Styleable.IMAGE_ROTATION), 0f);
        if (rotation > 0) {
            view.setRotation(rotation);
        }

        setWidth(view, attributes.getDimensionPixelSize(styleableMap.get(Styleable.IMAGE_WIDTH),
                0));
        setHeight(view, attributes.getDimensionPixelSize(styleableMap.get(Styleable.IMAGE_HEIGHT)
                , 0));
        setScaleType(view, attributes.getInt(styleableMap.get(Styleable.IMAGE_SCALE_TYPE), -1));
    }

    private void setScaleType(ImageView imageView, int scaleType) {
        switch (scaleType) {
            case 0:
                imageView.setScaleType(ImageView.ScaleType.MATRIX);
                break;
            case 1:
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 2:
                imageView.setScaleType(ImageView.ScaleType.FIT_START);
                break;
            case 3:
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                break;
            case 4:
                imageView.setScaleType(ImageView.ScaleType.FIT_END);
                break;
            case 5:
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                break;
            case 6:
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case 7:
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                break;
            default:
                break;
        }
    }

    public void setLeftImage(@DrawableRes int imageResId) {
        if (mLeftImageView != null) {
            mLeftImageView.setVisibility(VISIBLE);
            mLeftImageView.setImageResource(imageResId);
        }
    }

    public void setRightImage(@DrawableRes int imageResId) {
        if (mRightImageView != null) {
            mRightImageView.setVisibility(VISIBLE);
            mRightImageView.setImageResource(imageResId);
        }
    }

    public ImageView getLeftImageView() {
        return mLeftImageView;
    }

    public ImageView getRightImageView() {
        return mRightImageView;
    }

    public void setLabelText(String labelText) {
        if (mLabelView != null) {
            mLabelView.setText(labelText);
        }
    }

    public void setValueText(String valueText) {
        if (mTextView != null) {
            mTextView.setText(valueText);
        }
    }

    public void setLabelText(@StringRes int labelText) {
        if (mLabelView != null) {
            mLabelView.setText(labelText);
        }
    }

    public void setValueText(@StringRes int valueText) {
        if (mTextView != null) {
            mTextView.setText(valueText);
        }
    }

    public void setLabelTextVisibility(int visibility) {
        if (mLabelView != null) {
            mLabelView.setVisibility(visibility);
        }
    }

    public void setValueTextVisibility(int visibility) {
        if (mTextView != null) {
            mTextView.setVisibility(visibility);
        }
    }

    public TextView getLabelView() {
        return mLabelView;
    }

    public TextView getValueView() {
        return mTextView;
    }

    public TextView getMiddleView() {
        return mMiddleView;
    }

    public void setMiddleViewBorderStyle(GradientDrawable borderStyle) {
        mMiddleView.setBackground(borderStyle);
    }

    public void setEditTextViewBorderStyle(GradientDrawable borderStyle) {
        mEditTextView.setBackground(borderStyle);
    }

    public void setText(String labelText, String middleText, String valueText) {
        setLabelText(labelText);
        if (null != mMiddleView) {
            mMiddleView.setText(middleText);
        }
        setValueText(valueText);
    }

    public void setEditTextValue(String value) {
        mEditTextView.setText(value);
    }

    public String getEditTextValue() {
        return mEditTextView.getText().toString();
    }

    public EditText getEditTextView() {
        return mEditTextView;
    }

    public void setEditTextReadOnly() {
        mEditTextView.setCursorVisible(false);
        mEditTextView.setFocusable(false);
        mEditTextView.setFocusableInTouchMode(false);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mLayout.setOnClickListener(l);
    }
}

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
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.zjclugger.lib.R;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ImageTextView extends JTextView {
    private TextView mTextView;
    private ImageView mImageView;

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_image_text_view, this, true);
        //ButterKnife.bind(this,view);
        mTextView = view.findViewById(R.id.image_text);
        mImageView = view.findViewById(R.id.image_view);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView);
        if (attributes != null) {
            //image view
            Drawable imageSrc = attributes.getDrawable(R.styleable.ImageTextView_itv_icon_src);
            if (imageSrc != null) {
                mImageView.setImageDrawable(imageSrc);
            }
            int imageBackground =
                    attributes.getResourceId(R.styleable.ImageTextView_itv_icon_drawable, -1);
            if (imageBackground != -1) {
                mImageView.setBackgroundResource(imageBackground);
            }

            //attributes.getDimensionPixelSize(styleableMap.get(Styleable.TEXT_WIDTH), 0));
            setWidth(mImageView,
                    attributes.getDimensionPixelSize(R.styleable.ImageTextView_itv_image_width,
                            0));
            setHeight(mImageView,
                    attributes.getDimensionPixelSize(R.styleable.ImageTextView_itv_image_height,
                            0));

            int imageMargins =
                    attributes.getDimensionPixelSize(R.styleable.ImageTextView_itv_image_margins,
                            0);
            int imageLeftMargin =
                    attributes.getDimensionPixelSize(R.styleable.ImageTextView_itv_image_margin_left,
                            imageMargins);
            int imageTopMargin =
                    attributes.getDimensionPixelSize(R.styleable.ImageTextView_itv_image_margin_top,
                            imageMargins);
            int imageRightMargin =
                    attributes.getDimensionPixelSize(R.styleable.ImageTextView_itv_image_margin_right,
                            imageMargins);
            int imageBottomMargin =
                    attributes.getDimensionPixelSize(R.styleable.ImageTextView_itv_image_margin_bottom,
                            imageMargins);
            setMargins(mImageView, imageLeftMargin, imageTopMargin, imageRightMargin,
                    imageBottomMargin);

            //bottom text
            mTextView.setTextColor(attributes.getColor(R.styleable.ImageTextView_itv_text_color,
                    Color.BLACK));
            String bottomText = attributes.getString(R.styleable.ImageTextView_itv_text);

            int textMargins =
                    attributes.getDimensionPixelSize(R.styleable.ImageTextView_itv_text_margins,
                            0);
            int textLeftMargin =
                    attributes.getDimensionPixelSize(R.styleable.ImageTextView_itv_text_margin_left,
                            textMargins);
            int textTopMargin =
                    attributes.getDimensionPixelSize(R.styleable.ImageTextView_itv_text_margin_top,
                            textMargins);
            int textRightMargin =
                    attributes.getDimensionPixelSize(R.styleable.ImageTextView_itv_text_margin_right,
                            textMargins);
            int textBottomMargin =
                    attributes.getDimensionPixelSize(R.styleable.ImageTextView_itv_text_margin_bottom,
                            textMargins);
            setMargins(mTextView, textLeftMargin, textTopMargin, textRightMargin, textBottomMargin);
            if (!TextUtils.isEmpty(bottomText)) {
                mTextView.setText(bottomText);
            } else {
                //
            }
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    attributes.getDimensionPixelSize(R.styleable.ImageTextView_itv_text_size, 14));
            attributes.recycle();
        }
    }

    public void setImageText(String bottomText) {
        if (mTextView != null) {
            mTextView.setText(bottomText);
        }
    }

    public void setImageText(@StringRes int bottomTextResId) {
        if (mTextView != null) {
            mTextView.setText(bottomTextResId);
        }
    }

    public void setImageView(@DrawableRes int imageResId) {
        if (mImageView != null) {
            mImageView.setImageResource(imageResId);
        }
    }
}
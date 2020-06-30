package com.zjclugger.router.service;

import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.annotation.ColorInt;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 二维码服务<br>
 * Created by King.Zi.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public interface IQRCodeService extends IProvider {
    /**
     * 构建生成二维码图片的参数
     */
     class Builder {
        private final static int QR_MIN_WIDTH = 150;
        private final static int QR_MIN_HEIGHT = 150;
        private int mWidth = 200;
        private int mHeight = 200;
        private String mCharacterSet = "UTF-8";
        private String mErrorCorrectionLevel = ErrorCorrectionLevel.H;
        private int mMargin = 2;
        private int mLineColor = Color.BLACK;
        private int mBackgroundColor = Color.WHITE;
        private Bitmap mLogoBitmap;
        private float mLogoPercent = 0.2f;

        /**
         * 设置二维码的大小
         *
         * @param width
         * @param height
         * @return
         */
        public Builder setSize(int width, int height) {
            if (width < QR_MIN_WIDTH || height < QR_MIN_HEIGHT) {
                mWidth = QR_MIN_WIDTH;
                mHeight = QR_MIN_HEIGHT;
            } else {
                mWidth = width;
                mHeight = height;
            }
            return this;
        }

        public Builder setCharacterSet(String characterSet) {
            mCharacterSet = characterSet;
            return this;
        }

        public Builder setMargin(int margin) {
            if (0 < margin && margin < 5) {
                mMargin = margin;
            }
            return this;
        }

        /**
         * 容错等级，详情见{@link ErrorCorrectionLevel}
         *
         * @param errorCorrectionLevel
         * @return
         */
        public Builder setErrorCorrectionLevel(@ErrorCorrectionLevel String errorCorrectionLevel) {
            mErrorCorrectionLevel = errorCorrectionLevel;
            return this;
        }

        /**
         * 二维码线的颜色
         *
         * @param lineColor
         * @return
         */
        public Builder setLineColor(@ColorInt int lineColor) {
            mLineColor = lineColor;
            return this;
        }

        /**
         * 二级码背景颜色
         *
         * @param backgroundColor
         * @return
         */
        public Builder setBackgroundColor(@ColorInt int backgroundColor) {
            mBackgroundColor = backgroundColor;
            return this;
        }

        /**
         * 二维码Logo图片
         *
         * @param logoBitmap
         * @return
         */
        public Builder setLogoBitmap(Bitmap logoBitmap) {
            mLogoBitmap = logoBitmap;
            return this;
        }

        /**
         * 二维码Logo图片所在比例
         *
         * @param logoPercent
         * @return
         */
        public Builder setLogoPercent(float logoPercent) {
            if (logoPercent < 0.5f) {
                mLogoPercent = logoPercent;
            }
            return this;
        }

        public int getWidth() {
            return mWidth;
        }

        public int getHeight() {
            return mHeight;
        }

        public String getCharacterSet() {
            return mCharacterSet;
        }

        public String getErrorCorrectionLevel() {
            return mErrorCorrectionLevel;
        }

        public int getMargin() {
            return mMargin;
        }

        public int getLineColor() {
            return mLineColor;
        }

        public int getBackgroundColor() {
            return mBackgroundColor;
        }

        public Bitmap getLogoBitmap() {
            return mLogoBitmap;
        }

        public float getLogoPercent() {
            return mLogoPercent;
        }
    }


    /**
     * 构建生成二维码图片的参数
     *
     * @return
     */
    Builder builderParameter();


    /**
     * 生成二维码
     */
    Bitmap createQRCodeImage(String QRString);
}
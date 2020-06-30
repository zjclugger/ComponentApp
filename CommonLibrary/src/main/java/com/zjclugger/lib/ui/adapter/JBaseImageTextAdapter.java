package com.zjclugger.lib.ui.adapter;

import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

/**
 * 文本图片<br>
 * Created by King.Zi on 2020/4/22.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public abstract class JBaseImageTextAdapter<T, K extends JBaseViewHolder> extends BaseQuickAdapter<T, K> {
    protected int mImageWidth = LinearLayout.LayoutParams.WRAP_CONTENT;
    protected int mImageHeight = LinearLayout.LayoutParams.WRAP_CONTENT;
    protected int mLayoutWidth = LinearLayout.LayoutParams.WRAP_CONTENT;
    protected int mLayoutHeight = LinearLayout.LayoutParams.WRAP_CONTENT;
    protected boolean mIsPreview;

    public JBaseImageTextAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public JBaseImageTextAdapter(@Nullable List<T> data) {
        super(data);
    }

    public JBaseImageTextAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void setLayoutSize(int width, int height) {
        mLayoutWidth = width;
        mLayoutHeight = height;
    }

    public void setImageSize(int width, int height) {
        mImageWidth = width;
        mImageHeight = height;
    }

    public void setPreview(boolean isPreview) {
        mIsPreview = isPreview;
    }
}
package com.zjclugger.buyer.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.ModuleResult;
import com.zjclugger.lib.ui.adapter.JBaseImageTextAdapter;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;
import com.zjclugger.router.utils.ARouterUtils;

import java.util.List;


/**
 * <br>
 * Created by King.Zi on 2020/4/10.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ModuleAdapter extends JBaseImageTextAdapter<ModuleResult, JBaseViewHolder> {

    public ModuleAdapter(List<ModuleResult> data) {
        super(R.layout.item_image_text, data);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, ModuleResult item) {
        LinearLayout rootLayout = holder.getView(R.id.image_text_layout);
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(mLayoutWidth, mLayoutHeight));
        //TODO:如果一直有界面显示高度偶发不正常的问题时，可尝试设置根布局宽高
        final ImageView imageView = holder.getView(R.id.image_view);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(mImageWidth, mImageHeight));
        if (!TextUtils.isEmpty(item.getUrl())) {
            ImageUtils.loadImage(mContext, imageView, item.getUrl());
            if (mIsPreview) {
                imageView.setOnClickListener(ARouterUtils.getPreviewImageListener(mContext,
                        item.getUrl()));
            }
        }
        holder.setText(R.id.image_text, item.getName());
    }
}
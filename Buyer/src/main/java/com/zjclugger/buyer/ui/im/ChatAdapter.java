package com.zjclugger.buyer.ui.im;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.IMResult;
import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.view.CircleImageView;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

/**
 * 聊天<br>
 * Created by King.Zi on 2020/5/11.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ChatAdapter extends JBaseQuickAdapter<IMResult, JBaseViewHolder> {
    public ChatAdapter(int layoutResId, List<IMResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, IMResult item) {
        LinearLayout layout=holder.getView(R.id.chat_message_layout);
        TextView serviceNameView = holder.getView(R.id.service_name);
        CircleImageView serviceHeadImage = holder.getView(R.id.civ_shop_service_head);
        CircleImageView userHeadImage = holder.getView(R.id.civ_user_head);
        TextView messageView = holder.getView(R.id.message_content);

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        if (!item.isUserMessage()) {
            //service message
            serviceHeadImage.setVisibility(View.VISIBLE);
            userHeadImage.setVisibility(View.INVISIBLE);
            ImageUtils.loadImage(mContext, serviceHeadImage, item.getHeadImageUrl());
            serviceNameView.setVisibility(View.VISIBLE);
            serviceNameView.setText(item.getName());
            layoutParams.gravity = Gravity.LEFT;
            layoutParams.setMargins(0, 0, ViewUtils.dp2px(mContext, 30), 0);
        } else {
            serviceHeadImage.setVisibility(View.INVISIBLE);
            userHeadImage.setVisibility(View.VISIBLE);
            ImageUtils.loadImage(mContext, userHeadImage, item.getHeadImageUrl());
            serviceNameView.setVisibility(View.GONE);
            layoutParams.gravity = Gravity.RIGHT;
            layoutParams.setMargins(ViewUtils.dp2px(mContext, 30), 0, 0, 0);
        }

        layout.setLayoutParams(layoutParams);
        messageView.setText(item.getMessage());
    }
}

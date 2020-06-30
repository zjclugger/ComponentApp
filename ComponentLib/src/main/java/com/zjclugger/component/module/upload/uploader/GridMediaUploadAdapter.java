package com.zjclugger.component.module.upload.uploader;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.zjclugger.component.R;
import com.zjclugger.component.module.upload.uploader.entity.UploadLocalMedia;
import com.zjclugger.lib.ui.adapter.JBaseQuickAdapter;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class GridMediaUploadAdapter extends JBaseQuickAdapter<UploadLocalMedia,
        JBaseViewHolder> {

    public GridMediaUploadAdapter(int layoutResId, List<UploadLocalMedia> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull JBaseViewHolder holder, UploadLocalMedia item) {
        Glide.with(mContext)
                .load(item.getPath())
                .into((ImageView) holder.getView(R.id.upload_file));
        if (item.isIdentified()) {
            holder.setVisibility(R.id.err_result_layout, false);
            holder.setVisibility(R.id.upload_cloud, true);
        } else {
            holder.setVisibility(R.id.err_result_layout, true);
            //retry
            final ImageView retry = holder.getView(R.id.upload_retry);
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnItemClickListener(retry, holder.getAdapterPosition());
                }
            });

            //delete
            final ImageView delete = holder.getView(R.id.upload_trash);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除服务器记录
                    setOnItemClickListener(delete, holder.getAdapterPosition());
                }
            });
        }
    }
}
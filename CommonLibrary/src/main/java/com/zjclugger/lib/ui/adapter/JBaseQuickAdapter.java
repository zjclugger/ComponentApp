package com.zjclugger.lib.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.R;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

/**
 * 列表适配器基类 <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public abstract class JBaseQuickAdapter<T, K extends JBaseViewHolder> extends BaseQuickAdapter<T, K> {

    private ViewGroup mEmptyViewGroup;
    private View.OnClickListener mOnErrorViewClickListener;

    public JBaseQuickAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public JBaseQuickAdapter(@Nullable List<T> data) {
        super(data);
    }

    public JBaseQuickAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 设置空视图(需要使用bindToRecyclerView方法建立view与adapter的关联)
     *
     * @param isEmpty 是否为空
     */
    public void setEmptyView(boolean isEmpty) {
        if (null != mEmptyViewGroup) {
            View view =
                    LayoutInflater.from(mEmptyViewGroup.getContext()).inflate(R.layout.error_view
                    , mEmptyViewGroup, false);
            TextView tvDesc = view.findViewById(R.id.tv_desc);
            ImageView ivDesc = view.findViewById(R.id.iv_desc);
            if (isEmpty) {
                //no data
                tvDesc.setText(R.string.no_data);
                ivDesc.setImageResource(R.mipmap.img_no_data);
            } else if (null != mOnErrorViewClickListener) {
                tvDesc.setOnClickListener(mOnErrorViewClickListener);
                ivDesc.setOnClickListener(mOnErrorViewClickListener);
            }
            setEmptyView(view);
        }
    }

    /**
     * 设置出错视图的点击事件
     *
     * @param listener
     */
    public void setOnErrorViewClickListener(View.OnClickListener listener) {
        mOnErrorViewClickListener = listener;
    }

    /**
     * 数据更新
     */
    public void notifyDataChanged() {
        notifyDataSetChanged();
        if (mData.isEmpty()) {
            setEmptyView(true);
        }
    }

    /**
     * 绑定适配器到RecyclerView
     *
     * @param recyclerView
     */
    public void bindToRecyclerView(RecyclerView recyclerView) {
        super.bindToRecyclerView(recyclerView);
        mEmptyViewGroup = (ViewGroup) recyclerView.getParent();
    }

    /**
     * 设置单项中的某个View的点击事件
     *
     * @param view
     * @param position
     */
    public void setOnItemClickListener(View view, int position) {
        if (getOnItemClickListener() != null) {
            getOnItemClickListener().onItemClick(this, view, position);
        }
    }
}

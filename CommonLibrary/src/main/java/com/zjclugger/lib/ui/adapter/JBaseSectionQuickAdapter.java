package com.zjclugger.lib.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.zjclugger.lib.R;
import com.zjclugger.lib.view.recyclerview.JBaseViewHolder;

import java.util.List;

/**
 * 列表适配器基类 <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public abstract class JBaseSectionQuickAdapter<T extends JSectionEntity,
        K extends JBaseViewHolder> extends BaseSectionQuickAdapter<T, K> {

    private ViewGroup mEmptyViewGroup;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public JBaseSectionQuickAdapter(int layoutResId, int sectionHeadResId, List<T> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(K helper, T item) {

    }

    @Override
    protected void convert(@NonNull K helper, T item) {

    }

    /**
     * 设置空视图(需要使用bindToRecyclerView方法建立view与adapter的关联)
     *
     * @param isGetFailed 是否为获取数据失败
     */
    public void setEmptyView(boolean isGetFailed) {
        if (null != mEmptyViewGroup) {
            setEmptyView(isGetFailed ? R.layout.error_view : R.layout.empty_view, mEmptyViewGroup);
        }
    }

    /**
     * 数据更新
     */
    public void notifyDataChanged() {
        notifyDataSetChanged();
        if (mData.isEmpty()) {
            setEmptyView(false);
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
}

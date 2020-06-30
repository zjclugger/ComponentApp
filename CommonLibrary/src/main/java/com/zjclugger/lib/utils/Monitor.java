package com.zjclugger.lib.utils;

import android.view.KeyEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.zjclugger.lib.api.entity.WrapperEntity;

/**
 * 监听器
 * Created by King.Zi on 2016/10/12.<br>
 */
public class Monitor {

    private Monitor() {
    }

    public interface ResultCallback<T> {
        void onSuccess(T t);

        void onFailure(WrapperEntity error);
    }

    /**
     * 超时监听器
     */
    public interface TimeoutListener {
        /**
         * 超时时触发
         */
        void onTimeout();
    }

    /**
     * 窗口关闭时的监听器
     *
     * @param <T>
     */
    public interface OnCloseListener<T extends Object> {
        /**
         * 关闭时调用
         *
         * @param postBackData 回传数据
         */
        void onClose(T postBackData);
    }

    /**
     * 连续点击监听器
     */
    public interface MultipleHitsListener {
        /**
         * 完成连续点击次数
         */
        void onMultipleHitsCompleted();
    }

    /**
     * Fragment key listener
     */
    public interface FragmentOnKeyListener {
        /**
         * 监听按键事件
         *
         * @param keyCode
         * @param event
         * @return
         */
        boolean onKeyDown(int keyCode, KeyEvent event);
    }

    public interface OnItemClickListener {

        /**
         * Callback method to be invoked when an item in this RecyclerView has
         * been clicked.
         *
         * @param adapter  the adapter
         * @param view     The itemView within the RecyclerView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         */
        void onItemClick(RecyclerView.Adapter adapter, View view, int position);
    }

    public interface onPreparedListener {
        /**
         * 已经准备完成
         */
        void onPrepared();
    }

    public interface CustomListener {
        void customLayout(View v);
    }
}

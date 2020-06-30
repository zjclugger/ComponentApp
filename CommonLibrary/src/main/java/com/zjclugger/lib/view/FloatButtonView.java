package com.zjclugger.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zjclugger.basiclib.Log;
import com.zjclugger.lib.R;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class FloatButtonView extends RelativeLayout {
    private int mTopImageRes;
    private ImageView mTopImageView;
    private Context mContext;
    private int mScrollY;

    public FloatButtonView(Context context) {
        this(context, null);
    }

    public FloatButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.FloatButtonView);
        mTopImageRes = typedArray.getResourceId(R.styleable.FloatButtonView_fbv_image_src,
                R.mipmap.ic_go_top);
        typedArray.recycle();

        initView();
    }

    private void initView() {
        mTopImageView = new ImageView(mContext);
        mTopImageView.setImageResource(mTopImageRes);
        LayoutParams params = new LayoutParams(dp2px(50), dp2px(50));
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mTopImageView.setLayoutParams(params);
        addView(mTopImageView);
    }

    /**
     * 绑定返回顶部按钮到RecyclerView
     *
     * @param recyclerView
     */
    public void bindToRecyclerView(final RecyclerView recyclerView) {
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    //滑动中
                    mTopImageView.setVisibility(GONE);
                } else {
                    if (manager.findFirstVisibleItemPosition() > 0) {
                        //第一个可见的非第一个，则显示【置顶】按钮
                        mTopImageView.setVisibility(VISIBLE);
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mScrollY += dy;

                //最后一个可见数量的位置
                //int lastItemPosition = manager.findLastVisibleItemPosition();
                /*mTvProgress.setText(String.valueOf(lastItemPosition));
                mTvMax.setText(String.valueOf(manager.getItemCount()));*/
                //或者判断mScrollY>行间距（可传递过来）
                if (manager.findFirstVisibleItemPosition() > 0) {
                    mTopImageView.setVisibility(VISIBLE);
                } else {
                    mTopImageView.setVisibility(GONE);
                }

                Log.d("onScrolled,scrollY=" + mScrollY + ",fvi=" + manager.findFirstVisibleItemPosition());
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        //设置点击事件
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView != null) {
                    // recyclerView.scrollToPosition(0);//快速滚动
                    recyclerView.smoothScrollToPosition(0);//缓慢滚动
                }
            }
        });
    }

    private int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, mContext.getResources().getDisplayMetrics());
    }
}
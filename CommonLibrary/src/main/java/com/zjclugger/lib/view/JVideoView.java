package com.zjclugger.lib.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class JVideoView extends VideoView {

    public JVideoView(Context context) {
        super(context);
    }

    public JVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重新计算一下高度
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0,
                heightMeasureSpec));
    }

    @Override
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
        super.setOnPreparedListener(l);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
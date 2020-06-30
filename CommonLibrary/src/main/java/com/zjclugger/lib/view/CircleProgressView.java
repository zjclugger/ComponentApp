package com.zjclugger.lib.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.zjclugger.lib.R;

import java.text.DecimalFormat;

/**
 * 圆形进度视图<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class CircleProgressView extends View {
    private Context mContext;
    private int mCircleStrokeWidth;
    private int mProgressStrokeWidth;
    private int mCircleColor;
    private int mProgressColor;
    private boolean mIsShowProgressText;
    private int mCenterTextColor;
    private int mCenterTextSize;
    private int mSecondTextColor;
    private int mSecondTextSize;
    private String mSecondText;
    private Paint mProgressPaint;
    private Paint mCyclePaint;
    private Paint mTextPaint;
    private Paint mSecondTextPaint;
    //圆x,y坐标
    private float mCenterX;
    private float mCenterY;
    //圆半径
    private float mRadius;
    //进度
    private float mProgress;
    private float mCurrentProgress;
    private DecimalFormat mDecimalFormat = new DecimalFormat("#.##");
    //动画执行时间
    private int duration = 1000;
    //园形所在矩形
    private RectF rectF = new RectF();
    private ValueAnimator valueAnimator;

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setAttributeSet(attrs);
        initPaint();
    }

    private void setAttributeSet(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,
                R.styleable.CircleProgressView);
        mCircleStrokeWidth =
                typedArray.getDimensionPixelOffset(R.styleable.CircleProgressView_cpv_circle_stroke_width, 10);
        mProgressStrokeWidth =
                typedArray.getDimensionPixelOffset(R.styleable.CircleProgressView_cpv_progress_stroke_width, 10);
        mIsShowProgressText =
                typedArray.getBoolean(R.styleable.CircleProgressView_cpv_show_progress_text, false);
        mCenterTextColor =
                typedArray.getColor(R.styleable.CircleProgressView_cpv_progress_text_color,
                        Color.BLACK);
        mCenterTextSize =
                typedArray.getDimensionPixelOffset(R.styleable.CircleProgressView_cpv_progress_text_size, 14);
        mSecondTextColor =
                typedArray.getColor(R.styleable.CircleProgressView_cpv_second_text_color,
                        Color.BLACK);
        mSecondTextSize =
                typedArray.getDimensionPixelOffset(R.styleable.CircleProgressView_cpv_second_text_size, 14);
        mSecondText = typedArray.getString(R.styleable.CircleProgressView_cpv_second_text);
        mCircleColor = typedArray.getColor(R.styleable.CircleProgressView_cpv_circle_color,
                Color.GRAY);
        mProgressColor = typedArray.getColor(R.styleable.CircleProgressView_cpv_progress_color,
                Color.RED);
        typedArray.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //初始化进度和圆圈画笔
        mProgressPaint = getPaint(mProgressStrokeWidth, mProgressColor);
        mCyclePaint = getPaint(mCircleStrokeWidth, mCircleColor);

        //初始化文字画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mCenterTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mCenterTextSize);
        mTextPaint.setAntiAlias(true);
        //第二文字画笔
        mSecondTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondTextPaint.setColor(mSecondTextColor);
        mSecondTextPaint.setTextAlign(Paint.Align.CENTER);
        mSecondTextPaint.setTextSize(mSecondTextSize);
        mSecondTextPaint.setAntiAlias(true);
    }

    private Paint getPaint(int width, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(width);
        // Paint.Style.FILL:填充内部  Paint.Style.FILL_AND_STROKE:填充内部和描边Paint.Style.STROKE :描边
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setAntiAlias(true); // 扛锯齿
        paint.setStrokeCap(Paint.Cap.ROUND); // 两端是圆角
        return paint;
    }

    /**
     * 初始化动画
     */
    private void initAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0, mProgress);
        valueAnimator.setDuration(duration);
        valueAnimator.setStartDelay(1000);
        //  ——AccelerateInterpolator：动画从开始到结束，变化率是一个加速的过程。
        //——DecelerateInterpolator：动画从开始到结束，变化率是一个减速的过程。
        //——CycleInterpolator：动画从开始到结束，变化率是循环给定次数的正弦曲线。
        // ——AccelerateDecelerateInterpolator：动画从开始到结束，变化率是先加速后减速的过程。
        //——LinearInterpolator：动画从开始到结束，变化率是线性变化。
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { //
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                mProgress = value;
                mCurrentProgress = value * 360 / 100;
                invalidate();
            }
        });
    }

    /**
     * view发生改变的时候调用
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        mRadius = Math.min(w, h) / 2 - Math.max(mCircleStrokeWidth, mProgressStrokeWidth); //
        rectF.set(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mCyclePaint);
        canvas.drawArc(rectF, 270, mCurrentProgress, false, mProgressPaint);
        if (mIsShowProgressText) {
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            int baseline =
                    (int) ((rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2);

            if (!TextUtils.isEmpty(mSecondText)) {
                baseline = baseline - mCenterTextSize / 2;
                canvas.drawText(mSecondText, rectF.centerX(), baseline + mCenterTextSize,
                        mSecondTextPaint);
            }
            canvas.drawText(mDecimalFormat.format(mProgress) + "%", rectF.centerX(), baseline,
                    mTextPaint);
        }
    }

    /**
     * 设置动画
     *
     * @param progress
     */
    public void setProgress(float progress) {
        this.mProgress = progress;
        initAnimator();
    }

    /**
     * 开始动画
     */
    public void startAnimation() {
        valueAnimator.start();
    }

    /**
     * 结束动画
     */
    public void stopAnimation() {
        if(null != valueAnimator) {
            valueAnimator.end();
        }
    }
}
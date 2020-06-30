package com.zjclugger.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.zjclugger.lib.R;

import java.math.BigDecimal;

public class DialProgressView extends View {
    private final int RADIUS_DEFAULT = 100;
    private final float DEGREES_DEFAULT = 10;
    private final int PROGRESS_MAX = 100;

    // 内圆画笔
    private Paint mInnerCirclePaint;
    // 刻度画笔
    private Paint mDialPaint;
    // 圆环画笔
    private Paint mRingPaint;
    // 内圆字体画笔
    private Paint mTextPaint;
    // 内圆的颜色
    private int mInnerCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 最小半径
    private float mMinRadius;
    // 圆环半径
    private float mRingRadius;
    //内圆半径
    private float mInnerRingRadius;
    // 圆环宽度
    private float mRingStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTextWidth;
    // 字的高度
    private float mTextHeight;
    // 字体颜色
    private int mTextColor;
    private float mTextSize;
    // 当前进度
    private int mProgress;
    //进度值
    private String mProgressText;
    //刻度颜色
    private int mDialColor;
    //刻度线宽
    private float mDialStrokeWidth;
    //刻度开始Y值
    private float mDialStartY;
    //刻度结束Y值
    private float mDialStopY;
    //起始角度
    private float mStartAngle;
    //刻度起始索引
    private float mDialStartIndex;

    public DialProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
    }

    //属性
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.DialProgressView, 0, 0);
        mMinRadius = typeArray.getDimension(R.styleable.DialProgressView_dpv_min_radius,
                RADIUS_DEFAULT);
        mRingStrokeWidth = typeArray.getDimension(R.styleable.DialProgressView_dpv_stroke_width, 10);
        mInnerCircleColor = typeArray.getColor(R.styleable.DialProgressView_dpv_circle_color,
                0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.DialProgressView_dpv_ring_color, 0xFFFFFFFF);
        mDialColor = typeArray.getColor(R.styleable.DialProgressView_dpv_dial_color, 0xFFFFFFFF);
        mDialStrokeWidth = typeArray.getDimension(R.styleable.DialProgressView_dpv_dial_stroke_width,
                6);
        mDialStartY = typeArray.getDimension(R.styleable.DialProgressView_dpv_dial_startY, 30);
        mDialStopY = typeArray.getDimension(R.styleable.DialProgressView_dpv_dial_stopY, 48);
        mProgress = typeArray.getInt(R.styleable.DialProgressView_dpv_progress, 0);
        mStartAngle = typeArray.getFloat(R.styleable.DialProgressView_dpv_start_angle, -90);
        mTextColor = typeArray.getColor(R.styleable.DialProgressView_dpv_text_color, 0xFFFFFFFF);
        mTextSize = typeArray.getDimension(R.styleable.DialProgressView_dpv_text_height, 14);
    }

    //初始化画笔
    private void initVariable() {
        //内圆
        mInnerCirclePaint = new Paint();
        mInnerCirclePaint.setAntiAlias(true);
        mInnerCirclePaint.setColor(mInnerCircleColor);
        mInnerCirclePaint.setStyle(Paint.Style.FILL);

        //刻度
        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setAntiAlias(true);
        mDialPaint.setStrokeWidth(mDialStrokeWidth);
        mDialPaint.setColor(mDialColor);
        mDialPaint.setStyle(Paint.Style.STROKE);

        //外圆弧
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mRingStrokeWidth);
        //有圆有方
        mRingPaint.setStrokeCap(Paint.Cap.SQUARE);

        //中间字
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTextHeight = (int) Math.ceil(fm.descent - fm.ascent);
    }

    //画图
    @Override
    protected void onDraw(Canvas canvas) {
        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        if (mXCenter < mMinRadius || mYCenter < mMinRadius) {
            mXCenter = RADIUS_DEFAULT;
            mYCenter = RADIUS_DEFAULT;
        }

        mRingRadius = mXCenter - mRingStrokeWidth;
        mInnerRingRadius = mRingRadius - 20;

        BigDecimal temp = new BigDecimal(Math.floor(((double) mProgress / PROGRESS_MAX) * 36));
        mDialStartIndex = temp.floatValue();

        //内圆
        canvas.drawCircle(mXCenter, mYCenter, mInnerRingRadius, mInnerCirclePaint);

        //外圆弧
        drawProgress(canvas);

        //刻度
        drawDial(canvas);
    }

    /**
     * 绘制进度
     *
     * @param canvas
     */
    private void drawProgress(Canvas canvas) {
        if (mProgress > 0) {
            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
            oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
            //取整，防止进度条与刻度线叠加出现不美观[(float) mProgress / PROGRESS_MAX * 360]
            canvas.drawArc(oval, mStartAngle, mDialStartIndex * DEGREES_DEFAULT, false,
                    mRingPaint);
            if (!TextUtils.isEmpty(mProgressText)) {
                mTextWidth = mTextPaint.measureText(mProgressText, 0, mProgressText.length());
                canvas.drawText(mProgressText, mXCenter - mTextWidth / 2,
                        mYCenter + mTextHeight / 4, mTextPaint);
            }
        }
    }

    /**
     * 画刻度
     *
     * @param canvas
     */
    private void drawDial(Canvas canvas) {
        for (int i = 0; i < 36; i++) {
            if (i > mDialStartIndex) {
                mDialPaint.setColor(mDialColor);
            } else {
                if (i != 0) {
                    mDialPaint.setColor(Color.TRANSPARENT);
                }
            }

            canvas.drawLine(mXCenter, mDialStartY, mXCenter, mDialStopY, mDialPaint);
            canvas.rotate(DEGREES_DEFAULT, mXCenter, mYCenter);
        }
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();   //可以非线程中调用
    }

    /**
     * 设置进度值
     *
     * @param text
     */
    public void setProgressText(String text) {
        mProgressText = text;
    }
}
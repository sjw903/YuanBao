package com.yuanbaogo.zui.progress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.yuanbaogo.zui.R;

/**
 * @Description: 垂直的进度条
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/28/21 1:10 PM
 * @Modifier:
 * @Modify:
 */
public class VerticalProgress extends View {

    /**
     * 进度条圆角
     */
    private int mRadius;

    /**
     * 进度条是否有边框
     */
    private boolean mBorderEnable;

    /**
     * 是否有渐变色
     */
    private boolean mGradientEnable;

    /**
     * 渐变色
     */
    private int mStartResId;
    private int mEndResId;

    /**
     * 边框的颜色
     */
    private int mBorderColorResId;

    /**
     * 进度条背景填充色
     */
    private int mProgressBgColorId;

    /**
     * 边框宽度
     */
    private int mBorderWidth;

    /**
     * 默认显示
     */
    private int mProgress = 0;

    /**
     * 最大值
     */
    private int max = 100;

    /**
     * 宽度值
     */
    private int mWidth;

    /**
     * 高度值
     */
    private int mHeight;

    /**
     * 描边层
     */
    private RectF mRectF;

    /**
     * 进度条
     */
    private Paint mPaint;

    public VerticalProgress(Context context) {
        super(context);
        init(context, null);
    }

    public VerticalProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth() - 1;// 宽度值
        mHeight = getMeasuredHeight() - 1;// 高度值
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = null;
        if (attrs != null) {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.verticalProgress);
            mRadius = typedArray.getInt(R.styleable.verticalProgress_progress_radius, 0);
            mBorderEnable = typedArray.getBoolean(R.styleable.verticalProgress_progress_border_enable,
                    true);
            mGradientEnable = typedArray.getBoolean(R.styleable.verticalProgress_progress_gradient_enable,
                    true);
            mStartResId = typedArray.getResourceId(R.styleable.verticalProgress_progress_start_color,
                    R.color.colorE4E4E4);
            mProgressBgColorId = typedArray.getResourceId(R.styleable.verticalProgress_progress_solid_color,
                    R.color.colorEA5504);
            mEndResId = typedArray.getResourceId(R.styleable.verticalProgress_progress_end_color,
                    R.color.colorE4E4E4);
            mBorderColorResId = typedArray.getResourceId(R.styleable.verticalProgress_progress_border_color,
                    R.color.colorEA5504);
            mBorderWidth = typedArray.getResourceId(R.styleable.verticalProgress_progress_border_width,
                    3);
        }

        if (typedArray != null) {
            typedArray.recycle();
        }

        mRectF = new RectF();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRadius == 0) {
            //弧度为高度的一半
            mRadius = mWidth / 2;
        }

        if (mBorderEnable) {
            //第一层矩形(描边层)
            mRectF.set(0, 0, mWidth, mHeight);
            //第一层矩形颜色(进度条描边的颜色)
            mPaint.setColor(getResources().getColor(mBorderColorResId));
            //画第一层圆角矩形
            canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
            //第二层矩形颜色(背景层颜色)
            mPaint.setColor(getResources().getColor(mProgressBgColorId));
            //第二层矩形(背景层)
            mRectF.set(mBorderWidth,
                    mBorderWidth,
                    mWidth - mBorderWidth,
                    mHeight - mBorderWidth);
            //画背景层圆角矩形(盖在描边层之上)
            canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
        }

        if (mProgress == 0)//进度为 0不画进度
            return;

        float section = mProgress / max;

        //进度层底层
        mRectF.set(+8,
                mHeight - mProgress / 100f * mHeight + 10,
                mWidth - 8,
                mHeight - 8);

        if (mGradientEnable) {
            //渐变器
            LinearGradient shader = new LinearGradient(0, 0,
                    mWidth * section,
                    mHeight,
                    getResources().getColor(mStartResId),
                    getResources().getColor(mEndResId),
                    Shader.TileMode.CLAMP);
            //第三层矩形颜色(进度渐变色)
            mPaint.setShader(shader);
        }

        //画第三层(进度层)圆角矩形(盖在背景层之上)
        canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);

        //清除之前传递的shader
        mPaint.setShader(null);
    }

    /**
     * 设置进度
     *
     * @param currentCount
     */
    public void setProgress(int currentCount) {
        this.mProgress = currentCount > max ? max : currentCount;
        postInvalidate();
    }

}

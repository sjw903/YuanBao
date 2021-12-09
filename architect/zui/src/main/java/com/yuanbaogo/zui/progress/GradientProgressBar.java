package com.yuanbaogo.zui.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.yuanbaogo.zui.R;

/**
 * @Description: 幸运拼团  渐变进度条
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/27/21 4:56 PM
 * @Modifier:
 * @Modify:
 */
public class GradientProgressBar extends View {

    /**
     * 分段颜色
     */
    private static final int[] SECTION_COLORS = {0xFFFF6E00, 0xFFFF3D60, 0xFF953DD7};

    private int bgColor = R.color.colorCCCCCC;

    /**
     * 进度条最大值
     */
    private float maxCount;

    /**
     * 进度条当前值
     */
    private float currentCount;

    /**
     * 画笔
     */
    private Paint mPaint;

    private int mWidth, mHeight;

    public GradientProgressBar(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public GradientProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GradientProgressBar(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        int round = mHeight / 2;

        RectF rectBg = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectBg, round, round, mPaint);
        mPaint.setColor(getResources().getColor(bgColor));

        RectF rectBlackBg = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectBlackBg, round, round, mPaint);

        float section = currentCount / maxCount;
        RectF rectProgressBg = new RectF(0, 0, mWidth * section, mHeight);
        if (section <= 1.0f / 3.0f) {
            if (section != 0.0f) {
                mPaint.setColor(SECTION_COLORS[0]);
            } else {
                mPaint.setColor(getResources().getColor(R.color.colorED4D16));
            }
        } else {
            int count = (section <= 1.0f / 3.0f * 2) ? 2 : 3;
            int[] colors = new int[count];
            System.arraycopy(SECTION_COLORS, 0, colors, 0, count);
            float[] positions = new float[count];
            if (count == 2) {
                positions[0] = 0.0f;
                positions[1] = 1.0f - positions[0];
            } else {
                positions[0] = 0.0f;
                positions[1] = (maxCount / 3) / currentCount;
                positions[2] = 1.0f - positions[0] * 2;
            }
            positions[positions.length - 1] = 1.0f;
            LinearGradient shader = new LinearGradient(0, 0, mWidth * section,
                    mHeight, colors, null, Shader.TileMode.MIRROR);
            mPaint.setShader(shader);
        }

        try {
            canvas.drawRoundRect(rectProgressBg, round, round, mPaint);
        } catch (Exception e) {
        }

    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /***
     * 设置最大的进度值
     * @param bgColor
     */
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    /***
     * 设置最大的进度值
     * @param maxCount
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    /***
     * 设置当前的进度值
     * @param currentCount
     */
    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        invalidate();
    }

    public float getMaxCount() {
        return maxCount;
    }

    public float getCurrentCount() {
        return currentCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(15);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

}
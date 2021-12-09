package com.yuanbaogo.zui.banner.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.youth.banner.indicator.BaseIndicator;
import com.youth.banner.util.BannerUtils;

/**
 * @Description: 自定义数字指示器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/2/21 1:14 PM
 * @Modifier:
 * @Modify:
 */
public class NumIndicator extends BaseIndicator {

    private int width;
    private int height;
    private int radius;

    public NumIndicator(Context context) {
        this(context, null);
    }

    public NumIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setTextSize(BannerUtils.dp2px(10));
        mPaint.setTextAlign(Paint.Align.CENTER);
        width = (int) BannerUtils.dp2px(30);
        height = (int) BannerUtils.dp2px(15);
        radius = (int) BannerUtils.dp2px(20);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = config.getIndicatorSize();
        if (count <= 1) {
            return;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = config.getIndicatorSize();
        if (count <= 1) {
            return;
        }
        RectF rectF = new RectF(0, 0, width, height);
        mPaint.setColor(Color.parseColor("#70000000"));
        canvas.drawRoundRect(rectF, radius, radius, mPaint);

        String text = config.getCurrentPosition() + 1 + "/" + count;
        mPaint.setColor(Color.WHITE);
        canvas.drawText(text, width / 2, (float) (height * 0.7), mPaint);
    }

}

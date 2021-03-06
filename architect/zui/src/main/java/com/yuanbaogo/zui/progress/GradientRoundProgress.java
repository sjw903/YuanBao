package com.yuanbaogo.zui.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.yuanbaogo.zui.R;

/**
 * 渐变色环形进度条
 */
public class GradientRoundProgress extends View {
    private Paint paint; // 画笔对象的引用
    private int roundColor; // 圆环的颜色
    private float roundWidth; // 圆环的宽度
    private int progressColor; // 圆环进度的颜色
    private float progressWidth; // 圆环进度的宽度
    private String text; // 文字内容
    private int textColor; // 中间进度百分比的字符串的颜色
    private float textSize; // 中间进度百分比的字符串的字体大小
    private float numSize; // 中间进度文本大小
    private int max; // 最大进度
    private int startAngle; // 进度条起始角度
    private boolean textShow; // 是否显示中间的进度
    private boolean useCustomFont; // 是否使用自定义字体
    private int startColor; // 渐变色起始色
    private int midColor; // 渐变色中间色
    private int endColor; // 渐变色起始色
    private int progress; // 当前进度

    public GradientRoundProgress(Context context) {
        this(context, null);
    }

    public GradientRoundProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientRoundProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();

        // 读取自定义属性的值
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientRoundProgress);

        // 获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.GradientRoundProgress_grp_roundColor, Color.RED);
        roundWidth = mTypedArray.getDimension(R.styleable.GradientRoundProgress_grp_roundWidth, 5);
        progressColor = mTypedArray.getColor(R.styleable.GradientRoundProgress_grp_progressColor, Color.GREEN);
        progressWidth = mTypedArray.getDimension(R.styleable.GradientRoundProgress_grp_progressWidth, roundWidth);
        text = mTypedArray.getString(R.styleable.GradientRoundProgress_grp_text);
        textColor = mTypedArray.getColor(R.styleable.GradientRoundProgress_grp_textColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.GradientRoundProgress_grp_textSize, 11);
        numSize = mTypedArray.getDimension(R.styleable.GradientRoundProgress_grp_numSize, 14);
        max = mTypedArray.getInteger(R.styleable.GradientRoundProgress_grp_max, 100);
        startAngle = mTypedArray.getInt(R.styleable.GradientRoundProgress_grp_startAngle, 90);
        textShow = mTypedArray.getBoolean(R.styleable.GradientRoundProgress_grp_textShow, true);
        useCustomFont = mTypedArray.getBoolean(R.styleable.GradientRoundProgress_grp_userCustomFont, false);
        startColor = mTypedArray.getColor(R.styleable.GradientRoundProgress_grp_startColor, Color.GREEN);
        midColor = mTypedArray.getColor(R.styleable.GradientRoundProgress_grp_midColor, Color.GREEN);
        endColor = mTypedArray.getColor(R.styleable.GradientRoundProgress_grp_endColor, Color.GREEN);
        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = getWidth() / 2; // 获取圆心的x坐标
        int radius = (int) (centerX - roundWidth / 2); // 圆环的半径

        // step1 画最外层的大圆环
        paint.setStrokeWidth(roundWidth); // 设置圆环的宽度
        paint.setColor(roundColor); // 设置圆环的颜色
        paint.setAntiAlias(true); // 消除锯齿
        // 设置画笔样式
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centerX, centerX, radius, paint); // 画出圆环

        // step2 画圆弧-画圆环的进度
        // 锁画布(为了保存之前的画布状态)
        canvas.save();
        canvas.rotate(startAngle, centerX, centerX);
        paint.setStrokeWidth(progressWidth); // 设置画笔的宽度使用进度条的宽度
        paint.setColor(progressColor); // 设置进度的颜色
        RectF oval = new RectF(centerX - radius, centerX - radius, centerX + radius, centerX + radius); // 用于定义的圆弧的形状和大小的界限

        int sweepAngle = 360 * progress / max; // 计算进度值在圆环所占的角度
        // 根据进度画圆弧
        paint.setShader(new SweepGradient(centerX, centerX, new int[]{startColor, midColor, endColor}, null));
        canvas.drawArc(oval, 0, sweepAngle, false, paint);
        paint.setShader(null);
        canvas.rotate(-startAngle, centerX, centerX);
        canvas.restore();

        // step3 画文字指示
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        // 计算百分比
        int percent = (int) (((float) progress / (float) max) * 100);

        if (textShow && text != null && text.length() > 0 && percent >= 0) {
            // 3.1 画文字
            paint.setTypeface(Typeface.DEFAULT); // 设置为默认字体
            float textWidth = paint.measureText(text); // 测量字体宽度
            canvas.drawText(text, centerX - textWidth / 2, centerX + textSize + 5, paint);
            // 3.2 画百分比
            paint.setTextSize(numSize);
//            if (useCustomFont) {
//                paint.setTypeface(AppResource.getTypeface(getContext())); // 设置字体
//            } else {
            paint.setTypeface(Typeface.DEFAULT_BOLD); // 设置为加粗默认字体
//            }
            float numWidth = paint.measureText(percent + "%"); // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间
            canvas.drawText(percent + "%", centerX - numWidth / 2, centerX, paint); // 画出进度百分比
        }
    }

    /**
     * 设置进度的最大值
     * <p>根据需要，最大值一般设置为100，也可以设置为1000、10000等</p>
     *
     * @param max int最大值
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度
     *
     * @return int 当前进度值
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件
     *
     * @param progress 进度值
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        this.progress = progress;
        // 刷新界面调用postInvalidate()能在非UI线程刷新
        postInvalidate();
    }
}

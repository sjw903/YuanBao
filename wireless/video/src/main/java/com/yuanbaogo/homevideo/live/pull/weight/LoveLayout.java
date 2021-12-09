package com.yuanbaogo.homevideo.live.pull.weight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.yuanbaogo.homevideo.R;

import java.util.Random;

public class LoveLayout extends RelativeLayout {

    private Context context;
    //2、准备几张点赞图片
    private int[] christmas_drawable = {
            R.mipmap.icon_live_like_red,
            R.mipmap.icon_live_like_red2,
            R.mipmap.icon_live_like_yellow,
            R.mipmap.icon_live_like_green,
            R.mipmap.icon_live_like_orange};
    //随机数种子
    private Random random = new Random();
    //View的宽高
    private int width, height;
    //图片的宽高
    private int drawableWidth, drawableHeight;

    public LoveLayout(Context context) {
        this(context, null);
    }

    public LoveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        //4、获取点赞图片的宽高
        Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.icon_live_like_orange);
        drawableWidth = drawable.getIntrinsicWidth();
        drawableHeight = drawable.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    /**
     * 添加圣诞
     *
     * @param context
     */
    public void addChristmas(Context context) {
        // 1、点击一次增加一张图片在底部

        final ImageView imageView = new ImageView(context);
        imageView.setBackgroundResource(christmas_drawable[random.nextInt(christmas_drawable.length - 1)]);
        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(ALIGN_PARENT_RIGHT);
        imageView.setLayoutParams(params);
        addView(imageView);

        //2、开始执行点赞效果
        AnimatorSet animatorSet = getAnimatorSet(imageView);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //3、动画执行后移除View
                removeView(imageView);
            }
        });
        animatorSet.start();
    }

    private AnimatorSet getAnimatorSet(ImageView imageView) {
        AnimatorSet enter = new AnimatorSet();

        //1、缩放动画
        AnimatorSet scaleAnimator = new AnimatorSet();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.3f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.3f, 1f);
        scaleAnimator.setDuration(300);
        scaleAnimator.playTogether(alpha, scaleX, scaleY);

        //2、贝塞尔动画
        ValueAnimator bezierAnimator = getBezierAnimator(imageView);

        //3、两个动画按顺序播放
        enter.playSequentially(scaleAnimator, bezierAnimator);
        return enter;
    }

    /**
     * 贝塞尔动画
     *
     * @return
     */
    private ValueAnimator getBezierAnimator(final ImageView imageView) {
        //1、构建贝塞尔曲线的四个点

        PointF point0 = new PointF((width - drawableWidth), height - drawableHeight);
        PointF point1 = new PointF(random.nextInt(width), random.nextInt(height / 2));
        PointF point2 = new PointF(random.nextInt(width), random.nextInt(height / 2) + height / 2);
        PointF point3 = new PointF(random.nextInt(width - drawableWidth), 0);

        //2、创建贝塞尔属性动画
        BezierEvaluator evaluator = new BezierEvaluator(point1, point2);
        final ValueAnimator valueAnimator = ObjectAnimator.ofObject(evaluator, point0, point3);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(3000);
        //3、监听贝塞尔曲线估值器返回值
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //4、获取BezierEvaluator中evaluate()返回的运行轨迹坐标点，设置点赞图片路线
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
                //6、获取BezierEvaluator中evaluate()返回的参数t，设置消失动画
                float t = animation.getAnimatedFraction();
                imageView.setAlpha(1 - t + 0.2f);
            }
        });
        return valueAnimator;
    }

    /**
     * 贝塞尔曲线估值器：计算动画的执行轨迹
     *
     * @params 传入贝塞尔曲线需要的四个点
     * @return 通过计算返回贝塞尔曲线的坐标
     */
    public class BezierEvaluator implements TypeEvaluator<PointF> {

        private PointF point1;
        private PointF point2;

        public BezierEvaluator(PointF point1, PointF point2) {
            this.point1 = point1;
            this.point2 = point2;
        }

        @Override
        public PointF evaluate(float t, PointF point0, PointF point3) {
            PointF point = new PointF();
            //t 取值为 [0,1]

            /**
             * 三阶贝塞尔公式
             *
             * B(t)=(1 - t)^3 P0
             *     + 3 t (1 - t)^2 P1
             *     + 3 t^2 (1 - t) P2
             *     + t^3 P3
             */
            point.x = point0.x * (1 - t) * (1 - t) * (1 - t)
                    + 3 * point1.x * t * (1 - t) * (1 - t)
                    + 3 * point2.x * t * t * (1 - t)
                    + point3.x * t * t * t;

            /**
             * 三阶贝塞尔公式
             *
             * B(t)=(1 - t)^3 P0
             *     + 3 t (1 - t)^2 P1
             *     + 3 t^2 (1 - t) P2
             *     + t^3 P3
             */
            point.y = point0.y * (1 - t) * (1 - t) * (1 - t)
                    + 3 * point1.y * t * (1 - t) * (1 - t)
                    + 3 * point2.y * t * t * (1 - t)
                    + point3.y * t * t * t;

            return point;
        }
    }
}

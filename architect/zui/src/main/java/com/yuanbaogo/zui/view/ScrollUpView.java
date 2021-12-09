package com.yuanbaogo.zui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.yuanbaogo.zui.R;

import java.util.List;

/**
 * Function：自定义ViewFlipper控件
 */
public class ScrollUpView extends ViewFlipper {

    private static final int interval = 3000;
    /**
     * 动画时间
     */
    private static final int animDuration = 500;
    private boolean isSetAnimDuration = false;

    public ScrollUpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setFlipInterval(interval);//设置时间间隔2000毫秒
        //进来的动画
        Animation animIn = AnimationUtils.loadAnimation(context, R.anim.anim_in);
        if (isSetAnimDuration)
            animIn.setDuration(animDuration);
        setInAnimation(animIn);
        //退出的动画
        Animation animOut = AnimationUtils.loadAnimation(context, R.anim.anim_out);
        if (isSetAnimDuration)
            animOut.setDuration(animDuration);
        setOutAnimation(animOut);
    }

    /**
     * 设置循环滚动的View数组
     *
     * @param views
     */
    public void setViews(final List<View> views) {
        if (views == null || views.size() == 0) return;
        removeAllViews();
        for ( int i = 0; i < views.size(); i++) {
            //设置翻滚的子view
            addView(views.get(i));
        }
        startFlipping(); //开启翻滚
    }
}



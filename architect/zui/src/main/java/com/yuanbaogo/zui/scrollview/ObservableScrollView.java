package com.yuanbaogo.zui.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @Description: 带滚动监听的scrollview
 * @Params: 为了修改顶部导航栏颜色变化
 * @Problem:
 * @Author: HG
 * @Date: 7/2/21 1:30 PM
 * @Modifier:
 * @Modify:
 */
public class ObservableScrollView extends ScrollView {

    private ScrollViewListener scrollViewListener = null;

    private OnAnimationScrollChangeListener onAnimationScrollChangeListener = null;

    public interface ScrollViewListener {
        void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
    }

    public interface OnAnimationScrollChangeListener {
        void onAnimationScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy, float dy);
    }

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public void setOnAnimationScrollListener(OnAnimationScrollChangeListener onAnimationScrollChangeListener) {
        this.onAnimationScrollChangeListener = onAnimationScrollChangeListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
        if (onAnimationScrollChangeListener != null) {
            //x0.65 使位移效果更加平滑 解决手指按住停留时抖动问题
            onAnimationScrollChangeListener.onAnimationScrollChanged(this, x, y, oldx, oldy, getScrollY() * 0.65f);
        }
    }

}
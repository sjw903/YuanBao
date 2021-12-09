package com.yuanbaogo.zui.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.textview.animation.Rotate3dAnimation;

/**
 * @Description: 自定义TextView（广告 上下滚动）
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/7/21 2:08 PM
 * @Modifier:
 * @Modify:
 */
public class ADTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private float mHeight = 16;

    private Context mContext;

    /**
     * mInUp,mOutUp分别构成向下翻页的进出动画
     */
    private Animation mInUp;
    private Animation mOutUp;

    /**
     * mInDown,mOutDown分别构成向下翻页的进出动画
     */
    private Animation mInDown;
    private Animation mOutDown;

    public ADTextView(Context context) {
        this(context, null);
    }

    public ADTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        setFactory(this);
        mInUp = createAnim(0, 0, true, true);
        mOutUp = createAnim(0, 0, false, true);
        mInDown = createAnim(90, 0, true, false);
        mOutDown = createAnim(0, -90, false, false);
        setInAnimation(mInUp);
        setOutAnimation(mOutUp);
    }

    private Animation createAnim(float start, float end, boolean turnIn, boolean turnUp) {
        final Animation rotation = new Rotate3dAnimation(this, start, end, turnIn, turnUp);
        rotation.setDuration(1000);//设置持续时间
        rotation.setFillAfter(false);//设置填充后,动画结束是画面停留在此动画的最后一帧
        rotation.setInterpolator(new AccelerateInterpolator());//社会播放速度
        return rotation;
    }

    /**
     * 这里返回的TextView，就是我们看到的View
     *
     * @return
     */
    @Override
    public TextView makeView() {
        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.LEFT);
        textView.setTextSize(mHeight);
        textView.setMaxLines(1);
        textView.setTextColor(getResources().getColor(R.color.colorAAAAAA));
        return textView;
    }

    /**
     * 定义动作，向下滚动翻页
     */
    public void previous() {
        if (getInAnimation() != mInDown) {
            setInAnimation(mInDown);
        }
        if (getOutAnimation() != mOutDown) {
            setOutAnimation(mOutDown);
        }
    }

    /**
     * 定义动作，向上滚动翻页
     */
    public void next() {
        if (getOutAnimation() != mOutUp) {
            setOutAnimation(mOutUp);
        }
        if (getInAnimation() != mInUp) {
            setInAnimation(mInUp);
        }
    }

}

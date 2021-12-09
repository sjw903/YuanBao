package com.yuanbaogo.zui.dialog.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.yuanbaogo.zui.R;


/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/9/21 4:30 PM
 * @Modifier:
 * @Modify:
 */
public abstract class DialogsFragment extends DialogFragment {

    private DisplayMetrics dm;

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        //让布局向上移来显示软键盘
        win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(new ColorDrawable(getResources().getColor(getColor())));
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = getGravity();
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = getWidth();
        params.height = getHeight();
        params.y = getY();
        win.setAttributes(params);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //小米手机弹框两侧有留白
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(getLayout(), container, false);
        intViews(view);
        setTexts();
        setOnClicks();
        return view;
    }

    public abstract int getLayout();

    /**
     * 获取布局的控件
     *
     * @return
     */
    protected abstract void intViews(View view);

    /**
     * 初始化布局
     *
     * @return
     */
    protected abstract void setTexts();

    /**
     * 点击事件
     *
     * @return
     */
    protected abstract void setOnClicks();

    public int getGravity() {
        return Gravity.BOTTOM;
    }

    public int getWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    public int getHeight() {
        return dm.heightPixels / 2 + dm.heightPixels / 6;
    }

    protected void startUpAnimation(View view) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        slide.setDuration(250);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        view.startAnimation(slide);
    }

    protected void startDownAnimation(View view) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        slide.setDuration(250);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(slide);
    }

    protected DisplayMetrics getDm() {
        return dm;
    }

    protected int getColor() {
        return R.color.color00000000;
    }

    protected int getY() {
        return 0;
    }

}

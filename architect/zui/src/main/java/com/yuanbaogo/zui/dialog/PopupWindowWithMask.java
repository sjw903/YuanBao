package com.yuanbaogo.zui.dialog;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

/**
 * 带阴影背景的弹窗PopupWindow
 */

public abstract class PopupWindowWithMask extends PopupWindow {
    protected Context context;
    private WindowManager windowManager;
    private View maskView;
    private boolean isMask;

    public PopupWindowWithMask(Context context, boolean isMask) {
        super(context);
        this.context = context;
        this.isMask = isMask;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        setContentView(initContentView());
        setHeight(initHeight());
        setWidth(initWidth());
        setOutsideTouchable(true);
        setFocusable(false);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
       // setAnimationStyle(R.style.pop_anim_bottom);
    }

    protected abstract View initContentView();

    protected abstract int initHeight();

    protected abstract int initWidth();

    @Override
    public void showAsDropDown(View anchor) {
        addMask(anchor.getWindowToken());
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        addMask(parent.getWindowToken());
        super.showAtLocation(parent, gravity, x, y);
    }

    private void addMask(IBinder token) {
        if(!isMask) return;
        WindowManager.LayoutParams wl = new WindowManager.LayoutParams();
        wl.width = WindowManager.LayoutParams.MATCH_PARENT;
        wl.height = WindowManager.LayoutParams.MATCH_PARENT;
        wl.flags |= FLAG_LAYOUT_NO_LIMITS;
        wl.format = PixelFormat.TRANSLUCENT;//不设置这个弹出框的透明遮罩显示为黑色
        wl.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;//该Type描述的是形成的窗口的层级关系
        wl.token = token;//获取当前Activity中的View中的token,来依附Activity
        maskView = new View(context);
        maskView.setBackgroundColor(0x7f000000);
        maskView.setFitsSystemWindows(false);
        maskView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    removeMask();
                    return OnKeyListener();
                }
                return false;
            }
        });
        /**
         * 通过WindowManager的addView方法创建View，产生出来的View根据WindowManager.LayoutParams属性不同，效果也就不同了。
         * 比如创建系统顶级窗口，实现悬浮窗口效果！
         */
        windowManager.addView(maskView, wl);
    }

    private void removeMask() {
        if (null != maskView) {
            windowManager.removeViewImmediate(maskView);
            maskView = null;
        }
    }

    @Override
    public void dismiss() {
        removeMask();
        super.dismiss();
    }

    public void setOutside(boolean b){
        setOutsideTouchable(b);
    }

    public boolean OnKeyListener(){
        return true;
    }
}

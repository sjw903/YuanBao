package com.yuanbaogo.zui.toast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.libbase.config.ApplicationConfigHelper;
import com.yuanbaogo.zui.R;


/**
 * @Description: 自定义Toast
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 5/21/21 1:42 PM
 * @Modifier:
 * @Modify:
 */
public class ToastView {

    private static long lastClick;

    //点击间隔1000毫秒。可自定义
    private final static long TIME = 1000;

    public static boolean isDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClick < TIME) {
            return true;
        }
        lastClick = time;
        return false;
    }

    /**
     * 调用自定义toast
     *
     * @param context 上下文
     * @param img     需要显示图片，直接传图片R.mipmap.icon_toast_view_error（默认传0，不显示图片）
     * @param msg     需要显示的提示内容，直接传文字（默认传null，显示网络错误）
     */
    public static void showToast(Context context, int img, String msg) {

        if (!isDoubleClick()) {
            View toastView = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
            RelativeLayout toastViewRlOutermest = toastView.findViewById(R.id.toast_view_rl_outermest);
            ImageView toastViewImg = toastView.findViewById(R.id.toast_view_img);
            TextView toastViewTvMsg = toastView.findViewById(R.id.toast_view_tv_msg);
            if (img != 0) {
                toastViewImg.setVisibility(View.VISIBLE);
                toastViewImg.setImageResource(img);
            } else {
                toastViewImg.setVisibility(View.GONE);
            }
            if (msg != null) {
                toastViewTvMsg.setText(msg);
            }
            toastViewRlOutermest.getBackground().setAlpha(250);

            ToastUtil.showView(toastView, Toast.LENGTH_SHORT);
        }

    }

    public static void showToast(Context context, String msg) {
        showToast(context, 0, msg);
    }

    public static void showToast(Context context, int img, @StringRes int msg) {
        showToast(context, img, context.getString(msg));
    }

    public static void showToast(Context context, @StringRes int msg) {
        showToast(context, context.getString(msg));
    }

    public static void showToast(@StringRes int msg) {
        Context context = ApplicationConfigHelper.mApplication.getApplicationContext();
        showToast(context, context.getString(msg));
    }

    public static void showToast(String msg) {
        Context context = ApplicationConfigHelper.mApplication.getApplicationContext();
        showToast(context, msg);
    }

}

package com.yuanbaogo.libbase.baseutil;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yuanbaogo.libbase.config.ApplicationConfigHelper;


public class ToastUtil {
    private static Toast sToast;

    public ToastUtil() {
    }

    public static void showToast(final String text) {
        if (!StringUtil.isEmpty(text)) {
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                showToast(text, 1);
            } else {
                (new Handler(ApplicationConfigHelper.mApplication.getMainLooper())).post(new Runnable() {
                    public void run() {
                        ToastUtil.showToast(text, 1);
                    }
                });
            }

        }
    }

    public static void showToast(final int resId) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            showToast(resId, 1);
        } else {
            (new Handler(ApplicationConfigHelper.mApplication.getMainLooper())).post(new Runnable() {
                public void run() {
                    ToastUtil.showToast(resId, 1);
                }
            });
        }

    }

    public static void showToast(String text, int duration) {
        if (!StringUtil.isEmpty(text)) {
            cancel();
            if (sToast == null) {
                sToast = Toast.makeText(ApplicationConfigHelper.mApplication.getApplicationContext(), (CharSequence)null, duration);
            }

            sToast.setText(text);
            sToast.setDuration(duration);
            sToast.show();
        }
    }

    public static void showToast(int res, int duration) {
        cancel();
        if (sToast == null) {
            sToast = Toast.makeText(ApplicationConfigHelper.mApplication.getApplicationContext(), res, duration);
        } else {
            sToast.setText(res);
            sToast.setDuration(duration);
        }

        sToast.show();
    }

    public static void showToast(final int ImageResourceId, final String text, final int duration) {
        if (!StringUtil.isEmpty(text)) {
            cancel();
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                if (sToast == null) {
                    sToast = new Toast(ApplicationConfigHelper.mApplication.getApplicationContext());
                }

                sToast.setGravity(17, 0, 0);
                View toastView = sToast.getView();
                ImageView img = new ImageView(ApplicationConfigHelper.mApplication.getApplicationContext());
                img.setImageResource(ImageResourceId);
                LinearLayout ll = new LinearLayout(ApplicationConfigHelper.mApplication.getApplicationContext());
                ll.addView(img);
                ll.addView(toastView);
                sToast.setView(ll);
                sToast.show();
                showToast(String.valueOf(text), duration);
            } else {
                (new Handler(ApplicationConfigHelper.mApplication.getMainLooper())).post(new Runnable() {
                    public void run() {
                        if (ToastUtil.sToast == null) {
                            ToastUtil.sToast = new Toast(ApplicationConfigHelper.mApplication.getApplicationContext());
                        }
                        ToastUtil.sToast.setGravity(17, 0, 0);
                        View toastView = ToastUtil.sToast.getView();
                        ImageView img = new ImageView(ApplicationConfigHelper.mApplication.getApplicationContext());
                        img.setImageResource(ImageResourceId);
                        LinearLayout ll = new LinearLayout(ApplicationConfigHelper.mApplication.getApplicationContext());
                        ll.addView(img);
                        ll.addView(toastView);
                        ToastUtil.sToast.setView(ll);
                        ToastUtil.sToast.show();
                        ToastUtil.showToast(String.valueOf(text), duration);
                    }
                });
            }
        }
    }


    public static void showView(View toastView, int duration) {
        if (null != toastView) {
            cancel();
            if (sToast == null) {
                sToast = Toast.makeText(ApplicationConfigHelper.mApplication.getApplicationContext(), (CharSequence)null, duration);
            }
            sToast.setView(toastView);
            sToast.setGravity(Gravity.CENTER, 0, 0);
            sToast.setDuration(duration);
            sToast.show();
        }
    }


    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }



}

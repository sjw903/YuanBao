package com.yuanbaogo.datacenter.progress;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.yuanbaogo.datacenter.R;

import java.lang.ref.WeakReference;

public class LodingProgressDialog {

    private static Dialog loadingDialog;

    private static WeakReference<Activity> mActivityRef;
    private static String mPath;


    public LodingProgressDialog() {
    }

    public static void show(Activity context, boolean isCancel, boolean isRight) {
        mActivityRef = new WeakReference(context);
        if (!((Activity) mActivityRef.get()).isFinishing()) {
            createDialog(context, "", isCancel, isRight);
        }

    }

    public static void show(Context context, String msg, boolean isCancel, boolean isRight) {
        mActivityRef = new WeakReference((Activity) context);
        if (!((Activity) mActivityRef.get()).isFinishing()) {
            if (loadingDialog == null) {
                createDialog(context, msg, isCancel, isRight);
            } else {
                Context dialogContext = loadingDialog.getContext();
                if (dialogContext instanceof Activity && ((Activity) dialogContext).isFinishing()) {
                    loadingDialog = null;
                    createDialog(context, msg, isCancel, isRight);
                } else if (!isShowing()) {
                    loadingDialog.show();
                }
            }

        }
    }

    public static void setImageGif(String path) {
        mPath = path;
    }

    private static void createDialog(Context context, String msg, boolean isCancel, boolean isRight) {
        LinearLayout.LayoutParams wrap_content0 =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout main = new LinearLayout(context);
        Dialog creatingLoadingDialog = new Dialog(context);
        creatingLoadingDialog.setCancelable(isCancel);
        View loadingView = View.inflate(context, R.layout.dialog_loading_progress, (ViewGroup) null);
        ImageView iv = loadingView.findViewById(R.id.loadingImageView);
        Glide.with(context).asGif().load(R.drawable.loading_progress).into(iv);
        main.addView(loadingView, wrap_content0);
        LinearLayout.LayoutParams fill_parent = new LinearLayout.LayoutParams(-1, -1);
        creatingLoadingDialog.setContentView(main, fill_parent);
        Window dialogWindow = creatingLoadingDialog.getWindow();
        dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        loadingDialog = creatingLoadingDialog;
        //去除遮罩
        loadingDialog.getWindow().setDimAmount(0f);
        loadingDialog.show();
        if (loadingDialog != null) {
            loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    LodingProgressDialog.dismiss();
                }
            });
        }
    }

    public static void dismiss() {
        try {
            if (loadingDialog != null && mActivityRef != null && !((Activity) mActivityRef.get()).isFinishing()) {
                loadingDialog.dismiss();
                mActivityRef.clear();
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        } finally {
            mActivityRef = null;
            loadingDialog = null;
        }

    }

    public static boolean isShowing() {
        return loadingDialog.isShowing();
    }

    public static Dialog getDialog() {
        return loadingDialog;
    }


}

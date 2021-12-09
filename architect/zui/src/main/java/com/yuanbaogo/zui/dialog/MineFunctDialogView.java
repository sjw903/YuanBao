package com.yuanbaogo.zui.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.agentweb.ActionActivity;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;
import com.yuanbaogo.zui.toast.ToastView;

/**
 * @Description: 好友信息 右上角功能按钮
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/9/21 4:27 PM
 * @Modifier:
 * @Modify:
 */
public class MineFunctDialogView extends DialogsFragment implements View.OnClickListener {

    RelativeLayout dialogMineFunctRlRoot;

    RelativeLayout dialogMineFunctRl;

    RelativeLayout dialogMineFunctRlReport;

    public MineFunctDialogView() {

    }

    @Override
    public int getLayout() {
        return R.layout.dialog_mine_funct_view;
    }

    @Override
    protected void intViews(View view) {
        dialogMineFunctRlRoot = view.findViewById(R.id.dialog_mine_funct_rl_root);
        dialogMineFunctRl = view.findViewById(R.id.dialog_mine_funct_rl);
        dialogMineFunctRlReport = view.findViewById(R.id.dialog_mine_funct_rl_report);
        startUpAnimation(dialogMineFunctRl);
    }

    @Override
    protected void setTexts() {

    }

    @Override
    protected void setOnClicks() {
        dialogMineFunctRlRoot.setOnClickListener(this);
        dialogMineFunctRlReport.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dialog_mine_funct_rl_report) {
//            ToastView.showToast("举报");
            onCall.onClickReport();
            startDownAnimation(dialogMineFunctRl);
        } else if (id == R.id.dialog_mine_funct_rl_root) {
            startDownAnimation(dialogMineFunctRl);
        }
    }
    ReportOnCall onCall;

    public void setOnCall(ReportOnCall onCall) {
        this.onCall = onCall;
    }

    //举报回调
    public interface ReportOnCall {
        void onClickReport();
    }

    @Override
    public int getHeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    public int getWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    public int getGravity() {
        return Gravity.TOP;
    }

    protected void startUpAnimation(View view) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        slide.setDuration(250);//设置动画持续时间
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        view.startAnimation(slide);
    }

    protected void startDownAnimation(View view) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
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

}
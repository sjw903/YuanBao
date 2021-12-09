package com.yuanbaogo.zui.dialog;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;

/**
 * @Description: 二次确认弹窗
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/9/21 4:27 PM
 * @Modifier:
 * @Modify:
 */
public class ConfirmDialogView extends DialogsFragment implements View.OnClickListener {

    TextView dialogConfirmViewTitle;

    TextView dialogConfirmViewTvCancel;

    TextView dialogConfirmViewTvDetermine;

    OnCallDialog onCallDialog;

    String cancel = "取消";
    String size = "0";
    String confirm = "确认";

    public void setOnCallDialog(OnCallDialog onCallDialog) {
        this.onCallDialog = onCallDialog;
    }

    public ConfirmDialogView() {
    }

    public ConfirmDialogView(String cancel, String size) {
        this.cancel = cancel;
        this.size = size;
    }

    public ConfirmDialogView(String cancel, String size,String confirm) {
        this.cancel = cancel;
        this.size = size;
        this.confirm = confirm;
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_confirm_view;
    }

    @Override
    protected void intViews(View view) {
        dialogConfirmViewTitle = view.findViewById(R.id.dialog_confirm_view_title);
        dialogConfirmViewTvCancel = view.findViewById(R.id.dialog_confirm_view_tv_cancel);
        dialogConfirmViewTvDetermine = view.findViewById(R.id.dialog_confirm_view_tv_determine);
    }

    @Override
    protected void setTexts() {
        dialogConfirmViewTitle.setText(size);
        dialogConfirmViewTvCancel.setText(cancel);
        dialogConfirmViewTvDetermine.setText(confirm);
    }

    @Override
    protected void setOnClicks() {
        dialogConfirmViewTvCancel.setOnClickListener(this);
        dialogConfirmViewTvDetermine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dialog_confirm_view_tv_cancel) {
            dismiss();
        } else if (id == R.id.dialog_confirm_view_tv_determine) {
            onCallDialog.onCallDetermine();
        }
    }

    @Override
    public int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    //屏幕宽度的80%
    public int getWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return (int) (dm.widthPixels * 0.8);
    }

    public int getGravity() {
        return Gravity.CENTER;
    }

}
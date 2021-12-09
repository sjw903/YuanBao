package com.yuanbaogo.zui.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;

/**
 * 底下弹出的dialog, 确认取消
 */
public class SelectConfirmDialogView extends DialogsFragment {

    private TextView btn_cancel;

    private TextView tv_confirm;

    private OnListener onListener;

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }

    @Override
    public int getLayout() {
        return R.layout.pop_bottom_select_confirm;
    }

    @Override
    protected void intViews(View view) {
        btn_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
    }

    @Override
    protected void setTexts() {

    }

    @Override
    protected void setOnClicks() {
        //取消按钮
        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //销毁弹出框
                if (onListener != null) {
                    onListener.onCancel();
                }
                dismiss();
            }
        });

        tv_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //销毁弹出框
                if (onListener != null) {
                    onListener.onConfirm();
                }
                dismiss();
            }
        });
    }

    public interface OnListener {
        void onConfirm();

        void onCancel();
    }

    @Override
    public int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public int getGravity() {
        return Gravity.BOTTOM;
    }

}
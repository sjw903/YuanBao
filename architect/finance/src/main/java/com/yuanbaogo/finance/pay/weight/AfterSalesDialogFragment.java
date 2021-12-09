package com.yuanbaogo.finance.pay.weight;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yuanbaogo.finance.R;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;

public class AfterSalesDialogFragment extends DialogsFragment implements View.OnClickListener {

    private OnCancelRefundListener onCancelRefundListener;

    private TextView mAfterTvCancel;
    private TextView mAfterTvSure;


    @Override
    public int getLayout() {
        return R.layout.pay_after_sales_cancel_dialog;
    }

    @Override
    protected void intViews(View view) {
        mAfterTvCancel = view.findViewById(R.id.after_tv_cancel);
        mAfterTvSure = view.findViewById(R.id.after_tv_sure);
    }

    @Override
    protected void setTexts() {
    }

    @Override
    protected void setOnClicks() {
        mAfterTvCancel.setOnClickListener(this);
        mAfterTvSure.setOnClickListener(this);
    }

    public void setOnCancelRefundListener(OnCancelRefundListener onCancelRefundListener) {
        this.onCancelRefundListener = onCancelRefundListener;
    }

    @Override
    public int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public int getWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return (int) (dm.widthPixels * 0.8);
    }


    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.after_tv_cancel) {
            dismiss();
        } else if (id == R.id.after_tv_sure) {
            dismiss();
            if (onCancelRefundListener != null) {
                onCancelRefundListener.onCancelRefund();
            }
        }
    }

    public interface OnCancelRefundListener {
        void onCancelRefund();
    }

}

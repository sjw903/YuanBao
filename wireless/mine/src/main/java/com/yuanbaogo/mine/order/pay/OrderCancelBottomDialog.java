package com.yuanbaogo.mine.order.pay;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.mine.R;

public class OrderCancelBottomDialog extends BottomSheetDialog implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private RadioGroup orderDlRg;
    private TextView orderDlTitle;
    private TextView orderDlNotCancel;
    private TextView orderDlNextCancel;
    private OnNextRefund mOnNextRefund;
    private int checkId;
    private String mTitle;

    public OrderCancelBottomDialog(@NonNull Context context) {
        super(context, 0);
    }

    public OrderCancelBottomDialog(@NonNull Context context,String title) {
        super(context, 0);
        this.mTitle = title;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_layout_dialog_cancel_order);
        initView();
    }

    private void initView() {
        orderDlTitle = findViewById(R.id.order_dl_title);
        orderDlRg = findViewById(R.id.order_dl_rg);
        orderDlNotCancel = findViewById(R.id.order_dl_not_cancel);
        orderDlNextCancel = findViewById(R.id.order_dl_next_cancel);
        orderDlNotCancel.setOnClickListener(this);
        orderDlNextCancel.setOnClickListener(this);
        orderDlRg.setOnCheckedChangeListener(this);
        orderDlTitle.setText(mTitle);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.order_dl_not_cancel) {
            cancel();
        } else if (id == R.id.order_dl_next_cancel) {
            cancel();
            if (mOnNextRefund == null) {
                return;
            }
            if (checkId <= 0) {
                ToastUtil.showToast(R.string.sales_reason_toast);
                return;
            }
            RadioButton radioButton = findViewById(checkId);
            String toString = radioButton.getText().toString();
            mOnNextRefund.onNextRefund(toString, getReasonType(toString));
        }
    }

    private int getReasonType(String string) {
        switch (string) {
            case "不想要了":
                return 1;
            case "地址信息填写错误":
                return 2;
            case "商品选错/多选":
                return 3;
            case "商品降价":
                return 4;
            case "没用/少用/错用优惠券":
                return 5;
            default:

                return 6;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        orderDlNextCancel.setEnabled(true);
        checkId = checkedId;
    }

    public void setOnNextRefundListener(OnNextRefund onReasonSelect) {
        this.mOnNextRefund = onReasonSelect;
    }

    public interface OnNextRefund {
        void onNextRefund(String reason, int position);
    }

}

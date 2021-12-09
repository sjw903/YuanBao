package com.yuanbaogo.mine.order.finish.refund.dialog;

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

public class SalesStatusBottomDialog extends BottomSheetDialog implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private OnStatusSelect mOnStatusSelect;
    private int checkId;

    public SalesStatusBottomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.mine_layout_dialog_refund_status, null);
        setContentView(view);
        initView(view);
    }

    private void initView(View view) {
        RadioGroup salesStatusDlRg = view.findViewById(R.id.sales_status_dl_rg);
        TextView salesStatusTvSure = view.findViewById(R.id.sales_status_tv_sure);
        salesStatusTvSure.setOnClickListener(this);
        salesStatusDlRg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sales_status_tv_sure) {
            // 货物状态确定按钮
            if (checkId <= 0) {
                ToastUtil.showToast(R.string.sales_reason_toast);
                return;
            }
            cancel();
            if (mOnStatusSelect == null) {
                return;
            }
            RadioButton radioButton = findViewById(checkId);
            mOnStatusSelect.onStatusSelectListener(radioButton.getText().toString());
        }
    }

    public void setOnStatusSelect(OnStatusSelect onStatusSelect) {
        this.mOnStatusSelect = onStatusSelect;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        checkId = checkedId;
    }

    public interface OnStatusSelect {
        void onStatusSelectListener(String status);
    }

}

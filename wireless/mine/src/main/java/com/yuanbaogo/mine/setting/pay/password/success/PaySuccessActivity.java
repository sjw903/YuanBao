package com.yuanbaogo.mine.setting.pay.password.success;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.NullImplContract;
import com.yuanbaogo.mine.setting.NullImplPresenter;

public class PaySuccessActivity extends MvpBaseActivityImpl<NullImplContract.View, NullImplPresenter> implements View.OnClickListener {

    private TextView mPaySuccessBtn;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_pay_success;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mPaySuccessBtn = findViewById(R.id.pay_success_btn);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mPaySuccessBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.pay_success_btn) {
            finish();
        }
    }

}
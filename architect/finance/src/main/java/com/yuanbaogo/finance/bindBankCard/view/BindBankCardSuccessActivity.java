package com.yuanbaogo.finance.bindBankCard.view;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.finance.R;
import com.yuanbaogo.finance.bindBankCard.contract.BindBankCardSuccessContract;
import com.yuanbaogo.finance.bindBankCard.presenter.BindBankCardSuccessPresenter;
import com.yuanbaogo.zui.view.TitleBar;

public class BindBankCardSuccessActivity extends MvpBaseActivityImpl<BindBankCardSuccessContract.View, BindBankCardSuccessPresenter> {

    private TitleBar mBindSuccessTitleBar;
    private TextView mBindSuccessCountdownTv;

    private int time = 3;

    private Handler mHandler = new Handler();
    private Runnable myRunnale = new Runnable() {
        @Override
        public void run() {
            time--;
            if (time > 0) {
                mBindSuccessCountdownTv.setText(time + "");
                mHandler.postDelayed(myRunnale, 1000);
            } else {
                finish();
            }
        }
    };

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_bind_bank_card_success;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mBindSuccessTitleBar = findViewById(R.id.bind_success_title_bar);
        mBindSuccessCountdownTv = findViewById(R.id.bind_success_countdown_tv);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mBindSuccessTitleBar.setLeftImageRes(R.mipmap.icon_left_close);
        mHandler.post(myRunnale);
    }

}
package com.yuanbaogo.mine.integral.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanbaogo.commonlib.base.BaseActivityImpl;
import com.yuanbaogo.mine.R;

public class PayStatusActivity extends BaseActivityImpl implements View.OnClickListener{
    ImageView iv_back;
    TextView mRechargeSuccessCountdownTv;

    private int time = 3;

    private Handler mHandler = new Handler();
    private Runnable myRunnale = new Runnable() {
        @Override
        public void run() {
            time--;
            if (time > 0) {
                mRechargeSuccessCountdownTv.setText(time + "");
                mHandler.postDelayed(myRunnale, 1000);
            } else {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_pay_status);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        mRechargeSuccessCountdownTv = findViewById(R.id.recharge_success_countdown_tv);
        iv_back.setOnClickListener(this);
        mHandler.post(myRunnale);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
        }
    }
}

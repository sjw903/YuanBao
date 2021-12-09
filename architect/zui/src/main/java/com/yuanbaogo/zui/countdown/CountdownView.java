package com.yuanbaogo.zui.countdown;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yuanbaogo.zui.R;

/**
 * @Description: 倒计时控件
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/30/21 2:59 PM
 * @Modifier:
 * @Modify:
 */
public class CountdownView extends LinearLayout {

    Context context;

    TextView mTvSeckillHour;

    TextView mTvSeckillMinute;

    TextView mTvSeckillSecond;

    int time;

    int type = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    time = time - 1;
                    if (time >= 0) {
                        countDown();
                        sendEmptyMessageDelayed(0, 1000);
                    }
                    break;
                case 1:
                    handler.removeMessages(0);
                    handler = null;
                    onCallCountdown.onCallEnd();
                    break;
            }

        }
    };

    public CountdownView(Context context) {
        this(context, null);
    }

    public CountdownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.countdown_view, this);
        mTvSeckillHour = view.findViewById(R.id.tv_seckill_hour);
        mTvSeckillMinute = view.findViewById(R.id.tv_seckill_minute);
        mTvSeckillSecond = view.findViewById(R.id.tv_seckill_second);
    }

    public void startCountDown(int time) {
        this.time = time;
        if (type == 0) {
            handler.sendEmptyMessage(0);
            type = 1;
        }
    }

    /**
     * 倒计时
     */
    private void countDown() {
        int countDownNum = time;
        int hours = countDownNum / 3600;
        int minute = (countDownNum % 3600) / 60;
        int second = countDownNum % 60;
        mTvSeckillHour.setText(String.format("%02d", hours));
        mTvSeckillMinute.setText(String.format("%02d", minute));
        mTvSeckillSecond.setText(String.format("%02d", second));
        if (time == 0) {
            if (handler != null) {
                handler.sendEmptyMessage(1);
            }
        }
    }

    OnCallCountdown onCallCountdown;

    public void setOnCallCountdown(OnCallCountdown onCallCountdown) {
        this.onCallCountdown = onCallCountdown;
    }

    public interface OnCallCountdown {

        void onCallEnd();

    }

    /**
     * 关闭计时器
     */
    public void onDestroyHandler() {
        if (handler != null) {
            handler.removeMessages(0);
            handler.removeMessages(1);
            handler = null;
        }
    }

}

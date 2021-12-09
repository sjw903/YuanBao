package com.yuanbaogo.app.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.yuanbaogo.app.R;
import com.yuanbaogo.app.view.MainActivity;
import com.yuanbaogo.datacenter.local.main.MainSPUtils;
import com.yuanbaogo.zui.dialog.PactDialogView;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;

import com.yuanbaogo.zui.dialog.model.PactBean;

public class WelcomeActivity extends AppCompatActivity implements OnCallDialog, PactDialogView.OnCallCancel {


    final Handler handler = new Handler();
    private PactDialogView mPactDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wel_come);
        setStatusBar();

        if (MainSPUtils.getPact()) {
            initPactDialog();
        } else {
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    WelcomeActivity.this.finish();
                }
            }, 1000);
        }
    }

    private void initPactDialog() {
        mPactDialogView = new PactDialogView();
        mPactDialogView.setCancelable(false);
        mPactDialogView.show(WelcomeActivity.this.getSupportFragmentManager(), "pact");
        PactBean pactBean = new PactBean()
                .setTvTitle(true)
                .setTvTitles("用户协议与隐私保护")
                .setImgCancel(false)
                .setContentFileName("pact.txt")
                .setLine(true)
                .setCancel(true)
                .setTvCancels("不同意")
                .setDetermine(true)
                .setTvDetermines("同意并继续")
                .setType(2);
        mPactDialogView.setPact(pactBean);
        mPactDialogView.setOnCallDialog(this);
        mPactDialogView.setOnCallCancel(this);
    }

    /**
     * 同意并继续
     */
    @Override
    public void onCallDetermine() {
        MainSPUtils.savePact(false);
        mPactDialogView.dismiss();
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        WelcomeActivity.this.finish();
    }

    /**
     * 不同意
     */
    @Override
    public void onCallCancel() {
        mPactDialogView.dismiss();
        System.exit(0);
    }


    /**
     * 设置透明状态栏
     */
    protected void setStatusBar() {
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));//设置状态栏颜色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色

    }
}

package com.yuanbaogo.mine.setting.pay.set;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.commonlib.utils.EditTextUtils;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.toast.ToastView;

@Route(path = YBRouter.PAY_SET_ACTIVITY)
public class PaySetActivity extends MvpBaseActivityImpl<PaySetContract.View, PaySetPresenter> implements PaySetContract.View, View.OnClickListener, DialogInterface.OnDismissListener, DialogInterface.OnShowListener {

    private static final String TAG = "PaySetActivity";
    private TextView mPaySetTvPhone;
    private TextView mPaySetTvCode;
    private AlertDialog mDialog;
    private VerCodeTimer mVerCodeTimer;
    private EditText paySetDialogEtCode;
    private TextView paySetDialogTvSecond;
    private TextView paySetDialogTvSave;
    private ImageView paySetDialogIvClear;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_pay_set;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mPaySetTvPhone = findViewById(R.id.pay_set_tv_phone);
        mPaySetTvCode = findViewById(R.id.pay_set_tv_code);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mPaySetTvCode.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //mPresenter.setJiYan();
        mPaySetTvPhone.setText(UserStore.getUserPhone());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.pay_set_tv_code) {
            mPresenter.sendCode();
        } else if (id == R.id.pay_set_dialog_iv_close) {
            mDialog.dismiss();
        } else if (id == R.id.pay_set_dialog_tv_second) {
            Log.e(TAG, "onClick: 点击获取验证码按钮");
            //mPresenter.getVerificationCode();
            mPresenter.sendCode();
        } else if (id == R.id.pay_set_dialog_tv_save) {
            if (!TextUtils.isEmpty(paySetDialogEtCode.getText()) && paySetDialogEtCode.getText().toString().length() == 6) {
                mPresenter.checkCaptcha(paySetDialogEtCode.getText().toString());
            } else {
                ToastView.showToast("请输入正确的验证码");
            }
        } else if (id == R.id.pay_set_dialog_iv_clear) {
            paySetDialogEtCode.setText("");
        }
    }

    private void showCodeDialog() {
        AlertDialog.Builder customizeDialog = new AlertDialog.Builder(this);
        final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.mine_dialog_pay_set, null);
        customizeDialog.setView(dialogView);
        customizeDialog.setCancelable(false);

        TextView paySetDialogTvTip = dialogView.findViewById(R.id.pay_set_dialog_tv_tip);
        ImageView paySetDialogIvClose = dialogView.findViewById(R.id.pay_set_dialog_iv_close);
        paySetDialogEtCode = dialogView.findViewById(R.id.pay_set_dialog_et_code);
        paySetDialogTvSecond = dialogView.findViewById(R.id.pay_set_dialog_tv_second);
        paySetDialogTvSave = dialogView.findViewById(R.id.pay_set_dialog_tv_save);
        paySetDialogIvClear = dialogView.findViewById(R.id.pay_set_dialog_iv_clear);
        paySetDialogTvTip.setText(getString(R.string.pay_set_dialog_tip, UserStore.getUserPhone()));
        paySetDialogIvClose.setOnClickListener(this);
        paySetDialogTvSecond.setOnClickListener(this);
        paySetDialogTvSave.setOnClickListener(this);
        paySetDialogIvClear.setOnClickListener(this);
        mDialog = customizeDialog.show();
        mDialog.setCancelable(false);
        mDialog.setOnDismissListener(this);
        mDialog.setOnShowListener(this);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mVerCodeTimer == null) {
            return;
        }
        mVerCodeTimer.cancel();
    }

    @Override
    public void startTimer() {
        showCodeDialog();
        ToastView.showToast("发送验证码成功，请注意查收");
        mVerCodeTimer = new VerCodeTimer(60000, 1000);
        mVerCodeTimer.start();
        paySetDialogTvSecond.setClickable(false);
    }

    @Override
    public void checkCaptcha(boolean isSuccess) {
        if (isSuccess) {
            mDialog.dismiss();
            RouterHelper.toPayPassword();
            finish();
        } else {
            ToastView.showToast("验证码错误，请重新输入");
        }
    }


    @Override
    public void onShow(DialogInterface dialog) {
        EditTextUtils.focusEditText(paySetDialogEtCode);
    }

    private class VerCodeTimer extends CountDownTimer {

        /**
         * @param millisInFuture    设置的此次倒计时的总时长，比如60秒就设置为60000
         * @param countDownInterval 设置的时间间隔，比如一般为1秒,根据需要自定义。
         */
        public VerCodeTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            paySetDialogTvSecond.setText(getString(R.string.pay_set_dialog_second, (int) (millisInFuture / 1000) - 1));
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int interval = (int) (millisUntilFinished / 1000);
            paySetDialogTvSecond.setText(getString(R.string.pay_set_dialog_second, interval));
        }

        //倒计时结束时做的操作
        @Override
        public void onFinish() {
            paySetDialogTvSecond.setText(getString(R.string.update_phone_reacquire));
            paySetDialogTvSecond.setClickable(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDialog != null) {
            mDialog.cancel();
        }
        if (mVerCodeTimer != null) {
            mVerCodeTimer.cancel();
        }
    }

}
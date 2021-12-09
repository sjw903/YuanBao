package com.yuanbaogo.mine.setting.account.update;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libbase.baseutil.ValidatePhoneUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.toast.ToastView;

@Route(path = YBRouter.UPDATE_PHONE_ACTIVITY)
public class UpdatePhoneActivity extends MvpBaseActivityImpl<UpdatePhoneContract.View, UpdatePhonePresenter>
        implements UpdatePhoneContract.View, View.OnClickListener, TextWatcher {

    private static final String TAG = "UpdatePhoneActivity";
    private TextView mUpdateEtPhone;
    private EditText mUpdateEtCode;
    private TextView mUpdateTvGetCode;
    private TextView mUpdateTvUpdate;

    private VerCodeTimer mVerCodeTimer;

    private String mCode;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_update_phone;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mUpdateEtPhone = findViewById(R.id.update_et_phone);
        mUpdateEtCode = findViewById(R.id.update_et_code);
        mUpdateTvGetCode = findViewById(R.id.update_tv_get_code);
        mUpdateTvUpdate = findViewById(R.id.update_tv_update);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mUpdateTvGetCode.setOnClickListener(this);
        mUpdateTvUpdate.setOnClickListener(this);
        mUpdateEtCode.addTextChangedListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initViews(Bundle savedInstanceState) {
        mUpdateTvGetCode.setClickable(false);
        mUpdateEtPhone.setText(getString(R.string.update_phone_hint, UserStore.getEncryptionPhone()));
        initGetCode();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.update_tv_get_code) {
            mPresenter.sendCode();
        } else if (id == R.id.update_tv_update) {
            if (!TextUtils.isEmpty(mUpdateEtCode.getText()) && mUpdateEtCode.getText().toString().length() == 6) {
                mPresenter.checkCaptcha(mUpdateEtCode.getText().toString());
            } else {
                ToastView.showToast("请输入正确的验证码");
            }
        }
    }

    private void getCode() {
        mVerCodeTimer = new VerCodeTimer(60000, 1000);
        mVerCodeTimer.start();
        mUpdateTvGetCode.setClickable(false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mCode = mUpdateEtCode.getText().toString().trim();
        mUpdateTvUpdate.setEnabled(ValidatePhoneUtil.isCheckCode(mCode));
    }

    private void initGetCode() {
        mUpdateTvGetCode.setTextColor(getColor(R.color.wing_selected_title));
        mUpdateTvGetCode.setBackgroundResource(R.drawable.icon_update_phone_code_selected);
    }

    @Override
    public void startTimer() {
        ToastView.showToast("发送验证码成功，请注意查收");
        getCode();
    }

    @Override
    public void checkCaptcha(boolean isSuccess) {
        if (isSuccess) {
            RouterHelper.toNewPhone();
            finish();
        } else {
            ToastView.showToast("验证码错误，请重新输入");
        }
    }

    private class VerCodeTimer extends CountDownTimer {

        /**
         * @param millisInFuture    设置的此次倒计时的总时长，比如60秒就设置为60000
         * @param countDownInterval 设置的时间间隔，比如一般为1秒,根据需要自定义。
         */
        public VerCodeTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            mUpdateTvGetCode.setText(getString(R.string.update_phone_second, (int) (millisInFuture / 1000) - 1));
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int interval = (int) (millisUntilFinished / 1000);
            mUpdateTvGetCode.setTextColor(getColor(R.color.wing_sub_title_hint));
            mUpdateTvGetCode.setBackgroundResource(R.drawable.icon_update_phone_code_default);
            mUpdateTvGetCode.setText(getString(R.string.update_phone_second, interval));
        }

        //倒计时结束时做的操作
        @Override
        public void onFinish() {
            mUpdateTvGetCode.setTextColor(getColor(R.color.wing_selected_title));
            mUpdateTvGetCode.setBackgroundResource(R.drawable.icon_update_phone_code_selected);
            mUpdateTvGetCode.setText(getString(R.string.update_phone_reacquire));
            mUpdateTvGetCode.setClickable(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (mVerCodeTimer != null) {
            mVerCodeTimer.cancel();
        }
        super.onBackPressed();
    }

}
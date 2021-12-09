package com.yuanbaogo.mine.setting.account.newphone;

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
import com.yuanbaogo.datacenter.local.user.UserInfo;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.libbase.baseutil.ValidatePhoneUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.toast.ToastView;

@Route(path = YBRouter.NEW_PHONE_ACTIVITY)
public class NewPhoneActivity extends MvpBaseActivityImpl<NewPhoneContract.View, NewPhonePresenter>
        implements NewPhoneContract.View, View.OnClickListener, TextWatcher {

    private EditText mNewEtPhone;
    private EditText mNewEtCode;
    private TextView mNewTvGetCode;
    private TextView mNewTvUpdate;

    private VerCodeTimer mVerCodeTimer;

    private boolean validateMobileNumber = false;
    private String mPhone;
    private String mCode;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_new_phone;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mNewEtPhone = findViewById(R.id.new_et_phone);
        mNewEtCode = findViewById(R.id.new_et_code);
        mNewTvGetCode = findViewById(R.id.new_tv_get_code);
        mNewTvUpdate = findViewById(R.id.new_tv_update);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mNewTvGetCode.setOnClickListener(this);
        mNewTvUpdate.setOnClickListener(this);
        mNewEtPhone.addTextChangedListener(this);
        mNewEtCode.addTextChangedListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mNewTvGetCode.setClickable(false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mPhone = mNewEtPhone.getText().toString().trim();
        mCode = mNewEtCode.getText().toString().trim();
        validateMobileNumber = ValidatePhoneUtil.validateMobileNumber(mPhone);
        mNewTvUpdate.setEnabled(validateMobileNumber && ValidatePhoneUtil.isCheckCode(mCode));
        initGetCode();
    }

    private void initGetCode() {
        mNewEtCode.setClickable(validateMobileNumber);
        if (validateMobileNumber && !mNewTvGetCode.getText().toString().contains("已发送")) {
            mNewTvGetCode.setTextColor(getColor(R.color.wing_selected_title));
            mNewTvGetCode.setBackgroundResource(R.drawable.icon_update_phone_code_selected);
        } else {
            mNewTvGetCode.setTextColor(getColor(R.color.wing_sub_title_hint));
            mNewTvGetCode.setBackgroundResource(R.drawable.icon_update_phone_code_default);
        }
    }

    @Override
    public void startTimer() {
        ToastView.showToast("发送验证码成功，请注意查收");
        getCode();
    }

    @Override
    public void checkCaptcha(boolean isSuccess, String code) {
        if (isSuccess) {
            mPresenter.bindNewPhone(mPhone, mCode);
        } else {
            ToastView.showToast("验证码输入错误，请重新输入");
        }
    }

    @Override
    public void bindSuccess(String newPhone) {
        ToastView.showToast(R.string.new_phone_bind_success);
        finish();
        UserInfo user = UserStore.getUser();
        if (user != null) {
            // 如果修改成功，修改保存的用户信息
            user.setPhone(newPhone);
            UserStore.saveUser(user);
        }
    }

    private class VerCodeTimer extends CountDownTimer {

        /**
         * @param millisInFuture    设置的此次倒计时的总时长，比如60秒就设置为60000
         * @param countDownInterval 设置的时间间隔，比如一般为1秒,根据需要自定义。
         */
        public VerCodeTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            mNewTvGetCode.setText(getString(R.string.update_phone_second, (int) (millisInFuture / 1000) - 1));
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int interval = (int) (millisUntilFinished / 1000);
            mNewTvGetCode.setTextColor(getColor(R.color.wing_sub_title_hint));
            mNewTvGetCode.setBackgroundResource(R.drawable.icon_update_phone_code_default);
            mNewTvGetCode.setText(getString(R.string.update_phone_second, interval));
        }

        //倒计时结束时做的操作
        @Override
        public void onFinish() {
            mNewTvGetCode.setTextColor(getColor(R.color.wing_selected_title));
            mNewTvGetCode.setBackgroundResource(R.drawable.icon_update_phone_code_selected);
            mNewTvGetCode.setText(getString(R.string.update_phone_reacquire));
            mNewTvGetCode.setClickable(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (mVerCodeTimer != null) {
            mVerCodeTimer.cancel();
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.new_tv_get_code) {
            if (!validateMobileNumber) {
                ToastView.showToast("请输入正确的手机号码");
                return;
            }
            mPresenter.sendCode(mPhone);
        } else if (id == R.id.new_tv_update) {
            if (!TextUtils.isEmpty(mCode) && mCode.length() == 6) {
                //mPresenter.checkCaptcha(mPhone, mCode);
                mPresenter.bindNewPhone(mPhone, mCode);
            } else {
                ToastView.showToast("请输入正确的验证码");
            }
        }
    }

    private void getCode() {
        if (!validateMobileNumber) {
            return;
        }
        mVerCodeTimer = new VerCodeTimer(60000, 1000);
        mVerCodeTimer.start();
        mNewTvGetCode.setClickable(false);
    }
}
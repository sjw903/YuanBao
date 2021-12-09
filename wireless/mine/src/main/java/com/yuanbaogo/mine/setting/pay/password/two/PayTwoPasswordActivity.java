package com.yuanbaogo.mine.setting.pay.password.two;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.libbase.baseutil.ValidatePhoneUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.pay.password.PayPasswordActivity;
import com.yuanbaogo.mine.setting.pay.password.success.PaySuccessActivity;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.board.PasswordKeyboardView;
import com.yuanbaogo.zui.edittext.PwdEditText;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.TitleBar;

import java.util.Objects;

@Route(path = YBRouter.PAY_TWO_PASSWORD_ACTIVITY)
public class PayTwoPasswordActivity extends MvpBaseActivityImpl<PayTwoPasswordContract.View, PayTwoPasswordSetPresenter>
        implements PayTwoPasswordContract.View, View.OnClickListener, TextWatcher, PasswordKeyboardView.IOnKeyboardListener {

    private static final String TAG = "PayTwoPasswordActivity";
    private TitleBar mPayTwoPassTitleBar;
    private PwdEditText mPayTwoPassPet;
    private TextView mPayTwoPassSave;
    private String mPassword;
    private String mKeyPassword = "";
    private PasswordKeyboardView mPayTwoPassPkv;
    @Autowired(name = YBRouter.PayTwoPasswordActivityParams.password)
    String password = "";

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_pay_two_password;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mPayTwoPassTitleBar = findViewById(R.id.pay_two_pass_title_bar);
        mPayTwoPassPet = findViewById(R.id.pay_two_pass_pet);
        mPayTwoPassSave = findViewById(R.id.pay_two_pass_save);
        mPayTwoPassPkv = findViewById(R.id.pay_two_pass_pkv);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mPayTwoPassTitleBar.setRightTextOnClickListener(this);
        mPayTwoPassSave.setOnClickListener(this);
        mPayTwoPassPet.addTextChangedListener(this);
        mPayTwoPassPkv.setIOnKeyboardListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mPayTwoPassPet.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.title_bar_tv_right) {
            mPayTwoPassPet.setText("");
            mKeyPassword = "";
            finish();
        } else if (id == R.id.pay_two_pass_save) {
            if (password.equals(mPassword)) {
                mPresenter.setPayPassword(password);
            } else {
                ToastView.showToast(R.string.pay_six_two_please_reset);
                finish();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mPassword = Objects.requireNonNull(mPayTwoPassPet.getText()).toString().trim();
        mPayTwoPassSave.setEnabled(ValidatePhoneUtil.isCheckCode(mPassword));
    }

    @Override
    public void setSuccess() {
        PayPasswordActivity.mPayPasswordActivity.finish();
        startActivity(new Intent(this, PaySuccessActivity.class));
        finish();
    }

    @Override
    public void setFail(String error) {
        ToastView.showToast(error);
    }

    @Override
    public void onInsertKeyEvent(String text) {
        this.mKeyPassword += text;
        mPayTwoPassPet.setText(mKeyPassword);
    }

    @Override
    public void onDeleteKeyEvent() {
        if (mKeyPassword.length() < 1) {
            return;
        }
        mKeyPassword = mKeyPassword.substring(0, mKeyPassword.length() - 1);
        mPayTwoPassPet.setText(mKeyPassword);
    }
}
package com.yuanbaogo.mine.setting.pay.password;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.NullImplContract;
import com.yuanbaogo.mine.setting.NullImplPresenter;
import com.yuanbaogo.zui.board.PasswordKeyboardView;
import com.yuanbaogo.zui.edittext.PwdEditText;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.TitleBar;

import java.util.regex.Pattern;

@Route(path = YBRouter.PAY_PASSWORD_ACTIVITY)
public class PayPasswordActivity extends MvpBaseActivityImpl<NullImplContract.View, NullImplPresenter>
        implements View.OnClickListener, TextWatcher, PasswordKeyboardView.IOnKeyboardListener {

    private static final String TAG = "PayPasswordActivity";
    private TitleBar mPayPassTitleBar;
    private PwdEditText mPayPassPet;
    private PasswordKeyboardView mPayPassPkv;
    private String mPassword = "";

    public static PayPasswordActivity mPayPasswordActivity;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_pay_password;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mPayPassTitleBar = findViewById(R.id.pay_pass_title_bar);
        mPayPassPet = findViewById(R.id.pay_pass_pet);
        mPayPassPkv = findViewById(R.id.pay_pass_pkv);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mPayPassTitleBar.setRightTextOnClickListener(this);
        mPayPassPet.addTextChangedListener(this);
        mPayPassPkv.setIOnKeyboardListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mPayPasswordActivity = this;
        mPayPassPet.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.title_bar_tv_right) {
            mPassword = "";
            mPayPassPet.setText("");
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
        if (TextUtils.isEmpty(mPayPassPet.getText()) || mPayPassPet.getText().toString().length() != 6) {
            return;
        }
        String code = mPayPassPet.getText().toString().trim();
        if (isSameNumber(code)) {
            ToastView.showToast(R.string.pay_toast_same_number);
            return;
        }
        if (isConsecutiveNumbers(code)) {
            ToastView.showToast(R.string.pay_toast_consecutive_number);
            return;
        }
        RouterHelper.toPayTwoPassword(mPayPassPet.getText().toString());
        mPassword = "";
        mPayPassPet.setText("");
    }

    /**
     * 是否是连续数字
     */
    private boolean isConsecutiveNumbers(String numOrStr) {
        boolean isConsecutive = true;
        for (int i = 0; i < numOrStr.length(); i++) {
            if (i <= 0) {
                continue;
            }// 判断如123456
            int num = Integer.parseInt(numOrStr.charAt(i) + "");
            int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") + 1;
            if (num != num_) {
                isConsecutive = false;
                break;
            }
        }

        //987654跑到这里就不是连续数字 倒序
        boolean isConsecutiveReverse = true;
        for (int i = 0; i < numOrStr.length(); i++) {
            if (i <= 0) {
                continue;
            }// 判断如654321
            int num = Integer.parseInt(numOrStr.charAt(i) + "");
            int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") - 1;
            if (num != num_) {
                isConsecutiveReverse = false;
                break;
            }
        }
        return isConsecutive || isConsecutiveReverse;
    }

    /**
     * 判断是否相同数字
     *
     * @param num /
     * @return /
     */
    private boolean isSameNumber(String num) {
        //判断是否相同
        String regex = num.charAt(0) + "{6}";
        return Pattern.matches(regex, num);
    }

    @Override
    public void onInsertKeyEvent(String text) {
        this.mPassword += text;
        mPayPassPet.setText(mPassword);
    }

    @Override
    public void onDeleteKeyEvent() {
        if (mPassword.length() < 1) {
            return;
        }
        mPassword = mPassword.substring(0, mPassword.length() - 1);
        mPayPassPet.setText(mPassword);
    }
}
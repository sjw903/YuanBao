package com.yuanbaogo.login.bindphone.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.libbase.basemvp.MvpBaseActivity;
import com.yuanbaogo.login.R;
import com.yuanbaogo.login.bindphone.contract.BindPhoneContract;
import com.yuanbaogo.login.bindphone.presenter.BindPhonePresenter;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;

/**
 * @Description: 三方登录绑定手机号页面
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/16/21 4:25 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.CHANGE_PHONE_ACTIVITY)
public class BindPhoneActivity extends MvpBaseActivity<BindPhoneContract.View, BindPhonePresenter>
        implements BindPhoneContract.View, View.OnClickListener {

    /**
     * 标题栏
     */
    private HeadView mBindPhoneHeadView;

    /**
     * 手机号输入框
     */
    private EditText mBindPhoneEditPhoneNum;

    /**
     * 验证码输入框
     */
    private EditText mBindPhoneEditVerificationCode;

    /**
     * 修改手机号
     */
    private Button mBindPhoneButVerification;

    private Button mBindPhoneButBind;

    private int mCanGetCode = 2;//是否能获取验证码

    int mCanBind = 2;//修改按钮是否能点击

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mBindPhoneHeadView = findViewById(R.id.bind_phone_head_view);
        mBindPhoneEditPhoneNum = findViewById(R.id.bind_phone_edit_phone_num);
        mBindPhoneEditVerificationCode = findViewById(R.id.bind_phone_edit_verification_code);
        mBindPhoneButVerification = findViewById(R.id.bind_phone_but_verification);
        mBindPhoneButBind = findViewById(R.id.bind_phone_but_change);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mBindPhoneEditPhoneNum.addTextChangedListener(mTextWatcher);
        mBindPhoneEditVerificationCode.addTextChangedListener(mTextWatcher);
        mBindPhoneButVerification.setOnClickListener(this);
        mBindPhoneButBind.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bind_phone_but_verification) {
            mPresenter.verification(mCanGetCode);
        } else if (id == R.id.bind_phone_but_change) {
            mPresenter.bindPhone(mCanBind, mBindPhoneEditPhoneNum.getText().toString(),
                    mBindPhoneEditVerificationCode.getText().toString());
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
    }

    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(false)
                .setImgRight(false);
        mBindPhoneHeadView.setHead(headBean);
    }

    TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mCanGetCode = mPresenter.changeCanGetCode(mBindPhoneEditPhoneNum.getText().toString());
            mCanBind = mPresenter.changeCanBind(mBindPhoneEditPhoneNum.getText().toString(),
                    mBindPhoneEditVerificationCode.getText().toString());
        }

    };

    /**
     * 改变登录按钮颜色
     *
     * @param isCan
     */
    public void bindPhoneColor(boolean isCan) {
        if (isCan) {
            mBindPhoneButBind.setBackground(getResources().getDrawable(R.drawable.selector_bg_but_login));
        } else {
            mBindPhoneButBind.setBackground(getResources().getDrawable(R.drawable.shape_gradient_bg_but_alpha_50));
        }
    }

    /**
     * 改变获取验证码按钮颜色
     *
     * @param isCan
     */
    public void bindPhoneCodeColor(boolean isCan) {
        if (isCan) {
            mBindPhoneButVerification.setTextColor(getResources().getColor(R.color.colorEA5504, null));
            mBindPhoneButVerification.setBackgroundResource(R.drawable.shape_gradient_bg_but_line_orrange);
        } else {
            mBindPhoneButVerification.setTextColor(getResources().getColor(R.color.colorAAAAAA, null));
            mBindPhoneButVerification.setBackgroundResource(R.drawable.shape_gradient_bg_but_line_gray);
        }
    }

}
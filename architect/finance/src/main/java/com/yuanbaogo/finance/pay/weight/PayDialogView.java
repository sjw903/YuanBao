package com.yuanbaogo.finance.pay.weight;

import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.finance.R;
import com.yuanbaogo.zui.board.PasswordKeyboardView;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;
import com.yuanbaogo.zui.edittext.PwdEditText;

/**
 * @Description: 支付弹窗
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/9/21 4:27 PM
 * @Modifier:
 * @Modify:
 */
public class PayDialogView extends DialogsFragment implements View.OnClickListener,
        PasswordKeyboardView.IOnKeyboardListener, TextWatcher {

    ImageView dialogPayViewImgCancel;

    /**
     * 错误提示
     */
    TextView dialogPayViewTvErrors;

    TextView tv_tips;

    /**
     * 次数提示
     */
    TextView dialogPayViewTvNumberOfErrors;

    /**
     * 提示标题
     */
    TextView dialogPayViewTvTitle;

    /**
     * 网络提示
     */
    TextView dialogPayViewTvTimeOut;

    /**
     * 输入框
     */
    public PwdEditText dialogPayViewEditPwd;

    /**
     * 0 默认第一次输入支付密码
     * 1 密码支付错误
     * 2 密码错误仅剩一次支付机会
     * 3 密码输入已达上限
     * 4 账户被锁定
     */
    int type = 0;

    PasswordKeyboardView payPassPkv;

    String mPassword = "";

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 输入密码次数
     */
    int pwdNumber = 5;

    public void setPwdNumber(int pwdNumber) {
        this.pwdNumber = 5 - pwdNumber;
        initRefresh(type);
    }

    @Override
    public int getLayout() {
        return R.layout.pay_pwd_dialog_view;
    }

    @Override
    protected void intViews(View view) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        dialogPayViewImgCancel = view.findViewById(R.id.dialog_pay_view_img_cancel);

        dialogPayViewTvErrors = view.findViewById(R.id.tv_error);
        tv_tips = view.findViewById(R.id.tv_tips);
        dialogPayViewTvNumberOfErrors = view.findViewById(R.id.tv_number_of_error);
        dialogPayViewTvTitle = view.findViewById(R.id.dialog_pay_view_tv_title);
        dialogPayViewTvTimeOut = view.findViewById(R.id.dialog_pay_view_tv_time_out);

        dialogPayViewEditPwd = view.findViewById(R.id.edit_pwd_pay_view);
        payPassPkv = view.findViewById(R.id.pay_pass_pkv);

        dialogPayViewEditPwd.setInputType(InputType.TYPE_NULL);
        dialogPayViewEditPwd.setEnabled(false);
    }

    @Override
    protected void setTexts() {
        initRefresh(type);
    }

    @Override
    protected void setOnClicks() {
        dialogPayViewImgCancel.setOnClickListener(this);
        payPassPkv.setIOnKeyboardListener(this);
        dialogPayViewEditPwd.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dialog_pay_view_img_cancel) {
            dismiss();
        }
    }

    @Override
    public int getHeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    public int getWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public void onInsertKeyEvent(String text) {
        this.mPassword += text;
        dialogPayViewEditPwd.setText(mPassword);
    }

    @Override
    public void onDeleteKeyEvent() {
        if (mPassword.length() < 1) {
            return;
        }
        mPassword = mPassword.substring(0, mPassword.length() - 1);
        dialogPayViewEditPwd.setText(mPassword);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(dialogPayViewEditPwd.getText())
                || dialogPayViewEditPwd.getText().toString().length() != 6) {
            return;
        }
        onRefreshData.onRefresh(type, dialogPayViewEditPwd.getText().toString());
    }

    public interface OnRefreshData {
        void onRefresh(int type, String pwd);
    }

    OnRefreshData onRefreshData;

    public void setOnRefreshData(OnRefreshData onRefreshData) {
        this.onRefreshData = onRefreshData;
    }

    public void initRefresh(int type) {
        dialogPayViewEditPwd.clearText();
        mPassword = "";
        if (type == 0) {
            dialogPayViewTvNumberOfErrors.setVisibility(View.GONE);
            tv_tips.setVisibility(View.GONE);
            dialogPayViewTvTitle.setVisibility(View.VISIBLE);
            dialogPayViewEditPwd.setVisibility(View.VISIBLE);
            dialogPayViewTvErrors.setVisibility(View.GONE);
            dialogPayViewTvTimeOut.setVisibility(View.GONE);
        } else if (type == 1) {
            tv_tips.setVisibility(View.VISIBLE);
            dialogPayViewTvErrors.setVisibility(View.GONE);
            dialogPayViewTvNumberOfErrors.setVisibility(View.VISIBLE);
            dialogPayViewTvNumberOfErrors.setText("今日还可以输入" + pwdNumber + "次");
            dialogPayViewTvTitle.setVisibility(View.VISIBLE);
            dialogPayViewTvTitle.setText("请重新输入6位数字支付密码");
            dialogPayViewTvTimeOut.setVisibility(View.GONE);
            dialogPayViewEditPwd.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            tv_tips.setVisibility(View.VISIBLE);
            tv_tips.setText("密码已输入错误4次");
            dialogPayViewTvErrors.setVisibility(View.GONE);
            dialogPayViewTvNumberOfErrors.setVisibility(View.VISIBLE);
            dialogPayViewTvNumberOfErrors.setText("今日仅可再输入1次，再次错误账户将被锁定");
            dialogPayViewTvTitle.setVisibility(View.VISIBLE);
            dialogPayViewTvTitle.setText("请重新输入6位数字支付密码");
            dialogPayViewTvTimeOut.setVisibility(View.GONE);
            dialogPayViewEditPwd.setVisibility(View.VISIBLE);

        } else if (type == 3) {

            dialogPayViewEditPwd.setVisibility(View.GONE);
            dialogPayViewTvNumberOfErrors.setVisibility(View.GONE);
            tv_tips.setVisibility(View.GONE);
            dialogPayViewTvTitle.setVisibility(View.GONE);
            dialogPayViewEditPwd.setVisibility(View.GONE);

            dialogPayViewTvErrors.setVisibility(View.VISIBLE);
            dialogPayViewTvErrors.setText("密码输入错误已达上限");
            dialogPayViewTvTimeOut.setVisibility(View.VISIBLE);
            dialogPayViewTvTimeOut.setText(getClickableSpan());
            dialogPayViewTvTimeOut.setMovementMethod(LinkMovementMethod.getInstance());
            dialogPayViewTvTimeOut.setHighlightColor(getResources().getColor(android.R.color.transparent));

        } else if (type == 4) {
            dialogPayViewTvNumberOfErrors.setVisibility(View.GONE);
            tv_tips.setVisibility(View.GONE);
            dialogPayViewTvTitle.setVisibility(View.GONE);
            dialogPayViewEditPwd.setVisibility(View.GONE);

            dialogPayViewTvErrors.setVisibility(View.VISIBLE);
            dialogPayViewTvErrors.setText("支付密码已冻结");
            dialogPayViewTvTimeOut.setVisibility(View.VISIBLE);
            dialogPayViewTvTimeOut.setText(getClickableSpan());
            dialogPayViewTvTimeOut.setMovementMethod(LinkMovementMethod.getInstance());
            dialogPayViewTvTimeOut.setHighlightColor(getResources().getColor(android.R.color.transparent));
        }
    }

    /**
     * 获取可点击的SpannableString
     *
     * @return
     */
    private SpannableString getClickableSpan() {
        SpannableString spannableString = new SpannableString("请您进入\"支付设置\"重新设置密码");
        spannableString.setSpan(new UnderlineSpan(), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                RouterHelper.toPaySet();
                dismiss();
            }
        }, 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color358CFF)), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

}
package com.yuanbaogo.mine.dib.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.finance.pay.weight.PayDialogFragment;
import com.yuanbaogo.libbase.baseutil.Md5Util;
import com.yuanbaogo.mine.dib.model.BindBankCardInfoBean;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.dib.contract.WithdrawContract;
import com.yuanbaogo.mine.dib.presenter.WithdrawPresenter;
import com.yuanbaogo.finance.pay.weight.PayDialogView;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 零钱提现页面
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 7:05 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.WITHDRAW_ACTIVITY)
public class WithdrawActivity extends MvpBaseActivityImpl<WithdrawContract.View, WithdrawPresenter>
        implements View.OnClickListener, WithdrawContract.View {

    HeadView mineAssetsDibWithdrawHeadView;

    EditText mineAssetsDibWithdrawEdit;

    ImageView mineAssetsDibWithdrawImgClear;

    TextView mineAssetsDibWithdrawTvWithdrawableAll;

    Button mineAssetsDibWithdrawButWithdraw;

    TextView mineAssetsDibWithdrawTvMethod;

    double dib;

    TextView mineAssetsDibWithdrawTvWithdrawable;

    /**
     * 正则  判断输入框内容
     */
    Pattern pattern = Pattern.compile("((^[1-9]\\d{0,7}[.]\\d{0,2})|(^[0][.]\\d{0,2})|(^[1-9]\\d{0,7}))?");

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_withdraw;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mineAssetsDibWithdrawHeadView = findViewById(R.id.mine_assets_dib_withdraw_head_view);
        mineAssetsDibWithdrawEdit = findViewById(R.id.mine_assets_dib_withdraw_edit);
        mineAssetsDibWithdrawImgClear = findViewById(R.id.mine_assets_dib_withdraw_img_clear);
        mineAssetsDibWithdrawTvWithdrawableAll = findViewById(R.id.mine_assets_dib_withdraw_tv_withdrawable_all);
        mineAssetsDibWithdrawButWithdraw = findViewById(R.id.mine_assets_dib_withdraw_but_withdraw);
        mineAssetsDibWithdrawTvMethod = findViewById(R.id.mine_assets_dib_withdraw_tv_method);
        mineAssetsDibWithdrawTvWithdrawable = findViewById(R.id.mine_assets_dib_withdraw_tv_withdrawable);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        dib = getIntent().getDoubleExtra("dib", 0);
        mineAssetsDibWithdrawTvWithdrawable.setText(dib+"");
        mineAssetsDibWithdrawEdit.addTextChangedListener(textWatcher);
        mineAssetsDibWithdrawImgClear.setOnClickListener(this);
        mineAssetsDibWithdrawTvWithdrawableAll.setOnClickListener(this);
        mineAssetsDibWithdrawButWithdraw.setOnClickListener(this);
    }

    PayDialogView payDialogView;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mine_assets_dib_withdraw_img_clear) {
            if (!TextUtils.isEmpty(mineAssetsDibWithdrawEdit.getText().toString())) {
                mineAssetsDibWithdrawEdit.setText("");
            }
        } else if (id == R.id.mine_assets_dib_withdraw_tv_withdrawable_all) {
            mineAssetsDibWithdrawEdit.setText(dib + "");
        } else if (id == R.id.mine_assets_dib_withdraw_but_withdraw) {
            if (!TextUtils.isEmpty(mineAssetsDibWithdrawEdit.getText().toString())) {
                if (Double.parseDouble(mineAssetsDibWithdrawEdit.getText().toString()) > Double.parseDouble(mineAssetsDibWithdrawTvWithdrawable.getText().toString())){
                    ToastView.showToast(WithdrawActivity.this, "可提现金额不足，请重新输入");
                }else {
                    mPresenter.getUserPassword();
                }
            }else {
                ToastView.showToast(WithdrawActivity.this, "请输入提现金额");
            }
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();

        mPresenter.getBindBankCardInfo();

        //动态设置hint字体大小
        SpannableString ss = new SpannableString("请输入金额，输入金额不得低于￥100");//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mineAssetsDibWithdrawEdit.setHint(new SpannedString(ss));

    }

    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setRlTopNavigaBarBg(R.color.colorFFFFFF)
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("提现")
                .setImgRight(false);
        mineAssetsDibWithdrawHeadView.setHead(headBean);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String price = mineAssetsDibWithdrawEdit.getText().toString();
            Matcher matcher = pattern.matcher(price);
            if (matcher.matches()) {
                changeLoginColor(price != null);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Matcher matcher = pattern.matcher(editable.toString());
            if (!matcher.matches()) {//根据正则判断
                editable.delete(editable.length() - 1, editable.length());
            } else {//第一位是0  第二位不是小数点  就不能输入
                if (editable.toString().length() > 1
                        && String.valueOf(editable.toString().charAt(0)).equals("0")
                        && !String.valueOf(editable.toString().charAt(1)).equals(".")) {
                    editable.delete(1, 2);
                }
            }
        }
    };

    /**
     * 改变登录按钮颜色
     *
     * @param isCan
     */
    public void changeLoginColor(boolean isCan) {
        if (isCan) {
            mineAssetsDibWithdrawButWithdraw.setBackground(getResources().getDrawable(R.drawable.selector_bg_but_login));
        } else {
            mineAssetsDibWithdrawButWithdraw.setBackground(getResources().getDrawable(R.drawable.shape_gradient_bg_but_alpha_50));
        }
    }

    /**
     * 提现金额
     */
    String price;

    /**
     * 提现银行卡信息
     */
    String bankName;

    @Override
    public void setWithdrawal() {
        RouterHelper.toWithdrawSuccess(price, bankName);
        finish();
    }

    /**
     * 设置提现银行卡信息
     *
     * @param bean
     */
    @Override
    public void setBindBankCardInfo(BindBankCardInfoBean bean) {
        //提现到微信有图片，银行没图片
        Drawable drawableLeft = getResources().getDrawable(R.mipmap.icon_assets_withdraw_method_wx);
        mineAssetsDibWithdrawTvMethod.setCompoundDrawablesWithIntrinsicBounds(null,
                null, null, null);
        bankName = bean.getBankName() + "(" + bean.getCardNo() + ")";
        mineAssetsDibWithdrawTvMethod.setText(bankName);
    }

    /**
     * 设置用户状态
     */
    @Override
    public void setCheckUserStatus(int code, String bean) {
        payDialogView = new PayDialogView();
        if (code == NetConfig.NETWORK_SUCCESSFUL && !Boolean.parseBoolean(bean)) {
            payDialogView.setType(0);
        } else {
            payDialogView.setType(4);
        }
        payDialogView.setOnRefreshData(new PayDialogView.OnRefreshData() {
            @Override
            public void onRefresh(int type, String pwd) {
                mPresenter.verifyUserPayPassword(pwd);
            }
        });
        payDialogView.show(getSupportFragmentManager(), "pay");
    }

    /**
     * 设置密码弹窗
     */
    @Override
    public void showNumberPasswordDialog() {
        String mTitle = getResources().getString(com.yuanbaogo.finance.R.string.password_not_set);
        String mSubtitle = getResources().getString(com.yuanbaogo.finance.R.string.password_set);
        String mLefttxt = "取消";
        String mRighttxt = "立即设置";
        PayDialogFragment mDialog = new PayDialogFragment(mTitle, mSubtitle, mLefttxt, mRighttxt);
        mDialog.show(getSupportFragmentManager(), "showNumberPassword");
        mDialog.setOnNextOpenPayPassword(new PayDialogFragment.OnNextOpenPayPassword() {
            @Override
            public void onNextOpen() {
                RouterHelper.toPaySet();
            }
        });
    }

    @Override
    public void setVerifyUserPayPassword(int code, String str) {
        if (code == NetConfig.NETWORK_SUCCESSFUL && Boolean.parseBoolean(str)) {
            price = mineAssetsDibWithdrawEdit.getText().toString().trim();
            //TODO HG 支付
            mPresenter.getWithdrawal(price, 0);
        }
        payDialogView.dismiss();
    }

    @Override
    public void setVerifyUserPayPasswordErr(String code, String msg) {
        if ("A0400".equals(code)) {
            if ("4".equals(msg)) {
                payDialogView.setType(2);
                payDialogView.setPwdNumber(Integer.parseInt(msg));
            } else {
                payDialogView.setType(1);
                payDialogView.setPwdNumber(Integer.parseInt(msg));
            }
        } else if ("A0500".equals(code)) {
            payDialogView.setType(3);
            payDialogView.initRefresh(3);
        }
    }

}
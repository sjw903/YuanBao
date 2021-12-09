package com.yuanbaogo.mine.setting.pay;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.finance.pay.weight.PayDialogFragment;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.view.SettingItem;

@Route(path = YBRouter.PAY_ACTIVITY)
public class PayActivity extends MvpBaseActivityImpl<PayContract.View, PayPresenter> implements PayContract.View, View.OnClickListener, PayDialogFragment.OnNextOpenPayPassword {

    private SettingItem mPaySiSetPassword;
    private SettingItem mPaySiResetPassword;
    private PayDialogFragment mDialog;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_pay;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mPaySiSetPassword = findViewById(R.id.pay_si_set_password);
        mPaySiResetPassword = findViewById(R.id.pay_si_reset_password);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mPaySiSetPassword.setOnClickListener(this);
        mPaySiResetPassword.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getUserPassword();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.pay_si_set_password) {
            // 设置支付密码
            showNumberPasswordDialog();
        } else if (id == R.id.pay_si_reset_password) {
            // 重置支付密码 产品又定义为重置密码也需要弹对话框
            showNumberPasswordDialog();
            // RouterHelper.toPaySet();
        }
    }

    private void showNumberPasswordDialog() {
        mDialog = new PayDialogFragment(getString(R.string.pay_dialog_title), getString(R.string.pay_dialog_content),
                getString(R.string.pay_dialog_not_open), getString(R.string.pay_dialog_keep_on));
        mDialog.show(getSupportFragmentManager(), "payPassword");
        mDialog.setOnNextOpenPayPassword(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void showAddPasswordItem(boolean show) {
        if (show) {
            mPaySiSetPassword.setVisibility(View.VISIBLE);
            mPaySiResetPassword.setVisibility(View.GONE);
        } else {
            mPaySiSetPassword.setVisibility(View.GONE);
            mPaySiResetPassword.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNextOpen() {
        RouterHelper.toPaySet();
    }

}
package com.yuanbaogo.mine.setting.account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.utils.fragment.ChangeParms;
import com.yuanbaogo.mine.setting.utils.ActivityManager;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.SettingItem;

@Route(path = YBRouter.ACCOUNT_ACTIVITY)
public class AccountActivity extends MvpBaseActivityImpl<AccountContract.View, AccountPresenter> implements AccountContract.View, View.OnClickListener {

    private SettingItem mAccountSiUpdatePhone;
    private TextView mAccountTvLogout;
    private TextView mAccountTvSignOut;
    private BottomSheetDialog mSheetDialog;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_account;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mAccountSiUpdatePhone = findViewById(R.id.account_si_update_phone);
        mAccountTvLogout = findViewById(R.id.account_tv_logout);
        mAccountTvSignOut = findViewById(R.id.account_tv_sign_out);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mAccountSiUpdatePhone.setOnClickListener(this);
        mAccountTvLogout.setOnClickListener(this);
        mAccountTvSignOut.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        // 刷新手机号，有可能已经修改了手机号
        mAccountSiUpdatePhone.setSubNameText(UserStore.getEncryptionPhone());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.account_si_update_phone) {
            // 修改手机号
            RouterHelper.toUpdatePhone();
        } else if (id == R.id.account_tv_logout) {
            // 注销账号
//            RouterHelper.toCloseAccount();
            RouterHelper.toCloseAccountFinal();
        } else if (id == R.id.account_tv_sign_out) {
            // 退出登录
            showSignOutDialog();
        } else if (id == R.id.sign_dialog_tv_sure) {
            // 执行退出登录
            mPresenter.signOut();
        } else if (id == R.id.sign_dialog_tv_cancel) {
            mSheetDialog.dismiss();
        }
    }

    private void showSignOutDialog() {
        mSheetDialog = new BottomSheetDialog(this);
        @SuppressLint("InflateParams") final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.mine_dialog_sign_out, null);
        mSheetDialog.setContentView(dialogView);
        TextView signDialogTvSure = dialogView.findViewById(R.id.sign_dialog_tv_sure);
        TextView signDialogTvCancel = dialogView.findViewById(R.id.sign_dialog_tv_cancel);
        signDialogTvSure.setOnClickListener(this);
        signDialogTvCancel.setOnClickListener(this);
        mSheetDialog.show();
    }


    @Override
    public void signSuccess() {
        UserStore.cleanUser();
        finish();
//        ActivityManager.getInstance().popActivity(ActivityManager.getInstance().currentActivity());
        ChangeParms.sChangeFragment.changge(0);
        RouterHelper.toMain();
    }

    @Override
    public void signFail(Throwable throwable) {
        ToastView.showToast(R.string.account_error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSheetDialog != null) {
            mSheetDialog.cancel();
        }
    }

}
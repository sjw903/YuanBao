package com.yuanbaogo.mine.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.mine.setting.utils.ActivityManager;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.utils.ChangeHostUrlActivity;
import com.yuanbaogo.zui.view.SettingItem;
import com.yuanbaogo.zui.view.TitleBar;

@Route(path = YBRouter.SETTING_ACTIVITY)
public class SettingActivity extends MvpBaseActivityImpl<NullImplContract.View, NullImplPresenter> implements View.OnClickListener {

    private TitleBar mSettingTitleBar;
    private SettingItem mSettingSiAccount;
    private SettingItem mSettingSiAddress;
    private SettingItem mSettingSiPay;
    private SettingItem mSettingSiBill;
    private SettingItem mSettingSiCommon;
    private SettingItem mSettingSiAbout;

    private int mClickTimes;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_setting;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mSettingTitleBar = findViewById(R.id.setting_title_bar);
        mSettingSiAccount = findViewById(R.id.setting_si_account);
        mSettingSiAddress = findViewById(R.id.setting_si_address);
        mSettingSiPay = findViewById(R.id.setting_si_pay);
        mSettingSiBill = findViewById(R.id.setting_si_bill);
        mSettingSiCommon = findViewById(R.id.setting_si_common);
        mSettingSiAbout = findViewById(R.id.setting_si_about);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mSettingTitleBar.setTitleOnClickListener(this);
        mSettingSiAccount.setOnClickListener(this);
        mSettingSiAddress.setOnClickListener(this);
        mSettingSiPay.setOnClickListener(this);
        mSettingSiBill.setOnClickListener(this);
        mSettingSiCommon.setOnClickListener(this);
        mSettingSiAbout.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.title_bar_tv_title) {
            toChangeHost();
        } else if (id == R.id.setting_si_account) {
            ActivityManager.getInstance().pushActivity(this);
            RouterHelper.toAccount();
        } else if (id == R.id.setting_si_address) {
            RouterHelper.toAddress();
        } else if (id == R.id.setting_si_pay) {
            RouterHelper.toPayManager();
        } else if (id == R.id.setting_si_bill) {
            RouterHelper.toBillManager();
        } else if (id == R.id.setting_si_common) {
            RouterHelper.toCommon();
        } else if (id == R.id.setting_si_about) {
            RouterHelper.toAbout();
        }
    }

    private void toChangeHost() {
        if (mClickTimes >= 11) {
            return;
        }
        mClickTimes++;
        if (mClickTimes == 10) {
            Intent intent = new Intent();
            intent.setClass(this, ChangeHostUrlActivity.class);
            startActivity(intent);
        }
    }

}
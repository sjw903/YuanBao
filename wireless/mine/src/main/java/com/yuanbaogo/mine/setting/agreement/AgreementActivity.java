package com.yuanbaogo.mine.setting.agreement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.NullImplContract;
import com.yuanbaogo.mine.setting.NullImplPresenter;

public class AgreementActivity extends MvpBaseActivityImpl<NullImplContract.View, NullImplPresenter> {

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_agreement;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {

    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AgreementActivity.class));
    }

}
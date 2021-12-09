package com.yuanbaogo.mine.setting.account.close;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.NullImplContract;
import com.yuanbaogo.mine.setting.NullImplPresenter;

@Route(path = YBRouter.CLOSE_ACCOUNT_ACTIVITY)
public class CloseAccountActivity extends MvpBaseActivityImpl<NullImplContract.View, NullImplPresenter> implements View.OnClickListener {

    private TextView mCloseTvNext;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_close_account;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mCloseTvNext = findViewById(R.id.close_tv_next);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mCloseTvNext.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.close_tv_next) {
            RouterHelper.toCloseAccountFinal();
            finish();
        }
    }

}
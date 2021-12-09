package com.yuanbaogo.mine.order.after;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.after.record.RefundRecordFragment;
import com.yuanbaogo.mine.setting.NullImplContract;
import com.yuanbaogo.mine.setting.NullImplPresenter;
import com.yuanbaogo.router.YBRouter;

@Route(path = YBRouter.AFTER_SALES_ACTIVITY)
public class AfterSalesActivity extends MvpBaseActivityImpl<NullImplContract.View, NullImplPresenter> {


    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_after_sales;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {

    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setFragments();
    }

    protected void setFragments() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.sales_frame_layout);
        if (fragment != null) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.sales_frame_layout, RefundRecordFragment.newInstance());
        ft.commit();
    }

}
package com.yuanbaogo.mine.setting.bill;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.NullImplContract;
import com.yuanbaogo.mine.setting.NullImplPresenter;
import com.yuanbaogo.zui.view.SettingItem;

@Route(path = YBRouter.BILL_ACTIVITY)
public class BillActivity extends MvpBaseActivityImpl<NullImplContract.View, NullImplPresenter> implements View.OnClickListener {

    private SettingItem mBillSiViewAll;
    private SettingItem mBillSiHead;
    private SettingItem mBillSiChange;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_bill;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mBillSiViewAll = findViewById(R.id.bill_si_view_all);
        mBillSiHead = findViewById(R.id.bill_si_head);
        mBillSiChange = findViewById(R.id.bill_si_change);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mBillSiViewAll.setOnClickListener(this);
        mBillSiHead.setOnClickListener(this);
        mBillSiChange.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bill_si_view_all) {
            // 查看全部发票
            RouterHelper.toALLBill();
        } else if (id == R.id.bill_si_head) {
            // 抬头管理
            RouterHelper.toBillHead();
        } else if (id == R.id.bill_si_change) {
            // 换开发票
            RouterHelper.toALLBill();
        }
    }
}
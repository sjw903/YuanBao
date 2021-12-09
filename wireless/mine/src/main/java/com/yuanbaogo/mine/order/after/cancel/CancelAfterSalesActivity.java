package com.yuanbaogo.mine.order.after.cancel;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.after.base.BaseAfterDetailsActivity;
import com.yuanbaogo.router.YBRouter;

@Route(path = YBRouter.CANCEL_AFTER_SALES_DETAIL_ACTIVITY)
public class CancelAfterSalesActivity extends BaseAfterDetailsActivity<CancelAfterSalesContract.View, CancelAfterSalesPresenter> implements View.OnClickListener {

    private ImageButton mAfterIbBack;
    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";
    @Autowired(name = YBRouter.OrderDetailsActivityParams.afterSalesId)
    String afterSalesId = "";

    TextView afterTvTipStatus;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_cancel_after_sales;
    }


    @Override
    protected String getOrderId() {
        return totalOrderId;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        super.bindViews(savedInstanceState);
        mAfterIbBack = findViewById(R.id.after_ib_back);
        afterTvTipStatus = findViewById(R.id.after_tv_tip_status);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mAfterIbBack.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if (data != null) {
            afterTvTipStatus.setText(data.getCloseReason());
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.after_ib_back) {
            finish();
        }
    }

}
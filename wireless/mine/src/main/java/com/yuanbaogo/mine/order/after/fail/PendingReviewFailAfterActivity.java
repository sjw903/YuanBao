package com.yuanbaogo.mine.order.after.fail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.after.base.BaseAfterDetailsActivity;
import com.yuanbaogo.mine.order.after.cancel.CancelAfterSalesContract;
import com.yuanbaogo.mine.order.after.cancel.CancelAfterSalesPresenter;
import com.yuanbaogo.router.YBRouter;

@Route(path = YBRouter.PENDING_REVIEW_FAIL_AFTER_ACTIVITY)
public class PendingReviewFailAfterActivity extends BaseAfterDetailsActivity<CancelAfterSalesContract.View, CancelAfterSalesPresenter> implements View.OnClickListener {

    private ImageButton mAfterIbBack;
    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";
    @Autowired(name = YBRouter.OrderDetailsActivityParams.afterSalesId)
    String afterSalesId = "";

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_pending_review_fail_after;
    }

    @Override
    protected String getOrderId() {
        return totalOrderId;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        super.bindViews(savedInstanceState);
        mAfterIbBack = findViewById(R.id.after_ib_back);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mAfterIbBack.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.after_ib_back) {
            finish();
        }
    }
}
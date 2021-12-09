package com.yuanbaogo.mine.order.finish.after;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.callcenter.CallCenter;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.adapter.AfterOrderGoodsAdapter;
import com.yuanbaogo.mine.order.finish.refund.SalesReturnRefundActivity;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.router.YBRouter;

@Route(path = YBRouter.AFTER_SALES_SERVICE_ACTIVITY)
public class AfterSalesServiceActivity extends MvpBaseActivityImpl<AfterSalesServiceContract.View, AfterSalesServicePresenter> implements
        AfterSalesServiceContract.View, View.OnClickListener {

    private RelativeLayout mAfterRlRefunds;
    private RelativeLayout mAfterRlRefundOnly;
    private LinearLayout mOrderLlService;
    private RecyclerView mOrderRvGoodsList;
    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";

    GoodsDetail goodsDetail;

    TextView afteTvHint;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_after_sales_service;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mOrderRvGoodsList = findViewById(R.id.order_rv_goods_list);
        mAfterRlRefunds = findViewById(R.id.after_rl_refunds);
        mAfterRlRefundOnly = findViewById(R.id.after_rl_refund_only);
        mOrderLlService = findViewById(R.id.order_ll_service);
        afteTvHint = findViewById(R.id.afte_tv_hint);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mAfterRlRefunds.setOnClickListener(this);
        mAfterRlRefundOnly.setOnClickListener(this);
        mOrderLlService.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mPresenter.getOrderDetail(totalOrderId);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.after_rl_refunds) {
            // 退货退款
            RouterHelper.toSalesReturnRefund(this, 400, totalOrderId, SalesReturnRefundActivity.SALES_RETURN_REFUND_TYPE);
        } else if (id == R.id.after_rl_refund_only) {
            // 只退款（不退货）
            RouterHelper.toSalesReturnRefund(this, 400, totalOrderId, SalesReturnRefundActivity.REFUND_TYPE);
        } else if (id == R.id.order_ll_service) {
            // 咨询客服
            CallCenter.toService(null,
                    totalOrderId,
                    "¥" + Long.valueOf(goodsDetail.getRealPayedPrice()) / 100,
                    goodsDetail.getGoodsVOList().get(0).getGoodsImageUrl(),
                    goodsDetail.getGoodsVOList().get(0).getGoodsTitle());
        }
    }

    @Override
    public void showOrderDetail(GoodsDetail goodsDetail) {
        this.goodsDetail = goodsDetail;
        AfterOrderGoodsAdapter adapter = new AfterOrderGoodsAdapter(this, goodsDetail.getGoodsVOList());
        mOrderRvGoodsList.setAdapter(adapter);
        if (goodsDetail.getOrderActivityType() == 1) {//普通订单
            afteTvHint.setText(getResources().getText(R.string.after_tip));
        } else if (goodsDetail.getOrderActivityType() == 3) {//抽奖订单
            afteTvHint.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 400 && resultCode == 401) {
            finish();
        }
    }
}
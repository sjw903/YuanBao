package com.yuanbaogo.mine.order.after.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.adapter.OrderGoodsListRecyclerAdapter;
import com.yuanbaogo.mine.order.after.model.AfterSalesDetailsModel;

public abstract class BaseAfterDetailsActivity<V extends BaseAfterSalesDetailContract.View, P extends BaseAfterSalesDetailPresenter>
        extends MvpBaseActivityImpl<V, P> implements BaseAfterSalesDetailContract.View {

    protected RecyclerView mOrderRvGoodsList;
    protected AfterSalesDetailsModel data;
    protected OrderGoodsListRecyclerAdapter mAdapter;
    private TextView mAfterTvReason;
    private TextView mAfterTvMoney;
    private TextView mAfterTvTime;
    private TextView mAfterTvCode;
    private RelativeLayout mAfterRlExpress;
    private TextView mAfterTvExpress;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getAfterSalesDetails(getOrderId());
    }

    /**
     * 从子类获取订单id
     *
     * @return 订单id
     */
    protected abstract String getOrderId();

    @CallSuper
    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mAfterTvReason = findViewById(R.id.after_tv_reason);
        mAfterTvMoney = findViewById(R.id.after_tv_money);
        mAfterTvTime = findViewById(R.id.after_tv_time);
        mAfterTvCode = findViewById(R.id.after_tv_code);
        mAfterRlExpress = findViewById(R.id.after_rl_express);
        mAfterTvExpress = findViewById(R.id.after_tv_express);
        mOrderRvGoodsList = findViewById(R.id.order_rv_goods_list);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showAfterSales(AfterSalesDetailsModel data) {
        if (data == null) return;
        this.data = data;
        if (data.getGoodsVoList() != null && data.getGoodsVoList().size() != 0) {
            mAdapter = new OrderGoodsListRecyclerAdapter(this, data.getGoodsVoList());
            mOrderRvGoodsList.setAdapter(mAdapter);
        }
        mAfterTvReason.setText(data.getReason());
        int money = data.getAfterSalePrice() / 100;
        mAfterTvMoney.setText("¥ " + money);
        mAfterTvTime.setText(DateUtils.getTime(data.getCreateTime()));
        mAfterTvCode.setText(data.getOrderId());
        if (TextUtils.isEmpty(data.getExpressCompany())) {
            mAfterRlExpress.setVisibility(View.GONE);
        } else {
            mAfterRlExpress.setVisibility(View.VISIBLE);
            mAfterTvExpress.setText(data.getExpressCompany());
        }
    }
}

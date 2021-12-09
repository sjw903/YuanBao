package com.yuanbaogo.mine.order.after.finish;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.after.base.BaseAfterDetailsActivity;
import com.yuanbaogo.mine.order.after.model.AfterSalesDetailsModel;
import com.yuanbaogo.mine.order.after.model.RefundStatusModel;
import com.yuanbaogo.mine.order.utils.Configuration;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.setp.refund.RefundAdapter;
import com.yuanbaogo.zui.setp.refund.RefundModel;
import com.yuanbaogo.zui.setp.refund.RefundStatus;

import java.util.ArrayList;
import java.util.List;

@Route(path = YBRouter.AFTER_SALES_FINISH_DETAIL_ACTIVITY)
public class AfterSalesFinishDetailActivity extends BaseAfterDetailsActivity<AfterSalesFinishDetailContract.View, AfterSalesFinishDetailPresenter>
        implements AfterSalesFinishDetailContract.View, View.OnClickListener {

    private static final String TAG = "AfterSalesFinishDetailActivity";
    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";
    @Autowired(name = YBRouter.OrderDetailsActivityParams.afterSalesId)
    String afterSalesId = "";
    private ImageButton mAfterFinishIbBack;
    private TextView mAfterFinishTvTitle;
    private TextView mAfterFinishTvMoney;
    private TextView mAfterFinishTvEvaluation;
    private RecyclerView mAfterFinishRvRefund;
    private TextView mAfterFinishTvTipStatus;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_after_sales_finish_detail;
    }

    @Override
    protected String getOrderId() {
        return totalOrderId;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        super.bindViews(savedInstanceState);
        mAfterFinishTvTitle = findViewById(R.id.after_finish_tv_title);
        mAfterFinishIbBack = findViewById(R.id.after_finish_ib_back);
        mAfterFinishTvMoney = findViewById(R.id.after_finish_tv_money);
        mAfterFinishTvEvaluation = findViewById(R.id.after_finish_tv_evaluation);
        mAfterFinishRvRefund = findViewById(R.id.after_finish_rv_refund);
        mAfterFinishTvTipStatus = findViewById(R.id.after_finish_tv_tip_status);
        mAfterFinishTvEvaluation.setVisibility(View.GONE);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mAfterFinishIbBack.setOnClickListener(this);
        mAfterFinishTvEvaluation.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
//        mPresenter.getRefundStatus(afterSalesId);
        initScheduleData();
    }

    RefundAdapter mAdapter;
    List<RefundModel> mRefundModel;

    private void initScheduleData() {
        mRefundModel = new ArrayList<>();
        mRefundModel.add(new RefundModel("提交申请", RefundStatus.FINISH));
        mRefundModel.add(new RefundModel("平台审核", RefundStatus.FINISH));
        mRefundModel.add(new RefundModel("平台收货", RefundStatus.FINISH));
        mRefundModel.add(new RefundModel("退款成功", RefundStatus.FINISH));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mAfterFinishRvRefund.setLayoutManager(layoutManager);
        mAdapter = new RefundAdapter(mRefundModel, LinearLayout.HORIZONTAL);
        mAfterFinishRvRefund.setAdapter(mAdapter);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.after_finish_ib_back) {
            finish();
        } else if (id == R.id.after_finish_tv_evaluation) {
            // 售后评价
            RouterHelper.toEvaluation(totalOrderId);
        }
    }

    @Override
    public void showRefundStatus(List<RefundStatusModel> refundDetailModel) {
        List<RefundModel> mRefundModel = new ArrayList<>();
        for (int i = 0; i < refundDetailModel.size(); i++) {
            RefundStatusModel refundStatusModel = refundDetailModel.get(i);
            mRefundModel.add(new RefundModel(refundStatusModel.getOperationName(), refundStatusModel.isIsExectued() ? RefundStatus.FINISH : RefundStatus.NO_FINISH));
        }
        RefundAdapter mRefundAdapter = new RefundAdapter(mRefundModel, LinearLayout.HORIZONTAL);
        mAfterFinishRvRefund.setAdapter(mRefundAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mAfterFinishRvRefund.setLayoutManager(layoutManager);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showAfterSales(AfterSalesDetailsModel afterSalesDetailsModel) {
        super.showAfterSales(afterSalesDetailsModel);
        Log.e(TAG, "showAfterSales: " + afterSalesDetailsModel.getCreateTime());
        int salesStatus = afterSalesDetailsModel.getSalesStatus();
        mAfterFinishTvMoney.setText("¥ " + data.getAfterSalePrice() / 100);
        if (salesStatus == Configuration.ORDER_CLOSE) {
            mAfterFinishTvTitle.setText(R.string.refund_title_close);
            mAfterFinishTvTitle.setCompoundDrawables(AppCompatResources.getDrawable(this, R.mipmap.icon_order_cancel),
                    null, null, null);
            mAfterFinishTvTipStatus.setText(R.string.refund_status_fail_tip);
        } else {
            mAfterFinishTvTitle.setText(R.string.refund_title_success);
            mAfterFinishTvTitle.setCompoundDrawables(AppCompatResources.getDrawable(this, R.mipmap.icon_order_finish),
                    null, null, null);
            mAfterFinishTvTipStatus.setText(R.string.refund_status_success_tip);
        }
    }

}
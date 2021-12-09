package com.yuanbaogo.mine.order.after.refund;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.after.model.RefundStatusModel;
import com.yuanbaogo.mine.order.after.pending.PendingReviewAfterContract;
import com.yuanbaogo.mine.order.after.pending.PendingReviewAfterPresenter;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.setp.refund.RefundAdapter;
import com.yuanbaogo.zui.setp.refund.RefundModel;
import com.yuanbaogo.zui.setp.refund.RefundStatus;

import java.util.ArrayList;
import java.util.List;

@Route(path = YBRouter.PENDING_REFUND_DETAIL_ACTIVITY)
public class PendingRefundDetailActivity extends MvpBaseActivityImpl<PendingReviewAfterContract.View, PendingReviewAfterPresenter>
        implements View.OnClickListener, PendingReviewAfterContract.View {

    private ImageButton mAfterIbBack;
    private RecyclerView mAfterRvRefund;
    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";
    @Autowired(name = YBRouter.OrderDetailsActivityParams.afterSalesId)
    String afterSalesId = "";

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_pending_refund_detail;
    }

    @Override
    public void onResume() {
        super.onResume();
//        mPresenter.getRefundStatus(afterSalesId);
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mAfterIbBack = findViewById(R.id.after_ib_back);
        mAfterRvRefund = findViewById(R.id.after_rv_refund);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mAfterIbBack.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initScheduleData();
    }

    RefundAdapter mAdapter;
    List<RefundModel> mRefundModel;

    private void initScheduleData() {
        mRefundModel = new ArrayList<>();
        mRefundModel.add(new RefundModel("提交申请", RefundStatus.FINISH));
        mRefundModel.add(new RefundModel("平台审核", RefundStatus.FINISH));
        mRefundModel.add(new RefundModel("平台收货", RefundStatus.FINISH));
        mRefundModel.add(new RefundModel("退款成功", RefundStatus.NO_FINISH));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mAfterRvRefund.setLayoutManager(layoutManager);
        mAdapter = new RefundAdapter(mRefundModel, LinearLayout.HORIZONTAL);
        mAfterRvRefund.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.after_ib_back) {
            finish();
        }
    }

    @Override
    public void showRefundStatus(List<RefundStatusModel> refundDetailModel) {
        List<RefundModel> mRefundModel = new ArrayList<>();
        for (int i = 0; i < refundDetailModel.size(); i++) {
            RefundStatusModel refundStatusModel = refundDetailModel.get(i);
            mRefundModel.add(new RefundModel(refundStatusModel.getOperationName(), refundStatusModel.isIsExectued() ? RefundStatus.FINISH : RefundStatus.NO_FINISH));
        }
        RefundAdapter mAdapter = new RefundAdapter(mRefundModel, LinearLayout.HORIZONTAL);
        mAfterRvRefund.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mAfterRvRefund.setLayoutManager(layoutManager);
    }

}
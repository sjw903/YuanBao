package com.yuanbaogo.mine.order.after.pending;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.finance.pay.weight.AfterSalesDialogFragment;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.after.model.RefundStatusModel;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.setp.refund.RefundAdapter;
import com.yuanbaogo.zui.setp.refund.RefundModel;
import com.yuanbaogo.zui.setp.refund.RefundStatus;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

@Route(path = YBRouter.PENDING_REVIEW_AFTER_ACTIVITY)
public class PendingReviewAfterActivity extends MvpBaseActivityImpl<PendingReviewAfterContract.View, PendingReviewAfterPresenter>
        implements View.OnClickListener, PendingReviewAfterContract.View, AfterSalesDialogFragment.OnCancelRefundListener {

    private ImageButton mAfterIbBack;
    private RecyclerView mAfterRvRefund;
    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";
    private TextView mAfterTvCancel;
    private AfterSalesDialogFragment mDialog;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_pending_review_after;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mAfterIbBack = findViewById(R.id.after_ib_back);
        mAfterRvRefund = findViewById(R.id.after_rv_refund);
        mAfterTvCancel = findViewById(R.id.after_tv_cancel);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mAfterIbBack.setOnClickListener(this);
        mAfterTvCancel.setOnClickListener(this);
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
        mRefundModel.add(new RefundModel("平台审核", RefundStatus.NO_FINISH));
        mRefundModel.add(new RefundModel("平台收货", RefundStatus.NO_FINISH));
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
        } else if (id == R.id.after_tv_cancel){
            // 撤销申请
            mDialog = new AfterSalesDialogFragment();
            mDialog.show(getSupportFragmentManager(), "AfterSalesProgressDetailActivity");
            mDialog.setOnCancelRefundListener(this);
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
    @Override
    public void onCancelRefund() {
        OrderNetworkUtils.cancelRefund(this, totalOrderId, successful -> {
            if (successful) finish();
            ToastView.showToast(successful ? R.string.cancel_refund_success : R.string.cancel_refund_fail_limit);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
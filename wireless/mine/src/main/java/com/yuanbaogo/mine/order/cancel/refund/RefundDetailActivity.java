package com.yuanbaogo.mine.order.cancel.refund;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.utils.Configuration;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.setp.refund.RefundProgressAdapter;
import com.yuanbaogo.zui.setp.refund.RefundProgressModel;
import com.yuanbaogo.zui.setp.refund.RefundStatus;

import java.util.ArrayList;
import java.util.List;

@Route(path = YBRouter.REFUND_DETAIL_ACTIVITY)
public class RefundDetailActivity extends MvpBaseActivityImpl<RefundDetailContract.View, RefundDetailPresenter> implements
        RefundDetailContract.View {

    private TextView mRefundTvCode;
    private TextView mRefundTvStatus;
    private TextView mRefundTvMoney;
    private TextView mRefundTvText;
    private RecyclerView mRefundRvProgress;

    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_refund_detail;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mRefundTvCode = findViewById(R.id.refund_tv_code);
        mRefundTvStatus = findViewById(R.id.refund_tv_status);
        mRefundTvMoney = findViewById(R.id.refund_tv_money);
        mRefundTvText = findViewById(R.id.refund_tv_text);
        mRefundRvProgress = findViewById(R.id.refund_rv_progress);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mPresenter.getAfterSalesDetails(totalOrderId);
    }

    @Override
    public void showAfterSales(RefundCancelModel afterSalesDetailsModel) {
        mRefundTvCode.setText(afterSalesDetailsModel.getOrderId());
        mRefundTvMoney.setText(getString(R.string.refund_monty, afterSalesDetailsModel.getAfterSalePrice() / 100));
        if (!TextUtils.isEmpty(afterSalesDetailsModel.getDes())) {
            mRefundTvText.setText(afterSalesDetailsModel.getDes());
        }
        List<RefundProgressModel> mLogisticsModels = new ArrayList<>();
        int salesStatus = afterSalesDetailsModel.getSalesStatus();
        RefundStatus refundProgress2Status = RefundStatus.NO_FINISH;
        if (salesStatus == Configuration.ORDER_PENDING_REVIEW) {
            mRefundTvStatus.setText(R.string.refund_status1);
            refundProgress2Status = RefundStatus.FINISH;
        } else if (salesStatus == Configuration.ORDER_RETURNING) {
            mRefundTvStatus.setText(R.string.refund_status2);
            refundProgress2Status = RefundStatus.FINISH;
        } else if (salesStatus == Configuration.ORDER_REFUNDING) {
            mRefundTvStatus.setText(R.string.refund_status3);
            refundProgress2Status = RefundStatus.FINISH;
        } else if (salesStatus == Configuration.ORDER_REFUND_SUCCESSFULLY) {
            mRefundTvStatus.setText(R.string.refund_status4);
            RefundProgressModel refundProgressModel3 = new RefundProgressModel("您的退款元宝已受理完成，到账周期可查看退款明细。",
                    DateUtils.getTime(afterSalesDetailsModel.getFinishTime()), RefundStatus.FINISH);
            mLogisticsModels.add(refundProgressModel3);
        } else if (salesStatus == Configuration.ORDER_FINISH) {
            mRefundTvStatus.setText(R.string.refund_status5);
            RefundProgressModel refundProgressModel3 = new RefundProgressModel("您的退款元宝已受理完成，到账周期可查看退款明细。",
                    DateUtils.getTime(afterSalesDetailsModel.getFinishTime()), RefundStatus.FINISH);
            mLogisticsModels.add(refundProgressModel3);
        } else if (salesStatus == Configuration.ORDER_CLOSE) {
            mRefundTvStatus.setText(R.string.refund_status6);
            RefundProgressModel refundProgressModel3 = new RefundProgressModel("订单已关闭。",
                    DateUtils.getTime(afterSalesDetailsModel.getFinishTime()), RefundStatus.FINISH);
            mLogisticsModels.add(refundProgressModel3);
        }
        RefundProgressModel refundProgressModel2 = new RefundProgressModel("系统尝试拦截中，请耐心等待。",
                DateUtils.getTime(afterSalesDetailsModel.getCreateTime()), refundProgress2Status);
        mLogisticsModels.add(refundProgressModel2);
        RefundProgressModel refundProgressModel1 = new RefundProgressModel("您的取消申请已提交",
                DateUtils.getTime(afterSalesDetailsModel.getCreateTime()), RefundStatus.NO_FINISH);
        mLogisticsModels.add(refundProgressModel1);
        RefundProgressAdapter mAdapter = new RefundProgressAdapter(mLogisticsModels);
        mRefundRvProgress.setAdapter(mAdapter);
    }
}
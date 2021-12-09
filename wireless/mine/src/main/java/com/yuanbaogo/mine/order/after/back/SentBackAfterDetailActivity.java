package com.yuanbaogo.mine.order.after.back;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.finance.pay.weight.AfterSalesDialogFragment;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.libbase.baseutil.GsonUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.after.logistics.SetLogisticsCodeActivity;
import com.yuanbaogo.mine.order.after.model.AfterSalesDetailsModel;
import com.yuanbaogo.mine.order.after.model.RefundStatusModel;
import com.yuanbaogo.mine.order.after.model.SendBackInfoBean;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.setp.refund.RefundAdapter;
import com.yuanbaogo.zui.setp.refund.RefundModel;
import com.yuanbaogo.zui.setp.refund.RefundStatus;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

@Route(path = YBRouter.SENT_BACK_AFTER_DETAIL_ACTIVITY)
public class SentBackAfterDetailActivity extends MvpBaseActivityImpl<SentBackAfterDetailContract.View, SentBackAfterDetailPresenter> implements
        SentBackAfterDetailContract.View, View.OnClickListener, AfterSalesDialogFragment.OnCancelRefundListener {

    private static final String TAG = "SentBackAfterDetailActivity";
    private ImageButton mAfterProgressIbBack;
    private TextView mAfterProgressTvName;
    private TextView mAfterProgressTvPhone;
    private TextView mAfterProgressTvAddress;
    private RelativeLayout mAfterProgressRlLogistics;
    private TextView mAfterProgressTvLogistics;
    private TextView mAfterProgressTvCode;
    private TextView mAfterProgressTvGetCode;
    private TextView mAfterProgressTvEvaluation;
    private AfterSalesDialogFragment mDialog;
    private TextView mAfterProgressTvTitle;
    private TextView mAfterProgressTvStatus;
    private TextView mAfterProgressTvStatusTip;
    private RecyclerView mAfterProgressRvRefund;
    private LinearLayout mAfterProgressLlStatus;

    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";

    @Autowired(name = YBRouter.OrderDetailsActivityParams.afterSalesId)
    String afterSalesId = "";

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_after_sales_progress_detail;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mAfterProgressIbBack = findViewById(R.id.after_progress_ib_back);
        mAfterProgressRvRefund = findViewById(R.id.after_progress_rv_refund);
        mAfterProgressTvName = findViewById(R.id.after_progress_tv_name);
        mAfterProgressTvPhone = findViewById(R.id.after_progress_tv_phone);
        mAfterProgressTvAddress = findViewById(R.id.after_progress_tv_address);
        mAfterProgressRlLogistics = findViewById(R.id.after_progress_rl_logistics);
        mAfterProgressTvLogistics = findViewById(R.id.after_progress_tv_logistics);
        mAfterProgressTvCode = findViewById(R.id.after_progress_tv_code);
        mAfterProgressTvGetCode = findViewById(R.id.after_progress_tv_get_code);
        mAfterProgressTvEvaluation = findViewById(R.id.after_progress_tv_evaluation);
        mAfterProgressTvTitle = findViewById(R.id.after_progress_tv_title);
        mAfterProgressTvStatus = findViewById(R.id.after_progress_tv_status);
        mAfterProgressTvStatusTip = findViewById(R.id.after_progress_tv_status_tip);
        mAfterProgressLlStatus = findViewById(R.id.after_progress_ll_status);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mAfterProgressRlLogistics.setOnClickListener(this);
        mAfterProgressTvEvaluation.setOnClickListener(this);
        mAfterProgressIbBack.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initScheduleData(1, null);
    }

    RefundAdapter mAdapter;
    List<RefundModel> mRefundModel;

    private void initScheduleData(int type, AfterSalesDetailsModel afterSalesDetailsModel) {
        if (type == 1) {
            mRefundModel = new ArrayList<>();
            mRefundModel.add(new RefundModel("提交申请", RefundStatus.NO_FINISH));
            mRefundModel.add(new RefundModel("平台审核", RefundStatus.NO_FINISH));
            mRefundModel.add(new RefundModel("平台收货", RefundStatus.NO_FINISH));
            mRefundModel.add(new RefundModel("退款成功", RefundStatus.NO_FINISH));
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            mAfterProgressRvRefund.setLayoutManager(layoutManager);
            mAdapter = new RefundAdapter(mRefundModel, LinearLayout.HORIZONTAL);
            mAfterProgressRvRefund.setAdapter(mAdapter);
        } else if (type == 2) {
            //当前售后流程状态,
            // 1-操作:提交审核,状态:待审核
            // 2-操作:审核通过,状态:退货中
            // 3-操作:已退货,状态:退款中
            // 4-操作:已退款,状态:退款成功
            // 5-操作:订单已完成
            // 6-操作:关闭
            // 7-驳回
            if (afterSalesDetailsModel.getSalesStatus() == 1) {
                mRefundModel.get(0).setRefundStatus(RefundStatus.FINISH);
            } else if (afterSalesDetailsModel.getSalesStatus() == 2) {
                //是否填写快递单号,用来判断待收货状态 true已填写 false未填写
                if (afterSalesDetailsModel.isExpressNumberFlag()) {
                    mRefundModel.get(0).setRefundStatus(RefundStatus.FINISH);
                    mRefundModel.get(1).setRefundStatus(RefundStatus.FINISH);
                    mRefundModel.get(2).setRefundStatus(RefundStatus.FINISH);
                } else {
                    mRefundModel.get(0).setRefundStatus(RefundStatus.FINISH);
                    mRefundModel.get(1).setRefundStatus(RefundStatus.FINISH);
                }
            } else if (afterSalesDetailsModel.getSalesStatus() == 3) {

            }

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getAfterSalesDetails(totalOrderId);
//        mPresenter.getRefundStatus(afterSalesId);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.after_progress_ib_back) {
            finish();
        } else if (id == R.id.after_progress_rl_logistics) {
            // 填写快递单号
            Intent intent = new Intent(this, SetLogisticsCodeActivity.class);
            intent.putExtra(SetLogisticsCodeActivity.ORDER_ID, totalOrderId);
            startActivityForResult(intent, 200);
        } else if (id == R.id.after_progress_tv_evaluation) {
            // 撤销申请
            mDialog = new AfterSalesDialogFragment();
            mDialog.show(getSupportFragmentManager(), "AfterSalesProgressDetailActivity");
            mDialog.setOnCancelRefundListener(this);
        }
    }

    @Override
    public void onCancelRefund() {
        OrderNetworkUtils.cancelRefund(this, totalOrderId, successful -> {
            if (successful) finish();
            ToastView.showToast(successful ? R.string.cancel_refund_success : R.string.cancel_refund_fail);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void showAfterSales(AfterSalesDetailsModel afterSalesDetailsModel) {
        Log.e(TAG, "showAfterSales: " + afterSalesDetailsModel);
        initScheduleData(2, afterSalesDetailsModel);
        if (!TextUtils.isEmpty(afterSalesDetailsModel.getSendBackInfo())) {
            SendBackInfoBean sendBackInfoBean = GsonUtil
                    .GsonToBean(afterSalesDetailsModel.getSendBackInfo().trim(), SendBackInfoBean.class);
            mAfterProgressTvName.setText(sendBackInfoBean.getName());
            mAfterProgressTvPhone.setText(sendBackInfoBean.getTelephone());
            mAfterProgressTvAddress.setText(sendBackInfoBean.getAddress());
        }
        if (!TextUtils.isEmpty(afterSalesDetailsModel.getExpressCompany()) &&
                !TextUtils.isEmpty(afterSalesDetailsModel.getExpressNumber())) {
            mAfterProgressTvGetCode.setVisibility(View.GONE);
//            mAfterProgressTvLogistics.setText(afterSalesDetailsModel.getExpressCompany());
            mAfterProgressTvCode.setText(afterSalesDetailsModel.getExpressNumber());
        } else {
            mAfterProgressTvGetCode.setText("填写单号");
        }
//        mAfterProgressTvTipTime.setText(DateUtils.remainTime(afterSalesDetailsModel.getCreateTime()));
        if (afterSalesDetailsModel.getSalesStatus() == 2) {
            mAfterProgressLlStatus.setVisibility(View.VISIBLE);
            mAfterProgressTvTitle.setText("待寄回");
            mAfterProgressTvStatus.setText(R.string.refund_progress_tip);
            mAfterProgressTvStatusTip.setText(R.string.refund_progress_sub_tip);
            mAfterProgressTvEvaluation.setVisibility(View.GONE);
        } else {
            mAfterProgressLlStatus.setVisibility(View.VISIBLE);
            mAfterProgressTvTitle.setText("待收货");
            mAfterProgressTvStatus.setText("您的商品已寄出，等待平台收货");
            mAfterProgressTvStatusTip.setText("商品已寄出，等待平台收货，平台收货后可进行退款");
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
        mAfterProgressRvRefund.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mAfterProgressRvRefund.setLayoutManager(layoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 201) {
            String logisticsId = data.getStringExtra("logisticsId");
            mAfterProgressTvCode.setText(logisticsId);
            mAfterProgressTvGetCode.setVisibility(View.GONE);
        }
    }

}
package com.yuanbaogo.mine.order.finish.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.mine.order.finish.adapter.FinishDetailLogisticsAdapter;
import com.yuanbaogo.mine.order.receipt.detail.model.NewLogisticsModel;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.base.BaseOrderDetailsActivity;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.model.OrderInfoAddressVO;

import java.util.List;

@Route(path = YBRouter.FINISH_DETAIL_ACTIVITY)
public class FinishDetailActivity extends BaseOrderDetailsActivity<FinishDetailContract.View, FinishDetailPresenter> implements
        FinishDetailContract.View, View.OnClickListener {

    private ImageButton mFinishIbBack;
    private TextView mFinishTvDate;
    private TextView mFinishTvName;
    private TextView mFinishTvPhone;
    private TextView mFinishTvAddress;
    private TextView mOrderItemTvBill;
    private TextView mOrderItemTvService;
    private TextView mOrderItemTvEvaluated;
    private TextView mOrderTvRefunding;
    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";

    TextView finishTvTip;

    RecyclerView finishDetailRecyclerLogistics;

    RelativeLayout finishRlInfo;
    private LinearLayout mOrderLlRefund;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_finish_detail;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        super.bindViews(savedInstanceState);
        mFinishIbBack = findViewById(R.id.finish_ib_back);
        mFinishTvDate = findViewById(R.id.finish_tv_date);
        mFinishTvName = findViewById(R.id.finish_tv_name);
        mFinishTvPhone = findViewById(R.id.finish_tv_phone);
        mFinishTvAddress = findViewById(R.id.finish_tv_address);
        mOrderItemTvBill = findViewById(R.id.order_item_tv_bill);
        mOrderItemTvService = findViewById(R.id.order_item_tv_service);
        mOrderItemTvEvaluated = findViewById(R.id.order_item_tv_evaluated);
        finishTvTip = findViewById(R.id.finish_tv_tip);
        finishDetailRecyclerLogistics = findViewById(R.id.finish_detail_recycler_logistics);
        finishRlInfo = findViewById(R.id.finish_rl_info);
        mOrderLlRefund = findViewById(R.id.order_ll_refund);
        mOrderTvRefunding = findViewById(R.id.order_tv_refunding);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mFinishIbBack.setOnClickListener(this);
        mOrderItemTvBill.setOnClickListener(this);
        mOrderItemTvService.setOnClickListener(this);
        mOrderItemTvEvaluated.setOnClickListener(this);
        finishRlInfo.setOnClickListener(this);
        mOrderTvRefunding.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getOrderDetail(totalOrderId);
        mPresenter.getOrderLogisticsDetails(totalOrderId);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.finish_ib_back) {
            finish();
        } else if (id == R.id.order_item_tv_bill) {
            // 电子发票
            RouterHelper.toALLBill();
        } else if (id == R.id.order_item_tv_service) {
            // 售后服务
            RouterHelper.toAfterSalesService(totalOrderId);
        } else if (id == R.id.order_item_tv_evaluated) {
            // 待评价
            RouterHelper.toEvaluation(totalOrderId);
        } else if (id == R.id.finish_rl_info) {
            finishDetailRecyclerLogistics.setVisibility(View.VISIBLE);
        } else if (id == R.id.order_tv_refunding) {
            // 黄烁要求修改 ，跳转到售后列表
            RouterHelper.toAfterSales();
        }
    }

    @Override
    public void showOrderDetail(GoodsDetail goodsDetail) {
        getData(goodsDetail);
    }

    /**
     * 物流信息model
     */
    List<NewLogisticsModel> newLogisticsModels;

    @Override
    public void getOrderLogisticsDetails(List<NewLogisticsModel> bean) {
        newLogisticsModels = bean;
        if (newLogisticsModels != null && newLogisticsModels.size() != 0) {
            finishTvTip.setText(newLogisticsModels.get(0).getDes());
            mFinishTvDate.setText(DateUtils.getTime(newLogisticsModels.get(0).getCreateTime()));
            initRecyclerLogistics(newLogisticsModels);
        }
    }

    private void initRecyclerLogistics(List<NewLogisticsModel> newLogisticsModels) {
        newLogisticsModels.remove(0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        finishDetailRecyclerLogistics.setLayoutManager(linearLayoutManager);
        FinishDetailLogisticsAdapter finishDetailLogisticsAdapter = new FinishDetailLogisticsAdapter(this,
                newLogisticsModels, R.layout.item_finish_detail_logistics);
        finishDetailRecyclerLogistics.setAdapter(finishDetailLogisticsAdapter);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void getData(GoodsDetail data) {
        super.getData(data);
        if (data == null) return;
//        mFinishTvDate.setText(DateUtils.getTime(data.getCreatedTime()));
        OrderInfoAddressVO orderInfoAddressVO = data.getOrderInfoAddressVO();
        mFinishTvName.setText(orderInfoAddressVO.getName());
        mFinishTvPhone.setText(orderInfoAddressVO.getMobile());
        mFinishTvAddress.setText(orderInfoAddressVO.getProvince() +
                orderInfoAddressVO.getCity() +
                orderInfoAddressVO.getCountry() +
                orderInfoAddressVO.getDetail());
        if (data.getAfterSailedStatus() > 0) {
            mOrderItemTvService.setVisibility(View.GONE);
            mOrderItemTvEvaluated.setVisibility(View.GONE);
            mOrderLlRefund.setVisibility(View.VISIBLE);
        } else {
            mOrderItemTvService.setVisibility(View.VISIBLE);
            mOrderItemTvEvaluated.setVisibility(View.VISIBLE);
            mOrderLlRefund.setVisibility(View.GONE);
        }
        if (data.isCommented()) {
            mOrderItemTvEvaluated.setVisibility(View.GONE);
            mOrderItemTvEvaluated.setText(R.string.order_btn_evaluated);
        } else {
            mOrderItemTvEvaluated.setVisibility(View.VISIBLE);
        }
    }

}
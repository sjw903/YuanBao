package com.yuanbaogo.mine.order.receipt.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.base.BaseOrderDetailsActivity;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.model.OrderInfoAddressVO;
import com.yuanbaogo.mine.order.receipt.detail.model.NewLogisticsModel;
import com.yuanbaogo.mine.order.receipt.detail.utils.LogisticsState;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.setp.logistics.LogisticsAdapter;
import com.yuanbaogo.zui.setp.logistics.LogisticsModel;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

import static com.yuanbaogo.mine.order.utils.Configuration.ORDER_RECEIPT_TYPE;

@Route(path = YBRouter.RECEIPT_DETAIL_ACTIVITY)
public class ReceiptDetailActivity extends BaseOrderDetailsActivity<ReceiptDetailContract.View, ReceiptDetailPresenter> implements
        ReceiptDetailContract.View, View.OnClickListener {

    private TextView mOrderItemTvArea;
    private TextView mOrderItemTvDetail;
    private TextView mOrderItemTvName;
    private TextView mOrderItemTvPhone;
    private TextView mReceiptTvRefund;
    private TextView mReceiptTvConfirm;
    private LinearLayout mReceiptLlSmall;
    private ImageView mReceiptIvStatus;
    private TextView mReceiptTvStatus;
    private TextView mReceiptTvTime;
    private TextView mReceiptTvMsg;
    private TextView mReceiptTvView;
    private RecyclerView mReceiptRvBig;
    private LinearLayout mReceiptLlRefund;

    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";
    private LinearLayout mReceiptLlRaffle;
    private TextView mReceiptTvRaffleTime;

    public static ReceiptDetailActivity receiptDetailActivity;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_receipt_detail;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        super.bindViews(savedInstanceState);
        receiptDetailActivity = this;
        mReceiptLlRaffle = findViewById(R.id.receipt_ll_raffle);
        mReceiptTvRaffleTime = findViewById(R.id.receipt_tv_raffle_time);
        mReceiptLlSmall = findViewById(R.id.receipt_ll_small);
        mReceiptIvStatus = findViewById(R.id.receipt_iv_status);
        mReceiptTvStatus = findViewById(R.id.receipt_tv_status);
        mReceiptTvTime = findViewById(R.id.receipt_tv_time);
        mReceiptTvMsg = findViewById(R.id.receipt_tv_msg);
        mReceiptTvView = findViewById(R.id.receipt_tv_view);
        mReceiptRvBig = findViewById(R.id.receipt_rv_big);
        mOrderItemTvArea = findViewById(R.id.order_item_tv_area);
        mOrderItemTvDetail = findViewById(R.id.order_item_tv_detail);
        mOrderItemTvName = findViewById(R.id.order_item_tv_name);
        mOrderItemTvPhone = findViewById(R.id.order_item_tv_phone);
        mReceiptTvRefund = findViewById(R.id.receipt_tv_refund);
        mReceiptTvConfirm = findViewById(R.id.receipt_tv_confirm);
        mReceiptLlRefund = findViewById(R.id.receipt_ll_refund);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mReceiptTvRefund.setOnClickListener(this);
        mReceiptTvConfirm.setOnClickListener(this);
        mReceiptTvView.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getData(totalOrderId);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void getData(GoodsDetail data) {
        super.getData(data);
        if (data == null) return;
        OrderInfoAddressVO orderInfoAddressVO = data.getOrderInfoAddressVO();
        mOrderItemTvArea.setText(orderInfoAddressVO.getProvince() + orderInfoAddressVO.getCity() + orderInfoAddressVO.getCountry());
        mOrderItemTvDetail.setText(orderInfoAddressVO.getDetail());
        mOrderItemTvName.setText(orderInfoAddressVO.getName());
        mOrderItemTvPhone.setText(orderInfoAddressVO.getMobile());
        if (data.getOrderActivityType() != 1 && data.getLuckStatus() == 1) {
            mReceiptLlRaffle.setVisibility(View.VISIBLE);
            mReceiptTvRaffleTime.setText(DateUtils.getTime(data.getLotteryTime()));
        } else {
            mReceiptLlRaffle.setVisibility(View.GONE);
        }
//        if (data.getOrderStatus() == ORDER_RECEIPT_TYPE) {//TODO HG 待发货  只显示申请退款按钮
//            mReceiptTvConfirm.setVisibility(View.GONE);
//            mReceiptTvRefund.setVisibility(View.VISIBLE);
//        } else if (data.getOrderStatus() == ORDER_RECEIPT_TYPE_STOCK) {//TODO HG 待收货  只显示确认收货按钮
//            mReceiptTvConfirm.setVisibility(View.VISIBLE);
//            mReceiptTvRefund.setVisibility(View.GONE);
//        }
        // 有售后状态时无操作
        if (data.getLuckStatus()!=null){
            if (data.getAfterSailedStatus() > 0
                    && data.getAfterSailedStatus() != 6
                    && data.getAfterSailedStatus() != 7
                    || data.getLuckStatus() == 2 || data.getLuckStatus() == 3) {
//            mReceiptLlRefund.setVisibility(View.GONE);
                mReceiptLlRaffle.setVisibility(View.GONE);
            } else {
//            mReceiptLlRefund.setVisibility(View.VISIBLE);
            }
        }


        if (data.getOrderActivityType() == 1) {//普通订单
            if (data.getAfterSailedStatus() != 1) {// 无售后
                if (data.getOrderStatus() == 2) {//待收货(正在出库)
                    mReceiptTvRefund.setVisibility(View.VISIBLE);
                    mReceiptTvConfirm.setVisibility(View.GONE);
                } else {//待收货(已出库)
                    mReceiptTvRefund.setVisibility(View.GONE);
                    mReceiptTvConfirm.setVisibility(View.VISIBLE);
                }
            } else {//有售后
                mReceiptTvConfirm.setVisibility(View.GONE);
                mReceiptTvRefund.setVisibility(View.GONE);
            }
        } else if (data.getOrderActivityType() == 3) {//幸运拼团
            if (data.getAfterSailedStatus() != 1) {// 无售后
                if (data.getLuckStatus() == 1) {//待抽奖
                    mReceiptTvRefund.setVisibility(View.VISIBLE);
                    mReceiptTvConfirm.setVisibility(View.GONE);
                } else if (data.getLuckStatus() == 2) {//已经中奖
                    if (data.getOrderStatus() == 2) {//待收货(正在出库)
                        mReceiptTvRefund.setVisibility(View.GONE);
                        mReceiptTvConfirm.setVisibility(View.GONE);
                    } else if (data.getOrderStatus() == 3) {//待收货(已出库)
                        mReceiptTvRefund.setVisibility(View.GONE);
                        mReceiptTvConfirm.setVisibility(View.VISIBLE);
                    }
                } else {//没有中奖
                    mReceiptTvRefund.setVisibility(View.GONE);
                    mReceiptTvConfirm.setVisibility(View.GONE);
                }
            } else {//有售后
                mReceiptTvRefund.setVisibility(View.GONE);
                mReceiptTvConfirm.setVisibility(View.GONE);
            }
        }

    }

    private void initLogisticsStatus() {
        long time = System.currentTimeMillis();
        if (data.getOrderActivityType() != 1) {
            if (data.getLuckStatus() == 1) {
                mReceiptLlSmall.setVisibility(View.GONE);
                return;
            } else if (data.getLuckStatus() == 2) {
                mReceiptLlSmall.setVisibility(View.VISIBLE);
                mReceiptIvStatus.setImageResource(R.mipmap.icon_order_outbound);
                mReceiptTvStatus.setText("已中奖，正在出库");
                mReceiptTvTime.setText(DateUtils.getTime(time));
                mReceiptTvMsg.setText("您的订单正在打包出库");
                mReceiptTvView.setVisibility(View.GONE);
                return;
            } else {
                mReceiptLlSmall.setVisibility(View.VISIBLE);
                mReceiptIvStatus.setImageResource(R.mipmap.icon_order_outbound);
                mReceiptTvStatus.setText("抽奖结束，退款中");
                mReceiptTvTime.setText(DateUtils.getTime(time));
                mReceiptTvMsg.setText("您的订单正在准备退款");
                mReceiptTvView.setVisibility(View.GONE);
                return;
            }
        }
        if (data.getAfterSailedStatus() > 0 && data.getAfterSailedStatus() != 6 && data.getAfterSailedStatus() != 7) {
            mReceiptLlSmall.setVisibility(View.VISIBLE);
            mReceiptIvStatus.setImageResource(R.mipmap.icon_order_outbound);
            mReceiptTvStatus.setText("退款中");
            mReceiptTvTime.setText(DateUtils.getTime(time));
            mReceiptTvMsg.setText("您的订单正在准备退款");
            mReceiptTvView.setVisibility(View.GONE);
            return;
        }
        if (data.getOrderStatus() == ORDER_RECEIPT_TYPE) {
            mReceiptLlSmall.setVisibility(View.VISIBLE);
            mReceiptIvStatus.setImageResource(R.mipmap.icon_order_outbound);
            mReceiptTvStatus.setText("正在出库");
            mReceiptTvTime.setText(DateUtils.getTime(time));
            mReceiptTvMsg.setText("您的订单正在打包出库");
            mReceiptTvView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.receipt_tv_view) {
            // 查看详细物流状态
            mPresenter.getOrderLogisticsDetails(totalOrderId);
        } else if (id == R.id.receipt_tv_refund) {
            // 申请退款
            RouterHelper.toApplyRefund(totalOrderId, 2);
        } else if (id == R.id.receipt_tv_confirm) {
            OrderNetworkUtils.confirmGoods(this, totalOrderId, successful -> {
                ToastView.showToast(successful ? R.string.confirm_success : R.string.confirm_fail);
                RouterHelper.toFinishDetail(totalOrderId);
                finish();
            });
        }
    }

    @Override
    public void showOrderDetail(GoodsDetail goodsDetail) {
        getData(goodsDetail);
    }

    @Override
    public void showOrderLogistics(NewLogisticsModel logistics) {
        if (logistics == null || logistics.getId() == null || logistics.getOrderId() == null) {
            mReceiptLlSmall.setVisibility(View.GONE);
            initLogisticsStatus();
        } else {
            mReceiptTvView.setVisibility(View.VISIBLE);
            mReceiptLlSmall.setVisibility(View.VISIBLE);
            Pair<String, Integer> logisticsState = LogisticsState.getLogisticsState(logistics.getType());
            mReceiptIvStatus.setImageResource(logisticsState.second);
            mReceiptTvStatus.setText(logisticsState.first);
            long time;
//            if (logistics.getUpdateTime() <= 0) {
//                time = System.currentTimeMillis();
//            } else {
//                time = data.getCreatedTime();
//            }
            time = data.getCreatedTime();
            mReceiptTvTime.setText(DateUtils.getTime(time));
            mReceiptTvMsg.setText(logistics.getDes());
        }
    }

    @Override
    public void getOrderLogisticsDetails(List<NewLogisticsModel> totalOrderId) {
        mReceiptLlSmall.setVisibility(View.GONE);
        mReceiptRvBig.setVisibility(View.VISIBLE);
        List<LogisticsModel> mLogisticsModels = new ArrayList<>();
        for (NewLogisticsModel newLogisticsModel : totalOrderId) {
            Pair<String, Integer> logisticsState = LogisticsState.getLogisticsState(newLogisticsModel.getType());
            LogisticsModel logisticsModel = new LogisticsModel(logisticsState.first,
                    DateUtils.getTime(newLogisticsModel.getCreateTime()), newLogisticsModel.getDes(), LogisticsState.getLogisticsListState(newLogisticsModel.getType()));
            mLogisticsModels.add(logisticsModel);
        }
        LogisticsAdapter mAdapter = new LogisticsAdapter(mLogisticsModels);
        mReceiptRvBig.setAdapter(mAdapter);
    }

}
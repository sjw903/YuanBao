package com.yuanbaogo.mine.order.cancel.detail;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.callcenter.CallCenter;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.base.BaseOrderDetailsActivity;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.model.OrderInfoAddressVO;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.toast.ToastView;

@Route(path = YBRouter.CANCEL_DETAIL_ACTIVITY)
public class CancelDetailActivity extends BaseOrderDetailsActivity<CancelDetailContract.View, CancelDetailPresenter> implements
        CancelDetailContract.View, View.OnClickListener {

    private RelativeLayout mCancelRlInfo;
    private ImageButton mCancelIbBack;
    private TextView mCancelTvDate;
    private TextView mCancelTvName;
    private TextView mCancelTvPhone;
    private TextView mCancelTvAddress;
    private LinearLayout mOrderLlService;
    private TextView mCancelTvBtnDelete;
    private ImageView mCancelIvRight;
    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";

    private AlertDialog mDialog;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_cancel_detail;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        super.bindViews(savedInstanceState);
        mCancelRlInfo = findViewById(R.id.cancel_rl_info);
        mCancelIbBack = findViewById(R.id.cancel_ib_back);
        mCancelTvDate = findViewById(R.id.cancel_tv_date);
        mCancelTvName = findViewById(R.id.cancel_tv_name);
        mCancelTvPhone = findViewById(R.id.cancel_tv_phone);
        mCancelTvAddress = findViewById(R.id.cancel_tv_address);
        mOrderLlService = findViewById(R.id.order_ll_service);
        mCancelTvBtnDelete = findViewById(R.id.cancel_tv_btn_delete);
        mCancelIvRight = findViewById(R.id.cancel_iv_right);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mCancelIbBack.setOnClickListener(this);
        mCancelRlInfo.setOnClickListener(this);
        mOrderLlService.setOnClickListener(this);
        mCancelTvBtnDelete.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mPresenter.getOrderDetail(totalOrderId);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void getData(GoodsDetail data) {
        super.getData(data);
        if (data == null) return;
        mCancelTvDate.setText(DateUtils.getTime(data.getCreatedTime()));
        OrderInfoAddressVO orderInfoAddressVO = data.getOrderInfoAddressVO();
        mCancelTvName.setText(orderInfoAddressVO.getName());
        mCancelTvPhone.setText(orderInfoAddressVO.getMobile());
        mCancelTvAddress.setText(orderInfoAddressVO.getProvince() +
                orderInfoAddressVO.getCity() +
                orderInfoAddressVO.getCountry() +
                orderInfoAddressVO.getDetail());
        // 这里以支付时间来判断用户是否支付
        //如果是幸运拼团 就不能显示加入购物车  如果普通订单 未支付已取消订单  需要显示加入购物车按钮
        if (data.getOrderActivityType() == 2 || data.getOrderActivityType() == 3) {
            mAdapter.setShowAddCar(false);
        } else {
            mAdapter.setShowAddCar(!data.isPayed());
        }
        if (data.getAfterSailedStatus() != 0) {
            mCancelIvRight.setVisibility(View.VISIBLE);
        } else {
            mCancelIvRight.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cancel_ib_back) {
            finish();
        } else if (id == R.id.cancel_rl_info) {
            if (data.getAfterSailedStatus() != 0) {
                // 待付款已取消的查进度页面，是不调售后接口的。待付款已取消没走售后
                // 判断已经付款的才进入退款售后页面
                RouterHelper.toRefundDetail(totalOrderId);
            }
        } else if (id == R.id.order_ll_service) {
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            // 咨询客服
            CallCenter.toService(null,
                    totalOrderId,
                    "¥" + Long.valueOf(data.getRealPayedPrice()) / 100,
                    data.getGoodsVOList().get(0).getGoodsImageUrl(),
                    data.getGoodsVOList().get(0).getGoodsTitle());
        } else if (id == R.id.cancel_tv_btn_delete) {
            showDeleteOrderDialog();
        } else if (id == R.id.delete_cancel) {
            mDialog.dismiss();
        } else if (id == R.id.delete_sure) {
            mDialog.dismiss();
            OrderNetworkUtils.deleteOrder(this, totalOrderId, successful -> {
                if (successful) {
                    ToastView.showToast("删除订单成功");
                    finish();
                } else {
                    ToastView.showToast("删除订单失败");
                }
            });
        }
    }

    private void showDeleteOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        mDialog = builder.create();
        View view = LayoutInflater.from(this).inflate(R.layout.mine_layout_delete_order, null);
        TextView deleteCancel = view.findViewById(R.id.delete_cancel);
        TextView deleteSure = view.findViewById(R.id.delete_sure);
        deleteCancel.setOnClickListener(this);
        deleteSure.setOnClickListener(this);
        mDialog.setView(view);
        mDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDialog != null) {
            mDialog.cancel();
        }
    }

    @Override
    public void showOrderDetail(GoodsDetail goodsDetail) {
        getData(goodsDetail);
    }
}
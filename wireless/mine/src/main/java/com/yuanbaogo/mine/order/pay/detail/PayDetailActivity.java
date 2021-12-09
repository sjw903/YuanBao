package com.yuanbaogo.mine.order.pay.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.finance.pay.presenter.PayCenter;
import com.yuanbaogo.commonlib.callcenter.CallCenter;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.AddressOrderBean;
import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.OrderActivity;
import com.yuanbaogo.mine.order.base.BaseOrderDetailsActivity;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.model.OrderInfoAddressVO;
import com.yuanbaogo.mine.order.pay.OrderCancelBottomDialog;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.dialog.SureDialogFragment;
import com.yuanbaogo.zui.toast.ToastView;

@Route(path = YBRouter.PAY_DETAIL_ACTIVITY)
public class PayDetailActivity extends BaseOrderDetailsActivity<PayDetailContract.View, PayDetailPresenter> implements
        PayDetailContract.View, View.OnClickListener, OrderCancelBottomDialog.OnNextRefund, SureDialogFragment.OnSureListener {

    private static final String TAG = "PayDetailActivity";
    public static final int REQUEST_CODE = 200;
    private TextView mWaitPayTvMoney;
    private TextView mWaitPayTvHour;
    private TextView mWaitPayTvMinute;
    private TextView mWaitPayTvPay;
    private RelativeLayout mOrderItemRlAddress;
    private TextView mOrderItemTvArea;
    private TextView mOrderItemTvDetail;
    private TextView mOrderItemTvName;
    private TextView mOrderItemTvPhone;
    private LinearLayout mOrderLlService;
    private TextView mWaitPayTvCancel;
    private ImageView mOrderItemIvUpdate;
    private TextView mWaitPayTvToPay;
    private GoodsDetail data;
    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";
    @Autowired(name = YBRouter.OrderDetailsActivityParams.updateAddressFlag)
    boolean updateAddressFlag = false;


    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_pay_detail;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (updateAddressFlag) {
            updateAddressFlag = false;
            RouterHelper.toAddressRequest(this, REQUEST_CODE, null);
        }
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        super.bindViews(savedInstanceState);
        mWaitPayTvMoney = findViewById(R.id.wait_pay_tv_money);
        mWaitPayTvHour = findViewById(R.id.wait_pay_tv_hour);
        mWaitPayTvMinute = findViewById(R.id.wait_pay_tv_minute);
        mWaitPayTvPay = findViewById(R.id.wait_pay_tv_pay);
        mOrderItemRlAddress = findViewById(R.id.order_item_rl_address);
        mOrderItemTvArea = findViewById(R.id.order_item_tv_area);
        mOrderItemTvDetail = findViewById(R.id.order_item_tv_detail);
        mOrderItemTvName = findViewById(R.id.order_item_tv_name);
        mOrderItemTvPhone = findViewById(R.id.order_item_tv_phone);
        mOrderLlService = findViewById(R.id.order_ll_service);
        mWaitPayTvCancel = findViewById(R.id.wait_pay_tv_cancel);
        mWaitPayTvToPay = findViewById(R.id.wait_pay_tv_to_pay);
        mOrderItemIvUpdate = findViewById(R.id.order_item_iv_update);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mOrderItemRlAddress.setOnClickListener(this);
        mWaitPayTvCancel.setOnClickListener(this);
        mWaitPayTvPay.setOnClickListener(this);
        mWaitPayTvToPay.setOnClickListener(this);
        mOrderLlService.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mOrderItemIvUpdate.setVisibility(View.VISIBLE);
        mPresenter.getOrderDetail(totalOrderId);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void getData(GoodsDetail data) {
        super.getData(data);
        this.data = data;
        if (data == null) return;
        long createdTime = data.getCreatedTime();
        long day = 1000 * 60 * 60 * 24;
        long currentTime = System.currentTimeMillis();
        long remain = day + createdTime - currentTime;
        String hour = DateUtils.nowCurrentPoint(remain, true);
        String minute = DateUtils.nowCurrentPoint(remain, false);
        mWaitPayTvHour.setText(hour);
        mWaitPayTvMinute.setText(minute);
        mWaitPayTvMoney.setText(getString(R.string.wait_pay_money, Double.parseDouble(data.getRealPayedPrice()) / 100));
        OrderInfoAddressVO orderInfoAddressVO = data.getOrderInfoAddressVO();
        mOrderItemTvArea.setText(orderInfoAddressVO.getProvince() + orderInfoAddressVO.getCity() + orderInfoAddressVO.getCountry());
        mOrderItemTvDetail.setText(orderInfoAddressVO.getDetail());
        mOrderItemTvName.setText(orderInfoAddressVO.getName());
        mOrderItemTvPhone.setText(orderInfoAddressVO.getMobile());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.wait_pay_tv_cancel) {
            OrderCancelBottomDialog dialog = new OrderCancelBottomDialog(this);
            dialog.setOnNextRefundListener(this);
            dialog.show();
        } else if (id == R.id.wait_pay_tv_pay || id == R.id.wait_pay_tv_to_pay) {
            toPay();
        } else if (id == R.id.order_item_rl_address) {
            RouterHelper.toAddressRequest(this, REQUEST_CODE, null);
        } else if (id == R.id.order_ll_service) {
            // 咨询客服
            CallCenter.toService(null,
                    totalOrderId,
                    "¥" + Long.valueOf(data.getRealPayedPrice()) / 100,
                    data.getGoodsVOList().get(0).getGoodsImageUrl(),
                    data.getGoodsVOList().get(0).getGoodsTitle());
        }
    }

    private void toPay() {
        if (data.getOrderActivityType() == 3) {
            mPresenter.getLuckDrawEnter(data.getTotalOrderId());
        } else if (data.getOrderActivityType() == 1) {
            initPay();
        }
    }

    @Override
    public void setLuckDrawEnter() {
        initPay();
    }

    SureDialogFragment mSureDialogFragment;

    @Override
    public void initLuckDrawEnter() {
        mSureDialogFragment = new SureDialogFragment("十分抱歉，预约已结束", " 取消订单");
        mSureDialogFragment.setOnSureListener(this);
        mSureDialogFragment.setCancelable(false);
        mSureDialogFragment.show(getSupportFragmentManager(), "luck_draw_enter");
    }

    /**
     * 取消订单按钮
     */
    @Override
    public void onSure() {
        mPresenter.getCancelOrder(data.getTotalOrderId());
    }

    /**
     * 取消订单
     */
    @Override
    public void setCancelOrder() {
        if (mSureDialogFragment != null) {
            mSureDialogFragment.dismiss();
        }
        OrderActivity.orderType = OrderActivity.CANCEL_TYPE;
        RouterHelper.toCancelDetail(data.getTotalOrderId());
        finish();
    }

    /**
     * 接口请求失败
     */
    @Override
    public void initCancelOrder() {

    }

    private void initPay() {
        PayCenter payCenter = new PayCenter();
        payCenter.startPay(this,
                PayCenter.BUYTYPE_GOODSBUY,
                Integer.parseInt(data.getRealPayedPrice()) / 100 + "",
                data.getTotalOrderId(),
                new PayCenter.OnPayInfoListener() {
                    @Override
                    public void OnPayReslutListener(String orderid) {
                        //TODO HG  我要下单了
                        RouterHelper.toOrderSuccess((Integer.parseInt(data.getRealPayedPrice()) / 100) + "",1);
                        finish();
                    }
                });
    }

    @Override
    public void onNextRefund(String reason, int position) {
        OrderNetworkUtils.cancelOrder(this, totalOrderId, successful -> {
            ToastView.showToast(successful ? R.string.cancel_order_success : R.string.cancel_order_fail);
            // 如果在待支付页面  取消订单后  返回我的订单页面  需要显示待取消页面
            if (OrderActivity.orderType == OrderActivity.PAY_TYPE) {
                OrderActivity.orderType = OrderActivity.CANCEL_TYPE;
            }
            if (successful) finish();
        });
    }

    @Override
    public void showOrderDetail(GoodsDetail goodsDetail) {
        getData(goodsDetail);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateOrderAddressSuccess(AddressOrderBean orderAddressBean) {
        ToastView.showToast("修改收货地址成功");
        mOrderItemTvArea.setText(orderAddressBean.getProvince() + orderAddressBean.getCity() + orderAddressBean.getCountry());
        mOrderItemTvDetail.setText(orderAddressBean.getAddressDetails());
        mOrderItemTvName.setText(orderAddressBean.getContactsName());
        mOrderItemTvPhone.setText(orderAddressBean.getContactsPhone());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 200 || data == null) {
            return;
        }
        Bundle extras = data.getExtras();
        AddressOrderBean orderAddressBean = (AddressOrderBean) extras.get("addressOrderBean");
        // 修改地址
        mPresenter.updateOrderAddress(totalOrderId, orderAddressBean);
    }

}
package com.yuanbaogo.shopcart.order.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.finance.pay.presenter.PayCenter;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.AddressOrderBean;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.libpay.pay.Constant;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.commonlib.router.model.OrderNumBean;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.shopcart.R;
import com.yuanbaogo.shopcart.order.adapter.ConfirmOrderAdapter;
import com.yuanbaogo.shopcart.order.contract.ConfirmOrderContract;
import com.yuanbaogo.shopcart.order.model.CartSettleBean;
import com.yuanbaogo.shopcart.order.model.OrderCheckBean;
import com.yuanbaogo.shopcart.order.presenter.ConfirmOrderPresenter;
import com.yuanbaogo.zui.dialog.BillDialogView;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.toast.ToastView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 确认订单
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/4/21 4:03 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.CONFIRM_ORDER_ACTIVITY)
public class ConfirmOrderActivity extends MvpBaseActivityImpl<ConfirmOrderContract.View, ConfirmOrderPresenter>
        implements View.OnClickListener, ConfirmOrderContract.View, PayCenter.OnCallOrder {

    HeadView shopCartConfirmOrderHeadView;

    RecyclerView shopCartConfirmOrderRecycler;

    ConfirmOrderAdapter confirmOrderAdapter;

    RelativeLayout itemShopCartConfirmOrderInfoRlBill;

    Button shopCartConfirmOrderButSubmit;

    AddressOrderBean orderAddressBean;

    TextView itemShopCartConfirmOrderAddressTvNo;

    RelativeLayout itemShopCartConfirmOrderAddressRl;

    TextView itemShopCartConfirmOrderAddressTvDefault;

    TextView itemShopCartConfirmOrderAddressTvArea;

    TextView itemShopCartConfirmOrderAddressTvDetail;

    TextView itemShopCartConfirmOrderAddressTvName;

    TextView itemShopCartConfirmOrderAddressTvPhone;

    List<OrderCheckBean> orderCheckBean;

    //1 分类查看/立即购买  2 加入购物车 3 修改购物车规格  11 12 短视频带货|直播带货 立即购买 4 幸运拼团购买
    @Autowired(name = YBRouter.OrderActivityParams.type)
    int type;

    @Autowired(name = YBRouter.OrderActivityParams.skuIdList)
    ArrayList<String> mSkuIdList;

    @Autowired(name = YBRouter.OrderActivityParams.orderNumBean)
    ArrayList<OrderNumBean> orderNumBeans;

    TextView itemShopCartConfirmOrderInfoTvLumpSum;

    TextView shopCartConfirmOrderTvActualPayment;

    Long mAddressId;

    String totalOrderId;

    RelativeLayout itemShopCartConfirmOrderAddressRlAddress;

    @Autowired(name = YBRouter.OrderActivityParams.lot)
    String lot;

    @Autowired(name = YBRouter.OrderActivityParams.bizId)
    String bizId;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopCartConfirmOrderHeadView = findViewById(R.id.shop_cart_confirm_order_head_view);
        shopCartConfirmOrderRecycler = findViewById(R.id.shop_cart_confirm_order_recycler);
        itemShopCartConfirmOrderInfoRlBill = findViewById(R.id.item_shop_cart_confirm_order_info_rl_bill);
        shopCartConfirmOrderButSubmit = findViewById(R.id.shop_cart_confirm_order_but_submit);
        itemShopCartConfirmOrderAddressTvNo = findViewById(R.id.item_shop_cart_confirm_order_address_tv_no);
        itemShopCartConfirmOrderAddressRl = findViewById(R.id.item_shop_cart_confirm_order_address_rl);
        itemShopCartConfirmOrderAddressTvDefault = findViewById(R.id.item_shop_cart_confirm_order_address_tv_default);
        itemShopCartConfirmOrderAddressTvArea = findViewById(R.id.item_shop_cart_confirm_order_address_tv_area);
        itemShopCartConfirmOrderAddressTvDetail = findViewById(R.id.item_shop_cart_confirm_order_address_tv_detail);
        itemShopCartConfirmOrderAddressTvName = findViewById(R.id.item_shop_cart_confirm_order_address_tv_name);
        itemShopCartConfirmOrderAddressTvPhone = findViewById(R.id.item_shop_cart_confirm_order_address_tv_phone);
        itemShopCartConfirmOrderInfoTvLumpSum = findViewById(R.id.item_shop_cart_confirm_order_info_tv_lump_sum);
        shopCartConfirmOrderTvActualPayment = findViewById(R.id.shop_cart_confirm_order_tv_actual_payment);
        itemShopCartConfirmOrderAddressRlAddress = findViewById(R.id.item_shop_cart_confirm_order_address_rl_address);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        itemShopCartConfirmOrderInfoRlBill.setOnClickListener(this);
        shopCartConfirmOrderButSubmit.setOnClickListener(this);
        itemShopCartConfirmOrderAddressRlAddress.setOnClickListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.WX_MSG_OK);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.item_shop_cart_confirm_order_info_rl_bill) {
            BillDialogView billDialogView = new BillDialogView();
            billDialogView.show(getSupportFragmentManager(), "bill");
        } else if (id == R.id.shop_cart_confirm_order_but_submit) {
            if (mAddressId == null) {
                ToastView.showToast("请选择收货地址");
                return;
            }
            int num = 0;
            String skuid = null;
            for (int i = 0; i < orderCheckBean.size(); i++) {
                num = orderCheckBean.get(i).getNum();
                skuid = orderCheckBean.get(i).getSkuId();
            }
            if (!TextUtils.isEmpty(totalOrderId)) {//密码输入错误 或 设置密码后 回到确认订单页面 使用存在的订单号进行支付
                initCreateOrder();
            } else {
                if (type == 1) {
                    mPresenter.getCreateOrder(mAddressId, num, skuid, 0);
                } else if (type == 2) {
                    mPresenter.getCartSettle(new CartSettleBean()
                            .setAddressId(mAddressId)
                            .setType(0)
                            .setGoodsIdList(cartSettleGoodsBeans));
                } else if (type == TagParameteBean.liveBringGoods) {
                    mPresenter.getLiveBuyNow(bizId, new CartSettleBean()
                            .setAddressId(mAddressId)
                            .setType(2)
                            .setGoodsIdList(cartSettleGoodsBeans)
                            .setLot(lot));
                } else if (type == TagParameteBean.videoBringGoods) {
                    mPresenter.getLiveBuyNow(bizId, new CartSettleBean()
                            .setAddressId(mAddressId)
                            .setType(1)
                            .setGoodsIdList(cartSettleGoodsBeans)
                            .setLot(lot));
                } else if (type == 4) {
                    mPresenter.getLuckDrawSubscribe(mAddressId, orderNumBeans.get(0).getId());
                }
            }
        } else if (id == R.id.item_shop_cart_confirm_order_address_rl_address) {
            RouterHelper.toAddressRequest(ConfirmOrderActivity.this, 200, mAddressId + "");
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        mPresenter.getAddress(UserStore.getYbCode());
        if (type == 4) {
            mPresenter.getGoodsInfo(mSkuIdList.get(0));
        } else {
            mPresenter.getOrderCheck(mSkuIdList);
        }
    }

    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setRlTopNavigaBarBg(R.color.colorFFFFFF)
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("确认订单")
                .setImgRight(false);
        shopCartConfirmOrderHeadView.setHead(headBean);
    }

    /**
     * @param bean
     */
    @Override
    public void setAddress(AddressOrderBean bean) {
        orderAddressBean = bean;
        initAddress();
    }

    @Override
    public void initAddress() {
        if (orderAddressBean != null) {
            mAddressId = orderAddressBean.getAddressId();
            itemShopCartConfirmOrderAddressTvNo.setVisibility(View.GONE);
            itemShopCartConfirmOrderAddressRl.setVisibility(View.VISIBLE);
            if (orderAddressBean.getDefaulted()) {
                itemShopCartConfirmOrderAddressTvDefault.setVisibility(View.VISIBLE);
            } else {
                itemShopCartConfirmOrderAddressTvDefault.setVisibility(View.GONE);
            }
            itemShopCartConfirmOrderAddressTvArea.setText(orderAddressBean.getProvince()
                    + orderAddressBean.getCity() + orderAddressBean.getCountry());
            itemShopCartConfirmOrderAddressTvDetail.setText(orderAddressBean.getAddressDetails());
            itemShopCartConfirmOrderAddressTvName.setText(orderAddressBean.getContactsName());
            itemShopCartConfirmOrderAddressTvPhone.setText(
                    orderAddressBean.getContactsPhone().substring(0, 3) + "****"
                            + orderAddressBean.getContactsPhone().substring(7));
        } else {
            itemShopCartConfirmOrderAddressTvNo.setVisibility(View.VISIBLE);
            itemShopCartConfirmOrderAddressRl.setVisibility(View.GONE);
        }
    }

    long lumpSum = 0;

    List<CartSettleBean.CartSettleGoodsBean> cartSettleGoodsBeans;

    /**
     * @param bean
     */
    @Override
    public void setOrderCheck(List<OrderCheckBean> bean) {
        cartSettleGoodsBeans = new ArrayList<>();
        orderCheckBean = bean;
        initOrderCheck();

        for (int i = 0; i < orderCheckBean.size(); i++) {
            if (type == 1 || type == 2) {
                cartSettleGoodsBeans.add(new CartSettleBean.CartSettleGoodsBean()
                        .setGoodsType(orderCheckBean.get(i).getSpecialType())
                        .setNum(orderCheckBean.get(i).getNum())
                        .setSkuId(orderNumBeans.get(i).getSkuId()));
                lumpSum = lumpSum + (orderCheckBean.get(i).getSalePrice() * orderCheckBean.get(i).getNum());
            } else if (type == TagParameteBean.liveBringGoods || type == TagParameteBean.videoBringGoods) {
                cartSettleGoodsBeans.add(new CartSettleBean.CartSettleGoodsBean()
                        .setNum(orderCheckBean.get(i).getNum())
                        .setSkuId(orderNumBeans.get(i).getSkuId()));
                lumpSum = lumpSum + (orderCheckBean.get(i).getSalePrice() * orderCheckBean.get(i).getNum());
            } else if (type == 4) {
                lumpSum = lumpSum + (orderCheckBean.get(i).getGroupGoodsPrice() * orderCheckBean.get(i).getNum());
            }
        }
        itemShopCartConfirmOrderInfoTvLumpSum.setText(lumpSum / 100 + "");

        String countMoney = "实付¥" + lumpSum / 100;
        Spannable string = new SpannableString(countMoney);
        // 实付
        string.setSpan(new AbsoluteSizeSpan(30), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        string.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color424242)),
                0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // ￥
        string.setSpan(new AbsoluteSizeSpan(30), 2, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        string.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorEA5504)),
                2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 100
        string.setSpan(new AbsoluteSizeSpan(60), 3, string.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        string.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorEA5504)),
                3, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        shopCartConfirmOrderTvActualPayment.setText(string);
    }

    /**
     * 显示购买商品信息
     */
    @Override
    public void initOrderCheck() {
        initRecycler();
    }

    /**
     * @param bean
     */
    @Override
    public void setCreateOrder(String bean) {
        try {
            JSONObject obj = new JSONObject(bean);//最外层的JSONObject对象
            if (type == 4) {
                totalOrderId = obj.getString("orderId");//通过name字段获取其所包含的字符串
            } else {
                totalOrderId = obj.getString("totalOrderId");//通过name字段获取其所包含的字符串
            }
            if (!TextUtils.isEmpty(totalOrderId)) {
                initCreateOrder();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setCartSettle(String bean) {
        totalOrderId = bean;
        if (!TextUtils.isEmpty(totalOrderId)) {
            initCreateOrder();
        }
    }

    /**
     * 付款
     */
    @Override
    public void initCreateOrder() {
        if (!TextUtils.isEmpty(totalOrderId)) {
            PayCenter payCenter = new PayCenter();
            payCenter.startPay(ConfirmOrderActivity.this,
                    PayCenter.BUYTYPE_GOODSBUY,
                    (lumpSum / 100) + "",
                    totalOrderId,
                    new PayCenter.OnPayInfoListener() {
                        @Override
                        public void OnPayReslutListener(String orderid) {
                            //TODO HG  我要下单了
                            RouterHelper.toOrderSuccess((lumpSum / 100) + "", type);
                            finish();
                        }
                    });
            payCenter.setOnCallRight(this);
        }
    }

    private void initRecycler() {
        if (orderNumBeans != null) {
            for (int i = 0; i < orderCheckBean.size(); i++) {
                for (int j = 0; j < orderNumBeans.size(); j++) {
                    if (orderNumBeans.get(j).getSkuId().equals(orderCheckBean.get(i).getSkuId())) {
                        orderCheckBean.get(i).setNum(orderNumBeans.get(j).getGoodsCount());
                    }
                }
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ConfirmOrderActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        shopCartConfirmOrderRecycler.setLayoutManager(linearLayoutManager);
        confirmOrderAdapter = new ConfirmOrderAdapter(ConfirmOrderActivity.this, orderCheckBean,
                R.layout.item_shop_cart_confirm_order);
        shopCartConfirmOrderRecycler.setAdapter(confirmOrderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (data != null) {
                Bundle extras = data.getExtras();
                orderAddressBean = (AddressOrderBean) extras.get("addressOrderBean");
                initAddress();
            }
        }
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int resp = intent.getIntExtra("code", 0);
            if (action.equals(Constant.WX_MSG_OK)) {
                if (resp == 0) {
                    RouterHelper.toOrderSuccess((lumpSum / 100) + "", type);
                    finish();
                } else if (resp == -2) {
                    RouterHelper.toOrderList(1);
                    finish();
                }
            }
        }
    };

    /**
     * 购买商品 离开跳转待付款页面
     */
    @Override
    public void onPendingPayment() {
        // 待付款
        RouterHelper.toOrderList(1);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

}
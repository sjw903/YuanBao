package com.yuanbaogo.finance.pay.presenter;

import android.view.Gravity;

import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.finance.R;
import com.yuanbaogo.finance.pay.model.PayTypeBean;
import com.yuanbaogo.finance.pay.model.WeChatPayBean;
import com.yuanbaogo.finance.pay.view.PayCenterPop;
import com.yuanbaogo.finance.pay.weight.PayDialogFragment;
import com.yuanbaogo.finance.pay.weight.PayDialogView;
import com.yuanbaogo.libbase.baseutil.Md5Util;
import com.yuanbaogo.libpay.pay.WXPayEx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 @package
 @filename
 */

public class PayCenter implements PayCenterPop.OnCallRight {
    PayCenterPop mPopupWindow;
    String buyType;//购买类型(coinPointBuy:元宝积分充值, goodsBuy:购物, groupBuy:拼团),示例值(coinPointBuy)
    int chanelType;
    String orderid;
    String orderPrice;
    FragmentActivity mActivity;
    private WXPayEx wxpay;
    private List<PayTypeBean> mPayTypeList;

    public static final String BUYTYPE_COINPOINTBUY = "coinPointBuy";
    public static final String BUYTYPE_GOODSBUY = "goodsBuy";
    public static final String BUYTYPE_GROUPBUY = "groupBuy";


    public static final String PAYTYPE_WX = "WeChatPay";
    public static final String PAYTYPE_YB = "YbPay";


    public static final int PAYCHANNEL_WX = 0x03;
    public static final int PAYCHANNEL_JF = 0x04;
    //    public static final int PAYCHANNEL_UNIT =0x05;
    public static final int PAYCHANNEL_GROUP = 0x06;

    private PayDialogView payDialogView;

    private OnPayInfoListener mOnListener;

    public interface OnPayInfoListener {
        void OnPayReslutListener(String orderid);
    }

    public void startPay(FragmentActivity activity, String buyType, String price, String orderid, OnPayInfoListener onListener) {
        this.orderPrice = price;
        this.buyType = buyType;
        this.orderid = orderid;
        this.mActivity = activity;
        this.mOnListener = onListener;
        getPayType();
    }

    private void getPayType() {
        String url = "/" + buyType + "/" + orderid;
        NetWork.getInstance().get(mActivity, ChildUrl.getPayType + url, null, new RequestSateListener<List<PayTypeBean>>() {
            @Override
            public void onSuccess(int code, List<PayTypeBean> list) {
                mPayTypeList = list;
                mPopupWindow = new PayCenterPop(mActivity, list, orderPrice, orderid, buyType);
                //如果是商品购买 需要回调到确认订单页面 取消支付 跳转待支付页面
                if (buyType.equals(BUYTYPE_GOODSBUY)) {
                    mPopupWindow.setOnCallRight(PayCenter.this);
                }
                mPopupWindow.setOutside(false);
                mPopupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                mPopupWindow.setOnPayClickListener(new PayCenterPop.OnPayClickListener() {
                    @Override
                    public void OnPayClick(int payChanelType) {
                        chanelType = payChanelType;
                        if (payChanelType == PayCenter.PAYCHANNEL_JF) {
                            getUserPassword("0", orderPrice, PAYTYPE_YB);
                        } else if (payChanelType == PayCenter.PAYCHANNEL_WX) {
                            doStartPay(orderPrice, "0", PAYTYPE_WX);
                        } else if (payChanelType == PayCenter.PAYCHANNEL_GROUP) {
                            String cash = "";
                            String point = "";
                            for (PayTypeBean bean : mPayTypeList) {
                                if (bean.getPayAwayCode().equals(PAYTYPE_YB)) {
                                    point = bean.getBalance();
                                } else if (bean.getPayAwayCode().equals(PAYTYPE_WX)) {
                                    cash = bean.getMoney();
                                }
                            }
                            getUserPassword(cash, point, PAYTYPE_WX);
                        }
                    }
                });
                initWxpay();
            }

            @Override
            public void onFailure(Throwable e) {
            }
        });
    }

    private void initWxpay() {
        if (wxpay == null) {
            wxpay = new WXPayEx();
            wxpay.initWXAPI(mActivity);
        }
    }

    private void doStartPay(String cash, String point, String payType) {
        if (TextUtils.isEmpty(UserStore.getToken())) {
            RouterHelper.toLogin();
            return;
        }
        Map<String, Object> params = new HashMap<>();
        String url = "";
        params.put("buyType", buyType);
        params.put("payType", payType);
        params.put("payment", orderPrice);
        params.put("point", point);
        params.put("cash", cash);
        params.put("orderId", orderid);

        if (buyType.equals(BUYTYPE_COINPOINTBUY)) {
            url = ChildUrl.getPayInfo;
        } else if (buyType.equals(BUYTYPE_GROUPBUY)) {
            url = ChildUrl.getbuyPayInfo;
        } else if (buyType.equals(BUYTYPE_GOODSBUY)) {
            url = ChildUrl.getgoodsPayInfo;
        }

        NetWork.getInstance().post(mActivity, url, params, new RequestSateListener<WeChatPayBean>() {
            @Override
            public void onSuccess(int code, WeChatPayBean bean) {
                String partnerid = bean.getPartnerid();
                String prepayid = bean.getPrepayid();
                String packageValue = bean.getPackageValue();
                String noncestr = bean.getNoncestr();
                String timestamp = bean.getTimestamp();
                String sign = bean.getSign();
                String orderId = bean.getOrderId();
                if (buyType.equals(BUYTYPE_COINPOINTBUY)) {
                    if (payType.equals(PAYTYPE_WX)) {//微信支付
                        wxpay.doStartWXPay(mActivity, prepayid, noncestr, timestamp, packageValue, sign, partnerid);
                    }
                    mOnListener.OnPayReslutListener(orderId);
                } else if (buyType.equals(BUYTYPE_GOODSBUY)) {//商品订单支付
                    if (payType.equals(PAYTYPE_WX)) {//微信支付
                        wxpay.doStartWXPay(mActivity, prepayid, noncestr, timestamp, packageValue, sign, partnerid);
                    } else if (payType.equals(PAYTYPE_YB)) {//元宝积分支付
                        if (mOnListener != null) {
                            if (mPopupWindow != null) {
                                mPopupWindow.dismiss();
                            }
                            mOnListener.OnPayReslutListener(orderId);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Throwable e) {
            }
        });
    }

    public void onDismiss(){
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    private void verifyUserPayPassword(String payPassword, String cash, String point, String payType) {
        Map<String, Object> params = new HashMap<>();
        String md5Password = Md5Util.stringMd5(payPassword);
        params.put("payPassword", md5Password);
        NetWork.getInstance().post(mActivity, ChildUrl.VERIFY_PAY_PASSWORD, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String str) {
                if (code == NetConfig.NETWORK_SUCCESSFUL && Boolean.parseBoolean(str)) {
                    doStartPay(cash, point, payType);
                }
                payDialogView.dismiss();
            }

            @Override
            public void onFailure(Throwable e) {
            }

            @Override
            public boolean onHandleMessage(Throwable var1, String code, String msg) {
                if ("A0400".equals(code)) {
                    if ("4".equals(msg)) {
                        payDialogView.setType(2);
                        payDialogView.setPwdNumber(Integer.parseInt(msg));
                    } else {
                        payDialogView.setType(1);
                        payDialogView.setPwdNumber(Integer.parseInt(msg));
                    }
                } else if ("A0500".equals(code)) {
                    payDialogView.setType(3);
                    payDialogView.initRefresh(3);
                }
                return true;
            }
        });
    }

    public void getUserPassword(String cash, String point, String payType) {
        NetWork.getInstance().get(mActivity, ChildUrl.VERIFY_HAS_PAY_PASSWORD, null, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                if (code == NetConfig.NETWORK_SUCCESSFUL && Boolean.parseBoolean(bean)) {
                    checkUserStatus(cash, point, payType);
                } else {
                    showNumberPasswordDialog();
                }
            }

            @Override
            public void onFailure(Throwable var1) {
            }
        });
    }

    //查看用户状态(冻结是true正常状态是false)
    public void checkUserStatus(String cash, String point, String payType) {
        NetWork.getInstance().get(mActivity, ChildUrl.checkUserStatus, null, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                payDialogView = new PayDialogView();
                if (code == NetConfig.NETWORK_SUCCESSFUL && !Boolean.parseBoolean(bean)) {
                    payDialogView.setType(0);
                } else {
                    payDialogView.setType(4);
                }
                payDialogView.setOnRefreshData(new PayDialogView.OnRefreshData() {
                    @Override
                    public void onRefresh(int type, String pwd) {
                        verifyUserPayPassword(pwd, cash, point, payType);
                    }
                });
                payDialogView.show(mActivity.getSupportFragmentManager(), "pay");
            }

            @Override
            public void onFailure(Throwable var1) {
            }
        });
    }

    private void showNumberPasswordDialog() {
        String mTitle = mActivity.getResources().getString(R.string.password_not_set);
        String mSubtitle = mActivity.getResources().getString(R.string.password_set);
        String mLefttxt = "取消";
        String mRighttxt = "立即设置";
        PayDialogFragment mDialog = new PayDialogFragment(mTitle, mSubtitle, mLefttxt, mRighttxt);
        mDialog.show(mActivity.getSupportFragmentManager(), "showNumberPassword");
        mDialog.setOnNextOpenPayPassword(new PayDialogFragment.OnNextOpenPayPassword() {
            @Override
            public void onNextOpen() {
                RouterHelper.toPaySet();
            }
        });
    }

    /**
     * 购买商品 离开跳转待付款页面
     */
    @Override
    public void onRight() {
        if (onCallOrder != null) {
            onCallOrder.onPendingPayment();
        }
    }

    OnCallOrder onCallOrder;

    public void setOnCallRight(OnCallOrder onCallOrder) {
        this.onCallOrder = onCallOrder;
    }

    public interface OnCallOrder {
        void onPendingPayment();
    }

}

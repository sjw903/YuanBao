package com.yuanbaogo.mine.order.receipt.refund;

import android.util.Log;

import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.mine.order.model.GoodsDetail;

import java.util.HashMap;
import java.util.Map;

public class ApplyRefundDetailPresenter extends MvpBasePersenter<ApplyRefundContract.View> implements ApplyRefundContract.Presenter {

    private static final String TAG = "ApplyRefundDetailPresenter";

//    @Override
//    public void applyRefund(String orderId, int reasonType,String afterSalePrice) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("orderId", orderId);
//        params.put("afterSalePrice", afterSalePrice);
//        params.put("reasonType", reasonType);
//        params.put("salesType", 2);
//        params.put("goodsStatus", 1);
//        NetWork.getInstance().post(getContext(), ChildUrl.APPLY_REFUND, params, new RequestSateListener<String>() {
//            @Override
//            public void onSuccess(int code, String bean) {
//                Log.d(TAG, "applyRefund onSuccess: code:" + code + "   bean:" + bean);
//                if (getView() == null || !isActive()) {
//                    return;
//                }
//                getView().applyResult(code == NetConfig.NETWORK_SUCCESSFUL && Boolean.parseBoolean(bean));
//            }
//
//            @Override
//            public void onFailure(Throwable var1) {
//                Log.e(TAG, "applyRefund onFailure: " + var1.getMessage());
//                if (getView() == null || !isActive()) {
//                    return;
//                }
//                ToastUtil.showToast(var1.getMessage());
//                getView().applyResult(false);
//            }
//        });
//    }

    @Override
    public void applyRefund(String orderId, String reason, String name, String phone) {
        Map<String, Object> params = new HashMap<>();
        params.put("totalOrderId", orderId);
        params.put("cancelReason", reason);
        params.put("cancelName", name);
        params.put("cancelPhone", phone);
        NetWork.getInstance().post(getContext(), ChildUrl.CANCEL_ORDER_APPLY, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "applyRefund onSuccess: code:" + code + "   bean:" + bean);
                if (getView() == null || !isActive()) {
                    return;
                }
                getView().applyResult(code == NetConfig.NETWORK_SUCCESSFUL && bean != null);
            }

            @Override
            public void onFailure(Throwable var1) {
                if (getView() == null || !isActive()) {
                    return;
                }
                ToastUtil.showToast(var1.getMessage());
                getView().applyResult(false);
            }
        });
    }

    @Override
    public void getOrderInformation(String totalOrderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("totalOrderId", totalOrderId);
        NetWork.getInstance().post(getContext(), ChildUrl.GET_ORDER_DETAIL,
                params, new RequestSateListener<GoodsDetail>() {
                    @Override
                    public void onSuccess(int code, GoodsDetail bean) {
                        if (getView() == null || !isActive()) {
                            return;
                        }
                        getView().setOrderInformation(bean);
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() == null || !isActive()) {
                            return;
                        }
                    }
                });
    }
}

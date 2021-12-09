package com.yuanbaogo.mine.order.pay.detail;

import android.util.Log;

import com.yuanbaogo.commonlib.router.model.AddressOrderBean;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.order.model.GoodsDetail;

import java.util.HashMap;
import java.util.Map;

public class PayDetailPresenter extends MvpBasePersenter<PayDetailContract.View> implements PayDetailContract.Presenter {

    @Override
    public void getOrderDetail(String totalOrderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("totalOrderId", totalOrderId);
        NetWork.getInstance().post(getContext(), ChildUrl.GET_ORDER_DETAIL, params, new RequestSateListener<GoodsDetail>() {
            @Override
            public void onSuccess(int code, GoodsDetail bean) {
                Log.d(TAG, "getAddressList onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                getView().showOrderDetail(bean);
            }

            @Override
            public void onFailure(Throwable var1) {
            }
        });
    }

    @Override
    public void updateOrderAddress(String totalOrderId, AddressOrderBean orderAddressBean) {
        Map<String, Object> params = new HashMap<>();
        params.put("addressId", orderAddressBean.getAddressId());
        params.put("totalOrderId", totalOrderId);
        params.put("ybCode", UserStore.getYbCode());
        NetWork.getInstance().post(getContext(), ChildUrl.UPDATE_ORDER_ADDRESS, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "getAddressList onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive() || bean == null) {
                    return;
                }
                getView().updateOrderAddressSuccess(orderAddressBean);
            }

            @Override
            public void onFailure(Throwable var1) {
            }
        });
    }

    @Override
    public void getLuckDrawEnter(String orderId) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance()
                .get(getContext(),
                        ChildUrl.LUCK_DRAW_ENTER.replace("{orderId}", orderId),
                        params,
                        new RequestSateListener<String>() {
                            @Override
                            public void onSuccess(int code, String bean) {
                                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                                    return;
                                }
                                if (bean != null) {
                                    getView().setLuckDrawEnter();
                                }
                            }

                            @Override
                            public void onFailure(Throwable var1) {
                                if (getView() == null || !isActive()) {
                                    return;
                                }
                                getView().initLuckDrawEnter();
                            }
                        });
    }

    @Override
    public void getCancelOrder(String totalOrderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("totalOrderId", totalOrderId);
        NetWork.getInstance()
                .post(getContext(),
                        ChildUrl.CANCEL_ORDER,
                        params,
                        new RequestSateListener<String>() {
                            @Override
                            public void onSuccess(int code, String bean) {
                                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                                    return;
                                }
                                if (bean != null) {
                                    getView().setCancelOrder();
                                }
                            }

                            @Override
                            public void onFailure(Throwable var1) {
                                if (getView() == null || !isActive()) {
                                    return;
                                }
                                getView().initCancelOrder();
                            }
                        });
    }

}

package com.yuanbaogo.mine.order.receipt.detail;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.GsonUtil;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.receipt.detail.model.NewLogisticsModel;

import java.util.HashMap;
import java.util.Map;

public class ReceiptDetailPresenter extends MvpBasePersenter<ReceiptDetailContract.View> implements ReceiptDetailContract.Presenter {

    private static final String TAG = "ReceiptDetailPresenter";

    public void getData(String totalOrderId) {
        // 获取订单详情
        getOrderDetail(totalOrderId);
    }

    @Override
    public void getOrderDetail(String totalOrderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("totalOrderId", totalOrderId);
        NetWork.getInstance().post(getContext(), ChildUrl.GET_ORDER_DETAIL, params, new RequestSateListener<GoodsDetail>() {
            @Override
            public void onSuccess(int code, GoodsDetail bean) {
                Log.d(TAG, "getOrderDetail onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                getView().showOrderDetail(bean);
                // 获取物流详情
                getOrderLogistics(totalOrderId);
            }

            @Override
            public void onFailure(Throwable var1) {
            }
        });
    }

    @Override
    public void getOrderLogistics(String totalOrderId) {
        NetWork.getInstance().get(getContext(), ChildUrl.GET_ORDER_LOGISTICS_NEW + "/" + totalOrderId, null, new RequestSateListener<NewLogisticsModel>() {
            @Override
            public void onSuccess(int code, NewLogisticsModel bean) {
                Log.d(TAG, "getOrderDetail onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                getView().showOrderLogistics(bean);
            }

            @Override
            public void onFailure(Throwable var1) {
            }
        });
    }

    @Override
    public void getOrderLogisticsDetails(String totalOrderId) {
        NetWork.getInstance().get(getContext(), ChildUrl.GET_ORDER_LOGISTICS_DETAILS + "/" + totalOrderId, null, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "getOrderDetail onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }

                getView().getOrderLogisticsDetails(GsonUtil.GsonToList(bean, NewLogisticsModel.class));
            }

            @Override
            public void onFailure(Throwable var1) {
            }
        });
    }
}

package com.yuanbaogo.mine.order.cancel.detail;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.order.model.GoodsDetail;

import java.util.HashMap;
import java.util.Map;

public class CancelDetailPresenter extends MvpBasePersenter<CancelDetailContract.View> implements CancelDetailContract.Presenter {

    private static final String TAG = "CancelDetailPresenter";

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
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getOrderDetail onFailure: " + var1.getMessage());
            }
        });
    }

}

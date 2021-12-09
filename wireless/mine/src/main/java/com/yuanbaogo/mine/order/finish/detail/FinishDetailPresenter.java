package com.yuanbaogo.mine.order.finish.detail;

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
import java.util.List;
import java.util.Map;

public class FinishDetailPresenter extends MvpBasePersenter<FinishDetailContract.View>
        implements FinishDetailContract.Presenter {

    private static final String TAG = "FinishDetailPresenter";

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
                Log.e(TAG, "getAddressList onFailure: " + var1.getMessage());
            }
        });
    }

    @Override
    public void getOrderLogisticsDetails(String totalOrderId) {
        NetWork.getInstance().get(getContext(), ChildUrl.GET_ORDER_LOGISTICS_DETAILS + "/" + totalOrderId,
                null, new RequestSateListener<List<NewLogisticsModel>>() {
                    @Override
                    public void onSuccess(int code, List<NewLogisticsModel> bean) {
                        Log.d(TAG, "getOrderDetail onSuccess: code:" + code + "   bean:" + bean);
                        if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                            return;
                        }
                        getView().getOrderLogisticsDetails(bean);
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        Log.e(TAG, "getOrderDetail onFailure: " + var1.getMessage());
                    }
                });
    }

}

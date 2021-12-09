package com.yuanbaogo.mine.order.cancel.refund;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;

public class RefundDetailPresenter extends MvpBasePersenter<RefundDetailContract.View> implements RefundDetailContract.Presenter {

    @Override
    public void getAfterSalesDetails(String orderId) {
        NetWork.getInstance().get(getContext(), ChildUrl.GET_CANCEL_PROCESS + "/" + orderId, null, new RequestSateListener<RefundCancelModel>() {
            @Override
            public void onSuccess(int code, RefundCancelModel bean) {
                Log.d(TAG, "getAllOrder onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                getView().showAfterSales(bean);
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getAllOrder onFailure: " + var1.getMessage());
                OrderNetworkUtils.disposeError(var1);
            }
        });
    }

}

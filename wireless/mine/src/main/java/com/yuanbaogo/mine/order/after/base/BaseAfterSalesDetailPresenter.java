package com.yuanbaogo.mine.order.after.base;

import android.util.Log;

import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.order.after.model.AfterSalesDetailsModel;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;

public class BaseAfterSalesDetailPresenter<T extends BaseAfterSalesDetailContract.View> extends MvpBasePersenter<BaseAfterSalesDetailContract.View> implements BaseAfterSalesDetailContract.Presenter {

    @Override
    public void getAfterSalesDetails(String orderId) {
        NetWork.getInstance().get(getContext(), ChildUrl.GET_AFTER_SALES_DETAILS + "/" + orderId, null, new RequestSateListener<AfterSalesDetailsModel>() {
            @Override
            public void onSuccess(int code, AfterSalesDetailsModel bean) {
                Log.d(TAG, "getAfterSalesDetails onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                getView().showAfterSales(bean);
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getAfterSalesDetails onFailure: " + var1.getMessage());
                OrderNetworkUtils.disposeError(var1);
            }
        });
    }

}

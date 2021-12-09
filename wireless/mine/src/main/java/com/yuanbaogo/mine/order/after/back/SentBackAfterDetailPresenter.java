package com.yuanbaogo.mine.order.after.back;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.GsonUtil;
import com.yuanbaogo.mine.order.after.model.AfterSalesDetailsModel;
import com.yuanbaogo.mine.order.after.model.RefundStatusModel;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.zui.toast.ToastView;

public class SentBackAfterDetailPresenter extends MvpBasePersenter<SentBackAfterDetailContract.View> implements SentBackAfterDetailContract.Presenter {
    @Override
    public void getAfterSalesDetails(String orderId) {
        NetWork.getInstance().get(getContext(), ChildUrl.GET_AFTER_SALES_DETAILS + "/" + orderId, null, new RequestSateListener<AfterSalesDetailsModel>() {
            @Override
            public void onSuccess(int code, AfterSalesDetailsModel bean) {
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

    @Override
    public void getRefundStatus(String salesId) {
        NetWork.getInstance().get(getContext(), ChildUrl.GET_REFUND_STATUS + "/" + salesId, null, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "getCancelOrder onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                getView().showRefundStatus(GsonUtil.GsonToList(bean, RefundStatusModel.class));
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getCancelOrder onFailure: " + var1.getMessage());
                if (getView() == null || !isActive()) {
                    return;
                }
                ToastView.showToast(var1.getMessage());
            }
        });
    }
}

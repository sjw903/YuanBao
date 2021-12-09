package com.yuanbaogo.mine.order.after.pending;

import android.util.Log;

import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.GsonUtil;
import com.yuanbaogo.mine.order.after.model.RefundStatusModel;
import com.yuanbaogo.zui.toast.ToastView;

public class PendingReviewAfterPresenter extends MvpBasePersenter<PendingReviewAfterContract.View> implements PendingReviewAfterContract.Presenter {
    @Override
    public void getRefundStatus(String salesId) {
        NetWork.getInstance().get(getContext(), ChildUrl.GET_REFUND_STATUS + "/" + salesId, null, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "getRefundStatus onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                getView().showRefundStatus(GsonUtil.GsonToList(bean, RefundStatusModel.class));
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getRefundStatus onFailure: " + var1.getMessage());
                if (getView() == null || !isActive()) {
                    return;
                }
                ToastView.showToast(var1.getMessage());
            }
        });
    }
}

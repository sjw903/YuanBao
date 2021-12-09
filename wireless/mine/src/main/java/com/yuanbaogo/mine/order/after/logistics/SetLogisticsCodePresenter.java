package com.yuanbaogo.mine.order.after.logistics;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.GsonUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetLogisticsCodePresenter extends MvpBasePersenter<SetLogisticsCodeContract.View> implements SetLogisticsCodeContract.Presenter {

    @Override
    public void selectLogisticsCompany() {
        NetWork.getInstance().get(getContext(), ChildUrl.SELECT_LOGISTICS_COMPANY + "/4", null, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "getOrderDetail onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                List<LogisticsCompanyModel> logisticsCompanyModels = GsonUtil.GsonToList(bean, LogisticsCompanyModel.class);
                if (logisticsCompanyModels.isEmpty()) {
                    ToastView.showToast("获取物流公司信息失败");
                } else {
                    getView().showLogisticsList(logisticsCompanyModels);
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getOrderDetail onFailure: " + var1.getMessage());
            }
        });
    }


    @Override
    public void setLogisticsCode(int companyType, String logisticsId, String orderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("companyType", companyType);
        params.put("logisticsId", logisticsId);
        params.put("orderId", orderId);
        NetWork.getInstance().post(getContext(), ChildUrl.ADD_LOGISTICS, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "getOrderDetail onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                if (bean != null) {
                    getView().showSetLogisticsResult();
                } else {
                    ToastView.showToast(R.string.set_logistics_fail);
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getOrderDetail onFailure: " + var1.getMessage());
            }
        });
    }
}

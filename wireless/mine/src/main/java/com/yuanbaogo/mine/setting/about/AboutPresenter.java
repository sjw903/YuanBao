package com.yuanbaogo.mine.setting.about;

import android.util.Log;

import com.yuanbao.update.utils.APKInfoUtil;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;

import java.util.HashMap;
import java.util.Map;

public class AboutPresenter extends MvpBasePersenter<AboutContract.View> implements AboutContract.Presenter {

    @Override
    public void getNewVersion() {
        Map<String, Object> params = new HashMap<>();
        params.put("terminal", "android");
        params.put("version", APKInfoUtil.getVersionName(getContext()));
        NetWork.getInstance().post(getContext(), ChildUrl.GET_NEW_VERSION, params, new RequestSateListener<ApkInfoModel>() {
            @Override
            public void onSuccess(int code, ApkInfoModel bean) {
                Log.d(TAG, "getAddressList onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || !isActive()) {
                    return;
                }
                if (bean != null) {
                    getView().showNewVersion(bean);
                } else {
                    OrderNetworkUtils.disposeError(null);
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getAddressList onFailure: " + var1.getMessage());
                if (getView() == null || !isActive()) {
                    return;
                }
                OrderNetworkUtils.disposeError(var1);
            }
        });
    }
}

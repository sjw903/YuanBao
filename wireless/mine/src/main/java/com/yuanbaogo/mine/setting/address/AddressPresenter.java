package com.yuanbaogo.mine.setting.address;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;
import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.mine.setting.model.AddressListBean;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.Map;

public class AddressPresenter extends MvpBasePersenter<AddressContract.View> implements AddressContract.Presenter {

    private static final String TAG = "AddressPresenter";

    @Override
    public void getAddressList(int page) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("rows", LOAD_ROWS);
        NetWork.getInstance().post(getContext(), ChildUrl.GET_ADDRESS_LIST, params, new RequestSateListener<AddressListBean>() {
            @Override
            public void onSuccess(int code, AddressListBean bean) {
                Log.d(TAG, "getAddressList onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                if (bean.getRows() != null && bean.getRows().size() > 0) {
                    getView().loadAddressList(bean.getRows());
                } else {
                    if (page == DEFAULT_ONE) {
                        getView().loadAddressList(null);
                    } else {
                        getView().loadFailure();
                        ToastView.showToast(R.string.order_no_data_toast);
                    }
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getAddressList onFailure: " + var1.getMessage());
                if (getView() == null || !isActive()) {
                    return;
                }
                OrderNetworkUtils.disposeError(var1);
                getView().loadFailure();
            }
        });
    }

    @Override
    public void deleteAddress(long addressId) {
        Map<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);
        NetWork.getInstance().post(getContext(), ChildUrl.DELETE_ADDRESS, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "getAddressList onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                if (Boolean.parseBoolean(bean)) {
                    getView().deleteAddress(addressId);
                } else {
                    ToastView.showToast("新增收货地址失败，请检查网络");
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

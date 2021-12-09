package com.yuanbaogo.mine.order.all;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;
import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.model.AllOrderModel;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: ZJ
 * @Date: 8/5/21 8:32 AM
 * @Modifier:
 * @Modify:
 */
public class AllOrderPresenter extends MvpBasePersenter<AllOrderContract.View> implements AllOrderContract.Presenter {

    private static final String TAG = "AllOrderPresenter";

    @Override
    public void getAllOrder(int page) {
        getAllOrder(page, "");
    }

    @Override
    public void getAllOrder(int page, String screen) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("rows", LOAD_ROWS);
        params.put("screen", screen);
        Log.e(TAG, "getAllOrder: params:" + params);
        NetWork.getInstance().post(getContext(), ChildUrl.GET_ALL_ORDER, params, new RequestSateListener<AllOrderModel>() {
            @Override
            public void onSuccess(int code, AllOrderModel bean) {
                Log.d(TAG, "getAllOrder onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                if (bean.getOrderListResponseList() != null && bean.getOrderListResponseList().size() > 0) {
                    getView().showAllOrder(bean.getOrderListResponseList());
                } else {
                    if (page == DEFAULT_ONE) {
                        getView().showAllOrder(null);
                    } else {
                        getView().loadFail(null);
                        ToastView.showToast(R.string.order_no_data_toast);
                    }
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getAllOrder onFailure: " + var1.getMessage());
                if (getView() != null && isActive()) {
                    getView().loadFail(var1);
                }
            }
        });
    }

}

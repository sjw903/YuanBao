package com.yuanbaogo.mine.order.cancel;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;
import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.model.OrderModel;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.Map;


/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 4:32 PM
 * @Modifier:
 * @Modify:
 */
public class CancelOrderPresenter extends MvpBasePersenter<CancelOrderContract.View> implements CancelOrderContract.Presenter {

    private static final String TAG = "CancelOrderPresenter";

    @Override
    public void getCancelOrder(int page, String screen) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("screen", screen);
        params.put("rows", LOAD_ROWS);
        NetWork.getInstance().post(getContext(), ChildUrl.GET_CANCEL_ORDER, params, new RequestSateListener<OrderModel>() {
            @Override
            public void onSuccess(int code, OrderModel bean) {
                Log.d(TAG, "getCancelOrder onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                if (bean.getRows() != null && bean.getRows().size() > 0) {
                    getView().showCancelOrder(bean.getRows());
                } else {
                    Log.e(TAG, "onSuccess: " + page + "   " + DEFAULT_ONE);
                    if (page == DEFAULT_ONE) {
                        getView().showCancelOrder(null);
                    } else {
                        getView().loadFail(null);
                        ToastView.showToast(R.string.order_no_data_toast);
                    }
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getCancelOrder onFailure: " + var1.getMessage());
                if (getView() != null && isActive()) {
                    getView().loadFail(var1);
                }
            }
        });
    }

    @Override
    public void getCancelOrder(int page) {
        getCancelOrder(page, "");
    }
}

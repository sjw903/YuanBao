package com.yuanbaogo.mine.order.finish;

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

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;
import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 4:32 PM
 * @Modifier:
 * @Modify:
 */
public class FinishOrderPresenter extends MvpBasePersenter<FinishOrderContract.View> implements FinishOrderContract.Presenter {

    private static final String TAG = "FinishOrderPresenter";

    @Override
    public void getFinishOrder(int page, String screen) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("rows", LOAD_ROWS);
        params.put("screen", screen);
        NetWork.getInstance().post(getContext(), ChildUrl.GET_FINISH_ORDER, params, new RequestSateListener<OrderModel>() {
            @Override
            public void onSuccess(int code, OrderModel bean) {
                Log.d(TAG, "getFinishOrder onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                if (bean.getRows() != null && bean.getRows().size() > 0) {
                    getView().showFinishOrder(bean.getRows());
                } else {
                    Log.e(TAG, "onSuccess: " + page + "   " + DEFAULT_ONE);
                    if (page == DEFAULT_ONE) {
                        getView().showFinishOrder(null);
                    } else {
                        getView().loadFail(null);
                        ToastView.showToast(R.string.order_no_data_toast);
                    }
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getFinishOrder onFailure: " + var1.getMessage());
                if (getView() != null && isActive()) {
                    getView().loadFail(var1);
                }
            }
        });
    }

    @Override
    public void getFinishOrder(int page) {
        getFinishOrder(page, "");
    }
}

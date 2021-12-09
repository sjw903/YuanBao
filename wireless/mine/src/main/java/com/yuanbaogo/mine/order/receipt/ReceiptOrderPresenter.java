package com.yuanbaogo.mine.order.receipt;

import android.util.Log;

import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
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
public class ReceiptOrderPresenter extends MvpBasePersenter<ReceiptOrderContract.View> implements ReceiptOrderContract.Presenter {

    private static final String TAG = "ReceiptOrderPresenter";

    @Override
    public void getReceiptOrder(int page, String screen) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("rows", LOAD_ROWS);
        params.put("screen", screen);
        NetWork.getInstance().post(getContext(), ChildUrl.GET_RECEIPT_ORDER, params, new RequestSateListener<OrderModel>() {
            @Override
            public void onSuccess(int code, OrderModel bean) {
                Log.d(TAG, "getReceiptOrder onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                if (bean.getRows() != null && bean.getRows().size() > 0) {
                    getView().showReceiptOrder(bean.getRows());
                } else {
                    if (page == DEFAULT_ONE) {
                        getView().showReceiptOrder(null);
                    } else {
                        getView().loadFail(null);
                        ToastView.showToast(R.string.order_no_data_toast);
                    }
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                if (getView() != null && isActive()) {
                    getView().loadFail(var1);
                }
            }
        });
    }

    @Override
    public void getReceiptOrder(int page) {
        getReceiptOrder(page, "");
    }
}

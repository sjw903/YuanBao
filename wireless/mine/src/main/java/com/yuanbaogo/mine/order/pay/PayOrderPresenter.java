package com.yuanbaogo.mine.order.pay;

import android.util.Log;

import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.GsonUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.model.LikeListModelItem;
import com.yuanbaogo.mine.order.model.PayOrderModel;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 4:32 PM
 * @Modifier:
 * @Modify:
 */
public class PayOrderPresenter extends MvpBasePersenter<PayOrderContract.View> implements PayOrderContract.Presenter {

    private static final String TAG = "PayOrderPresenter";

    @Override
    public void getPayOrder(int page, String screen) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("rows", 100);
        params.put("screen", screen);
        NetWork.getInstance().post(getContext(), ChildUrl.GET_PAY_ORDER, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "getPayOrder onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                PayOrderModel payOrderModel = GsonUtil.GsonToBean(bean, PayOrderModel.class);
                if (payOrderModel.getOrderListVOList() != null && payOrderModel.getOrderListVOList().size() > 0) {
                    getView().showPayOrder(payOrderModel.getOrderListVOList());
                } else {
                    if (page == DEFAULT_ONE) {
                        getView().showPayOrder(null);
                    } else {
                        getView().loadFail(null);
                        ToastView.showToast(R.string.order_no_data_toast);
                    }
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                if (getView() == null || !isActive()) {
                    return;
                }
                getView().loadFail(var1);
            }
        });
    }

    @Override
    public void getPayOrder(int page) {
        getPayOrder(page, "");
    }

    @Override
    public void getLikeList(int page) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", page);
        params.put("pageSize", 10);
        NetWork.getInstance().post(getContext(), ChildUrl.GET_LIKE_LIST, params, new RequestSateListener<List<LikeListModelItem>>() {
            @Override
            public void onSuccess(int code, List<LikeListModelItem> bean) {
                Log.d(TAG, "getLikeList onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                if (bean != null && bean.size() > 0) {
                    getView().showLikeLise(bean);
                } else {
                    getView().loadFail(null);
                    ToastView.showToast(R.string.order_no_data_toast);
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                if (getView() == null || !isActive()) {
                    return;
                }
                getView().loadFail(var1);
            }
        });
    }

    @Override
    public void getLuckDrawEnter(String orderId) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance()
                .get(getContext(),
                        ChildUrl.LUCK_DRAW_ENTER.replace("{orderId}", orderId),
                        params,
                        new RequestSateListener<String>() {
                            @Override
                            public void onSuccess(int code, String bean) {
                                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                                    return;
                                }
                                if (bean != null) {
                                    getView().setLuckDrawEnter();
                                }
                            }

                            @Override
                            public void onFailure(Throwable var1) {
                                if (getView() == null || !isActive()) {
                                    return;
                                }
                                getView().initLuckDrawEnter();
                            }
                        });
    }

    @Override
    public void getCancelOrder(String totalOrderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("totalOrderId", totalOrderId);
        NetWork.getInstance()
                .post(getContext(),
                        ChildUrl.CANCEL_ORDER,
                        params,
                        new RequestSateListener<String>() {
                            @Override
                            public void onSuccess(int code, String bean) {
                                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                                    return;
                                }
                                if (bean != null) {
                                    getView().setCancelOrder();
                                }
                            }

                            @Override
                            public void onFailure(Throwable var1) {
                                if (getView() == null || !isActive()) {
                                    return;
                                }
                                getView().initCancelOrder();
                            }
                        });
    }

}

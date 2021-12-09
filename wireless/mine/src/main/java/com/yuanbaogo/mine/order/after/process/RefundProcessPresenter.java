package com.yuanbaogo.mine.order.after.process;

import android.util.Log;

import com.alibaba.android.arouter.utils.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.after.model.AfterSalesModel;
import com.yuanbaogo.mine.order.after.model.RefundModel;
import com.yuanbaogo.mine.order.after.record.PageModel;
import com.yuanbaogo.mine.order.utils.Configuration;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

public class RefundProcessPresenter extends MvpBasePersenter<RefundProcessContract.View> implements RefundProcessContract.Presenter {

    @Override
    public void getRefundList(int page, String search) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageReq", JSONObject.toJSON(new PageModel(LOAD_ROWS, page)));
        params.put("screen", search);
        params.put("saleType", 1);
        params.put("userId", UserStore.getYbCode());
        NetWork.getInstance().post(getContext(), ChildUrl.GET_REFUND_LIST, params, new RequestSateListener<RefundModel>() {
            @Override
            public void onSuccess(int code, RefundModel bean) {
                Log.d(TAG, "getReceiptOrder onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                if (bean == null || bean.getRows() == null || bean.getRows().size() <= 0) {
                    if (!TextUtils.isEmpty(search)) {
                        getView().loadFail(Configuration.DEFAULT_SEARCH);
                    } else {
                        getView().loadFail(null);
                        ToastView.showToast(R.string.order_no_data_toast);
                    }
                } else {
                    List<AfterSalesModel> afterSalesModels = new ArrayList<>();
                    // 筛选当前在处理中的售后订单
                    for (int i = 0; i < bean.getRows().size(); i++) {
                        AfterSalesModel afterSalesModel = bean.getRows().get(i);
                        int salesStatus = afterSalesModel.getSalesStatus();
                        if (salesStatus == Configuration.REFUND_WAIT ||
                                salesStatus == Configuration.REFUND_RETURNING ||
                                salesStatus == Configuration.REFUND_REFUNDING) {
                            afterSalesModels.add(afterSalesModel);
                        }
                    }
                    getView().showRefundList(afterSalesModels);
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                if (getView() == null || !isActive()) {
                    return;
                }
                getView().loadFail(var1.getMessage());
            }
        });
    }

    @Override
    public void getRefundList(int page) {
        getRefundList(page, "");
    }
}

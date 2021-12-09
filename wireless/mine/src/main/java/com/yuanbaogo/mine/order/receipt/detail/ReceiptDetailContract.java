package com.yuanbaogo.mine.order.receipt.detail;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.receipt.detail.model.NewLogisticsModel;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 5:27 PM
 * @Modifier:
 * @Modify:
 */
public interface ReceiptDetailContract {

    interface View extends IBaseView {
        void showOrderDetail(GoodsDetail goodsDetail);
        void showOrderLogistics(NewLogisticsModel logistics);
        void getOrderLogisticsDetails(List<NewLogisticsModel> totalOrderId);
    }

    interface Presenter extends IBasePresenter {
        void getOrderDetail(String totalOrderId);
        void getOrderLogistics(String totalOrderId);
        void getOrderLogisticsDetails(String totalOrderId);
    }

}


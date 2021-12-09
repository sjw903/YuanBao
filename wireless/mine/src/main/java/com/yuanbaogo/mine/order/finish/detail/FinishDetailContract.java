package com.yuanbaogo.mine.order.finish.detail;

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
public interface FinishDetailContract {

    interface View extends IBaseView {
        void showOrderDetail(GoodsDetail goodsDetail);

        /**
         * 物流信息
         *
         * @param bean
         */
        void getOrderLogisticsDetails(List<NewLogisticsModel> bean);
    }

    interface Presenter extends IBasePresenter {
        void getOrderDetail(String totalOrderId);

        /**
         * 获取物流信息
         *
         * @param totalOrderId
         */
        void getOrderLogisticsDetails(String totalOrderId);
    }

}


package com.yuanbaogo.mine.order.after.back;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.order.after.model.AfterSalesDetailsModel;
import com.yuanbaogo.mine.order.after.model.RefundStatusModel;

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
public interface SentBackAfterDetailContract {

    interface View extends IBaseView {
        void showAfterSales(AfterSalesDetailsModel afterSalesDetailsModel);
        void showRefundStatus(List<RefundStatusModel> refundDetailModel);
    }

    interface Presenter extends IBasePresenter {
        void getAfterSalesDetails(String orderId);
        /**
         * 查询退款状态
         *
         * @param salesId 售后id
         */
        void getRefundStatus(String salesId);
    }

}


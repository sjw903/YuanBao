package com.yuanbaogo.mine.order.after.finish;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.mine.order.after.base.BaseAfterSalesDetailContract;
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
public interface AfterSalesFinishDetailContract {

    interface View extends BaseAfterSalesDetailContract.View {
        void showRefundStatus(List<RefundStatusModel> refundDetailModel);
    }

    interface Presenter extends IBasePresenter {
        /**
         * 查询退款状态
         *
         * @param salesId 售后id
         */
        void getRefundStatus(String salesId);
    }

}


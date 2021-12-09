package com.yuanbaogo.mine.order.receipt.refund;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.order.model.GoodsDetail;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 5:27 PM
 * @Modifier:
 * @Modify:
 */
public interface ApplyRefundContract {

    interface View extends IBaseView {
        void applyResult(boolean result);
        void setOrderInformation(GoodsDetail bean);
    }

    interface Presenter extends IBasePresenter {
        /**
         * 申请退款
         *
         * @param orderId 商户订单号,示例值(1422454630874674534)
         * @param reason  退款原因,示例值(7天无理由退换货)
         */
        void applyRefund(String orderId, String reason, String name, String phone);

        /**
         * 获取退单详情
         */
        void getOrderInformation(String totalOrderId);
    }

}


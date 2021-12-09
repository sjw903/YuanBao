package com.yuanbaogo.mine.order.cancel.refund;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.order.after.model.AfterSalesDetailsModel;
import com.yuanbaogo.mine.order.after.model.RefundStatusModel;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.model.RefundDetailModel;

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
public interface RefundDetailContract {

    interface View extends IBaseView {
        void showAfterSales(RefundCancelModel refundCancelModel);
    }

    interface Presenter extends IBasePresenter {



        void getAfterSalesDetails(String orderId);
    }

}


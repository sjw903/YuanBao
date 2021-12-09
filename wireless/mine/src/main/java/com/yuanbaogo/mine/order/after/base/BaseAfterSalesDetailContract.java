package com.yuanbaogo.mine.order.after.base;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.order.after.model.AfterSalesDetailsModel;
import com.yuanbaogo.mine.order.after.model.RefundStatusModel;
import com.yuanbaogo.mine.order.model.GoodsDetail;

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
public interface BaseAfterSalesDetailContract {

    interface View extends IBaseView {
        void showAfterSales(AfterSalesDetailsModel afterSalesDetailsModel);
    }

    interface Presenter extends IBasePresenter {
        void getAfterSalesDetails(String orderId);
    }

}


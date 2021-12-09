package com.yuanbaogo.mine.order.after.apply;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.order.after.model.AfterSalesModel;

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
public interface RefundApplyContract {

    interface View extends IBaseView {
        void showRefundList(List<AfterSalesModel> afterSalesModels);
        void loadFail(String error);
    }

    interface Presenter extends IBasePresenter {
        void getRefundList(int page);

        void getRefundList(int page,String search);
    }

}


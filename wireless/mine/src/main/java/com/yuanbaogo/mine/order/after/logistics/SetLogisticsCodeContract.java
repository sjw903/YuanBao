package com.yuanbaogo.mine.order.after.logistics;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

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
public interface SetLogisticsCodeContract {

    interface View extends IBaseView {
        void showLogisticsList(List<LogisticsCompanyModel> logisticsCompanyModels);

        void showSetLogisticsResult();
    }

    interface Presenter extends IBasePresenter {
        void selectLogisticsCompany();

        void setLogisticsCode(int companyType, String logisticsId, String orderId);
    }

}


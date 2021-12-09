package com.yuanbaogo.mine.groupaccount.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.groupaccount.model.PreRechargeBean;
import com.yuanbaogo.mine.groupaccount.model.RechargeNowBean;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/29/21 9:17 AM
 * @Modifier:
 * @Modify:
 */
public interface RechargeNowContract {

    interface View extends IBaseView {

        void setFindNouseCouPonList(List<RechargeNowBean> bean);

        void initFindNouseCouPonList();

        void setPreRecharge(PreRechargeBean bean);

        void initPreRecharge();

    }

    interface Presenter extends IBasePresenter {

        void getFindNouseCouPonList(String ybCode, int areaType);

        /**
         * 用户拼团预下单
         *
         * @param buyerId    专区ID
         * @param areaId     用户元宝ID
         * @param couponList 使用优惠券ID列表
         * @param useCash    使用现金金额（单位：分）
         * @param useTicket  使用券金额（单位：分）
         */
        void getPreRecharge(String buyerId, int areaId, String[] couponList, long useCash, long useTicket);

    }

}

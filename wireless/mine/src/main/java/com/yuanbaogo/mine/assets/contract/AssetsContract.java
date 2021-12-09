package com.yuanbaogo.mine.assets.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.assets.model.FindAreaAmountBean;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 11:08 AM
 * @Modifier:
 * @Modify:
 */
public interface AssetsContract {

    interface View extends IBaseView {

        /**
         * 设置元宝积分数据
         */
        void setIntegrals(String bean);

        /**
         * 设置零钱数据
         */
        void setDIB(String bean);

        /**
         * 设置冻结零钱数据
         */
        void setFrozen(String bean);

        /**
         * 设置查询用户各个专区钱包总额数据
         *
         * @param bean
         */
        void setFindAreaAmount(List<FindAreaAmountBean> bean);

        /**
         * 接口请求失败
         *
         * @param walletType
         */
        void onError(int walletType);

        /**
         * 去提现
         */
        void toWithdrawal();

        /**
         * 去绑定银行卡
         */
        void toBindBankCard();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 查询不同类型钱包数量
         *
         * @param walletType 0 元宝积分  1 零钱  2 冻结零钱
         * @param ybCode
         */
        void getDiffTypeWallet(int walletType, String ybCode);

        /**
         * 查询用户各个专区钱包总额
         *
         * @param ybCode
         */
        void getFindAreaAmount(String ybCode);

        /**
         * 是否可以提现
         */
        void canWithdrawal();

    }

}

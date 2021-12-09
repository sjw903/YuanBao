package com.yuanbaogo.mine.dib.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.dib.model.BindBankCardInfoBean;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 7:06 PM
 * @Modifier:
 * @Modify:
 */
public interface WithdrawContract {

    interface View extends IBaseView {

        /**
         * 设置用户状态
         */
        void setCheckUserStatus(int code, String bean);

        /**
         * 设置密码提示
         */
        void showNumberPasswordDialog();

        /**
         * 密码验证通过
         */
        void setVerifyUserPayPassword(int code, String str);

        /**
         * 密码验证失败
         */
        void setVerifyUserPayPasswordErr(String code, String str);

        /**
         * 设置提现结果
         */
        void setWithdrawal();

        /**
         * 账户绑定银行卡信息
         */
        void setBindBankCardInfo(BindBankCardInfoBean bean);

    }

    interface Presenter extends IBasePresenter {

        /**
         * 验证用户是否设置支付密码
         */
        void getUserPassword();

        /**
         * 查看用户状态(冻结是true正常状态是false)
         */
        void checkUserStatus();

        /**
         * 验证用户账户支付密码
         *
         * @param payPassword
         */
        void verifyUserPayPassword(String payPassword);

        /**
         * 请求提现
         *
         * @param money 提现金额,示例值(123)
         * @param type  提现方式:0银行卡,1微信,示例值(1)
         */
        void getWithdrawal(String money, int type);

        /**
         * 账户绑定银行卡信息
         */
        void getBindBankCardInfo();
    }

}

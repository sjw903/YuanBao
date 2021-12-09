package com.yuanbaogo.mine.setting.pay.set;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 5:27 PM
 * @Modifier:
 * @Modify:
 */
public interface PaySetContract {

    interface View extends IBaseView {
        void startTimer();

        void checkCaptcha(boolean isSuccess);
    }

    interface Presenter extends IBasePresenter {
        void setJiYan();

        void getVerificationCode();

        void sendCode();

        /**
         * 校验手机验证码
         *
         * @param code /
         */
        void checkCaptcha(String code);
    }

}


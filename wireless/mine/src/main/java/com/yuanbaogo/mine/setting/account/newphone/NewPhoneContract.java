package com.yuanbaogo.mine.setting.account.newphone;

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
public interface NewPhoneContract {

    interface View extends IBaseView {
        void startTimer();

        void checkCaptcha(boolean isSuccess, String code);

        void bindSuccess(String newPhone);
    }

    interface Presenter extends IBasePresenter {
        void sendCode(String newPhone);

        void bindNewPhone(String newPhone, String code);

        /**
         * 校验手机验证码
         *
         * @param phone 手机号
         * @param code  /
         */
        void checkCaptcha(String phone, String code);
    }

}


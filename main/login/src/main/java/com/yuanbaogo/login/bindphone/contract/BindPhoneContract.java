package com.yuanbaogo.login.bindphone.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/16/21 1:20 PM
 * @Modifier:
 * @Modify:
 */
public interface BindPhoneContract {

    interface View extends IBaseView {
        void bindPhoneCodeColor(boolean isCan);

        void bindPhoneColor(boolean isCan);
    }

    interface Presenter extends IBasePresenter {

        void verification(int mCanGetCode);

        int changeCanBind(String bindPhoneNum, String bindVerificationCode);

        int changeCanGetCode(String loginPhoneNum);

        void bindPhone(int canBind, String bindPhoneNum, String bindVerificationCode);
    }

}

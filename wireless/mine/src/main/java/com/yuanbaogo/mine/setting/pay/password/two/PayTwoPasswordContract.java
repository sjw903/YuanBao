package com.yuanbaogo.mine.setting.pay.password.two;

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
public interface PayTwoPasswordContract {

    interface View extends IBaseView {
        void setSuccess();

        void setFail(String error);
    }

    interface Presenter extends IBasePresenter {
        void setPayPassword(String password);
    }

}


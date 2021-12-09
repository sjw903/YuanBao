package com.yuanbaogo.mine.setting.account;

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
public interface AccountContract {

    interface View extends IBaseView {
        void signSuccess();
        void signFail(Throwable throwable);
    }

    interface Presenter extends IBasePresenter {
        void signOut();
    }

}


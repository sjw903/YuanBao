package com.yuanbaogo.mine.setting.pay;

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
public interface PayContract {

    interface View extends IBaseView {
        void showAddPasswordItem(boolean show);
    }

    interface Presenter extends IBasePresenter {
        void getUserPassword();
    }

}


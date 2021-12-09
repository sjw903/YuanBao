package com.yuanbaogo.mine.setting.address.add;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.setting.model.AddressBean;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 5:27 PM
 * @Modifier:
 * @Modify:
 */
public interface AddAddressContract {

    interface View extends IBaseView {
        void addFinish();

        void addFail(String error);

        /**
         * 是否有默认地址
         *
         * @param hasDefault /
         */
        void hasDefaultAddress(boolean hasDefault);
    }

    interface Presenter extends IBasePresenter {
        void addAddress(AddressBean addressBean);

        void getDefaultAddress();
    }

}


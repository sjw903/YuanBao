package com.yuanbaogo.mine.setting.address;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.setting.model.AddressBean;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 5:27 PM
 * @Modifier:
 * @Modify:
 */
public interface AddressContract {

    interface View extends IBaseView {
        void loadAddressList(List<AddressBean> addressBeanList);

        void loadFailure();

        void deleteAddress(long addressId);
    }

    interface Presenter extends IBasePresenter {
        /**
         * 获取收获地址列表
         *
         * @param page 页码
         */
        void getAddressList(int page);

        /**
         * 删除地址
         *
         * @param addressId addressId
         */
        void deleteAddress(long addressId);
    }

}


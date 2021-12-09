package com.yuanbaogo.mine.groupaccount.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/13/21 10:24 AM
 * @Modifier:
 * @Modify:
 */
public interface WebContract {

    interface View extends IBaseView {
        void setWXcode(String bean);

        void initWXcode();
    }

    interface Presenter extends IBasePresenter {
        /**
         * 获取小程序邀请二维码
         *
         * @param page  扫码进入的小程序页面路径
         * @param scene 链接参数，32个可见字符,示例值(a=1)
         */
        void getWXcode(String page, String scene);
    }

}

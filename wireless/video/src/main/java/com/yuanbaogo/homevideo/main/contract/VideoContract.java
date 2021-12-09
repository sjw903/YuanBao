package com.yuanbaogo.homevideo.main.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

/**
 * @author lhx
 * @description:
 * @date : 2021/7/21 18:11
 */
public interface VideoContract {

    interface View extends IBaseView {

    }

    interface Presenter extends IBasePresenter {

        void isOpenLivingRoom();

    }

}

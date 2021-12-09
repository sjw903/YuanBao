package com.yuanbaogo.mine.mine.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.mine.model.PersonalInfoBean;
import com.yuanbaogo.mine.mine.model.UserInfoModel;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 4:31 PM
 * @Modifier:
 * @Modify:
 */
public interface MineContract {

    interface View extends IBaseView {
        void getUserInfoSuccess(UserInfoModel userInfoModel);

        /**
         * 设置个人信息
         *
         * @param bean
         */
        void setPersonalInfo(PersonalInfoBean bean);

        /**
         * 显示个人信息
         */
        void initPersonalInfo();
    }

    interface Presenter extends IBasePresenter {
        void getUserInfo();

        /**
         * 获取个人信息
         *
         * @param ybCode
         */
        void getPersonalInfo(String ybCode);
    }

}

package com.yuanbaogo.mine.personal;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.personal.model.PersonalSubmitBean;

import java.io.File;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 5:27 PM
 * @Modifier:
 * @Modify:
 */
public interface EditMineContract {

    interface View extends IBaseView {

        /**
         * 设置修改个人信息
         */
        void setPersonalSubmit(String bean);

        /**
         * 显示修改个人信息
         */
        void initPersonalSubmit();

        /**
         * 设置修改头像
         *
         * @param url
         */
        void setUpdatePortrait(String url);

    }

    interface Presenter extends IBasePresenter {

        /**
         * 修改个人信息
         */
        void getPersonalSubmit(PersonalSubmitBean bean);

        /**
         * 修改头像
         *
         * @param path
         */
        void getUpdatePortrait(String path, File file, String mimeType);

    }

}


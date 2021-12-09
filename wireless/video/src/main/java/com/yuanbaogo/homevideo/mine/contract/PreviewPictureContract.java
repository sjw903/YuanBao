package com.yuanbaogo.homevideo.mine.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

import java.io.File;

public interface PreviewPictureContract {

    interface View extends IBaseView {

        /**
         * 设置修改用户背景
         *
         * @param url
         */
        void setUpdatePortrait(String url);

    }

    interface Presenter extends IBasePresenter {
        /**
         * 修改用户背景
         *
         * @param path
         */
        void getUpdatePortrait(String path, File file, String mimeType);
    }

}

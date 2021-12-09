package com.yuanbaogo.mine.setting.about;

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
public interface AboutContract {

    interface View extends IBaseView {
        void showNewVersion(ApkInfoModel apkInfoModel);
    }

    interface Presenter extends IBasePresenter {
        void getNewVersion();
    }

}


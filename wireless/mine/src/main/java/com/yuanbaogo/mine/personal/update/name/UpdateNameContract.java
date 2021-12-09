package com.yuanbaogo.mine.personal.update.name;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;


public interface UpdateNameContract {

    interface View extends IBaseView {
        void showUpdateResult();
    }

    interface Presenter extends IBasePresenter {
        void updateName(String name);
    }

}


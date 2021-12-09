package com.yuanbaogo.mine.personal.update.signature;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;


public interface UpdateSignatureContract {

    interface View extends IBaseView {

        void showUpdateResult();

    }

    interface Presenter extends IBasePresenter {

        void updateSignature(String signature);

    }

}


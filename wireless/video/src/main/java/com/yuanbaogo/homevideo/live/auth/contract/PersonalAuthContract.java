package com.yuanbaogo.homevideo.live.auth.contract;

import android.text.TextWatcher;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

import java.io.File;

/**
 * @author lhx
 * @description:
 * @date : 2021/7/21 18:36
 */
public interface PersonalAuthContract {

    interface View extends IBaseView {

        //        void setListData(List<PersonalAuthBean> list);
        String getTextEtNameView();
        String getTextEtPhoneView();
        String getTextEtCardView();
        void setBtncomitStatus(String txt, boolean isEnable);
        void showStatus(boolean isStatus);
        void setCardBackPicture(File file);
        void setCardFrontPicture(File file);


    }

    interface Presenter extends IBasePresenter {

        TextWatcher getTextWatcher();
        void compressAndGenImage(File photoFile);
        void comit();
        void uploadPic(String path, File file, String mimeType);
        void setCurrentPhoto(String type);
    }

}

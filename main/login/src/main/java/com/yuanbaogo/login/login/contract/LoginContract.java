package com.yuanbaogo.login.login.contract;


import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

public interface LoginContract {

    interface View extends IBaseView {

        void changeCodeColor(boolean isCan);

        void changeLoginColor(boolean isCan);

        String getLoginPhoneNum();

        String getLoginEditVerificationCode();
        String getLoginEditInviteCode();

        boolean getPrivacyChecked();

        void changeVerificationTimer(int time);

        void changeVerificationButton();

        void clearPhoneNum();

        void changeInviteView(boolean isCan);

        void close();

    }

    interface Presenter extends IBasePresenter {

        void login(String loginPhoneNum, String loginEditVerificationCode, String loginEditInviteCode);

        void changeCanLogin(String loginPhoneNum, String loginEditVerificationCode, boolean privacyChecked);

        void changeCanGetCode(String loginPhoneNum);

        void verification();

        void setJiYan();
    }

}

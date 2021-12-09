package com.yuanbaogo.homevideo.live.pull.contract;


import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.homevideo.live.pull.model.EnterLiveBean;
import com.yuanbaogo.homevideo.live.push.model.CoverImgBean;
import com.yuanbaogo.homevideo.shortvideo.report.model.ReportRequestModel;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

import java.util.List;

public interface PullMainContract {

    interface View extends IBaseView {

        void setLive(EnterLiveBean bean);
        void close();
        void setFollowState(boolean status);

        void onReportSubmitSuccess();
        void onReportSubmitFail();
        void onUploadPicFail();
        void onUploadPicSuccess(CoverImgBean coverImgBean);
    }

    interface Presenter extends IBasePresenter {

        void getInto(String avChatRoomId);
        void getOut(String avChatRoomId);

        void addCharm(String avChatRoomId, int charmValue);
        void clickCharm();

        void follow(String businessId, String followerYbCode,int state);
        void followState();

        void toCartGoods();

        void getSku(String spuId, String mLot, long price, String url);

        void reportSubmit(ReportRequestModel requestModel);

        void uploadPic(List<LocalMedia> localMedia);
    }

}

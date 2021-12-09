package com.yuanbaogo.video.videopublish.contract;

import com.yuanbaogo.video.videopublish.model.SignatureBean;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

/**
 * @author lhx
 * @description:
 * @date : 2021/7/21 18:43
 */
public interface VideoPublishContract {

    interface View extends IBaseView {
        void getSignatureSuccess(SignatureBean signature);

        void uploadVideoSuccess(String msg);
    }

    interface Presenter extends IBasePresenter {
        void getSignature();

        void uploadVideo(String address, String coverUrl, String lat, String lng, String title, String videoId, String videoUrl, String goodId);
    }

}

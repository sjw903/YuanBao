package com.yuanbaogo.homevideo.main.contract;

import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.commonlib.bean.RecommendVideoBean;
import com.yuanbaogo.homevideo.live.push.model.CoverImgBean;
import com.yuanbaogo.homevideo.main.bean.VideoLiveFollowListBean;
import com.yuanbaogo.homevideo.shortvideo.report.model.ReportRequestModel;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

import java.util.List;

/**
 * @author lhx
 * @description:
 * @date : 2021/7/21 18:30
 */
public interface FollowVideoContract {

    interface View extends IBaseView {
        void onVideoListSuccess(RecommendVideoBean bean);
        void onLiveListSuccess(VideoLiveFollowListBean bean);
        void onVideoListFail();
        void onClickLikeSuccess(String likeState, int position);
        void onDeleteVideoSuccess();
        void onUnconcernVideoSuccess();
        void onReportSubmitSuccess();
        void onReportSubmitFail();
        void onUploadPicFail();

        void onUploadPicSuccess(CoverImgBean coverImgBean);
    }

    interface Presenter extends IBasePresenter {
        void getRecommendVideoList(int page, String size);
        void clickLike(String likeState, String videoId, int position);
        void deleteVideo(String vodId);
        void unconcernVideo(String vodId);
        void uploadPic(List<LocalMedia> localMedia);
        void reportSubmit(ReportRequestModel requestModel);
        void getLiveVideoList();
        void getSku(String spuId, String mLot, long price, String url, String videoId);
    }

}

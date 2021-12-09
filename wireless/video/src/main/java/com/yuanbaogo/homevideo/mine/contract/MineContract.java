package com.yuanbaogo.homevideo.mine.contract;

import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.homevideo.live.pull.model.FollowBean;
import com.yuanbaogo.homevideo.live.push.model.CoverImgBean;
import com.yuanbaogo.homevideo.mine.model.PersonalInfoBean;
import com.yuanbaogo.homevideo.shortvideo.report.model.ReportRequestModel;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

import java.util.List;

/**
 * @author lxx
 * @description:
 * @date : 2021/8/24 18:30
 */
public interface MineContract {

    interface View extends IBaseView {

        /**
         * 设置个人信息
         *
         * @param bean
         */
        void setPersonalInfo(PersonalInfoBean bean);

        /**
         * 显示个人信息
         */
        void initPersonalInfo();

        /**
         * 设置关注，取消关注
         *
         * @param bean
         */
        void setFollow(FollowBean bean);

        /**
         * 显示关注，取消关注
         */
        void initFollow();

        void onReportSubmitSuccess();
        void onReportSubmitFail();
        void onUploadPicFail();
        void onUploadPicSuccess(CoverImgBean coverImgBean);

    }

    interface Presenter extends IBasePresenter {

        /**
         * 获取个人信息
         *
         * @param ybCode
         */
        void getPersonalInfo(String ybCode);

        /**
         * 关注，取消关注
         *
         * @param followerYbCode
         * @param state          关注状态：1.关注，2.取消关注
         */
        void getFollow(String followerYbCode, int state);

        void uploadPic(List<LocalMedia> localMedia);

        void reportSubmit(ReportRequestModel requestModel);

    }

}

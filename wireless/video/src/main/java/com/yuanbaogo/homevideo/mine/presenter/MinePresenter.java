package com.yuanbaogo.homevideo.mine.presenter;

import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.remote.model.UpLoadFileBean;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.homevideo.live.pull.model.FollowBean;
import com.yuanbaogo.homevideo.live.push.model.CoverImgBean;
import com.yuanbaogo.homevideo.mine.contract.MineContract;
import com.yuanbaogo.homevideo.mine.model.PersonalInfoBean;
import com.yuanbaogo.homevideo.shortvideo.report.model.ReportRequestModel;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lxx
 * @description:
 * @date : 2021/8/24
 */
public class MinePresenter extends MvpBasePersenter<MineContract.View>
        implements MineContract.Presenter {

    /**
     * 获取个人信息
     *
     * @param ybCode
     */
    @Override
    public void getPersonalInfo(String ybCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("ybCode", ybCode);
        NetWork.getInstance().get(getContext(),
                ChildUrl.PERSONAL_INFO,
                params,
                new RequestSateListener<PersonalInfoBean>() {
                    @Override
                    public void onSuccess(int code, PersonalInfoBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setPersonalInfo(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initPersonalInfo();
                        }
                    }
                });
    }

    @Override
    public void getFollow(String followerYbCode, int state) {
        Map<String, Object> params = new HashMap<>();
        params.put("followerYbCode", followerYbCode);
        params.put("state", state);
        NetWork.getInstance().post(getContext(),
                ChildUrl.follow,
                params,
                new RequestSateListener<FollowBean>() {
                    @Override
                    public void onSuccess(int code, FollowBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setFollow(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initFollow();
                        }
                    }
                });
    }

    @Override
    public void uploadPic(List<LocalMedia> localMedias) {
        if(localMedias==null){
            return;
        }
        List<UpLoadFileBean> fileList = new ArrayList();
        for (int i=0;i<localMedias.size();i++){
            LocalMedia localMedia = localMedias.get(i);
            UpLoadFileBean bean = new UpLoadFileBean();
            String path =localMedia.isCompressed()? localMedia.getCompressPath():localMedia.getPath();
            File file = new File(path);
            bean.setFileName(file.getName());
            bean.setMediaType(localMedia.getMimeType());
            bean.setPath(path);
            bean.setFileKey("file");
            fileList.add(bean);
        }

        Map<String, String> params = new HashMap<>();
        params.put("type", "3");
        NetWork.getInstance().upLoadFile(getContext(), ChildUrl.upload, params, fileList, new RequestSateListener<CoverImgBean>() {
            @Override
            public void onSuccess(int code, CoverImgBean bean) {
                if(isActive())
                    getView().onUploadPicSuccess(bean);
            }

            @Override
            public void onFailure(Throwable e) {
                getView().onUploadPicFail();
            }
        });
    }

    @Override
    public void reportSubmit(ReportRequestModel requestModel) {
        Map<String, Object> params = new HashMap<>();
        params.put("authorId", requestModel.getAuthorId());
//        params.put("authorNickName", requestModel.getAuthorNickName());
        params.put("bizId", requestModel.getAuthorId());//举报业务ID不能为空,举报个人填写被举报人的ybCode
        params.put("bizTitle", requestModel.getBizTitle());
        params.put("content", requestModel.getContent());
        params.put("screenshots", requestModel.getScreenshots());
//        params.put("description", requestModel.getDescription());
//        params.put("lookerId", requestModel.getLookerId());
        params.put("tagName", requestModel.getTagName());
        NetWork.getInstance().post(getContext(), ChildUrl.reportVodSubmit.replace("{type}", "user")
                , params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int var1, String var2) {
                if(isActive())
                    getView().onReportSubmitSuccess();
            }

            @Override
            public void onFailure(Throwable var1) {
                if(isActive())
                    getView().onReportSubmitFail();
            }
        });
    }

}

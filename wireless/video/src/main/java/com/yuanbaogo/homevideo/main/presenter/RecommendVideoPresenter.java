package com.yuanbaogo.homevideo.main.presenter;


import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.commonlib.bean.RecommendVideoBean;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.remote.model.UpLoadFileBean;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.homevideo.live.pull.model.FollowBean;
import com.yuanbaogo.homevideo.live.push.model.CoverImgBean;
import com.yuanbaogo.homevideo.main.contract.RecommendVideoContract;
import com.yuanbaogo.homevideo.shortvideo.report.model.ReportRequestModel;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.zui.dialog.SortDialogView;
import com.yuanbaogo.zui.dialog.model.SkuBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lhx
 * @description:
 * @date : 2021/7/21 18:26
 */
public class RecommendVideoPresenter extends MvpBasePersenter<RecommendVideoContract.View>
        implements RecommendVideoContract.Presenter {


    @Override
    protected void onIntent(Intent intent) {
        super.onIntent(intent);
    }

    @Override
    public void getSku(String spuId, String mLot, long price, String url, String videoId) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.SKU.replace("{spuId}", spuId),
                params,
                new RequestSateListener<SkuBean>() {
                    @Override
                    public void onSuccess(int code, SkuBean skuBean) {
                        if (code == 200 && skuBean != null) {
                            skuBean.setPrice(price)//价格
                                    .setImgUrl(url)//图片
                                    .setStock(0);//库存
                            //直播    skubean  商品批次   直播ID
                            SortDialogView sortDialogView = new SortDialogView(TagParameteBean.videoBringGoods, skuBean, mLot, videoId);
                            sortDialogView.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "shop_info");
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                    }
                });
    }


    @Override
    public void getRecommendVideoList(String page, String size) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page);
        params.put("size", size);
        NetWork.getInstance().get(getContext(), ChildUrl.getRecommendVideo, params, new RequestSateListener<RecommendVideoBean>() {

            @Override
            public void onSuccess(int var1, RecommendVideoBean var2) {
                if (isActive())
                    getView().onVideoListSuccess(var2);
            }

            @Override
            public void onFailure(Throwable var1) {
                if (isActive())
                    getView().onVideoListFail();
            }
        }, false);
    }

    @Override
    public void clickLike(String likeState, String videoId,int position) {
        Map<String, String> params = new HashMap<>();
        params.put("likeState", likeState);
        params.put("likeId", videoId);
        NetWork.getInstance().post(getContext(), ChildUrl.clickLike, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int var1, String var2) {
                if (isActive())
                    getView().onClickLikeSuccess(likeState,position);
            }

            @Override
            public void onFailure(Throwable var1) {

            }
        },false);
    }

    @Override
    public void deleteVideo(String vodId) {
        NetWork.getInstance().post(getContext(), ChildUrl.deleteVideo + vodId, null, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int var1, String var2) {
                if (isActive())
                getView().onDeleteVideoSuccess();
            }

            @Override
            public void onFailure(Throwable var1) {

            }
        },false);
    }

    @Override
    public void unconcernVideo(String vodId) {
        NetWork.getInstance().post(getContext(), ChildUrl.unconcernVideo + vodId, null, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int var1, String var2) {
                if(isActive())
                getView().onUnconcernVideoSuccess();
            }

            @Override
            public void onFailure(Throwable var1) {
            }
        },false);
    }

    @Override
    public void reportSubmit(ReportRequestModel requestModel) {
        Map<String, Object> params = new HashMap<>();
        params.put("authorId", requestModel.getAuthorId());
//        params.put("authorNickName", requestModel.getAuthorNickName());
        params.put("bizId", requestModel.getBizId());
        params.put("bizTitle", requestModel.getBizTitle());
        params.put("content", requestModel.getContent());
        params.put("screenshots", requestModel.getScreenshots());
//        params.put("description", requestModel.getDescription());
//        params.put("lookerId", requestModel.getLookerId());
        params.put("tagName", requestModel.getTagName());
        NetWork.getInstance().post(getContext(), ChildUrl.reportVodSubmit.replace("{type}", "vod")
                , params, new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int var1, String var2) {
                        if (isActive())
                            getView().onReportSubmitSuccess();
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (isActive())
                            getView().onReportSubmitFail();
                    }
                },false);
    }

    @Override
    public void uploadPic(List<LocalMedia> localMedias) {
        if (localMedias == null) {
            return;
        }
        List<UpLoadFileBean> fileList = new ArrayList();
        for (int i = 0; i < localMedias.size(); i++) {
            LocalMedia localMedia = localMedias.get(i);
            UpLoadFileBean bean = new UpLoadFileBean();
            String path = localMedia.isCompressed() ? localMedia.getCompressPath() : localMedia.getPath();
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

                if (isActive())
                    getView().onUploadPicSuccess(bean);
            }

            @Override
            public void onFailure(Throwable e) {
                getView().onUploadPicFail();
            }
        });

    }

    @Override
    public void toFollowFans(String followerYbCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("followerYbCode", followerYbCode);
        params.put("state", 1);//1.关注，2.取消关注
        params.put("type","");
        NetWork.getInstance().post(getContext(), ChildUrl.follow, params,
                new RequestSateListener<FollowBean>() {
                    @Override
                    public void onSuccess(int code, FollowBean bean) {
                        if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                            return;
                        }
//                        if (!bean.getYbCode().isEmpty()) {
//                            getView().followFans();
//                        } else {
//                            getView().followFansFail();
//                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {

                    }
                },false);
    }
}

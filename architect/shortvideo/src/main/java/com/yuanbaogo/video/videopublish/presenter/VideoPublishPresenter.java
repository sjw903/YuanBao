package com.yuanbaogo.video.videopublish.presenter;

import com.yuanbaogo.video.videopublish.model.SignatureBean;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.video.videopublish.contract.VideoPublishContract;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lhx
 * @description:
 * @date : 2021/7/21 18:44
 */
public class VideoPublishPresenter extends MvpBasePersenter<VideoPublishContract.View>
    implements VideoPublishContract.Presenter{

    @Override
    public void getSignature() {
        NetWork.getInstance().get(getContext(), ChildUrl.getSignature, null, new RequestSateListener<SignatureBean>() {
            @Override
            public void onSuccess(int var1, SignatureBean var2) {
                getView().getSignatureSuccess(var2);
            }

            @Override
            public void onFailure(Throwable var1) {

            }
        },false);
    }

    @Override
    public void uploadVideo(String address, String coverUrl, String lat, String lng, String title, String videoId, String videoUrl, String goodId) {
        Map<String, String> params = new HashMap<>();
        params.put("address", address);
        params.put("coverUrl", coverUrl);
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("title", title);
        params.put("videoId", videoId);
        params.put("videoUrl", videoUrl);
        params.put("goodId", goodId);
        NetWork.getInstance().post(getContext(), ChildUrl.uploadVideo, params, new RequestSateListener<String>(){

            @Override
            public void onSuccess(int var1, String var2) {
                getView().uploadVideoSuccess(var2);
            }

            @Override
            public void onFailure(Throwable var1) {

            }
        },false);
    }

}

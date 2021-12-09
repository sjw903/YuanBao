package com.yuanbaogo.mine.order.finish.evaluation;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.datacenter.remote.model.UpLoadFileBean;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.order.finish.evaluation.model.EvaluationParam;
import com.yuanbaogo.mine.order.finish.refund.model.UploadListBean;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.model.GoodsVOListItem;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluationPresenter extends MvpBasePersenter<EvaluationContract.View> implements EvaluationContract.Presenter {

    @Override
    public void onEvaluation(List<GoodsVOListItem> goodsVOListItemList,
                             float serviceRating,
                             float logisticsRating,
                             String totalOrderId, List<String> urlList) {
        List<EvaluationParam> evaluationParamList = new ArrayList<>();
        for (int i = 0; i < goodsVOListItemList.size(); i++) {
            GoodsVOListItem goodsVOListItem = goodsVOListItemList.get(i);
            evaluationParamList.add(new EvaluationParam((int) serviceRating, goodsVOListItem.getDescEvaluation(),
                    (int) goodsVOListItem.getDesc(), (int) logisticsRating, totalOrderId, urlList,
                    goodsVOListItem.getSpuId(), goodsVOListItem.getSkuId()));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("orderCommentList", JSONObject.toJSON(evaluationParamList));
        NetWork.getInstance().post(getContext(), ChildUrl.EVALUATION_GOODS, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "onEvaluation onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                getView().showEvaluationResult(bean != null);
            }

            @Override
            public void onFailure(Throwable var1) {
                getView().showEvaluationResult(false);
                Log.e(TAG, "onEvaluation onFailure: " + var1.getMessage());
            }
        });
    }


    @Override
    public void getOrderDetail(String totalOrderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("totalOrderId", totalOrderId);
        NetWork.getInstance().post(getContext(), ChildUrl.GET_ORDER_DETAIL, params, new RequestSateListener<GoodsDetail>() {
            @Override
            public void onSuccess(int code, GoodsDetail bean) {
                Log.d(TAG, "getOrderDetail onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                getView().showOrderDetail(bean);
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getOrderDetail onFailure: " + var1.getMessage());
            }
        });
    }

    public static final String FILE_KEY = "file";
    public static final String MEDIA_TYPE = "image/jpg";
    public static final String MEDIA_NAME = "image.jpg";

    /**
     * 上传图片
     *
     * @param localMediaList
     */
    @Override
    public void uploadImageList(List<LocalMedia> localMediaList) {
        List<UpLoadFileBean> fileList = new ArrayList<>();
        for (int i = 0; i < localMediaList.size(); i++) {
            LocalMedia localMedia = localMediaList.get(i);
            fileList.add(new UpLoadFileBean(FILE_KEY, MEDIA_NAME, MEDIA_TYPE, localMedia.getCompressPath()));
        }
        Map<String, Object> params = new HashMap<>();
        params.put("type", "10");
        NetWork.getInstance().upLoadFile(
                getContext(),
                ChildUrl.UPLOAD_LIST,
                params,
                fileList,
                new RequestSateListener<List<UploadListBean>>() {
                    @Override
                    public void onSuccess(int code, List<UploadListBean> bean) {
                        if (getView() == null || !isActive()) {
                            return;
                        }
                        getView().showImageList(bean);
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        OrderNetworkUtils.disposeError(var1);
                    }
                });
    }

}

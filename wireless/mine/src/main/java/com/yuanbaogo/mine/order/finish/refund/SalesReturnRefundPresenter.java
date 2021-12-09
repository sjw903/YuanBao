package com.yuanbaogo.mine.order.finish.refund;

import android.util.Log;

import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.remote.model.UpLoadFileBean;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.mine.order.finish.refund.model.UploadListBean;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesReturnRefundPresenter extends MvpBasePersenter<SalesReturnRefundContract.View> implements SalesReturnRefundContract.Presenter {

    public static final String FILE_KEY = "file";
    public static final String MEDIA_TYPE = "image/jpg";
    public static final String MEDIA_NAME = "image.jpg";

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
            }
        });
    }

    @Override
    public void returnRefund(String afterSalePrice, String des, int goodsStatus, String orderId, int reasonType, int salesType,
                             List<String> urlList) {
        Map<String, Object> params = new HashMap<>();
        params.put("afterSalePrice", afterSalePrice);
        params.put("orderId", orderId);
        params.put("goodsStatus", goodsStatus);
        params.put("reasonType", reasonType);
        params.put("des", des);
        params.put("salesType", salesType);
        params.put("urlList", urlList);
        params.put("userId", UserStore.getYbCode());
        params.put("backType", 1);
        NetWork.getInstance().post(getContext(), ChildUrl.APPLY_REFUND, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "applyRefund onSuccess: code:" + code + "   bean:" + bean);
                if (getView() == null || !isActive()) {
                    return;
                }
                getView().returnRefundResult(code == NetConfig.NETWORK_SUCCESSFUL);
            }

            @Override
            public void onFailure(Throwable var1) {
                if (getView() == null || !isActive()) {
                    return;
                }
                ToastUtil.showToast(var1.getMessage());
            }
        });
    }

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

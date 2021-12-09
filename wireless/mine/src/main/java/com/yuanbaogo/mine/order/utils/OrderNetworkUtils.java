package com.yuanbaogo.mine.order.utils;

import android.content.Context;
import android.util.Log;

import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.baseutil.GsonUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.model.GoodsVOListItem;
import com.yuanbaogo.network.parser.ErrorResponse;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.Map;

public class OrderNetworkUtils {

    private static final String TAG = "CancelRefundUtils";

    /**
     * 取消售后接口
     *
     * @param context  /
     * @param orderId  id
     * @param listener 回调
     */
    public static void cancelRefund(Context context, String orderId, OnOrderResultListener listener) {
        NetWork.getInstance().post(context, ChildUrl.CANCEL_REFUND + "/" + orderId, null, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "cancelRefund onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || listener == null) {
                    return;
                }
                listener.onOrderResult(bean != null);
            }

            @Override
            public void onFailure(Throwable var1) {
                listener.onOrderResult(false);
                disposeError(var1);
            }
        });
    }

    /**
     * 获取订单状态
     *
     * @param context  /
     * @param orderId  id
     * @param listener 回调
     */
    public static void getOrderStatus(Context context, String orderId, OnOrderStatusListener listener) {
        Map<String, Object> params = new HashMap<>();
        params.put("totalOrderId", orderId);
        NetWork.getInstance().post(context, ChildUrl.GET_ORDER_DETAIL, params, new RequestSateListener<GoodsDetail>() {
            @Override
            public void onSuccess(int code, GoodsDetail bean) {
                Log.d(TAG, "getOrderDetail onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || listener == null) {
                    return;
                }
                listener.onOrderStatus(bean);
            }

            @Override
            public void onFailure(Throwable var1) {
                disposeError(var1);
            }
        });
    }

    /**
     * 确认收货接口
     *
     * @param context  /
     * @param orderId  id
     * @param listener 回调
     */
    public static void confirmGoods(Context context, String orderId, OnOrderResultListener listener) {
        Map<String, Object> params = new HashMap<>();
        params.put("totalOrderId", orderId);
        NetWork.getInstance().post(context, ChildUrl.CONFIRM_GOODS, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "confirmGoods onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || listener == null) {
                    return;
                }
                listener.onOrderResult(bean != null);
            }

            @Override
            public void onFailure(Throwable var1) {
                listener.onOrderResult(false);
                disposeError(var1);
            }
        });
    }

    /**
     * 取消订单接口
     *
     * @param context  /
     * @param orderId  id
     * @param listener 回调
     */
    public static void cancelOrder(Context context, String orderId, OnOrderResultListener listener) {
        Map<String, Object> params = new HashMap<>();
        params.put("totalOrderId", orderId);
        NetWork.getInstance().post(context, ChildUrl.CANCEL_ORDER, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "cancelOrder onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || listener == null) {
                    return;
                }
                listener.onOrderResult(bean != null);
            }

            @Override
            public void onFailure(Throwable var1) {
                listener.onOrderResult(false);
                disposeError(var1);
            }
        });
    }

    /**
     * 删除订单接口
     *
     * @param context  /
     * @param orderId  id
     * @param listener 回调
     */
    public static void deleteOrder(Context context, String orderId, OnOrderResultListener listener) {
        Map<String, Object> params = new HashMap<>();
        params.put("cancelReason", "");
        params.put("totalOrderId", orderId);
        NetWork.getInstance().post(context, ChildUrl.DELETE_ORDER, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "cancelOrder onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || listener == null) {
                    return;
                }
                listener.onOrderResult(bean != null);
            }

            @Override
            public void onFailure(Throwable var1) {
                listener.onOrderResult(false);
                disposeError(var1);
            }
        });
    }

    /**
     * 加入购物车接口
     * <p>
     * "areaId": 0,
     * "cartType": 0,
     * "createBy": 0,
     * "createGoodsName": "",
     * "createGoodsPrice": 0,
     * "goodsCount": 0,
     * "goodsImgUrl": "",
     * "goodsType": 0,
     * "skuId": 0,
     * "skuName": "",
     * "spuId": 0
     *
     * @param context         /
     * @param goodsVOListItem 商品信息
     * @param listener        回调
     */
    public static void goodsAddCar(Context context, GoodsVOListItem goodsVOListItem, OnOrderResultListener listener) {
        Map<String, Object> params = new HashMap<>();
        params.put("areaId", goodsVOListItem.getAreaId());
        params.put("cartType", 0);
        params.put("createBy", UserStore.getYbCode());
        params.put("createGoodsName", goodsVOListItem.getGoodsTitle());
        params.put("createGoodsPrice", goodsVOListItem.getGoodsPrice());
        params.put("goodsCount", 1);
        params.put("goodsImgUrl", goodsVOListItem.getGoodsImageUrl());
        params.put("goodsType", goodsVOListItem.getAreaType());
        params.put("skuId", goodsVOListItem.getSkuId());
        params.put("skuName", goodsVOListItem.getSkuIndexesName());
        params.put("spuId", goodsVOListItem.getSpuId());
        NetWork.getInstance().post(context, ChildUrl.ORDER_GOODS_ADD_CAR, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "cancelOrder onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || listener == null) {
                    return;
                }
                listener.onOrderResult(bean != null);
            }

            @Override
            public void onFailure(Throwable var1) {
                listener.onOrderResult(false);
                disposeError(var1);
            }
        });
    }

    /**
     * 处理错误
     *
     * @param var1 throwable
     */
    public static void disposeError(Throwable var1) {
        try {
            ErrorResponse errorResponse = GsonUtil.GsonToBean(var1.getMessage(), ErrorResponse.class);
            ToastView.showToast(errorResponse.getMessage());
        } catch (Exception e) {
            ToastView.showToast(R.string.account_error);
            e.printStackTrace();
        }
    }

    /**
     * 结果回调接口
     */
    public interface OnOrderResultListener {
        void onOrderResult(boolean successful);
    }

    /**
     * 订单状态结果回调接口
     */
    public interface OnOrderStatusListener {
        void onOrderStatus(GoodsDetail goodsDetail);
    }

}

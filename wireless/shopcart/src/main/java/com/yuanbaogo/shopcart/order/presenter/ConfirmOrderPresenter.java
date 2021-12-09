package com.yuanbaogo.shopcart.order.presenter;

import android.util.Log;

import com.yuanbaogo.commonlib.router.model.AddressOrderBean;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.shopcart.order.contract.ConfirmOrderContract;
import com.yuanbaogo.shopcart.order.model.CartSettleBean;
import com.yuanbaogo.shopcart.order.model.OrderCheckBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/4/21 4:05 PM
 * @Modifier:
 * @Modify:
 */
public class ConfirmOrderPresenter extends MvpBasePersenter<ConfirmOrderContract.View>
        implements ConfirmOrderContract.Presenter {

    /**
     * 获取默认地址
     *
     * @param userId
     */
    @Override
    public void getAddress(String userId) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().post(getContext(),
                ChildUrl.GET_DEFAULT_ADDRESS,
                params,
                new RequestSateListener<AddressOrderBean>() {
                    @Override
                    public void onSuccess(int code, AddressOrderBean bean) {
                        Log.e("==========HG:默认地址：：：",
                                "getOrderCheck onSuccess: code:" + code + "   bean:" + bean);
                        if (code == 200 && getView() != null) {
                            getView().setAddress(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        Log.e("==========HG:默认地址：：：",
                                "getOrderCheck onFailure: " + var1.getMessage());
                        if (getView() != null) {
                            getView().initAddress();
                        }
                    }
                });
    }

    /**
     * 获取商品信息
     *
     * @param skuIdList
     */
    @Override
    public void getOrderCheck(List<String> skuIdList) {
        NetWork.getInstance().post(
                skuIdList,
                getContext(),
                ChildUrl.ORDER_CHECK,
                new RequestSateListener<List<OrderCheckBean>>() {
                    @Override
                    public void onSuccess(int code, List<OrderCheckBean> bean) {
                        Log.e("==========HG:订单确认信息：：：",
                                "getOrderCheck onSuccess: code:" + code + "   bean:" + bean);
                        if (code == 200 && getView() != null) {
                            getView().setOrderCheck(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        Log.e("==========HG:订单确认信息：：：",
                                "getOrderCheck onFailure: " + var1.getMessage());
                        if (getView() != null) {
                            getView().initOrderCheck();
                        }
                    }
                });
    }

    /**
     * 获取商品信息
     *
     * @param id
     */
    @Override
    public void getGoodsInfo(String id) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.GOODS_INFO.replace("{id}", id),
                params,
                new RequestSateListener<OrderCheckBean>() {
                    @Override
                    public void onSuccess(int code, OrderCheckBean bean) {
                        if (code == 200 && getView() != null) {
                            List<OrderCheckBean> orderCheckBeans = new ArrayList<>();
                            orderCheckBeans.add(bean);
                            getView().setOrderCheck(orderCheckBeans);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initOrderCheck();
                        }
                    }
                });
    }


    /**
     * 下单
     *
     * @param addressId
     * @param num
     * @param skuId
     * @param type
     */
    @Override
    public void getCreateOrder(Long addressId, int num, String skuId, int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);
        params.put("num", num);
        params.put("skuId", skuId);
        params.put("type", type);
        NetWork.getInstance().post(getContext(),
                ChildUrl.CREATE_ORDER,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        Log.e("==========HG:下单：：：",
                                "getCreateOrder onSuccess: code:" + code + "   bean:" + bean);
                        if (code == 200 && getView() != null) {
                            getView().setCreateOrder(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        Log.e("==========HG:下单：：：",
                                "getCreateOrder onFailure: " + var1.getMessage());
                        if (getView() != null) {
                            getView().initCreateOrder();
                        }
                    }
                });
    }

    /**
     * 购物车下单
     *
     * @param bean
     */
    @Override
    public void getCartSettle(CartSettleBean bean) {
        Map<String, Object> params = new HashMap<>();
        params.put("addressId", bean.getAddressId());
        params.put("goodsIdList", bean.getGoodsIdList());
        params.put("type", bean.getType());

        NetWork.getInstance().post(
                getContext(),
                ChildUrl.CART_SETTLE,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        Log.e("==========HG:购物车下单：：：",
                                "getCartSettle onSuccess: code:" + code + "   bean:" + bean);
                        if (code == 200 && getView() != null) {
                            getView().setCartSettle(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        Log.e("==========HG:购物车下单：：：",
                                "getCartSettle onFailure: " + var1.getMessage());
                        if (getView() != null) {
                            getView().initCreateOrder();
                        }
                    }
                });
    }

    /**
     * 预约大抽奖
     *
     * @param addressId 地址ID
     * @param id        大抽奖ID
     */
    @Override
    public void getLuckDrawSubscribe(Long addressId, String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);
        params.put("id", id);
        NetWork.getInstance().post(
                getContext(),
                ChildUrl.LUCK_DRAW_SUBSCRIBE,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        if (code == 200 && getView() != null) {
                            getView().setCreateOrder(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initCreateOrder();
                        }
                    }
                });
    }

    /**
     * 直播带货下单
     *
     * @param bizId 业务ID(短视频ID,直播聊天室ID,场次号)
     * @param bean
     */
    @Override
    public void getLiveBuyNow(String bizId, CartSettleBean bean) {
        Map<String, Object> params = new HashMap<>();
        params.put("addressId", bean.getAddressId());
        params.put("goodsIdList", bean.getGoodsIdList());
        params.put("type", bean.getType());
        params.put("lot", bean.getLot());
        params.put("ybCode", UserStore.getYbCode());
        NetWork.getInstance().post(
                getContext(),
                ChildUrl.GENERATE_GOODS_ORDER.replace("{bizId}", bizId),
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        Log.e("==========HG:直播带货：：：",
                                "getLiveBuyNow onSuccess: code:" + code + "   bean:" + bean);
                        if (code == 200 && getView() != null) {
                            getView().setCreateOrder(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        Log.e("==========HG:直播带货：：：",
                                "getLiveBuyNow onFailure: " + var1.getMessage());
                        if (getView() != null) {
                            getView().initCreateOrder();
                        }
                    }
                });
    }

}

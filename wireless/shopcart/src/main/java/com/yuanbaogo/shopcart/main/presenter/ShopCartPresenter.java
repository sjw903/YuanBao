package com.yuanbaogo.shopcart.main.presenter;

import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.shopcart.main.contract.ShopCartContract;
import com.yuanbaogo.shopcart.main.model.MoveFavoritesBean;
import com.yuanbaogo.shopcart.main.model.ShopCartBean;
import com.yuanbaogo.shopcart.main.model.UpdateCartParametBean;
import com.yuanbaogo.zui.dialog.model.SkuBean;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/23/21 10:05 AM
 * @Modifier:
 * @Modify:
 */
public class ShopCartPresenter extends MvpBasePersenter<ShopCartContract.View>
        implements ShopCartContract.Presenter {

    @Override
    public void getQueryCartGoods(String ybCode, boolean isLoad) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.QUERY_CART_GOODS,
                params,
                new RequestSateListener<List<ShopCartBean>>() {
                    @Override
                    public void onSuccess(int code, List<ShopCartBean> bean) {
                        if (code == 200 && getView() != null) {
                            getView().setQueryCartGoods(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initQueryCartGoods();
                        }
                    }
                }, isLoad);
    }

    /**
     * 修改购物车规格
     *
     * @param bean
     */
    @Override
    public void getUpdateCartGoodsCount(UpdateCartParametBean bean) {
        Map<String, Object> params = new HashMap<>();
        params.put("createGoodsPrice", bean.getCreateGoodsPrice());
        params.put("goodsCount", bean.getGoodsCount());
        params.put("id", bean.getId());
        params.put("skuId", bean.getSkuId());
        params.put("skuName", bean.getSkuName());
        params.put("spuId", bean.getSpuId());
        NetWork.getInstance().post(getContext(),
                ChildUrl.UPDATE_CART_GOODS_COUNT,
                params,
                new RequestSateListener<ShopCartBean>() {
                    @Override
                    public void onSuccess(int code, ShopCartBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setUpdateCartGoodsCount(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initUpdateCartGoodsCount(false);
                        }
                    }
                }, false);
    }

    @Override
    public void getSku(String spuId) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.SKU.replace("{spuId}", spuId),
                params,
                new RequestSateListener<SkuBean>() {
                    @Override
                    public void onSuccess(int code, SkuBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setSku(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initSku();
                        }
                    }
                });
    }

    @Override
    public void getStock(String skuId) {
        Map<String, Object> params = new HashMap<>();
        params.put("skuId", skuId);
        NetWork.getInstance().get(getContext(),
                ChildUrl.STOCK,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        if (code == 200 && getView() != null) {
                            getView().setStock(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initStock();
                        }
                    }
                });
    }

    @Override
    public void getDeleteGoods(List<String> idList) {
        Map<String, Object> params = new HashMap<>();
        params.put("idList", idList);
        NetWork.getInstance().post(
                getContext(),
                ChildUrl.DELETE_GOODS,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        if (code == 200 && getView() != null) {
                            getView().setDeleteGoods(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initDeleteGoods();
                        }
                    }
                });
    }

    @Override
    public void getMoveFavorites(List<MoveFavoritesBean> shopCartId) {
        Map<String, Object> params = new HashMap<>();
        params.put("addFavoritesDtoList", shopCartId);
        NetWork.getInstance().post(getContext(),
                ChildUrl.MOVE_FAVORITES,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        if (code == 200 && getView() != null) {
                            getView().setDeleteGoods(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
//                            getView().initDeleteGoods();
                            ToastView.showToast("移入收藏夹失败");
                        }
                    }
                });
    }

}

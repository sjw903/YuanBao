package com.yuanbaogo.shop.productdetails.presenter;

import com.yuanbaogo.commonlib.router.model.OrderNumBean;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.shop.productdetails.contract.ProductDetailsContract;
import com.yuanbaogo.shop.productdetails.model.CommentBean;
import com.yuanbaogo.shop.productdetails.model.DetailBean;
import com.yuanbaogo.shop.productdetails.model.DetailsModelBean;
import com.yuanbaogo.shop.productdetails.model.ProductDetailsBean;
import com.yuanbaogo.shop.publics.model.RecommendBean;
import com.yuanbaogo.zui.dialog.model.SkuBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 2:39 PM
 * @Modifier:
 * @Modify:
 */
public class ProductDetailsPresenter extends MvpBasePersenter<ProductDetailsContract.View>
        implements ProductDetailsContract.Presenter {

    @Override
    public void initDisplayData() {
        getView().initTab();
        getView().initListener();
        List<DetailsModelBean> detailsBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DetailsModelBean bean = new DetailsModelBean();
            bean.setType(i + 1);
            detailsBeanList.add(bean);
        }
        getView().setAdapter(detailsBeanList);
    }

    @Override
    public void getSearchMerchandiseDetail(String spuId) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.SEARCH_MERCHANDISE_DETAIL.replace("{spuId}", spuId),
                params,
                new RequestSateListener<ProductDetailsBean>() {
                    @Override
                    public void onSuccess(int code, ProductDetailsBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setSearchMerchandiseDetail(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initSearchMerchandiseDetail();
                        }
                    }
                });
    }

    /**
     * 推荐商品
     *
     * @param attribution 0 商品详情次屏推荐
     */
    @Override
    public void getSearchRecommend(int attribution) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", "1");
        params.put("pageSize", "6");
        NetWork.getInstance().post(getContext(),
                ChildUrl.RECOMMEND,
                params,
                new RequestSateListener<List<RecommendBean>>() {
                    @Override
                    public void onSuccess(int code, List<RecommendBean> bean) {
                        if (code == 200 && getView() != null) {
                            getView().setSearchRecommend(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initSearchRecommend();
                        }
                    }
                });
    }

    /**
     * 商品详情-商品评论
     *
     * @param page
     * @param rows
     * @param spuId
     */
    @Override
    public void getComment(int page, int rows, String spuId) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("size", rows);
        params.put("spuId", spuId);
        NetWork.getInstance().post(getContext(),
                ChildUrl.COMMENT,
                params,
                new RequestSateListener<CommentBean>() {
                    @Override
                    public void onSuccess(int code, CommentBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setComment(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initComment();
                        }
                    }
                });
    }

    @Override
    public void getDetail(String id) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.DETAIL.replace("{id}", id),
                params,
                new RequestSateListener<DetailBean>() {
                    @Override
                    public void onSuccess(int code, DetailBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setDetail(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initDetail();
                        }
                    }
                });
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
    public void getStock(String spuId) {
        Map<String, Object> params = new HashMap<>();
        params.put("skuId", spuId);
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

    /**
     * APP-查询商品是否加入收藏夹接口
     *
     * @param spuId
     * @param ybCode
     */
    @Override
    public void getFindFavoritesFlag(String spuId, String ybCode) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.FIND_FAVORITES_FLAG
                        .replace("{spuId}", spuId),
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        if (code == 200 && getView() != null) {
                            getView().setFindFavoritesFlag(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initFindFavoritesFlag();
                        }
                    }
                });
    }

    /**
     * APP-收藏夹新增商品接口
     *
     * @param spuId
     * @param createBy
     * @param goodsType
     */
    @Override
    public void getAddFavorites(String spuId, String createBy, int goodsType) {
        Map<String, Object> params = new HashMap<>();
        params.put("spuId", spuId);
        params.put("createBy", createBy);
        params.put("goodsType", goodsType);
        NetWork.getInstance().post(getContext(),
                ChildUrl.ADD_FAVORITES,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        if (code == 200 && getView() != null) {
                            getView().setAddFavorites(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initAddFavorites();
                        }
                    }
                });
    }

    /**
     * APP-商品详情页移除收藏夹
     *
     * @param spuId
     * @param createBy
     */
    @Override
    public void getDeleteFavorites(String spuId, String createBy) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.DELETE_FAVORITES1
                        .replace("{spuId}", spuId),
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        if (code == 200 && getView() != null) {
                            getView().setDeleteFavorites(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initDeltetFavorites();
                        }
                    }
                });
    }

    /**
     * APP-购物车新增商品接口
     *
     * @param bean
     */
    @Override
    public void getAddGoods(OrderNumBean bean) {
        Map<String, Object> params = new HashMap<>();
        params.put("areaId", bean.getAreaId());
        params.put("cartType", bean.getCartType());
        params.put("createBy", bean.getCreateBy());
        params.put("createGoodsName", bean.getCreateGoodsName());
        params.put("createGoodsPrice", bean.getCreateGoodsPrice());
        params.put("goodsCount", bean.getGoodsCount());
        params.put("goodsImgUrl", bean.getGoodsImgUrl());
        params.put("goodsType", bean.getGoodsType());
        params.put("skuId", bean.getSkuId());
        params.put("skuName", bean.getSkuName());
        params.put("spuId", bean.getSpuId());
        NetWork.getInstance().post(getContext(),
                ChildUrl.ADD_GOODS,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {

                        if (code == 200 && getView() != null) {
                            getView().setAddGoods(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initAddGoods(2);
                        }
                    }
                });
    }

}

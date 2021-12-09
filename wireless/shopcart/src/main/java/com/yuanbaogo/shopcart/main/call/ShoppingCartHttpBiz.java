package com.yuanbaogo.shopcart.main.call;

import com.yuanbaogo.shopcart.main.model.ShopCartBean;
import com.yuanbaogo.shopcart.main.model.ShoppingCartBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 购物车数据请求业务
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 8:48 AM
 * @Modifier:
 * @Modify:
 */
public class ShoppingCartHttpBiz {

    /**
     * 普通数据
     */
    static ArrayList<ShoppingCartBean.Goods.Prefecture> prefectureList;
    static ArrayList<ShoppingCartBean.Goods> goods;
    static ShoppingCartBean shoppingCartBean;

    /**
     * 失效数据
     */
    static ArrayList<ShoppingCartBean.Goods.Prefecture> prefectureList3;
    static ArrayList<ShoppingCartBean.Goods> goods3;
    static ShoppingCartBean shoppingCartBean3;

    static Map<String, Boolean> isSelectShop;

    public static List<ShoppingCartBean> handleOrderList(Map<String, Boolean> isSelect,
                                                         List<ShopCartBean> bean) {
        isSelectShop = isSelect;
        List<ShoppingCartBean> shoppingCartBeans = new ArrayList<>();

        prefectureList = new ArrayList<>();
        prefectureList3 = new ArrayList<>();

        goods = new ArrayList<>();
        goods3 = new ArrayList<>();

        for (int i = 0; i < bean.size(); i++) {
            // 6失效商品

            if (bean.get(i).getGoodsType() == 6) {//失效
                addPrefecture(3, bean.get(i));
            } else {
                addPrefecture(1, bean.get(i));
            }
        }

        if (prefectureList.size() != 0) {
            goods.add(new ShoppingCartBean.Goods().setChildSelected(false).setPrefectures(prefectureList));
        }
        if (prefectureList3.size() != 0) {
            goods3.add(new ShoppingCartBean.Goods().setChildSelected(false).setPrefectures(prefectureList3));
        }

        if (goods.size() != 0) {
            shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.setGoods(goods).setMerchantName("普通商品").setMerID("1");
        } else {
            if (shoppingCartBean != null) {
                shoppingCartBean = null;
            }
        }
        if (goods3.size() != 0) {
            shoppingCartBean3 = new ShoppingCartBean();
            shoppingCartBean3.setGoods(goods3).setMerchantName("失效商品").setMerID("3");
        } else {
            if (shoppingCartBean3 != null) {
                shoppingCartBean3 = null;
            }
        }

        if (shoppingCartBean != null) {
            shoppingCartBeans.add(shoppingCartBean);
        }
        if (shoppingCartBean3 != null) {
            shoppingCartBeans.add(shoppingCartBean3);
        }
        return shoppingCartBeans;
    }

    /**
     * 分别添加数据
     *
     * @param type
     * @param bean
     */
    public static void addPrefecture(int type, ShopCartBean bean) {
        ShoppingCartBean.Goods.Prefecture prefecture = new ShoppingCartBean.Goods.Prefecture();
        prefecture.setId(bean.getId())
                .setAreaId(bean.getAreaId())
                .setCreateGoodsName(bean.getCreateGoodsName())
                .setUpdateGoodsName(bean.getUpdateGoodsName())
                .setSpuId(bean.getSpuId())
                .setSkuId(bean.getSkuId())
                .setSkuName(bean.getSkuName())
                .setGoodsImgUrl(bean.getGoodsImgUrl())
                .setGoodsCount(bean.getGoodsCount())
                .setCreateGoodsPrice(bean.getCreateGoodsPrice())
                .setUpdateGoodsPrice(bean.getUpdateGoodsPrice())
                .setInvalidReason(bean.getInvalidReason())
                .setGoodsType(bean.getGoodsType())
                .setCreateTime(bean.getCreateTime())
                .setChildSelected(isSelectShop.containsKey(bean.getSpuId()) ? true : false);
        if (type == 1) {//普通
            prefectureList.add(prefecture);
        } else if (type == 3) {//失效
            prefectureList3.add(prefecture);
        }
    }

}

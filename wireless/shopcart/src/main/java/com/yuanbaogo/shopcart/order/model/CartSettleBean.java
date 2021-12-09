package com.yuanbaogo.shopcart.order.model;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/24/21 3:00 PM
 * @Modifier:
 * @Modify:
 */
public class CartSettleBean {

//  {
//      "addressId": 0,
//      "goodsIdList": [
//          {
//              "areaId": 0,
//              "goodsType": 0,
//              "num": 0,
//              "skuId": 0
//          }
//      ],
//      "type": 0,
//      "lot": ""
//  }

    /**
     * 收货地址id
     */
    private Long addressId;
    /**
     * 购买的商品信息集合
     */
    private List<CartSettleGoodsBean> goodsIdList;
    /**
     * 商城订单 订单类型:0-商城订单
     * 直播带货 订单类型:1-视频订单 2-直播订单,示例值(1)
     */
    private int type;
    /**
     * 商品批次
     */
    private String lot;

    public Long getAddressId() {
        return addressId;
    }

    public CartSettleBean setAddressId(Long addressId) {
        this.addressId = addressId;
        return this;
    }

    public List<CartSettleGoodsBean> getGoodsIdList() {
        return goodsIdList;
    }

    public CartSettleBean setGoodsIdList(List<CartSettleGoodsBean> goodsIdList) {
        this.goodsIdList = goodsIdList;
        return this;
    }

    public int getType() {
        return type;
    }

    public CartSettleBean setType(int type) {
        this.type = type;
        return this;
    }

    public String getLot() {
        return lot;
    }

    public CartSettleBean setLot(String lot) {
        this.lot = lot;
        return this;
    }

    public static class CartSettleGoodsBean {

        //商品所属专区id
        private String areaId;
        //购物车商品类型 0普通商品 1五百专区商品 2五千专区商品 3五万专区商品 4一城一品 5秒杀商品 6失效商品 7主题专区
        private Integer goodsType;
        //购买商品数量
        private int num;
        //商品SKUid
        private String skuId;

        public String getAreaId() {
            return areaId;
        }

        public CartSettleGoodsBean setAreaId(String areaId) {
            this.areaId = areaId;
            return this;
        }

        public Integer getGoodsType() {
            return goodsType;
        }

        public CartSettleGoodsBean setGoodsType(Integer goodsType) {
            this.goodsType = goodsType;
            return this;
        }

        public int getNum() {
            return num;
        }

        public CartSettleGoodsBean setNum(int num) {
            this.num = num;
            return this;
        }

        public String getSkuId() {
            return skuId;
        }

        public CartSettleGoodsBean setSkuId(String skuId) {
            this.skuId = skuId;
            return this;
        }
    }

}

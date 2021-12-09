package com.yuanbaogo.commonlib.router.model;

import java.io.Serializable;

/**
 * @Description: 订单 商品选择数量
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/18/21 1:52 PM
 * @Modifier:
 * @Modify:
 */
public class OrderNumBean implements Serializable {

    //专区id,仅拼团、一城一品、秒杀、主题专区等专区商品添加购物车时传此参数
    private String areaId;

    //订单类型 0-商城订单
    private int cartType;

    //用户元宝号
    private String createBy;

    //创建购物车时的商品名称
    private String createGoodsName;

    //创建购物车时的商品单价,单位:分
    private long createGoodsPrice;

    //购买商品数量
    private int goodsCount;

    //商品图片路径
    private String goodsImgUrl;

    //购物车商品类型 0普通商品 1五百专区商品 2五千专区商品 3五万专区商品 4一城一品 5秒杀商品 7主题专区
    private int goodsType;

    //规格id
    private String skuId;

    //规格名称，前端组装后传给后端
    private String skuName;

    //商品id
    private String spuId;

    //大抽奖ID
    private String id;

    public String getAreaId() {
        return areaId;
    }

    public OrderNumBean setAreaId(String areaId) {
        this.areaId = areaId;
        return this;
    }

    public int getCartType() {
        return cartType;
    }

    public OrderNumBean setCartType(int cartType) {
        this.cartType = cartType;
        return this;
    }

    public String getCreateBy() {
        return createBy;
    }

    public OrderNumBean setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public String getCreateGoodsName() {
        return createGoodsName;
    }

    public OrderNumBean setCreateGoodsName(String createGoodsName) {
        this.createGoodsName = createGoodsName;
        return this;
    }

    public long getCreateGoodsPrice() {
        return createGoodsPrice;
    }

    public OrderNumBean setCreateGoodsPrice(long createGoodsPrice) {
        this.createGoodsPrice = createGoodsPrice;
        return this;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public OrderNumBean setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
        return this;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public OrderNumBean setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
        return this;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public OrderNumBean setGoodsType(int goodsType) {
        this.goodsType = goodsType;
        return this;
    }

    public String getSkuId() {
        return skuId;
    }

    public OrderNumBean setSkuId(String skuId) {
        this.skuId = skuId;
        return this;
    }

    public String getSkuName() {
        return skuName;
    }

    public OrderNumBean setSkuName(String skuName) {
        this.skuName = skuName;
        return this;
    }

    public String getSpuId() {
        return spuId;
    }

    public OrderNumBean setSpuId(String spuId) {
        this.spuId = spuId;
        return this;
    }

    public String getId() {
        return id;
    }

    public OrderNumBean setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "OrderNumBean{" +
                "areaId='" + areaId + '\'' +
                ", cartType=" + cartType +
                ", createBy='" + createBy + '\'' +
                ", createGoodsName='" + createGoodsName + '\'' +
                ", createGoodsPrice=" + createGoodsPrice +
                ", goodsCount=" + goodsCount +
                ", goodsImgUrl='" + goodsImgUrl + '\'' +
                ", goodsType=" + goodsType +
                ", skuId='" + skuId + '\'' +
                ", skuName='" + skuName + '\'' +
                ", spuId='" + spuId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

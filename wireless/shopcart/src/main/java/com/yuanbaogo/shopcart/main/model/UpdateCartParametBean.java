package com.yuanbaogo.shopcart.main.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/19/21 7:51 PM
 * @Modifier:
 * @Modify:
 */
public class UpdateCartParametBean {

//    {
//            "createGoodsPrice": 0,
//            "goodsCount": 0,
//            "id": 0,
//            "skuId": 0,
//            "skuName": "",
//            "spuId": 0,
//            "ybCode": 0
//    }

    private long createGoodsPrice;
    private int goodsCount;
    private String id;
    private String skuId;
    private String skuName;
    private String spuId;
    private String ybCode;

    public long getCreateGoodsPrice() {
        return createGoodsPrice;
    }

    public UpdateCartParametBean setCreateGoodsPrice(long createGoodsPrice) {
        this.createGoodsPrice = createGoodsPrice;
        return this;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public UpdateCartParametBean setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
        return this;
    }

    public String getId() {
        return id;
    }

    public UpdateCartParametBean setId(String id) {
        this.id = id;
        return this;
    }

    public String getSkuId() {
        return skuId;
    }

    public UpdateCartParametBean setSkuId(String skuId) {
        this.skuId = skuId;
        return this;
    }

    public String getSkuName() {
        return skuName;
    }

    public UpdateCartParametBean setSkuName(String skuName) {
        this.skuName = skuName;
        return this;
    }

    public String getSpuId() {
        return spuId;
    }

    public UpdateCartParametBean setSpuId(String spuId) {
        this.spuId = spuId;
        return this;
    }

    public String getYbCode() {
        return ybCode;
    }

    public UpdateCartParametBean setYbCode(String ybCode) {
        this.ybCode = ybCode;
        return this;
    }
}

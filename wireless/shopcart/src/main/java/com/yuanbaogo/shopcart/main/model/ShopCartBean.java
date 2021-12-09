package com.yuanbaogo.shopcart.main.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/17/21 3:15 PM
 * @Modifier:
 * @Modify:
 */
public class ShopCartBean {

//    [
//        {
//            "id": "1427180687495913472",
//            "areaId": "43",
//            "createGoodsName": "玄冰4000",
//            "updateGoodsName": "玄冰400+",
//            "spuId": "1425647363516039168",
//            "skuId": "1425647363696394241",
//            "skuName": "12",
//            "goodsImgUrl": "",
//            "goodsCount": 5,
//            "createGoodsPrice": 10100,
//            "updateGoodsPrice": 10100,
//            "invalidReason": null,
//            "goodsType": 4,
//            "createTime": 1629101366000
//        }
//    ]

    private String id;
    private String areaId;
    private String createGoodsName;
    private String updateGoodsName;
    private String spuId;
    private String skuId;
    private String skuName;
    private String goodsImgUrl;
    private int goodsCount;
    private long createGoodsPrice;
    private long updateGoodsPrice;
    private String invalidReason;
    private int goodsType;
    private String createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCreateGoodsName() {
        return createGoodsName;
    }

    public void setCreateGoodsName(String createGoodsName) {
        this.createGoodsName = createGoodsName;
    }

    public String getUpdateGoodsName() {
        return updateGoodsName;
    }

    public void setUpdateGoodsName(String updateGoodsName) {
        this.updateGoodsName = updateGoodsName;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public long getCreateGoodsPrice() {
        return createGoodsPrice;
    }

    public void setCreateGoodsPrice(long createGoodsPrice) {
        this.createGoodsPrice = createGoodsPrice;
    }

    public long getUpdateGoodsPrice() {
        return updateGoodsPrice;
    }

    public void setUpdateGoodsPrice(long updateGoodsPrice) {
        this.updateGoodsPrice = updateGoodsPrice;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ShopCartBean{" +
                "id='" + id + '\'' +
                ", areaId='" + areaId + '\'' +
                ", createGoodsName='" + createGoodsName + '\'' +
                ", updateGoodsName='" + updateGoodsName + '\'' +
                ", spuId='" + spuId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", skuName='" + skuName + '\'' +
                ", goodsImgUrl='" + goodsImgUrl + '\'' +
                ", goodsCount=" + goodsCount +
                ", createGoodsPrice=" + createGoodsPrice +
                ", updateGoodsPrice=" + updateGoodsPrice +
                ", invalidReason='" + invalidReason + '\'' +
                ", goodsType=" + goodsType +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}

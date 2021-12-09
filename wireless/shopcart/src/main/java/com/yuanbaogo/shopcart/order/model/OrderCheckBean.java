package com.yuanbaogo.shopcart.order.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/18/21 11:46 AM
 * @Modifier:
 * @Modify:
 */
public class OrderCheckBean {

//  [
//      {
//          "spuId": "1425647363516039168",
//          "skuId": "1425647363696394241",
//          "coverImages": "https://ybsjds-oss-shanghai.oss-cn-shanghai.aliyuncs.com/material/picture/mall-management/DX4Y6TU9KK/mall-management-picture/DX4Y6TU9KK.png?1628735761",
//          "goodsName": "玄冰400+",
//          "salePrice": 10100,
//          "linePrice": 30000,
//          "stock": 10,
//          "skuIndexesName": "黑色 200cm",
//          "specialId": 0,
//          "specialName": "",
//          "specialType": 0,
//          "enabled": true
//      }
//  ]

//      {
//          "spuId": "1427460990766186496",
//          "skuId": "1427460990782963712",
//          "coverImages": "https://ybsjds-oss-shanghai.oss-cn-shanghai.aliyuncs.com/material/picture/mall-management/M40K888OR0/mall-management-picture/M40K888OR0.png?1629168192",
//          "goodsName": "测试丫丫丫测试丫丫丫",
//          "salePrice": 100,
//          "groupGoodsPrice": 10000,
//          "skuIndexesName": "2"
//      }

    private String spuId;
    private String skuId;
    private String coverImages;
    private String goodsName;
    private long salePrice;
    private long linePrice;
    private int stock;
    private String skuIndexesName;
    private String specialId;
    private String specialName;
    private int specialType;
    private boolean enabled;
    private int num;
    //拼团价格
    private Long groupGoodsPrice;

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

    public String getCoverImages() {
        return coverImages;
    }

    public void setCoverImages(String coverImages) {
        this.coverImages = coverImages;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }

    public long getLinePrice() {
        return linePrice;
    }

    public void setLinePrice(long linePrice) {
        this.linePrice = linePrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSkuIndexesName() {
        return skuIndexesName;
    }

    public void setSkuIndexesName(String skuIndexesName) {
        this.skuIndexesName = skuIndexesName;
    }

    public String getSpecialId() {
        return specialId;
    }

    public void setSpecialId(String specialId) {
        this.specialId = specialId;
    }

    public String getSpecialName() {
        return specialName;
    }

    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    public int getSpecialType() {
        return specialType;
    }

    public void setSpecialType(int specialType) {
        this.specialType = specialType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Long getGroupGoodsPrice() {
        return groupGoodsPrice;
    }

    public void setGroupGoodsPrice(Long groupGoodsPrice) {
        this.groupGoodsPrice = groupGoodsPrice;
    }
}

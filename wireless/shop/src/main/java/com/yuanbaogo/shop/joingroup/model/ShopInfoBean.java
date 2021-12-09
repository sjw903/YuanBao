package com.yuanbaogo.shop.joingroup.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/6/21 5:24 PM
 * @Modifier:
 * @Modify:
 */
public class ShopInfoBean {

    private String id;//商品ID
    private Long lotteryTime;//抽奖时间
    private String coverImages;//商品封面URL
    private String goodsName;//商品名称
    private Long groupGoodsPrice;//商品价格
    private Integer imagesList;//幸运拼团列表封面mipmap

    public String getId() {
        return id;
    }

    public ShopInfoBean setId(String id) {
        this.id = id;
        return this;
    }

    public Long getLotteryTime() {
        return lotteryTime;
    }

    public ShopInfoBean setLotteryTime(Long lotteryTime) {
        this.lotteryTime = lotteryTime;
        return this;
    }

    public String getCoverImages() {
        return coverImages;
    }

    public ShopInfoBean setCoverImages(String coverImages) {
        this.coverImages = coverImages;
        return this;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public ShopInfoBean setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public Long getGroupGoodsPrice() {
        return groupGoodsPrice;
    }

    public ShopInfoBean setGroupGoodsPrice(Long groupGoodsPrice) {
        this.groupGoodsPrice = groupGoodsPrice;
        return this;
    }

    public Integer getImagesList() {
        return imagesList;
    }

    public ShopInfoBean setImagesList(Integer imagesList) {
        this.imagesList = imagesList;
        return this;
    }
}

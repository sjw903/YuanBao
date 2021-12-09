
package com.yuanbaogo.mine.order.model;

@SuppressWarnings("unused")
public class GoodsVOList {

    private Long mBuyQuantity;
    private String mGoodsImageUrl;
    private Long mGoodsPrice;
    private String mGoodsTitle;

    public Long getBuyQuantity() {
        return mBuyQuantity;
    }

    public void setBuyQuantity(Long buyQuantity) {
        mBuyQuantity = buyQuantity;
    }

    public String getGoodsImageUrl() {
        return mGoodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        mGoodsImageUrl = goodsImageUrl;
    }

    public Long getGoodsPrice() {
        return mGoodsPrice;
    }

    public void setGoodsPrice(Long goodsPrice) {
        mGoodsPrice = goodsPrice;
    }

    public String getGoodsTitle() {
        return mGoodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        mGoodsTitle = goodsTitle;
    }

}

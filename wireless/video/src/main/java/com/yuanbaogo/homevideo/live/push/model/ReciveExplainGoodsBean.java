package com.yuanbaogo.homevideo.live.push.model;



public class ReciveExplainGoodsBean {

    private long goodsPrice;
    private String goodsTitle;
    private String goodsUrl;
    private String liveGoods;
    private String productBatch;

    public long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getLiveGoods() {
        return liveGoods;
    }

    public void setLiveGoods(String liveGoods) {
        this.liveGoods = liveGoods;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }
}

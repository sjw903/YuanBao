package com.yuanbaogo.mine.order.model;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

public class GoodsVOListItem {
    private String areaId;
    private int areaType;
    private String goodsPrice;
    private String goodsImageUrl;
    private int buyQuantity;
    private String goodsTitle;
    private String skuIndexesName;
    private String skuId;
    private String spuId;
    private List<LocalMedia> mLocalMediaList = new ArrayList<>();
    private String descEvaluation;
    private List<String> evaluationImgUrl;
    private float desc = 1f;

    private int goodsCount;
    private Long goodsAmount;


    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setBuyQuantity(int buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public List<LocalMedia> getLocalMediaList() {
        return mLocalMediaList;
    }

    public void setLocalMediaList(List<LocalMedia> localMediaList) {
        this.mLocalMediaList = localMediaList;
    }

    public String getDescEvaluation() {
        return descEvaluation;
    }

    public void setDescEvaluation(String descEvaluation) {
        this.descEvaluation = descEvaluation;
    }

    public float getDesc() {
        return desc;
    }

    public void setDesc(float desc) {
        this.desc = desc;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public List<String> getEvaluationImgUrl() {
        return evaluationImgUrl;
    }

    public void setEvaluationImgUrl(List<String> evaluationImgUrl) {
        this.evaluationImgUrl = evaluationImgUrl;
    }

    public String getSkuIndexesName() {
        return skuIndexesName;
    }

    public void setSkuIndexesName(String skuIndexesName) {
        this.skuIndexesName = skuIndexesName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public int getAreaType() {
        return areaType;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Long getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Long goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    @Override
    public String toString() {
        return "GoodsVOListItem{" +
                "areaId='" + areaId + '\'' +
                ", areaType=" + areaType +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", goodsImageUrl='" + goodsImageUrl + '\'' +
                ", buyQuantity=" + buyQuantity +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", skuIndexesName='" + skuIndexesName + '\'' +
                ", skuId='" + skuId + '\'' +
                ", spuId='" + spuId + '\'' +
                ", mLocalMediaList=" + mLocalMediaList +
                ", descEvaluation='" + descEvaluation + '\'' +
                ", evaluationImgUrl=" + evaluationImgUrl +
                ", desc=" + desc +
                ", goodsCount=" + goodsCount +
                ", goodsAmount=" + goodsAmount +
                '}';
    }
}

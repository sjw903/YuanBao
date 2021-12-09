package com.yuanbaogo.mine.order.model;

import java.util.List;

public class OrderListResponseListItem {
    private int orderType;
    private String totalPrice;
    private List<GoodsVOListItem> goodsVOList;
    private int afterSailedStatus;
    private String afterSalesId;
    private String realPayedPrice;
    private String totalOrderId;
    private String totalBuyQuantity;
    private Object createdTime;
    private int orderStatus;
    private boolean commented;
    private long lotteryTime;
    private int luckStatus;
    private int orderActivityType;

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setGoodsVOList(List<GoodsVOListItem> goodsVOList) {
        this.goodsVOList = goodsVOList;
    }

    public List<GoodsVOListItem> getGoodsVOList() {
        return goodsVOList;
    }

    public void setAfterSailedStatus(int afterSailedStatus) {
        this.afterSailedStatus = afterSailedStatus;
    }

    public int getAfterSailedStatus() {
        return afterSailedStatus;
    }

    public void setRealPayedPrice(String realPayedPrice) {
        this.realPayedPrice = realPayedPrice;
    }

    public String getRealPayedPrice() {
        return realPayedPrice;
    }

    public void setTotalOrderId(String totalOrderId) {
        this.totalOrderId = totalOrderId;
    }

    public String getTotalOrderId() {
        return totalOrderId;
    }

    public void setTotalBuyQuantity(String totalBuyQuantity) {
        this.totalBuyQuantity = totalBuyQuantity;
    }

    public String getTotalBuyQuantity() {
        return totalBuyQuantity;
    }

    public void setCreatedTime(Object createdTime) {
        this.createdTime = createdTime;
    }

    public Object getCreatedTime() {
        return createdTime;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public boolean isCommented() {
        return commented;
    }

    public void setCommented(boolean commented) {
        this.commented = commented;
    }

    public String getAfterSalesId() {
        return afterSalesId;
    }

    public void setAfterSalesId(String afterSalesId) {
        this.afterSalesId = afterSalesId;
    }

    public long getLotteryTime() {
        return lotteryTime;
    }

    public void setLotteryTime(long lotteryTime) {
        this.lotteryTime = lotteryTime;
    }

    public int getLuckStatus() {
        return luckStatus;
    }

    public void setLuckStatus(int luckStatus) {
        this.luckStatus = luckStatus;
    }

    public int getOrderActivityType() {
        return orderActivityType;
    }

    public void setOrderActivityType(int orderActivityType) {
        this.orderActivityType = orderActivityType;
    }

    @Override
    public String toString() {
        return "OrderListResponseListItem{" +
                "orderType=" + orderType +
                ", totalPrice='" + totalPrice + '\'' +
                ", goodsVOList=" + goodsVOList +
                ", afterSailedStatus=" + afterSailedStatus +
                ", afterSalesId='" + afterSalesId + '\'' +
                ", realPayedPrice='" + realPayedPrice + '\'' +
                ", totalOrderId='" + totalOrderId + '\'' +
                ", totalBuyQuantity='" + totalBuyQuantity + '\'' +
                ", createdTime=" + createdTime +
                ", orderStatus=" + orderStatus +
                ", commented=" + commented +
                ", lotteryTime='" + lotteryTime + '\'' +
                ", luckStatus='" + luckStatus + '\'' +
                ", orderActivityType='" + orderActivityType + '\'' +
                '}';
    }
}
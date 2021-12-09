package com.yuanbaogo.mine.order.model;

import java.util.List;

public class GoodsDetail {
    private Long afterSailedStatus;
    private OrderInfoAddressVO orderInfoAddressVO;
    private Long payedTime;
    private List<GoodsVOListItem> goodsVOList;
    private Integer payedType;
    private String realPayedPrice;
    private String totalOrderId;
    private Object discountPrice;
    private Long createdTime;
    private String totalOrderPrice;
    private Boolean commented;
    private Integer orderActivityType;
    private Long lotteryTime;
    private Integer luckStatus;
    private Boolean afterSaleButton;
    private Integer orderStatus;
    private Boolean payed;

    public void setOrderInfoAddressVO(OrderInfoAddressVO orderInfoAddressVO) {
        this.orderInfoAddressVO = orderInfoAddressVO;
    }

    public OrderInfoAddressVO getOrderInfoAddressVO() {
        return orderInfoAddressVO;
    }

    public void setPayedTime(Long payedTime) {
        this.payedTime = payedTime;
    }

    public Long getPayedTime() {
        return payedTime;
    }

    public void setGoodsVOList(List<GoodsVOListItem> goodsVOList) {
        this.goodsVOList = goodsVOList;
    }

    public List<GoodsVOListItem> getGoodsVOList() {
        return goodsVOList;
    }

    public void setPayedType(Integer payedType) {
        this.payedType = payedType;
    }

    public Integer getPayedType() {
        return payedType;
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

    public void setDiscountPrice(Object discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Object getDiscountPrice() {
        return discountPrice;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setTotalOrderPrice(String totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public String getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setCommented(Boolean commented) {
        this.commented = commented;
    }

    public Boolean isCommented() {
        return commented;
    }

    public Integer getOrderActivityType() {
        return orderActivityType;
    }

    public void setOrderActivityType(Integer orderActivityType) {
        this.orderActivityType = orderActivityType;
    }

    public Long getLotteryTime() {
        return lotteryTime;
    }

    public void setLotteryTime(Long lotteryTime) {
        this.lotteryTime = lotteryTime;
    }

    public Integer getLuckStatus() {
        return luckStatus;
    }

    public void setLuckStatus(Integer luckStatus) {
        this.luckStatus = luckStatus;
    }

    public boolean isAfterSaleButton() {
        return afterSaleButton;
    }

    public void setAfterSaleButton(Boolean afterSaleButton) {
        this.afterSaleButton = afterSaleButton;
    }

    public Long getAfterSailedStatus() {
        return afterSailedStatus;
    }

    public void setAfterSailedStatus(Long afterSailedStatus) {
        this.afterSailedStatus = afterSailedStatus;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean isPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    @Override
    public String toString() {
        return "GoodsDetail{" +
                "afterSailedStatus=" + afterSailedStatus +
                ", orderInfoAddressVO=" + orderInfoAddressVO +
                ", payedTime=" + payedTime +
                ", goodsVOList=" + goodsVOList +
                ", payedType=" + payedType +
                ", realPayedPrice='" + realPayedPrice + '\'' +
                ", totalOrderId='" + totalOrderId + '\'' +
                ", discountPrice=" + discountPrice +
                ", createdTime=" + createdTime +
                ", totalOrderPrice='" + totalOrderPrice + '\'' +
                ", commented=" + commented +
                ", orderActivityType=" + orderActivityType +
                ", lotteryTime=" + lotteryTime +
                ", luckStatus=" + luckStatus +
                ", afterSaleButton=" + afterSaleButton +
                ", orderStatus=" + orderStatus +
                ", payed=" + payed +
                '}';
    }
}
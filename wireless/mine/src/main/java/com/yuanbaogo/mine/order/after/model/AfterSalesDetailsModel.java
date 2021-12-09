package com.yuanbaogo.mine.order.after.model;

import com.yuanbaogo.mine.order.model.GoodsVOListItem;

import java.util.List;

public class AfterSalesDetailsModel {
    private String reason;
    private int afterSalePrice;
    private String consignee;
    private String afterSalesId;
    private String orderId;
    private List<GoodsVOListItem> goodsVoList;
    private String expressNumber;
    private long createTime;
    private String expressCompany;
    private String consigneeMobile;
    private int salesStatus;
    private String closeReason;
    private String consigneeAddress;
    private boolean expressNumberFlag;
    private String sendBackInfo;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setAfterSalePrice(int afterSalePrice) {
        this.afterSalePrice = afterSalePrice;
    }

    public int getAfterSalePrice() {
        return afterSalePrice;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setAfterSalesId(String afterSalesId) {
        this.afterSalesId = afterSalesId;
    }

    public String getAfterSalesId() {
        return afterSalesId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setGoodsVoList(List<GoodsVOListItem> goodsVoList) {
        this.goodsVoList = goodsVoList;
    }

    public List<GoodsVOListItem> getGoodsVoList() {
        return goodsVoList;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setSalesStatus(int salesStatus) {
        this.salesStatus = salesStatus;
    }

    public int getSalesStatus() {
        return salesStatus;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public boolean isExpressNumberFlag() {
        return expressNumberFlag;
    }

    public void setExpressNumberFlag(boolean expressNumberFlag) {
        this.expressNumberFlag = expressNumberFlag;
    }

    public String getSendBackInfo() {
        return sendBackInfo;
    }

    public void setSendBackInfo(String sendBackInfo) {
        this.sendBackInfo = sendBackInfo;
    }

    @Override
    public String toString() {
        return
                "Response{" +
                        "reason = '" + reason + '\'' +
                        ",afterSalePrice = '" + afterSalePrice + '\'' +
                        ",consignee = '" + consignee + '\'' +
                        ",afterSalesId = '" + afterSalesId + '\'' +
                        ",orderId = '" + orderId + '\'' +
                        ",goodsVoList = '" + goodsVoList + '\'' +
                        ",expressNumber = '" + expressNumber + '\'' +
                        ",createTime = '" + createTime + '\'' +
                        ",expressCompany = '" + expressCompany + '\'' +
                        ",consigneeMobile = '" + consigneeMobile + '\'' +
                        ",salesStatus = '" + salesStatus + '\'' +
                        ",closeReason = '" + closeReason + '\'' +
                        ",consigneeAddress = '" + consigneeAddress + '\'' +
                        "}";
    }
}
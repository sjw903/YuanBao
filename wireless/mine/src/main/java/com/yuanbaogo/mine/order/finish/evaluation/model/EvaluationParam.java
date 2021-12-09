package com.yuanbaogo.mine.order.finish.evaluation.model;

import java.util.List;

public class EvaluationParam {
    private int serviceAttitude;
    private String commentRemark;
    private int describeTheNumber;
    private int logisticsServices;
    private String totalOrderId;
    private List<String> commentImageUrl;
    private String spuId;
    private String skuId;

    public EvaluationParam(int serviceAttitude, String commentRemark, int describeTheNumber, int logisticsServices, String totalOrderId, List<String> commentImageUrl, String spuId, String skuId) {
        this.serviceAttitude = serviceAttitude;
        this.commentRemark = commentRemark;
        this.describeTheNumber = describeTheNumber;
        this.logisticsServices = logisticsServices;
        this.totalOrderId = totalOrderId;
        this.commentImageUrl = commentImageUrl;
        this.spuId = spuId;
        this.skuId = skuId;
    }

    public void setServiceAttitude(int serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public int getServiceAttitude() {
        return serviceAttitude;
    }

    public void setCommentRemark(String commentRemark) {
        this.commentRemark = commentRemark;
    }

    public String getCommentRemark() {
        return commentRemark;
    }

    public void setDescribeTheNumber(int describeTheNumber) {
        this.describeTheNumber = describeTheNumber;
    }

    public int getDescribeTheNumber() {
        return describeTheNumber;
    }

    public void setLogisticsServices(int logisticsServices) {
        this.logisticsServices = logisticsServices;
    }

    public int getLogisticsServices() {
        return logisticsServices;
    }

    public void setTotalOrderId(String totalOrderId) {
        this.totalOrderId = totalOrderId;
    }

    public String getTotalOrderId() {
        return totalOrderId;
    }

    public List<String> getCommentImageUrl() {
        return commentImageUrl;
    }

    public void setCommentImageUrl(List<String> commentImageUrl) {
        this.commentImageUrl = commentImageUrl;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuId() {
        return skuId;
    }

    @Override
    public String toString() {
        return
                "OrderCommentListItem{" +
                        "serviceAttitude = '" + serviceAttitude + '\'' +
                        ",commentRemark = '" + commentRemark + '\'' +
                        ",describeTheNumber = '" + describeTheNumber + '\'' +
                        ",logisticsServices = '" + logisticsServices + '\'' +
                        ",totalOrderId = '" + totalOrderId + '\'' +
                        ",commentImageUrl = '" + commentImageUrl + '\'' +
                        ",spuId = '" + spuId + '\'' +
                        ",skuId = '" + skuId + '\'' +
                        "}";
    }
}

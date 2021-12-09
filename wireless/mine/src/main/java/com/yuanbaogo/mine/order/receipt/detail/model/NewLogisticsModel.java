package com.yuanbaogo.mine.order.receipt.detail.model;

public class NewLogisticsModel {
    private String imgUrl;
    private String com;
    private Long createBy;
    private String des;
    private String orderId;
    private String expressNo;
    private Long createTime;
    private Long updateTime;
    private String id;
    private String type;
    private Long scanDate;
    //每条物流数据的签收状态
    private String dataStatus;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getCom() {
        return com;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setScanDate(Long scanDate) {
        this.scanDate = scanDate;
    }

    public Long getScanDate() {
        return scanDate;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    @Override
    public String toString() {
        return "NewLogisticsModel{" +
                "imgUrl='" + imgUrl + '\'' +
                ", com='" + com + '\'' +
                ", createBy=" + createBy +
                ", des='" + des + '\'' +
                ", orderId='" + orderId + '\'' +
                ", expressNo='" + expressNo + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", scanDate=" + scanDate +
                ", dataStatus='" + dataStatus + '\'' +
                '}';
    }
}

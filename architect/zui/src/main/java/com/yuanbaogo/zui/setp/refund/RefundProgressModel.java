package com.yuanbaogo.zui.setp.refund;

public class RefundProgressModel {

    private String status;
    private String date;
    private String message;
    private RefundStatus refundStatus;


    public RefundProgressModel(String status, String date, RefundStatus refundStatus) {
        this.status = status;
        this.date = date;
        this.refundStatus = refundStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RefundStatus getOrderStatus() {
        return refundStatus;
    }

    public void setOrderStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    @Override
    public String toString() {
        return "TimeLineModel{" +
                "status='" + status + '\'' +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                ", orderStatus=" + refundStatus +
                '}';
    }
}

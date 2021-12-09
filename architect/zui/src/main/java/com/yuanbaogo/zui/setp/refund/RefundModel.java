package com.yuanbaogo.zui.setp.refund;

public class RefundModel {

    private String message;
    private RefundStatus refundStatus;


    public RefundModel(String message, RefundStatus refundStatus) {
        this.message = message;
        this.refundStatus = refundStatus;
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

    public void setRefundStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    @Override
    public String toString() {
        return "TimeLineModel{" +
                ", message='" + message + '\'' +
                ", orderStatus=" + refundStatus +
                '}';
    }
}

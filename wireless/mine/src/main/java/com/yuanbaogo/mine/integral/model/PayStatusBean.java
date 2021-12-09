
package com.yuanbaogo.mine.integral.model;


public class PayStatusBean {

    private String orderId;
    private String payTime;
    private String payTypeName;
    private String status;
//    {"orderId":null,"status":"0","payTypeName":null,"payTime":null}

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

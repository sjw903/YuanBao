package com.yuanbaogo.mine.order.after.model;

public class RefundStatusModel {
    private int code;
    private boolean isExectued;
    private String statusName;
    private String operationName;

    public RefundStatusModel setCode(int code) {
        this.code = code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public RefundStatusModel setIsExectued(boolean isExectued) {
        this.isExectued = isExectued;
        return this;
    }

    public boolean isIsExectued() {
        return isExectued;
    }

    public RefundStatusModel setStatusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

    public String getStatusName() {
        return statusName;
    }

    public RefundStatusModel setOperationName(String operationName) {
        this.operationName = operationName;
        return this;
    }

    public String getOperationName() {
        return operationName;
    }

    @Override
    public String toString() {
        return
                "RefundModel{" +
                        "code = '" + code + '\'' +
                        ",isExectued = '" + isExectued + '\'' +
                        ",statusName = '" + statusName + '\'' +
                        ",operationName = '" + operationName + '\'' +
                        "}";
    }
}

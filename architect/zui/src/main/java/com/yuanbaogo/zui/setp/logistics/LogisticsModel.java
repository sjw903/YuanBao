package com.yuanbaogo.zui.setp.logistics;

public class LogisticsModel {

    private String status;
    private String date;
    private String message;
    private LogisticsStatus logisticsStatus;


    public LogisticsModel(String status, String date, String message, LogisticsStatus logisticsStatus) {
        this.status = status;
        this.date = date;
        this.message = message;
        this.logisticsStatus = logisticsStatus;
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

    public LogisticsStatus getOrderStatus() {
        return logisticsStatus;
    }

    public void setOrderStatus(LogisticsStatus logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    @Override
    public String toString() {
        return "TimeLineModel{" +
                "status='" + status + '\'' +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                ", orderStatus=" + logisticsStatus +
                '}';
    }
}

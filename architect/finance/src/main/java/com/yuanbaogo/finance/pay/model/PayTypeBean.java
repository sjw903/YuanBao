package com.yuanbaogo.finance.pay.model;

public class PayTypeBean {

    private String payAwayCode;
    private String payAwayName;
    private String balance;
    private String money;
    private String androidIconUrl;
    private String isCombination;

    public String getAndroidIconUrl() {
        return androidIconUrl;
    }

    public void setAndroidIconUrl(String androidIconUrl) {
        this.androidIconUrl = androidIconUrl;
    }

    public String getPayAwayCode() {
        return payAwayCode;
    }

    public void setPayAwayCode(String payAwayCode) {
        this.payAwayCode = payAwayCode;
    }

    public String getPayAwayName() {
        return payAwayName;
    }

    public void setPayAwayName(String payAwayName) {
        this.payAwayName = payAwayName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getIsCombination() {
        return isCombination;
    }

    public void setIsCombination(String isCombination) {
        this.isCombination = isCombination;
    }
}

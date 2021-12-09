package com.yuanbaogo.finance.bindBankCard.model;

public class BankCardInfoBean {

//    {
//        "accountBank": "",
//            "accountType": 0,
//            "description": "",
//            "result": ""
//    }

    /**
     * 开户行
     */
    private String accountBank;
    /**
     * 卡性质：1. 借记卡；2. 贷记卡
     */
    private int accountType;
    /**
     * 业务结果描述
     */
    private String description;
    /**
     * 认证结果码
     */
    private String result;

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

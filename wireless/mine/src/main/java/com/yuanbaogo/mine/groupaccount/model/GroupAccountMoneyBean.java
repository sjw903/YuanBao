package com.yuanbaogo.mine.groupaccount.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/12/21 7:48 PM
 * @Modifier:
 * @Modify:
 */
public class GroupAccountMoneyBean {

//    {
//        "areaType": 1,
//            "amount": 0,
//            "count": 0
//    }

    private int areaType;
    private long amount;
    private int count;

    public int getAreaType() {
        return areaType;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

package com.yuanbaogo.mine.assets.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/23/21 4:20 PM
 * @Modifier:
 * @Modify:
 */
public class FindAreaAmountBean {

//  [
//      {
//          "amount": 0,
//          "areaId": 0,
//          "areaType": 0,
//          "createdBy": 0,
//          "createdTime": "",
//          "deleted": true,
//          "id": 0,
//          "updateBy": 0,
//          "updateTime": "",
//          "userId": 0,
//          "walletAccount": ""
//      }
//  ]

    private long amount;
    private String areaId;
    private int areaType;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public int getAreaType() {
        return areaType;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }
}

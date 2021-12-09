package com.yuanbaogo.mine.groupaccount.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/15/21 11:44 AM
 * @Modifier:
 * @Modify:
 */
public class RechargeNowBean {

//    [
//        {
//            "areaId": 0,
//            "areaType": 0,
//            "createBy": 0,
//            "createTime": "",
//            "id": 0,
//            "ticketAmount": 0,
//            "updateBy": 0,
//            "updateTime": "",
//            "useStatus": 0,
//            "userId": 0
//        }
//    ]

    private String areaId;
    private int areaType;//1 五百 2 五千 3 五万
    private String createBy;
    private long createTime;
    private String id;
    private long ticketAmount;
    private String updateBy;
    private String updateTime;
    private String useStatus;//1 未使用 2 使用
    private String userId;

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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(long ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

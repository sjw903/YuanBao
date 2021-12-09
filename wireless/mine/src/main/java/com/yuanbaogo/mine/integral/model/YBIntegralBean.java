package com.yuanbaogo.mine.integral.model;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/8/21 1:22 PM
 * @Modifier:
 * @Modify:
 */
public class YBIntegralBean {

//    {
//        "size":6,
//        "page":3,
//        "total":18,
//        "rows":[
//          {
//            "transactionType":"直播打赏",
//            "transactionTime":1628493560000,
//            "changeNumber":"400",
//            "direct":0
//          }
//        ]
//    }

//    {
//        "size": 0,
//        "page": 0,
//        "total": 0,
//        "rows": [
//          {
//            "id":"1422505490812858368",
//            "userId":30063,
//            "areaId":"1",
//            "areaType":1,
//            "useAmount":50000,
//            "orderId":"11111",
//            "projectName":"拼团退款",
//            "businessType":3,
//            "directType":1,
//            "createdBy":null,
//            "createdTime":1627986713000
//          }
//        ]
//    }

//    {
//        "size": 0,
//        "page": 0,
//        "total": 0,
//        "rows": [
//          {
//            "createTime": "",
//            "directType": 0,
//            "projectName": "",
//            "useAmount": 0
//          }
//        ]
//    }

    private int size;
    private int page;
    private int total;
    private List<YBIntegralListBean> rows;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<YBIntegralListBean> getRows() {
        return rows;
    }

    public void setRows(List<YBIntegralListBean> rows) {
        this.rows = rows;
    }

    public class YBIntegralListBean {

        String transactionType;
        long transactionTime;
        String changeNumber;
        int direct;//0 收   1 支

        private String areaId;
        private String areaType;
        private int businessType;//1 充值 2 购买商品 3 退款
        private String createdBy;
        private String createdTime;
        private int directType;//1 收入 2 支出
        private String id;
        private String orderId;
        private String projectName;
        private long useAmount;
        private String userId;

        private long createTime;

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public long getTransactionTime() {
            return transactionTime;
        }

        public void setTransactionTime(long transactionTime) {
            this.transactionTime = transactionTime;
        }

        public String getChangeNumber() {
            return changeNumber;
        }

        public void setChangeNumber(String changeNumber) {
            this.changeNumber = changeNumber;
        }

        public int getDirect() {
            return direct;
        }

        public void setDirect(int direct) {
            this.direct = direct;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAreaType() {
            return areaType;
        }

        public void setAreaType(String areaType) {
            this.areaType = areaType;
        }

        public int getBusinessType() {
            return businessType;
        }

        public void setBusinessType(int businessType) {
            this.businessType = businessType;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public int getDirectType() {
            return directType;
        }

        public void setDirectType(int directType) {
            this.directType = directType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public long getUseAmount() {
            return useAmount;
        }

        public void setUseAmount(long useAmount) {
            this.useAmount = useAmount;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }

}

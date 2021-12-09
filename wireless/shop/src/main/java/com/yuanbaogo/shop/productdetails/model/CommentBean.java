package com.yuanbaogo.shop.productdetails.model;

import android.util.Log;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/11/21 9:53 AM
 * @Modifier:
 * @Modify:
 */
public class CommentBean {

//    {
//        "commentList": [
//          {
//            "totalOrderId":1437594199040843776,
//            "ybCode":30022,
//            "spuId":1435848071800455168,
//            "skuId":1435848071813038081,
//            "userNickName":null,
//            "userHeadImageUrl":null,
//            "commentRemark":"可口可乐了看看考虑考虑",
//            "describeTheNumber":1,
//            "serviceAttitude":1,
//            "logisticsServices":1,
//            "commentImageUrl":null,
//            "createTime":1631616754732
//          }
//	      ],
//        "total": 0
//    }

    private int total;
    private List<CommentListBean> commentList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListBean> commentList) {
        this.commentList = commentList;
    }

    public static class CommentListBean {

        private String totalOrderId;
        private String ybCode;
        private String spuId;
        private String skuId;
        private int describeTheNumber;
        private int serviceAttitude;
        private int logisticsServices;
        private String[] commentImageUrl;
        private Long createTime;
        private String userNickName;
        private String userHeadImageUrl;
        private String commentRemark;

        public String getTotalOrderId() {
            return totalOrderId;
        }

        public void setTotalOrderId(String totalOrderId) {
            this.totalOrderId = totalOrderId;
        }

        public String getYbCode() {
            return ybCode;
        }

        public void setYbCode(String ybCode) {
            this.ybCode = ybCode;
        }

        public String getSpuId() {
            return spuId;
        }

        public void setSpuId(String spuId) {
            this.spuId = spuId;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public int getDescribeTheNumber() {
            return describeTheNumber;
        }

        public void setDescribeTheNumber(int describeTheNumber) {
            this.describeTheNumber = describeTheNumber;
        }

        public int getServiceAttitude() {
            return serviceAttitude;
        }

        public void setServiceAttitude(int serviceAttitude) {
            this.serviceAttitude = serviceAttitude;
        }

        public int getLogisticsServices() {
            return logisticsServices;
        }

        public void setLogisticsServices(int logisticsServices) {
            this.logisticsServices = logisticsServices;
        }

        public String[] getCommentImageUrl() {
            return commentImageUrl;
        }

        public void setCommentImageUrl(String[] commentImageUrl) {
            this.commentImageUrl = commentImageUrl;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public String getUserHeadImageUrl() {
            return userHeadImageUrl;
        }

        public void setUserHeadImageUrl(String userHeadImageUrl) {
            this.userHeadImageUrl = userHeadImageUrl;
        }

        public String getCommentRemark() {
            return commentRemark;
        }

        public void setCommentRemark(String commentRemark) {
            this.commentRemark = commentRemark;
        }
    }

}

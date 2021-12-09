package com.yuanbaogo.shop.joingroup.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/6/21 12:58 PM
 * @Modifier:
 * @Modify:
 */
public class LuckFightParamsBean {

//  {
//      "activityId": 0,
//      "page": {
//          "page": 0,
//          "size": 0
//      },
//      "timeFlag"：0
//  }

    private String activityId;
    private LuckFightParameteReqBean pageReq;
    //0 今日开奖 1 抢先预约
    private int timeFlag;

    public String getActivityId() {
        return activityId;
    }

    public LuckFightParamsBean setActivityId(String activityId) {
        this.activityId = activityId;
        return this;
    }

    public LuckFightParameteReqBean getPageReq() {
        return pageReq;
    }

    public LuckFightParamsBean setPageReq(LuckFightParameteReqBean pageReq) {
        this.pageReq = pageReq;
        return this;
    }

    public static class LuckFightParameteReqBean {

        private int page;
        private int size;

        public int getPage() {
            return page;
        }

        public LuckFightParameteReqBean setPage(int page) {
            this.page = page;
            return this;
        }

        public int getSize() {
            return size;
        }

        public LuckFightParameteReqBean setSize(int size) {
            this.size = size;
            return this;
        }
    }

    public int getTimeFlag() {
        return timeFlag;
    }

    public LuckFightParamsBean setTimeFlag(int timeFlag) {
        this.timeFlag = timeFlag;
        return this;
    }
}

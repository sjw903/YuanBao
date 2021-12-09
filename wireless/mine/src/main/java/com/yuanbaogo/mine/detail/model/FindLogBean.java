package com.yuanbaogo.mine.detail.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/23/21 3:27 PM
 * @Modifier:
 * @Modify:
 */
public class FindLogBean {

//  {
//      "areaType": 0,
//      "pageReq": {
//          "page": 0,
//          "size": 0
//      }
//  }

    private int areaType;
    private FindLogReqBean pageReq;

    public int getAreaType() {
        return areaType;
    }

    public FindLogBean setAreaType(int areaType) {
        this.areaType = areaType;
        return this;
    }

    public FindLogReqBean getPageReq() {
        return pageReq;
    }

    public FindLogBean setPageReq(FindLogReqBean pageReq) {
        this.pageReq = pageReq;
        return this;
    }

    public static class FindLogReqBean {

        private int page;
        private int size;

        public int getPage() {
            return page;
        }

        public FindLogReqBean setPage(int page) {
            this.page = page;
            return this;
        }

        public int getSize() {
            return size;
        }

        public FindLogReqBean setSize(int size) {
            this.size = size;
            return this;
        }
    }

}

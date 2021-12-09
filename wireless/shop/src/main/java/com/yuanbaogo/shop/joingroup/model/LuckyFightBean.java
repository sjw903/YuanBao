package com.yuanbaogo.shop.joingroup.model;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/27/21 1:29 PM
 * @Modifier:
 * @Modify:
 */
public class LuckyFightBean {

//  {
//      "size":10,
//      "page":1,
//      "rows":[
//          {
//              "id":1434418335100821504,
//              "lotteryTime":1630944000000,
//              "countdownTime":38650012,
//              "spuId":1427460990766186496,
//              "coverImages":"https://ybsjds-oss-shanghai.oss-cn-shanghai.aliyuncs.com/material/picture/mall-management/M40K888OR0/mall-management-picture/M40K888OR0.png?1629168192",
//              "mainImages":["https://ybsjds-oss-shanghai.oss-cn-shanghai.aliyuncs.com/material/picture/mall-management/M40K888OR0/mall-management-picture/M40K888OR0.png?1629168192"],
//              "goodsName":"测试丫丫丫测试丫丫丫",
//              "brandDTO":null,
//              "groupGoodsPrice":100,
//              "currentNumber":5,
//              "numberOfWinners":25,
//              "limitNumber":5,
//              "actiivitiesStatus":"2"
//          }
//      ],
//      "total":1
//  }

    private int size;
    private int page;
    private int total;
    private List<LuckyFightRowsBean> rows;

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

    public List<LuckyFightRowsBean> getRows() {
        return rows;
    }

    public void setRows(List<LuckyFightRowsBean> rows) {
        this.rows = rows;
    }

    public class LuckyFightRowsBean {

        /**
         * 大抽奖ID
         */
        private String id;
        /**
         * 开奖时间
         */
        private Long lotteryTime;
        /**
         * 剩余时间
         */
        private Long countdownTime;
        /**
         *
         */
        private String spuId;
        /**
         * 商品封面URL
         */
        private String coverImages;
        /**
         * 商品主图URL列表
         */
        private String[] mainImages;
        /**
         * 商品名称
         */
        private String goodsName;
        /**
         * 品牌信息
         */
        private String brandDTO;
        /**
         * 拼团价格
         */
        private Long groupGoodsPrice;
        /**
         * 当前人数
         */
        private int currentNumber;
        /**
         * 中奖人数
         */
        private int numberOfWinners;
        /**
         * 参团人数限制
         */
        private int limitNumber;
        /**
         * 活动状态（1：未开始、2：进行中、3：已结束）
         */
        private int actiivitiesStatus;
        /**
         * 参加状态（True：已参加、False：未参加）
         */
        private Boolean participated;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Long getLotteryTime() {
            return lotteryTime;
        }

        public void setLotteryTime(Long lotteryTime) {
            this.lotteryTime = lotteryTime;
        }

        public Long getCountdownTime() {
            return countdownTime;
        }

        public void setCountdownTime(Long countdownTime) {
            this.countdownTime = countdownTime;
        }

        public String getSpuId() {
            return spuId;
        }

        public void setSpuId(String spuId) {
            this.spuId = spuId;
        }

        public String getCoverImages() {
            return coverImages;
        }

        public void setCoverImages(String coverImages) {
            this.coverImages = coverImages;
        }

        public String[] getMainImages() {
            return mainImages;
        }

        public void setMainImages(String[] mainImages) {
            this.mainImages = mainImages;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getBrandDTO() {
            return brandDTO;
        }

        public void setBrandDTO(String brandDTO) {
            this.brandDTO = brandDTO;
        }

        public Long getGroupGoodsPrice() {
            return groupGoodsPrice;
        }

        public void setGroupGoodsPrice(Long groupGoodsPrice) {
            this.groupGoodsPrice = groupGoodsPrice;
        }

        public int getCurrentNumber() {
            return currentNumber;
        }

        public void setCurrentNumber(int currentNumber) {
            this.currentNumber = currentNumber;
        }

        public int getNumberOfWinners() {
            return numberOfWinners;
        }

        public void setNumberOfWinners(int numberOfWinners) {
            this.numberOfWinners = numberOfWinners;
        }

        public int getLimitNumber() {
            return limitNumber;
        }

        public void setLimitNumber(int limitNumber) {
            this.limitNumber = limitNumber;
        }

        public int getActiivitiesStatus() {
            return actiivitiesStatus;
        }

        public void setActiivitiesStatus(int actiivitiesStatus) {
            this.actiivitiesStatus = actiivitiesStatus;
        }

        public Boolean getParticipated() {
            return participated;
        }

        public void setParticipated(Boolean participated) {
            this.participated = participated;
        }
    }

}

package com.yuanbaogo.shop.joingroup.model;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/6/21 2:22 PM
 * @Modifier:
 * @Modify:
 */
public class LuckyFighInfoBean {

//  {
//      "id":1434418335100821504,
//      "lotteryTime":1630944000000,
//      "countdownTime":34540646,
//      "spuId":1427460990766186496,
//      "coverImages":"https://ybsjds-oss-shanghai.oss-cn-shanghai.aliyuncs.com/material/picture/mall-management/M40K888OR0/mall-management-picture/M40K888OR0.png?1629168192",
//      "mainImages":["https://ybsjds-oss-shanghai.oss-cn-shanghai.aliyuncs.com/material/picture/mall-management/M40K888OR0/mall-management-picture/M40K888OR0.png?1629168192"],
//      "goodsName":"测试丫丫丫测试丫丫丫",
//      "brandDTO":null,
//      "groupGoodsPrice":100,
//      "currentNumber":5,
//      "numberOfWinners":25,
//      "limitNumber":5,
//      "actiivitiesStatus":"2",
//      "skuId":1427460990782963712,
//      "skuIndexesName":"2",
//      "specList":[
//          {
//              "id":1427460990774575104,
//              "spuId":1427460990766186496,
//              "specId":1427460556689276928,
//              "valueAggList":[
//                  {
//                      "id":1427460990774575105,
//                      "spuId":1427460990766186496,
//                      "specId":1427460990774575104,
//                      "relationId":null,
//                      "specValue":"2",
//                      "imageUrl":null,
//                      "specIndex":0,
//                      "isGeneralSpec":false
//                  }
//              ],
//              "specName":"尺码",
//              "isGeneralSpec":false,
//              "required":false,
//              "numerical":false,
//              "hasCustomSpec":false,
//              "hasImage":false
//          }
//      ],
//      "description":""
//  }

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
     *
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
     *
     */
    private String skuId;
    /**
     * sku规格字符串
     */
    private String skuIndexesName;
    /**
     * 规格列表
     */
    private List<LuckyFighInfoSpecBean> specList;
    /**
     * 商品详情
     */
    private String description;
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

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuIndexesName() {
        return skuIndexesName;
    }

    public void setSkuIndexesName(String skuIndexesName) {
        this.skuIndexesName = skuIndexesName;
    }

    public List<LuckyFighInfoSpecBean> getSpecList() {
        return specList;
    }

    public void setSpecList(List<LuckyFighInfoSpecBean> specList) {
        this.specList = specList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getParticipated() {
        return participated;
    }

    public void setParticipated(Boolean participated) {
        this.participated = participated;
    }

    public class LuckyFighInfoSpecBean {

        /**
         * 主键
         */
        private String id;
        /**
         *
         */
        private String spuId;
        /**
         * 规格参数ID
         */
        private String specId;
        /**
         * 规格参数值列表
         */
        private List<LuckyFighInfoSpecAggBean> valueAggList;
        /**
         * 规格参数名称
         */
        private String specName;
        /**
         * 是否为通用参数; true:通用参数，false:非通用参数
         */
        private boolean isGeneralSpec;
        /**
         * 是否必填; true:必填项，false:非必填
         */
        private boolean required;
        /**
         * 是否为数值类型; true:数值类型，false:非数值类型
         */
        private boolean numerical;
        /**
         * 是否存在自定义属性; true:存在自定义属性，false:不存在自定义属性
         */
        private boolean hasCustomSpec;
        /**
         * 是否存在展示图; true:存在展示图，false:不存在展示图
         */
        private boolean hasImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSpuId() {
            return spuId;
        }

        public void setSpuId(String spuId) {
            this.spuId = spuId;
        }

        public String getSpecId() {
            return specId;
        }

        public void setSpecId(String specId) {
            this.specId = specId;
        }

        public List<LuckyFighInfoSpecAggBean> getValueAggList() {
            return valueAggList;
        }

        public void setValueAggList(List<LuckyFighInfoSpecAggBean> valueAggList) {
            this.valueAggList = valueAggList;
        }

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }

        public boolean isGeneralSpec() {
            return isGeneralSpec;
        }

        public void setGeneralSpec(boolean generalSpec) {
            isGeneralSpec = generalSpec;
        }

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public boolean isNumerical() {
            return numerical;
        }

        public void setNumerical(boolean numerical) {
            this.numerical = numerical;
        }

        public boolean isHasCustomSpec() {
            return hasCustomSpec;
        }

        public void setHasCustomSpec(boolean hasCustomSpec) {
            this.hasCustomSpec = hasCustomSpec;
        }

        public boolean isHasImage() {
            return hasImage;
        }

        public void setHasImage(boolean hasImage) {
            this.hasImage = hasImage;
        }

        public class LuckyFighInfoSpecAggBean {

            /**
             * 主键
             */
            private String id;
            /**
             *
             */
            private String spuId;
            /**
             * 规格参数ID
             */
            private String specId;
            /**
             * 关系id
             */
            private String relationId;
            /**
             * 规格参数值
             */
            private String specValue;
            /**
             * 展示图
             */
            private String imageUrl;
            /**
             * 对于规格角标
             */
            private int specIndex;
            /**
             * 是否为通用参数; true:通用参数，false:非通用参数
             */
            private boolean isGeneralSpec;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSpuId() {
                return spuId;
            }

            public void setSpuId(String spuId) {
                this.spuId = spuId;
            }

            public String getSpecId() {
                return specId;
            }

            public void setSpecId(String specId) {
                this.specId = specId;
            }

            public String getRelationId() {
                return relationId;
            }

            public void setRelationId(String relationId) {
                this.relationId = relationId;
            }

            public String getSpecValue() {
                return specValue;
            }

            public void setSpecValue(String specValue) {
                this.specValue = specValue;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public int getSpecIndex() {
                return specIndex;
            }

            public void setSpecIndex(int specIndex) {
                this.specIndex = specIndex;
            }

            public boolean isGeneralSpec() {
                return isGeneralSpec;
            }

            public void setGeneralSpec(boolean generalSpec) {
                isGeneralSpec = generalSpec;
            }
        }

    }

}

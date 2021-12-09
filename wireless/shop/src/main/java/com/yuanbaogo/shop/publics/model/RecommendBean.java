package com.yuanbaogo.shop.publics.model;

import java.util.List;

/**
 * @Description: 推荐商品
 * @Params: /goods/search/recommend
 * @Problem:
 * @Author: HG
 * @Date: 8/6/21 1:36 PM
 * @Modifier:
 * @Modify:
 */
public class RecommendBean {

//            [
//              {
//                "spuId": 1,
//                "categoryOneName": "一级分类1",
//                "categoryTwoName": "二级分类1",
//                "categoryThreeName": "三级分类1",
//                "cargoName": "傻货1",
//                "goodsName": "球鞋1",
//                "specificationAgg":[
//                  {
//                      "id":1425648314679328768,
//                      "spuId":1425647363516039168,
//                      "specId":1425646828721307648,
//                      "valueAggList":[
//                        {
//                            "id":1425648314679328769,
//                            "spuId":1425647363516039168,
//                            "specId":1425648314679328768,
//                            "relationId":null,
//                            "specValue":"黑色",
//                            "imageUrl":null,
//                            "specIndex":6,
//                            "isGeneralSpec":false
//                        }
//                      ],
//                      "specName":"颜色",
//                      "isGeneralSpec":false,
//                      "required":false,
//                      "numerical":false,
//                      "hasCustomSpec":false,
//                      "hasImage":false
//                  },
//                  {
//                      "id":1425648314679328772,
//                      "spuId":1425647363516039168,
//                      "specId":1425646854264619008,
//                      "valueAggList":[
//                        {
//                            "id":1425648314679328773,
//                            "spuId":1425647363516039168,
//                            "specId":1425648314679328772,
//                            "relationId":null,
//                            "specValue":"100cm",
//                            "imageUrl":null,
//                            "specIndex":9,
//                            "isGeneralSpec":false
//                        }
//                      ],
//                      "specName":"大小",
//                      "isGeneralSpec":false,
//                      "required":false,
//                      "numerical":false,
//                      "hasCustomSpec":false,
//                      "hasImage":false
//                  }
//                ],
//                "brandName": "大品牌1",
//                "englishName": "EnglishName1",
//                "logoUrl": "http://localhost/",
//                "brandDescription": "这是个品牌的描述",
//                "keywordList": null,
//                "minSalePrice": 100000,
//                "minCostPrice": 10,
//                "minLinePrice": 100,
//                "totalSales": 150,
//                "mainImage": ["http://localhost/1","http://localhost/1","http://localhost/1"],
//                "coverImages": "http://localhost/",
//                "description": "这是个商品的描述",
//                "shelfStatus": true,
//                "recycleStatus": true
//              }
//            ]

    private String spuId;
    private String categoryOneName;
    private String categoryTwoName;
    private String categoryThreeName;
    private String cargoName;
    private String goodsName;
    private List<RecommendAGGBean> specificationAgg;
    private String brandName;
    private String englishName;
    private String logoUrl;
    private String brandDescription;
    private String[] keywordList;
    private long minSalePrice;
    private long minCostPrice;
    private long minLinePrice;
    private int totalSales;
    private String[] mainImage;
    private String coverImages;
    private String description;
    private boolean shelfStatus;
    private boolean recycleStatus;

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getCategoryOneName() {
        return categoryOneName;
    }

    public void setCategoryOneName(String categoryOneName) {
        this.categoryOneName = categoryOneName;
    }

    public String getCategoryTwoName() {
        return categoryTwoName;
    }

    public void setCategoryTwoName(String categoryTwoName) {
        this.categoryTwoName = categoryTwoName;
    }

    public String getCategoryThreeName() {
        return categoryThreeName;
    }

    public void setCategoryThreeName(String categoryThreeName) {
        this.categoryThreeName = categoryThreeName;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public List<RecommendAGGBean> getSpecificationAgg() {
        return specificationAgg;
    }

    public void setSpecificationAgg(List<RecommendAGGBean> specificationAgg) {
        this.specificationAgg = specificationAgg;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBrandDescription() {
        return brandDescription;
    }

    public void setBrandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
    }

    public String[] getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(String[] keywordList) {
        this.keywordList = keywordList;
    }

    public long getMinSalePrice() {
        return minSalePrice;
    }

    public void setMinSalePrice(long minSalePrice) {
        this.minSalePrice = minSalePrice;
    }

    public long getMinCostPrice() {
        return minCostPrice;
    }

    public void setMinCostPrice(long minCostPrice) {
        this.minCostPrice = minCostPrice;
    }

    public long getMinLinePrice() {
        return minLinePrice;
    }

    public void setMinLinePrice(long minLinePrice) {
        this.minLinePrice = minLinePrice;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public String[] getMainImage() {
        return mainImage;
    }

    public void setMainImage(String[] mainImage) {
        this.mainImage = mainImage;
    }

    public String getCoverImages() {
        return coverImages;
    }

    public void setCoverImages(String coverImages) {
        this.coverImages = coverImages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isShelfStatus() {
        return shelfStatus;
    }

    public void setShelfStatus(boolean shelfStatus) {
        this.shelfStatus = shelfStatus;
    }

    public boolean isRecycleStatus() {
        return recycleStatus;
    }

    public void setRecycleStatus(boolean recycleStatus) {
        this.recycleStatus = recycleStatus;
    }

    public class RecommendAGGBean {

        private String id;
        private String spuId;
        private String specId;
        private List<RecommendAGGListBean> valueAggList;
        private String specName;
        private boolean isGeneralSpec;
        private boolean required;
        private boolean numerical;
        private boolean hasCustomSpec;
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

        public List<RecommendAGGListBean> getValueAggList() {
            return valueAggList;
        }

        public void setValueAggList(List<RecommendAGGListBean> valueAggList) {
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

        public class RecommendAGGListBean {

            private String id;
            private String spuId;
            private String specId;
            private String relationId;
            private String specValue;
            private String imageUrl;
            private int specIndex;
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

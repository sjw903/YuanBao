package com.yuanbaogo.shop.publics.model;

import java.util.List;

/**
 * @Description: 商品信息Model
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/31/21 7:06 PM
 * @Modifier:
 * @Modify:
 */
public class SearchMerchandiseRowsBean {

//      "spuId":"1427461453825736704",
//      "specialId":3,
//      "specialName":"一城一品-首推爆款",
//      "specialType":8,
//      "categoryOneName":"女装/女士精品",
//      "categoryTwoName":"裤子",
//      "categoryThreeName":"休闲裤",
//      "cargoName":"的点点滴滴的",
//      "goodsName":"的点点滴滴的",
//      "specificationAgg":[
//          {
//            "id":1427595101858201600,
//            "spuId":1427461453825736704,
//            "specId":1427460556689276928,
//            "valueAggList":[
//              {
//                  "id":1427595101858201601,
//                  "spuId":1427461453825736704,
//                  "specId":1427595101858201600,
//                  "relationId":null,
//                  "specValue":"和",
//                  "imageUrl":"https://ybsjds-oss-shanghai.oss-cn-shanghai.aliyuncs.com/material/picture/mall-management/SH49EMHWRL/mall-management-picture/SH49EMHWRL.png?1629200163",
//                  "specIndex":4,
//                  "isGeneralSpec":false
//              }
//            ],
//            "specName":"尺码",
//            "isGeneralSpec":false,
//            "required":false,
//            "numerical":false,
//            "hasCustomSpec":false,
//            "hasImage":true
//          }
//      ],
//      "brandName":"李宁",
//      "englishName":"",
//      "logoUrl":"https://ybsjds-oss-shanghai.oss-cn-shanghai.aliyuncs.com/material/picture/mall-management/NM1LK6B14Q/mall-management-picture/NM1LK6B14Q.jpeg?1629163927",
//      "brandDescription":null,
//      "keywordList":[],
//      "minSalePrice":100,
//      "minCostPrice":100,
//      "minLinePrice":100,
//      "totalStock":1,
//      "totalSales":0,
//      "mainImages":[],
//      "coverImages":"https://ybsjds-oss-shanghai.oss-cn-shanghai.aliyuncs.com/material/picture/mall-management/XXPC1FP0WB/mall-management-picture/XXPC1FP0WB.jpg?1629198429",
//      "description":null,
//      "shelfStatus":true,
//      "recycleStatus":false

    //商品ID
    private Long spuId;
    //专区ID
    private Long specialId;
    //专区名称
    private String specialName;
    //所属专区 0：普通商品、1：500专区商品、2：5000专区商品、3：50000专区商品、4：一城一品、5：秒杀商品、6：失效商品、7：主题专区商品
    private int specialType;
    //一级分类名称
    private String categoryOneName;
    //二级分类名称
    private String categoryTwoName;
    //三级分类名称
    private String categoryThreeName;
    //货品名称
    private String cargoName;
    //商品名称
    private String goodsName;
    //规格参数列表
    private List<SearchMerchandiseAgg> specificationAgg;
    //品牌名
    private String brandName;
    //品牌英文名
    private String englishName;
    //品牌Logo
    private String logoUrl;
    //品牌描述
    private String brandDescription;
    //关键字（标签）列表
    private String[] keywordList;
    //最低销售价格
    private double minSalePrice;
    //最低成本价
    private double minCostPrice;
    //最低划线价
    private double minLinePrice;
    //库存总和
    private String totalStock;
    //总销量
    private String totalSales;
    //商品主图URL列表
    private String[] mainImages;
    //商品封面URL
    private String coverImages;
    //商品描述
    private String description;
    //上架状态 true：立即上架售卖  false：暂不售卖，放入仓库
    private boolean shelfStatus;
    //是否在回收站 true：在回收站  false：不在回收站
    private boolean recycleStatus;

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Long getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Long specialId) {
        this.specialId = specialId;
    }

    public String getSpecialName() {
        return specialName;
    }

    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    public int getSpecialType() {
        return specialType;
    }

    public void setSpecialType(int specialType) {
        this.specialType = specialType;
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

    public List<SearchMerchandiseAgg> getSpecificationAgg() {
        return specificationAgg;
    }

    public void setSpecificationAgg(List<SearchMerchandiseAgg> specificationAgg) {
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

    public double getMinSalePrice() {
        return minSalePrice;
    }

    public void setMinSalePrice(double minSalePrice) {
        this.minSalePrice = minSalePrice;
    }

    public double getMinCostPrice() {
        return minCostPrice;
    }

    public void setMinCostPrice(double minCostPrice) {
        this.minCostPrice = minCostPrice;
    }

    public double getMinLinePrice() {
        return minLinePrice;
    }

    public void setMinLinePrice(double minLinePrice) {
        this.minLinePrice = minLinePrice;
    }

    public String getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(String totalStock) {
        this.totalStock = totalStock;
    }

    public String getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(String totalSales) {
        this.totalSales = totalSales;
    }

    public String[] getMainImages() {
        return mainImages;
    }

    public void setMainImages(String[] mainImages) {
        this.mainImages = mainImages;
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

    public class SearchMerchandiseAgg {

        //主键
        private Long id;
        //spuID
        private Long spuId;
        //规格参数ID
        private Long specId;
        //规格参数值列表
        private List<SearchMerchandiseAggList> valueAggList;
        //规格参数名称
        private String specName;
        //是否为通用参数 true：通用参数  false：非通用参数
        private boolean isGeneralSpec;
        //是否必填 true：必填项  false：非必填
        private boolean required;
        //是否为数值类型 true：数值类型  false：非数值类型
        private boolean numerical;
        //是否存在自定义属性 true：存在自定义属性  false：不存在自定义属性
        private boolean hasCustomSpec;
        //是否存在展示图 true：存在展示图  false：不存在展示图
        private boolean hasImage;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getSpuId() {
            return spuId;
        }

        public void setSpuId(Long spuId) {
            this.spuId = spuId;
        }

        public Long getSpecId() {
            return specId;
        }

        public void setSpecId(Long specId) {
            this.specId = specId;
        }

        public List<SearchMerchandiseAggList> getValueAggList() {
            return valueAggList;
        }

        public void setValueAggList(List<SearchMerchandiseAggList> valueAggList) {
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

        public class SearchMerchandiseAggList {

            //主键
            private Long id;
            //spuID
            private Long spuId;
            //规格参数ID
            private Long specId;
            //关系id
            private Long relationId;
            //规格参数值
            private String specValue;
            //展示图
            private String imageUrl;
            //对于规格角标
            private int specIndex;
            //是否为通用参数 true：通用参数  false：非通用参数
            private boolean isGeneralSpec;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Long getSpuId() {
                return spuId;
            }

            public void setSpuId(Long spuId) {
                this.spuId = spuId;
            }

            public Long getSpecId() {
                return specId;
            }

            public void setSpecId(Long specId) {
                this.specId = specId;
            }

            public Long getRelationId() {
                return relationId;
            }

            public void setRelationId(Long relationId) {
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

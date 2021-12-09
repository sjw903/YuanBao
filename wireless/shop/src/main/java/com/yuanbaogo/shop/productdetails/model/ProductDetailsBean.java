package com.yuanbaogo.shop.productdetails.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/12/21 5:03 PM
 * @Modifier:
 * @Modify:
 */
public class ProductDetailsBean {

//    {
//        "spuId": "1425647363516039168",
//        "cargoName": "玄冰400",
//        "specialId": 0,
//        "specialName": "未分类商品",
//        "specialType": 0,
//        "goodsName": "玄冰400+",
//        "specificationAgg":[
//             {
//                  "id":1426063805625434112,
//                  "spuId":1426063685198577664,
//                  "specId":1425648119379951616,
//                  "valueAggList":[
//                       {
//                            "id":1426063805625434113,
//                            "spuId":1426063685198577664,
//                            "specId":1426063805625434112,
//                            "relationId":null,
//                            "specValue":"啊",
//                            "imageUrl":"",
//                            "specIndex":0,
//                            "isGeneralSpec":true
//                       }
//                  ],
//                  "specName":"cpu插孔",
//                  "isGeneralSpec":true,
//                  "required":false,
//                  "numerical":false,
//                  "hasCustomSpec":false,
//                  "hasImage":false
//             }
//        ],
//        "brandName":"大苏打",
//        "englishName":"发放的是否石帆胜丰石帆胜丰士大夫首发式发放的是否石帆胜丰石帆胜丰士大夫首发式发放的是否石帆胜丰石帆胜丰士大夫首发式发放的是否石帆胜丰石帆胜丰士大夫首发式发放的是否石帆胜丰石帆胜丰士大夫首发式发放的是否石帆胜丰石帆胜丰士大夫首发式发放的是否石",
//        "logoUrl":"",
//        "keywordList":Array[1],
//        "minSalePrice":100,
//        "minLinePrice":100,
//        "totalStock":5,
//        "totalSales":0,
//        "mainImage":Array[0],
//        "coverImages":""
//    }

    private String spuId;
    private String cargoName;
    private String specialId;
    private String specialName;
    private int specialType;
    private String areaTypeName;
    private String goodsName;
    private List<ProductDetailsAggBean> specificationAgg;
    private String brandName;
    private String englishName;
    private String logoUrl;
    private String[] keywordList;
    private long minSalePrice;
    private long minLinePrice;
    private int totalStock;
    private int totalSales;
    private String[] mainImages;
    private String coverImages;

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getSpecialId() {
        return specialId;
    }

    public void setSpecialId(String specialId) {
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

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getAreaTypeName() {
        return areaTypeName;
    }

    public void setAreaTypeName(String areaTypeName) {
        this.areaTypeName = areaTypeName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public List<ProductDetailsAggBean> getSpecificationAgg() {
        return specificationAgg;
    }

    public void setSpecificationAgg(List<ProductDetailsAggBean> specificationAgg) {
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

    public long getMinLinePrice() {
        return minLinePrice;
    }

    public void setMinLinePrice(long minLinePrice) {
        this.minLinePrice = minLinePrice;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
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

    public class ProductDetailsAggBean {

        private String id;
        private String spuId;
        private String specId;
        private List<ProductDetailsAggListBean> valueAggList;
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

        public List<ProductDetailsAggListBean> getValueAggList() {
            return valueAggList;
        }

        public void setValueAggList(List<ProductDetailsAggListBean> valueAggList) {
            this.valueAggList = valueAggList;
        }

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }

        public boolean getIsGeneralSpec() {
            return isGeneralSpec;
        }

        public void setIsGeneralSpec(boolean isGeneralSpec) {
            this.isGeneralSpec = isGeneralSpec;
        }

        public boolean getRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public boolean getNumerical() {
            return numerical;
        }

        public void setNumerical(boolean numerical) {
            this.numerical = numerical;
        }

        public boolean getHasCustomSpec() {
            return hasCustomSpec;
        }

        public void setHasCustomSpec(boolean hasCustomSpec) {
            this.hasCustomSpec = hasCustomSpec;
        }

        public boolean getHasImage() {
            return hasImage;
        }

        public void setHasImage(boolean hasImage) {
            this.hasImage = hasImage;
        }

        public class ProductDetailsAggListBean {

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

            public boolean getIsGeneralSpec() {
                return isGeneralSpec;
            }

            public void setIsGeneralSpec(boolean isGeneralSpec) {
                this.isGeneralSpec = isGeneralSpec;
            }
        }

    }

    /**
     * 返回规格
     *
     * @return
     */
    public List<ProductDetailsAggBean> getSpecification() {
        return getSpec(Boolean.FALSE);
    }

    /**
     * 返回参数
     *
     * @return
     */
    public List<ProductDetailsAggBean> getParameter() {
        return getSpec(Boolean.TRUE);
    }

    /**
     * 返回展示规格
     *
     * @return
     */
    public ProductDetailsAggBean getRevealParameter(Boolean flag) {
        List<ProductDetailsAggBean> parameter;
        if (flag) {
            parameter = getParameter();
        } else {
            parameter = getSpecification();
        }

        if (parameter.size() == 0) {
            return null;
        }
        if (parameter.stream().anyMatch(ProductDetailsAggBean::getHasImage)) {
            Optional<ProductDetailsAggBean> first = parameter.stream()
                    .filter(ProductDetailsAggBean::getHasImage)
                    .findFirst();
            if (first.isPresent()) {
                ProductDetailsAggBean aggBean = first.get();
                return aggBean;
            }
        }
        return parameter.get(0);
    }

    public List<ProductDetailsAggBean> getSpec(Boolean flag) {
        if (specificationAgg == null) {
            return Collections.emptyList();
        }
        Map<Boolean, List<ProductDetailsAggBean>> listMap = specificationAgg.stream()
                .collect(Collectors.groupingBy(ProductDetailsAggBean::getIsGeneralSpec));
        return listMap.get(flag);
    }

}

package com.yuanbaogo.zui.dialog.model;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/17/21 10:35 AM
 * @Modifier:
 * @Modify:
 */
public class SkuBean {

//    {
//        "specList":[
//              {
//                  "specName":"颜色",
//                  "specValue":[
//                      {
//                          "value":"黑色",
//                          "specImageUrl":null,
//                          "index":6
//                      }
//                  ],
//                  "specName":"大小",
//                  "specValue":[
//                      {
//                          "value":"100cm",
//                          "specImageUrl":null,
//                          "index":9
//                      }
//                  ]
//              }
//        ],
//        "skuList":[
//              {
//                  "spuId":"1425647363516039168",
//                  "skuId":"1425647363696394240",
//                  "indexes":"6_9",
//                  "skuIndexesName":[ "黑色","100cm"],
//                  "salePrice":10000,
//                  "linePrice":30000
//              }
//        ]
//    }

    //默认价格
    private long price;
    //默认商品头像
    private String imgUrl;
    //总库存
    private int stock;
    //购物车添加 - 规格名称
    private String skuName;
    //购物车添加 - 商品数量
    private int goodsCount;

    private List<SkuSpecBean> specList;
    private List<SkuListBean> skuList;

    public long getPrice() {
        return price;
    }

    public SkuBean setPrice(long price) {
        this.price = price;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public SkuBean setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public SkuBean setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public String getSkuName() {
        return skuName;
    }

    public SkuBean setSkuName(String skuName) {
        this.skuName = skuName;
        return this;
    }


    public int getGoodsCount() {
        return goodsCount;
    }

    public SkuBean setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
        return this;
    }

    public List<SkuSpecBean> getSpecList() {
        return specList;
    }

    public void setSpecList(List<SkuSpecBean> specList) {
        this.specList = specList;
    }

    public List<SkuListBean> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SkuListBean> skuList) {
        this.skuList = skuList;
    }

    public class SkuSpecBean {

        private String specName;
        private List<SkuSpecValueBean> specValue;

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }

        public List<SkuSpecValueBean> getSpecValue() {
            return specValue;
        }

        public void setSpecValue(List<SkuSpecValueBean> specValue) {
            this.specValue = specValue;
        }

        public class SkuSpecValueBean {

            private String value;
            private String specImageUrl;
            private int index;
            private boolean isSelected = false;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getSpecImageUrl() {
                return specImageUrl;
            }

            public void setSpecImageUrl(String specImageUrl) {
                this.specImageUrl = specImageUrl;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }
        }

    }

    public class SkuListBean {

        private String spuId;
        private String skuId;
        private String indexes;
        private String[] skuIndexesName;
        private long salePrice;
        private long linePrice;

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

        public String getIndexes() {
            return indexes;
        }

        public void setIndexes(String indexes) {
            this.indexes = indexes;
        }

        public String[] getSkuIndexesName() {
            return skuIndexesName;
        }

        public void setSkuIndexesName(String[] skuIndexesName) {
            this.skuIndexesName = skuIndexesName;
        }

        public long getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(long salePrice) {
            this.salePrice = salePrice;
        }

        public long getLinePrice() {
            return linePrice;
        }

        public void setLinePrice(long linePrice) {
            this.linePrice = linePrice;
        }
    }

}

package com.yuanbaogo.shopcart.main.model;

import java.util.ArrayList;

public class ShoppingCartBean {

    /**
     * 组是否被选中
     */
    private boolean isGroupSelected;

    /**
     * 店铺ID
     */
    private String merID;

    /**
     * 店铺名称
     */
    private String merchantName;

    /**
     * 商品
     */
    private ArrayList<Goods> goods;

    public boolean isGroupSelected() {
        return isGroupSelected;
    }

    public ShoppingCartBean setGroupSelected(boolean groupSelected) {
        isGroupSelected = groupSelected;
        return this;
    }

    public String getMerID() {
        return merID;
    }

    public ShoppingCartBean setMerID(String merID) {
        this.merID = merID;
        return this;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public ShoppingCartBean setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public ArrayList<Goods> getGoods() {
        return goods;
    }

    public ShoppingCartBean setGoods(ArrayList<Goods> goods) {
        this.goods = goods;
        return this;
    }

    public static class Goods {

        /**
         * 商品信息
         */
        private ArrayList<Prefecture> prefecture;

        /**
         * 是否被选中
         */
        private boolean isChildSelected;

        public ArrayList<Prefecture> getPrefectures() {
            return prefecture;
        }

        public Goods setPrefectures(ArrayList<Prefecture> prefecture) {
            this.prefecture = prefecture;
            return this;
        }

        public boolean isChildSelected() {
            return isChildSelected;
        }

        public Goods setChildSelected(boolean childSelected) {
            isChildSelected = childSelected;
            return this;
        }

        public static class Prefecture {

            /**
             * 购物车ID
             */
            private String id;
            /**
             * 专区ID
             */
            private String areaId;
            /**
             * 商品名称
             */
            private String createGoodsName;
            /**
             * 最新商品名称
             */
            private String updateGoodsName;
            /**
             * 商品ID
             */
            private String spuId;
            /**
             * 商品规格ID
             */
            private String skuId;
            /**
             * 商品规格
             */
            private String skuName;
            /**
             * 商品宣传图片
             */
            private String goodsImgUrl;
            /**
             * 数量
             */
            private int goodsCount;
            /**
             * 价格，当前价格
             */
            private long createGoodsPrice;
            /**
             * 最新商品名称
             */
            private long updateGoodsPrice;
            /**
             * 失效原因
             */
            private String invalidReason;
            /**
             * 购物车商品类型
             */
            private int goodsType;
            /**
             * 创建时间
             */
            private String createTime;
            /**
             * 是否失效,0删除(失效),1正常
             */
            private String status;
            /**
             * 是否是编辑状态
             */
            private boolean isEditing;
            /**
             * 是否被选中
             */
            private boolean isChildSelected;
            /**
             * 临时解决方案，为了避免尾部重绘两次，增加一个虚ITEM，不显示，但是没有子项的组项，会有一条黑线，所以需要做特殊处理
             */
            private boolean isLastTempItem;

            public String getId() {
                return id;
            }

            public Prefecture setId(String id) {
                this.id = id;
                return this;
            }

            public String getAreaId() {
                return areaId;
            }

            public Prefecture setAreaId(String areaId) {
                this.areaId = areaId;
                return this;
            }

            public String getCreateGoodsName() {
                return createGoodsName;
            }

            public Prefecture setCreateGoodsName(String createGoodsName) {
                this.createGoodsName = createGoodsName;
                return this;
            }

            public String getUpdateGoodsName() {
                return updateGoodsName;
            }

            public Prefecture setUpdateGoodsName(String updateGoodsName) {
                this.updateGoodsName = updateGoodsName;
                return this;
            }

            public String getSpuId() {
                return spuId;
            }

            public Prefecture setSpuId(String spuId) {
                this.spuId = spuId;
                return this;
            }

            public String getSkuId() {
                return skuId;
            }

            public Prefecture setSkuId(String skuId) {
                this.skuId = skuId;
                return this;
            }

            public String getSkuName() {
                return skuName;
            }

            public Prefecture setSkuName(String skuName) {
                this.skuName = skuName;
                return this;
            }

            public String getGoodsImgUrl() {
                return goodsImgUrl;
            }

            public Prefecture setGoodsImgUrl(String goodsImgUrl) {
                this.goodsImgUrl = goodsImgUrl;
                return this;
            }

            public int getGoodsCount() {
                return goodsCount;
            }

            public Prefecture setGoodsCount(int goodsCount) {
                this.goodsCount = goodsCount;
                return this;
            }

            public long getCreateGoodsPrice() {
                return createGoodsPrice;
            }

            public Prefecture setCreateGoodsPrice(long createGoodsPrice) {
                this.createGoodsPrice = createGoodsPrice;
                return this;
            }

            public long getUpdateGoodsPrice() {
                return updateGoodsPrice;
            }

            public Prefecture setUpdateGoodsPrice(long updateGoodsPrice) {
                this.updateGoodsPrice = updateGoodsPrice;
                return this;
            }

            public String getInvalidReason() {
                return invalidReason;
            }

            public Prefecture setInvalidReason(String invalidReason) {
                this.invalidReason = invalidReason;
                return this;
            }

            public int getGoodsType() {
                return goodsType;
            }

            public Prefecture setGoodsType(int goodsType) {
                this.goodsType = goodsType;
                return this;
            }

            public String getCreateTime() {
                return createTime;
            }

            public Prefecture setCreateTime(String createTime) {
                this.createTime = createTime;
                return this;
            }

            public String getStatus() {
                return status;
            }

            public Prefecture setStatus(String status) {
                this.status = status;
                return this;
            }

            public boolean isEditing() {
                return isEditing;
            }

            public Prefecture setEditing(boolean editing) {
                isEditing = editing;
                return this;
            }

            public boolean isChildSelected() {
                return isChildSelected;
            }

            public Prefecture setChildSelected(boolean childSelected) {
                isChildSelected = childSelected;
                return this;
            }

            public boolean isLastTempItem() {
                return isLastTempItem;
            }

            public Prefecture setLastTempItem(boolean lastTempItem) {
                isLastTempItem = lastTempItem;
                return this;
            }
        }

    }

}

package com.yuanbaogo.homevideo.mine.model;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/7/21 6:27 PM
 * @Modifier:
 * @Modify:
 */
public class VodListBean {

//  {
//      "size":10,
//      "page":1,
//      "rows":[
//          {
//              "title":"我们的",
//              "videoId":"3701925923854349650",
//              "videoUrl":"http://1305625675.vod2.myqcloud.com/12a7bbaavodcq1305625675/13d457ca3701925923854349650/019y4806Z0oA.mp4",
//              "coverUrl":"http://1305625675.vod2.myqcloud.com/12a7bbaavodcq1305625675/13d457ca3701925923854349650/3701925923854349652.png",
//              "ybCode":"30023",
//              "createTime":1630906925682,
//              "coverThumbUrl":"https://ybsjds-oss.oss-cn-beijing.aliyuncs.com/3701925923854349652_thumb.png",
//              "portrait":"https://ybsjds-oss.oss-cn-beijing.aliyuncs.com/user/avatar/default/head2.png",
//              "coverImgHeight":533,
//              "coverImgWidth":300,
//              "userNickName":"在",
//              "hasLiked":-1,
//              "followState":-1,
//              "canDelete":-1,
//              "viewCount":0,
//              "likeCount":0,
//              "commentCount":-1,
//              "shareCount":-1,
//              "goodsId":"",
//              "goodsInfoVO":[
//				    {
//					    "brandId": 0,
//					    "brandName": "",
//					    "commissionRate": 0,
//					    "costPrice": 0,
//					    "explainStatus": 0,
//					    "goodsMoney": 0,
//					    "goodsName": "",
//					    "goodsPicture": "",
//					    "id": "",
//					    "liveScreening": 0,
//					    "lot": "",
//					    "mallGoodsId": 0,
//					    "orderCount": 0,
//					    "productStatus": "",
//					    "salePrice": 0,
//					    "sales": 0,
//					    "shoppingGoodsStatus": "",
//					    "sort": 0,
//					    "specificationId": "",
//					    "specificationName": "",
//					    "stock": 0,
//					    "viewsCount": 0
//				    }
//			    ],
//          }
//      ],
//      "total":2
//  }


    private int size;
    private int page;
    private int total;
    private List<VodListRowsBean> rows;

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

    public List<VodListRowsBean> getRows() {
        return rows;
    }

    public void setRows(List<VodListRowsBean> rows) {
        this.rows = rows;
    }

    public class VodListRowsBean {

        //标题
        private String title;
        //视频id
        private String videoId;
        //视频网址
        private String videoUrl;
        //封面url
        private String coverUrl;
        //用户id
        private String ybCode;
        //创建时间
        private Long createTime;
        //封面url
        private String coverThumbUrl;
        //肖像
        private String portrait;
        //封面img高度
        private String coverImgHeight;
        //封面img宽度
        private String coverImgWidth;
        //用户昵称
        private String userNickName;
        //是否点赞,0未点赞,1已点赞
        private int hasLiked;
        //被关注状态:0未关注,1已关注
        private int followState;
        //可删除标志,0不可删除,1可以删除
        private int canDelete;
        //播放量
        private int viewCount;
        //点赞量
        private int likeCount;
        //评论数
        private int commentCount;
        //分享量
        private int shareCount;
        //商品id
        private String goodsId;
        //
        private List<VodListRowsGoodsBean> goodsInfoVO;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public String getYbCode() {
            return ybCode;
        }

        public void setYbCode(String ybCode) {
            this.ybCode = ybCode;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public String getCoverThumbUrl() {
            return coverThumbUrl;
        }

        public void setCoverThumbUrl(String coverThumbUrl) {
            this.coverThumbUrl = coverThumbUrl;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getCoverImgHeight() {
            return coverImgHeight;
        }

        public void setCoverImgHeight(String coverImgHeight) {
            this.coverImgHeight = coverImgHeight;
        }

        public String getCoverImgWidth() {
            return coverImgWidth;
        }

        public void setCoverImgWidth(String coverImgWidth) {
            this.coverImgWidth = coverImgWidth;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public int getHasLiked() {
            return hasLiked;
        }

        public void setHasLiked(int hasLiked) {
            this.hasLiked = hasLiked;
        }

        public int getFollowState() {
            return followState;
        }

        public void setFollowState(int followState) {
            this.followState = followState;
        }

        public int getCanDelete() {
            return canDelete;
        }

        public void setCanDelete(int canDelete) {
            this.canDelete = canDelete;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getShareCount() {
            return shareCount;
        }

        public void setShareCount(int shareCount) {
            this.shareCount = shareCount;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public List<VodListRowsGoodsBean> getGoodsInfoVO() {
            return goodsInfoVO;
        }

        public void setGoodsInfoVO(List<VodListRowsGoodsBean> goodsInfoVO) {
            this.goodsInfoVO = goodsInfoVO;
        }

        public class VodListRowsGoodsBean {

            //品牌id
            private String brandId;
            //品牌名称
            private String brandName;
            //分佣比例
            private String commissionRate;
            //成本价
            private Long costPrice;
            //讲解状态
            private int explainStatus;
            //商品金额
            private Long goodsMoney;
            //商品名称
            private String goodsName;
            //商品图片
            private String goodsPicture;
            //直播商品Id
            private String id;
            //直播场次
            private int liveScreening;
            //直播商品批次
            private String lot;
            //商城商品id
            private String mallGoodsId;
            //订单量
            private int orderCount;
            //批次状态,0,未使用,1,使用中,2,未使用
            private int productStatus;
            //售价
            private Long salePrice;
            //销量
            private int sales;
            //购物车要展示的商品通过状态辨别,1,展示,0,不展示
            private String shoppingGoodsStatus;
            //商城商品id
            private String sort;
            //商品规格id
            private String specificationId;
            //商品规格名称
            private String specificationName;
            //库存
            private int stock;
            //浏览量
            private int viewsCount;

            public String getBrandId() {
                return brandId;
            }

            public void setBrandId(String brandId) {
                this.brandId = brandId;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getCommissionRate() {
                return commissionRate;
            }

            public void setCommissionRate(String commissionRate) {
                this.commissionRate = commissionRate;
            }

            public Long getCostPrice() {
                return costPrice;
            }

            public void setCostPrice(Long costPrice) {
                this.costPrice = costPrice;
            }

            public int getExplainStatus() {
                return explainStatus;
            }

            public void setExplainStatus(int explainStatus) {
                this.explainStatus = explainStatus;
            }

            public Long getGoodsMoney() {
                return goodsMoney;
            }

            public void setGoodsMoney(Long goodsMoney) {
                this.goodsMoney = goodsMoney;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsPicture() {
                return goodsPicture;
            }

            public void setGoodsPicture(String goodsPicture) {
                this.goodsPicture = goodsPicture;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getLiveScreening() {
                return liveScreening;
            }

            public void setLiveScreening(int liveScreening) {
                this.liveScreening = liveScreening;
            }

            public String getLot() {
                return lot;
            }

            public void setLot(String lot) {
                this.lot = lot;
            }

            public String getMallGoodsId() {
                return mallGoodsId;
            }

            public void setMallGoodsId(String mallGoodsId) {
                this.mallGoodsId = mallGoodsId;
            }

            public int getOrderCount() {
                return orderCount;
            }

            public void setOrderCount(int orderCount) {
                this.orderCount = orderCount;
            }

            public int getProductStatus() {
                return productStatus;
            }

            public void setProductStatus(int productStatus) {
                this.productStatus = productStatus;
            }

            public Long getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(Long salePrice) {
                this.salePrice = salePrice;
            }

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }

            public String getShoppingGoodsStatus() {
                return shoppingGoodsStatus;
            }

            public void setShoppingGoodsStatus(String shoppingGoodsStatus) {
                this.shoppingGoodsStatus = shoppingGoodsStatus;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getSpecificationId() {
                return specificationId;
            }

            public void setSpecificationId(String specificationId) {
                this.specificationId = specificationId;
            }

            public String getSpecificationName() {
                return specificationName;
            }

            public void setSpecificationName(String specificationName) {
                this.specificationName = specificationName;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public int getViewsCount() {
                return viewsCount;
            }

            public void setViewsCount(int viewsCount) {
                this.viewsCount = viewsCount;
            }
        }

    }

}

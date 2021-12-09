package com.yuanbaogo.commonlib.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhx
 * @description:
 * @date : 2021/7/30 11:06
 */

public class RecommendVideoBean implements Parcelable {


    private int size;
    private int page;
    private List<RowsBean> rows;
    private int total;

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

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class RowsBean implements Parcelable {
        private String title;
        private String videoId;
        private String videoUrl;
        private String coverUrl;
        private String ybCode;
        private long createTime;
        private String coverThumbUrl;
        private String portrait;
        private int coverImgHeight;
        private int coverImgWidth;
        private String userNickName;
        private int hasLiked;
        private int followState;
        private int canDelete;
        private int viewCount;
        private int likeCount;
        private int commentCount;
        private String shareCount;
        private String goodsId;
        private List<GoodsInfoVOBean> goodsInfoVO;

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

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
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

        public int getCoverImgHeight() {
            return coverImgHeight;
        }

        public void setCoverImgHeight(int coverImgHeight) {
            this.coverImgHeight = coverImgHeight;
        }

        public int getCoverImgWidth() {
            return coverImgWidth;
        }

        public void setCoverImgWidth(int coverImgWidth) {
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

        public String getShareCount() {
            return shareCount;
        }

        public void setShareCount(String shareCount) {
            this.shareCount = shareCount;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public List<GoodsInfoVOBean> getGoodsInfoVO() {
            return goodsInfoVO;
        }

        public void setGoodsInfoVO(List<GoodsInfoVOBean> goodsInfoVO) {
            this.goodsInfoVO = goodsInfoVO;
        }

        public static class GoodsInfoVOBean implements Parcelable {
            private String id;
            private String lot;
            private long goodsMoney;
            private String brandId;
            private String brandName;
            private String goodsName;
            private String mallGoodsId;
            private String sort;
            private String stock;
            private String sales;
            private String liveScreening;
            private String commissionRate;
            private String specificationId;
            private String specificationName;
            private String shoppingGoodsStatus;
            private String productStatus;
            private String goodsPicture;
            private String explainStatus;
            private String viewsCount;
            private String orderCount;
            private String salePrice;
            private String costPrice;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLot() {
                return lot;
            }

            public void setLot(String lot) {
                this.lot = lot;
            }

            public long getGoodsMoney() {
                return goodsMoney;
            }

            public void setGoodsMoney(long goodsMoney) {
                this.goodsMoney = goodsMoney;
            }

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

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getMallGoodsId() {
                return mallGoodsId;
            }

            public void setMallGoodsId(String mallGoodsId) {
                this.mallGoodsId = mallGoodsId;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getStock() {
                return stock;
            }

            public void setStock(String stock) {
                this.stock = stock;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            public String getLiveScreening() {
                return liveScreening;
            }

            public void setLiveScreening(String liveScreening) {
                this.liveScreening = liveScreening;
            }

            public String getCommissionRate() {
                return commissionRate;
            }

            public void setCommissionRate(String commissionRate) {
                this.commissionRate = commissionRate;
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

            public String getShoppingGoodsStatus() {
                return shoppingGoodsStatus;
            }

            public void setShoppingGoodsStatus(String shoppingGoodsStatus) {
                this.shoppingGoodsStatus = shoppingGoodsStatus;
            }

            public String getProductStatus() {
                return productStatus;
            }

            public void setProductStatus(String productStatus) {
                this.productStatus = productStatus;
            }

            public String getGoodsPicture() {
                return goodsPicture;
            }

            public void setGoodsPicture(String goodsPicture) {
                this.goodsPicture = goodsPicture;
            }

            public String getExplainStatus() {
                return explainStatus;
            }

            public void setExplainStatus(String explainStatus) {
                this.explainStatus = explainStatus;
            }

            public String getViewsCount() {
                return viewsCount;
            }

            public void setViewsCount(String viewsCount) {
                this.viewsCount = viewsCount;
            }

            public String getOrderCount() {
                return orderCount;
            }

            public void setOrderCount(String orderCount) {
                this.orderCount = orderCount;
            }

            public String getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(String salePrice) {
                this.salePrice = salePrice;
            }

            public String getCostPrice() {
                return costPrice;
            }

            public void setCostPrice(String costPrice) {
                this.costPrice = costPrice;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.lot);
                dest.writeLong(this.goodsMoney);
                dest.writeString(this.brandId);
                dest.writeString(this.brandName);
                dest.writeString(this.goodsName);
                dest.writeString(this.mallGoodsId);
                dest.writeString(this.sort);
                dest.writeString(this.stock);
                dest.writeString(this.sales);
                dest.writeString(this.liveScreening);
                dest.writeString(this.commissionRate);
                dest.writeString(this.specificationId);
                dest.writeString(this.specificationName);
                dest.writeString(this.shoppingGoodsStatus);
                dest.writeString(this.productStatus);
                dest.writeString(this.goodsPicture);
                dest.writeString(this.explainStatus);
                dest.writeString(this.viewsCount);
                dest.writeString(this.orderCount);
                dest.writeString(this.salePrice);
                dest.writeString(this.costPrice);
            }

            public void readFromParcel(Parcel source) {
                this.id = source.readString();
                this.lot = source.readString();
                this.goodsMoney = source.readLong();
                this.brandId = source.readString();
                this.brandName = source.readString();
                this.goodsName = source.readString();
                this.mallGoodsId = source.readString();
                this.sort = source.readString();
                this.stock = source.readString();
                this.sales = source.readString();
                this.liveScreening = source.readString();
                this.commissionRate = source.readString();
                this.specificationId = source.readString();
                this.specificationName = source.readString();
                this.shoppingGoodsStatus = source.readString();
                this.productStatus = source.readString();
                this.goodsPicture = source.readString();
                this.explainStatus = source.readString();
                this.viewsCount = source.readString();
                this.orderCount = source.readString();
                this.salePrice = source.readString();
                this.costPrice = source.readString();
            }

            public GoodsInfoVOBean() {
            }

            protected GoodsInfoVOBean(Parcel in) {
                this.id = in.readString();
                this.lot = in.readString();
                this.goodsMoney = in.readLong();
                this.brandId = in.readString();
                this.brandName = in.readString();
                this.goodsName = in.readString();
                this.mallGoodsId = in.readString();
                this.sort = in.readString();
                this.stock = in.readString();
                this.sales = in.readString();
                this.liveScreening = in.readString();
                this.commissionRate = in.readString();
                this.specificationId = in.readString();
                this.specificationName = in.readString();
                this.shoppingGoodsStatus = in.readString();
                this.productStatus = in.readString();
                this.goodsPicture = in.readString();
                this.explainStatus = in.readString();
                this.viewsCount = in.readString();
                this.orderCount = in.readString();
                this.salePrice = in.readString();
                this.costPrice = in.readString();
            }

            public static final Creator<GoodsInfoVOBean> CREATOR = new Creator<GoodsInfoVOBean>() {
                @Override
                public GoodsInfoVOBean createFromParcel(Parcel source) {
                    return new GoodsInfoVOBean(source);
                }

                @Override
                public GoodsInfoVOBean[] newArray(int size) {
                    return new GoodsInfoVOBean[size];
                }
            };
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.videoId);
            dest.writeString(this.videoUrl);
            dest.writeString(this.coverUrl);
            dest.writeString(this.ybCode);
            dest.writeLong(this.createTime);
            dest.writeString(this.coverThumbUrl);
            dest.writeString(this.portrait);
            dest.writeInt(this.coverImgHeight);
            dest.writeInt(this.coverImgWidth);
            dest.writeString(this.userNickName);
            dest.writeInt(this.hasLiked);
            dest.writeInt(this.followState);
            dest.writeInt(this.canDelete);
            dest.writeInt(this.viewCount);
            dest.writeInt(this.likeCount);
            dest.writeInt(this.commentCount);
            dest.writeString(this.shareCount);
            dest.writeString(this.goodsId);
            dest.writeList(this.goodsInfoVO);
        }

        public void readFromParcel(Parcel source) {
            this.title = source.readString();
            this.videoId = source.readString();
            this.videoUrl = source.readString();
            this.coverUrl = source.readString();
            this.ybCode = source.readString();
            this.createTime = source.readLong();
            this.coverThumbUrl = source.readString();
            this.portrait = source.readString();
            this.coverImgHeight = source.readInt();
            this.coverImgWidth = source.readInt();
            this.userNickName = source.readString();
            this.hasLiked = source.readInt();
            this.followState = source.readInt();
            this.canDelete = source.readInt();
            this.viewCount = source.readInt();
            this.likeCount = source.readInt();
            this.commentCount = source.readInt();
            this.shareCount = source.readString();
            this.goodsId = source.readString();
            this.goodsInfoVO = new ArrayList<GoodsInfoVOBean>();
            source.readList(this.goodsInfoVO, GoodsInfoVOBean.class.getClassLoader());
        }

        public RowsBean() {
        }

        protected RowsBean(Parcel in) {
            this.title = in.readString();
            this.videoId = in.readString();
            this.videoUrl = in.readString();
            this.coverUrl = in.readString();
            this.ybCode = in.readString();
            this.createTime = in.readLong();
            this.coverThumbUrl = in.readString();
            this.portrait = in.readString();
            this.coverImgHeight = in.readInt();
            this.coverImgWidth = in.readInt();
            this.userNickName = in.readString();
            this.hasLiked = in.readInt();
            this.followState = in.readInt();
            this.canDelete = in.readInt();
            this.viewCount = in.readInt();
            this.likeCount = in.readInt();
            this.commentCount = in.readInt();
            this.shareCount = in.readString();
            this.goodsId = in.readString();
            this.goodsInfoVO = new ArrayList<GoodsInfoVOBean>();
            in.readList(this.goodsInfoVO, GoodsInfoVOBean.class.getClassLoader());
        }

        public static final Creator<RowsBean> CREATOR = new Creator<RowsBean>() {
            @Override
            public RowsBean createFromParcel(Parcel source) {
                return new RowsBean(source);
            }

            @Override
            public RowsBean[] newArray(int size) {
                return new RowsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.size);
        dest.writeInt(this.page);
        dest.writeList(this.rows);
        dest.writeInt(this.total);
    }

    public void readFromParcel(Parcel source) {
        this.size = source.readInt();
        this.page = source.readInt();
        this.rows = new ArrayList<RowsBean>();
        source.readList(this.rows, RowsBean.class.getClassLoader());
        this.total = source.readInt();
    }

    public RecommendVideoBean() {
    }

    protected RecommendVideoBean(Parcel in) {
        this.size = in.readInt();
        this.page = in.readInt();
        this.rows = new ArrayList<RowsBean>();
        in.readList(this.rows, RowsBean.class.getClassLoader());
        this.total = in.readInt();
    }

    public static final Parcelable.Creator<RecommendVideoBean> CREATOR = new Parcelable.Creator<RecommendVideoBean>() {
        @Override
        public RecommendVideoBean createFromParcel(Parcel source) {
            return new RecommendVideoBean(source);
        }

        @Override
        public RecommendVideoBean[] newArray(int size) {
            return new RecommendVideoBean[size];
        }
    };
}

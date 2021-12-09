package com.yuanbaogo.shop.main.model;

/**
 * @Description: 会挑会选Model
 * @Params:
 * @Problem: 需要添加备注
 * @Author: HG
 * @Date: 8/17/21 6:58 PM
 * @Modifier:
 * @Modify:
 */
public class ShopRecommendVideoBean {

//  [
//      {
//          "sort":-1,
//          "businessId":"3701925923530891764",
//          "businessTitleOne":"脱单街坊34",
//          "businessTitleTwo":"",
//          "businessTitleThree":"",
//          "businessCover":"http://1305625675.vod2.myqcloud.com/e991a952vodtranscq1305625675/b5f24f093701925923530891764/coverBySnapshot/coverBySnapshot_110922_0.jpg",
//          "businessCoverThumb":"http://1305625675.vod2.myqcloud.com/e991a952vodtranscq1305625675/b5f24f093701925923530891764/coverBySnapshot/coverBySnapshot_110922_0.jpg",
//          "coverImgHeight":280,
//          "coverImgWidth":210,
//          "type":1,
//          "lanuchId":"1434795743084105728"
//      }
//  ]

    private String sort;
    private String businessId;
    private String businessTitleOne;
    private String businessTitleTwo;
    private String businessTitleThree;
    private String businessCover;
    private String businessCoverThumb;
    private String coverImgHeight;
    private String coverImgWidth;
    private String type;
    private String lanuchId;
    //动画图片
    private int bitmapIndex = 0;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessTitleOne() {
        return businessTitleOne;
    }

    public void setBusinessTitleOne(String businessTitleOne) {
        this.businessTitleOne = businessTitleOne;
    }

    public String getBusinessTitleTwo() {
        return businessTitleTwo;
    }

    public void setBusinessTitleTwo(String businessTitleTwo) {
        this.businessTitleTwo = businessTitleTwo;
    }

    public String getBusinessTitleThree() {
        return businessTitleThree;
    }

    public void setBusinessTitleThree(String businessTitleThree) {
        this.businessTitleThree = businessTitleThree;
    }

    public String getBusinessCover() {
        return businessCover;
    }

    public void setBusinessCover(String businessCover) {
        this.businessCover = businessCover;
    }

    public String getBusinessCoverThumb() {
        return businessCoverThumb;
    }

    public void setBusinessCoverThumb(String businessCoverThumb) {
        this.businessCoverThumb = businessCoverThumb;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanuchId() {
        return lanuchId;
    }

    public void setLanuchId(String lanuchId) {
        this.lanuchId = lanuchId;
    }

    public int getBitmapIndex() {
        return bitmapIndex;
    }

    public void setBitmapIndex(int bitmapIndex) {
        this.bitmapIndex = bitmapIndex;
    }
}

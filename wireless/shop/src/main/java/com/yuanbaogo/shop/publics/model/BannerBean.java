package com.yuanbaogo.shop.publics.model;

/**
 * @Description: Banner
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/16/21 1:09 PM
 * @Modifier:
 * @Modify:
 */
public class BannerBean {

//  [
//      {
//          "title": "测试图2",
//          "pictureUrl": "http://localhost:10011/doc.html",
//          "linkUrl": "http://localhost:10011/doc.html",
//          "linkUrlStatus": true
//      }
//  ]

    /**
     * 标题
     */
    String title;

    /**
     * 图片地址
     */
    String pictureUrl;

    /**
     * 图片跳转链接
     */
    String linkUrl;

    /**
     * 跳转状态 false无跳转 true有跳转链接
     */
    boolean linkUrlStatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public boolean isLinkUrlStatus() {
        return linkUrlStatus;
    }

    public void setLinkUrlStatus(boolean linkUrlStatus) {
        this.linkUrlStatus = linkUrlStatus;
    }
}

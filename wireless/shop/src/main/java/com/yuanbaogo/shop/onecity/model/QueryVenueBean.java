package com.yuanbaogo.shop.onecity.model;

/**
 * @Description: 场馆model
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/20/21 10:58 AM
 * @Modifier:
 * @Modify:
 */
public class QueryVenueBean {

//  [
//      {
//          "id": 0,
//          "imageUrl": "",
//          "name": "",
//          "status": ""
//      }
//  ]

    /**
     * 场馆id
     */
    private String id;

    /**
     * 场馆图片
     */
    private String imageUrl;

    /**
     * 场馆名称
     */
    private String name;

    /**
     * 是否启用 0-不启用 1-启用
     */
    private int status;

    public String getId() {
        return id;
    }

    public QueryVenueBean setId(String id) {
        this.id = id;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public QueryVenueBean setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getName() {
        return name;
    }

    public QueryVenueBean setName(String name) {
        this.name = name;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public QueryVenueBean setStatus(int status) {
        this.status = status;
        return this;
    }
}

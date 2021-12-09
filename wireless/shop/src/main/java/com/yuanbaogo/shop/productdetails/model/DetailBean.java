package com.yuanbaogo.shop.productdetails.model;

/**
 * @Description: 商品详情
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/16/21 5:25 PM
 * @Modifier:
 * @Modify:
 */
public class DetailBean {

//    {
//        "detailText": "",
//            "id": 0
//    }

    private String detailText;
    private String id;

    public String getDetailText() {
        return detailText;
    }

    public void setDetailText(String detailText) {
        this.detailText = detailText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

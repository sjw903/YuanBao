package com.yuanbaogo.shop.search.model;

import com.yuanbaogo.shop.publics.model.RecommendBean;

import java.util.List;

/**
 * @Description: 商品列表
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/12/21 11:23 AM
 * @Modifier:
 * @Modify:
 */
public class ProductDetailsListBean {

//    {
//            "size": 10,
//            "page": 1,
//            "rows": [
//              {
//                "spuId": 1,
//                "categoryOneName": "一级分类1",
//                "categoryTwoName": "二级分类1",
//                "categoryThreeName": "三级分类1",
//                "cargoName": "傻货1",
//                "goodsName": "球鞋1",
//                "specificationAgg":[
//                  {
//                      "id":1425648314679328768,
//                      "spuId":1425647363516039168,
//                      "specId":1425646828721307648,
//                      "valueAggList":[
//                        {
//                            "id":1425648314679328769,
//                            "spuId":1425647363516039168,
//                            "specId":1425648314679328768,
//                            "relationId":null,
//                            "specValue":"黑色",
//                            "imageUrl":null,
//                            "specIndex":6,
//                            "isGeneralSpec":false
//                        }
//                      ],
//                      "specName":"颜色",
//                      "isGeneralSpec":false,
//                      "required":false,
//                      "numerical":false,
//                      "hasCustomSpec":false,
//                      "hasImage":false
//                  },
//                  {
//                      "id":1425648314679328772,
//                      "spuId":1425647363516039168,
//                      "specId":1425646854264619008,
//                      "valueAggList":[
//                        {
//                            "id":1425648314679328773,
//                            "spuId":1425647363516039168,
//                            "specId":1425648314679328772,
//                            "relationId":null,
//                            "specValue":"100cm",
//                            "imageUrl":null,
//                            "specIndex":9,
//                            "isGeneralSpec":false
//                        }
//                      ],
//                      "specName":"大小",
//                      "isGeneralSpec":false,
//                      "required":false,
//                      "numerical":false,
//                      "hasCustomSpec":false,
//                      "hasImage":false
//                  }
//                ],
//                "brandName": "大品牌1",
//                "englishName": "EnglishName1",
//                "logoUrl": "http://localhost/",
//                "brandDescription": "这是个品牌的描述",
//                "keywordList": null,
//                "minSalePrice": 100000,
//                "minCostPrice": 10,
//                "minLinePrice": 100,
//                "totalSales": 150,
//                "mainImage": ["http://localhost/1","http://localhost/1","http://localhost/1"],
//                "coverImages": "http://localhost/",
//                "description": "这是个商品的描述",
//                "shelfStatus": true,
//                "recycleStatus": true
//              }
//            ],
//            "total": 1
//    }

    private int size;
    private int page;
    private int total;
    private List<RecommendBean> rows;

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

    public List<RecommendBean> getRows() {
        return rows;
    }

    public void setRows(List<RecommendBean> rows) {
        this.rows = rows;
    }

}

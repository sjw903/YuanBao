package com.yuanbaogo.commonlib.router.model;

import java.io.Serializable;

/**
 * @Description: 根据输入框内容检索商品列表
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/31/21 6:44 PM
 * @Modifier:
 * @Modify:
 */
public class SearchMerchandiseBean implements Serializable {

//    {
//        "esGoodsName": "货物",
//        "orderBy": 1,
//        "pageNum": 1,
//        "pageSize": 10,
//        "specialId": 0,
//        "tag": 0
//    }

    /**
     * 要检索的名称,示例值(货物)
     */
    private String esGoodsName;

    /**
     * 检索条件：1：价格从高到低（降序DESC），2：价格从低到高（升序ASC），3：销量从高到低（降序DESC）,示例值(1)
     */
    private int orderBy = 0;

    /**
     * 页码,示例值(1)
     */
    private int pageNum = 1;

    /**
     * 每页页数,示例值(6)
     */
    private int pageSize = 6;

    /**
     * 专区Id
     */
    private String specialId;

    /**
     * 专区类型
     * 0 普通商品
     * 1 五百专区商品 2五千专区商品 3五万专区商品
     * 4 一城一品 5 秒杀商品 7 主题专区商品
     * 8 一城一品-首推爆款 9 一城一品-为你推荐
     * 10 元宝国际-好货必
     */
    private int tag;

    /**
     * 用来判断  是否finsh搜索页面  true：销毁搜索页面 false：不销毁搜索页面
     */
    private boolean isFinsh = false;

    public String getEsGoodsName() {
        return esGoodsName;
    }

    public SearchMerchandiseBean setEsGoodsName(String esGoodsName) {
        this.esGoodsName = esGoodsName;
        return this;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public SearchMerchandiseBean setOrderBy(int orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public int getPageNum() {
        return pageNum;
    }

    public SearchMerchandiseBean setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public SearchMerchandiseBean setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public String getSpecialId() {
        return specialId;
    }

    public SearchMerchandiseBean setSpecialId(String specialId) {
        this.specialId = specialId;
        return this;
    }

    public int getTag() {
        return tag;
    }

    public SearchMerchandiseBean setTag(int tag) {
        this.tag = tag;
        return this;
    }

    public boolean isFinsh() {
        return isFinsh;
    }

    public SearchMerchandiseBean setFinsh(boolean finsh) {
        isFinsh = finsh;
        return this;
    }
}

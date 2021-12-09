package com.yuanbaogo.shop.publics.model;

import java.util.List;

/**
 * @Description: 商品分页Model
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/31/21 7:04 PM
 * @Modifier:
 * @Modify:
 */
public class SearchMerchandiseListBean {

//    {
//        "size":6,
//        "page":1,
//        "rows":Array[1],
//        "total":1
//    }

    private int size;

    private int page;

    private List<SearchMerchandiseRowsBean> rows;

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

    public List<SearchMerchandiseRowsBean> getRows() {
        return rows;
    }

    public void setRows(List<SearchMerchandiseRowsBean> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}

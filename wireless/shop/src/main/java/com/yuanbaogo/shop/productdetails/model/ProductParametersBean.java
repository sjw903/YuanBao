package com.yuanbaogo.shop.productdetails.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/17/21 9:32 AM
 * @Modifier:
 * @Modify:
 */
public class ProductParametersBean {

    private String specName;
    private String[] valueAggList;

    public String getSpecName() {
        return specName;
    }

    public ProductParametersBean setSpecName(String specName) {
        this.specName = specName;
        return this;
    }

    public String[] getValueAggList() {
        return valueAggList;
    }

    public ProductParametersBean setValueAggList(String[] valueAggList) {
        this.valueAggList = valueAggList;
        return this;
    }
}

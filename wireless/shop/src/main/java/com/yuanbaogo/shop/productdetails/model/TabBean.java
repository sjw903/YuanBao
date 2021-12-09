package com.yuanbaogo.shop.productdetails.model;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/15/21 1:33 PM
 * @Modifier:
 * @Modify:
 */
public class TabBean implements CustomTabEntity {

    public String title;

    public TabBean(String title) {
        this.title = title;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }

}

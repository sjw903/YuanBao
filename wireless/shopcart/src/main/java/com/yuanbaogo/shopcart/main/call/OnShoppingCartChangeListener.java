package com.yuanbaogo.shopcart.main.call;

/**
 * @Description: 回调
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/26/21 3:22 PM
 * @Modifier:
 * @Modify:
 */
public interface OnShoppingCartChangeListener {

    /**
     * 数据更改
     *
     * @param selectCount
     * @param selectMoney
     */
    void onDataChange(String selectCount, String selectMoney, String num);

    /**
     * item
     *
     * @param isSelectedAll
     */
    void onSelectItem(boolean isSelectedAll);

}

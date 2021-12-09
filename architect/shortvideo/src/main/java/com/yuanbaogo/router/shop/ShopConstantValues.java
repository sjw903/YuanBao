package com.yuanbaogo.router.shop;

/**
 * 商城模块的scheme<BR>
 */
public interface ShopConstantValues {

    String SCHEME = "yuanbaoCustomer://";
    String HOST = "ybShopModule/";
    String BASE_URI = SCHEME + HOST;

    interface SHOP_SEARCH {
        String URI = BASE_URI + "shop_search";
        String SEARCH = "search";
    }

}

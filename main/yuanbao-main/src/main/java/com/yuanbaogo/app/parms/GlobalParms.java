package com.yuanbaogo.app.parms;

import com.yuanbaogo.find.FindFragment;
import com.yuanbaogo.homevideo.main.view.VideoFragment;
import com.yuanbaogo.mine.mine.view.MineFragment;
import com.yuanbaogo.shop.main.view.ShopFragment;
import com.yuanbaogo.shopcart.main.view.ShopCartFragment;

/**
 * @Description: 全局变量
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/31/21 3:58 PM
 * @Modifier:
 * @Modify:
 */
public class GlobalParms {

    /**
     * 商城
     */
    private static ShopFragment shopFragment;

    /**
     * 短视频
     */
    private static VideoFragment videoFragment;

    /**
     * 圈子
     */
    private static FindFragment findFragment;

    /**
     * 购物车
     */
    private static ShopCartFragment shopCartFragment;

    /**
     * 我的
     */
    private static MineFragment mineFragment;

    /**
     * 获取首页Fragment
     *
     * @return
     */
    public static ShopFragment getShopFragment() {
        if (shopFragment == null) {
            shopFragment = new ShopFragment();
        }
        return shopFragment;
    }

    /**
     * 获取短视频Fragment
     *
     * @return
     */
    public static VideoFragment getVideoFragment() {
        if (videoFragment == null) {
            videoFragment = new VideoFragment();
        }
        return videoFragment;
    }

    /**
     * 获取圈子Fragment
     *
     * @return
     */
    public static FindFragment getFindFragment() {
        if (findFragment == null) {
            findFragment = new FindFragment();
        }
        return findFragment;
    }

    /**
     * 获取购物车Fragment
     *
     * @return
     */
    public static ShopCartFragment getShopCartFragment() {
        if (shopCartFragment == null) {
            shopCartFragment = new ShopCartFragment();
        }
        return shopCartFragment;
    }

    /**
     * 获取购物车Fragment
     *
     * @return
     */
    public static MineFragment getMineFragment() {
        if (mineFragment == null) {
            mineFragment = new MineFragment();
        }
        return mineFragment;
    }

}

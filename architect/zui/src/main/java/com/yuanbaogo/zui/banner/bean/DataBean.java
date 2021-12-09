package com.yuanbaogo.zui.banner.bean;

import com.yuanbaogo.zui.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description: 轮播图默认数据数据
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/2/21 9:38 AM
 * @Modifier:
 * @Modify:
 */
public class DataBean {

    public Integer imageRes;//图片
    public String imageUrl;//网络图片或网络视频
    public String title;//标题
    public int viewType;// 1 显示图片  2 播放视频 3 显示文字

    public DataBean(Integer imageRes, String title, int viewType) {
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
    }

    public DataBean(String imageUrl, String title, int viewType) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.viewType = viewType;
    }

    /**
     * 商城首页Banner 默认显示
     *
     * @return
     */
    public static List<DataBean> getShopDataVideo() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.mipmap.icon_lucky_draw_banner1, "第一个", 1));
        list.add(new DataBean(R.mipmap.icon_lucky_draw_banner2, "第一个", 1));
        list.add(new DataBean(R.mipmap.icon_lucky_draw_banner3, "第一个", 1));
        return list;
    }

    /**
     * 一城一品Banner 默认显示
     *
     * @return
     */
    public static List<DataBean> getOneCityDataVideo() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.mipmap.icon_shop_onecity_banner, "第一个", 1));
        return list;
    }

    /**
     * 全名拼团Banner 默认显示
     *
     * @return
     */
    public static List<DataBean> getJoinGroup() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.mipmap.icon_shop_international_banner, "第一个", 1));
        return list;
    }

    /**
     * 秒杀Banner 默认显示
     *
     * @return
     */
    public static List<DataBean> getSpickDataVideo() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.mipmap.icon_shop_spick_banner, "第一个", 1));
        return list;
    }

    /**
     * 元宝国际Banner 默认显示
     *
     * @return
     */
    public static List<DataBean> getYBInternationalDataVideo() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.mipmap.icon_shop_international_banner, "第一个", 1));
        return list;
    }

    /**
     * 商品详情第一个是视频
     *
     * @return
     */
    public static List<DataBean> getDataVideo() {
        List<DataBean> list = new ArrayList<>();
//        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4", "第一个放视频", 2));
        list.add(new DataBean(R.mipmap.icon_shop_international_banner, "第一个", 1));
        list.add(new DataBean(R.mipmap.icon_shop_international_banner, "第二个", 1));
        list.add(new DataBean(R.mipmap.icon_shop_international_banner, "第三个", 1));
        return list;
    }

    /**
     * 获取十六进制的颜色代码.例如  "#5A6677"
     * 分别取R、G、B的随机值，然后加起来即可
     *
     * @return String
     */
    public static String getRandColor() {
        String R, G, B;
        Random random = new Random();
        R = Integer.toHexString(random.nextInt(256)).toUpperCase();
        G = Integer.toHexString(random.nextInt(256)).toUpperCase();
        B = Integer.toHexString(random.nextInt(256)).toUpperCase();
        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;
        return "#" + R + G + B;
    }

}

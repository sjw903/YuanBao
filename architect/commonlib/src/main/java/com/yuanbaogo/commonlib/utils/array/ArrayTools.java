package com.yuanbaogo.commonlib.utils.array;


import com.yuanbaogo.commonlib.R;

/**
 * @Description: 数组工具类
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/2/21 2:37 PM
 * @Modifier:
 * @Modify:
 */
public class ArrayTools {

    public enum shopFunction {
        JOIN_GROUP(2, "全民拼团", R.mipmap.icon_shop_function_join_group, true),
        ONE_CITY_ONE_PRODUCCT(1, "一城一品", R.mipmap.icon_shop_function_one_city_one_product, true),
        SPIKE(3, "秒杀", R.mipmap.icon_shop_function_spike, false),
        SEVEN_PURCHASE(4, "七天采购", R.mipmap.icon_shop_function_seven_purchase, false),
        YB_INTERNATIONAL(5, "元宝精选", R.mipmap.icon_shop_function_yb_international, true),
        RECHARGE(6, "话费充值", R.mipmap.icon_shop_function_recharge, true),
        GAS_GIFT_CARD(8, "加油卡", R.mipmap.icon_shop_function_gas_gift_card, true),
        AIR_TICKETS(7, "机票", R.mipmap.icon_shop_function_air_tickets, false),
        COLLAR_INGOT(9, "领元宝", R.mipmap.icon_shop_function_collar_ingot, false),
        MORE(10, "更多", R.mipmap.icon_shop_function_more, false);

        private int id;
        private String name;
        private int icon;
        private boolean show;

        shopFunction(int id, String name, int icon, boolean show) {
            this.id = id;
            this.icon = icon;
            this.name = name;
            this.show = show;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

    }

    public enum shopGroupJoiningZone {

        FIVE_HUNDRED(1, "五百专区", R.mipmap.icon_shop_group_joining_zone3, true),
        FIVE_THOUSAND(2, "五千专区", R.mipmap.icon_shop_group_joining_zone4, true),
        FIFTY_THOUSAND(3, "五万专区", R.mipmap.icon_shop_group_joining_zone5, true);

        private int id;
        private String name;
        private int icon;
        private boolean show;

        shopGroupJoiningZone(int id, String name, int icon, boolean show) {
            this.id = id;
            this.icon = icon;
            this.name = name;
            this.show = show;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

    }

    public enum shopOneCityFunction {

        LIVE_OFFER(1, "直播优惠", R.mipmap.icon_shop_onecity_live_offer, true),
        LOVE_HELP_FARMERS(2, "爱心助农", R.mipmap.icon_shop_onecity_love_help_farmers, true),
        HOME_TASTE(3, "家乡味道", R.mipmap.icon_shop_onecity_home_taste, true),
        FRESH_FRUIT_VEGETABLE(4, "新鲜果蔬", R.mipmap.icon_shop_onecity_fresh, true),
        HAND_GIFT(5, "伴手好礼", R.mipmap.icon_shop_onecity_hand_gift, true);

        private int id;
        private String name;
        private int icon;
        private boolean show;

        shopOneCityFunction(int id, String name, int icon, boolean show) {
            this.id = id;
            this.icon = icon;
            this.name = name;
            this.show = show;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

    }

    public enum shopOneCityPavilion {

        LIVE_OFFER(1, "山东馆", R.mipmap.icon_shop_onecity_pavilion1, true),
        LOVE_HELP_FARMERS(2, "天津馆", R.mipmap.icon_shop_onecity_pavilion2, true),
        HOME_TASTE(3, "广东馆", R.mipmap.icon_shop_onecity_pavilion3, true),
        FRESH_FRUIT_VEGETABLE(4, "江苏馆", R.mipmap.icon_shop_onecity_pavilion4, true);

        private int id;
        private String name;
        private int icon;
        private boolean show;

        shopOneCityPavilion(int id, String name, int icon, boolean show) {
            this.id = id;
            this.icon = icon;
            this.name = name;
            this.show = show;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

    }

    public enum shopLabel {

        LABEL1(1, "官方自营", R.mipmap.icon_shop_product_details_label1, true),
        LABEL2(2, "免运费", R.mipmap.icon_shop_product_details_label2, true),
        LABEL3(3, "正品保障", R.mipmap.icon_shop_product_details_label3, true);

        private int id;
        private String name;
        private int icon;
        private boolean show;

        shopLabel(int id, String name, int icon, boolean show) {
            this.id = id;
            this.icon = icon;
            this.name = name;
            this.show = show;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

    }

    public enum share {

        WEIXIN_CIRCLE(1, "朋友圈", R.mipmap.icon_share_moments, true),
        WEIXIN(2, "微信", R.mipmap.icon_share_wechat, true),
        FRIEND(3, "好友", R.mipmap.icon_share_friend, true),
        COMMUNITY(4, "社群", R.mipmap.icon_share_association, true);

        private int id;
        private String name;
        private int icon;
        private boolean show;

        share(int id, String name, int icon, boolean show) {
            this.id = id;
            this.icon = icon;
            this.name = name;
            this.show = show;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

    }

    public enum site {

        REPORT(1, "举报", R.mipmap.icon_share_tip_off, true),
        NOT_INTERESTED(2, "不感兴趣", R.mipmap.icon_share_unconcern, true),
        DELETE(3, "删除", R.mipmap.icon_share_delete, true);

        private int id;
        private String name;
        private int icon;
        private boolean show;

        site(int id, String name, int icon, boolean show) {
            this.id = id;
            this.icon = icon;
            this.name = name;
            this.show = show;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

    }

}

package com.yuanbaogo.mine.order.utils;

import com.yuanbaogo.datacenter.local.user.UserInfo;
import com.yuanbaogo.datacenter.local.user.UserStore;

public class Configuration {

    // 默认加载列表条数
    public static final int LOAD_ROWS = 20;
    // 默认第一页的position
    public static final int DEFAULT_ONE = 1;
    // 搜索
    public static final String DEFAULT_SEARCH = "default_search";

    // 获取用户的id
    public static String getUserId() {
        UserInfo user = UserStore.getUser();
        String userId = "";
        if (user == null || user.getUserId() == null) return userId;
        return user.getUserId();
    }

    //OrderStatus订单状态:
    // 1-待付款
    // 2-待收货(正在出库)
    // 3-待收货(已出库)
    // 4-已收货/已完成
    // 5-订单取消(订单关闭)

    // 订单状态 待付款
    public static final int ORDER_PAY_TYPE = 1;
    // 订单状态 待收货(正在出库)
    public static final int ORDER_RECEIPT_TYPE = 2;
    // 订单状态 待收货(已出库)
    public static final int ORDER_RECEIPT_TYPE_STOCK = 3;
    // 订单状态 已收货/已完成
    public static final int ORDER_FINISH_TYPE = 4;
    // 订单状态 已取消
    public static final int ORDER_CANCEL_TYPE = 5;


    // orderType订单类型:
    // 0-商城订单
    // 1-视频订单
    // 2-直播订单
    // 3-小程序订单
    // 所属专区 商城订单
    public static final int ORDER_MALL_AREA = 0;
    // 所属专区 视频订单
    public static final int ORDER_VIDEO_AREA = 1;
    // 所属专区 直播订单
    public static final int ORDER_LIVE_AREA = 2;
    // 所属专区 小程序订单
    public static final int ORDER_APP_AREA = 3;


    // afterSailedStatus售后状态:
    // 1-待审核
    // 2-退货中
    // 3-退款中
    // 4-退款成功
    // 5-订单已完成
    // 6-订单已关闭
    // 售后状态 待审核
    public static final int ORDER_PENDING_REVIEW = 1;
    // 售后状态 退货中
    public static final int ORDER_RETURNING = 2;
    // 售后状态 退款中
    public static final int ORDER_REFUNDING = 3;
    // 售后状态 退款成功
    public static final int ORDER_REFUND_SUCCESSFULLY = 4;
    // 售后状态 完成
    public static final int ORDER_FINISH = 5;
    // 售后状态 关闭
    public static final int ORDER_CLOSE = 6;


    // 1-操作:提交审核,状态:待审核
    public static final int REFUND_WAIT = 1;
    // 2-操作:审核通过,状态:退货中
    public static final int REFUND_RETURNING = 2;
    // 3-操作:已退货,状态:退款中
    public static final int REFUND_REFUNDING = 3;
    // 4-操作:已退款,状态:退款成功
    public static final int REFUND_SUCCESSFULLY = 4;
    // 5-操作:订单已完成
    public static final int REFUND_FINISH = 5;
    // 6-操作:关闭
    public static final int REFUND_CLOSE = 6;

    // 网络请求中YBCode   false为固定的
    public static final String YB_CODE_DEFAULT_TYPE = "yb_code_default_type";

    // Payedtype
    // 1元宝支付
    // 2微信支付
    // 3支付宝支付
    // 4银联支付

    // 1元宝支付
    public static final int PAY_WING = 1;
    // 2微信支付
    public static final int PAY_WE_CHART = 2;
    // 3支付宝支付
    public static final int PAY_ALI = 3;
    // 4银联支付
    public static final int PAY_BANK = 4;
}

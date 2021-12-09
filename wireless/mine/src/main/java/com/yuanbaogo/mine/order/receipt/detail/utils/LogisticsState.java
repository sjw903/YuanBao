package com.yuanbaogo.mine.order.receipt.detail.utils;

import android.util.Pair;

import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.setp.logistics.LogisticsStatus;

/**
 * 0-无轨迹，1-已揽收，2-在途中，3签收,4-问题件，5-派送中，6-已出库
 * <p>
 * <p>
 * 0运输中，1已揽收，2疑难，3已签收，4已退签，5派件中，6退回，
 * 7转单，10待清关，11清关中，12已清关，13清关异常，14收件人拒签 15已出库
 */
public class LogisticsState {

    // 运输中
    public static final int IN_TRANSIT = 0;
    // 已揽收
    public static final int RECEIVED = 1;
    // 疑难
    public static final int DIFFICULT = 2;
    // 已签收
    public static final int SIGNED_FOR = 3;
    // 已退签
    public static final int SIGNED_OUT = 4;
    // 派送中
    public static final int DELIVERING = 5;
    // 退回
    public static final int BACK = 6;
    // 转单
    public static final int TRANSFER_ORDER = 7;
    // 待清关
    public static final int PENDING_CUSTOMS_CLEARANCE = 10;
    // 清关中
    public static final int DURING_CUSTOMS_CLEARANCE = 11;
    // 已清关
    public static final int CLEARED = 12;
    // 清关异常
    public static final int CUSTOMS_CLEARANCE_EXCEPTION = 13;
    // 收件人拒签
    public static final int REJECTED_BY_THE_RECIPIENT = 14;
    // 已出库
    public static final int OUT_OF_STOCK = 15;

    /**
     * 获取当前状态中文
     *
     * @param logisticsState /
     * @return 中文状态
     */
    public static Pair<String, Integer> getLogisticsState(String logisticsState) {
        int status;
        try {
            status = Integer.parseInt(logisticsState);
        } catch (Exception e) {
            e.printStackTrace();
            status = 0;
        }
        String statusDes = "";
        int imgRes = R.mipmap.icon_order_outbound;
        switch (status) {
            case IN_TRANSIT:
                statusDes = "运输中";
                imgRes = R.drawable.icon_timeline_small_marker;
                break;
            case RECEIVED:
                statusDes = "已揽收";
                imgRes = R.mipmap.icon_order_received;
                break;
            case DIFFICULT:
                statusDes = "疑难";
                imgRes = R.mipmap.icon_order_outbound;
                break;
            case SIGNED_FOR:
                statusDes = "已签收";
                imgRes = R.mipmap.icon_order_signed_for;
                break;
            case SIGNED_OUT:
                statusDes = "已退签";
                imgRes = R.mipmap.icon_order_signed_for;
                break;
            case DELIVERING:
                statusDes = "派送中";
                imgRes = R.mipmap.icon_order_on_the_way;
                break;
            case BACK:
                statusDes = "退回";
                imgRes = R.mipmap.icon_order_outbound;
                break;
            case TRANSFER_ORDER:
                statusDes = "转单";
                imgRes = R.mipmap.icon_order_outbound;
                break;
            case PENDING_CUSTOMS_CLEARANCE:
                statusDes = "待清关";
                imgRes = R.mipmap.icon_order_outbound;
                break;
            case DURING_CUSTOMS_CLEARANCE:
                statusDes = "清关中";
                imgRes = R.mipmap.icon_order_outbound;
                break;
            case CLEARED:
                statusDes = "已清关";
                imgRes = R.mipmap.icon_order_outbound;
                break;
            case CUSTOMS_CLEARANCE_EXCEPTION:
                statusDes = "清关异常";
                imgRes = R.mipmap.icon_order_outbound;
                break;
            case REJECTED_BY_THE_RECIPIENT:
                statusDes = "收件人拒签";
                imgRes = R.mipmap.icon_order_outbound;
                break;
            case OUT_OF_STOCK:
                statusDes = "已出库";
                imgRes = R.mipmap.icon_order_outbound;
                break;
        }
        return new Pair<>(statusDes, imgRes);
    }

    public static LogisticsStatus getLogisticsListState(String logisticsState) {
        int status;
        try {
            status = Integer.parseInt(logisticsState);
        } catch (Exception e) {
            e.printStackTrace();
            status = 0;
        }
        LogisticsStatus logisticsStatus;
        switch (status) {
            case RECEIVED:
                logisticsStatus = LogisticsStatus.RECEIVED;
                break;
            case IN_TRANSIT:
                logisticsStatus = LogisticsStatus.IN_TRANSIT;
                break;
            case SIGNED_FOR:
                logisticsStatus = LogisticsStatus.SIGNED_FOR;
                break;
            case DELIVERING:
                logisticsStatus = LogisticsStatus.ON_THE_WAY;
                break;
            default:
                logisticsStatus = LogisticsStatus.OUTBOUND;
                break;
        }
        return logisticsStatus;
    }

}

package com.yuanbaogo.mine.order.finish;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/14/21 4:13 PM
 * @Modifier:
 * @Modify:
 */
public class LogisticsParamete {

    /**
     * 签收状态
     */
    /// 0在途
    public static String onWay = "0";
    /// 1揽收
    public static String collected = "1";
    /// 2疑难
    public static String difficult = "2";
    /// 3签收
    public static String signReceived = "3";
    /// 4退签
    public static String signBack = "4";
    /// 5派件
    public static String dispatching = "5";
    /// 6退回
    public static String sendBack = "6";
    /// 7转单
    public static String transferOrder = "7";
    /// 10待清关
    public static String customsClearanceWaiting = "10";
    /// 11清关中
    public static String customsClearanceOnGoing = "11";
    /// 12已清关
    public static String customsClearanceDone = "12";
    /// 13清关异常
    public static String customsClearanceError = "13";
    /// 14收件人拒签
    public static String rejected = "14";
    /// 15已出库
    public static String startDelivery = "15";


    public static String setParamete(String s) {

        String s1 = "";
        if (s.equals(onWay)) {
            s1 = "运输中";
        } else if (s.equals(collected)) {
            s1 = "已揽收";
        } else if (s.equals(difficult)) {
            s1 = "疑难";
        } else if (s.equals(signReceived)) {
            s1 = "已签收";
        } else if (s.equals(signBack)) {
            s1 = "已退签";
        } else if (s.equals(dispatching)) {
            s1 = "派件中";
        } else if (s.equals(sendBack)) {
            s1 = "退回";
        } else if (s.equals(transferOrder)) {
            s1 = "转单";
        } else if (s.equals(customsClearanceWaiting)) {
            s1 = "待清关";
        } else if (s.equals(customsClearanceOnGoing)) {
            s1 = "清关中";
        } else if (s.equals(customsClearanceDone)) {
            s1 = "已清关";
        } else if (s.equals(customsClearanceError)) {
            s1 = "清关异常";
        } else if (s.equals(rejected)) {
            s1 = "收件人拒签";
        } else if (s.equals(startDelivery)) {
            s1 = "已出库";
        }
        return s1;
    }

}

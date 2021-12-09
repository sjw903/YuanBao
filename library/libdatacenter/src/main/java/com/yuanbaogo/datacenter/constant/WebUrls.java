package com.yuanbaogo.datacenter.constant;

import com.yuanbaogo.datacenter.url.http.RootUrl;

public class WebUrls {

    //访问web页面添加的标记，表明是android访问
    public static final String device = "LDClientAndroid";

    /**
     * 配置web
     */
    public static final String MALL_BUSI = "/mall-busi/operating/redirect-by-key/";
    // 特别声明
    public static final String proSpecial = RootUrl.WEB_ROOT + MALL_BUSI + "pro-special";
    // 用户协议
    public static final String proUser = RootUrl.WEB_ROOT + MALL_BUSI + "pro-user";
    // 隐私协议
    public static final String proPrivacy = RootUrl.WEB_ROOT + MALL_BUSI + "pro-privacy";
    // 充值权益
    public static final String proRecharge = RootUrl.WEB_ROOT + MALL_BUSI + "pro-recharge";
    // 直播服务协议
    public static final String proImclause = RootUrl.WEB_ROOT + MALL_BUSI + "pro-imclause";
    // 实名认证协议
    public static final String proRealproto = RootUrl.WEB_ROOT + MALL_BUSI + "pro-realproto";
    // 元宝支付服务协议
    public static final String proPayproto = RootUrl.WEB_ROOT + MALL_BUSI + "pro-payproto";
    // 推广明细
    public static final String proPromotion = RootUrl.WEB_ROOT + MALL_BUSI + "pro-promotion";
    // 邀请好友
    public static final String inviUser = RootUrl.WEB_ROOT + MALL_BUSI + "invi-user?inviteCode={inviteCode}&token={token}";
    // 邀请注册
    public static final String inviReg = RootUrl.WEB_ROOT + MALL_BUSI + "invi-reg";
    //商品详情
    public static final String goodsDetails = RootUrl.WEB_ROOT + MALL_BUSI + "/goods-details?goodsId={goodsId}";
}

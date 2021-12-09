package com.yuanbaogo.libshare.model;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/2/21 2:40 PM
 * @Modifier:
 * @Modify:
 */
public class ShareParamete {

    //小程序原始ID
    public static final String WEIXIN_APPLETS_ORIGINAL_ID = "gh_28daa7af9c08";

    //小程序ID
    public static final String WEIXIN_APPLETS_ID = "wxd0f379bc26b23610";

    //微信好友
    public static final SHARE_MEDIA WEIXIN = SHARE_MEDIA.WEIXIN;

    //微信朋友圈
    public static final SHARE_MEDIA WEIXIN_CIRCLE = SHARE_MEDIA.WEIXIN_CIRCLE;

    //邀请好友 小程序路径
    public static final String WEIXIN_INVITE_PATH = "pages_login/pages/login/register?i={inviteCode}";

    //详情分享 小程序路径
    public static final String WEIXIN_GOODS_PATH = "pages_goods/pages/goods/detail?k={id}&i={inviteCode}";

    //列表分享 小程序路径
    public static final String WEIXIN_GOODS_LIST_PATH = "pages/home/index?i={inviteCode}";

}

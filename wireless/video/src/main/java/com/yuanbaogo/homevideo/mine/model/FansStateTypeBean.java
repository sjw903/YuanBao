package com.yuanbaogo.homevideo.mine.model;

/**
 * @Description: 关注状态
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/7/21 8:09 PM
 * @Modifier:
 * @Modify:
 */
public class FansStateTypeBean {

    //  关注粉丝状态:0.陌生人,1.关注,2.被关注,3.相互关注

    static final int normal = 0;
    static final int follow = 1;
    static final int byFollow = 2;
    static final int each = 3;

    public static Boolean setFansStateType(int self) {
        // 是否关注了
        if (self == follow || self == each) {
            return true;
        } else {
            return false;
        }
    }

}

package com.yuanbaogo.libbase.baseutil;

import androidx.annotation.NonNull;

/**
 * 防止暴力点击
 */
public class ClickUtils {

    public static final int MIN_DELAY_TIME = 800;  // 两次点击间隔不能少于500ms
    private static long lastClickTime;
    private static long lastNoRecordClickTime;

    /**
     * 是否是第一次点击
     *
     * @return false为第一次点击，true不是第一次点击
     */
    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        lastNoRecordClickTime = currentClickTime;
        return flag;
    }

    /**
     * activity两次点击间隔不能少于800ms
     */
    private static final int MIN_DELAY_TIME_ACTIVITY = 1000;
    private static String sLastActivitySimpleName;

    /**
     * video 拍摄短视频使用
     * 用于判断录制按钮和其他按钮同时按下
     *
     * @return
     */
    public static boolean isRecordWithOtherClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastNoRecordClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return false;
    }

    /**
     * fix连续点击弹出多个activity
     * 1.设置android:launchMode="singleTop"在这个场景下并不管用
     * 2.部分手机可能因为activity的阻塞耗时不同会导致计算的间隔超出500ms，这里定位800毫秒能处理绝大部分的手机和情况
     * 3.通过记录activitySimpleName，避免出现用户熟练连续进入多个页面的时候需要等待
     *
     * @param activitySimpleName Activity.class.getSimpleName()
     * @return boolean
     */
    public static boolean isFastClickActivity(@NonNull String activitySimpleName) {
        long currentClickTime = System.currentTimeMillis();
        boolean isFastClick = (currentClickTime - lastClickTime) <= MIN_DELAY_TIME_ACTIVITY;
        lastClickTime = currentClickTime;
        if (!activitySimpleName.equals(sLastActivitySimpleName)) {
            //如果两次的activity不是同一个，不是快速点击
            isFastClick = false;
            sLastActivitySimpleName = activitySimpleName;
        }
        return isFastClick;
    }
}

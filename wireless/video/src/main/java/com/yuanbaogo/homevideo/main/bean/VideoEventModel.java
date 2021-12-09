package com.yuanbaogo.homevideo.main.bean;

/**
 * 功能：
 * 时间 ： 2021/8/17:.
 * 作者：11190
 */

public class VideoEventModel {
    public VideoEventModel(boolean isVisiable) {
        this.isVisiable = isVisiable;
    }

    boolean isVisiable;

    public boolean isVisiable() {
        return isVisiable;
    }

    public void setVisiable(boolean visiable) {
        isVisiable = visiable;
    }
}

package com.yuanbaogo.zui.dialog.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 3:02 PM
 * @Modifier:
 * @Modify:
 */
public class PactBean {

    boolean isTvTitle = true;//设置标题是否显示 true 显示  false 不显示
    String tvTitles = "说明";//设置标题显示内容
    boolean isImgCancel = false;//设置关闭按钮是否显示 true 显示  false 不显示
    String contentFileName;//设置显示内容文件
    boolean isLine = true;//设置横线是否显示 true 显示  false 不显示
    boolean isLlBottom = true;//设置底部按钮是否显示 true 显示  false 不显示
    boolean isCancel = true;//设置取消按钮是否显示 true 显示  false 不显示
    String tvCancels = "取消";//设置取消按钮显示内容
    boolean isDetermine = true;//设置确定按钮是否显示 true 显示  false 不显示
    String tvDetermines = "确定";//设置确定按钮显示内容
    int type = 1;//1 普通提示弹窗  2 协议弹窗包含《》 需要点击

    public boolean isTvTitle() {
        return isTvTitle;
    }

    public PactBean setTvTitle(boolean tvTitle) {
        isTvTitle = tvTitle;
        return this;
    }

    public String getTvTitles() {
        return tvTitles;
    }

    public PactBean setTvTitles(String tvTitles) {
        this.tvTitles = tvTitles;
        return this;
    }

    public boolean isImgCancel() {
        return isImgCancel;
    }

    public PactBean setImgCancel(boolean imgCancel) {
        isImgCancel = imgCancel;
        return this;
    }

    public String getContentFileName() {
        return contentFileName;
    }

    public PactBean setContentFileName(String contentFileName) {
        this.contentFileName = contentFileName;
        return this;
    }

    public boolean isLine() {
        return isLine;
    }

    public PactBean setLine(boolean line) {
        isLine = line;
        return this;
    }

    public boolean isLlBottom() {
        return isLlBottom;
    }

    public PactBean setLlBottom(boolean llBottom) {
        isLlBottom = llBottom;
        return this;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public PactBean setCancel(boolean cancel) {
        isCancel = cancel;
        return this;
    }

    public String getTvCancels() {
        return tvCancels;
    }

    public PactBean setTvCancels(String tvCancels) {
        this.tvCancels = tvCancels;
        return this;
    }

    public boolean isDetermine() {
        return isDetermine;
    }

    public PactBean setDetermine(boolean determine) {
        isDetermine = determine;
        return this;
    }

    public String getTvDetermines() {
        return tvDetermines;
    }

    public PactBean setTvDetermines(String tvDetermines) {
        this.tvDetermines = tvDetermines;
        return this;
    }

    public int getType() {
        return type;
    }

    public PactBean setType(int type) {
        this.type = type;
        return this;
    }
}

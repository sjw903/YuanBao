package com.yuanbaogo.zui.head.bean;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/6/21 10:01 AM
 * @Modifier:
 * @Modify:
 */
public class HeadBean {

    private Integer rlTopNavigaBarBg;//设置头部背景颜色
    private boolean isRlSearch = true;//设置搜索框是否显示 true 显示  false 不显示（上下滚动显示推荐内容）
    private Integer rlSearchBg;//设置搜索框背景颜色
    private boolean isTvSearch = true;//设置搜索内容是否显示 true 显示  false 不显示（上下滚动显示推荐内容）
    private boolean isEditSearch = false;//设置搜索输入框是否显示 true 显示  false 不显示
    private boolean isImgLeft = true;//设置左上角按钮是否显示 true 显示  false 不显示
    private Integer imgLeftIcon;//设置左上角按钮显示图标
    private Integer imgLeftIconBg;//设置左上角按钮显示图标背景
    private boolean isTvLeftTitle = false;//设置左上角标题是否显示 true 显示  false 不显示
    private String tvLeftTitles;//设置左上角标题内容
    private boolean isTvCenterTitle = false;//设置中间标题是否显示 true 显示  false 不显示
    private String tvCenterTitles;//设置中间标题内容
    private boolean isImgRight = true;//设置右上角按钮是否显示 true 显示  false 不显示（最右边的按钮）
    private Integer imgRightIcon;//设置右上角按钮显示图标
    private Integer imgRightIconBg;//设置右上角按钮显示图标背景
    private boolean isImgToleft = false;//设置右上角按钮是否显示 true 显示  false 不显示（依附于最右边的按钮）(右边)
    private Integer imgToleftIcon;//设置右上角按钮显示图标
    private Integer imgToleftIconBg;//设置右上角按钮显示图标背景
    private boolean isImgToleft2 = false;//设置右上角按钮是否显示 true 显示  false 不显示（依附于右边的按钮）
    private Integer imgToleftIcon2;//设置右上角按钮显示图标
    private Integer imgToleftIconBg2;//设置右上角按钮显示图标背景

    public Integer getRlTopNavigaBarBg() {
        return rlTopNavigaBarBg;
    }

    public HeadBean setRlTopNavigaBarBg(Integer rlTopNavigaBarBg) {
        this.rlTopNavigaBarBg = rlTopNavigaBarBg;
        return this;
    }

    public boolean isRlSearch() {
        return isRlSearch;
    }

    public HeadBean setRlSearch(boolean rlSearch) {
        isRlSearch = rlSearch;
        return this;
    }

    public Integer getRlSearchBg() {
        return rlSearchBg;
    }

    public HeadBean setRlSearchBg(Integer rlSearchBg) {
        this.rlSearchBg = rlSearchBg;
        return this;
    }

    public boolean isTvSearch() {
        return isTvSearch;
    }

    public HeadBean setTvSearch(boolean tvSearch) {
        isTvSearch = tvSearch;
        return this;
    }

    public boolean isEditSearch() {
        return isEditSearch;
    }

    public HeadBean setEditSearch(boolean editSearch) {
        isEditSearch = editSearch;
        return this;
    }

    public boolean isImgLeft() {
        return isImgLeft;
    }

    public HeadBean setImgLeft(boolean imgLeft) {
        isImgLeft = imgLeft;
        return this;
    }

    public Integer getImgLeftIcon() {
        return imgLeftIcon;
    }

    public HeadBean setImgLeftIcon(Integer imgLeftIcon) {
        this.imgLeftIcon = imgLeftIcon;
        return this;
    }

    public Integer getImgLeftIconBg() {
        return imgLeftIconBg;
    }

    public HeadBean setImgLeftIconBg(Integer imgLeftIconBg) {
        this.imgLeftIconBg = imgLeftIconBg;
        return this;
    }

    public boolean isTvLeftTitle() {
        return isTvLeftTitle;
    }

    public HeadBean setTvLeftTitle(boolean tvLeftTitle) {
        isTvLeftTitle = tvLeftTitle;
        return this;
    }

    public String getTvLeftTitles() {
        return tvLeftTitles;
    }

    public HeadBean setTvLeftTitles(String tvLeftTitles) {
        this.tvLeftTitles = tvLeftTitles;
        return this;
    }

    public boolean isTvCenterTitle() {
        return isTvCenterTitle;
    }

    public HeadBean setTvCenterTitle(boolean tvCenterTitle) {
        isTvCenterTitle = tvCenterTitle;
        return this;
    }

    public String getTvCenterTitles() {
        return tvCenterTitles;
    }

    public HeadBean setTvCenterTitles(String tvCenterTitles) {
        this.tvCenterTitles = tvCenterTitles;
        return this;
    }

    public boolean isImgRight() {
        return isImgRight;
    }

    public HeadBean setImgRight(boolean imgRight) {
        isImgRight = imgRight;
        return this;
    }

    public Integer getImgRightIcon() {
        return imgRightIcon;
    }

    public HeadBean setImgRightIcon(Integer imgRightIcon) {
        this.imgRightIcon = imgRightIcon;
        return this;
    }

    public Integer getImgRightIconBg() {
        return imgRightIconBg;
    }

    public HeadBean setImgRightIconBg(Integer imgRightIconBg) {
        this.imgRightIconBg = imgRightIconBg;
        return this;
    }

    public boolean isImgToleft() {
        return isImgToleft;
    }

    public HeadBean setImgToleft(boolean imgToleft) {
        isImgToleft = imgToleft;
        return this;
    }

    public Integer getImgToleftIcon() {
        return imgToleftIcon;
    }

    public HeadBean setImgToleftIcon(Integer imgToleftIcon) {
        this.imgToleftIcon = imgToleftIcon;
        return this;
    }

    public Integer getImgToleftIconBg() {
        return imgToleftIconBg;
    }

    public HeadBean setImgToleftIconBg(Integer imgToleftIconBg) {
        this.imgToleftIconBg = imgToleftIconBg;
        return this;
    }

    public boolean isImgToleft2() {
        return isImgToleft2;
    }

    public HeadBean setImgToleft2(boolean imgToleft2) {
        isImgToleft2 = imgToleft2;
        return this;
    }

    public Integer getImgToleftIcon2() {
        return imgToleftIcon2;
    }

    public HeadBean setImgToleftIcon2(Integer imgToleftIcon2) {
        this.imgToleftIcon2 = imgToleftIcon2;
        return this;
    }

    public Integer getImgToleftIconBg2() {
        return imgToleftIconBg2;
    }

    public HeadBean setImgToleftIconBg2(Integer imgToleftIconBg2) {
        this.imgToleftIconBg2 = imgToleftIconBg2;
        return this;
    }

}

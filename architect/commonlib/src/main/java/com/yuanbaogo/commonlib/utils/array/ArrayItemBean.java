package com.yuanbaogo.commonlib.utils.array;

/**
 * @Description: 数组实体类
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 5/6/21 11:54 AM
 * @Modifier:
 * @Modify:
 */
public class ArrayItemBean {

    private int id;
    private String name;
    private int icon;
    private String imgUrl;
    private boolean isVisibility;

    public int getId() {
        return id;
    }

    public ArrayItemBean setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ArrayItemBean setName(String name) {
        this.name = name;
        return this;
    }

    public int getIcon() {
        return icon;
    }

    public ArrayItemBean setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public ArrayItemBean setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public boolean isVisibility() {
        return isVisibility;
    }

    public ArrayItemBean setVisibility(boolean visibility) {
        isVisibility = visibility;
        return this;
    }

}

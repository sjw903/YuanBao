package com.yuanbaogo.homevideo.live.push.model;

import java.util.List;

public class CreateLiveRequestBean {

    List<String> goods;
    String livePictureId;
    String title;

    public List<String> getGoods() {
        return goods;
    }

    public void setGoods(List<String> goods) {
        this.goods = goods;
    }

    public String getLivePictureId() {
        return livePictureId;
    }

    public void setLivePictureId(String livePictureId) {
        this.livePictureId = livePictureId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

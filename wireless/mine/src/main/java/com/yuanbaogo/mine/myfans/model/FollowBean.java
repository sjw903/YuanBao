package com.yuanbaogo.mine.myfans.model;

public class FollowBean {
    //关注/取消关注
    private Integer state;//关注粉丝状态:0.陌生人,1.关注,2.被关注,3.相互关注
    private String ybCode;//用户code

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getYbCode() {
        return ybCode;
    }

    public void setYbCode(String ybCode) {
        this.ybCode = ybCode;
    }
}

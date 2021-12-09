package com.yuanbaogo.mine.myfans.model;

public class MyFansFollowItem {
    private String fansState;//关注粉丝状态:0.陌生人,1.关注,2.被关注,3.相互关注
    private String nickName;//昵称
    private String phone;//手机号
    private String portraitUrl;//头像
    private String signature;//签名
    private String ybCode;//元宝id

    public String getFansState() {
        return fansState;
    }

    public void setFansState(String fansState) {
        this.fansState = fansState;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getYbCode() {
        return ybCode;
    }

    public void setYbCode(String ybCode) {
        this.ybCode = ybCode;
    }
}

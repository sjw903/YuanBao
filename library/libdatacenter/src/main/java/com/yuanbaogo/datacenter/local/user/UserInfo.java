package com.yuanbaogo.datacenter.local.user;

import java.io.Serializable;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/12/21 1:52 PM
 * @Modifier:
 * @Modify:
 */
public class UserInfo implements Serializable {

    private String userId;
    private String nickName;
    private String phone;
    private String token;
    private String imSign;
    private String invitationCode;//邀请码

    public String getImSign() {
        return imSign;
    }

    public void setImSign(String imSign) {
        this.imSign = imSign;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                ", imSign='" + imSign + '\'' +
                ", invitationCode='" + invitationCode + '\'' +
                '}';
    }

}

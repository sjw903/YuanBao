package com.yuanbaogo.mine.personal.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/9/21 9:05 AM
 * @Modifier:
 * @Modify:
 */
public class PersonalSubmitBean {

//    {
//        "address": "",
//            "nickName": "",
//            "signature": "",
//            "ybCode": ""
//    }

    //地址
    private String address;
    //昵称的名字
    private String nickName;
    //签名
    private String signature;

    public String getAddress() {
        return address;
    }

    public PersonalSubmitBean setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public PersonalSubmitBean setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public PersonalSubmitBean setSignature(String signature) {
        this.signature = signature;
        return this;
    }
}

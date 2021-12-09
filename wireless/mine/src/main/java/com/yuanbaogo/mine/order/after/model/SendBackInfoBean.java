package com.yuanbaogo.mine.order.after.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/13/21 11:33 PM
 * @Modifier:
 * @Modify:
 */
public class SendBackInfoBean {


//		{\"address\":\"aaa\",\"name\":\"aaa\",\"telephone\":\"aaaa\",\"remark\":\"aaa\"}

    private String address;
    private String name;
    private String telephone;
    private String remark;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}

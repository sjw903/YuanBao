package com.yuanbaogo.commonlib.router.model;

import java.io.Serializable;

/**
 * @Description: 用户地址Model
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/26/21 11:44 AM
 * @Modifier:
 * @Modify:
 */
public class AddressOrderBean implements Serializable {

    private String addressDetails;
    private Long addressId;
    private String city;
    private String contactsName;
    private String contactsPhone;
    private String country;
    private Boolean defaulted;
    private String province;
    private Long userId;

    public String getAddressDetails() {
        return addressDetails;
    }

    public AddressOrderBean setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
        return this;
    }

    public Long getAddressId() {
        return addressId;
    }

    public AddressOrderBean setAddressId(Long addressId) {
        this.addressId = addressId;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressOrderBean setCity(String city) {
        this.city = city;
        return this;
    }

    public String getContactsName() {
        return contactsName;
    }

    public AddressOrderBean setContactsName(String contactsName) {
        this.contactsName = contactsName;
        return this;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public AddressOrderBean setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public AddressOrderBean setCountry(String country) {
        this.country = country;
        return this;
    }

    public Boolean getDefaulted() {
        return defaulted;
    }

    public AddressOrderBean setDefaulted(Boolean defaulted) {
        this.defaulted = defaulted;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public AddressOrderBean setProvince(String province) {
        this.province = province;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public AddressOrderBean setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "addressDetails='" + addressDetails + '\'' +
                ", addressId=" + addressId +
                ", city='" + city + '\'' +
                ", contactsName='" + contactsName + '\'' +
                ", contactsPhone='" + contactsPhone + '\'' +
                ", country='" + country + '\'' +
                ", defaulted=" + defaulted +
                ", province='" + province + '\'' +
                ", userId=" + userId +
                '}';
    }
}

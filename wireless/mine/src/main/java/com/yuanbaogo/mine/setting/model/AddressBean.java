
package com.yuanbaogo.mine.setting.model;

@SuppressWarnings("unused")
public class AddressBean {

    private String addressDetails;
    private Long addressId;
    private String city;
    private String contactsName;
    private String contactsPhone;
    private String country;
    private Boolean defaulted;
    private String province;
    private Long userId;
    //是否选择 从确认地址过来
    private boolean isSelect;

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getDefaulted() {
        return defaulted;
    }

    public void setDefaulted(Boolean defaulted) {
        this.defaulted = defaulted;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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
                ", isSelect=" + isSelect +
                '}';
    }
}

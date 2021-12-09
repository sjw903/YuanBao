package com.yuanbaogo.mine.live.model;
public class LiveAmountItem {

    private BaseUserVOBean baseUserVO;
    private Integer charmValue;
    private String commissionAmount;
    private String id;
    private Integer newFan;
    private String orderCount;
    private Boolean sellGoods;
    private String time;
    private String totalAmount;
    private Integer viewsNumber;

    public static class BaseUserVOBean {
        private String address;
        private String age;
        private String backgroundPictureUrl;
        private String gender;
        private String name;
        private String nickName;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getBackgroundPictureUrl() {
            return backgroundPictureUrl;
        }

        public void setBackgroundPictureUrl(String backgroundPictureUrl) {
            this.backgroundPictureUrl = backgroundPictureUrl;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        private String phone;
        private String portraitUrl;
        private String signature;
        private String ybCode;
    }

    public BaseUserVOBean getBaseUserVO() {
        return baseUserVO;
    }

    public void setBaseUserVO(BaseUserVOBean baseUserVO) {
        this.baseUserVO = baseUserVO;
    }

    public Integer getCharmValue() {
        return charmValue;
    }

    public void setCharmValue(Integer charmValue) {
        this.charmValue = charmValue;
    }

    public String getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(String commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNewFan() {
        return newFan;
    }

    public void setNewFan(Integer newFan) {
        this.newFan = newFan;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public Boolean getSellGoods() {
        return sellGoods;
    }

    public void setSellGoods(Boolean sellGoods) {
        this.sellGoods = sellGoods;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getViewsNumber() {
        return viewsNumber;
    }

    public void setViewsNumber(Integer viewsNumber) {
        this.viewsNumber = viewsNumber;
    }
}

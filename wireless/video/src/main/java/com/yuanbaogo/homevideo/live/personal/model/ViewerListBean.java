package com.yuanbaogo.homevideo.live.personal.model;

import java.util.List;


public class ViewerListBean {


    private int size;
    private int page;
    private List<RowsBean> rows;
    private int totle;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public int getTotle() {
        return totle;
    }

    public void setTotle(int totle) {
        this.totle = totle;
    }

    public static class RowsBean {
        private String ybCode;
        private String charmValue;
        private int fansState;
        private BaseUserBean baseUser;

        public String getYbCode() {
            return ybCode;
        }

        public void setYbCode(String ybCode) {
            this.ybCode = ybCode;
        }

        public String getCharmValue() {
            return charmValue;
        }

        public void setCharmValue(String charmValue) {
            this.charmValue = charmValue;
        }

        public int getFansState() {
            return fansState;
        }

        public void setFansState(int fansState) {
            this.fansState = fansState;
        }

        public BaseUserBean getBaseUser() {
            return baseUser;
        }

        public void setBaseUser(BaseUserBean baseUser) {
            this.baseUser = baseUser;
        }

        public static class BaseUserBean {
            private String ybCode;
            private String nickName;
            private String portraitUrl;
            private String backgroundPictureUrl;
            private String signature;
            private String phone;
            private String name;
            private String gender;
            private String age;

            public String getYbCode() {
                return ybCode;
            }

            public void setYbCode(String ybCode) {
                this.ybCode = ybCode;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getPortraitUrl() {
                return portraitUrl;
            }

            public void setPortraitUrl(String portraitUrl) {
                this.portraitUrl = portraitUrl;
            }

            public String getBackgroundPictureUrl() {
                return backgroundPictureUrl;
            }

            public void setBackgroundPictureUrl(String backgroundPictureUrl) {
                this.backgroundPictureUrl = backgroundPictureUrl;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }
        }
    }
}

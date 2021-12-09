package com.yuanbaogo.homevideo.shortvideo.comment.model;

import java.util.List;


public class CommentListBean {

    private int page;
    private List<RowsBean> rows;
    private int size;
    private int total;
    private int allTotal;

    public int getAllTotal() {
        return allTotal;
    }

    public void setAllTotal(int allTotal) {
        this.allTotal = allTotal;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class RowsBean {
        private boolean canDelete;
        private CommentUserBean commentUser;
        private String content;
        private String createTime;
        private boolean haveLike;
        private boolean haveToUser;
        private String id;
        private int likeCount;
        private String pid;
        private String createTimeStr;
        private int replyCount;
        private ToUserBean toUser;

        public boolean isCanDelete() {
            return canDelete;
        }

        public void setCanDelete(boolean canDelete) {
            this.canDelete = canDelete;
        }

        public CommentUserBean getCommentUser() {
            return commentUser;
        }

        public void setCommentUser(CommentUserBean commentUser) {
            this.commentUser = commentUser;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public boolean isHaveLike() {
            return haveLike;
        }

        public void setHaveLike(boolean haveLike) {
            this.haveLike = haveLike;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public ToUserBean getToUser() {
            return toUser;
        }

        public void setToUser(ToUserBean toUser) {
            this.toUser = toUser;
        }

        public boolean isHaveToUser() {
            return haveToUser;
        }

        public void setHaveToUser(boolean haveToUser) {
            this.haveToUser = haveToUser;
        }

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public static class CommentUserBean {
            private String address;
            private String age;
            private String backgroundPictureUrl;
            private String gender;
            private String name;
            private String nickName;
            private String phone;
            private String portraitUrl;
            private String signature;
            private String ybCode;

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
        }

        public static class ToUserBean {
            private String address;
            private String age;
            private String backgroundPictureUrl;
            private String gender;
            private String name;
            private String nickName;
            private String phone;
            private String portraitUrl;
            private String signature;
            private String ybCode;

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
        }
    }
}

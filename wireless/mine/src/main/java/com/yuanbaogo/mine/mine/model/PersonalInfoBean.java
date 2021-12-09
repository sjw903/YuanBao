package com.yuanbaogo.mine.mine.model;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/7/21 5:27 PM
 * @Modifier:
 * @Modify:
 */
public class PersonalInfoBean {

//  {
//      "user":{
//          "ybCode":"237718",
//          "nickName":"用户_32377184",
//          "portraitUrl":"https://ybsjds-oss.oss-cn-beijing.aliyuncs.com/user/avatar/default/head1.png",
//          "backgroundPictureUrl":"https://ybsjds-oss.oss-cn-beijing.aliyuncs.com/user/bg/default/bg1.png",
//          "signature":"",
//          "phone":"",
//          "name":"王小明",
//          "gender":"男",
//          "age":"20",
//          "address":""
//      },
//      "statisticsInfo":{
//          "fans":1,
//          "follow":0,
//          "sessions":0,
//          "sessionsSell":0,
//          "works":1,
//          "likeCount":0
//      },
//      "userRelation":{
//          "ybCode":"237718",
//          "cname":"陌生人",
//          "buttonName":"关注",
//          "value":0,
//          "relationEnums":"STRANGER"
//      }
//  }

    //用户的基本信息
    private PersonalInfoUserBean user;
    //用户的统计信息
    private PersonalInfoStatisBean statisticsInfo;
    //用户关系
    private PersonalInfoRelationBean userRelation;

    public PersonalInfoUserBean getUser() {
        return user;
    }

    public void setUser(PersonalInfoUserBean user) {
        this.user = user;
    }

    public PersonalInfoStatisBean getStatisticsInfo() {
        return statisticsInfo;
    }

    public void setStatisticsInfo(PersonalInfoStatisBean statisticsInfo) {
        this.statisticsInfo = statisticsInfo;
    }

    public PersonalInfoRelationBean getUserRelation() {
        return userRelation;
    }

    public void setUserRelation(PersonalInfoRelationBean userRelation) {
        this.userRelation = userRelation;
    }

    public class PersonalInfoUserBean {

        //用户Id
        private String ybCode;
        //用户昵称
        private String nickName;
        //用户头像地址
        private String portraitUrl;
        //背景图片地址
        private String backgroundPictureUrl;
        //个性签名
        private String signature;
        //手机
        private String phone;
        //真实姓名
        private String name;
        //性别
        private String gender;
        //年龄
        private String age;
        //地址
        private String address;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public class PersonalInfoStatisBean {

        //粉丝数
        private Long fans;
        //关注数
        private Long follow;
        //直播场次（不带货）
        private Long sessions;
        //直播场次（带货）
        private Long sessionsSell;
        //作品数
        private Long works;
        //点赞数
        private Long likeCount;

        public Long getFans() {
            return fans;
        }

        public void setFans(Long fans) {
            this.fans = fans;
        }

        public Long getFollow() {
            return follow;
        }

        public void setFollow(Long follow) {
            this.follow = follow;
        }

        public Long getSessions() {
            return sessions;
        }

        public void setSessions(Long sessions) {
            this.sessions = sessions;
        }

        public Long getSessionsSell() {
            return sessionsSell;
        }

        public void setSessionsSell(Long sessionsSell) {
            this.sessionsSell = sessionsSell;
        }

        public Long getWorks() {
            return works;
        }

        public void setWorks(Long works) {
            this.works = works;
        }

        public Long getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(Long likeCount) {
            this.likeCount = likeCount;
        }
    }

    public class PersonalInfoRelationBean {

        //用户ID
        private String ybCode;
        //关注状态中文名
        private String cname;
        //
        private String buttonName;
        //关注状态value
        private int value;
        //关注状态枚举类,可用值: FOLLOWED, FOLLOW_CLOSELY, MUTUAL_ATTENTION, STRANGER
        private String relationEnums;

        public String getYbCode() {
            return ybCode;
        }

        public void setYbCode(String ybCode) {
            this.ybCode = ybCode;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getButtonName() {
            return buttonName;
        }

        public void setButtonName(String buttonName) {
            this.buttonName = buttonName;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getRelationEnums() {
            return relationEnums;
        }

        public void setRelationEnums(String relationEnums) {
            this.relationEnums = relationEnums;
        }
    }

}

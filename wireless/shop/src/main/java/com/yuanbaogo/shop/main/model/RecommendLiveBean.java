package com.yuanbaogo.shop.main.model;

/**
 * @Description: 会挑会选Model
 * @Params:
 * @Problem: 需要添加备注
 * @Author: HG
 * @Date: 8/17/21 6:58 PM
 * @Modifier:
 * @Modify:
 */
public class RecommendLiveBean {

//  [
//      {
//          "id":"1234124",
//          "typeId":"1897979345789",
//          "type":1,
//          "title":"全网最低百货",
//          "pairTitle":"满额直减",
//          "discounts":" 6000-1000",
//          "liveRoomInfoVO":{
//            "anchorNickName":"马冬梅",
//            "sellGoods":"",
//            "coverUrl":"https://ybsjds-oss.oss-cn-beijing.aliyuncs.com/live1.jpeg",
//            "charm":"10000",
//            "fansNew":"20",
//            "visitors":"20000",
//            "pushUrl":"rtmp://ipush.yuanbaogo.com/ybsd/30045liveStream?txSecret=cfad8c8f37313fb35d5ce2b52bf8327a&txTime=612F0139",
//            "playerUrl":"http://iplayer.yuanbaogo.com/ybsd/30045liveStream.flv?txSecret=da7038d134f684776d16135f2f1cb66b&txTime=612F0139",
//            "webPlayerUrl":"",
//            "avChatRoomId":"20210901092737",
//            "ybCode":"30045",
//            "goodsAll":[],
//            "liveThumbnailUrl":"",
//            "thumbnailHigh":"",
//            "thumbnailWide":"",
//            "title":"",
//            "liveStandardContent":"",
//            "createDateTime":"",
//            "goodsPrice":"",
//            "goodsTitle":"",
//            "goodsUrl":"",
//            "liveGoods":"",
//            "fans":"",
//            "anchorName":"",
//            "anchorGender":"",
//            "anchorPhone":"",
//            "anchorAge":"",
//            "closeDateTime":"",
//            "duration":"",
//            "liveState":"",
//            "attentionType":"",
//            "orderCount":"",
//            "totalAmount":"",
//            "commissionAmount":"",
//            "ranking":[]
//         }
//      },
//  ]

    //ID
    private String id;
    //typeId
    private String typeId;
    //type
    private int type;
    //title
    private String title;
    //副标题
    private String pairTitle;
    //折扣
    private String discounts;
    //直播间信息
    private RecommendLiveInfoBean liveRoomInfoVO;
    //随机播放动画图片下标
    private int bitmapPos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPairTitle() {
        return pairTitle;
    }

    public void setPairTitle(String pairTitle) {
        this.pairTitle = pairTitle;
    }

    public String getDiscounts() {
        return discounts;
    }

    public void setDiscounts(String discounts) {
        this.discounts = discounts;
    }

    public RecommendLiveInfoBean getLiveRoomInfoVO() {
        return liveRoomInfoVO;
    }

    public void setLiveRoomInfoVO(RecommendLiveInfoBean liveRoomInfoVO) {
        this.liveRoomInfoVO = liveRoomInfoVO;
    }

    public int getBitmapPos() {
        return bitmapPos;
    }

    public void setBitmapPos(int bitmapPos) {
        this.bitmapPos = bitmapPos;
    }

    public class RecommendLiveInfoBean {

        //
        private String roomId;
        //
        private boolean sellGoods;
        //
        private String portrait;
        //
        private String anchorNickName;
        //
        private String coverUrl;
        //
        private long charmValue;
        //
        private long fansCount;
        //
        private long audienceCount;
        //
        private int fansState;
        //
        private String pushUrl;
        //
        private String playerUrl;
        //
        private String avChatRoomId;

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public boolean isSellGoods() {
            return sellGoods;
        }

        public void setSellGoods(boolean sellGoods) {
            this.sellGoods = sellGoods;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getAnchorNickName() {
            return anchorNickName;
        }

        public void setAnchorNickName(String anchorNickName) {
            this.anchorNickName = anchorNickName;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public long getCharmValue() {
            return charmValue;
        }

        public void setCharmValue(long charmValue) {
            this.charmValue = charmValue;
        }

        public long getFansCount() {
            return fansCount;
        }

        public void setFansCount(long fansCount) {
            this.fansCount = fansCount;
        }

        public long getAudienceCount() {
            return audienceCount;
        }

        public void setAudienceCount(long audienceCount) {
            this.audienceCount = audienceCount;
        }

        public int getFansState() {
            return fansState;
        }

        public void setFansState(int fansState) {
            this.fansState = fansState;
        }

        public String getPushUrl() {
            return pushUrl;
        }

        public void setPushUrl(String pushUrl) {
            this.pushUrl = pushUrl;
        }

        public String getPlayerUrl() {
            return playerUrl;
        }

        public void setPlayerUrl(String playerUrl) {
            this.playerUrl = playerUrl;
        }

        public String getAvChatRoomId() {
            return avChatRoomId;
        }

        public void setAvChatRoomId(String avChatRoomId) {
            this.avChatRoomId = avChatRoomId;
        }
    }

}

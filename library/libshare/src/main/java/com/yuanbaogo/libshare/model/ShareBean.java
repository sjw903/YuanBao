package com.yuanbaogo.libshare.model;

import android.graphics.Bitmap;

/**
 * @Description: 分享内容
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/20/21 1:58 PM
 * @Modifier:
 * @Modify:
 */
public class ShareBean {

    private boolean isWeixinCircle = true;//是否显示 朋友圈
    private boolean isWeixin = true;//是否显示 微信
    private boolean isFriend = true;//是否显示 好友
    private boolean isCommunity = true;//是否显示 社群
    private boolean isReport = true;//是否显示 举报
    private boolean isNotInterested = true;//是否显示 不感兴趣
    private boolean isDelete = false;//是否显示 删除 默认不显示

    private String title;//分享标题
    private String message;//分享描述
    private String umWebUrl = "https://www.baidu.com";//分享链接 链接必须是http开头
    private String umImgUrl;//分享网络图片
    private String umImgFile;//分享本地文件
    private Integer umImgMipmap;//分享资源文件
    private Bitmap umImgBitmap;//分享bitmap文件
    private String umVideoUrl;//分享视频 只能使用网络视频
    private String umMinUrl;//分享小程序 兼容低版本的网页链接
    private String umMinPath;//分享小程序 小程序页面路径
    private String umMinUserName;//分享小程序 小程序原始id,在微信平台查询

    private Bitmap wxcode;//微信小程序二维码

    public boolean isWeixinCircle() {
        return isWeixinCircle;
    }

    public ShareBean setWeixinCircle(boolean weixinCircle) {
        isWeixinCircle = weixinCircle;
        return this;
    }

    public boolean isWeixin() {
        return isWeixin;
    }

    public ShareBean setWeixin(boolean weixin) {
        isWeixin = weixin;
        return this;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public ShareBean setFriend(boolean friend) {
        isFriend = friend;
        return this;
    }

    public boolean isCommunity() {
        return isCommunity;
    }

    public ShareBean setCommunity(boolean community) {
        isCommunity = community;
        return this;
    }

    public boolean isReport() {
        return isReport;
    }

    public ShareBean setReport(boolean report) {
        isReport = report;
        return this;
    }

    public boolean isNotInterested() {
        return isNotInterested;
    }

    public ShareBean setNotInterested(boolean notInterested) {
        isNotInterested = notInterested;
        return this;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public ShareBean setDelete(boolean delete) {
        isDelete = delete;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ShareBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ShareBean setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUmWebUrl() {
        return umWebUrl;
    }

    public ShareBean setUmWebUrl(String umWebUrl) {
        this.umWebUrl = umWebUrl;
        return this;
    }

    public String getUmImgUrl() {
        return umImgUrl;
    }

    public ShareBean setUmImgUrl(String umImgUrl) {
        this.umImgUrl = umImgUrl;
        return this;
    }

    public String getUmImgFile() {
        return umImgFile;
    }

    public ShareBean setUmImgFile(String umImgFile) {
        this.umImgFile = umImgFile;
        return this;
    }

    public Integer getUmImgMipmap() {
        return umImgMipmap;
    }

    public ShareBean setUmImgMipmap(Integer umImgMipmap) {
        this.umImgMipmap = umImgMipmap;
        return this;
    }

    public Bitmap getUmImgBitmap() {
        return umImgBitmap;
    }

    public void setUmImgBitmap(Bitmap umImgBitmap) {
        this.umImgBitmap = umImgBitmap;
    }

    public String getUmVideoUrl() {
        return umVideoUrl;
    }

    public ShareBean setUmVideoUrl(String umVideoUrl) {
        this.umVideoUrl = umVideoUrl;
        return this;
    }

    public String getUmMinUrl() {
        return umMinUrl;
    }

    public ShareBean setUmMinUrl(String umMinUrl) {
        this.umMinUrl = umMinUrl;
        return this;
    }

    public String getUmMinPath() {
        return umMinPath;
    }

    public ShareBean setUmMinPath(String umMinPath) {
        this.umMinPath = umMinPath;
        return this;
    }

    public String getUmMinUserName() {
        return umMinUserName;
    }

    public ShareBean setUmMinUserName(String umMinUserName) {
        this.umMinUserName = umMinUserName;
        return this;
    }

    public Bitmap getWxcode() {
        return wxcode;
    }

    public ShareBean setWxcode(Bitmap wxcode) {
        this.wxcode = wxcode;
        return this;
    }
}

package com.yuanbaogo.homevideo.shortvideo.report.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 功能：
 * 时间 ： 2021/8/19:.
 * 作者：11190
 * @author 11190
 */

public class ReportRequestModel implements Parcelable {

    private String authorId;//作者Id
    private String authorNickName;
    private String bizId;//举报业务ID
    private String bizTitle;//举报业务简介(短视频标题)
    private String content;//内容
    private String description;
    private String lookerId;
    private String screenshots;//截图
    private String tagName;//举报标签

    public String getBizTitle() {
        return bizTitle;
    }

    public void setBizTitle(String bizTitle) {
        this.bizTitle = bizTitle;
    }

    public String getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(String screenshots) {
        this.screenshots = screenshots;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorNickName() {
        return authorNickName;
    }

    public void setAuthorNickName(String authorNickName) {
        this.authorNickName = authorNickName;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLookerId() {
        return lookerId;
    }

    public void setLookerId(String lookerId) {
        this.lookerId = lookerId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.authorId);
        dest.writeString(this.authorNickName);
        dest.writeString(this.bizId);
        dest.writeString(this.content);
        dest.writeString(this.description);
        dest.writeString(this.lookerId);
        dest.writeString(this.tagName);
    }

    public void readFromParcel(Parcel source) {
        this.authorId = source.readString();
        this.authorNickName = source.readString();
        this.bizId = source.readString();
        this.content = source.readString();
        this.description = source.readString();
        this.lookerId = source.readString();
        this.tagName = source.readString();
    }

    public ReportRequestModel() {
    }

    protected ReportRequestModel(Parcel in) {
        this.authorId = in.readString();
        this.authorNickName = in.readString();
        this.bizId = in.readString();
        this.content = in.readString();
        this.description = in.readString();
        this.lookerId = in.readString();
        this.tagName = in.readString();
    }

    public static final Parcelable.Creator<ReportRequestModel> CREATOR = new Parcelable.Creator<ReportRequestModel>() {
        @Override
        public ReportRequestModel createFromParcel(Parcel source) {
            return new ReportRequestModel(source);
        }

        @Override
        public ReportRequestModel[] newArray(int size) {
            return new ReportRequestModel[size];
        }
    };
}

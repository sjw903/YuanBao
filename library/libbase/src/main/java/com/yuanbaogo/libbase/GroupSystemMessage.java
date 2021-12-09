package com.yuanbaogo.libbase;


/**
 * 群系统消息
 *
 * @author 肖立彤
 * @why
 * @how
 * @date 2021/08/31
 */

public class GroupSystemMessage {
    private String code;
    private String data;
    private String anchorData;
    private String audienceData;


    public String getAnchorData() {
        return anchorData;
    }

    public void setAnchorData(String anchorData) {
        this.anchorData = anchorData;
    }

    public String getAudienceData() {
        return audienceData;
    }

    public void setAudienceData(String audienceData) {
        this.audienceData = audienceData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

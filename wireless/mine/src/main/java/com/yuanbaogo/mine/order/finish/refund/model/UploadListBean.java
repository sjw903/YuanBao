package com.yuanbaogo.mine.order.finish.refund.model;

/**
 * @Description: 上传图片
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/13/21 5:26 PM
 * @Modifier:
 * @Modify:
 */
public class UploadListBean {

//[
//    {
//        "thumbHeight":400,
//            "fileName":"1437346670651396096.jpg",
//            "fileUrl":"https://ybsjds-oss.oss-cn-beijing.aliyuncs.com/entertainment/1437346670651396096.jpg",
//            "thumbWidth":300,
//            "thumbUrl":"https://ybsjds-oss.oss-cn-beijing.aliyuncs.com/entertainment/thumb/1437346670651396096.jpg"
//    }
//]

    private String fileName;
    private String fileUrl;
    private String thumbUrl;
    private String thumbHeight;
    private String thumbWidth;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getThumbHeight() {
        return thumbHeight;
    }

    public void setThumbHeight(String thumbHeight) {
        this.thumbHeight = thumbHeight;
    }

    public String getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(String thumbWidth) {
        this.thumbWidth = thumbWidth;
    }
}

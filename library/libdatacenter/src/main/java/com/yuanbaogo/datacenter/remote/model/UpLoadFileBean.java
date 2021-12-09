package com.yuanbaogo.datacenter.remote.model;

public class UpLoadFileBean {

    String fileKey;
    String fileName;
    String mediaType;
    String path;

    public UpLoadFileBean(String fileKey, String fileName, String mediaType, String path) {
        this.fileKey = fileKey;
        this.fileName = fileName;
        this.mediaType = mediaType;
        this.path = path;
    }

    public UpLoadFileBean(String fileName, String mediaType, String path) {
        this.fileName = fileName;
        this.mediaType = mediaType;
        this.path = path;
    }

    public UpLoadFileBean() {
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "UpLoadFileBean{" +
                "fileKey='" + fileKey + '\'' +
                ", fileName='" + fileName + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}

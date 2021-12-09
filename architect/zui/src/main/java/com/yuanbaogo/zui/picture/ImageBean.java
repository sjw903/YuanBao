package com.yuanbaogo.zui.picture;

public class ImageBean {

    //图片名称
    private String name;
    //图片路径
    private String path;
    //图片类型
    private String type="-1";//表示本地图片
    //图片ID
    private String imageId;
    public ImageBean(String name, String path){
        this.name = name;
        this.path = path;
    }
    public ImageBean(String name, String path, String type){
        this.name = name;
        this.path = path;
        this.type=type;
    }
    public ImageBean(String name, String path, String type, String imageId) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", type='" + type + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }
}

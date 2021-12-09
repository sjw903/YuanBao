package com.yuanbaogo.datacenter.url.file;

import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUrl {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //外部sd卡
    public static final String DCMI_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();

    //获取文件夹夹路径
    public static final String CAMERA_IMAGE_PATH = DCMI_PATH + "/simaier/";

    public static final String CAMERA_IMAGE_PATH_SHARE = DCMI_PATH + "/share/";

    //获取后缀名
    public String getFileName() {
        Date date = new Date();
        String sim = dateFormat.format(date);
        return sim + ".mp4";
    }

}


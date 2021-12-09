package com.tencent.qcloud.ugckit.module.picker.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.tencent.liteav.basic.log.TXCLog;
import com.yuanbaogo.libbase.baseutil.ImageUtils;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class PickerManagerKit {
    private static final String TAG = "PickerManagerKit";

    private static PickerManagerKit sInstance;
    private final Context mContext;
    private final ContentResolver mContentResolver;
    private final Uri mUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

    public static PickerManagerKit getInstance(@NonNull Context context) {
        if (sInstance == null)
            sInstance = new PickerManagerKit(context);
        return sInstance;
    }

    private PickerManagerKit(@NonNull Context context) {
        mContext = context.getApplicationContext();
        mContentResolver = context.getApplicationContext().getContentResolver();
    }

    @NonNull
    public ArrayList<TCVideoFileInfo> getAllVideo() {
        ArrayList<TCVideoFileInfo> videos = new ArrayList<TCVideoFileInfo>();
        String[] mediaColumns = new String[]{
                MediaStore.Video.VideoColumns._ID,
                //DATA 数据在 Android Q 以前代表了文件的路径，但在 Android Q上该路径无法被访问。
                MediaStore.Video.VideoColumns.DATA,
                MediaStore.Video.VideoColumns.DISPLAY_NAME,
                MediaStore.Video.VideoColumns.DURATION
        };
        Cursor cursor = mContentResolver.query(mUri, mediaColumns, null, null, null);

        if (cursor == null) return videos;

        if (cursor.moveToFirst()) {
            do {
                TCVideoFileInfo fileItem = new TCVideoFileInfo();
                // 兼容 Android 10以上
                Uri uri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, cursor.getLong(cursor.getColumnIndexOrThrow((MediaStore.Video.Media._ID))));
                fileItem.setFileUri(uri);
                fileItem.setFilePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
                fileItem.setFileName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                if (duration < 0)
                    duration = 0;
                fileItem.setDuration(duration);

                if (fileItem.getFilePath() != null && fileItem.getFilePath().endsWith(".mp4")) {
                    if(fileItem.getDuration() <=300000){//小于5分钟视频显示
                        videos.add(fileItem);
                    }
                }
                TXCLog.d(TAG, "fileItem = " + fileItem.toString());
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return videos;
    }

    @NonNull
    public ArrayList<TCVideoFileInfo> getAllPictrue() {
        ArrayList<TCVideoFileInfo> pictureList = new ArrayList<TCVideoFileInfo>();
        String[] mediaColumns = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                //DATA 数据在 Android Q 以前代表了文件的路径，但在 Android Q上该路径无法被访问。
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DESCRIPTION
        };
        DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
        int screenHeight = ScreenUtils.getScreenHeight(mContext);
        int screenWidth = ScreenUtils.getScreenWidth(mContext);
        float ratioHW = screenHeight / (float)screenWidth;
        Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                TCVideoFileInfo fileItem = new TCVideoFileInfo();
                // 兼容 Android 10以上
                Uri uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(cursor.getColumnIndexOrThrow((MediaStore.Images.Media._ID))));
                fileItem.setFileUri(uri);
                fileItem.setFilePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                fileItem.setFileName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));
                fileItem.setFileType(TCVideoFileInfo.FILE_TYPE_PICTURE);
                fileItem.setDuration(2000);//TODO HG 设置图片时长为2秒（2000ms）
                int[] size = ImageUtils.getSize(fileItem.getFilePath());
                if(size!=null&&size.length==2){
                    if(size[1]/(float)size[0]<=ratioHW*1.3){
                        pictureList.add(fileItem);
                    }
                }else {
                    pictureList.add(fileItem);
                }
            }
            cursor.close();
        }
        return pictureList;
    }
}
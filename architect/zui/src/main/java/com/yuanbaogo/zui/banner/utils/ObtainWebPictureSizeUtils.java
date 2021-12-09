package com.yuanbaogo.zui.banner.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 获取图片 宽高
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/26/21 4:23 PM
 * @Modifier:
 * @Modify:
 */
public class ObtainWebPictureSizeUtils {

    static Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    static ExecutorService mPicFixThreadPool = Executors.newCachedThreadPool();

    /**
     * 获取网络图片
     *
     * @param url
     * @param listener
     */
    public static void getUrlPicSize(String url, OnPicListener listener) {
        mPicFixThreadPool.execute(() -> {
            HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                InputStream inputStream = connection.getInputStream();
                int[] imageSize = getImageSize(inputStream);
                handler.post(() -> listener.onImageSize(imageSize[0], imageSize[1]));
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取mipmap下图片
     *
     * @param context
     * @param mipmap
     * @param listener
     */
    public static void getMipmapPicSize(Context context, int mipmap, OnPicListener listener) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeResource(context.getResources(), mipmap, options);
        //获取图片的宽高
        int[] imageSize = new int[2];
        imageSize[0] = options.outWidth;
        imageSize[1] = options.outHeight;
        handler.post(() -> listener.onImageSize(imageSize[0], imageSize[1]));
    }

    private static int[] getImageSize(InputStream is) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        int[] size = new int[2];
        size[0] = options.outWidth;
        size[1] = options.outHeight;
        return size;
    }

    /**
     * 返回宽高
     */
    public interface OnPicListener {
        void onImageSize(int width, int height);
    }

}

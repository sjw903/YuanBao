package com.yuanbaogo.zui.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.picture.GlideEngine;

import java.util.List;

/**
 * 预览图片 风格
 */
public class PictureParameter {

    public static void PreviewImg(Context context, int position, List<LocalMedia> list) {
        PictureSelector.create((Activity) context)
                .themeStyle(R.style.picture_default_style) // xml设置主题
                .setPictureStyle(getDefaultStyle(context))// 动态自定义相册主题
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isNotPreviewDownload(false)// 预览图片长按是否可以下载
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .openExternalPreview(position, list);
    }

    public static PictureParameterStyle getDefaultStyle(Context context) {
        // 相册主题
        com.luck.picture.lib.style.PictureParameterStyle mPictureParameterStyle = new com.luck.picture.lib.style.PictureParameterStyle();
        // 是否改变状态栏字体颜色(黑白切换)
        mPictureParameterStyle.isChangeStatusBarFontColor = false;
        // 相册状态栏背景色
        mPictureParameterStyle.pictureStatusBarColor = Color.parseColor("#393a3e");
        // 相册列表标题栏背景色
        mPictureParameterStyle.pictureTitleBarBackgroundColor = Color.parseColor("#393a3e");
        // 相册父容器背景色
        mPictureParameterStyle.pictureContainerBackgroundColor = ContextCompat.getColor(context, R.color.color424242);
        // 相册返回箭头
        mPictureParameterStyle.pictureLeftBackIcon = R.drawable.picture_icon_back;
        // 标题栏字体颜色
        mPictureParameterStyle.pictureTitleTextColor = ContextCompat.getColor(context, R.color.picture_color_white);
        return mPictureParameterStyle;
    }

}

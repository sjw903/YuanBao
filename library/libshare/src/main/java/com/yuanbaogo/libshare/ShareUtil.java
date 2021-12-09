package com.yuanbaogo.libshare;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.yuanbaogo.libshare.model.ShareBean;

import java.io.File;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/20/21 4:17 PM
 * @Modifier:
 * @Modify:
 */
public class ShareUtil {

    private static ShareUtil shareUtil = null;

    FragmentActivity fragmentActivity;

    OnCallShare onCallShare;

    public ShareUtil setOnCallShare(OnCallShare onCallShare) {
        this.onCallShare = onCallShare;
        return this;
    }

    public ShareUtil shareUtils(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
        return this;
    }

    public ShareUtil shareUtilss(Activity mActivity) {
        this.fragmentActivity = (FragmentActivity) mActivity;
        return this;
    }

    public static ShareUtil getShareUtils() {
        if (shareUtil == null) {
            synchronized (ShareUtil.class) {
                if (shareUtil == null) {
                    shareUtil = new ShareUtil();
                }
            }
        }
        return shareUtil;
    }

    /**
     * 分享链接
     *
     * @param web
     */
    public void setUmWeb(UMWeb web, SHARE_MEDIA share_media) {
        new ShareAction(fragmentActivity)
                .setPlatform(share_media)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    public UMWeb umWeb(ShareBean shareBean) {
        UMWeb umWeb = new UMWeb(shareBean.getUmWebUrl()); //切记切记  链接必须是http开头
        umWeb.setTitle(shareBean.getTitle());//标题
        UMImage umImage = new UMImage(fragmentActivity, R.mipmap.ic_launcher);//分享图标
        umWeb.setThumb(umImage);
        umWeb.setDescription(shareBean.getMessage());//描述
        return umWeb;
    }

    /**
     * 分享图片
     *
     * @param share_media
     */
    public void setUMImage(UMImage umImage, SHARE_MEDIA share_media) {
        new ShareAction(fragmentActivity)
                .setPlatform(share_media)
                .withMedia(umImage)
                .setCallback(umShareListener)
                .share();
    }

    public UMImage umImage(ShareBean shareBean) {
        UMImage umImage = null;
        UMImage thumb = new UMImage(fragmentActivity, R.mipmap.ic_launcher);//设置缩略图
        if (!TextUtils.isEmpty(shareBean.getUmImgUrl())) {
            umImage = new UMImage(fragmentActivity, shareBean.getUmImgUrl());//网络图片
            thumb = new UMImage(fragmentActivity, shareBean.getUmImgUrl());//设置缩略图
        }
        if (!TextUtils.isEmpty(shareBean.getUmImgFile())) {
            File file = new File(shareBean.getUmImgFile());
            umImage = new UMImage(fragmentActivity, file);//本地文件
            thumb = new UMImage(fragmentActivity, file);//设置缩略图
        }
        if (shareBean.getUmImgMipmap() != null) {
            umImage = new UMImage(fragmentActivity, shareBean.getUmImgMipmap());//资源文件
            thumb = new UMImage(fragmentActivity, shareBean.getUmImgMipmap());//设置缩略图
        }
        if (shareBean.getUmImgBitmap() != null) {
            umImage = new UMImage(fragmentActivity, shareBean.getUmImgBitmap());//bitmap文件
            thumb = new UMImage(fragmentActivity, shareBean.getUmImgBitmap());//设置缩略图
        }
        umImage.setThumb(thumb);
        umImage.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        return umImage;
    }

    /**
     * 分享文字
     *
     * @param shareBean
     * @param share_media
     * @return
     */
    public ShareUtil setText(ShareBean shareBean, SHARE_MEDIA share_media) {
        new ShareAction(fragmentActivity)
                .setPlatform(share_media)
                .withText(shareBean.getTitle())
                .setCallback(umShareListener)
                .share();
        return this;
    }

    /**
     * 分享视频
     *
     * @param share_media
     */
    public void setUMVideo(UMVideo umVideo, SHARE_MEDIA share_media) {
        new ShareAction(fragmentActivity)
                .setPlatform(share_media)
                .withMedia(umVideo)
                .setCallback(umShareListener)
                .share();
    }

    public UMVideo umVideo(ShareBean shareBean) {
        UMVideo umVideo = new UMVideo(shareBean.getUmVideoUrl());
        umVideo.setTitle(shareBean.getTitle());//视频的标题
        UMImage thumb = new UMImage(fragmentActivity, R.mipmap.ic_launcher);//设置缩略图
        umVideo.setThumb(thumb);//视频的缩略图
        umVideo.setDescription(shareBean.getMessage());//视频的描述
        return umVideo;
    }

    /**
     * 分享小程序
     *
     * @param min
     */
    public void setUmMin(UMMin min, SHARE_MEDIA share_media) {
        new ShareAction(fragmentActivity)
                .withMedia(min)
                .setPlatform(share_media)
                .setCallback(umShareListener)
                .share();
    }

    public UMMin umMin(ShareBean shareBean) {
        //兼容低版本的网页链接
        UMMin umMin = new UMMin(shareBean.getUmMinUrl());
        UMImage thumb = null;
        if (!TextUtils.isEmpty(shareBean.getUmImgUrl())) {
            thumb = new UMImage(fragmentActivity, shareBean.getUmImgUrl());//设置缩略图
        }
        if (shareBean.getUmImgMipmap() != null) {
            // 小程序消息封面图片
            Bitmap bitmap = BitmapFactory.decodeResource(fragmentActivity.getResources(), shareBean.getUmImgMipmap());
            Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 600, 600, true);
            bitmap.recycle();
            thumb = new UMImage(fragmentActivity, sendBitmap);//设置缩略图
        }
        umMin.setThumb(thumb);
        // 小程序消息title
        umMin.setTitle(shareBean.getTitle());
        // 小程序消息描述
        umMin.setDescription(shareBean.getMessage());
        // 小程序页面路径
        umMin.setPath(shareBean.getUmMinPath());
        // 小程序原始id,在微信平台查询
        umMin.setUserName(shareBean.getUmMinUserName());
        return umMin;
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            onCallShare.onStartShares();
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            onCallShare.onResultShares();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param throwable 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable throwable) {
            onCallShare.onErrorShares();
            initErrorCode(platform, throwable);
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            onCallShare.onCancelShares();
        }
    };


    private void initErrorCode(SHARE_MEDIA platform, Throwable throwable) {
    }

    public interface OnCallShare {

        void onStartShares();

        void onResultShares();

        void onErrorShares();

        void onCancelShares();

    }

}

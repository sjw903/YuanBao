package com.yuanbaogo.homevideo.mine.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.animators.AnimationType;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.language.LanguageConfig;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.permissionx.guolindev.PermissionX;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.datacenter.url.file.FileUrl;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.mine.contract.PreviewPictureContract;
import com.yuanbaogo.homevideo.mine.presenter.PreviewPicturePresenter;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.dialog.SelectPicPopupWindow;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.picture.GlideEngine;
import com.yuanbaogo.zui.toast.ToastView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 这个类里面   东西需要封装 暂时先这样
 */
@Route(path = YBRouter.PREVIEW_PICTURE_ACTIVITY)
public class PreviewPictureActivity extends MvpBaseActivityImpl<PreviewPictureContract.View, PreviewPicturePresenter>
        implements PreviewPictureContract.View, View.OnClickListener {

    HeadView previewPictureHeadView;

    ImageView previewPictureImg;

    @Autowired(name = YBRouter.PreviewPictureActivityParams.url)
    String url;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_preview_picture;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        previewPictureHeadView = findViewById(R.id.preview_picture_head_view);
        previewPictureImg = findViewById(R.id.preview_picture_img);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        previewPictureHeadView.getLibHeadImgRight().setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        Glide.with(this).load(url).into(previewPictureImg);
    }

    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("个人封面")
                .setImgRight(true)
                .setImgRightIcon(R.mipmap.icon_head_more_black);
        previewPictureHeadView.setHead(headBean);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.lib_head_img_right) {
            // 头像
            selectPhoto("更换个人封面", "保存本地", 1);
        }
    }

    private List<String> mPhotoSelectTypes;

    private SelectPicPopupWindow mPhotoSelectPopupWindow;

    private void selectPhoto(String title, String title2, int type) {
        mPhotoSelectTypes = new ArrayList<>();
        mPhotoSelectTypes.clear();
        mPhotoSelectTypes.add(title);
        mPhotoSelectTypes.add(title2);
        mPhotoSelectPopupWindow = new SelectPicPopupWindow(this, "",
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mPhotoSelectPopupWindow.dismiss();
                        if (position == 0) {
                            if (type == 1) {
                                selectPhoto("从手机相册选择", "拍照片", 2);
                            } else if (type == 2) {
                                initGallery();
                            }
                        } else if (position == 1) {
                            if (type == 1) {
                                donwloadImg(getApplication(), url);
                                ToastView.showToast(mSaveMessage);
                            } else if (type == 2) {
                                PermissionX.init(PreviewPictureActivity.this)
                                        .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                                        .setDialogTintColor(Color.parseColor("#008577"), Color.parseColor("#83e8dd"))
                                        .onExplainRequestReason((scope, deniedList) -> {
                                            String message = "需要您同意以下权限才能正常使用";
                                            scope.showRequestReasonDialog(deniedList, message, "确定", "取消");
                                        })
                                        .request((allGranted, grantedList, deniedList) -> {
                                            if (allGranted) {
                                                initCamera();
                                            } else {
                                                ToastUtil.showToast("请先打开摄像头和存储权限");
                                            }
                                        });
                            }
                        }
                    }
                }, mPhotoSelectTypes);
        mPhotoSelectPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 选择图库照片
     */
    private void initGallery() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .setPictureWindowAnimationStyle(PictureWindowAnimationStyle.ofDefaultWindowAnimationStyle())// 自定义相册启动退出动画
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .isUseCustomCamera(false)// 是否使用自定义相机
                .setLanguage(LanguageConfig.CHINESE)// 设置语言，默认中文
                .isPageStrategy(true)// 是否开启分页策略 & 每页多少条；默认开启
                .setRecyclerAnimationMode(AnimationType.DEFAULT_ANIMATION)// 列表动画效果
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .maxVideoSelectNum(1) // 视频最大选择数量
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高,默认为false
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(false)// 是否可预览视频
                .isEnablePreviewAudio(false) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(true)// 是否裁剪
                .isCompress(true)// 是否压缩
                .synOrAsy(false)//同步true或异步false 压缩 默认同步
                .withAspectRatio(3, 2)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isOpenClickSound(true)// 是否开启点击声音
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 调起手机自带照相机
     */
    private void initCamera() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .isUseCustomCamera(false)// 是否使用自定义相机
                .setLanguage(LanguageConfig.CHINESE)// 设置语言，默认中文
                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(false)// 是否可预览视频
                .isEnablePreviewAudio(false) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(true)// 是否裁剪
                .isCompress(true)// 是否压缩
                .synOrAsy(false)//同步true或异步false 压缩 默认同步
                .withAspectRatio(3, 2)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isOpenClickSound(true)// 是否开启点击声音
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 通过URL保存图片   后期需要重新封装一下
     */
    private static Context context;
    private static String filePath;
    private static Bitmap mBitmap;
    public static String mSaveMessage = "图片保存成功！";

    public static void donwloadImg(Context contexts, String filePaths) {
        context = contexts;
        filePath = filePaths;
        new Thread(saveFileRunnable).start();
    }

    private static Runnable saveFileRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (!TextUtils.isEmpty(filePath)) { //网络图片
                    // 对资源链接
                    URL url = new URL(filePath);
                    //打开输入流
                    InputStream inputStream = url.openStream();
                    //对网上资源进行下载转换位图图片
                    mBitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                }
                saveFile(mBitmap);
                mSaveMessage = "图片保存成功！";
            } catch (IOException e) {
                mSaveMessage = "图片保存失败！";
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 保存图片
     *
     * @param bm
     * @throws IOException
     */
    public static void saveFile(Bitmap bm) throws IOException {
        File dirFile = new File(FileUrl.CAMERA_IMAGE_PATH_SHARE);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        String fileName = UUID.randomUUID().toString() + ".jpg";
        File myCaptureFile = new File(dirFile + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
        //把图片保存后声明这个广播事件通知系统相册有新图片到来
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    List<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == RESULT_OK) {
            // 图片选择结果回调
            selectList = PictureSelector.obtainMultipleResult(data);
            for (LocalMedia local : selectList) {
                File file = new File(local.getCompressPath());
                mPresenter.getUpdatePortrait(local.getCompressPath(), file, "0");
            }
        }
    }

    @Override
    public void setUpdatePortrait(String url) {
        Glide.with(this).load(url).into(previewPictureImg);
    }
}
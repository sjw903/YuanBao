package com.yuanbaogo.mine.personal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.github.gzuliyujiang.wheelpicker.AddressPicker;
import com.github.gzuliyujiang.wheelpicker.annotation.AddressMode;
import com.github.gzuliyujiang.wheelpicker.contract.OnAddressPickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.CityEntity;
import com.github.gzuliyujiang.wheelpicker.entity.CountyEntity;
import com.github.gzuliyujiang.wheelpicker.entity.ProvinceEntity;
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
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.personal.model.PersonalSubmitBean;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.dialog.SelectPicPopupWindow;
import com.yuanbaogo.zui.picture.GlideEngine;
import com.yuanbaogo.zui.view.CircleImageView;
import com.yuanbaogo.zui.view.SettingItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Route(path = YBRouter.EDIT_MINE_ACTIVITY)
public class EditMineActivity extends MvpBaseActivityImpl<EditMineContract.View, EditMinePresenter>
        implements EditMineContract.View, View.OnClickListener, OnAddressPickedListener {

    private LinearLayout mEditLlHead;
    private CircleImageView mEditCivHead;
    private SettingItem mEditSiName;
    private SettingItem mEditSiArea;
    private SettingItem mEditSiSignature;

    private String address;

    private String nickName;

    private String signature;

    private String portraitUrl;

    private String mProvince, mCity, mCounty;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_edit_mine;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mEditLlHead = findViewById(R.id.edit_ll_head);
        mEditCivHead = findViewById(R.id.edit_civ_head);
        mEditSiName = findViewById(R.id.edit_si_name);
        mEditSiArea = findViewById(R.id.edit_si_area);
        mEditSiSignature = findViewById(R.id.edit_si_signature);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mEditLlHead.setOnClickListener(this);
        mEditSiName.setOnClickListener(this);
        mEditSiArea.setOnClickListener(this);
        mEditSiSignature.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        address = getIntent().getStringExtra(YBRouter.EditMineActivityParams.address);
        nickName = getIntent().getStringExtra(YBRouter.EditMineActivityParams.nickName);
        signature = getIntent().getStringExtra(YBRouter.EditMineActivityParams.signature);
        portraitUrl = getIntent().getStringExtra(YBRouter.EditMineActivityParams.portraitUrl);

        mEditSiName.setSubNameText(nickName);
        mEditSiArea.setSubNameText(address);
        mEditSiSignature.setSubNameText(signature);
        Glide.with(this).load(portraitUrl).into(mEditCivHead);

        String[] addressList = address.split(" ");
        if (addressList.length == 3) {
            mProvince = addressList[0];
            mCity = addressList[1];
            mCounty = addressList[2];
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.edit_ll_head) {
            // 头像
            selectPhoto();
        } else if (id == R.id.edit_si_name) {
            // 昵称
            RouterHelper.toUpdateName(this, 300, nickName);
        } else if (id == R.id.edit_si_area) {
            // 地区
            // 选择具体区域
            AddressPicker picker = new AddressPicker(this);
            picker.setDefaultValue(mProvince, mCity, mCounty);
            picker.setAddressMode(AddressMode.PROVINCE_CITY_COUNTY);
            picker.setOnAddressPickedListener(this);
            picker.show();
        } else if (id == R.id.edit_si_signature) {
            // 个性签名
            RouterHelper.toUpdateSignature(this, 400, signature);
        }
    }

    @Override
    public void onAddressPicked(ProvinceEntity province, CityEntity city, CountyEntity county) {
        mProvince = province.getName();
        mCity = city != null ? city.getName() : mProvince;
        mCounty = county != null ? county.getName() : mProvince;
        mEditSiArea.setSubNameText(String.format("%s %s %s", mProvince, mCity, mCounty));
        mPresenter.getPersonalSubmit(new PersonalSubmitBean()
                .setAddress(String.format("%s %s %s", mProvince, mCity, mCounty)));
    }

    @Override
    public void setPersonalSubmit(String bean) {

    }

    @Override
    public void initPersonalSubmit() {

    }

    @Override
    public void setUpdatePortrait(String url) {

    }

    private List<String> mPhotoSelectTypes;

    private SelectPicPopupWindow mPhotoSelectPopupWindow;

    private void selectPhoto() {
        mPhotoSelectTypes = new ArrayList<>();
        mPhotoSelectTypes.add("拍照");
        mPhotoSelectTypes.add("相册选择");
        mPhotoSelectPopupWindow = new SelectPicPopupWindow(this,"",
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mPhotoSelectPopupWindow.dismiss();
                        if (position == 0) {  //拍照
                            PermissionX.init(EditMineActivity.this)
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

                        } else if (position == 1) {   //相册
                            initGallery();
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
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
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
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isOpenClickSound(true)// 是否开启点击声音
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    List<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300) {
            if (data != null) {
                nickName = data.getStringExtra("nickName");
                mEditSiName.setSubNameText(nickName);
            }
        } else if (requestCode == 400) {
            if (data != null) {
                signature = data.getStringExtra("signature");
                mEditSiSignature.setSubNameText(signature);
            }
        } else if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == RESULT_OK) {
            // 图片选择结果回调
            selectList = PictureSelector.obtainMultipleResult(data);
            for (LocalMedia local : selectList) {
                File file = new File(local.getCompressPath());
                Glide.with(this).load(file).into(mEditCivHead);
                mPresenter.getUpdatePortrait(local.getCompressPath(), file, "0");
            }
        }
    }

}
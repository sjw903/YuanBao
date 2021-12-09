package com.yuanbaogo.homevideo.live.auth.view;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.permissionx.guolindev.PermissionX;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.constant.WebUrls;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.homevideo.live.auth.contract.PersonalAuthContract;
import com.yuanbaogo.homevideo.live.auth.presenter.PersonalAuthPresenter;
import com.yuanbaogo.homevideo.live.push.view.PushMainActivity;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.libbase.baseutil.DensityUtil;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.video.R;
import com.yuanbaogo.zui.dialog.SelectPicPopupWindow;
import com.yuanbaogo.zui.dialog.StatusShipPop;
import com.yuanbaogo.zui.picture.GlideEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PersonalAuthActivity extends MvpBaseActivityImpl<PersonalAuthContract.View, PersonalAuthPresenter> implements PersonalAuthContract.View, View.OnClickListener {

    private ImageView mBack;
    private ImageView mIdCardBack;
    private ImageView mIdCardFront;
    private TextView mAgreement;
    private TextView mSubmit;
    private EditText mEtNameView;
    private EditText mEtCardView;
    private EditText mEtPhoneView;
    private SelectPicPopupWindow mPhotoSelectPopupWindow;
    private List<String> mPhotoSelectTypes;
    private File photoFile = null;
    private String photoFileAbsolutePath = null;


    /**
     * 请求相册
     */
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0X01;
    /**
     * 请求相机
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 0X02;
    /**
     * 请求裁剪
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCROP = 0X03;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.video_activity_personal_auth;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mBack = findViewById(R.id.iv_back);
        mAgreement = findViewById(R.id.tv_agreement);
        mEtNameView = findViewById(R.id.et_name_view);
        mEtCardView = findViewById(R.id.et_id_card_view);
        mEtPhoneView = findViewById(R.id.et_phone_view);
        mIdCardBack = findViewById(R.id.iv_photo_back);
        mIdCardFront = findViewById(R.id.iv_photo_front);
        mSubmit = findViewById(R.id.tv_submit);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mBack.setOnClickListener(this);
        mIdCardFront.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mIdCardBack.setOnClickListener(this);
        mAgreement.setOnClickListener(this);
        mEtNameView.addTextChangedListener(mPresenter.getTextWatcher());
        mEtPhoneView.addTextChangedListener(mPresenter.getTextWatcher());
        mEtCardView.addTextChangedListener(mPresenter.getTextWatcher());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
//        mLoginTimerVerification.setVisibility(View.GONE);
        mPresenter.validateInput();
        mEtPhoneView.setText(UserStore.getUserPhone());

        int width = (ScreenUtils.getScreenWidth(this)- DensityUtil.dip2px(this,30))/2;
        int height = (int) (width*0.64);
        ViewGroup.LayoutParams lpf = mIdCardFront.getLayoutParams();
        lpf.width = width;
        lpf.height = height;
        mIdCardFront.setLayoutParams(lpf);
        ViewGroup.LayoutParams lp = mIdCardBack.getLayoutParams();
        lp.width = width;
        lp.height = height;
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) lp).setMargins(DensityUtil.dip2px(this,10), 0, 0, 0);
        }
        mIdCardBack.setLayoutParams(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

//            if (requestCode == REQUEST_CODE_GETIMAGE_BYCAMERA) {
//                if (photoFile == null) {
//                    if (TextUtils.isEmpty(photoFileAbsolutePath)) {
//                        ToastUtil.showToast("图片获取失败");
//                        return;
//                    } else {
//                        File photoFileTemp = new File(photoFileAbsolutePath);
//                        if (!photoFileTemp.isFile() || !photoFileTemp.exists()) {
//                            ToastUtil.showToast("图片获取失败");
//                            return;
//                        } else {
//                            photoFile = new File(photoFileAbsolutePath);
//                        }
//                    }
//                }
//                mPresenter.compressAndGenImage(photoFile);
////                mPresenter.uploadPic(file.getAbsolutePath(), file);
//            } else if (requestCode == REQUEST_CODE_GETIMAGE_BYSDCARD) {
//                Uri selectedImage = data.getData();
//                String name = FileUtils.getRealFilePath(this, selectedImage);
//                if (name != null) {
//                    File file = new File(name);
//                    if (file.exists()) {
////                        mPresenter.uploadPic(file.getAbsolutePath(), file);
//                    } else {
//                        ToastUtil.showToast("图片获取失败");
//                    }
//                }
//            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.tv_agreement) {
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            RouterHelper.toWebJs(WebUrls.proImclause, true);
        } else if (id == R.id.iv_photo_back) {
            mPresenter.setCurrentPhoto("back");
            selectPhoto();
        } else if (id == R.id.iv_photo_front) {
            mPresenter.setCurrentPhoto("front");
            selectPhoto();
        } else if (id == R.id.tv_submit) {
            mPresenter.comit();
        }
    }

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

                            PermissionX.init(PersonalAuthActivity.this)
                                    .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                                    .setDialogTintColor(Color.parseColor("#008577"), Color.parseColor("#83e8dd"))
                                    .onExplainRequestReason((scope, deniedList) -> {
                                        String message = "需要您同意以下权限才能正常使用";
                                        scope.showRequestReasonDialog(deniedList, message, "确定", "取消");
                                    })
                                    .request((allGranted, grantedList, deniedList) -> {
                                        if (allGranted) {


//                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                            if (intent.resolveActivity(PersonalAuthActivity.this.getPackageManager()) != null) {
//                                                photoFile = FileUtils.createImageFile();
//                                                photoFileAbsolutePath = photoFile.getAbsolutePath();
//                                                if (photoFile != null) {
////                                            Uri uri = ZRFileProvider.getUriForFile(photoFile);
//                                                    Uri uri = null;
//                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                                                        uri = FileProvider.getUriForFile(PersonalAuthActivity.this, "com.yuanbaogo.video.fileProvider", photoFile);
//                                                    } else {
//                                                        uri = Uri.fromFile(photoFile);
//                                                    }
//                                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                                                }
//                                            }
//                                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                                            startActivityForResult(intent, REQUEST_CODE_GETIMAGE_BYCAMERA);

                                            PictureSelector.create(PersonalAuthActivity.this)
                                                    .openCamera(PictureMimeType.ofImage())
                                                    .isCompress(true)
                                                    .loadImageEngine(GlideEngine.createGlideEngine())
                                                    .minimumCompressSize(100)
                                                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                                                        @Override
                                                        public void onResult(List<LocalMedia> result) {
                                                            String path = result.get(0).getCompressPath();
                                                            String mimeType = result.get(0).getMimeType();
                                                            if (!TextUtils.isEmpty(path)) {
                                                                File file = new File(path);
                                                                if (file.exists()) {
                                                                    mPresenter.uploadPic(path, file, mimeType);
                                                                } else {
                                                                    ToastUtil.showToast("图片获取失败");
                                                                }
                                                            }else{
                                                                ToastUtil.showToast("图片获取失败");
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancel() {
                                                        }
                                                    });

                                        } else {
                                            ToastUtil.showToast(R.string.livepusher_app_camera_mic);
                                        }
                                    });

                        } else if (position == 1) {   //相册
//                            Intent camera = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            int reqcode = REQUEST_CODE_GETIMAGE_BYSDCARD;
//                            startActivityForResult(camera, reqcode);

                            PictureSelector.create(PersonalAuthActivity.this).openGallery(PictureMimeType.ofImage())
                                    .maxSelectNum(1) // 最大选择个数
                                    .isCamera(false)
                                    .isCompress(true)
                                    .minimumCompressSize(100)
                                    .isCompress(true)
                                    .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                                        @Override
                                        public void onResult(List<LocalMedia> result) {
                                            String path = result.get(0).getCompressPath();
                                            String mimeType = result.get(0).getMimeType();
                                            if (!TextUtils.isEmpty(path)) {
                                                File file = new File(path);
                                                if (file.exists()) {
                                                    mPresenter.uploadPic(path, file, mimeType);
                                                } else {
                                                    ToastUtil.showToast("图片获取失败");
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancel() {

                                        }
                                    });
                        }
                    }
                }, mPhotoSelectTypes);
        mPhotoSelectPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public String getTextEtNameView() {
        return mEtNameView.getText().toString();
    }

    @Override
    public String getTextEtPhoneView() {
        return mEtPhoneView.getText().toString();
    }

    @Override
    public String getTextEtCardView() {
        return mEtCardView.getText().toString();
    }

    @Override
    public void setBtncomitStatus(String txt, boolean isEnable) {
        mSubmit.setText(txt);
        mSubmit.setEnabled(isEnable);
    }

    @Override
    public void showStatus(boolean isStatus) {
        if (isStatus) {
            StatusShipPop statusShipPop = new StatusShipPop(this, getResources().getDrawable(R.mipmap.auth_sucess), "认证成功");
            statusShipPop.setOutside(false);
            statusShipPop.setOnItemClickListener(new StatusShipPop.OnItemClickListener() {
                @Override
                public void onClickBack() {
                    //认证成功，点击关闭认证成功弹窗后结束当前认证页面，并跳转到开启直播页面
                    finish();
                    startActivity(new Intent(PersonalAuthActivity.this, PushMainActivity.class));
                }
            });
            statusShipPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        } else {
            new StatusShipPop(this, getResources().getDrawable(R.mipmap.auth_error), "认证失败")
                    .showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }

    }

    @Override
    public void setCardBackPicture(File file) {
        Glide.with(this).load(file).into(mIdCardBack);
    }

    @Override
    public void setCardFrontPicture(File file) {
        Glide.with(this).load(file).into(mIdCardFront);
    }
}

package com.yuanbaogo.zui.dialog;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.permissionx.guolindev.PermissionX;
import com.yuanbaogo.datacenter.url.file.FileUrl;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.libbase.config.ApplicationConfigHelper;
import com.yuanbaogo.libshare.ShareUtil;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.libshare.model.ShareParamete;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.utils.BitmapUtil;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;
import com.yuanbaogo.zui.toast.ToastView;

import java.io.File;

/**
 * @Description: 分享美图
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 5:01 PM
 * @Modifier:
 * @Modify:
 */
public class GenerateMitoDialogView extends DialogsFragment
        implements View.OnClickListener, ShareUtil.OnCallShare {

    View mView;

    TextView dialogShareViewTvCancel;

    private ShareBean shareBean;

    TextView itemShareViewTvWechat;

    TextView itemShareViewTvSave;

    ImageView dialogShareViewImgQr;

    TextView itemShareViewTvWechatCircle;

    RelativeLayout dialogShareViewRlShareSave;

    private File mShareImageFile;//分享图片文件

    RelativeLayout dialogShareViewRlShare;

    LinearLayout dialogShareViewLlShare;

    public GenerateMitoDialogView(ShareBean shareBean) {
        this.shareBean = shareBean;
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_generate_mito_view;
    }

    @Override
    protected void intViews(View view) {
        this.mView = view;
        dialogShareViewTvCancel = mView.findViewById(R.id.dialog_share_view_tv_cancel);
        itemShareViewTvWechat = mView.findViewById(R.id.item_share_view_tv_wechat);
        itemShareViewTvSave = mView.findViewById(R.id.item_share_view_tv_save);
        dialogShareViewImgQr = mView.findViewById(R.id.dialog_share_view_img_qr);
        itemShareViewTvWechatCircle = mView.findViewById(R.id.item_share_view_tv_wechat_circle);
        dialogShareViewRlShareSave = mView.findViewById(R.id.dialog_share_view_rl_share_save);
        dialogShareViewRlShare = mView.findViewById(R.id.dialog_share_view_rl_share);
        dialogShareViewLlShare = mView.findViewById(R.id.dialog_share_view_ll_share);
        startUpAnimation(mView);
    }

    @Override
    protected void setTexts() {
        dialogShareViewImgQr.setImageBitmap(shareBean.getWxcode());

        //获取屏幕宽高
        int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：1080px）
        int screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight(); // 屏幕高（像素，如：2292px）

        /*
         *设置底部分享高度
         */
        ViewGroup.MarginLayoutParams bottomParams = (ViewGroup.MarginLayoutParams)
                dialogShareViewRlShare.getLayoutParams();
        bottomParams.height = screenHeight / 5;
        dialogShareViewRlShare.setLayoutParams(bottomParams);

        /*
         *设置商品详情高度 宽度
         */
        ViewGroup.MarginLayoutParams topParams = (ViewGroup.MarginLayoutParams)
                dialogShareViewRlShareSave.getLayoutParams();
        topParams.height = screenHeight / 5 * 4;
        topParams.width = screenWidth - 40;
        dialogShareViewRlShareSave.setLayoutParams(topParams);

        if (screenHeight <= 1920) {

            ViewGroup.MarginLayoutParams shareParams = (ViewGroup.MarginLayoutParams)
                    dialogShareViewLlShare.getLayoutParams();
            shareParams.height = screenHeight / 5 / 3 * 2;
            dialogShareViewLlShare.setLayoutParams(shareParams);

            /*
             * 动态设置二维码展示位置
             */
            ViewGroup.MarginLayoutParams functionParams = (ViewGroup.MarginLayoutParams)
                    dialogShareViewImgQr.getLayoutParams();
            functionParams.width = screenWidth / 5;
            functionParams.height = screenWidth / 5;
            dialogShareViewImgQr.setLayoutParams(functionParams);

            ViewGroup.MarginLayoutParams cancelParams = (ViewGroup.MarginLayoutParams)
                    dialogShareViewTvCancel.getLayoutParams();
            cancelParams.bottomMargin = 10;
            cancelParams.height = screenHeight / 5 / 3;
            dialogShareViewTvCancel.setLayoutParams(cancelParams);

        } else {
            /*
             * 动态设置商品详情LOGO展示位置
             */
            ViewGroup.MarginLayoutParams functionParams = (ViewGroup.MarginLayoutParams)
                    dialogShareViewImgQr.getLayoutParams();
            functionParams.width = screenWidth / 4;
            functionParams.height = screenWidth / 4;
            dialogShareViewImgQr.setLayoutParams(functionParams);
        }

    }

    @Override
    protected void setOnClicks() {
        dialogShareViewTvCancel.setOnClickListener(this);
        itemShareViewTvWechat.setOnClickListener(this);
        itemShareViewTvSave.setOnClickListener(this);
        itemShareViewTvWechatCircle.setOnClickListener(this);
    }

    @Override
    public int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dialog_share_view_tv_cancel) {
            startDownAnimation(mView);
        } else if (id == R.id.item_share_view_tv_wechat) {
            saveCode(2, dialogShareViewRlShareSave);
            ShareUtil
                    .getShareUtils()
                    .shareUtils(getActivity())
                    .setOnCallShare(this)
                    .setUMImage(ShareUtil.getShareUtils().umImage(shareBean), ShareParamete.WEIXIN);
        } else if (id == R.id.item_share_view_tv_save) {
            PermissionX.init(getActivity())
                    .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .setDialogTintColor(Color.parseColor("#008577"), Color.parseColor("#83e8dd"))
                    .onExplainRequestReason((scope, deniedList) -> {
                        String message = "需要您同意以下权限才能正常使用";
                        scope.showRequestReasonDialog(deniedList, message, "确定", "取消");
                    })
                    .request((allGranted, grantedList, deniedList) -> {
                        if (allGranted) {
                            saveCode(1, dialogShareViewRlShareSave);
                        } else {
                            ToastUtil.showToast("请先打开存储权限");
                        }
                    });
        } else if (id == R.id.item_share_view_tv_wechat_circle) {
            saveCode(2, dialogShareViewRlShareSave);
            ShareUtil
                    .getShareUtils()
                    .shareUtils(getActivity())
                    .setOnCallShare(this)
                    .setUMImage(ShareUtil.getShareUtils().umImage(shareBean), ShareParamete.WEIXIN_CIRCLE);
        }
    }

    /**
     * 二维码保存本地
     */
    public void saveCode(int type, View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        //view的内容以图片的方式保存下来
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        if (type == 1) {
            File fileDir = new File(FileUrl.CAMERA_IMAGE_PATH_SHARE);//保存到本地的路径
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            mShareImageFile = new File(fileDir, fileName);
            boolean result = BitmapUtil.getInstance(ApplicationConfigHelper.mApplication)
                    .saveBitmap(bitmap, mShareImageFile, ApplicationConfigHelper.mApplication);
            if (result) {
                ToastView.showToast("已保存本地");
            } else {
                ToastView.showToast("未保存成功");
            }
        } else if (type == 2) {
            shareBean.setUmImgUrl(null);
            shareBean.setUmImgBitmap(bitmap);
        }
    }

    @Override
    public void onStartShares() {
    }

    @Override
    public void onResultShares() {
    }

    @Override
    public void onErrorShares() {
    }

    @Override
    public void onCancelShares() {
    }

}

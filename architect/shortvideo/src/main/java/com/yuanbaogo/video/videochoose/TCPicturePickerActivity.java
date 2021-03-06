package com.yuanbaogo.video.videochoose;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.tencent.qcloud.ugckit.UGCKitPicturePicker;
import com.tencent.qcloud.ugckit.module.picker.data.TCVideoFileInfo;
import com.tencent.qcloud.ugckit.module.picker.view.IPickerLayout;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.videochoose.contract.PicturePickerContract;
import com.yuanbaogo.video.videochoose.presenter.PicturePickerPresenter;
import com.yuanbaogo.video.videojoiner.TCPictureJoinActivity;

import java.util.ArrayList;

/**
 * 图片选择类，用于选择多张图片，返回图片路径集合S
 */
public class TCPicturePickerActivity extends MvpBaseActivityImpl<PicturePickerContract.View, PicturePickerPresenter>
    implements PicturePickerContract.View{

    private UGCKitPicturePicker mUGCKitPicturePicker;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        // 必须在代码中设置主题(setTheme)或者在AndroidManifest中设置主题(android:theme)
        setTheme(R.style.PickerActivityTheme);
        return R.layout.activity_ugc_video_list;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mUGCKitPicturePicker = (UGCKitPicturePicker) findViewById(R.id.picture_choose);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mUGCKitPicturePicker.getTitleBar().setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUGCKitPicturePicker.setOnPickerListener(new IPickerLayout.OnPickerListener() {
            @Override
            public void onPickedList(ArrayList<TCVideoFileInfo> list) {
                startPictureJoinActivity(list);
            }
        });
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    private void initWindowParam() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUGCKitPicturePicker.pauseRequestBitmap();
    }

    @Override
    public void onResume() {
        super.onResume();
        mUGCKitPicturePicker.resumeRequestBitmap();
    }

    /**
     * 跳转到图片合成视频界面，将多张图片添加转场动画后生成一个图频
     */
    private void startPictureJoinActivity(ArrayList<TCVideoFileInfo> fileInfoList) {
        ArrayList<String> picturePathList = new ArrayList<String>();
        for (TCVideoFileInfo info : fileInfoList) {
            if (Build.VERSION.SDK_INT >= 29) {
                picturePathList.add(info.getFileUri().toString());
            } else {
                picturePathList.add(info.getFilePath());
            }
        }
        Intent intent = new Intent(TCPicturePickerActivity.this, TCPictureJoinActivity.class);
        intent.putExtra(UGCKitConstants.INTENT_KEY_MULTI_PIC_LIST, picturePathList);
        startActivity(intent);
    }
}

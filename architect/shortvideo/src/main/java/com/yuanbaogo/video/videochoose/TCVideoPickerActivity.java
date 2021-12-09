package com.yuanbaogo.video.videochoose;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.tencent.qcloud.ugckit.UGCKitVideoPicker;
import com.tencent.qcloud.ugckit.module.picker.data.TCVideoFileInfo;
import com.tencent.qcloud.ugckit.module.picker.view.IPickerLayout;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.videochoose.contract.VideoPickerContract;
import com.yuanbaogo.video.videochoose.presenter.VideoPickerPresenter;
import com.yuanbaogo.video.videoeditor.TCVideoCutActivity;
import com.yuanbaogo.video.videojoiner.TCVideoJoinerActivity;

import java.util.ArrayList;

public class TCVideoPickerActivity extends MvpBaseActivityImpl<VideoPickerContract.View, VideoPickerPresenter>
    implements VideoPickerContract.View{
    private UGCKitVideoPicker mUGCKitVideoPicker;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        // 必须在代码中设置主题(setTheme)或者在AndroidManifest中设置主题(android:theme)
        setTheme(R.style.PickerActivityTheme);
        return R.layout.activity_video_picker;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mUGCKitVideoPicker = (UGCKitVideoPicker) findViewById(R.id.video_choose_layout);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mUGCKitVideoPicker.getTitleBar().setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUGCKitVideoPicker.setOnPickerListener(new IPickerLayout.OnPickerListener() {
            @Override
            public void onPickedList(ArrayList list) {
                int size = list.size();
                if (size == 0) {
                    return;
                } else if (size == 1) {
                    TCVideoFileInfo fileInfo = (TCVideoFileInfo) list.get(0);
                    startVideoCutActivity(fileInfo);
                } else {
                    startVideoJoinActivity(list);
                }
            }
        });
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mUGCKitVideoPicker.pauseRequestBitmap();
    }

    @Override
    public void onResume() {
        super.onResume();
        mUGCKitVideoPicker.resumeRequestBitmap();
    }

    public void startVideoCutActivity(TCVideoFileInfo fileInfo) {
        Intent intent = new Intent(this, TCVideoCutActivity.class);
        intent.putExtra(UGCKitConstants.VIDEO_PATH, fileInfo.getFilePath());
        startActivity(intent);
    }

    public void startVideoJoinActivity(ArrayList<TCVideoFileInfo> videoPathList) {
        Intent intent = new Intent(this, TCVideoJoinerActivity.class);
        intent.putExtra(UGCKitConstants.INTENT_KEY_MULTI_CHOOSE, videoPathList);
        startActivity(intent);
    }

}

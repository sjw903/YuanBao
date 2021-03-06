package com.yuanbaogo.video.videorecord;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.tencent.qcloud.ugckit.UGCKitVideoRecord;
import com.tencent.qcloud.ugckit.basic.UGCKitResult;
import com.tencent.qcloud.ugckit.module.effect.bgm.TCMusicActivity;
import com.tencent.qcloud.ugckit.module.record.MusicInfo;
import com.tencent.qcloud.ugckit.module.record.UGCKitRecordConfig;
import com.tencent.qcloud.ugckit.module.record.interfaces.IVideoRecordKit;
import com.tencent.qcloud.ugckit.utils.ToastUtil;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.videoeditor.TCVideoEditerActivity;
import com.yuanbaogo.video.videorecord.contract.VideoRecordContract;
import com.yuanbaogo.video.videorecord.presenter.VideoRecordPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

/**
 * 小视频录制界面
 *
 */
public class TCVideoRecordActivity extends MvpBaseActivityImpl<VideoRecordContract.View, VideoRecordPresenter>
        implements VideoRecordContract.View, ActivityCompat.OnRequestPermissionsResultCallback {

    private UGCKitVideoRecord mUGCKitVideoRecord;
    @Override
    protected int createContentView(Bundle savedInstanceState) {
        // 必须在代码中设置主题(setTheme)或者在AndroidManifest中设置主题(android:theme)
        setTheme(R.style.RecordActivityTheme);
        return R.layout.activity_video_record;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mUGCKitVideoRecord = findViewById(R.id.video_record_layout);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        UGCKitRecordConfig ugcKitRecordConfig = UGCKitRecordConfig.getInstance();
        mUGCKitVideoRecord.setConfig(ugcKitRecordConfig);
        mUGCKitVideoRecord.getTitleBar().setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUGCKitVideoRecord.setOnRecordListener(new IVideoRecordKit.OnRecordListener() {
            @Override
            public void onRecordCanceled() {
                finish();
            }

            @Override
            public void onRecordCompleted(UGCKitResult ugcKitResult) {
                if (ugcKitResult.errorCode == 0) {
                    startEditActivity();
                } else {
                    ToastUtil.toastShortMessage("record video failed. error code:" + ugcKitResult.errorCode + ",desc msg:" + ugcKitResult.descMsg);
                }
            }
        });
        mUGCKitVideoRecord.setOnMusicChooseListener(new IVideoRecordKit.OnMusicChooseListener() {
            @Override
            public void onChooseMusic(int position) {
                Intent bgmIntent = new Intent(TCVideoRecordActivity.this, TCMusicActivity.class);
                bgmIntent.putExtra(UGCKitConstants.MUSIC_POSITION, position);
                startActivityForResult(bgmIntent, UGCKitConstants.ACTIVITY_MUSIC_REQUEST_CODE);
            }
        });
    }

    private void initWindowParam() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void startEditActivity() {
        Intent intent = new Intent(this, TCVideoEditerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasPermission()) {
            mUGCKitVideoRecord.start();
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this, permissions.toArray(new String[0]), 100);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUGCKitVideoRecord.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUGCKitVideoRecord.release();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUGCKitVideoRecord.screenOrientationChange();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != UGCKitConstants.ACTIVITY_MUSIC_REQUEST_CODE) {
            return;
        }
        if (data == null) {
            return;
        }
        MusicInfo musicInfo = new MusicInfo();

        musicInfo.path = data.getStringExtra(UGCKitConstants.MUSIC_PATH);
        musicInfo.name = data.getStringExtra(UGCKitConstants.MUSIC_NAME);
        musicInfo.position = data.getIntExtra(UGCKitConstants.MUSIC_POSITION, -1);

        mUGCKitVideoRecord.setRecordMusicInfo(musicInfo);
    }

    @Override
    public void onBackPressed() {
        mUGCKitVideoRecord.backPressed();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mUGCKitVideoRecord.start();
        }
    }
}
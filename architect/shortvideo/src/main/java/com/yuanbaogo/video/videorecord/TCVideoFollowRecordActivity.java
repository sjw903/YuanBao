package com.yuanbaogo.video.videorecord;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.tencent.qcloud.ugckit.UGCKitVideoMixRecord;
import com.tencent.qcloud.ugckit.basic.UGCKitResult;
import com.tencent.qcloud.ugckit.module.mixrecord.IVideoMixRecordKit;
import com.tencent.qcloud.ugckit.module.mixrecord.MixRecordActionData;
import com.tencent.qcloud.ugckit.module.mixrecord.MixRecordConfigBuildInfo;
import com.tencent.ugc.TXRecordCommon;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.videochoose.TCTripleRecordVideoPickerActivity;
import com.yuanbaogo.video.videoeditor.TCVideoEditerActivity;

import java.util.ArrayList;
import java.util.List;

import static com.yuanbaogo.video.videorecord.TCVideoTripleScreenActivity.REQUEST_CODE;


/**
 * 合唱
 */
public class TCVideoFollowRecordActivity extends FragmentActivity {

    private static final String TAG = "TCVideoFollowRecordActivity";
    // 视频合唱组件
    private UGCKitVideoMixRecord mUGCKitVideoFollowRecord;
    // 视频合唱跟拍的视频路径
    private String mFollowShotVideoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWindowParam();

        // 必须在代码中设置主题(setTheme)或者在AndroidManifest中设置主题(android:theme)
        setTheme(R.style.MixRecordActivityTheme);

        initData();
        setContentView(R.layout.activity_video_chorus);

        mUGCKitVideoFollowRecord = (UGCKitVideoMixRecord) findViewById(R.id.video_chorus);

        List<String> paths = new ArrayList<>();
        paths.add(mFollowShotVideoPath);
        MixRecordConfigBuildInfo buildInfo = new MixRecordConfigBuildInfo(paths, 0, 720 * 2, 1280, TXRecordCommon.VIDEO_ASPECT_RATIO_9_16);
        mUGCKitVideoFollowRecord.setMixRecordInfo(buildInfo);
        mUGCKitVideoFollowRecord.getTitleBar().setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUGCKitVideoFollowRecord.setOnMixRecordListener(new IVideoMixRecordKit.OnMixRecordListener() {
            @Override
            public void onMixRecordCanceled() {
                finish();
            }

            @Override
            public void onMixRecordCompleted(UGCKitResult ugcKitResult) {
                startEditActivity();
                finish();
            }

            @Override
            public void onMixRecordAction(IVideoMixRecordKit.MixRecordActionT actionT, Object object) {
                if (actionT == IVideoMixRecordKit.MixRecordActionT.MIX_RECORD_ACTION_T_SELECT) {
                    MixRecordActionData data = (MixRecordActionData) object;
                    Intent intent = new Intent(TCVideoFollowRecordActivity.this, TCTripleRecordVideoPickerActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            String path = data.getStringExtra("file");
            mUGCKitVideoFollowRecord.updateMixFile(-1, path);
        }
    }

    private void startEditActivity() {
        // 更新一下VideoInfo的时间
        Intent intent = new Intent(this, TCVideoEditerActivity.class);
        startActivity(intent);
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            TXCLog.e(TAG, "intent is null");
            return;
        }
        mFollowShotVideoPath = intent.getStringExtra(UGCKitConstants.VIDEO_PATH);
    }

    private void initWindowParam() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasPermission()) {
            mUGCKitVideoFollowRecord.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUGCKitVideoFollowRecord.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUGCKitVideoFollowRecord.release();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUGCKitVideoFollowRecord.screenOrientationChange();
    }

    @Override
    public void onBackPressed() {
        mUGCKitVideoFollowRecord.backPressed();
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mUGCKitVideoFollowRecord.start();
        }
    }
}

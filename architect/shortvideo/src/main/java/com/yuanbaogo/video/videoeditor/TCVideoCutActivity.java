package com.yuanbaogo.video.videoeditor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.tencent.qcloud.ugckit.UGCKitVideoCut;
import com.tencent.qcloud.ugckit.basic.UGCKitResult;
import com.tencent.qcloud.ugckit.module.cut.IVideoCutKit;
import com.tencent.qcloud.ugckit.utils.ToastUtil;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.videoeditor.contract.VideoCutContract;
import com.yuanbaogo.video.videoeditor.presenter.VideoCutPresenter;

/**
 * 裁剪视频Activity
 */
public class TCVideoCutActivity extends MvpBaseActivityImpl<VideoCutContract.View, VideoCutPresenter>
    implements VideoCutContract.View{
    private String TAG = "TCVideoCutActivity";
    private UGCKitVideoCut mUGCKitVideoCut;
    private String mInVideoPath;
    private IVideoCutKit.OnCutListener mOnCutListener = new IVideoCutKit.OnCutListener() {
        /**
         * 视频裁剪进度条执行完成后调用
         */
        @Override
        public void onCutterCompleted(UGCKitResult ugcKitResult) {
            Log.i(TAG, "onCutterCompleted");
            if (ugcKitResult.errorCode == 0) {
                startEditActivity();
            } else {
                ToastUtil.toastShortMessage("cut video failed. error code:" + ugcKitResult.errorCode + ",desc msg:" + ugcKitResult.descMsg);
            }
        }

        /**
         * 点击视频裁剪进度叉号，取消裁剪时被调用
         */
        @Override
        public void onCutterCanceled() {
            Log.i(TAG, "onCutterCanceled");
        }
    };

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_video_cut;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mUGCKitVideoCut = (UGCKitVideoCut) findViewById(R.id.video_cutter_layout);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mUGCKitVideoCut.getTitleBar().setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mInVideoPath = getIntent().getStringExtra(UGCKitConstants.VIDEO_PATH);
        mUGCKitVideoCut.setProgressText(1);
        mUGCKitVideoCut.setVideoPath(mInVideoPath);
    }

    private void initWindowParam() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onResume() {
        super.onResume();
        mUGCKitVideoCut.setVideoPath(mInVideoPath);
        mUGCKitVideoCut.setOnCutListener(mOnCutListener);
        mUGCKitVideoCut.startPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUGCKitVideoCut.stopPlay();
        mUGCKitVideoCut.setOnCutListener(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUGCKitVideoCut.release();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void startEditActivity() {
        Intent intent = new Intent(this, TCVideoEditerActivity.class);
        startActivity(intent);
//        finish();
    }

}

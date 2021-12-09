package com.yuanbaogo.video.videopublish.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.tencent.qcloud.ugckit.UGCKitVideoCut;
import com.tencent.qcloud.ugckit.basic.ITitleBarLayout;
import com.tencent.qcloud.ugckit.basic.UGCKitResult;
import com.tencent.qcloud.ugckit.module.cut.IVideoCutKit;
import com.tencent.qcloud.ugckit.utils.ToastUtil;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.videopublish.contract.ChooseCoverContract;
import com.yuanbaogo.video.videopublish.presenter.ChooseCoverPresenter;

/**
 * @author lhx
 * @description: 选择封面
 * @date : 2021/8/3 10:27
 */
public class ChooseCoverActivity extends MvpBaseActivityImpl<ChooseCoverContract.View, ChooseCoverPresenter>
    implements ChooseCoverContract.View{

    private UGCKitVideoCut mUGCKitVideoCut;
    private String mInVideoPath;
    private IVideoCutKit.OnCutListener mOnCutListener = new IVideoCutKit.OnCutListener() {
        /**
         * 视频裁剪进度条执行完成后调用
         */
        @Override
        public void onCutterCompleted(UGCKitResult ugcKitResult) {
            if (ugcKitResult.errorCode == 0) {
                Intent intent = new Intent(ChooseCoverActivity.this, VideoPublishActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(UGCKitConstants.VIDEO_COVERPATH, ugcKitResult.coverPath);
                intent.putExtras(bundle);
                setResult(200, intent);
                finish();
            } else {
                ToastUtil.toastShortMessage("cut video failed. error code:" + ugcKitResult.errorCode + ",desc msg:" + ugcKitResult.descMsg);
            }
        }

        /**
         * 点击视频裁剪进度叉号，取消裁剪时被调用
         */
        @Override
        public void onCutterCanceled() {

        }
    };

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        setTheme(R.style.EditerActivityTheme);
        return R.layout.activity_choose_cover;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mUGCKitVideoCut = (UGCKitVideoCut) findViewById(R.id.video_cutter_layout);
        mInVideoPath = getIntent().getStringExtra(UGCKitConstants.VIDEO_PATH);
        mUGCKitVideoCut.setProgressText(2);
        mUGCKitVideoCut.setVideoPath(mInVideoPath);
        //选择封面设置不编辑视频可以获取到封面地址，否则获取不到
        mUGCKitVideoCut.setVideoEditFlag(false);
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
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mUGCKitVideoCut.getTitleBar().getLayoutParams();
        params.topMargin = 30;
        mUGCKitVideoCut.getTitleBar().setLayoutParams(params);
        //mUGCKitVideoCut.getTitleBar().setBackgroundColor(Color.parseColor("#ffffff"));
        mUGCKitVideoCut.getTitleBar().setTitle("选择封面", ITitleBarLayout.POSITION.MIDDLE);
        mUGCKitVideoCut.getTitleBar().getMiddleTitle().setTextColor(Color.parseColor("#ffffff"));
        mUGCKitVideoCut.getTitleBar().getMiddleTitle().setTextSize(16);
        mUGCKitVideoCut.getVideoCutLayout().getTextDuration().setVisibility(View.GONE);
        mUGCKitVideoCut.getVideoCutLayout().getImageRotate().setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();
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

}

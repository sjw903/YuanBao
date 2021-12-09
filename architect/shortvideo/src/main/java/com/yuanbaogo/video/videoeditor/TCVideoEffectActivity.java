package com.yuanbaogo.video.videoeditor;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.tencent.qcloud.ugckit.UGCKitVideoEffect;
import com.tencent.qcloud.ugckit.module.effect.IVideoEffectKit;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.videoeditor.contract.VideoEditerContract;
import com.yuanbaogo.video.videoeditor.presenter.VideoEditerPresenter;

//特效设置页
public class TCVideoEffectActivity extends MvpBaseActivityImpl<VideoEditerContract.View, VideoEditerPresenter> {
    private static final String TAG = "TCVideoEffectActivity";
    private int mFragmentType;
    private UGCKitVideoEffect mUGCKitVideoEffect;
    private IVideoEffectKit.OnVideoEffectListener mOnVideoEffectListener = new IVideoEffectKit.OnVideoEffectListener() {
        @Override
        public void onEffectApply() {
            finish();
        }

        @Override
        public void onEffectCancel() {
            finish();
        }
    };



    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mFragmentType = getIntent().getIntExtra(UGCKitConstants.KEY_FRAGMENT, 0);

        mUGCKitVideoEffect = (UGCKitVideoEffect) findViewById(R.id.video_effect_layout);
        mUGCKitVideoEffect.setEffectType(mFragmentType);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }
    @Override
    protected int createContentView(Bundle savedInstanceState) {
        setTheme(R.style.EditerActivityTheme);
        return R.layout.activity_video_effect;
    }


    private void initWindowParam() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onResume() {
        super.onResume();
        mUGCKitVideoEffect.setOnVideoEffectListener(mOnVideoEffectListener);
        mUGCKitVideoEffect.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUGCKitVideoEffect.stop();
        mUGCKitVideoEffect.setOnVideoEffectListener(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUGCKitVideoEffect.release();
    }


    @Override
    public void onBackPressed() {
        mUGCKitVideoEffect.backPressed();
    }

}

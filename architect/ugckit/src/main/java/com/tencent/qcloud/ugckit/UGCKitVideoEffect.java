package com.tencent.qcloud.ugckit;

import android.app.KeyguardManager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.tencent.qcloud.ugckit.component.timeline.VideoProgressController;
import com.tencent.qcloud.ugckit.module.PlayerManagerKit;
import com.tencent.qcloud.ugckit.module.effect.AbsVideoEffectUI;
import com.tencent.qcloud.ugckit.module.effect.ConfigureLoader;
import com.tencent.qcloud.ugckit.module.effect.TimelineViewUtil;
import com.tencent.qcloud.ugckit.module.effect.VideoEditerSDK;
import com.tencent.qcloud.ugckit.module.effect.utils.DraftEditer;
import com.tencent.qcloud.ugckit.utils.TelephonyUtil;
import com.tencent.qcloud.ugckit.utils.UIAttributeUtil;
import com.yuanbaogo.libbase.config.ApplicationConfigHelper;

public class UGCKitVideoEffect extends AbsVideoEffectUI implements VideoProgressController.VideoProgressSeekListener {

    private FragmentActivity mActivity;
    private int mConfirmIcon;
    int mEffectType;

    private OnVideoEffectListener mOnVideoEffectListener;

    public UGCKitVideoEffect(Context context) {
        super(context);
        initDefault();
    }

    public UGCKitVideoEffect(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefault();
    }

    public UGCKitVideoEffect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefault();
    }

    private void initDefault() {
        mActivity = (FragmentActivity) getContext();
        // 当上个界面为裁剪界面，此时重置裁剪的开始时间和结束时间
        VideoEditerSDK.getInstance().resetDuration();
        // 加载草稿配置
        ConfigureLoader.getInstance().loadConfigToDraft();

        TelephonyUtil.getInstance().initPhoneListener();

        initTitlebar();

        preivewVideo();
    }

    private void preivewVideo() {
        // 初始化图片时间轴
        getTimelineView().initVideoProgressLayout();
        if (VideoEditerSDK.getInstance().getTXVideoInfo() != null) {
            int width = VideoEditerSDK.getInstance().getTXVideoInfo().width;
            int height = VideoEditerSDK.getInstance().getTXVideoInfo().height;
            // 初始化播放器
            getVideoPlayLayout().initPlayerLayout(height > width ? true : false);//TODO HG 短视频 是否全屏;
        } else {
            getVideoPlayLayout().initPlayerLayout(false);
        }
        // 开始播放
        PlayerManagerKit.getInstance().startPlay();
    }

    private void initTitlebar() {
        mConfirmIcon = UIAttributeUtil.getResResources(mActivity, R.attr.editerConfirmIcon, R.drawable.ugckit_ic_edit_effect_confirm_selector);
        //getTitleBar().getRightButton().setBackgroundResource(mConfirmIcon);
        getTitleBar().getRightButton().setText("保存");
        getTitleBar().setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击"返回",清除当前设置的视频特效
               /* DraftEditer.getInstance().clear();
                // 还原已经设置给SDK的特效
                VideoEditerSDK.getInstance().restore();
                clearFragmenteffect(mEffectType);
                PlayerManagerKit.getInstance().stopPlay();*/

                DraftEditer.getInstance().clear();
                clearFragmenteffect(mEffectType);
                PlayerManagerKit.getInstance().stopPlay();
                if (mOnVideoEffectListener != null) {
                    mOnVideoEffectListener.onEffectCancel();
                }
            }
        });
        getTitleBar().setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击"完成"，应用当前设置的视频特效
                ConfigureLoader.getInstance().saveConfigFromDraft();

                PlayerManagerKit.getInstance().stopPlay();

                if (mOnVideoEffectListener != null) {
                    mOnVideoEffectListener.onEffectApply();
                }
            }
        });
    }

    @Override
    public void start() {
        KeyguardManager manager = (KeyguardManager) ApplicationConfigHelper.mApplication.getSystemService(Context.KEYGUARD_SERVICE);
        if (!manager.inKeyguardRestrictedInputMode()) {
            PlayerManagerKit.getInstance().restartPlay();
        }
    }

    @Override
    public void stop() {
        PlayerManagerKit.getInstance().stopPlay();
    }

    @Override
    public void release() {
        PlayerManagerKit.getInstance().removeAllPreviewListener();
        PlayerManagerKit.getInstance().removeAllPlayStateListener();
        TelephonyUtil.getInstance().uninitPhoneListener();
    }

    @Override
    public void setEffectType(int type) {
        mEffectType = type;
        TimelineViewUtil.getInstance().setTimelineView(getTimelineView());
        getPlayControlLayout().updateUIByFragment(type);
        getTimelineView().updateUIByFragment(type);
        showFragmentByType(type);
    }

    @Override
    public void setOnVideoEffectListener(OnVideoEffectListener listener) {
        mOnVideoEffectListener = listener;
    }

    @Override
    public void backPressed() {
        DraftEditer.getInstance().clear();
        clearFragmenteffect(mEffectType);
        PlayerManagerKit.getInstance().stopPlay();
        if (mOnVideoEffectListener != null) {
            mOnVideoEffectListener.onEffectCancel();
        }
    }

    @Override
    public void onVideoProgressSeek(long currentTimeMs) {
        PlayerManagerKit.getInstance().previewAtTime(currentTimeMs);
    }

    @Override
    public void onVideoProgressSeekFinish(long currentTimeMs) {
        PlayerManagerKit.getInstance().previewAtTime(currentTimeMs);
    }

    public void showFragmentByType(int type) {
        switch (type) {
            case UGCKitConstants.TYPE_EDITER_BGM:
                //音乐fragment
                showFragment(getMusicFragment(), "bgm_setting_fragment");
                break;
            //动作fragment
            case UGCKitConstants.TYPE_EDITER_MOTION:
                showFragment(getMotionFragment(), "motion_fragment");
                break;
            //速度fragment
            case UGCKitConstants.TYPE_EDITER_SPEED:
                showFragment(getTimeFragment(), "time_fragment");
                break;
            //滤镜fragment
            case UGCKitConstants.TYPE_EDITER_FILTER:
                showFragment(getStaticFilterFragment(), "static_filter_fragment");
                break;
            //贴纸fragment
            case UGCKitConstants.TYPE_EDITER_PASTER:
                showFragment(getPasterFragment(), "paster_fragment");
                break;
            //字幕fragment
            case UGCKitConstants.TYPE_EDITER_SUBTITLE:
                showFragment(getBubbleFragment(), "bubble_fragment");
                break;
        }
    }

    public void clearFragmenteffect(int type) {
        switch (type) {
            case UGCKitConstants.TYPE_EDITER_BGM:
                //音乐fragment
                //showFragment(getMusicFragment(), "bgm_setting_fragment");
                break;
            //动作fragment
            case UGCKitConstants.TYPE_EDITER_MOTION:
                getMotionFragment().undoAllMotion();
                break;
            //速度fragment
            case UGCKitConstants.TYPE_EDITER_SPEED:
                getTimeFragment().cancelSetEffect();
                break;
            //滤镜fragment
            case UGCKitConstants.TYPE_EDITER_FILTER:
                getStaticFilterFragment().clear();
                break;
            //贴纸fragment
            case UGCKitConstants.TYPE_EDITER_PASTER:
                getPasterFragment().deleteAllPaster();

                break;
            //字幕fragment
            case UGCKitConstants.TYPE_EDITER_SUBTITLE:
                getBubbleFragment().deleteAllBubble();
                break;
        }
    }

    private void showFragment(@NonNull Fragment fragment, String tag) {
        if (fragment == getCurrentFragment()) return;
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        if (getCurrentFragment() != null) {
            transaction.hide(getCurrentFragment());
        }
        if (!fragment.isAdded()) {
            transaction.add(R.id.fragment_layout, fragment, tag);
        } else {
            transaction.show(fragment);
        }
        setCurrentFragment(fragment);
        transaction.commit();
    }
}

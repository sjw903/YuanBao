package com.tencent.qcloud.ugckit.module.record;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.tencent.qcloud.ugckit.R;
import com.tencent.qcloud.ugckit.module.record.interfaces.IRecordRightLayout;
import com.tencent.qcloud.ugckit.utils.UIAttributeUtil;
import com.tencent.ugc.TXUGCRecord;

import androidx.annotation.NonNull;

public class RecordRightLayout extends RelativeLayout implements IRecordRightLayout,
        View.OnClickListener, AspectView.OnAspectListener {
    private static final String TAG = "RecordRightLayout";

    private Activity        mActivity;
    private ImageView       mImageMusic;        // 音乐
    private LinearLayout    mLayoutTorch;       // 闪光灯
    private ImageView       mImageTorch;        // 闪光灯
    private TextView        mTextMusic;
    private ImageView       mImageMusicMask;
    private RelativeLayout  mLayoutMusic;
    private AspectView      mAspectView;        // 屏比，目前有三种（1:1；3:4；9:16）
    private ImageView       mImageBeauty;       // 美颜
    private TextView        mTextBeauty;
    private RelativeLayout  mLayoutBeauty;
    private ImageView       mImageSoundEffect;  // 音效
    private TextView        mTextSoundEffect;
    private ImageView       mImageSoundEffectMask;
    private RelativeLayout  mLayoutSoundEffect;

    private int     mTorchOnImage;
    private int     mTorchOffImage;
    private int     mTorchDisableImage;
    private boolean mFrontCameraFlag = true;                //是否前置摄像头UI判断
    private boolean mIsTorchOpenFlag;                       // 是否打开闪光灯UI判断

    private OnItemClickListener mOnItemClickListener;

    public RecordRightLayout(Context context) {
        super(context);
        initViews();
    }

    public RecordRightLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public RecordRightLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        mActivity = (Activity) getContext();
        inflate(mActivity, R.layout.ugckit_record_right_layout, this);

        mLayoutMusic = (RelativeLayout) findViewById(R.id.layout_music);
        mImageMusic = (ImageView) findViewById(R.id.iv_music);
        mTextMusic = (TextView) findViewById(R.id.tv_music);
        mImageMusic.setOnClickListener(this);
        mImageMusicMask = (ImageView) findViewById(R.id.iv_music_mask);

        mAspectView = (AspectView) findViewById(R.id.aspect_view);
        mAspectView.setOnAspectListener(this);

        mLayoutBeauty = (RelativeLayout) findViewById(R.id.layout_beauty);
        mImageBeauty = (ImageView) findViewById(R.id.iv_beauty);
        mTextBeauty = (TextView) findViewById(R.id.tv_beauty);
        mImageBeauty.setOnClickListener(this);

        mLayoutTorch = findViewById(R.id.layout_torch);
        mImageTorch = findViewById(R.id.iv_torch);
        mImageTorch.setOnClickListener(this);

        mLayoutSoundEffect = (RelativeLayout) findViewById(R.id.layout_sound_effect);
        mImageSoundEffect = (ImageView) findViewById(R.id.iv_sound_effect);
        mTextSoundEffect = (TextView) findViewById(R.id.tv_sound_effect);
        mImageSoundEffect.setOnClickListener(this);
        mImageSoundEffectMask = (ImageView) findViewById(R.id.iv_sound_effect_mask);

        mTorchDisableImage = UIAttributeUtil.getResResources(mActivity, R.attr.recordTorchDisableIcon, R.drawable.ugckit_torch_disable);
        mTorchOffImage = UIAttributeUtil.getResResources(mActivity, R.attr.recordTorchOffIcon, R.mipmap.icon_flashlight_off);
        mTorchOnImage = UIAttributeUtil.getResResources(mActivity, R.attr.recordTorchOnIcon, R.mipmap.icon_flashlight_on);

        if (mFrontCameraFlag) {
            mLayoutTorch.setVisibility(View.GONE);
            mImageTorch.setImageResource(mTorchDisableImage);
        } else {
            mLayoutTorch.setVisibility(View.VISIBLE);
            mImageTorch.setImageResource(mTorchOffImage);
        }

    }

    @Override
    public void onClick(@NonNull View view) {
        int id = view.getId();
        if (id == R.id.iv_beauty) {
            mOnItemClickListener.onShowBeautyPanel();
        } else if (id == R.id.iv_music) {
            mOnItemClickListener.onShowMusicPanel();
        } else if (id == R.id.iv_sound_effect) {
            mOnItemClickListener.onShowSoundEffectPanel();
        } else if (id == R.id.iv_torch) {
            toggleTorch();
        }
    }

    public void changeFlashlight(boolean isShow) {
        mFrontCameraFlag = isShow;
        mIsTorchOpenFlag = false;
        if (isShow) {
            mLayoutTorch.setVisibility(View.GONE);
            mImageTorch.setImageResource(mTorchDisableImage);
        } else {
            mLayoutTorch.setVisibility(View.VISIBLE);
            mImageTorch.setImageResource(mTorchOffImage);
        }
    }

    /**
     * 切换闪光灯开/关
     */
    private void toggleTorch() {
        mIsTorchOpenFlag = !mIsTorchOpenFlag;
        if (mIsTorchOpenFlag) {
            mImageTorch.setImageResource(mTorchOnImage);

            TXUGCRecord record = VideoRecordSDK.getInstance().getRecorder();
            if (record != null) {
                record.toggleTorch(true);
            }
        } else {
            mImageTorch.setImageResource(mTorchOffImage);
            TXUGCRecord record = VideoRecordSDK.getInstance().getRecorder();
            if (record != null) {
                record.toggleTorch(false);
            }
        }
    }

    /**
     * 设置闪光灯的状态为关闭
     */
    public void closeTorch() {
        if (mIsTorchOpenFlag) {
            mIsTorchOpenFlag = false;
            if (mFrontCameraFlag) {
                mLayoutTorch.setVisibility(View.GONE);
                mImageTorch.setImageResource(mTorchDisableImage);
            } else {
                mImageTorch.setImageResource(mTorchOffImage);
                mLayoutTorch.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 切换了一种屏比
     *
     * @param currentAspect 当前屏比
     */
    @Override
    public void onAspectSelect(int currentAspect) {
        mOnItemClickListener.onAspectSelect(currentAspect);
    }

    /**
     * 设置"音乐"按钮是否可用
     *
     * @param enable {@code true} 可点击<br>
     *               {@code false} 不可点击
     */
    @Override
    public void setMusicIconEnable(boolean enable) {
        if (enable) {
            mImageMusicMask.setVisibility(View.GONE);
        } else {
            mImageMusicMask.setVisibility(View.VISIBLE);
        }
        mImageMusic.setEnabled(enable);
    }

    /**
     * 设置"屏比"按钮是否可用
     *
     * @param enable {@code true} 可点击<br>
     *               {@code false} 不可点击
     */
    @Override
    public void setAspectIconEnable(boolean enable) {
        mAspectView.hideAspectSelectAnim();
        if (enable) {
            mAspectView.disableMask();
        } else {
            mAspectView.enableMask();
        }
        mAspectView.setEnabled(enable);
    }

    /**
     * 设置"音效"按钮是否可用
     *
     * @param enable {@code true} 清除背景音后，音效Icon变为可点击<br>
     *               {@code false} 录制添加BGM后是录制不了人声的，而音效是针对人声有效的，此时开启音效遮罩层，音效Icon变为不可用
     */
    @Override
    public void setSoundEffectIconEnable(boolean enable) {
        if (enable) {
            mImageSoundEffectMask.setVisibility(View.INVISIBLE);
        } else {
            mImageSoundEffectMask.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void disableRecordMusic() {
        mLayoutMusic.setVisibility(View.GONE);
    }

    public void disableRecordSoundEffect() {
        mLayoutSoundEffect.setVisibility(View.GONE);
    }

    public void disableAspect() {
        mAspectView.setVisibility(View.GONE);
    }

    public void disableBeauty() {
        mLayoutBeauty.setVisibility(View.GONE);
    }

    @Override
    public void setMusicIconResource(int resid) {
        mImageMusic.setImageResource(resid);
    }

    @Override
    public void setMusicTextSize(int size) {
        mTextMusic.setTextSize(size);
    }

    @Override
    public void setMusicTextColor(int color) {
        mTextMusic.setTextColor(getResources().getColor(color));
    }

    @Override
    public void setAspectTextSize(int size) {
        mAspectView.setTextSize(size);
    }

    @Override
    public void setAspectTextColor(int color) {
        mAspectView.setTextColor(color);
    }

    @Override
    public void setAspectIconList(int[] residList) {
        mAspectView.setIconList(residList);
    }

    @Override
    public void setBeautyIconResource(int resid) {
        mImageBeauty.setImageResource(resid);
    }

    @Override
    public void setBeautyTextSize(int size) {
        mTextBeauty.setTextSize(size);
    }

    @Override
    public void setBeautyTextColor(int color) {
        mTextBeauty.setTextColor(getResources().getColor(color));
    }

    @Override
    public void setSoundEffectIconResource(int resid) {
        mImageSoundEffect.setImageResource(resid);
    }

    @Override
    public void setSoundEffectTextSize(int size) {
        mTextSoundEffect.setTextSize(size);
    }

    @Override
    public void setSoundEffectTextColor(int color) {
        mTextSoundEffect.setTextColor(getResources().getColor(color));
    }

    public void setAspect(int aspectRatio) {
        mAspectView.setAspect(aspectRatio);
    }
}

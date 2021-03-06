package com.tencent.qcloud.ugckit.module.record;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tencent.liteav.demo.beauty.BeautyParams;
import com.tencent.qcloud.ugckit.R;
import com.tencent.qcloud.ugckit.module.record.draft.RecordDraftInfo;
import com.tencent.qcloud.ugckit.module.record.draft.RecordDraftManager;
import com.tencent.qcloud.ugckit.utils.BackgroundTasks;
import com.tencent.qcloud.ugckit.utils.LogReport;
import com.tencent.qcloud.ugckit.utils.ToastUtil;
import com.tencent.qcloud.ugckit.utils.VideoPathUtil;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import com.tencent.ugc.TXUGCPartsManager;
import com.tencent.ugc.TXUGCRecord;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoInfoReader;
import com.yuanbaogo.libbase.config.ApplicationConfigHelper;

import java.util.List;

public class VideoRecordSDK implements TXRecordCommon.ITXVideoRecordListener {
    private static final String TAG = "VideoRecordSDK";

    public static int STATE_START       = 1;
    public static int STATE_STOP        = 2;
    public static int STATE_RESUME      = 3;
    public static int STATE_PAUSE       = 4;
    public static int START_RECORD_SUCC = 0;
    public static int START_RECORD_FAIL = -1;

    @NonNull
    private static VideoRecordSDK  sInstance = new VideoRecordSDK();

    @Nullable
    private TXUGCRecord            mRecordSDK;
    private UGCKitRecordConfig     mUGCKitRecordConfig;
    private RecordDraftManager     mRecordDraftManager;
    private OnVideoRecordListener  mOnVideoRecordListener;
    private OnRestoreDraftListener mOnRestoreDraftListener;

    private int     mCurrentState  = STATE_STOP;
    private boolean mPreviewFlag;
    private String  mRecordVideoPath;

    private VideoRecordSDK() {

    }

    @NonNull
    public static VideoRecordSDK getInstance() {
        return sInstance;
    }

    /**
     * ?????????SDK???TXUGCRecord
     */
    public void initSDK() {
        if (mRecordSDK == null) {
            mRecordSDK = TXUGCRecord.getInstance(ApplicationConfigHelper.mApplication);
        }
        mCurrentState = STATE_STOP;
        TXLog.d(TAG, "initSDK");
    }

    @Nullable
    public TXUGCRecord getRecorder() {
        TXLog.d(TAG, "getRecorder mTXUGCRecord:" + mRecordSDK);
        return mRecordSDK;
    }

    public void initConfig(@NonNull UGCKitRecordConfig config) {
        mUGCKitRecordConfig = config;
        Log.d(TAG, "initConfig mBeautyParam:" + mUGCKitRecordConfig.mBeautyParams);
    }

    public UGCKitRecordConfig getConfig() {
        return mUGCKitRecordConfig;
    }

    public void startCameraPreview(TXCloudVideoView videoView) {
        Log.d(TAG, "startCameraPreview");
        if (mPreviewFlag) {
            return;
        }
        mPreviewFlag = true;

        if (mUGCKitRecordConfig.mQuality >= 0) {
            // ????????????
            TXRecordCommon.TXUGCSimpleConfig simpleConfig = new  TXRecordCommon.TXUGCSimpleConfig();
            simpleConfig.videoQuality = mUGCKitRecordConfig.mQuality;
            simpleConfig.minDuration = mUGCKitRecordConfig.mMinDuration;
            simpleConfig.maxDuration = mUGCKitRecordConfig.mMaxDuration;
            simpleConfig.isFront = mUGCKitRecordConfig.mFrontCamera;
            simpleConfig.touchFocus = mUGCKitRecordConfig.mTouchFocus;
            simpleConfig.needEdit = mUGCKitRecordConfig.mIsNeedEdit;

            if (mRecordSDK != null) {
                mRecordSDK.setVideoRenderMode(mUGCKitRecordConfig.mRecordMode);
                mRecordSDK.setMute(mUGCKitRecordConfig.mIsMute);
            }
            mRecordSDK.startCameraSimplePreview(simpleConfig, videoView);
        } else {
            // ???????????????
            TXRecordCommon.TXUGCCustomConfig customConfig = new TXRecordCommon.TXUGCCustomConfig();
            customConfig.videoResolution = mUGCKitRecordConfig.mResolution;
            customConfig.minDuration = mUGCKitRecordConfig.mMinDuration;
            customConfig.maxDuration = mUGCKitRecordConfig.mMaxDuration;
            customConfig.videoBitrate = mUGCKitRecordConfig.mVideoBitrate;
            customConfig.videoGop = mUGCKitRecordConfig.mGOP;
            customConfig.videoFps = mUGCKitRecordConfig.mFPS;
            customConfig.isFront = mUGCKitRecordConfig.mFrontCamera;
            customConfig.touchFocus = mUGCKitRecordConfig.mTouchFocus;
            customConfig.needEdit = mUGCKitRecordConfig.mIsNeedEdit;

            mRecordSDK.startCameraCustomPreview(customConfig, videoView);
        }

        if (mRecordSDK != null) {
            mRecordSDK.setRecordSpeed(mUGCKitRecordConfig.mRecordSpeed);
            mRecordSDK.setHomeOrientation(mUGCKitRecordConfig.mHomeOrientation);
            mRecordSDK.setRenderRotation(mUGCKitRecordConfig.mRenderRotation);
            mRecordSDK.setAspectRatio(mUGCKitRecordConfig.mAspectRatio);
            mRecordSDK.setVideoRecordListener(this);
        }
    }

    public void stopCameraPreview() {
        Log.d(TAG, "stopCameraPreview");
        if (mRecordSDK != null) {
            mRecordSDK.stopCameraPreview();
        }
        mPreviewFlag = false;
    }

    public int getRecordState() {
        return mCurrentState;
    }

    public void updateBeautyParam(@NonNull BeautyParams beautyParams) {
        mUGCKitRecordConfig.mBeautyParams = beautyParams;
        if (mRecordSDK != null) {
            mRecordSDK.getBeautyManager().setBeautyStyle(beautyParams.mBeautyStyle);
            mRecordSDK.getBeautyManager().setBeautyLevel(beautyParams.mBeautyLevel);
            mRecordSDK.getBeautyManager().setWhitenessLevel(beautyParams.mWhiteLevel);
            mRecordSDK.getBeautyManager().setRuddyLevel(beautyParams.mRuddyLevel);
            mRecordSDK.getBeautyManager().setFaceSlimLevel(beautyParams.mFaceSlimLevel);
            mRecordSDK.getBeautyManager().setEyeScaleLevel(beautyParams.mBigEyeLevel);
            mRecordSDK.getBeautyManager().setFaceVLevel(beautyParams.mFaceVLevel);
            mRecordSDK.getBeautyManager().setFaceShortLevel(beautyParams.mFaceShortLevel);
            mRecordSDK.getBeautyManager().setChinLevel(beautyParams.mChinSlimLevel);
            mRecordSDK.getBeautyManager().setNoseSlimLevel(beautyParams.mNoseSlimLevel);
            mRecordSDK.getBeautyManager().setMotionTmpl(beautyParams.mMotionTmplPath);
            mRecordSDK.getBeautyManager().setEyeLightenLevel(beautyParams.mEyeLightenLevel);
            mRecordSDK.getBeautyManager().setToothWhitenLevel(beautyParams.mToothWhitenLevel);
            mRecordSDK.getBeautyManager().setWrinkleRemoveLevel(beautyParams.mWrinkleRemoveLevel);
            mRecordSDK.getBeautyManager().setPounchRemoveLevel(beautyParams.mPounchRemoveLevel);
            mRecordSDK.getBeautyManager().setSmileLinesRemoveLevel(beautyParams.mSmileLinesRemoveLevel);
            mRecordSDK.getBeautyManager().setForeheadLevel(beautyParams.mForeheadLevel);
            mRecordSDK.getBeautyManager().setEyeDistanceLevel(beautyParams.mEyeDistanceLevel);
            mRecordSDK.getBeautyManager().setEyeAngleLevel(beautyParams.mEyeAngleLevel);
            mRecordSDK.getBeautyManager().setMouthShapeLevel(beautyParams.mMouthShapeLevel);
            mRecordSDK.getBeautyManager().setNoseWingLevel(beautyParams.mNoseWingLevel);
            mRecordSDK.getBeautyManager().setNosePositionLevel(beautyParams.mNosePositionLevel);
            mRecordSDK.getBeautyManager().setLipsThicknessLevel(beautyParams.mLipsThicknessLevel);
            mRecordSDK.getBeautyManager().setFaceBeautyLevel(beautyParams.mFaceBeautyLevel);
            mRecordSDK.getBeautyManager().setGreenScreenFile(beautyParams.mGreenFile);
            mRecordSDK.getBeautyManager().setFilterStrength(beautyParams.mFilterStrength / 10.f);
        }
    }

    public void updateMotionParam(@NonNull BeautyParams beautyParams) {
        if (mRecordSDK != null) {
            mRecordSDK.getBeautyManager().setMotionTmpl(beautyParams.mMotionTmplPath);
        }
    }

    /**
     * ??????????????????
     */
    public void updateAspectRatio() {
        int aspectRatio = UGCKitRecordConfig.getInstance().mAspectRatio;
        switch (aspectRatio) {
            case TXRecordCommon.VIDEO_ASPECT_RATIO_9_16:
                if (mRecordSDK != null) {
                    mRecordSDK.setAspectRatio(TXRecordCommon.VIDEO_ASPECT_RATIO_9_16);
                }
                break;
            case TXRecordCommon.VIDEO_ASPECT_RATIO_3_4:
                if (mRecordSDK != null) {
                    mRecordSDK.setAspectRatio(TXRecordCommon.VIDEO_ASPECT_RATIO_3_4);
                }
                break;
            case TXRecordCommon.VIDEO_ASPECT_RATIO_1_1:
                if (mRecordSDK != null) {
                    mRecordSDK.setAspectRatio(TXRecordCommon.VIDEO_ASPECT_RATIO_1_1);
                }
                break;
            case TXRecordCommon.VIDEO_ASPECT_RATIO_4_3:
                if (mRecordSDK != null) {
                    mRecordSDK.setAspectRatio(TXRecordCommon.VIDEO_ASPECT_RATIO_4_3);
                }
                break;
            case TXRecordCommon.VIDEO_ASPECT_RATIO_16_9:
                if (mRecordSDK != null) {
                    mRecordSDK.setAspectRatio(TXRecordCommon.VIDEO_ASPECT_RATIO_16_9);
                }
                break;
        }
    }

    /**
     * ??????API {@link TXUGCRecord#snapshot(TXRecordCommon.ITXSnapshotListener)}
     */
    public void takePhoto(@Nullable final RecordModeView.OnSnapListener listener) {
        if (mRecordSDK != null) {
            mRecordSDK.snapshot(new TXRecordCommon.ITXSnapshotListener() {
                @Override
                public void onSnapshot(final Bitmap bitmap) {
                    String fileName = System.currentTimeMillis() + ".jpg";
                    MediaStore.Images.Media.insertImage(ApplicationConfigHelper.mApplication.getContentResolver(), bitmap, fileName, null);

                    BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (listener != null) {
                                listener.onSnap(bitmap);
                            }
                        }
                    });
                }
            });
        }
    }

    public TXUGCPartsManager getPartManager() {
        if (mRecordSDK != null) {
            return mRecordSDK.getPartsManager();
        }
        return null;
    }

    /**
     * ????????????????????????
     *
     * @param context
     */
    public void initRecordDraft(Context context) {
        mRecordDraftManager = new RecordDraftManager(context);
        RecordDraftInfo lastDraftInfo = mRecordDraftManager.getLastDraftInfo();
        if (lastDraftInfo == null) {
            return;
        }
        List<RecordDraftInfo.RecordPart> recordPartList = lastDraftInfo.getPartList();
        if (recordPartList == null || recordPartList.size() == 0) {
            return;
        }

        long duration = 0;
        int recordPartSize = recordPartList.size();
        Log.d(TAG, "initRecordDraft recordPartSize:" + recordPartSize);
        for (int i = 0; i < recordPartSize; i++) {
            RecordDraftInfo.RecordPart recordPart = recordPartList.get(i);
            if (mRecordSDK != null) {
                mRecordSDK.getPartsManager().insertPart(recordPart.getPath(), i);
            }
            TXVideoEditConstants.TXVideoInfo txVideoInfo = TXVideoInfoReader.getInstance(context).getVideoFileInfo(recordPart.getPath());
            if (txVideoInfo != null) {
                duration = duration + txVideoInfo.duration;
            }

            if (mOnRestoreDraftListener != null) {
                mOnRestoreDraftListener.onDraftProgress((int) duration);
            }
        }

        if (recordPartList != null && recordPartList.size() > 0) {
            if (mOnRestoreDraftListener != null) {
                mOnRestoreDraftListener.onDraftTotal(mRecordSDK.getPartsManager().getDuration());
            }
        }
    }

    public void setOnRestoreDraftListener(OnRestoreDraftListener listener) {
        mOnRestoreDraftListener = listener;
    }

    public void setConfig(UGCKitRecordConfig config) {
        mUGCKitRecordConfig = config;
    }

    public interface OnRestoreDraftListener {
        void onDraftProgress(long duration);

        void onDraftTotal(long duration);
    }

    public void deleteAllParts() {
        if (mRecordSDK != null) {
            mRecordSDK.getPartsManager().deleteAllParts();
        }
        // ????????????????????????
        if (mRecordDraftManager != null) {
            mRecordDraftManager.deleteLastRecordDraft();
        }
    }

    /**
     * ??????????????????????????????????????????
     */
    public void saveLastPart() {
        if (mRecordSDK != null && mRecordDraftManager != null) {
            List<String> pathList = mRecordSDK.getPartsManager().getPartsPathList();
            String lastPath = pathList.get(pathList.size() - 1);
            mRecordDraftManager.saveLastPart(lastPath);
        }
    }

    /**
     * ??????????????????????????????????????????
     */
    public void deleteLastPart() {
        mRecordSDK.getPartsManager().deleteLastPart();
        // ????????????
        if (mRecordDraftManager != null) {
            mRecordDraftManager.deleteLastPart();
        }
    }

    /**
     * ????????????
     */
    public int startRecord() {
        Log.d(TAG, "startRecord mCurrentState" + mCurrentState);
        if (mCurrentState == STATE_STOP) {
            String customVideoPath = VideoPathUtil.getCustomVideoOutputPath();
            String customCoverPath = customVideoPath.replace(".mp4", ".jpg");

            int retCode = 0;

            if (mRecordSDK != null) {
                retCode = mRecordSDK.startRecord(customVideoPath, customCoverPath);
            }
            Log.d(TAG, "startRecord retCode:" + retCode);
            if (retCode != TXRecordCommon.START_RECORD_OK) {
                if (retCode == TXRecordCommon.START_RECORD_ERR_NOT_INIT) {
                    ToastUtil.toastShortMessage(ApplicationConfigHelper.mApplication.getString(R.string.ugckit_start_record_not_init));
                } else if (retCode == TXRecordCommon.START_RECORD_ERR_IS_IN_RECORDING) {
                    ToastUtil.toastShortMessage(ApplicationConfigHelper.mApplication.getString(R.string.ugckit_start_record_not_finish));
                } else if (retCode == TXRecordCommon.START_RECORD_ERR_VIDEO_PATH_IS_EMPTY) {
                    ToastUtil.toastShortMessage(ApplicationConfigHelper.mApplication.getString(R.string.ugckit_start_record_path_empty));
                } else if (retCode == TXRecordCommon.START_RECORD_ERR_API_IS_LOWER_THAN_18) {
                    ToastUtil.toastShortMessage(ApplicationConfigHelper.mApplication.getString(R.string.ugckit_start_record_version_below));
                }
                // ?????????TXUgcSDK.licence????????????????????????
                else if (retCode == TXRecordCommon.START_RECORD_ERR_LICENCE_VERIFICATION_FAILED) {
                    ToastUtil.toastShortMessage("licence????????????????????????TXUGCBase.getLicenceInfo(Context context)??????licence??????");
                }
                return START_RECORD_FAIL;
            }
            LogReport.getInstance().reportStartRecord(retCode);

            RecordMusicManager.getInstance().startMusic();
        } else if (mCurrentState == STATE_PAUSE) {
            resumeRecord();
        }

        mCurrentState = STATE_START;
        return START_RECORD_SUCC;
    }

    /**
     * ????????????
     */
    public void resumeRecord() {
        Log.d(TAG, "resumeRecord");
        if (mRecordSDK != null) {
            mRecordSDK.resumeRecord();
        }
        RecordMusicManager.getInstance().resumeMusic();
        AudioFocusManager.getInstance().requestAudioFocus();

        mCurrentState = STATE_RESUME;
    }

    /**
     * ????????????
     * FIXBUG:???????????????????????????????????????????????????????????????????????????????????????
     */
    public void pauseRecord() {
        Log.d(TAG, "pauseRecord");
        if (mCurrentState == STATE_START || mCurrentState == STATE_RESUME) {
            RecordMusicManager.getInstance().pauseMusic();
            if (mRecordSDK != null) {
                mRecordSDK.pauseRecord();
            }
            mCurrentState = STATE_PAUSE;
        }
        mPreviewFlag = false;

        AudioFocusManager.getInstance().abandonAudioFocus();
    }

    /**
     * ????????????
     */
    public void stopRecord() {
        Log.d(TAG, "stopRecord");
        int size = 0;
        if (mRecordSDK != null) {
            size = mRecordSDK.getPartsManager().getPartsPathList().size();
        }
        if (mCurrentState == STATE_STOP && size == 0) {
            //????????????????????????????????????????????????0???????????????????????????
            return;
        }
        if (mRecordSDK != null) {
            mRecordSDK.stopBGM();
            mRecordSDK.stopRecord();
        }
        AudioFocusManager.getInstance().abandonAudioFocus();

        mCurrentState = STATE_STOP;
    }

    /**
     * ??????Record SDK??????
     */
    public void releaseRecord() {
        Log.d(TAG, "releaseRecord");
        if (mRecordSDK != null) {
            mRecordSDK.stopBGM();
            mRecordSDK.stopCameraPreview();
            mRecordSDK.setVideoRecordListener(null);
            mRecordSDK.getPartsManager().deleteAllParts();
            mRecordSDK.release();
            mRecordSDK = null;
            mPreviewFlag = false;

            RecordMusicManager.getInstance().deleteMusic();
        }
        // ???????????????????????????
        if (mRecordDraftManager != null) {
            mRecordDraftManager.deleteLastRecordDraft();
        }

        AudioFocusManager.getInstance().abandonAudioFocus();
    }

    public void setFilter(Bitmap leftBmp, float leftSpecialRatio, Bitmap rightBmp,
                          float rightSpecialRatio, float leftRatio) {
        if (mRecordSDK != null) {
            mRecordSDK.setFilter(leftBmp, leftSpecialRatio, rightBmp, rightSpecialRatio, leftRatio);
        }
    }

    public void setRecordSpeed(int speed) {
        if (mRecordSDK != null) {
            mRecordSDK.setRecordSpeed(speed);
        }
    }

    public void setVideoRecordListener(OnVideoRecordListener listener) {
        mOnVideoRecordListener = listener;
    }

    @Override
    public void onRecordEvent(int event, Bundle param) {
        if (event == TXRecordCommon.EVT_ID_PAUSE) {
            //TODO ?????????????????????????????????
//            saveLastPart();
            if (mOnVideoRecordListener != null) {
                mOnVideoRecordListener.onRecordEvent();
            }
        } else if (event == TXRecordCommon.EVT_CAMERA_CANNOT_USE) {
            ToastUtil.toastShortMessage(ApplicationConfigHelper.mApplication.getResources().getString(R.string.ugckit_video_record_activity_on_record_event_evt_camera_cannot_use));
        } else if (event == TXRecordCommon.EVT_MIC_CANNOT_USE) {
            ToastUtil.toastShortMessage(ApplicationConfigHelper.mApplication.getResources().getString(R.string.ugckit_video_record_activity_on_record_event_evt_mic_cannot_use));
        }
    }

    @Override
    public void onRecordProgress(long milliSecond) {
        if (mOnVideoRecordListener != null) {
            mOnVideoRecordListener.onRecordProgress(milliSecond);
        }
    }

    @Override
    public void onRecordComplete(@NonNull TXRecordCommon.TXRecordResult result) {
        Log.d(TAG, "onRecordComplete");
        mCurrentState = STATE_STOP;
        if (result.retCode < 0) {
            ToastUtil.toastShortMessage(ApplicationConfigHelper.mApplication.getResources().getString(R.string.ugckit_video_record_activity_on_record_complete_fail_tip) + result.descMsg);
        } else {
            pauseRecord();

            mRecordVideoPath = result.videoPath;
            if (mOnVideoRecordListener != null) {
                mOnVideoRecordListener.onRecordComplete(result);
            }
        }
    }

    public String getRecordVideoPath() {
        return mRecordVideoPath;
    }

    public void switchCamera(boolean isFront) {
        TXUGCRecord record = getRecorder();
        if (record != null) {
            record.switchCamera(isFront);
        }
        if (mUGCKitRecordConfig != null) {
            mUGCKitRecordConfig.mFrontCamera = isFront;
        }
    }

    public interface OnVideoRecordListener {
        void onRecordProgress(long milliSecond);

        void onRecordEvent();

        void onRecordComplete(TXRecordCommon.TXRecordResult result);
    }
}

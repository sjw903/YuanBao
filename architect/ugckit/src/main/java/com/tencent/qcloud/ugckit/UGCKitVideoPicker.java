package com.tencent.qcloud.ugckit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.permissionx.guolindev.PermissionX;
import com.tencent.qcloud.ugckit.basic.ITitleBarLayout;
import com.tencent.qcloud.ugckit.module.picker.data.ItemView;
import com.tencent.qcloud.ugckit.module.picker.data.PickerManagerKit;
import com.tencent.qcloud.ugckit.module.picker.data.TCVideoFileInfo;
import com.tencent.qcloud.ugckit.module.picker.view.AbsPickerUI;
import com.tencent.qcloud.ugckit.module.picker.view.PickedLayout;
import com.tencent.qcloud.ugckit.utils.DateTimeUtil;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;

/**
 * Module：视频选择
 */
public class UGCKitVideoPicker extends AbsPickerUI {
    private Activity mActivity;
    @NonNull
    private Handler mHandlder = new Handler();

    public UGCKitVideoPicker(Context context) {
        super(context);
        initDefault();
    }

    public UGCKitVideoPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefault();
    }

    public UGCKitVideoPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefault();
    }

    @Override
    public void initDefault() {
        // 设置标题
        getTitleBar().setTitle(getResources().getString(R.string.ugckit_video_choose), ITitleBarLayout.POSITION.MIDDLE);
        getTitleBar().setVisible(false, ITitleBarLayout.POSITION.RIGHT);
        getPickerListLayout().setOnItemAddListener(new ItemView.OnAddListener() {
            @Override
            public void onAdd(TCVideoFileInfo fileInfo) {
                //TODO HG 判断视频选择长度
                long timeL = 0;
                //获取已选择视频集合
                ArrayList<TCVideoFileInfo> arrayList = getPickedLayout().getSelectItems(PickedLayout.TYPE_VIDEO);
                for (int i = 0; i < arrayList.size(); i++) {
                    //计算已选择是总时长（ms）
                    timeL = timeL + arrayList.get(i).getDuration();
                }
                //计算已选择 + 当前选择视频（ms）时长
                timeL = timeL + fileInfo.getDuration();
                //超过5分钟 提示
                if (timeL > 300000) {
                    ToastView.showToast(mActivity, "短视频只能选择5分钟~");
                    return;
                }
                // 选中一个视频
                getPickedLayout().addItem(fileInfo);
                String time = DateTimeUtil.formattedTimeToMinuteSecond(timeL / 1000);
                Log.e("==========time:::", time);
            }
        });
        // 加载视频
        loadVideoList();
    }

    private void loadVideoList() {
        mActivity = (Activity) getContext();
        PermissionX.init((FragmentActivity) mActivity)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .setDialogTintColor(Color.parseColor("#008577"), Color.parseColor("#83e8dd"))
                .onExplainRequestReason((scope, deniedList) -> {
                    String message = "需要您同意以下权限才能正常使用";
                    scope.showRequestReasonDialog(deniedList, message, "确定", "取消");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        mHandlder.post(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<TCVideoFileInfo> list = PickerManagerKit.getInstance(mActivity).getAllVideo();
                                getPickerListLayout().updateItems(list);
                            }
                        });
                    } else {
                        ToastView.showToast("请先同意相关权限");
                    }
                });

    }

    /**
     * Glide停止加载图片
     */
    @Override
    public void pauseRequestBitmap() {
        getPickerListLayout().pauseRequestBitmap();
    }

    /**
     * Glide继续加载图片
     */
    @Override
    public void resumeRequestBitmap() {
        getPickerListLayout().resumeRequestBitmap();
    }

    @Override
    public void setOnPickerListener(@Nullable final OnPickerListener listener) {
        getPickedLayout().setOnNextStepListener(new PickedLayout.OnNextStepListener() {
            @Override
            public void onNextStep() {
                if (listener != null) {
                    ArrayList<TCVideoFileInfo> arrayList = getPickedLayout().getSelectItems(PickedLayout.TYPE_VIDEO);
                    listener.onPickedList(arrayList);
                }
            }
        });
    }
}

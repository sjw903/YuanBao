package com.yuanbaogo.zui.picture;

import android.app.Activity;
import android.content.Context;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.List;

public class SelectPictureInternalAdapter extends SelectPictureAdapter {

    private static final String TAG = "SelectPictureInternalAdapter";
    private OnUploadImageListener onUploadImageListener;

    /**
     * 这里之所以用多行视图，因为我们默认的有一张图片的（那个带+的图片，用户点击它才会才会让你去选择图片）
     * 集合url为空的时候，默认显示它，当它达到集合9时，这个图片会自动隐藏。
     *
     * @param context /
     * @param mList   /
     */
    public SelectPictureInternalAdapter(Context context, List<LocalMedia> mList) {
        super(context, mList);
    }

    public void setOnUploadImageListener(OnUploadImageListener onUploadImageListener) {
        this.onUploadImageListener = onUploadImageListener;
    }

    @Override
    protected void openPicture() {
        if (context.get() == null) {
            return;
        }
        PictureSelector.create((Activity) context.get()).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(maxPicture - listSize()) // 最大选择个数
                .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                .isCompress(true)// 是否压缩
                .synOrAsy(false)//同步true或异步false 压缩 默认同步
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        mList.addAll(result);
                        if (onUploadImageListener != null) {
                            onUploadImageListener.onUploadImage();
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }

    public interface OnUploadImageListener {
        void onUploadImage();
    }

}

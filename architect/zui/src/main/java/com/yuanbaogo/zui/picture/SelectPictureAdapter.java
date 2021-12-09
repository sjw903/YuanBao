package com.yuanbaogo.zui.picture;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.JumpUtils;
import com.permissionx.guolindev.PermissionX;
import com.yalantis.ucrop.UCrop;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.RoundImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SelectPictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ReleaseMsgAdapter";
    private static final int ITEM_TYPE_ONE = 0x00001;
    private static final int ITEM_TYPE_TWO = 0x00002;
    private static final int MAX_PICTURE = 5;
    protected int maxPicture = MAX_PICTURE;
    private final LayoutInflater inflater;
    protected final WeakReference<Context> context;
    protected final List<LocalMedia> mList;
    private PictureResultCallback mPictureResultCallback;//调用PictureSelector 的Callback返回数据方式 ，方便窗口调用

    /**
     * 这里之所以用多行视图，因为我们默认的有一张图片的（那个带+的图片，用户点击它才会才会让你去选择图片）
     * 集合url为空的时候，默认显示它，当它达到集合9时，这个图片会自动隐藏。
     */
    public SelectPictureAdapter(Context context, List<LocalMedia> mList) {
        this.context = new WeakReference<>(context);
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_ONE) {
            return new PictureHolder(inflater.inflate(R.layout.item_layout_picture_item, parent, false));
        }
        // 添加图片item
        return new AddPictureHolder(inflater.inflate(R.layout.item_layout_picture_select_item, parent, false));
    }

    public void setMaxPicture(int maxPicture) {
        this.maxPicture = maxPicture;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PictureHolder) {
            bindPictureHolder((PictureHolder) holder, position);
        } else if (holder instanceof AddPictureHolder) {
            bindAddPictureHolder((AddPictureHolder) holder);
        }
    }

    private void bindAddPictureHolder(final AddPictureHolder holder) {
        if (maxPicture < MAX_PICTURE) {//小于9张  添加图片文字显示已选择数量  例如： 0/4
            holder.mTvAddImage.setText(listSize() + "/" + maxPicture);
        }
        if (holder.mTvAddImage.getText().equals("4/" + maxPicture)) {
            holder.mSelectImgLl.setVisibility(View.GONE);
        } else {
            holder.mSelectImgLl.setVisibility(View.VISIBLE);
        }
        holder.mSelectImgLl.setOnClickListener(v -> {
            if (listSize() >= maxPicture) {
                ToastUtil.showToast("最多选择" + maxPicture + "张图片");
                return;
            }
            initPermission();
        });
    }

    private void initPermission() {
        PermissionX.init((FragmentActivity) context.get())
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .setDialogTintColor(Color.parseColor("#008577"), Color.parseColor("#83e8dd"))
                .onExplainRequestReason((scope, deniedList) -> {
                    String message = "需要您同意以下权限才能正常使用";
                    scope.showRequestReasonDialog(deniedList, message, "确定", "取消");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        // 初始化完成之后打开图片选择
                        if (mPictureResultCallback != null) {
                            //adapter外部调用相册
                            mPictureResultCallback.onChioceResult();
                        } else {
                            //adapter内部调用相册
                            openPicture();
                        }
//                        Toast.makeText(this, "所有申请的权限都已通过", Toast.LENGTH_SHORT).show();
                    } else {
                        ToastView.showToast("请先同意相关权限");
//                        Toast.makeText(this, "您拒绝了如下权限：" + deniedList, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    protected void openPicture() {
        if ((context == null || context.get() == null)) {
            Log.e(TAG, "openPicture: 参数为空");
            return;
        }
        PictureSelector.create((Activity) context.get()).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(maxPicture - listSize()) // 最大选择个数
                .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    private void bindPictureHolder(final PictureHolder holder, final int position) {
        Glide.with(context.get()).load(Uri.parse(mList.get(position).getPath())).into(holder.mSelectImgIvImg);
        Log.e(TAG, "bindPictureHolder: " + mList.toString());
        holder.mSelectImgIvImg.setOnClickListener(v -> {
            // 图片预览
//            Bundle bundle = new Bundle();
//            bundle.putParcelableArrayList(PictureConfig.EXTRA_PREVIEW_SELECT_LIST, (ArrayList<? extends Parcelable>) mList);
//            bundle.putParcelableArrayList(PictureConfig.EXTRA_SELECT_LIST, (ArrayList<? extends Parcelable>) mList);
//            bundle.putBoolean(PictureConfig.EXTRA_BOTTOM_PREVIEW, true);
//            bundle.putInt(PictureConfig.EXTRA_POSITION, position);
//            JumpUtils.startPicturePreviewActivity(context.get(), false, bundle, UCrop.REQUEST_CROP);
            onClickPreview.onPreview(position, mList);
        });

        holder.mSelectImgIvClose.setOnClickListener(view -> {
            int index = holder.getAbsoluteAdapterPosition();
            // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
            // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
            if (index == RecyclerView.NO_POSITION) {
                return;
            }
            if (onDeleteItemListener != null) {
                onDeleteItemListener.OnDeleteItem(index);
            }
            mList.remove(index);
            notifyItemRemoved(index);
            notifyDataSetChanged();
        });
    }

    private OnDeleteItemListener onDeleteItemListener;

    public void setOnDeleteItemListener(OnDeleteItemListener onDeleteItemListener) {
        if (this.onDeleteItemListener == null) {
            this.onDeleteItemListener = onDeleteItemListener;
        }
    }

    public interface OnDeleteItemListener {
        void OnDeleteItem(int position);
    }

    //外部调用相册
    public interface PictureResultCallback {
        void onChioceResult();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return ITEM_TYPE_TWO;
        } else {
            return ITEM_TYPE_ONE;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    public int listSize() {
        return mList.size();
    }

    static class PictureHolder extends RecyclerView.ViewHolder {
        private final RoundImageView mSelectImgIvImg;
        private final ImageView mSelectImgIvClose;

        PictureHolder(View itemView) {
            super(itemView);
            mSelectImgIvImg = itemView.findViewById(R.id.select_img_iv_img);
            mSelectImgIvClose = itemView.findViewById(R.id.select_img_iv_close);
        }

    }

    static class AddPictureHolder extends RecyclerView.ViewHolder {

        private final LinearLayout mSelectImgLl;
        private final TextView mTvAddImage;

        AddPictureHolder(View itemView) {
            super(itemView);
            mSelectImgLl = itemView.findViewById(R.id.select_img_ll);
            mTvAddImage = itemView.findViewById(R.id.tv_add_image);
        }
    }


    public void setPictureResultCallback(PictureResultCallback pictureResultCallback) {
        mPictureResultCallback = pictureResultCallback;
    }

    OnClickPreview onClickPreview;

    public void setOnClickPreview(OnClickPreview onClickPreview) {
        this.onClickPreview = onClickPreview;
    }

    public interface OnClickPreview {
        void onPreview(int position, List<LocalMedia> result);
    }

}

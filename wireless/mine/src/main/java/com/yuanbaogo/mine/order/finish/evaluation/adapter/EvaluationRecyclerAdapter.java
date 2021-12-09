package com.yuanbaogo.mine.order.finish.evaluation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.model.GoodsVOListItem;
import com.yuanbaogo.zui.picture.SelectPictureAdapter;
import com.yuanbaogo.zui.picture.SelectPictureInternalAdapter;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.zui.view.RatingBar;
import com.yuanbaogo.zui.view.RoundImageView;

import java.util.List;

public class EvaluationRecyclerAdapter extends BaseRecycleAdapter<GoodsVOListItem> implements RatingBar.OnRatingChangeListener, TextWatcher, SelectPictureInternalAdapter.OnUploadImageListener, SelectPictureAdapter.OnDeleteItemListener {

    private static final String TAG = "EvaluationRecyclerAdapter";
    public static final String FILE_KEY = "image";
    public static final String MEDIA_TYPE = "image/*";
    private RoundImageView mEvaluationIvImg;
    private TextView mEvaluationTvName;
    private TextView mEvaluationTvDesc;
    private RatingBar mEvaluationRbDesc;
    private TextView mEvaluationTvRating;
    private EditText mEvaluationEtEvaluation;
    private RecyclerView mEvaluationRvImg;
    private GoodsVOListItem mGoodsVOListItem;

    public EvaluationRecyclerAdapter(Context context, List<GoodsVOListItem> mDataList) {
        this(context, mDataList, 0);
    }

    public EvaluationRecyclerAdapter(Context context, List<GoodsVOListItem> mDataList, int layoutId) {
        super(context, mDataList, layoutId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_layout_evaluation_item;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        initView(holder);
        mGoodsVOListItem = getDataList().get(position);
        Glide.with(mEvaluationIvImg).load(mGoodsVOListItem.getGoodsImageUrl()).into(mEvaluationIvImg);
        mEvaluationTvName.setText(mGoodsVOListItem.getGoodsTitle());
        mEvaluationTvDesc.setText(mGoodsVOListItem.getSkuIndexesName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        SelectPictureInternalAdapter mAdapter = new SelectPictureInternalAdapter(mContext, mGoodsVOListItem.getLocalMediaList());
        mEvaluationRvImg.setLayoutManager(layoutManager);
        mEvaluationRvImg.setAdapter(mAdapter);
        mEvaluationRbDesc.setOnRatingChangeListener(this);
        mEvaluationEtEvaluation.addTextChangedListener(this);
        mAdapter.setOnUploadImageListener(this);
        mAdapter.setOnDeleteItemListener(this);
    }

    private void initView(BaseViewHolder holder) {
        mEvaluationIvImg = holder.getView(R.id.evaluation_iv_img);
        mEvaluationTvName = holder.getView(R.id.evaluation_tv_name);
        mEvaluationTvDesc = holder.getView(R.id.evaluation_tv_desc);
        mEvaluationRbDesc = holder.getView(R.id.evaluation_rb_desc);
        mEvaluationTvRating = holder.getView(R.id.evaluation_tv_rating);
        mEvaluationEtEvaluation = holder.getView(R.id.evaluation_et_evaluation);
        mEvaluationRvImg = holder.getView(R.id.evaluation_rv_img);
    }


    @Override
    public void onRatingChange(int id, float ratingCount) {
        mGoodsVOListItem.setDesc(ratingCount);
        if (ratingCount == 1) {
            mEvaluationTvRating.setText(R.string.evaluation_rating1);
        } else if (ratingCount == 2) {
            mEvaluationTvRating.setText(R.string.evaluation_rating2);
        } else if (ratingCount == 3) {
            mEvaluationTvRating.setText(R.string.evaluation_rating3);
        } else if (ratingCount == 4) {
            mEvaluationTvRating.setText(R.string.evaluation_rating4);
        } else {
            mEvaluationTvRating.setText(R.string.evaluation_rating5);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mGoodsVOListItem.setDescEvaluation(mEvaluationEtEvaluation.getText().toString().trim());
    }

    @Override
    public void onUploadImage() {
        List<LocalMedia> localMediaList = mGoodsVOListItem.getLocalMediaList();
        onCallUpdatePicture.onUpdatePicture(localMediaList);

//        List<UpLoadFileBean> fileList = new ArrayList<>();
//        for (int i = 0; i < localMediaList.size(); i++) {
//            LocalMedia localMedia = localMediaList.get(i);
//            fileList.add(new UpLoadFileBean(FILE_KEY, localMedia.getFileName(), MEDIA_TYPE, localMedia.getRealPath()));
//        }
//        NetWork.getInstance().upLoadFile(mContext, ChildUrl.UPLOAD_IMAGE, null, fileList, new RequestSateListener<String>() {
//            @Override
//            public void onSuccess(int code, String bean) {
//                Log.d(TAG, "onUploadImage onSuccess: code:" + code + "   bean:" + bean);
//                if (code != NetConfig.NETWORK_SUCCESSFUL) {
//                    return;
//                }
//                mGoodsVOListItem.setEvaluationImgUrl(GsonUtil.GsonToList(bean, String.class));
//                Log.d(TAG, "onUploadImage onSuccess: EvaluationImgUrl:" + mGoodsVOListItem.getEvaluationImgUrl());
//            }
//
//            @Override
//            public void onFailure(Throwable var1) {
//                Log.e(TAG, "onUploadImage onFailure: " + var1.getMessage());
//                OrderNetworkUtils.disposeError(var1);
//            }
//        });
    }

    @Override
    public void OnDeleteItem(int position) {
        List<String> evaluationImgUrl = mGoodsVOListItem.getEvaluationImgUrl();
        if (evaluationImgUrl.size() >= position) {
            evaluationImgUrl.remove(position);
        }
    }

    OnCallUpdatePicture onCallUpdatePicture;

    public void setOnCallUpdatePicture(OnCallUpdatePicture onCallUpdatePicture) {
        this.onCallUpdatePicture = onCallUpdatePicture;
    }

    public interface OnCallUpdatePicture {
        void onUpdatePicture(List<LocalMedia> mLocalMediaList);
    }

}

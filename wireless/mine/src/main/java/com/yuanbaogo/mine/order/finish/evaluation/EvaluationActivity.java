package com.yuanbaogo.mine.order.finish.evaluation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.finish.evaluation.adapter.EvaluationRecyclerAdapter;
import com.yuanbaogo.mine.order.finish.refund.model.UploadListBean;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.model.GoodsVOListItem;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.RatingBar;
import com.yuanbaogo.zui.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

@Route(path = YBRouter.EVALUATION_ACTIVITY)
public class EvaluationActivity extends MvpBaseActivityImpl<EvaluationContract.View, EvaluationPresenter> implements
        EvaluationContract.View, View.OnClickListener, RatingBar.OnRatingChangeListener, EvaluationRecyclerAdapter.OnCallUpdatePicture {

    private static final String TAG = "EvaluationActivity";
    private TitleBar mEvaluationTitleBar;
    private RecyclerView mEvaluationRv;
    private RatingBar mEvaluationRbService;
    private TextView mEvaluationTvService;
    private RatingBar mEvaluationRbLogistics;
    private TextView mEvaluationTvLogistics;
    private EvaluationRecyclerAdapter mEvaluationRecyclerAdapter;
    private float serviceRating = 1f, logisticsRating = 1f;
    private List<GoodsVOListItem> goodsVOListItemList = new ArrayList<>();
    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_evaluation;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mEvaluationTitleBar = findViewById(R.id.evaluation_title_bar);
        mEvaluationRv = findViewById(R.id.evaluation_rv);
        mEvaluationRbService = findViewById(R.id.evaluation_rb_service);
        mEvaluationTvService = findViewById(R.id.evaluation_tv_service);
        mEvaluationRbLogistics = findViewById(R.id.evaluation_rb_logistics);
        mEvaluationTvLogistics = findViewById(R.id.evaluation_tv_logistics);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mEvaluationTitleBar.setRightTextOnClickListener(this);
        mEvaluationRbService.setOnRatingChangeListener(this);
        mEvaluationRbLogistics.setOnRatingChangeListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mPresenter.getOrderDetail(totalOrderId);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.title_bar_tv_right) {
            // 提交评价
            boolean isNotEmpty = false;
            for (int i = 0; i < goodsVOListItemList.size(); i++) {
                GoodsVOListItem goodsVOListItem = goodsVOListItemList.get(i);
                isNotEmpty = TextUtils.isEmpty(goodsVOListItem.getDescEvaluation());
            }
            // 测试让改成不必要的
            if (isNotEmpty) {
                ToastView.showToast(R.string.evaluation_error_toast);
                return;
            }
            if (mLocalMediaList.size() != 0) {
                mPresenter.uploadImageList(mLocalMediaList);
            } else {
                mPresenter.onEvaluation(goodsVOListItemList, serviceRating, logisticsRating, totalOrderId, imgUrlList);
            }
        }
    }

    @Override
    public void onRatingChange(int id, float ratingCount) {
        if (id == R.id.evaluation_rb_service) {
            serviceRating = ratingCount;
            showRatingText(mEvaluationTvService, ratingCount);
        } else if (id == R.id.evaluation_rb_logistics) {
            logisticsRating = ratingCount;
            showRatingText(mEvaluationTvLogistics, ratingCount);
        }
    }

    private void showRatingText(TextView rating, float ratingCount) {
        if (ratingCount == 1) {
            rating.setText(R.string.evaluation_rating1);
        } else if (ratingCount == 2) {
            rating.setText(R.string.evaluation_rating2);
        } else if (ratingCount == 3) {
            rating.setText(R.string.evaluation_rating3);
        } else if (ratingCount == 4) {
            rating.setText(R.string.evaluation_rating4);
        } else {
            rating.setText(R.string.evaluation_rating5);
        }
    }

    @Override
    public void showEvaluationResult(boolean result) {
        if (result) {
            ToastView.showToast(R.string.evaluation_success);
            finish();
        } else {
            ToastView.showToast(R.string.evaluation_fail);
        }
    }

    @Override
    public void showOrderDetail(GoodsDetail goodsDetail) {
        if (goodsDetail.getGoodsVOList() == null) {
            return;
        }
        goodsVOListItemList.clear();
        goodsVOListItemList.addAll(goodsDetail.getGoodsVOList());
        mEvaluationRecyclerAdapter = new EvaluationRecyclerAdapter(this, goodsVOListItemList);
        mEvaluationRecyclerAdapter.setOnCallUpdatePicture(this);
        mEvaluationRv.setAdapter(mEvaluationRecyclerAdapter);
    }

    @Override
    public void showImageList(List<UploadListBean> bean) {
        if (imgUrlList.size() > 0) {
            imgUrlList.clear();
        }
        for (int i = 0; i < bean.size(); i++) {
            imgUrlList.add(bean.get(i).getFileUrl());
        }
        mPresenter.onEvaluation(goodsVOListItemList, serviceRating, logisticsRating, totalOrderId, imgUrlList);
    }

    private List<LocalMedia> mLocalMediaList = new ArrayList<>();

    private List<String> imgUrlList = new ArrayList<>();

    @Override
    public void onUpdatePicture(List<LocalMedia> mLocalMediaList) {
        this.mLocalMediaList = mLocalMediaList;
    }
}
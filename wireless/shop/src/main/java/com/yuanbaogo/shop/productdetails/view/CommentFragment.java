package com.yuanbaogo.shop.productdetails.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.zui.view.PictureParameter;
import com.yuanbaogo.shop.productdetails.adapter.ShopProductDetailsCommentAdapter;
import com.yuanbaogo.shop.productdetails.contract.CommentContract;
import com.yuanbaogo.shop.productdetails.model.CommentBean;
import com.yuanbaogo.shop.productdetails.presenter.CommentPresenter;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 评论列表
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/22/21 9:00 AM
 * @Modifier:
 * @Modify:
 */
public class CommentFragment extends MvpBaseFragmentImpl<CommentContract.View, CommentPresenter>
        implements View.OnClickListener, CommentContract.View, OnRefreshListener, OnLoadMoreListener,
        ShopProductDetailsCommentAdapter.OnCallCommentImg {

    SmartRefreshLayout itemShopProductDetailsCommentSmart;

    RecyclerView itemShopProductDetailsCommentRecyclerAll;

    ShopProductDetailsCommentAdapter shopProductDetailsCommentAdapter;

    /**
     * 实体类Model
     */
    CommentBean commentBean = new CommentBean();

    RelativeLayout itemShopProductDetailsCommentRlNoData;

    OnAnimation onAnimation;

    CommentPresenter commentPresenter = new CommentPresenter();

    /**
     * 页数
     */
    int pageNum = 1;

    /**
     * 一页多少条
     */
    int size = 10;

    /**
     * 总页数
     */
    int totalPage = 0;

    String mSpuId;

    TextView itemShopProductDetailsCommentTvNum;

    List<CommentBean.CommentListBean> commentListBeans = new ArrayList<>();

    public static CommentFragment getInstance(String mSpuId) {
        CommentFragment fragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("spuId", mSpuId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setOnAnimation(OnAnimation onAnimation) {
        this.onAnimation = onAnimation;
    }

    @Override
    protected CommentPresenter createPresenter(Bundle savedInstanceState) {
        return commentPresenter;
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.fragment_comment_view;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        itemShopProductDetailsCommentSmart = (SmartRefreshLayout) findViewById(R.id.item_shop_product_details_comment_smart);
        itemShopProductDetailsCommentRecyclerAll = (RecyclerView) findViewById(R.id.item_shop_product_details_comment_recycler_all);
        itemShopProductDetailsCommentRlNoData = (RelativeLayout) findViewById(R.id.item_shop_product_details_comment_rl_no_data);
        itemShopProductDetailsCommentTvNum = (TextView) findViewById(R.id.item_shop_product_details_comment_tv_num);
        startUpAnimation(itemShopProductDetailsCommentSmart);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemShopProductDetailsCommentRecyclerAll.setLayoutManager(linearLayoutManager);

        mSpuId = getArguments().getString("spuId");
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        itemShopProductDetailsCommentSmart.setOnRefreshListener(this);
        itemShopProductDetailsCommentSmart.setOnLoadMoreListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        mPresenter.getComment(pageNum, size, mSpuId);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

    }

    @Override
    public void setComment(CommentBean bean) {
        loadFinish(true);
        commentBean = bean;
        commentListBeans.addAll(commentBean.getCommentList());
        if (commentBean.getCommentList().size() != 10) {
            itemShopProductDetailsCommentSmart.finishLoadMoreWithNoMoreData();
        }
        totalPage = commentBean.getTotal();
        itemShopProductDetailsCommentTvNum.setText("(" + commentBean.getTotal() + ")");
        initComment();
    }

    @Override
    public void initComment() {
        initRecycler();
    }

    private void initRecycler() {
        if (commentBean.getCommentList() == null) {
            itemShopProductDetailsCommentRecyclerAll.setVisibility(View.GONE);
            itemShopProductDetailsCommentRlNoData.setVisibility(View.VISIBLE);
            return;
        }
        if (pageNum == 1) {
            if (commentBean.getCommentList().size() == 0) {
                itemShopProductDetailsCommentRecyclerAll.setVisibility(View.GONE);
                itemShopProductDetailsCommentRlNoData.setVisibility(View.VISIBLE);
                return;
            }
            shopProductDetailsCommentAdapter = new ShopProductDetailsCommentAdapter(
                    getContext(), commentBean.getCommentList(), R.layout.item_shop_product_details_comment);
            shopProductDetailsCommentAdapter.setType(2);
            shopProductDetailsCommentAdapter.setOnCallCommentImg(this);
            itemShopProductDetailsCommentRecyclerAll.setAdapter(shopProductDetailsCommentAdapter);
        } else {
            shopProductDetailsCommentAdapter.addAll(commentBean.getCommentList());
        }
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        initData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (pageNum == totalPage) {
            ToastView.showToast(getActivity(), "暂无更多数据");
            loadFinish(true);
            return;
        }
        pageNum++;
        initData();
    }

    protected void loadFinish(boolean successful) {
        itemShopProductDetailsCommentSmart.finishRefresh(successful);
        itemShopProductDetailsCommentSmart.finishLoadMore(successful);
    }

    @Override
    public void onCallCheckImg(View view, int position, int fatherPosition) {
        List<LocalMedia> list = new ArrayList<>();
        for (int i = 0; i < commentListBeans.get(fatherPosition).getCommentImageUrl().length; i++) {
            LocalMedia media = new LocalMedia();
            String url = commentListBeans.get(fatherPosition).getCommentImageUrl()[i];
            media.setPath(url);
            list.add(media);
        }
        PictureParameter.PreviewImg(getActivity(), position, list);
    }

    public interface OnAnimation {
        void onAnimationEnd();
    }

    public void startUpAnimation(View view) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        slide.setDuration(500);//设置动画持续时间
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        view.startAnimation(slide);
    }

    public void startDownAnimation(View view) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        slide.setDuration(500);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onAnimation.onAnimationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(slide);
    }

}

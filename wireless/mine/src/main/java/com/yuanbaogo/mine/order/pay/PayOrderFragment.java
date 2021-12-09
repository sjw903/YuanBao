package com.yuanbaogo.mine.order.pay;

import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.adapter.LikeListRecyclerAdapter;
import com.yuanbaogo.mine.order.base.BaseOrderFragment;
import com.yuanbaogo.mine.order.model.LikeListModelItem;
import com.yuanbaogo.mine.order.model.OrderListResponseListItem;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;

import java.util.ArrayList;
import java.util.List;

public class PayOrderFragment extends BaseOrderFragment<PayOrderContract.View, PayOrderPresenter>
        implements PayOrderContract.View, LikeListRecyclerAdapter.OnCallClick {

    private SmartRefreshLayout mOrderListSmartRefreshLike;
    private RecyclerView mOrderListRvLike;
    private final List<LikeListModelItem> mLikeListModelItems = new ArrayList<>();
    protected LikeListRecyclerAdapter mLikeAdapter;

    public static PayOrderFragment newInstance() {
        return new PayOrderFragment();
    }

    private static final String KEYWORD = "keyword";

    private String mKeyword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mKeyword = getArguments().getString(KEYWORD);
        }
    }

    public static PayOrderFragment newInstance(String keyword) {
        PayOrderFragment fragment = new PayOrderFragment();
        Bundle args = new Bundle();
        args.putString(KEYWORD, keyword);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected PayOrderPresenter createPresenter(Bundle savedInstanceState) {
        return new PayOrderPresenter();
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_pay_order_fragment;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        super.bindViews(savedInstanceState);
        mOrderListSmartRefreshLike = (SmartRefreshLayout) findViewById(R.id.order_list_smart_refresh_like);
        mOrderListRvLike = (RecyclerView) findViewById(R.id.order_list_rv_like);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        super.bindListeners(savedInstanceState);
        mOrderListSmartRefreshLike.setOnLoadMoreListener(this);
        mOrderListSmartRefreshLike.setEnableRefresh(false);
    }

    @Override
    protected void loadData() {
        mPresenter.getPayOrder(mPage, mKeyword);
        mPresenter.getLikeList(mLikePage);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mLikeAdapter = new LikeListRecyclerAdapter(getContext(), mLikeListModelItems);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mOrderListRvLike.setLayoutManager(layoutManager);
        mLikeAdapter.setOnCallClick(this);
        mOrderListRvLike.setAdapter(mLikeAdapter);
    }

    @Override
    public void showPayOrder(List<OrderListResponseListItem> orderModelList) {
        if (orderModelList == null) {
            mOrderModelList.clear();
        } else {
            mOrderModelList.addAll(orderModelList);
            Log.e(TAG, "showPayOrder: " + orderModelList);
            if (orderModelList.size() < LOAD_ROWS * mPage) {
                mOrderListSmartRefresh.finishLoadMoreWithNoMoreData();
            }
        }
        loadFinish(true);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(Throwable throwable) {
        loadFinish(false);
    }

    @Override
    public void showLikeLise(List<LikeListModelItem> likeListModelItems) {
        mLikeListModelItems.addAll(likeListModelItems);
        mLikeAdapter.notifyDataSetChanged();
        loadFinish(true);
    }

    @Override
    protected void loadFinish(boolean successful) {
        super.loadFinish(successful);
        mOrderListSmartRefreshLike.finishLoadMore(successful);
    }

    @Override
    public void onClickItem(String spuId, int specialType) {
        RouterHelper.toShopProductDetails(spuId + "",
                specialType, null, null, false);
    }

    /**
     * 待付款列表  付款按钮点击请求接口
     */
    @Override
    public void setLuckDrawEnter() {

    }

    @Override
    public void initLuckDrawEnter() {

    }

    @Override
    public void setCancelOrder() {

    }

    @Override
    public void initCancelOrder() {

    }

}
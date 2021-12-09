package com.yuanbaogo.shop.publics.view;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseRowsBean;
import com.yuanbaogo.shop.publics.contract.RecommendContract;
import com.yuanbaogo.shop.publics.presenter.RecommendPresenter;
import com.yuanbaogo.shop.search.adapter.ShopSearchCommodityAdapter;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.List;

/**
 * @Description: 相关推荐
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/16/21 3:11 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.RECOMMEND_ACTIVITY)
public class RecommendActivity extends MvpBaseActivityImpl<RecommendContract.View, RecommendPresenter>
        implements RecommendContract.View, OnCallRecyclerItem {

    HeadView shopRecommendHeadView;

    RecyclerView shopRecommendListRecycler;

    ShopSearchCommodityAdapter shopSearchCommodityAdapter;

    RelativeLayout shopRecommendListRlNoData;

    List<SearchMerchandiseRowsBean> recommendBeanList;

    /**
     * 页数
     */
    int pageNum = 1;

    /**
     * 一页多少条
     */
    int pageSize = 20;

    int type = 0;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_recommend;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopRecommendHeadView = findViewById(R.id.shop_recommend_head_view);
        shopRecommendListRecycler = findViewById(R.id.shop_recommend_list_recycler);
        shopRecommendListRlNoData = findViewById(R.id.shop_recommend_list_rl_no_data);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        initData();
    }

    private void initData() {
        mPresenter.getRecommend(pageNum, pageSize, type);
    }

    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setRlTopNavigaBarBg(R.color.colorFFFFFF)
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("相关推荐")
                .setImgRight(false);
        shopRecommendHeadView.setHead(headBean);
    }

    @Override
    public void setRecommend(List<SearchMerchandiseRowsBean> bean) {
        if (bean != null) {
            recommendBeanList = bean;
            initRecommend();
        } else {
            shopRecommendListRecycler.setVisibility(View.GONE);
            shopRecommendListRlNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initRecommend() {
        initRecycler();
    }

    private void initRecycler() {
        if (recommendBeanList == null) {
            shopRecommendListRecycler.setVisibility(View.GONE);
            shopRecommendListRlNoData.setVisibility(View.VISIBLE);
            return;
        }
        if (pageNum == 1) {
            if (recommendBeanList.size() == 0) {
                shopRecommendListRecycler.setVisibility(View.GONE);
                shopRecommendListRlNoData.setVisibility(View.VISIBLE);
                return;
            }
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL);
            shopRecommendListRecycler.setLayoutManager(staggeredGridLayoutManager);
            shopSearchCommodityAdapter = new ShopSearchCommodityAdapter(
                    RecommendActivity.this, recommendBeanList, R.layout.item_shop_search_commodity);
            shopRecommendListRecycler.setAdapter(shopSearchCommodityAdapter);
            shopSearchCommodityAdapter.setOnCallBack(this);
        } else {
            shopSearchCommodityAdapter.addAll(recommendBeanList);
        }
    }

    @Override
    public void onCallItem(View view, int postion) {
        int id = view.getId();
        if (id == R.id.item_shop_search_commodity_rl) {
            RouterHelper.toShopProductDetails(recommendBeanList.get(postion).getSpuId() + "",
                    type, null, null,true);
        }
    }
}
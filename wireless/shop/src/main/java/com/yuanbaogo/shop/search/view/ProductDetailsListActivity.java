package com.yuanbaogo.shop.search.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.callcenter.CallCenter;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.commonlib.router.model.SearchMerchandiseBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseRowsBean;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.adapter.ShopSearchHeadAdapter;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.scroller.TopSmoothScroller;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.search.adapter.ShopSearchCommodityAdapter;
import com.yuanbaogo.shop.search.contract.ProductDetailsListContract;
import com.yuanbaogo.shop.search.presenter.ProductDetailsListPresenter;
import com.yuanbaogo.shop.search.widget.FilterView;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 商品列表
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/9/21 3:40 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.SHOP_PRODUCT_DETAILS_LIST_ACTIVITY)
public class ProductDetailsListActivity extends MvpBaseActivityImpl<ProductDetailsListContract.View, ProductDetailsListPresenter>
        implements FilterView.Callback,
        OnCallRecyclerItem,
        ProductDetailsListContract.View,
        OnRefreshListener,
        OnLoadMoreListener,
        View.OnClickListener {

    /**
     * 标题栏
     */
    HeadView shopProductDetailsListHeadView;

    /**
     * 搜索内容列表
     */
    RecyclerView libHeadRecyclerSearch;

    /**
     * 搜索内容
     */
    @Autowired(name = YBRouter.ShopSearchActivityParams.searchMerchandiseBean)
    SearchMerchandiseBean mSearchBean;

    String mSearch;

    /**
     * 搜索内容集合
     */
    List<String> stringList = new ArrayList<>();

    /**
     * 价格
     */
    FilterView shopProductDetailsFilterPrice;

    /**
     * 销量
     */
    FilterView shopProductDetailsFilterSales;

    /**
     * 视图
     */
    FilterView shopProductDetailsFilterView;

    /**
     * 刷新
     */
    SmartRefreshLayout shopProductDetailsListSmart;

    /**
     * 商品列表
     */
    RecyclerView shopProductDetailsListRecycler;

    GridLayoutManager staggeredGridLayoutManager;

    LinearLayoutManager linearLayoutManager;

    ShopSearchCommodityAdapter shopSearchCommodityAdapter;

    /**
     * 商品实体类
     */
    SearchMerchandiseListBean productDetailsBean = new SearchMerchandiseListBean();

    List<SearchMerchandiseRowsBean> searchMerchandiseRowsBeans = new ArrayList<>();

    /**
     * 没有数据
     */
    RelativeLayout shopProductDetailsListRlNoData;

    /**
     * 页数
     */
    int pageNum = 1;

    /**
     * 一页多少条
     */
    int pageSize = 10;

    /**
     * 总页数
     */
    int totalPage = 0;

    /**
     * 价格 销量 视图
     */
    int orderBy = 0;//0 不排序  1 升序 2 降序  3 销量从高到低

    int view = 0;//0 网格  1 列表

    /**
     * 购物车
     */
    ImageView shopProductDetailsImgShopcart;

    /**
     * 置顶
     */
    ImageView shopProductDetailsImgTop;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_product_details_list;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopProductDetailsListHeadView = findViewById(R.id.shop_product_details_list_head_view);
        libHeadRecyclerSearch = shopProductDetailsListHeadView.getLibHeadRecyclerSearch();
        libHeadRecyclerSearch.setVisibility(View.VISIBLE);

        shopProductDetailsFilterPrice = findViewById(R.id.shop_product_details_filter_price);
        shopProductDetailsFilterSales = findViewById(R.id.shop_product_details_filter_sales);
        shopProductDetailsFilterView = findViewById(R.id.shop_product_details_filter_view);

        shopProductDetailsListSmart = findViewById(R.id.shop_product_details_list_smart);
        shopProductDetailsListRecycler = findViewById(R.id.shop_product_details_list_recycler);
        shopProductDetailsListRlNoData = findViewById(R.id.shop_product_details_list_rl_no_data);
        shopProductDetailsImgShopcart = findViewById(R.id.shop_product_details_img_shopcart);
        shopProductDetailsImgTop = findViewById(R.id.shop_product_details_img_top);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        shopProductDetailsFilterPrice.setCallback(this, 1);
        shopProductDetailsFilterSales.setCallback(this, 2);
        shopProductDetailsFilterView.setCallback(this, 3);

        shopProductDetailsImgShopcart.setOnClickListener(this);
        shopProductDetailsImgTop.setOnClickListener(this);
        shopProductDetailsListHeadView.getLibHeadRlSearch().setOnClickListener(this);

        shopProductDetailsListSmart.setOnRefreshListener(this);
        shopProductDetailsListSmart.setOnLoadMoreListener(this);
        shopProductDetailsListHeadView.getLibHeadImgRight().setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        mSearch = mSearchBean.getEsGoodsName();
        initHeadRecycler();
        initFilterView();
        initData();
    }

    private void initData() {
        mPresenter.getSearchMerchandise(mSearchBean
                .setOrderBy(orderBy)
                .setPageNum(pageNum)
                .setPageSize(pageSize));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.shop_product_details_img_top) {//置顶
            LinearSmoothScroller s2 = new TopSmoothScroller(this);
            s2.setTargetPosition(0);
            if (this.view == 0) {
                staggeredGridLayoutManager.startSmoothScroll(s2);
            } else if (this.view == 1) {
                linearLayoutManager.startSmoothScroll(s2);
            }
        } else if (id == R.id.lib_head_rl_search) {
            if (mSearchBean.isFinsh()) {
                RouterHelper.toShopSearchResult(this, mSearchBean, 100);
                return;
            }
            finish();
        } else if (id == R.id.shop_product_details_img_shopcart) {
            RouterHelper.toShopCart();
        } else if (id == R.id.lib_head_img_right) {
            if (!UserStore.isLogin()) {
                RouterHelper.toLogin();
                return;
            }
            CallCenter.toService(null, null, null, null, null);
        }
    }

    /**
     * 标题栏
     */
    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setRlTopNavigaBarBg(R.color.colorFFFFFF)
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(true)
                .setRlSearchBg(R.drawable.shape_gradient_bg_gray_50)
                .setTvSearch(false)
                .setEditSearch(false)
                .setImgRight(true)
                .setImgRightIcon(R.mipmap.icon_shop_message_gray);
        shopProductDetailsListHeadView.setHead(headBean);
    }

    /**
     * 搜索内容
     */
    private void initHeadRecycler() {
        if (TextUtils.isEmpty(mSearch)) {
            return;
        }
        stringList.clear();
        stringList.add(mSearch);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        libHeadRecyclerSearch.setLayoutManager(linearLayoutManager);
        ShopSearchHeadAdapter shopSearchHeadAdapter = new ShopSearchHeadAdapter(
                ProductDetailsListActivity.this, stringList, R.layout.item_shop_search_head);
        shopSearchHeadAdapter.setOnCallBack(this);
        libHeadRecyclerSearch.setAdapter(shopSearchHeadAdapter);
    }

    /**
     * 初始化筛选条件
     */
    private void initFilterView() {
        shopProductDetailsFilterPrice.setPriceView("价格", R.mipmap.icon_search_price, false);
        shopProductDetailsFilterView.setPriceView("视图", R.mipmap.icon_head_sort_gray, false);
        shopProductDetailsFilterSales.setPriceView("销量", null, false);
    }

    /**
     * 筛选条件  点击回调
     *
     * @param isUp
     * @param type 1 价格 2 销量 3 视图
     */
    @Override
    public void getStatus(boolean isUp, int type) {
        if (type == 1) {//价格
            if (isUp) {
                orderBy = 2;
            } else {
                orderBy = 1;
            }
            shopProductDetailsFilterView.getPriceView().setTextColor(getResources().getColor(R.color.color424242));
            shopProductDetailsFilterSales.getPriceView().setTextColor(getResources().getColor(R.color.color424242));
        } else if (type == 2) {//销量
            orderBy = 3;
            shopProductDetailsFilterPrice.setPriceView("价格", R.mipmap.icon_search_price, false);
            shopProductDetailsFilterPrice.getPriceView().setTextColor(getResources().getColor(R.color.color424242));
            shopProductDetailsFilterView.getPriceView().setTextColor(getResources().getColor(R.color.color424242));
        } else if (type == 3) {//视图
            if (isUp) {
                view = 1;
            } else {
                view = 0;
            }
            initRecycler();
            shopProductDetailsFilterPrice.setPriceView("价格", R.mipmap.icon_search_price, false);
            shopProductDetailsFilterPrice.getPriceView().setTextColor(getResources().getColor(R.color.color424242));
            shopProductDetailsFilterSales.getPriceView().setTextColor(getResources().getColor(R.color.color424242));
        }
        pageNum = 1;
        initData();
    }

    @Override
    public void setSearchMerchandise(SearchMerchandiseListBean bean) {
        loadFinish(true);
        if (bean != null) {
            productDetailsBean = bean;
            if (pageNum == 1) {
                searchMerchandiseRowsBeans.clear();
            }
            for (int i = 0; i < productDetailsBean.getRows().size(); i++) {
                searchMerchandiseRowsBeans.add(productDetailsBean.getRows().get(i));
            }
            if (productDetailsBean.getTotal() % pageSize == 0) {
                totalPage = productDetailsBean.getTotal() / pageSize;
            } else {
                totalPage = productDetailsBean.getTotal() / pageSize + 1;
            }
            initSearchMerchandise();
        } else {
            shopProductDetailsListRecycler.setVisibility(View.GONE);
            shopProductDetailsListRlNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initSearchMerchandise() {
        initRecycler();
    }

    /**
     * 商品数据
     */
    private void initRecycler() {
        if (productDetailsBean.getRows() == null) {
            shopProductDetailsListRecycler.setVisibility(View.GONE);
            shopProductDetailsListRlNoData.setVisibility(View.VISIBLE);
            return;
        }
        if (pageNum == 1) {
            if (productDetailsBean.getRows().size() == 0) {
                shopProductDetailsListRecycler.setVisibility(View.GONE);
                shopProductDetailsListRlNoData.setVisibility(View.VISIBLE);
                return;
            }
            if (view == 0) {
                staggeredGridLayoutManager = new GridLayoutManager(this, 2);
                shopProductDetailsListRecycler.setLayoutManager(staggeredGridLayoutManager);
                shopSearchCommodityAdapter = new ShopSearchCommodityAdapter(
                        ProductDetailsListActivity.this, productDetailsBean.getRows(), R.layout.item_shop_search_commodity);
            } else if (view == 1) {
                linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                shopProductDetailsListRecycler.setLayoutManager(linearLayoutManager);
                shopSearchCommodityAdapter = new ShopSearchCommodityAdapter(
                        ProductDetailsListActivity.this, productDetailsBean.getRows(), R.layout.item_shop_search_commodity_list);
            }
            shopSearchCommodityAdapter.setView(view);
            shopProductDetailsListRecycler.setAdapter(shopSearchCommodityAdapter);
            shopSearchCommodityAdapter.setOnCallBack(this);
        } else {
            shopSearchCommodityAdapter.addAll(productDetailsBean.getRows());
        }
    }

    /**
     * 商品点击事件回调
     *
     * @param view
     * @param postion
     */
    @Override
    public void onCallItem(View view, int postion) {
        int id = view.getId();
        if (id == R.id.item_search_head_rl) {
            if (mSearchBean.isFinsh()) {
                RouterHelper.toShopSearchResult(this, mSearchBean, 100);
                return;
            }
            finish();
        } else if (id == R.id.item_shop_search_commodity_rl) {
            RouterHelper.toShopProductDetails(searchMerchandiseRowsBeans.get(postion).getSpuId() + "",
                    mSearchBean.getTag(), null, null, true);
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
            ToastView.showToast(ProductDetailsListActivity.this, "暂无更多数据");
            loadFinish(true);
            return;
        }
        pageNum++;
        initData();
    }

    protected void loadFinish(boolean successful) {
        shopProductDetailsListSmart.finishRefresh(successful);
        shopProductDetailsListSmart.finishLoadMore(successful);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data != null) {
                Bundle extras = data.getExtras();
                mSearchBean = (SearchMerchandiseBean) extras.get("searchBean");
                mSearch = mSearchBean.getEsGoodsName();
                initHeadRecycler();
                initData();
            }
        }
    }

}
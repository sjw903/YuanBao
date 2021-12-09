package com.yuanbaogo.shop.onecity.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.commonlib.utils.array.ArrayTools;
import com.yuanbaogo.shop.onecity.model.QueryVenueBean;
import com.yuanbaogo.commonlib.router.model.SearchMerchandiseBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseRowsBean;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.shop.publics.model.BannerBean;
import com.yuanbaogo.shop.search.adapter.ShopSearchCommodityAdapter;
import com.yuanbaogo.zui.banner.BannerView;
import com.yuanbaogo.zui.banner.bean.DataBean;
import com.yuanbaogo.zui.banner.utils.ObtainWebPictureSizeUtils;
import com.yuanbaogo.zui.dialog.ShareDialogView;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.scrollview.ObservableScrollView;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.publics.adapter.GridViewAdapter;
import com.yuanbaogo.shop.publics.adapter.ViewPagerAdapter;
import com.yuanbaogo.shop.publics.adapter.ShopOneCityExplosionAdapter;
import com.yuanbaogo.shop.onecity.adapter.ShopOneCityLiveGoodsAdapter;
import com.yuanbaogo.shop.onecity.adapter.ShopOneCityPavilionAdapter;
import com.yuanbaogo.shop.onecity.contract.OneCityOPContract;
import com.yuanbaogo.shop.onecity.presenter.OneCityOPPresenter;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 一城一品
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/3/21 11:56 AM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.SHOP_ONE_CITY_ACTIVITY)
public class OneCityOPActivity extends MvpBaseActivityImpl<OneCityOPContract.View, OneCityOPPresenter>
        implements OneCityOPContract.View,//View层
        ObservableScrollView.ScrollViewListener,//自定义ScrollView滑动监听
        OnCallRecyclerItem,//RecyclerView item点击事件
        ObtainWebPictureSizeUtils.OnPicListener,//Banner获取图片高度回调并设置
        View.OnClickListener {

    /**
     * 自定义ScrollView (滑动顶部变色)
     */
    ObservableScrollView shopOnecityScrollView;

    /**
     * 自定义HeadView
     */
    HeadView shopOnecityHeadView;

    /**
     * Banner （二次封装）
     */
    BannerView shopOnecityBanner;

    /**
     * 轮播图数据
     */
    List<DataBean> mBannerList = new ArrayList<>();

    /**
     * Banner 高度
     */
    private int bannerHeight;

    /**
     * 首推爆款 显示商品 点击可以进入商品详情
     */
    RecyclerView shopOnecityRecyclerExplosion;

    /**
     * 首推爆款适配器
     */
    ShopOneCityExplosionAdapter shopOneCityExplosionAdapter;

    /**
     * 首推爆款集合
     */
    List<SearchMerchandiseRowsBean> explosionList = new ArrayList<>();

    /**
     * 馆
     */
    RecyclerView shopOnecityRecyclerPavilion;

    /**
     * 馆适配器
     */
    ShopOneCityPavilionAdapter shopOneCityPavilionAdapter;

    /**
     * 馆集合
     */
    List<QueryVenueBean> queryVenueBeans = new ArrayList<>();

    /**
     * 为你推荐 商品列表 点击进入商品详情
     */
    RecyclerView shopOnecityRecyclerRecommend;

    /**
     * 为你推荐适配器
     */
    ShopSearchCommodityAdapter shopOneCityRecommendAdapter;

    /**
     * 为你推荐集合
     */
    List<SearchMerchandiseRowsBean> recommendList = new ArrayList<>();

    /**
     * 去购物车
     */
    ImageView shopOnecityImgShopcart;

    /**
     * 置顶
     */
    ImageView shopOnecityImgTop;

    /*
     *===================================以下功能暂时隐藏===================================
     */
    /**
     * 直播优惠、爱心助农、家乡味道等功能模块图标 可以左右滑动
     */
    ViewPager shopOnecityViewpagerFunction;

    /**
     * 功能模块集合
     */
    List<ArrayItemBean> functionArray = new ArrayList<>();

    /**
     * 功能模块视图集合
     */
    private List<View> mPagerFunctionList = new ArrayList<>();

    /**
     * 功能模块
     */
    private LayoutInflater inflater;

    /**
     * 总的页数
     */
    private int pageCount;

    /**
     * 每一页显示的个数
     */
    private int pageSize = 10;

    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;

    /**
     * 直播好货 显示直播封面 点击可以进入观看直播
     */
    RecyclerView shopOnecityRecyclerLiveGoods;

    /**
     * 直播好货适配器
     */
    ShopOneCityLiveGoodsAdapter shopOneCityLiveGoodsAdapter;

    /**
     * 直播好货集合
     */
    List<ArrayItemBean> liveGoodsArray = new ArrayList<>();
    /*
     *===================================暂时隐藏===================================
     */

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_one_city_one_produccty;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopOnecityHeadView = findViewById(R.id.shop_onecity_head_view);
        shopOnecityScrollView = findViewById(R.id.shop_onecity_scroll_view);
        shopOnecityBanner = findViewById(R.id.shop_onecity_banner);
        shopOnecityViewpagerFunction = findViewById(R.id.shop_onecity_viewpager_function);
        shopOnecityRecyclerLiveGoods = findViewById(R.id.shop_onecity_recycler_live_goods);
        shopOnecityRecyclerExplosion = findViewById(R.id.shop_onecity_recycler_explosion);
        shopOnecityRecyclerPavilion = findViewById(R.id.shop_onecity_recycler_pavilion);
        shopOnecityRecyclerRecommend = findViewById(R.id.shop_onecity_recycler_recommend);
        shopOnecityImgShopcart = findViewById(R.id.shop_onecity_img_shopcart);
        shopOnecityImgTop = findViewById(R.id.shop_onecity_img_top);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        shopOnecityImgShopcart.setOnClickListener(this);//去购物车
        shopOnecityImgTop.setOnClickListener(this);//置顶
        shopOnecityHeadView.getLibHeadImgToleft().setOnClickListener(this);//搜索
        shopOnecityHeadView.getLibHeadImgRight().setOnClickListener(this);//分享
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.shop_onecity_img_top) {//置顶
            shopOnecityScrollView.smoothScrollTo(0, 0);
        } else if (id == R.id.lib_head_img_right) {//搜索
            RouterHelper.toShopSearch(new SearchMerchandiseBean().setTag(TagParameteBean.oneCity));
        } else if (id == R.id.shop_onecity_img_shopcart) {//去购物车
            if (ToastView.isDoubleClick()) {
                return;
            }
            RouterHelper.toShopCart();
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        initHeadView();//设置顶部自定义HeadView

        mPresenter.getBanner();//获取轮播图信息

        mPresenter.initDisplayData();//ScrollView监听

        mPresenter.getRecommend(new SearchMerchandiseBean()
                .setTag(TagParameteBean.oneCityHot));//获取首推爆款信息

        mPresenter.getRecommend(new SearchMerchandiseBean()
                .setTag(TagParameteBean.oneCityRecomm)
                .setPageSize(20));//获取为你推荐信息

        mPresenter.getQueryVenue();//查询场馆

    }

    /**
     * 设置自定义HeadView
     */
    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_head_back_white)
                .setImgLeftIconBg(R.drawable.shape_gradient_bg_black_alpha_100)
                .setRlSearch(false)
                .setTvLeftTitle(true)
                .setTvLeftTitles(getResources().getString(R.string.app_one_city_one_produccty))
                .setImgRight(true)
                .setImgRightIcon(R.mipmap.icon_head_search_white)
                .setImgRightIconBg(R.drawable.shape_gradient_bg_black_alpha_100);
        shopOnecityHeadView.setHead(headBean);
    }

    /**
     * 设置Banner数据
     */
    @Override
    public void setBanner(List<BannerBean> bean) {
        if (bean != null) {
            if (bean.size() != 0) {
                for (int i = 0; i < bean.size(); i++) {
                    if (TextUtils.isEmpty(bean.get(i).getPictureUrl())) {
                        mBannerList.add(
                                new DataBean(R.mipmap.icon_shop_onecity_banner, bean.get(i).getTitle(), 1));
                    } else {
                        mBannerList.add(
                                new DataBean(bean.get(i).getPictureUrl(), bean.get(i).getTitle(), 1));
                    }
                }
            } else {
                mBannerList.add(
                        new DataBean(R.mipmap.icon_shop_onecity_banner, "一城一品", 1));
            }
        }
        initBanner();
    }

    /**
     * 显示Banner数据
     */
    @Override
    public void initBanner() {
//        if (mBannerList.size() != 0) {
//            if (!TextUtils.isEmpty(mBannerList.get(0).imageUrl)) {
//                ObtainWebPictureSizeUtils.getUrlPicSize(mBannerList.get(0).imageUrl, this);
//            } else {
//                ObtainWebPictureSizeUtils.getMipmapPicSize(this, mBannerList.get(0).imageRes, this);
//            }
//        }
        shopOnecityBanner
                .setHeight(ScreenUtils.getScreenWidth(this) / 2 + 100)
                .isAutoLoop(mBannerList.size() != 0 ?
                        mBannerList.size() != 1 ? true : false
                        :
                        DataBean.getOneCityDataVideo().size() != 1 ? true : false)
                .isAttachToBanner(false)
                .setData(mBannerList.size() != 0 ?
                        mBannerList : DataBean.getOneCityDataVideo());
    }

    @Override
    public void onImageSize(int width, int height) {
        int scale = ScreenUtils.getScreenWidth(this) / width;
        shopOnecityBanner
                .setHeight(scale == 0 ? height : height * scale)
                .isAutoLoop(mBannerList.size() != 0 ?
                        mBannerList.size() != 1 ? true : false
                        :
                        DataBean.getOneCityDataVideo().size() != 1 ? true : false)
                .isAttachToBanner(false)
                .setData(mBannerList.size() != 0 ?
                        mBannerList : DataBean.getOneCityDataVideo());
    }

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    public void initListener() {
        ViewTreeObserver treeObserver = shopOnecityBanner.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                shopOnecityBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                bannerHeight = shopOnecityBanner.getHeight() - 200;
                shopOnecityScrollView.setScrollViewListener(OneCityOPActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            //设置渐变的头部的背景颜色
            shopOnecityHeadView.getLibHeadRlTopNavigaBar()
                    .setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            initHeadView();
        } else if (y > 0 && y <= bannerHeight) {
            //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / bannerHeight;
            int alpha = (int) (scale * 255);
            shopOnecityHeadView.getLibHeadRlTopNavigaBar()
                    .setBackgroundColor(Color.argb((int) alpha, 234, 85, 4));
        } else {
            //滑动到banner下面设置普通颜色
            HeadBean headBean = new HeadBean()
                    .setRlTopNavigaBarBg(R.color.colorEA5504)
                    .setImgLeft(true)
                    .setImgLeftIcon(R.mipmap.icon_head_back_white)
                    .setRlSearch(false)
                    .setTvLeftTitle(true)
                    .setTvLeftTitles(getResources().getString(R.string.app_one_city_one_produccty))
                    .setImgRight(true)
                    .setImgRightIcon(R.mipmap.icon_head_search_white);
            shopOnecityHeadView.setHead(headBean);
        }
    }

    /**
     * 设置首款爆推数据
     */
    @Override
    public void setExplosion(SearchMerchandiseListBean bean) {
        explosionList = bean.getRows();
        initExplosion();
    }

    /**
     * 显示首款爆推数据
     */
    public void initExplosion() {
        if (explosionList != null && explosionList.size() != 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OneCityOPActivity.this);
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            shopOnecityRecyclerExplosion.setLayoutManager(linearLayoutManager);
            shopOneCityExplosionAdapter = new ShopOneCityExplosionAdapter(
                    OneCityOPActivity.this, explosionList, R.layout.item_shop_onecity_live_explosion);
            shopOneCityExplosionAdapter.setOnCallback(this);
            shopOnecityRecyclerExplosion.setAdapter(shopOneCityExplosionAdapter);
        }
    }

    /**
     * 设置为你推荐数据
     *
     * @param bean
     */
    @Override
    public void setRecommend(SearchMerchandiseListBean bean) {
        recommendList = bean.getRows();
        initRecommend();
    }

    /**
     * 显示为你推荐数据
     */
    public void initRecommend() {
        if (recommendList != null && recommendList.size() != 0) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL);
            shopOnecityRecyclerRecommend.setLayoutManager(staggeredGridLayoutManager);
            shopOneCityRecommendAdapter = new ShopSearchCommodityAdapter(
                    OneCityOPActivity.this, recommendList, R.layout.item_shop_search_commodity);
            shopOneCityRecommendAdapter.setOnCallBack(this);
            shopOnecityRecyclerRecommend.setAdapter(shopOneCityRecommendAdapter);
        }
    }

    /**
     * 设置场馆数据
     *
     * @param bean
     */
    @Override
    public void setQueryVenue(List<QueryVenueBean> bean) {
        queryVenueBeans = new ArrayList<>();
        for (int i = 0; i < bean.size(); i++) {
            if (bean.get(i).getStatus() == 1) {//是否启用 0-不启用 1-启用
                queryVenueBeans.add(new QueryVenueBean()
                        .setId(bean.get(i).getId())
                        .setName(bean.get(i).getName())
                        .setImageUrl(bean.get(i).getImageUrl())
                        .setStatus(bean.get(i).getStatus())
                );
            }
        }
        initQueryVenue();
    }

    /**
     * 展示场馆数据
     */
    @Override
    public void initQueryVenue() {
        if (queryVenueBeans != null && queryVenueBeans.size() != 0) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(OneCityOPActivity.this, 2);
            shopOnecityRecyclerPavilion.setLayoutManager(gridLayoutManager);
            shopOneCityPavilionAdapter = new ShopOneCityPavilionAdapter(
                    OneCityOPActivity.this, queryVenueBeans, R.layout.item_shop_onecity_live_goods);
            shopOneCityPavilionAdapter.setOnCallback(this);
            shopOnecityRecyclerPavilion.setAdapter(shopOneCityPavilionAdapter);
        }
    }

    /**
     * RecyclerView   item点击回调
     *
     * @param view
     * @param postion
     */
    @Override
    public void onCallItem(View view, int postion) {
        int id = view.getId();
        if (id == R.id.item_shop_onecity_live_goods_rl_root) {//馆
            RouterHelper.toShopProductDetailsList(
                    new SearchMerchandiseBean().setTag(TagParameteBean.oneCity)
                            .setSpecialId(queryVenueBeans.get(postion).getId())
                            .setFinsh(true));
        } else if (id == R.id.item_shop_onecity_live_explosion_rl_root) {//首推爆款
            RouterHelper.toShopProductDetails(
                    explosionList.get(postion).getSpuId() + "", TagParameteBean.oneCityHot, null, null, true);
        } else if (id == R.id.item_shop_search_commodity_rl) {//为你推荐
            RouterHelper.toShopProductDetails(
                    recommendList.get(postion).getSpuId() + "", TagParameteBean.oneCityRecomm, null, null, true);
        }
    }

    /*
     *===================================以下功能暂时隐藏===================================
     */

    /**
     * 功能表
     */
    public void initFunction() {
        for (ArrayTools.shopOneCityFunction airlineTypeEnum : ArrayTools.shopOneCityFunction.values()) {
            if (airlineTypeEnum.isShow()) {
                functionArray.add(new ArrayItemBean()
                        .setId(airlineTypeEnum.getId())
                        .setName(airlineTypeEnum.getName())
                        .setIcon(airlineTypeEnum.getIcon())
                        .setVisibility(airlineTypeEnum.isShow()));
            }
        }
        inflater = LayoutInflater.from(OneCityOPActivity.this);
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(functionArray.size() * 1.0 / pageSize);
        for (int i = 0; i < pageCount; i++) {
            // 每个页面都是inflate出一个新实例
            GridView gridView = (GridView) inflater
                    .inflate(R.layout.shop_function_gridview, shopOnecityViewpagerFunction, false);
            gridView.setAdapter(new GridViewAdapter(OneCityOPActivity.this, functionArray, i, pageSize));
            mPagerFunctionList.add(gridView);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int postion = position + curIndex * pageSize;
                    if (postion == 9) {
                        shopOnecityViewpagerFunction.setCurrentItem(1);
                        return;
                    }
                }
            });
        }
        //设置适配器
        shopOnecityViewpagerFunction.setAdapter(new ViewPagerAdapter(mPagerFunctionList));
    }

    /**
     * 直播好货
     */
    public void initLiveGoods() {
        for (int i = 0; i < 2; i++) {
            liveGoodsArray.add(new ArrayItemBean()
                    .setId(1)
                    .setName("山东 枣庄")
                    .setIcon(0)
                    .setVisibility(true));
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(OneCityOPActivity.this, 2);
        shopOnecityRecyclerLiveGoods.setLayoutManager(gridLayoutManager);
        shopOneCityLiveGoodsAdapter = new ShopOneCityLiveGoodsAdapter(
                OneCityOPActivity.this, liveGoodsArray, R.layout.item_shop_onecity_live_goods);
        shopOneCityLiveGoodsAdapter.setOnCallback(this);
        shopOnecityRecyclerLiveGoods.setAdapter(shopOneCityLiveGoodsAdapter);
    }

    /*
     *===================================隐藏中===================================
     */

}
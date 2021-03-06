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
 * @Description: ????????????
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/3/21 11:56 AM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.SHOP_ONE_CITY_ACTIVITY)
public class OneCityOPActivity extends MvpBaseActivityImpl<OneCityOPContract.View, OneCityOPPresenter>
        implements OneCityOPContract.View,//View???
        ObservableScrollView.ScrollViewListener,//?????????ScrollView????????????
        OnCallRecyclerItem,//RecyclerView item????????????
        ObtainWebPictureSizeUtils.OnPicListener,//Banner?????????????????????????????????
        View.OnClickListener {

    /**
     * ?????????ScrollView (??????????????????)
     */
    ObservableScrollView shopOnecityScrollView;

    /**
     * ?????????HeadView
     */
    HeadView shopOnecityHeadView;

    /**
     * Banner ??????????????????
     */
    BannerView shopOnecityBanner;

    /**
     * ???????????????
     */
    List<DataBean> mBannerList = new ArrayList<>();

    /**
     * Banner ??????
     */
    private int bannerHeight;

    /**
     * ???????????? ???????????? ??????????????????????????????
     */
    RecyclerView shopOnecityRecyclerExplosion;

    /**
     * ?????????????????????
     */
    ShopOneCityExplosionAdapter shopOneCityExplosionAdapter;

    /**
     * ??????????????????
     */
    List<SearchMerchandiseRowsBean> explosionList = new ArrayList<>();

    /**
     * ???
     */
    RecyclerView shopOnecityRecyclerPavilion;

    /**
     * ????????????
     */
    ShopOneCityPavilionAdapter shopOneCityPavilionAdapter;

    /**
     * ?????????
     */
    List<QueryVenueBean> queryVenueBeans = new ArrayList<>();

    /**
     * ???????????? ???????????? ????????????????????????
     */
    RecyclerView shopOnecityRecyclerRecommend;

    /**
     * ?????????????????????
     */
    ShopSearchCommodityAdapter shopOneCityRecommendAdapter;

    /**
     * ??????????????????
     */
    List<SearchMerchandiseRowsBean> recommendList = new ArrayList<>();

    /**
     * ????????????
     */
    ImageView shopOnecityImgShopcart;

    /**
     * ??????
     */
    ImageView shopOnecityImgTop;

    /*
     *===================================????????????????????????===================================
     */
    /**
     * ??????????????????????????????????????????????????????????????? ??????????????????
     */
    ViewPager shopOnecityViewpagerFunction;

    /**
     * ??????????????????
     */
    List<ArrayItemBean> functionArray = new ArrayList<>();

    /**
     * ????????????????????????
     */
    private List<View> mPagerFunctionList = new ArrayList<>();

    /**
     * ????????????
     */
    private LayoutInflater inflater;

    /**
     * ????????????
     */
    private int pageCount;

    /**
     * ????????????????????????
     */
    private int pageSize = 10;

    /**
     * ???????????????????????????
     */
    private int curIndex = 0;

    /**
     * ???????????? ?????????????????? ??????????????????????????????
     */
    RecyclerView shopOnecityRecyclerLiveGoods;

    /**
     * ?????????????????????
     */
    ShopOneCityLiveGoodsAdapter shopOneCityLiveGoodsAdapter;

    /**
     * ??????????????????
     */
    List<ArrayItemBean> liveGoodsArray = new ArrayList<>();
    /*
     *===================================????????????===================================
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
        shopOnecityImgShopcart.setOnClickListener(this);//????????????
        shopOnecityImgTop.setOnClickListener(this);//??????
        shopOnecityHeadView.getLibHeadImgToleft().setOnClickListener(this);//??????
        shopOnecityHeadView.getLibHeadImgRight().setOnClickListener(this);//??????
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.shop_onecity_img_top) {//??????
            shopOnecityScrollView.smoothScrollTo(0, 0);
        } else if (id == R.id.lib_head_img_right) {//??????
            RouterHelper.toShopSearch(new SearchMerchandiseBean().setTag(TagParameteBean.oneCity));
        } else if (id == R.id.shop_onecity_img_shopcart) {//????????????
            if (ToastView.isDoubleClick()) {
                return;
            }
            RouterHelper.toShopCart();
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        initHeadView();//?????????????????????HeadView

        mPresenter.getBanner();//?????????????????????

        mPresenter.initDisplayData();//ScrollView??????

        mPresenter.getRecommend(new SearchMerchandiseBean()
                .setTag(TagParameteBean.oneCityHot));//????????????????????????

        mPresenter.getRecommend(new SearchMerchandiseBean()
                .setTag(TagParameteBean.oneCityRecomm)
                .setPageSize(20));//????????????????????????

        mPresenter.getQueryVenue();//????????????

    }

    /**
     * ???????????????HeadView
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
     * ??????Banner??????
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
                        new DataBean(R.mipmap.icon_shop_onecity_banner, "????????????", 1));
            }
        }
        initBanner();
    }

    /**
     * ??????Banner??????
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
     * ????????????????????????????????????????????????
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
            //????????????????????????????????????
            shopOnecityHeadView.getLibHeadRlTopNavigaBar()
                    .setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            initHeadView();
        } else if (y > 0 && y <= bannerHeight) {
            //??????????????????banner??????????????????????????????????????????????????????????????????
            float scale = (float) y / bannerHeight;
            int alpha = (int) (scale * 255);
            shopOnecityHeadView.getLibHeadRlTopNavigaBar()
                    .setBackgroundColor(Color.argb((int) alpha, 234, 85, 4));
        } else {
            //?????????banner????????????????????????
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
     * ????????????????????????
     */
    @Override
    public void setExplosion(SearchMerchandiseListBean bean) {
        explosionList = bean.getRows();
        initExplosion();
    }

    /**
     * ????????????????????????
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
     * ????????????????????????
     *
     * @param bean
     */
    @Override
    public void setRecommend(SearchMerchandiseListBean bean) {
        recommendList = bean.getRows();
        initRecommend();
    }

    /**
     * ????????????????????????
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
     * ??????????????????
     *
     * @param bean
     */
    @Override
    public void setQueryVenue(List<QueryVenueBean> bean) {
        queryVenueBeans = new ArrayList<>();
        for (int i = 0; i < bean.size(); i++) {
            if (bean.get(i).getStatus() == 1) {//???????????? 0-????????? 1-??????
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
     * ??????????????????
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
     * RecyclerView   item????????????
     *
     * @param view
     * @param postion
     */
    @Override
    public void onCallItem(View view, int postion) {
        int id = view.getId();
        if (id == R.id.item_shop_onecity_live_goods_rl_root) {//???
            RouterHelper.toShopProductDetailsList(
                    new SearchMerchandiseBean().setTag(TagParameteBean.oneCity)
                            .setSpecialId(queryVenueBeans.get(postion).getId())
                            .setFinsh(true));
        } else if (id == R.id.item_shop_onecity_live_explosion_rl_root) {//????????????
            RouterHelper.toShopProductDetails(
                    explosionList.get(postion).getSpuId() + "", TagParameteBean.oneCityHot, null, null, true);
        } else if (id == R.id.item_shop_search_commodity_rl) {//????????????
            RouterHelper.toShopProductDetails(
                    recommendList.get(postion).getSpuId() + "", TagParameteBean.oneCityRecomm, null, null, true);
        }
    }

    /*
     *===================================????????????????????????===================================
     */

    /**
     * ?????????
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
        //????????????=??????/????????????????????????
        pageCount = (int) Math.ceil(functionArray.size() * 1.0 / pageSize);
        for (int i = 0; i < pageCount; i++) {
            // ??????????????????inflate??????????????????
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
        //???????????????
        shopOnecityViewpagerFunction.setAdapter(new ViewPagerAdapter(mPagerFunctionList));
    }

    /**
     * ????????????
     */
    public void initLiveGoods() {
        for (int i = 0; i < 2; i++) {
            liveGoodsArray.add(new ArrayItemBean()
                    .setId(1)
                    .setName("?????? ??????")
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
     *===================================?????????===================================
     */

}
package com.yuanbaogo.shop.international.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.international.contract.YBInternationalContract;
import com.yuanbaogo.shop.international.presenter.YBInternationalPresenter;
import com.yuanbaogo.commonlib.router.model.SearchMerchandiseBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseRowsBean;
import com.yuanbaogo.shop.publics.adapter.ShopOneCityExplosionAdapter;
import com.yuanbaogo.shop.publics.model.BannerBean;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.shop.search.adapter.ShopSearchCommodityAdapter;
import com.yuanbaogo.zui.banner.BannerView;
import com.yuanbaogo.zui.banner.bean.DataBean;
import com.yuanbaogo.zui.banner.utils.ObtainWebPictureSizeUtils;
import com.yuanbaogo.zui.dialog.ShareDialogView;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.scrollview.ObservableScrollView;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 元宝国际
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/5/21 3:20 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.YB_INTERNATIONAL_ACTIVITY)
public class YBInternationalActivity extends MvpBaseActivityImpl<YBInternationalContract.View, YBInternationalPresenter>
        implements YBInternationalContract.View,
        ObservableScrollView.ScrollViewListener,
        OnCallRecyclerItem,
        ObtainWebPictureSizeUtils.OnPicListener,
        View.OnClickListener {

    /**
     * 自定义ScrollView (滑动顶部变色)
     */
    ObservableScrollView shopInternationalScrollView;

    /**
     * 自定义HeadView
     */
    HeadView shopInternationalHeadView;

    /**
     * Banner （二次封装）
     */
    BannerView shopInternationalBanner;

    /**
     * 轮播图数据
     */
    List<DataBean> mBannerList = new ArrayList<>();

    /**
     * Banner 高度
     */
    private int bannerHeight;

    /**
     * 好货必Buy
     */
    RecyclerView shopInternationalRecyclerBuy;

    /**
     * 好货必Buy适配器
     */
    ShopOneCityExplosionAdapter shopOneCityExplosionAdapter;

    /**
     * 好货必Buy数据
     */
    List<SearchMerchandiseRowsBean> buyList = new ArrayList<>();

    /**
     * 元宝国际商品
     */
    RecyclerView shopInternationalRecyclerRecommend;

    /**
     * 元宝国际商品适配器
     */
    ShopSearchCommodityAdapter shopOneCityRecommendAdapter;

    /**
     * 元宝国际商品数据
     */
    List<SearchMerchandiseRowsBean> recommenList = new ArrayList<>();

    /**
     * 去购物车
     */
    ImageView shopInternationalImgShopcart;

    /**
     * 置顶
     */
    ImageView shopInternationalImgTop;

    /**
     * 页数
     */
    int pageNum = 2;

    /**
     * 一页多少条
     */
    int pageSize = 6;

    /**
     * 总页数
     */
    int totalPage = 0;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_yb_international;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopInternationalHeadView = findViewById(R.id.shop_international_head_view);
        shopInternationalImgShopcart = findViewById(R.id.shop_international_img_shopcart);
        shopInternationalImgTop = findViewById(R.id.shop_international_img_top);
        shopInternationalScrollView = findViewById(R.id.shop_international_scroll_view);
        shopInternationalBanner = findViewById(R.id.shop_international_banner);
        shopInternationalRecyclerBuy = findViewById(R.id.shop_international_recycler_buy);
        shopInternationalRecyclerRecommend = findViewById(R.id.shop_international_recycler_recommend);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        shopInternationalImgShopcart.setOnClickListener(this);//去购物车
        shopInternationalImgTop.setOnClickListener(this);//置顶
        shopInternationalHeadView.getLibHeadImgToleft().setOnClickListener(this);//搜索
        shopInternationalHeadView.getLibHeadImgRight().setOnClickListener(this);//分享
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.shop_international_img_shopcart) {//去购物车
            if (ToastView.isDoubleClick()) {
                return;
            }
            RouterHelper.toShopCart();
        } else if (id == R.id.shop_international_img_top) {//置顶
            shopInternationalScrollView.smoothScrollTo(0, 0);
        } else if (id == R.id.lib_head_img_right) {//搜索
            RouterHelper.toShopSearch(new SearchMerchandiseBean().setTag(TagParameteBean.international));
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        initHeadView();//设置顶部自定义HeadView

        mPresenter.getBanner();//获取轮播图信息

        mPresenter.initDisplayData();//ScrollView监听

        mPresenter.getRecommend(new SearchMerchandiseBean()
                .setTag(TagParameteBean.international));//获取好货必Buy

        mPresenter.getRecommend(new SearchMerchandiseBean()
                .setTag(TagParameteBean.international)
                .setPageNum(pageNum)
                .setPageSize(pageSize));//获取元宝国际商品

    }

    /**
     * zidingyi HeadView
     */
    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_head_back_white)
                .setImgLeftIconBg(R.drawable.shape_gradient_bg_black_alpha_100)
                .setRlSearch(false)
                .setTvLeftTitle(true)
                .setTvLeftTitles("元宝精选")
                .setImgRight(true)
                .setImgRightIcon(R.mipmap.icon_head_search_white)
                .setImgRightIconBg(R.drawable.shape_gradient_bg_black_alpha_100);
        shopInternationalHeadView.setHead(headBean);
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
                                new DataBean(R.mipmap.icon_shop_international_banner, bean.get(i).getTitle(), 1));
                    } else {
                        mBannerList.add(
                                new DataBean(bean.get(i).getPictureUrl(), bean.get(i).getTitle(), 1));
                    }
                }
            } else {
                mBannerList.add(
                        new DataBean(R.mipmap.icon_shop_international_banner, "元宝精选", 1));
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
        shopInternationalBanner
                .setHeight(ScreenUtils.getScreenWidth(this) / 2+100)
                .isAutoLoop(mBannerList.size() != 0 ?
                        mBannerList.size() != 1 ? true : false
                        :
                        DataBean.getYBInternationalDataVideo().size() != 1 ? true : false)
                .isAttachToBanner(false)
                .setData(mBannerList.size() != 0 ?
                        mBannerList : DataBean.getYBInternationalDataVideo());
    }

    @Override
    public void onImageSize(int width, int height) {
        int scale = ScreenUtils.getScreenWidth(this) / width;
        shopInternationalBanner
                .setHeight(scale == 0 ? height : height * scale)
                .isAutoLoop(mBannerList.size() != 0 ?
                        mBannerList.size() != 1 ? true : false
                        :
                        DataBean.getYBInternationalDataVideo().size() != 1 ? true : false)
                .isAttachToBanner(false)
                .setData(mBannerList.size() != 0 ?
                        mBannerList : DataBean.getYBInternationalDataVideo());
    }

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    public void initListener() {
        ViewTreeObserver treeObserver = shopInternationalBanner.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                shopInternationalBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                bannerHeight = shopInternationalBanner.getHeight() - 200;
                shopInternationalScrollView.setScrollViewListener(YBInternationalActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            //设置渐变的头部的背景颜色
            shopInternationalHeadView.getLibHeadRlTopNavigaBar()
                    .setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            initHeadView();
        } else if (y > 0 && y <= bannerHeight) {
            //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / bannerHeight;
            int alpha = (int) (scale * 255);
            shopInternationalHeadView.getLibHeadRlTopNavigaBar()
                    .setBackgroundColor(Color.argb((int) alpha, 234, 85, 4));
        } else {
            //滑动到banner下面设置普通颜色
            HeadBean headBean = new HeadBean()
                    .setRlTopNavigaBarBg(R.color.colorEA5504)
                    .setImgLeft(true)
                    .setImgLeftIcon(R.mipmap.icon_head_back_white)
                    .setRlSearch(false)
                    .setTvLeftTitle(true)
                    .setTvLeftTitles("元宝精选")
                    .setImgRight(true)
                    .setImgRightIcon(R.mipmap.icon_head_search_white);
            shopInternationalHeadView.setHead(headBean);
        }
        View contentView = scrollView.getChildAt(0);
        //判断是否滑到的底部
        if (contentView != null
                && contentView.getMeasuredHeight() == (scrollView.getScrollY() + scrollView.getHeight())) {
            if (pageNum < totalPage) {
                pageNum++;
                mPresenter.getRecommend(new SearchMerchandiseBean()
                        .setTag(TagParameteBean.international)
                        .setPageNum(pageNum)
                        .setPageSize(pageSize));//获取元宝国际商品
            }
        }
    }

    /**
     * 设置好货必Buy数据
     *
     * @param bean
     */
    @Override
    public void setBuy(SearchMerchandiseListBean bean) {
        buyList = bean.getRows();
        initBuy();
    }

    /**
     * 显示好货必Buy
     */
    @Override
    public void initBuy() {
        if (buyList != null && buyList.size() != 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YBInternationalActivity.this);
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            shopInternationalRecyclerBuy.setLayoutManager(linearLayoutManager);
            shopOneCityExplosionAdapter = new ShopOneCityExplosionAdapter(
                    YBInternationalActivity.this, buyList, R.layout.item_shop_onecity_live_explosion);
            shopOneCityExplosionAdapter.setOnCallback(this);
            shopInternationalRecyclerBuy.setAdapter(shopOneCityExplosionAdapter);
        }
    }

    List<SearchMerchandiseRowsBean> searchMerchandiseRowsBeans = new ArrayList<>();

    /**
     * 设置列表数据
     *
     * @param bean
     */
    @Override
    public void setRecommend(SearchMerchandiseListBean bean) {
        recommenList = bean.getRows();
        if (bean.getTotal() % pageSize == 0) {
            totalPage = bean.getTotal() / pageSize;
        } else {
            totalPage = bean.getTotal() / pageSize + 1;
        }
        if (pageNum == 1) {
            searchMerchandiseRowsBeans.clear();
        }
        for (int i = 0; i < recommenList.size(); i++) {
            searchMerchandiseRowsBeans.add(recommenList.get(i));
        }
        initRecommend();
    }

    /**
     * 显示列表
     */
    @Override
    public void initRecommend() {
        if (recommenList != null && recommenList.size() != 0) {
            if (pageNum == 2) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL);
                shopInternationalRecyclerRecommend.setLayoutManager(staggeredGridLayoutManager);
                shopOneCityRecommendAdapter = new ShopSearchCommodityAdapter(
                        YBInternationalActivity.this, recommenList, R.layout.item_shop_search_commodity);
                shopOneCityRecommendAdapter.setOnCallBack(this);
                shopInternationalRecyclerRecommend.setAdapter(shopOneCityRecommendAdapter);
            } else {
                shopOneCityRecommendAdapter.addAll(recommenList);
            }
        }
    }

    @Override
    public void onCallItem(View view, int postion) {
        int id = view.getId();
        if (id == R.id.item_shop_onecity_live_explosion_rl_root) {//好货必Buy
            //商品SpuID  直播  商品批次  直播ID号
            RouterHelper.toShopProductDetails(buyList.get(postion).getSpuId() + "",
                    TagParameteBean.international, null, null, true);
        } else if (id == R.id.item_shop_search_commodity_rl) {//元宝国际商品
            RouterHelper.toShopProductDetails(searchMerchandiseRowsBeans.get(postion).getSpuId() + "",
                    TagParameteBean.international, null, null, true);
        }
    }

}
package com.yuanbaogo.shop.joingroup.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseRowsBean;
import com.yuanbaogo.shop.publics.model.BannerBean;
import com.yuanbaogo.shop.publics.view.GroupJoiningZoneFragment;
import com.yuanbaogo.shop.search.adapter.ShopSearchCommodityAdapter;
import com.yuanbaogo.zui.banner.BannerView;
import com.yuanbaogo.zui.banner.bean.DataBean;
import com.yuanbaogo.zui.dialog.ShareDialogView;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.scrollview.ObservableScrollView;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.joingroup.contract.JoinGroupContract;
import com.yuanbaogo.shop.joingroup.presenter.JoinGroupPresenter;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 拼团
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/3/21 11:59 AM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.SHOP_JOIN_GROUP_ACTIVITY)
public class JoinGroupActivity extends MvpBaseActivityImpl<JoinGroupContract.View, JoinGroupPresenter>
        implements JoinGroupContract.View,
        ObservableScrollView.ScrollViewListener,
        OnCallRecyclerItem,
        View.OnClickListener {

    /**
     * 自定义ScrollView (滑动顶部变色)
     */
    ObservableScrollView shopJoinGroupScrollView;

    /**
     * 自定义Head
     */
    HeadView shopJoinGroupHeadView;

    /**
     * Banner （二次封装）
     */
    BannerView shopJoinGroupBanner;

    /**
     * 轮播图数据
     */
    List<DataBean> mBannerList = new ArrayList<>();

    /**
     * Banner 高度
     */
    private int bannerHeight;

    /**
     * 销量排行 商品列表 点击进入商品详情
     */
    RecyclerView shopJoinGroupRecycler;

    /**
     * 销量排行适配器
     */
    ShopSearchCommodityAdapter shopOneCityRecommendAdapter;

    /**
     * 销量排行集合
     */
    List<SearchMerchandiseRowsBean> recommendList = new ArrayList<>();

    /**
     * 购物车
     */
    ImageView shopJoinGroupImgShopcart;

    /**
     * 置顶
     */
    ImageView shopJoinGroupImgTop;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_join_group;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopJoinGroupHeadView = findViewById(R.id.shop_join_group_head_view);
        shopJoinGroupBanner = findViewById(R.id.shop_join_group_banner);
        shopJoinGroupScrollView = findViewById(R.id.shop_join_group_scroll_view);
        shopJoinGroupRecycler = findViewById(R.id.shop_join_group_recycler);
        shopJoinGroupImgShopcart = findViewById(R.id.shop_join_group_img_shopcart);
        shopJoinGroupImgTop = findViewById(R.id.shop_join_group_img_top);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        shopJoinGroupImgShopcart.setOnClickListener(this);//去购物车
        shopJoinGroupImgTop.setOnClickListener(this);//置顶
        shopJoinGroupHeadView.getLibHeadImgToleft().setOnClickListener(this);//搜索
        shopJoinGroupHeadView.getLibHeadImgRight().setOnClickListener(this);//分享
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.shop_join_group_img_top) {//置顶
            shopJoinGroupScrollView.smoothScrollTo(0, 0);
        } else if (id == R.id.lib_head_img_toleft) {//搜索
//            RouterHelper.toShopSearch(new SearchBean().setType(3));
        } else if (id == R.id.shop_join_group_img_shopcart) {//去购物车
            if (ToastView.isDoubleClick()) {
                return;
            }
            RouterHelper.toShopCart();
        } else if (id == R.id.lib_head_img_right) {//分享
            ShareBean shareBean = new ShareBean()
                    .setReport(false)
                    .setNotInterested(false);
            ShareDialogView shareDialogView = new ShareDialogView(shareBean);
            shareDialogView.show(getSupportFragmentManager(), "share");
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        initHeadView();//设置顶部自定义HeadView

        mPresenter.getBanner();//获取轮播图信息

        mPresenter.initDisplayData();//ScrollView监听、超值拼团区

        mPresenter.getRecommend(1);//获取销量排行信息

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
                .setTvLeftTitles("全名拼团")
                .setImgRight(true)
                .setImgRightIcon(R.mipmap.icon_head_more)
                .setImgRightIconBg(R.drawable.shape_gradient_bg_black_alpha_100)
                .setImgToleft(true)
                .setImgToleftIcon(R.mipmap.icon_head_search_white)
                .setImgToleftIconBg(R.drawable.shape_gradient_bg_black_alpha_100);
        shopJoinGroupHeadView.setHead(headBean);
    }

    /**
     * 设置Banner数据
     */
    @Override
    public void setBanner(List<BannerBean> bean) {
        if (bean != null) {
            for (int i = 0; i < bean.size(); i++) {
                if (TextUtils.isEmpty(bean.get(i).getPictureUrl())) {
                    mBannerList.add(
                            new DataBean(R.mipmap.icon_shop_international_banner, bean.get(i).getTitle(), 1));
                } else {
                    mBannerList.add(
                            new DataBean(bean.get(i).getPictureUrl(), bean.get(i).getTitle(), 1));
                }
            }
        }
        initBanner();
    }

    /**
     * 设置轮播图
     */
    public void initBanner() {
        shopJoinGroupBanner
                .isAutoLoop(mBannerList.size() != 0 ?
                        mBannerList.size() != 1 ? true : false
                        :
                        DataBean.getJoinGroup().size() != 1 ? true : false)
                .isAttachToBanner(false)
                .setData(mBannerList.size() != 0 ?
                        mBannerList : DataBean.getJoinGroup());
    }

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    public void initListener() {
        ViewTreeObserver treeObserver = shopJoinGroupBanner.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                shopJoinGroupBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                bannerHeight = shopJoinGroupBanner.getHeight() - 200;
                shopJoinGroupScrollView.setScrollViewListener(JoinGroupActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            //设置渐变的头部的背景颜色
            shopJoinGroupHeadView.getLibHeadRlTopNavigaBar()
                    .setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            initHeadView();
        } else if (y > 0 && y <= bannerHeight) {
            //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / bannerHeight;
            int alpha = (int) (scale * 255);
            shopJoinGroupHeadView.getLibHeadRlTopNavigaBar()
                    .setBackgroundColor(Color.argb((int) alpha, 234, 85, 4));
        } else {
            //滑动到banner下面设置普通颜色
            HeadBean headBean = new HeadBean()
                    .setRlTopNavigaBarBg(R.color.colorEA5504)
                    .setImgLeft(true)
                    .setImgLeftIcon(R.mipmap.icon_head_back_white)
                    .setRlSearch(false)
                    .setTvLeftTitle(true)
                    .setTvLeftTitles("全名拼团")
                    .setImgRight(true)
                    .setImgRightIcon(R.mipmap.icon_head_more)
                    .setImgToleft(true)
                    .setImgToleftIcon(R.mipmap.icon_head_search_white);
            shopJoinGroupHeadView.setHead(headBean);
        }
    }

    /**
     * 专区
     */
    public void initGroupJoiningZone() {
        GroupJoiningZoneFragment groupJoiningZoneFragment = new GroupJoiningZoneFragment();
        groupJoiningZoneFragment.setType(1);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.shop_join_group_zone, groupJoiningZoneFragment);
        transaction.commit();
    }

    /**
     * 设置销量排行数据
     */
    @Override
    public void setRecommend(SearchMerchandiseListBean bean) {
        recommendList = bean.getRows();
        initRecommend();
    }

    /**
     * 显示销量排行数据
     */
    public void initRecommend() {
        if (recommendList != null && recommendList.size() != 0) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL);
            shopJoinGroupRecycler.setLayoutManager(staggeredGridLayoutManager);
            shopOneCityRecommendAdapter = new ShopSearchCommodityAdapter(
                    JoinGroupActivity.this, recommendList, R.layout.item_shop_search_commodity);
            shopOneCityRecommendAdapter.setOnCallBack(this);
            shopJoinGroupRecycler.setAdapter(shopOneCityRecommendAdapter);
        }
    }

    @Override
    public void onCallItem(View view, int postion) {
        int id = view.getId();
        if (id == R.id.item_shop_search_commodity_rl) {//销量排行
//            RouterHelper.toShopProductDetails(recommendList.get(postion).getSpuId(), 3);
        }
    }

}
package com.yuanbaogo.shop.spick.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.publics.model.BannerBean;
import com.yuanbaogo.shop.spick.adapter.SpickAdapter;
import com.yuanbaogo.shop.spick.contract.SpickContract;
import com.yuanbaogo.shop.spick.presenter.SpickPresenter;
import com.yuanbaogo.zui.banner.BannerView;
import com.yuanbaogo.zui.banner.bean.DataBean;
import com.yuanbaogo.zui.dialog.ShareDialogView;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.scrollview.ObservableScrollView;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 秒杀
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/3/21 11:57 AM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.SHOP_SPICK_ACTIVITY)
public class SpickActivity extends MvpBaseActivityImpl<SpickContract.View, SpickPresenter>
        implements SpickContract.View,
        ObservableScrollView.ScrollViewListener,
        OnCallRecyclerItem,
        View.OnClickListener {

    /**
     * 自定义ScrollView (滑动顶部变色)
     */
    ObservableScrollView shopSpickScrollView;

    /**
     * 自定义HeadView
     */
    HeadView shopSpickHeadView;

    /**
     * Banner （二次封装）
     */
    BannerView shopSpickBanner;

    /**
     * 轮播图数据
     */
    List<DataBean> mBannerList = new ArrayList<>();

    /**
     * Banner 高度
     */
    private int bannerHeight;

    /**
     * 秒杀商品
     */
    RecyclerView shopSpickRecycler;

    /**
     * 秒杀商品适配器
     */
    SpickAdapter spickAdapter;

    /**
     * 去购物车
     */
    ImageView shopSpickImgShopcart;

    /**
     * 置顶
     */
    ImageView shopSpickImgTop;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_spick;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopSpickHeadView = findViewById(R.id.shop_spick_head_view);
        shopSpickImgShopcart = findViewById(R.id.shop_spick_img_shopcart);
        shopSpickImgTop = findViewById(R.id.shop_spick_img_top);
        shopSpickScrollView = findViewById(R.id.shop_spick_scroll_view);
        shopSpickBanner = findViewById(R.id.shop_spick_banner);
        shopSpickRecycler = findViewById(R.id.shop_spick_recycler);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        shopSpickImgShopcart.setOnClickListener(this);//去购物车
        shopSpickImgTop.setOnClickListener(this);//置顶
        shopSpickHeadView.getLibHeadImgToleft().setOnClickListener(this);//搜索
        shopSpickHeadView.getLibHeadImgRight().setOnClickListener(this);//分享
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.shop_spick_img_shopcart) {//去购物车
            if (ToastView.isDoubleClick()) {
                return;
            }
            RouterHelper.toShopCart();
        } else if (id == R.id.shop_spick_img_top) {//置顶
            shopSpickScrollView.smoothScrollTo(0, 0);
        } else if (id == R.id.lib_head_img_toleft) {//搜索
//            RouterHelper.toShopSearch(new SearchBean().setType(4));
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

        mPresenter.initDisplayData();//ScrollView监听

        initRecycler();

    }

    /**
     * 自定义HeadView
     */
    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_head_back_white)
                .setImgLeftIconBg(R.drawable.shape_gradient_bg_black_alpha_100)
                .setRlSearch(false)
                .setTvLeftTitle(true)
                .setTvLeftTitles("今日秒杀")
                .setImgRight(true)
                .setImgRightIcon(R.mipmap.icon_head_more)
                .setImgRightIconBg(R.drawable.shape_gradient_bg_black_alpha_100)
                .setImgToleft(true)
                .setImgToleftIcon(R.mipmap.icon_head_search_white)
                .setImgToleftIconBg(R.drawable.shape_gradient_bg_black_alpha_100);
        shopSpickHeadView.setHead(headBean);
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
                            new DataBean(R.mipmap.icon_shop_spick_banner, bean.get(i).getTitle(), 1));
                } else {
                    mBannerList.add(
                            new DataBean(bean.get(i).getPictureUrl(), bean.get(i).getTitle(), 1));
                }
            }
        }
        initBanner();
    }

    /**
     * 显示Banner数据
     */
    @Override
    public void initBanner() {
        shopSpickBanner
                .isAutoLoop(mBannerList.size() != 0 ?
                        mBannerList.size() != 1 ? true : false
                        :
                        DataBean.getSpickDataVideo().size() != 1 ? true : false)
                .isAttachToBanner(false)
                .setData(mBannerList.size() != 0 ?
                        mBannerList : DataBean.getSpickDataVideo());
    }

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    public void initListener() {
        ViewTreeObserver treeObserver = shopSpickBanner.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                shopSpickBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                bannerHeight = shopSpickBanner.getHeight() - 200;
                shopSpickScrollView.setScrollViewListener(SpickActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            //设置渐变的头部的背景颜色
            shopSpickHeadView.getLibHeadRlTopNavigaBar()
                    .setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            initHeadView();
        } else if (y > 0 && y <= bannerHeight) {
            //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / bannerHeight;
            int alpha = (int) (scale * 255);
            shopSpickHeadView.getLibHeadRlTopNavigaBar()
                    .setBackgroundColor(Color.argb((int) alpha, 234, 85, 4));
        } else {
            //滑动到banner下面设置普通颜色
            HeadBean headBean = new HeadBean()
                    .setRlTopNavigaBarBg(R.color.colorEA5504)
                    .setImgLeft(true)
                    .setImgLeftIcon(R.mipmap.icon_head_back_white)
                    .setRlSearch(false)
                    .setTvLeftTitle(true)
                    .setTvLeftTitles("今日秒杀")
                    .setImgRight(true)
                    .setImgRightIcon(R.mipmap.icon_head_more)
                    .setImgToleft(true)
                    .setImgToleftIcon(R.mipmap.icon_head_search_white);
            shopSpickHeadView.setHead(headBean);
        }
    }

    private void initRecycler() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("年轻有为" + i);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        shopSpickRecycler.setLayoutManager(linearLayoutManager);
        spickAdapter = new SpickAdapter(this, list, R.layout.item_shop_spick);
        spickAdapter.setOnCallBack(this);
        shopSpickRecycler.setAdapter(spickAdapter);
    }

    @Override
    public void onCallItem(View view, int postion) {

    }

}
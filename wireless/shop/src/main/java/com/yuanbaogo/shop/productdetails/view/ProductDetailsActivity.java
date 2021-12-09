package com.yuanbaogo.shop.productdetails.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.imsdk.relationship.FriendOperationResult;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.callcenter.CallCenter;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.commonlib.router.model.OrderNumBean;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.zui.banner.utils.ObtainWebPictureSizeUtils;
import com.yuanbaogo.shop.productdetails.adapter.DetailsAdapter;
import com.yuanbaogo.shop.productdetails.contract.ProductDetailsContract;
import com.yuanbaogo.shop.productdetails.model.CommentBean;
import com.yuanbaogo.shop.productdetails.model.DetailBean;
import com.yuanbaogo.shop.productdetails.model.DetailsModelBean;
import com.yuanbaogo.shop.productdetails.model.ProductDetailsBean;
import com.yuanbaogo.zui.dialog.model.SkuBean;
import com.yuanbaogo.shop.productdetails.model.TabBean;
import com.yuanbaogo.shop.productdetails.presenter.ProductDetailsPresenter;
import com.yuanbaogo.shop.publics.model.RecommendBean;
import com.yuanbaogo.zui.banner.BannerView;
import com.yuanbaogo.zui.banner.bean.DataBean;
import com.yuanbaogo.zui.dialog.SortDialogView;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.head.call.OnCallHeadBack;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.scrollview.ObservableScrollView;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.PictureParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 商品详情页面
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/5/21 11:30 AM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.SHOP_PRODUCT_DETAILS_ACTIVITY)
public class ProductDetailsActivity
        extends MvpBaseActivityImpl<ProductDetailsContract.View, ProductDetailsPresenter>
        implements ProductDetailsContract.View,
        ObservableScrollView.ScrollViewListener,
        OnTabSelectListener,
        OnCallRecyclerItem,
        DetailsAdapter.OnCallComment,
        OnCallHeadBack,
        CommentFragment.OnAnimation,
        DetailsAdapter.OnCallSort,
        DetailsAdapter.OnCallRecommendAll,
        SortDialogView.OnCallSort,
        ObtainWebPictureSizeUtils.OnPicListener,
        View.OnClickListener, BannerView.OnCallBanner {

    /**
     * 父控件
     */
    RelativeLayout shopProductDetailsRlHead;

    /**
     * 自定义Head
     */
    HeadView shopProductDetailsHeadView;

    /**
     * 商品分类Tab  商品 评价 推荐 详情
     */
    CommonTabLayout shopProductDetailsTab;

    /**
     * tab 标题
     */
    private final String[] mTitles = {"商品", "评价", "详情"};

    /**
     * Tab
     */
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    /**
     * 自定义ScrollView (滑动顶部变色)
     */
    ObservableScrollView shopProductDetailsScroll;

    /**
     * Banner （二次封装）
     */
    BannerView shopProductDetailsBanner;

    /**
     * 轮播图数据
     */
    List<DataBean> mBannerList = new ArrayList<>();

    /**
     * Banner 高度
     */
    private int bannerHeight;

    /**
     * 内容
     */
    RecyclerView shopProductDetailsRecycler;

    /**
     * 商品信息适配器
     */
    DetailsAdapter detailsAdapter;

    /**
     * 模块集合
     */
    private int item1 = 0;//商品信息
    private int item2 = 0;//评论
    private int item3 = 0;//商品参数
    private int item4 = 0;//推荐
    private int item5 = 0;//商品详情

    /**
     * 商品 全部评论
     */
    FrameLayout shopProductDetailsFl;

    /**
     * 加入购物车
     */
    TextView shopProductDetailsTvAddShopCart;

    /**
     * 立即购买
     */
    TextView shopProductDetailsTvBuy;

    /**
     * 收藏
     */
    TextView shopProductDetailsTvFavorites;

    /**
     * 商品ID
     */
    @Autowired(name = YBRouter.ProductDetailsActivityParams.spuId)
    String mSpuId;

    /**
     * 商品信息
     */
    ProductDetailsBean productDetailsBean;

    /**
     * 相关推荐
     */
    List<RecommendBean> recommendBeanList;

    /**
     * 评论
     */
    CommentBean commentBean;

    /**
     * 商品详情
     */
    DetailBean detailBean;

    /**
     * ScrollView y轴位置
     */
    int yScrollView = 0;

    /**
     * 全部评论
     */
    CommentFragment commentFragment;

    /**
     * 全部评论是否显示   默认不显示
     * 为了监听返回按钮 是否finish商品信息页面  还是关闭全部评论页面
     */
    boolean isComment = false;

    /**
     * 商品规格
     */
    SkuBean skuBean;

    /**
     * 1 立即购买  2 加入购物车 11 12 短视频带货|直播带货 立即购买
     */
    int shoppingType = 1;

    /**
     * 是否收藏
     */
    boolean isCollect = false;

    /**
     * 11 短视频带货 12 直播带货
     */
    @Autowired(name = YBRouter.ProductDetailsActivityParams.type)
    int mType;

    RelativeLayout shopProductDetailsRlBottom;

    RelativeLayout shopProductDetailsRlBottomLive;

    TextView shopProductDetailsTvBuyNow;

    @Autowired(name = YBRouter.ProductDetailsActivityParams.lot)
    String mLot;

    @Autowired(name = YBRouter.ProductDetailsActivityParams.bizId)
    String mBizId;

    TextView shopProductDetailsTvConsult;

    /**
     * 是否显示相关推荐
     */
    @Autowired(name = YBRouter.ProductDetailsActivityParams.isShow)
    boolean isShow;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_product_details;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopProductDetailsRlHead = findViewById(R.id.shop_product_details_rl_head);
        shopProductDetailsHeadView = findViewById(R.id.shop_product_details_head_view);
        shopProductDetailsTab = findViewById(R.id.shop_product_details_tab);
        shopProductDetailsScroll = findViewById(R.id.shop_product_details_scroll);
        shopProductDetailsBanner = findViewById(R.id.shop_product_details_banner);
        shopProductDetailsRecycler = findViewById(R.id.shop_product_details_recycler);
        shopProductDetailsFl = findViewById(R.id.shop_product_details_fl);
        shopProductDetailsTvAddShopCart = findViewById(R.id.shop_product_details_tv_add_shop_cart);
        shopProductDetailsTvBuy = findViewById(R.id.shop_product_details_tv_buy);
        shopProductDetailsTvFavorites = findViewById(R.id.shop_product_details_tv_favorites);
        shopProductDetailsRlBottom = findViewById(R.id.shop_product_details_rl_bottom);
        shopProductDetailsRlBottomLive = findViewById(R.id.shop_product_details_rl_bottom_live);
        shopProductDetailsTvBuyNow = findViewById(R.id.shop_product_details_tv_buy_now);
        shopProductDetailsTvConsult = findViewById(R.id.shop_product_details_tv_consult);
        if (mType == TagParameteBean.videoBringGoods
                || mType == TagParameteBean.liveBringGoods) {
            shopProductDetailsRlBottom.setVisibility(View.GONE);
            shopProductDetailsRlBottomLive.setVisibility(View.VISIBLE);
        } else {
            shopProductDetailsRlBottom.setVisibility(View.VISIBLE);
            shopProductDetailsRlBottomLive.setVisibility(View.GONE);
        }
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        shopProductDetailsHeadView.setOnCallHeadBack(this);
        shopProductDetailsTvAddShopCart.setOnClickListener(this);
        shopProductDetailsTvBuy.setOnClickListener(this);
        shopProductDetailsHeadView.getLibHeadImgToleft().setOnClickListener(this);
        shopProductDetailsTvFavorites.setOnClickListener(this);
        shopProductDetailsTvBuyNow.setOnClickListener(this);
        shopProductDetailsTvConsult.setOnClickListener(this);
        shopProductDetailsBanner.setOnCallBanner(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        //获取商品信息
        mPresenter.getSearchMerchandiseDetail(mSpuId);
        //获取相关推荐
        mPresenter.getSearchRecommend(0);
        //获取商品评论
        mPresenter.getComment(1, 3, mSpuId);
        //获取商品详情
        mPresenter.getDetail(mSpuId);
        //是否收藏
        mPresenter.getFindFavoritesFlag(mSpuId, UserStore.getYbCode());
        mPresenter.initDisplayData();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.shop_product_details_tv_add_shop_cart) {//加入购物车
//            if (!UserStore.isLogin()) {
//                RouterHelper.toLogin();
//                return;
//            }
            shoppingType = 2;
            mPresenter.getSku(mSpuId);
        } else if (id == R.id.shop_product_details_tv_buy) {//立即购买
//            if (!UserStore.isLogin()) {
//                RouterHelper.toLogin();
//                return;
//            }
            shoppingType = 1;
            mPresenter.getSku(mSpuId);
        } else if (id == R.id.lib_head_img_toleft) {//去购物车
            RouterHelper.toShopCart();
        } else if (id == R.id.shop_product_details_tv_favorites) {//收藏
            if (!UserStore.isLogin()) {
                RouterHelper.toLogin();
                return;
            }
            if (isCollect) {
                mPresenter.getDeleteFavorites(mSpuId, UserStore.getYbCode());
            } else {
                mPresenter.getAddFavorites(mSpuId, UserStore.getYbCode(), 3);
            }
        } else if (id == R.id.shop_product_details_tv_buy_now) {//直播带货 立即购买
            shoppingType = mType;
            mPresenter.getSku(mSpuId);
        } else if (id == R.id.shop_product_details_tv_consult) {//客服
            if (!UserStore.isLogin()) {
                RouterHelper.toLogin();
                return;
            }
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            CallCenter.toService(mSpuId,
                    null,
                    "¥" + productDetailsBean.getMinSalePrice() / 100,
                    productDetailsBean.getCoverImages(),
                    productDetailsBean.getGoodsName());
        }
    }

    /**
     * 自定义HeadView
     */
    private void initHeadView() {
        HeadBean headBean = new HeadBean();
        if (mType == TagParameteBean.videoBringGoods
                || mType == TagParameteBean.liveBringGoods) {
            headBean.setRlSearch(false)
                    .setImgLeftIcon(R.mipmap.icon_head_back_white_yuan)
                    .setImgRight(false)
                    .setImgRightIcon(R.mipmap.icon_head_more_yuan);
        } else {
            headBean.setRlSearch(false)
                    .setImgLeftIcon(R.mipmap.icon_head_back_white_yuan)
                    .setImgRight(false)
                    .setImgRightIcon(R.mipmap.icon_head_more_yuan)
                    .setImgToleft(false)
                    .setImgToleftIcon(R.mipmap.icon_head_shop_cart)
                    .setImgToleft2(false)
                    .setImgToleftIcon2(R.mipmap.icon_head_share);
        }
        shopProductDetailsHeadView.setHead(headBean);
    }

    /**
     * 设置tab
     */
    public void initTab() {
        //添加tab
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabBean(mTitles[i]));
        }
        shopProductDetailsTab.setTabData(mTabEntities);
        shopProductDetailsTab.setOnTabSelectListener(this);
    }

    /**
     * tab点击事件
     *
     * @param position
     */
    @Override
    public void onTabSelect(int position) {
        if (position == 0) {//商品
            shopProductDetailsScroll.smoothScrollTo(0, bannerHeight);
        } else if (position == 1) {//评论
            shopProductDetailsScroll.smoothScrollTo(0, item1);
        } else if (position == 2) {//详情
            if (isShow) {
                shopProductDetailsScroll.smoothScrollTo(0, item4);
            } else {
                shopProductDetailsScroll.smoothScrollTo(0, item3);
            }
        }
    }

    @Override
    public void onTabReselect(int position) {

    }

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    public void initListener() {
        ViewTreeObserver treeObserver = shopProductDetailsBanner.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                shopProductDetailsBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                bannerHeight = ScreenUtils.getScreenWidth(ProductDetailsActivity.this) - 300;
                shopProductDetailsScroll.setScrollViewListener(ProductDetailsActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        this.yScrollView = y;
        initScrollView(yScrollView);
    }

    private void initScrollView(int yScrollView) {
        if (yScrollView <= 0) {
            shopProductDetailsTab.setVisibility(View.GONE);
            //设置渐变的头部的背景颜色
            shopProductDetailsRlHead.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            initHeadView();
        } else if (yScrollView > 0 && yScrollView <= bannerHeight) {
            //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) yScrollView / bannerHeight;
            int alpha = (int) (scale * 255);
            shopProductDetailsRlHead.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        } else {
            shopProductDetailsTab.setVisibility(View.VISIBLE);
            //滑动到banner下面设置普通颜色
            shopProductDetailsRlHead.setBackgroundColor(getResources().getColor(R.color.colorFFFFFF));
            initSCHeadView();
            if (item1 != 0 && item2 != 0 && item3 != 0 && item5 != 0) {
                if (item1 > yScrollView) {
                    shopProductDetailsTab.setCurrentTab(0);
                } else if (item2 > yScrollView) {
                    shopProductDetailsTab.setCurrentTab(1);
                } else if (isShow ? item4 > yScrollView : item3 > yScrollView) {
                    shopProductDetailsTab.setCurrentTab(2);
                }
            }
        }
    }

    private void initSCHeadView() {
        HeadBean headBean = new HeadBean();
        if (mType == TagParameteBean.videoBringGoods
                || mType == TagParameteBean.liveBringGoods) {
            headBean.setRlSearch(false)
                    .setImgLeftIcon(R.mipmap.icon_head_back_gray)
                    .setImgRight(false)
                    .setImgRightIcon(R.mipmap.icon_head_more_gray);
        } else {
            headBean.setRlSearch(false)
                    .setImgLeftIcon(R.mipmap.icon_head_back_gray)
                    .setImgRight(false)
                    .setImgRightIcon(R.mipmap.icon_head_more_gray)
                    .setImgRightIconBg(null)
                    .setImgToleft(false)
                    .setImgToleftIcon(R.mipmap.icon_head_shop_cart_gray)
                    .setImgToleft2(false)
                    .setImgToleftIcon2(R.mipmap.icon_head_share_gray);
        }
        shopProductDetailsHeadView.setHead(headBean);
    }

    /**
     * 设置布局
     */
    public void setAdapter(List<DetailsModelBean> list) {
        shopProductDetailsRecycler.setNestedScrollingEnabled(false);

        detailsAdapter = new DetailsAdapter(this);
        detailsAdapter.setOnCallBack(this);//item点击事件
        detailsAdapter.setOnCallComment(this);//全部评论点击事件
        detailsAdapter.setOnCallSort(this);//规格点击事件
        detailsAdapter.setOnCallRecommendAll(this);//全部相关推荐点击事件
        detailsAdapter.setDataList(list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        shopProductDetailsRecycler.setLayoutManager(linearLayoutManager);
        shopProductDetailsRecycler.setAdapter(detailsAdapter);
        detailsAdapter.setListener(new DetailsAdapter.OnItemHeightListener() {
            @Override
            public void setOnItemHeightListener(int height, int type) {
                if (height != 0) {
                    if (type == 1001) {
                        item1 = height + bannerHeight;
                    } else if (type == 1002) {
                        item2 = item1 + height;
                    } else if (type == 1003) {
                        item3 = item2 + height;
                    } else if (type == 1004) {
                        item4 = item3 + height;
                    } else if (type == 1005) {
                        item5 = item4 + height;
                    }
                }
            }
        });
    }

    /**
     * 点击事件回调
     *
     * @param view
     * @param postion
     */
    @Override
    public void onCallItem(View view, int postion) {

    }

    /**
     * 设置商品信息
     *
     * @param bean
     */
    @Override
    public void setSearchMerchandiseDetail(ProductDetailsBean bean) {
        productDetailsBean = bean;
        initBanner();
        initSearchMerchandiseDetail();
    }

    /**
     * 设置Banner
     */
    public void initBanner() {
        if (productDetailsBean != null) {
            for (int i = 0; i < productDetailsBean.getMainImages().length; i++) {
                if (TextUtils.isEmpty(productDetailsBean.getMainImages()[i])) {
                    mBannerList.add(
                            new DataBean(R.mipmap.icon_shop_international_banner, "商品详情", 1));
                } else {
                    mBannerList.add(
                            new DataBean(productDetailsBean.getMainImages()[i], "商品详情", 1));
                }
            }
        }
//        if (!TextUtils.isEmpty(mBannerList.get(0).imageUrl)) {
//            ObtainWebPictureSizeUtils.getUrlPicSize(mBannerList.get(0).imageUrl, this);
//        } else {
//            ObtainWebPictureSizeUtils.getMipmapPicSize(this, mBannerList.get(0).imageRes, this);
//        }
        shopProductDetailsBanner
                .setHeight(ScreenUtils.getScreenWidth(this))
                .isAutoLoop(mBannerList.size() != 0 ?
                        mBannerList.size() != 1 ? true : false
                        :
                        DataBean.getDataVideo().size() != 1 ? true : false)
                .isAttachToBanner(true)
                .setData(mBannerList.size() != 0 ?
                        mBannerList : DataBean.getDataVideo());

    }

    @Override
    public void onCallBannerItem(int position) {
        List<LocalMedia> list = new ArrayList<>();
        for (int i = 0; i < mBannerList.size(); i++) {
            LocalMedia media = new LocalMedia();
            String url = mBannerList.get(i).imageUrl;
            media.setPath(url);
            list.add(media);
        }
        PictureParameter.PreviewImg(this, position, list);
    }

    @Override
    public void onImageSize(int width, int height) {
        shopProductDetailsBanner
                .setHeight(height * (ScreenUtils.getScreenWidth(this) / width))
                .isAutoLoop(mBannerList.size() != 0 ?
                        mBannerList.size() != 1 ? true : false
                        :
                        DataBean.getDataVideo().size() != 1 ? true : false)
                .isAttachToBanner(true)
                .setData(mBannerList.size() != 0 ?
                        mBannerList : DataBean.getDataVideo());
    }

    /**
     * 显示商品信息
     */
    @Override
    public void initSearchMerchandiseDetail() {
        if (productDetailsBean != null) {
            detailsAdapter.setProductDetailsBean(productDetailsBean);
            detailsAdapter.notifyDataSetChanged();
        } else {
            shopProductDetailsBanner
                    .setHeight(ScreenUtils.getScreenWidth(this))
                    .isAutoLoop(DataBean.getDataVideo().size() != 1 ? true : false)
                    .isAttachToBanner(true)
                    .setData(DataBean.getDataVideo());
            productDetailsBean = new ProductDetailsBean();
            detailsAdapter.setProductDetailsBean(productDetailsBean);
            detailsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置相关推荐
     *
     * @param bean
     */
    @Override
    public void setSearchRecommend(List<RecommendBean> bean) {
        recommendBeanList = bean;
        initSearchRecommend();
    }

    /**
     * 显示相关推荐
     */
    @Override
    public void initSearchRecommend() {
        if (recommendBeanList != null) {
            detailsAdapter.setRecommendBeanList(recommendBeanList, isShow);
            detailsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置商品评论
     *
     * @param bean
     */
    @Override
    public void setComment(CommentBean bean) {
        commentBean = bean;
        initComment();
    }

    /**
     * 显示商品评论
     */
    @Override
    public void initComment() {
        if (commentBean != null) {
            detailsAdapter.setCommentListBeans(commentBean);
            detailsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置商品详情
     *
     * @param bean
     */
    @Override
    public void setDetail(DetailBean bean) {
        detailBean = bean;
        initDetail();
    }

    /**
     * 显示商品详情
     */
    @Override
    public void initDetail() {
        if (detailBean != null) {
            detailsAdapter.setDetailBean(detailBean);
            detailsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setSku(SkuBean bean) {
        skuBean = bean;
        initSku();
    }

    SortDialogView sortDialogView;

    @Override
    public void initSku() {
        if (skuBean != null) {
            if (productDetailsBean == null) {
                return;
            }
            skuBean.setPrice(productDetailsBean.getMinSalePrice())
                    .setImgUrl(productDetailsBean.getCoverImages())
                    .setStock(productDetailsBean.getTotalStock());
            sortDialogView = new SortDialogView(shoppingType, skuBean, mLot, mBizId);
            sortDialogView.setOnCallSort(this);
            sortDialogView.show(getSupportFragmentManager(), "shop_info");
        }
    }

    /**
     * 商品详情-商品库存
     *
     * @param bean
     */
    @Override
    public void setStock(String bean) {
        sortDialogView.setBean(skuBean.setStock(Integer.parseInt(bean)),true);
        initStock();
    }

    @Override
    public void initStock() {

    }

    /**
     * 设置APP-查询商品是否加入收藏夹接口数据
     *
     * @param bean
     */
    @Override
    public void setFindFavoritesFlag(String bean) {
        isCollect = Boolean.parseBoolean(bean);
        initFindFavoritesFlag();
    }

    /**
     * 显示APP-查询商品是否加入收藏夹接口数据
     */
    @Override
    public void initFindFavoritesFlag() {
        Drawable drawable;
        if (isCollect) {
            shopProductDetailsTvFavorites.setText("已收藏");
            shopProductDetailsTvFavorites.setTextColor(getResources().getColor(R.color.colorEA5504));
            drawable = getResources().getDrawable(R.mipmap.icon_shop_favorites_gray_x);
        } else {
            shopProductDetailsTvFavorites.setText("收藏夹");
            shopProductDetailsTvFavorites.setTextColor(getResources().getColor(R.color.colorAAAAAA));
            drawable = getResources().getDrawable(R.mipmap.icon_shop_favorites_gray);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        shopProductDetailsTvFavorites.setCompoundDrawables(null, drawable, null, null);
        shopProductDetailsTvFavorites.setCompoundDrawablePadding(8);
    }

    /**
     * 添加到收藏夹
     *
     * @param bean
     */
    @Override
    public void setAddFavorites(String bean) {
        isCollect = true;
        initAddFavorites();
    }

    @Override
    public void initAddFavorites() {
        initFindFavoritesFlag();
    }

    /**
     * 设置商品详情页移除收藏夹接口数据
     *
     * @param bean
     */
    @Override
    public void setDeleteFavorites(String bean) {
        isCollect = false;
        initDeltetFavorites();
    }

    /**
     * 显示商品详情页移除收藏夹接口数据
     */
    @Override
    public void initDeltetFavorites() {
        initFindFavoritesFlag();
    }

    /**
     * 设置APP-购物车新增商品接口
     *
     * @param bean
     */
    @Override
    public void setAddGoods(String bean) {
        initAddGoods(1);
    }

    /**
     * 显示购物车新增商品接口
     */
    @Override
    public void initAddGoods(int type) {
        if (type == 1) {
            if (sortDialogView != null) {
                sortDialogView.dismiss();
            }
            ToastView.showToast("添加购物车成功");
        } else if (type == 2) {
            ToastView.showToast("添加购物车失败");
        }
    }

    /**
     * 查看全部评论
     */
    @Override
    public void onClickAll() {
        isComment = true;
        shopProductDetailsRlHead.setBackgroundColor(getResources().getColor(R.color.colorFFFFFF));
        HeadBean headBean = new HeadBean()
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("商品评价")
                .setImgLeftIcon(R.mipmap.icon_head_back_gray)
                .setImgRight(false);
        shopProductDetailsHeadView.setHead(headBean);
        shopProductDetailsTab.setVisibility(View.GONE);
        shopProductDetailsFl.setVisibility(View.VISIBLE);
        if (commentFragment == null) {
            commentFragment = new CommentFragment();
            commentFragment.setOnAnimation(this);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.shop_product_details_fl, commentFragment.getInstance(mSpuId));
            transaction.commit();
        } else {
            commentFragment.startUpAnimation(shopProductDetailsFl);
        }
    }

    /**
     * 返回键按钮
     */
    @Override
    public void onClickBack() {
        if (isComment) {
            isComment = false;
            if (commentFragment != null) {
                commentFragment.startDownAnimation(shopProductDetailsFl);
            }
            initScrollView(yScrollView);
        } else {
            finish();
        }
    }

    @Override
    public void onAnimationEnd() {
        //清除动画
        shopProductDetailsFl.clearAnimation();
        shopProductDetailsFl.setVisibility(View.GONE);
    }

    /**
     * 查看分类
     */
    @Override
    public void onClickSort() {
//        shoppingType = 1;
//        mPresenter.getSku(mSpuId);
    }

    /**
     * 查看全部相关推荐
     */
    @Override
    public void onClickRecommendAll() {
        RouterHelper.toRecommend();
    }

    /**
     * 商品详情-商品库存
     */
    @Override
    public void onCallSort(String skuId) {
        mPresenter.getStock(skuId);
    }

    /**
     * 加入购物车
     *
     * @param bean
     */
    @Override
    public void onCallAddShoppingCart(OrderNumBean bean) {
        bean.setAreaId(productDetailsBean.getSpecialId())
                .setCartType(0)
                .setCreateBy(UserStore.getYbCode())
                .setCreateGoodsName(productDetailsBean.getGoodsName())
                .setGoodsImgUrl(productDetailsBean.getCoverImages())
                .setGoodsType(productDetailsBean.getSpecialType());
        mPresenter.getAddGoods(bean);
    }

    @Override
    public void onCallModifySku(OrderNumBean bean) {

    }

    /**
     * 监听系统自带返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (isComment) {
                isComment = false;
                if (commentFragment != null) {
                    commentFragment.startDownAnimation(shopProductDetailsFl);
                }
                initScrollView(yScrollView);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

}

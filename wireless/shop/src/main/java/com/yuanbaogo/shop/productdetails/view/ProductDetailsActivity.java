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
 * @Description: ??????????????????
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
     * ?????????
     */
    RelativeLayout shopProductDetailsRlHead;

    /**
     * ?????????Head
     */
    HeadView shopProductDetailsHeadView;

    /**
     * ????????????Tab  ?????? ?????? ?????? ??????
     */
    CommonTabLayout shopProductDetailsTab;

    /**
     * tab ??????
     */
    private final String[] mTitles = {"??????", "??????", "??????"};

    /**
     * Tab
     */
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    /**
     * ?????????ScrollView (??????????????????)
     */
    ObservableScrollView shopProductDetailsScroll;

    /**
     * Banner ??????????????????
     */
    BannerView shopProductDetailsBanner;

    /**
     * ???????????????
     */
    List<DataBean> mBannerList = new ArrayList<>();

    /**
     * Banner ??????
     */
    private int bannerHeight;

    /**
     * ??????
     */
    RecyclerView shopProductDetailsRecycler;

    /**
     * ?????????????????????
     */
    DetailsAdapter detailsAdapter;

    /**
     * ????????????
     */
    private int item1 = 0;//????????????
    private int item2 = 0;//??????
    private int item3 = 0;//????????????
    private int item4 = 0;//??????
    private int item5 = 0;//????????????

    /**
     * ?????? ????????????
     */
    FrameLayout shopProductDetailsFl;

    /**
     * ???????????????
     */
    TextView shopProductDetailsTvAddShopCart;

    /**
     * ????????????
     */
    TextView shopProductDetailsTvBuy;

    /**
     * ??????
     */
    TextView shopProductDetailsTvFavorites;

    /**
     * ??????ID
     */
    @Autowired(name = YBRouter.ProductDetailsActivityParams.spuId)
    String mSpuId;

    /**
     * ????????????
     */
    ProductDetailsBean productDetailsBean;

    /**
     * ????????????
     */
    List<RecommendBean> recommendBeanList;

    /**
     * ??????
     */
    CommentBean commentBean;

    /**
     * ????????????
     */
    DetailBean detailBean;

    /**
     * ScrollView y?????????
     */
    int yScrollView = 0;

    /**
     * ????????????
     */
    CommentFragment commentFragment;

    /**
     * ????????????????????????   ???????????????
     * ???????????????????????? ??????finish??????????????????  ??????????????????????????????
     */
    boolean isComment = false;

    /**
     * ????????????
     */
    SkuBean skuBean;

    /**
     * 1 ????????????  2 ??????????????? 11 12 ???????????????|???????????? ????????????
     */
    int shoppingType = 1;

    /**
     * ????????????
     */
    boolean isCollect = false;

    /**
     * 11 ??????????????? 12 ????????????
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
     * ????????????????????????
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
        //??????????????????
        mPresenter.getSearchMerchandiseDetail(mSpuId);
        //??????????????????
        mPresenter.getSearchRecommend(0);
        //??????????????????
        mPresenter.getComment(1, 3, mSpuId);
        //??????????????????
        mPresenter.getDetail(mSpuId);
        //????????????
        mPresenter.getFindFavoritesFlag(mSpuId, UserStore.getYbCode());
        mPresenter.initDisplayData();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.shop_product_details_tv_add_shop_cart) {//???????????????
//            if (!UserStore.isLogin()) {
//                RouterHelper.toLogin();
//                return;
//            }
            shoppingType = 2;
            mPresenter.getSku(mSpuId);
        } else if (id == R.id.shop_product_details_tv_buy) {//????????????
//            if (!UserStore.isLogin()) {
//                RouterHelper.toLogin();
//                return;
//            }
            shoppingType = 1;
            mPresenter.getSku(mSpuId);
        } else if (id == R.id.lib_head_img_toleft) {//????????????
            RouterHelper.toShopCart();
        } else if (id == R.id.shop_product_details_tv_favorites) {//??????
            if (!UserStore.isLogin()) {
                RouterHelper.toLogin();
                return;
            }
            if (isCollect) {
                mPresenter.getDeleteFavorites(mSpuId, UserStore.getYbCode());
            } else {
                mPresenter.getAddFavorites(mSpuId, UserStore.getYbCode(), 3);
            }
        } else if (id == R.id.shop_product_details_tv_buy_now) {//???????????? ????????????
            shoppingType = mType;
            mPresenter.getSku(mSpuId);
        } else if (id == R.id.shop_product_details_tv_consult) {//??????
            if (!UserStore.isLogin()) {
                RouterHelper.toLogin();
                return;
            }
            //???????????????
            if (ClickUtils.isFastClick()) {
                return;
            }
            CallCenter.toService(mSpuId,
                    null,
                    "??" + productDetailsBean.getMinSalePrice() / 100,
                    productDetailsBean.getCoverImages(),
                    productDetailsBean.getGoodsName());
        }
    }

    /**
     * ?????????HeadView
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
     * ??????tab
     */
    public void initTab() {
        //??????tab
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabBean(mTitles[i]));
        }
        shopProductDetailsTab.setTabData(mTabEntities);
        shopProductDetailsTab.setOnTabSelectListener(this);
    }

    /**
     * tab????????????
     *
     * @param position
     */
    @Override
    public void onTabSelect(int position) {
        if (position == 0) {//??????
            shopProductDetailsScroll.smoothScrollTo(0, bannerHeight);
        } else if (position == 1) {//??????
            shopProductDetailsScroll.smoothScrollTo(0, item1);
        } else if (position == 2) {//??????
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
     * ????????????????????????????????????????????????
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
            //????????????????????????????????????
            shopProductDetailsRlHead.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            initHeadView();
        } else if (yScrollView > 0 && yScrollView <= bannerHeight) {
            //??????????????????banner??????????????????????????????????????????????????????????????????
            float scale = (float) yScrollView / bannerHeight;
            int alpha = (int) (scale * 255);
            shopProductDetailsRlHead.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        } else {
            shopProductDetailsTab.setVisibility(View.VISIBLE);
            //?????????banner????????????????????????
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
     * ????????????
     */
    public void setAdapter(List<DetailsModelBean> list) {
        shopProductDetailsRecycler.setNestedScrollingEnabled(false);

        detailsAdapter = new DetailsAdapter(this);
        detailsAdapter.setOnCallBack(this);//item????????????
        detailsAdapter.setOnCallComment(this);//????????????????????????
        detailsAdapter.setOnCallSort(this);//??????????????????
        detailsAdapter.setOnCallRecommendAll(this);//??????????????????????????????
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
     * ??????????????????
     *
     * @param view
     * @param postion
     */
    @Override
    public void onCallItem(View view, int postion) {

    }

    /**
     * ??????????????????
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
     * ??????Banner
     */
    public void initBanner() {
        if (productDetailsBean != null) {
            for (int i = 0; i < productDetailsBean.getMainImages().length; i++) {
                if (TextUtils.isEmpty(productDetailsBean.getMainImages()[i])) {
                    mBannerList.add(
                            new DataBean(R.mipmap.icon_shop_international_banner, "????????????", 1));
                } else {
                    mBannerList.add(
                            new DataBean(productDetailsBean.getMainImages()[i], "????????????", 1));
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
     * ??????????????????
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
     * ??????????????????
     *
     * @param bean
     */
    @Override
    public void setSearchRecommend(List<RecommendBean> bean) {
        recommendBeanList = bean;
        initSearchRecommend();
    }

    /**
     * ??????????????????
     */
    @Override
    public void initSearchRecommend() {
        if (recommendBeanList != null) {
            detailsAdapter.setRecommendBeanList(recommendBeanList, isShow);
            detailsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * ??????????????????
     *
     * @param bean
     */
    @Override
    public void setComment(CommentBean bean) {
        commentBean = bean;
        initComment();
    }

    /**
     * ??????????????????
     */
    @Override
    public void initComment() {
        if (commentBean != null) {
            detailsAdapter.setCommentListBeans(commentBean);
            detailsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * ??????????????????
     *
     * @param bean
     */
    @Override
    public void setDetail(DetailBean bean) {
        detailBean = bean;
        initDetail();
    }

    /**
     * ??????????????????
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
     * ????????????-????????????
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
     * ??????APP-?????????????????????????????????????????????
     *
     * @param bean
     */
    @Override
    public void setFindFavoritesFlag(String bean) {
        isCollect = Boolean.parseBoolean(bean);
        initFindFavoritesFlag();
    }

    /**
     * ??????APP-?????????????????????????????????????????????
     */
    @Override
    public void initFindFavoritesFlag() {
        Drawable drawable;
        if (isCollect) {
            shopProductDetailsTvFavorites.setText("?????????");
            shopProductDetailsTvFavorites.setTextColor(getResources().getColor(R.color.colorEA5504));
            drawable = getResources().getDrawable(R.mipmap.icon_shop_favorites_gray_x);
        } else {
            shopProductDetailsTvFavorites.setText("?????????");
            shopProductDetailsTvFavorites.setTextColor(getResources().getColor(R.color.colorAAAAAA));
            drawable = getResources().getDrawable(R.mipmap.icon_shop_favorites_gray);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        shopProductDetailsTvFavorites.setCompoundDrawables(null, drawable, null, null);
        shopProductDetailsTvFavorites.setCompoundDrawablePadding(8);
    }

    /**
     * ??????????????????
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
     * ????????????????????????????????????????????????
     *
     * @param bean
     */
    @Override
    public void setDeleteFavorites(String bean) {
        isCollect = false;
        initDeltetFavorites();
    }

    /**
     * ????????????????????????????????????????????????
     */
    @Override
    public void initDeltetFavorites() {
        initFindFavoritesFlag();
    }

    /**
     * ??????APP-???????????????????????????
     *
     * @param bean
     */
    @Override
    public void setAddGoods(String bean) {
        initAddGoods(1);
    }

    /**
     * ?????????????????????????????????
     */
    @Override
    public void initAddGoods(int type) {
        if (type == 1) {
            if (sortDialogView != null) {
                sortDialogView.dismiss();
            }
            ToastView.showToast("?????????????????????");
        } else if (type == 2) {
            ToastView.showToast("?????????????????????");
        }
    }

    /**
     * ??????????????????
     */
    @Override
    public void onClickAll() {
        isComment = true;
        shopProductDetailsRlHead.setBackgroundColor(getResources().getColor(R.color.colorFFFFFF));
        HeadBean headBean = new HeadBean()
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("????????????")
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
     * ???????????????
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
        //????????????
        shopProductDetailsFl.clearAnimation();
        shopProductDetailsFl.setVisibility(View.GONE);
    }

    /**
     * ????????????
     */
    @Override
    public void onClickSort() {
//        shoppingType = 1;
//        mPresenter.getSku(mSpuId);
    }

    /**
     * ????????????????????????
     */
    @Override
    public void onClickRecommendAll() {
        RouterHelper.toRecommend();
    }

    /**
     * ????????????-????????????
     */
    @Override
    public void onCallSort(String skuId) {
        mPresenter.getStock(skuId);
    }

    /**
     * ???????????????
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
     * ???????????????????????????
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

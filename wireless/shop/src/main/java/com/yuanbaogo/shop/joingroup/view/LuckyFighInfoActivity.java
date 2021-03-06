package com.yuanbaogo.shop.joingroup.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.callcenter.CallCenter;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.OrderNumBean;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.joingroup.adapter.LuckyFighInfoAdapter;
import com.yuanbaogo.shop.joingroup.contract.LuckyFighInfoContract;
import com.yuanbaogo.shop.joingroup.model.LuckyFighInfoBean;
import com.yuanbaogo.shop.joingroup.model.ShopInfoBean;
import com.yuanbaogo.shop.joingroup.presenter.LuckyFighInfoPresenter;
import com.yuanbaogo.shop.productdetails.model.CommentBean;
import com.yuanbaogo.shop.productdetails.model.DetailBean;
import com.yuanbaogo.shop.productdetails.model.DetailsModelBean;
import com.yuanbaogo.shop.productdetails.model.TabBean;
import com.yuanbaogo.shop.productdetails.view.CommentFragment;
import com.yuanbaogo.shop.publics.model.RecommendBean;
import com.yuanbaogo.zui.banner.BannerView;
import com.yuanbaogo.zui.banner.bean.DataBean;
import com.yuanbaogo.zui.banner.utils.ObtainWebPictureSizeUtils;
import com.yuanbaogo.zui.dialog.ConfirmDialogView;
import com.yuanbaogo.zui.dialog.PactDialogView;
import com.yuanbaogo.zui.dialog.SortDialogView;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;
import com.yuanbaogo.zui.dialog.model.PactBean;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.head.call.OnCallHeadBack;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.scrollview.ObservableScrollView;
import com.yuanbaogo.zui.view.PictureParameter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: ??????????????????
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/28/21 5:04 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.LUCKY_FIGH_INFO_ACTIVITY)
public class LuckyFighInfoActivity extends MvpBaseActivityImpl<LuckyFighInfoContract.View, LuckyFighInfoPresenter>
        implements LuckyFighInfoContract.View, View.OnClickListener,
        ObservableScrollView.ScrollViewListener, OnTabSelectListener,
        OnCallRecyclerItem, LuckyFighInfoAdapter.OnCallComment,
        OnCallHeadBack, CommentFragment.OnAnimation,
        LuckyFighInfoAdapter.OnCallRecommendAll, SortDialogView.OnCallSort,
        ObtainWebPictureSizeUtils.OnPicListener, LuckyFighInfoAdapter.OnCallCountdownEnd, OnCallDialog,
        LuckyFighInfoAdapter.OnRuleDescription, BannerView.OnCallBanner {

    /**
     * ??????
     */
    RelativeLayout shopLuckyDrawInfoRlHead;

    /**
     * ?????????HeadView
     */
    HeadView shopLuckyDrawInfoHeadView;

    /**
     * tablayout
     */
    CommonTabLayout shopLuckyDrawInfoTab;

    /**
     * tab ??????
     */
    private final String[] mTitles = {"??????", "??????", "??????", "??????"};

    /**
     * Tab
     */
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    /**
     * ??????
     */
    SmartRefreshLayout shopLuckyDrawInfoSmart;

    /**
     * ?????????ScrollView
     */
    ObservableScrollView shopLuckyDrawInfoScroll;

    /**
     * ?????????Banner
     */
    BannerView shopLuckyDrawInfoBanner;

    /**
     * ???????????????
     */
    List<DataBean> mBannerList = new ArrayList<>();

    /**
     * Banner ??????
     */
    private int bannerHeight;

    /**
     * ???????????????????????????  ????????????  ????????????  ????????????  ???????????????
     */
    RecyclerView shopLuckyDrawInfoRecycler;

    /**
     * ?????????????????????
     */
    LuckyFighInfoAdapter luckyFighInfoAdapter;

    /**
     * ????????????
     */
    private int item1 = 0;//????????????
    private int item2 = 0;//??????
    private int item3 = 0;//????????????
    private int item4 = 0;//??????
    private int item5 = 0;//????????????

    /**
     * ScrollView y?????????
     */
    int yScrollView = 0;

    /**
     * ?????????????????????   ???????????????
     */
    LinearLayout shopLuckyDrawInfoLlBut;

    /**
     * ????????????
     */
    TextView shopLuckyDrawInfoTvEnd;

    /**
     * ?????????ID
     */
    @Autowired(name = YBRouter.LuckyFighInfoActivityParams.id)
    String luckyFighID;

    /**
     * ????????????Model
     */
    LuckyFighInfoBean luckyFighInfoBean;

    /**
     * ????????????Model
     */
    List<RecommendBean> recommendBeanList;

    /**
     * ??????Model
     */
    CommentBean commentBean;

    /**
     * ???????????? Model
     */
    DetailBean detailBean;

    /**
     * ?????? ????????????
     */
    FrameLayout shopLuckyDrawInfoFl;

    /**
     * ???????????? ????????????
     */
    CommentFragment commentFragment;

    /**
     * ??????
     */
    TextView shopLuckyDrawConsult;

    /**
     * ????????????????????????   ???????????????
     * ???????????????????????? ??????finish??????????????????  ??????????????????????????????
     */
    boolean isComment = false;

    TextView shopLuckyDrawInfoTvBuy;

    TextView shopLuckyDrawInfoTvAddShopCart;

    ImageView shopLuckyDrawInfoImgBanner;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_lucky_figh_info;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopLuckyDrawInfoRlHead = findViewById(R.id.shop_lucky_draw_info_rl_head);
        shopLuckyDrawInfoHeadView = findViewById(R.id.shop_lucky_draw_info_head_view);
        shopLuckyDrawInfoTab = findViewById(R.id.shop_lucky_draw_info_tab);
        shopLuckyDrawInfoSmart = findViewById(R.id.shop_lucky_draw_info_smart);
        shopLuckyDrawInfoScroll = findViewById(R.id.shop_lucky_draw_info_scroll);
        shopLuckyDrawInfoBanner = findViewById(R.id.shop_lucky_draw_info_banner);
        shopLuckyDrawInfoRecycler = findViewById(R.id.shop_lucky_draw_info_recycler);
        shopLuckyDrawInfoLlBut = findViewById(R.id.shop_lucky_draw_info_ll_but);
        shopLuckyDrawInfoTvEnd = findViewById(R.id.shop_lucky_draw_info_tv_end);
        shopLuckyDrawInfoFl = findViewById(R.id.shop_lucky_draw_info_fl);
        shopLuckyDrawInfoTvBuy = findViewById(R.id.shop_lucky_draw_info_tv_buy);
        shopLuckyDrawInfoTvAddShopCart = findViewById(R.id.shop_lucky_draw_info_tv_add_shop_cart);
        shopLuckyDrawConsult = findViewById(R.id.shop_lucky_draw_consult);
        shopLuckyDrawInfoImgBanner = findViewById(R.id.shop_lucky_draw_info_img_banner);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        shopLuckyDrawInfoHeadView.getLibHeadImgRight().setOnClickListener(this);//??????
        shopLuckyDrawInfoTvBuy.setOnClickListener(this);
        shopLuckyDrawInfoTvAddShopCart.setOnClickListener(this);
        shopLuckyDrawConsult.setOnClickListener(this);
        shopLuckyDrawInfoBanner.setOnCallBanner(this);
    }

    ArrayList<String> skuIdList = new ArrayList<>();

    OrderNumBean orderNumBean = new OrderNumBean();

    /**
     * 1 ???????????? 2 ??????
     */
    int type;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (!UserStore.isLogin()) {
            RouterHelper.toLogin();
            return;
        }
        //???????????????
        if (ClickUtils.isFastClick()) {
            return;
        }
        if (id == R.id.lib_head_img_right) {//??????
            if (luckyFighInfoBean.getActiivitiesStatus() == 3) {
                return;
            }
            type = 2;
            mPresenter.getParticipated(luckyFighID);
        } else if (id == R.id.shop_lucky_draw_info_tv_buy) {
            type = 1;
            mPresenter.getParticipated(luckyFighID);
        } else if (id == R.id.shop_lucky_draw_info_tv_add_shop_cart) {
            type = 2;
            mPresenter.getParticipated(luckyFighID);
        } else if (id == R.id.shop_lucky_draw_consult) {
            //??????????????????
            CallCenter.toService(luckyFighID,
                    null,
                    "??" + luckyFighInfoBean.getGroupGoodsPrice() / 100,
                    luckyFighInfoBean.getCoverImages(),
                    luckyFighInfoBean.getGoodsName());
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        mPresenter.initDisplayData();
        //????????????
        mPresenter.getGoodsDetail(luckyFighID);
        //??????????????????
        mPresenter.getSearchRecommend(0);
        //??????????????????
        mPresenter.getComment(1, 3, luckyFighID);
    }

    /**
     * ?????????HeadView
     */
    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setRlSearch(false)
                .setImgLeftIcon(R.mipmap.icon_head_back_white_yuan)
                .setImgRightIcon(R.mipmap.icon_head_share);
        shopLuckyDrawInfoHeadView.setHead(headBean);
    }

    @Override
    public void initTab() {
        //??????tab
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabBean(mTitles[i]));
        }
        shopLuckyDrawInfoTab.setTabData(mTabEntities);
        shopLuckyDrawInfoTab.setOnTabSelectListener(this);
    }

    /**
     * tab????????????
     *
     * @param position
     */
    @Override
    public void onTabSelect(int position) {
        if (position == 0) {//??????
            shopLuckyDrawInfoScroll.smoothScrollTo(0, bannerHeight);
        } else if (position == 1) {//??????
            shopLuckyDrawInfoScroll.smoothScrollTo(0, item1);
        } else if (position == 2) {//??????
            shopLuckyDrawInfoScroll.smoothScrollTo(0, item3);
        } else if (position == 3) {//??????
            shopLuckyDrawInfoScroll.smoothScrollTo(0, item4);
        }
    }

    @Override
    public void onTabReselect(int position) {

    }

    /**
     * ????????????????????????????????????????????????
     */
    public void initListener() {
        ViewTreeObserver treeObserver = shopLuckyDrawInfoBanner.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                shopLuckyDrawInfoBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                bannerHeight = ScreenUtils.getScreenWidth(LuckyFighInfoActivity.this) - 300;
                shopLuckyDrawInfoScroll.setScrollViewListener(LuckyFighInfoActivity.this);
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
            shopLuckyDrawInfoTab.setVisibility(View.GONE);
            //????????????????????????????????????
            shopLuckyDrawInfoRlHead.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            initHeadView();
        } else if (yScrollView > 0 && yScrollView <= bannerHeight) {
            //??????????????????banner??????????????????????????????????????????????????????????????????
            float scale = (float) yScrollView / bannerHeight;
            int alpha = (int) (scale * 255);
            shopLuckyDrawInfoRlHead.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        } else {
            shopLuckyDrawInfoTab.setVisibility(View.VISIBLE);
            //?????????banner????????????????????????
            shopLuckyDrawInfoRlHead.setBackgroundColor(getResources().getColor(R.color.colorFFFFFF));
            HeadBean headBean = new HeadBean()
                    .setRlSearch(false)
                    .setImgLeftIcon(R.mipmap.icon_head_back_gray)
                    .setImgRightIcon(R.mipmap.icon_head_share_gray);
            shopLuckyDrawInfoHeadView.setHead(headBean);
            if (item1 != 0 && item2 != 0 && item3 != 0 && item4 != 0 && item5 != 0) {
                if (item1 > yScrollView) {
                    shopLuckyDrawInfoTab.setCurrentTab(0);
                } else if (item2 > yScrollView) {
                    shopLuckyDrawInfoTab.setCurrentTab(1);
                } else if (item4 > yScrollView) {
                    shopLuckyDrawInfoTab.setCurrentTab(2);
                } else if (item5 > yScrollView) {
                    shopLuckyDrawInfoTab.setCurrentTab(3);
                }
            }
        }
    }

    /**
     * ????????????
     */
    public void setAdapter(List<DetailsModelBean> list) {
        shopLuckyDrawInfoRecycler.setNestedScrollingEnabled(false);

        luckyFighInfoAdapter = new LuckyFighInfoAdapter(this);
        luckyFighInfoAdapter.setOnCallBack(this);//item????????????
        luckyFighInfoAdapter.setOnCallComment(this);//????????????????????????
        luckyFighInfoAdapter.setOnCallRecommendAll(this);//??????????????????????????????
        luckyFighInfoAdapter.setOnCallCountdownEnd(this);//??????????????? ????????????
        luckyFighInfoAdapter.setOnRuleDescription(this);//????????????
        luckyFighInfoAdapter.setDataList(list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        shopLuckyDrawInfoRecycler.setLayoutManager(linearLayoutManager);
        shopLuckyDrawInfoRecycler.setAdapter(luckyFighInfoAdapter);
        luckyFighInfoAdapter.setListener(new LuckyFighInfoAdapter.OnItemHeightListener() {
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

    @Override
    public void setGoodsDetail(LuckyFighInfoBean bean) {
        luckyFighInfoBean = bean;
        initBanner();
        initBottom();
        initGoodsDetail();
    }

    private void initBottom() {
        if (luckyFighInfoBean.getCountdownTime() > 0) {
            shopLuckyDrawInfoLlBut.setVisibility(View.VISIBLE);
            shopLuckyDrawInfoTvEnd.setVisibility(View.GONE);
            if (UserStore.isLogin() && luckyFighInfoBean.getParticipated()) {
                shopLuckyDrawInfoTvBuy.setText("?????????");
                shopLuckyDrawInfoTvBuy.setTextColor(getResources().getColor(R.color.colorE03030));
                shopLuckyDrawInfoTvBuy.setBackground(getResources().getDrawable(R.drawable.shape_gradient_left_bg_e4e4e4_10));
            } else {
                shopLuckyDrawInfoTvBuy.setText("????????????");
                shopLuckyDrawInfoTvBuy.setTextColor(getResources().getColor(R.color.colorFFFFFF));
                shopLuckyDrawInfoTvBuy.setBackground(getResources().getDrawable(R.drawable.shape_gradient_left_bg_ea5504_10));
            }
        } else {
            shopLuckyDrawInfoLlBut.setVisibility(View.GONE);
            shopLuckyDrawInfoTvEnd.setVisibility(View.VISIBLE);
        }
    }

    /**
     * ?????????Banner
     */
    private void initBanner() {

        if (luckyFighInfoBean != null) {
            for (int i = 0; i < luckyFighInfoBean.getMainImages().length; i++) {
                if (TextUtils.isEmpty(luckyFighInfoBean.getMainImages()[i])) {
                    mBannerList.add(
                            new DataBean(R.mipmap.icon_shop_international_banner, "????????????", 1));
                } else {
                    mBannerList.add(
                            new DataBean(luckyFighInfoBean.getMainImages()[i], "????????????", 1));
                }
            }
        }

        shopLuckyDrawInfoBanner
                .setHeight(ScreenUtils.getScreenWidth(this))
                .isAutoLoop(mBannerList.size() != 0 ?
                        mBannerList.size() != 1 ? true : false
                        :
                        DataBean.getDataVideo().size() != 1 ? true : false)
                .isAttachToBanner(true)
                .setIndicatorMarginBottom(shopLuckyDrawInfoImgBanner.getHeight() + 20)
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
        shopLuckyDrawInfoBanner
                .setHeight(height * (ScreenUtils.getScreenWidth(this) / width))
                .isAutoLoop(mBannerList.size() != 0 ?
                        mBannerList.size() != 1 ? true : false
                        :
                        DataBean.getDataVideo().size() != 1 ? true : false)
                .isAttachToBanner(true)
                .setIndicatorMarginBottom(100)
                .setData(mBannerList.size() != 0 ?
                        mBannerList : DataBean.getDataVideo());
    }

    @Override
    public void initGoodsDetail() {
        if (luckyFighInfoBean != null) {
            luckyFighInfoAdapter.setProductDetailsBean(luckyFighInfoBean);
            luckyFighInfoAdapter.notifyDataSetChanged();
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
            luckyFighInfoAdapter.setRecommendBeanList(recommendBeanList);
            luckyFighInfoAdapter.notifyDataSetChanged();
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
            luckyFighInfoAdapter.setCommentListBeans(commentBean);
            luckyFighInfoAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 0??????????????????1?????????????????????2???????????????
     */
    int status = 3;

    @Override
    public void setParticipated(String bean) {
        try {
            JSONObject jsonObject = new JSONObject(bean);
            status = jsonObject.getInt("status");
            initParticipated();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    ConfirmDialogView confirmDialogView;

    @Override
    public void initParticipated() {
        if (type == 1) {
            if (status == 0) {
                skuIdList.add(luckyFighID);
                ArrayList<OrderNumBean> numList = new ArrayList<>();
                orderNumBean.setId(luckyFighInfoBean.getId())
                        .setSkuId(luckyFighInfoBean.getSkuId())
                        .setSkuName(luckyFighInfoBean.getSkuIndexesName())
                        .setCreateGoodsPrice(luckyFighInfoBean.getGroupGoodsPrice())
                        .setSpuId(luckyFighInfoBean.getSpuId())
                        .setGoodsCount(1);
                numList.add(orderNumBean);
                RouterHelper.toConfirmOrder(4, skuIdList, numList, null, null);
            } else if (status == 1) {
                confirmDialogView =
                        new ConfirmDialogView("??????", "???????????????????????????\n?????????????????????????????????");
                confirmDialogView.show(getSupportFragmentManager(), "lucky_figh");
                confirmDialogView.setOnCallDialog(this);
            } else if (status == 2) {
                confirmDialogView =
                        new ConfirmDialogView("??????", "??????????????????????????????\n?????????????????????????????????");
                confirmDialogView.show(getSupportFragmentManager(), "lucky_figh");
                confirmDialogView.setOnCallDialog(this);
            }
        } else if (type == 2) {
            if (status == 0) {
                initShareDialog();
            } else if (status == 1) {
                initShareDialog();
            } else if (status == 2) {
                confirmDialogView =
                        new ConfirmDialogView("??????", "??????????????????????????????\n?????????????????????????????????");
                confirmDialogView.show(getSupportFragmentManager(), "lucky_figh");
                confirmDialogView.setOnCallDialog(this);
            }
        }
    }

    private void initShareDialog() {
        ShareBean shareBean = new ShareBean()
                .setTitle("???????????????????????????");
        LuckyFightShareFragment generateMitoDialogView = new LuckyFightShareFragment(
                shareBean,
                new ShopInfoBean().setLotteryTime(luckyFighInfoBean.getLotteryTime())
                        .setCoverImages(luckyFighInfoBean.getCoverImages())
                        .setGoodsName(luckyFighInfoBean.getGoodsName())
                        .setGroupGoodsPrice(luckyFighInfoBean.getGroupGoodsPrice())
                        .setId(luckyFighID));
        generateMitoDialogView.show(getSupportFragmentManager(), "lucky_fight_share");
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

    @Override
    public void onClickAll() {
        isComment = true;
        shopLuckyDrawInfoRlHead.setBackgroundColor(getResources().getColor(R.color.colorFFFFFF));
        HeadBean headBean = new HeadBean()
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("????????????")
                .setImgLeftIcon(R.mipmap.icon_head_back_gray)
                .setImgRightIcon(R.mipmap.icon_head_more_gray)
                .setImgRightIconBg(null)
                .setImgToleft(true)
                .setImgToleftIcon(R.mipmap.icon_head_shop_cart_gray)
                .setImgToleft2(true)
                .setImgToleftIcon2(R.mipmap.icon_head_share_gray);
        shopLuckyDrawInfoHeadView.setHead(headBean);
        shopLuckyDrawInfoTab.setVisibility(View.GONE);
        shopLuckyDrawInfoFl.setVisibility(View.VISIBLE);
        if (commentFragment == null) {
            commentFragment = new CommentFragment();
            commentFragment.setOnAnimation(this);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.shop_product_details_fl, commentFragment.getInstance(luckyFighID));
            transaction.commit();
        } else {
            commentFragment.startUpAnimation(commentFragment.getView());
        }
    }

    @Override
    public void onClickRecommendAll() {
        RouterHelper.toRecommend();
    }

    @Override
    public void onAnimationEnd() {
        //????????????
        shopLuckyDrawInfoFl.clearAnimation();
        shopLuckyDrawInfoFl.setVisibility(View.GONE);
    }

    @Override
    public void onCallSort(String skuId) {

    }

    @Override
    public void onCallAddShoppingCart(OrderNumBean bean) {

    }

    @Override
    public void onCallModifySku(OrderNumBean bean) {

    }

    @Override
    public void onClickBack() {
        if (isComment) {
            isComment = false;
            if (commentFragment != null) {
                commentFragment.startDownAnimation(shopLuckyDrawInfoFl);
            }
            initScrollView(yScrollView);
        } else {
            finish();
        }
    }

    /**
     * ??????????????????????????????
     */
    @Override
    public void onClickEnd() {
        shopLuckyDrawInfoLlBut.setVisibility(View.GONE);
        shopLuckyDrawInfoTvEnd.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        luckyFighInfoAdapter.itemLuckyDrawInfoCountdownView.onDestroyHandler();
        luckyFighInfoAdapter.itemLuckyDrawInfoCountdownView = null;
    }

    @Override
    public void onCallDetermine() {
        if (confirmDialogView != null) {
            confirmDialogView.dismiss();
            finish();
        } else if (ruleDialogView != null) {
            ruleDialogView.dismiss();
        }
    }

    PactDialogView ruleDialogView;

    /**
     * ????????????
     */
    @Override
    public void onClickrule() {
        ruleDialogView = new PactDialogView();
        ruleDialogView.setCancelable(false);
        ruleDialogView.show(this.getSupportFragmentManager(), "rule");
        PactBean pactBean = new PactBean()
                .setTvTitle(true)
                .setTvTitles("????????????")
                .setImgCancel(false)
                .setContentFileName("rule.txt")
                .setLine(true)
                .setCancel(false)
                .setDetermine(true)
                .setTvDetermines("????????????")
                .setType(1);
        ruleDialogView.setPact(pactBean);
        ruleDialogView.setOnCallDialog(this);
    }

}
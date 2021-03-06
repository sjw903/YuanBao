package com.yuanbaogo.shop.main.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;
import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.commonlib.bean.RecommendVideoBean;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.commonlib.utils.array.ArrayTools;
import com.yuanbaogo.commonlib.utils.fragment.ChangeParms;
import com.yuanbaogo.datacenter.constant.WebUrls;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.main.adapter.FunctionAdapter;
import com.yuanbaogo.shop.main.adapter.ShopRecommendLiveAdapter;
import com.yuanbaogo.shop.main.contract.ShopContract;
import com.yuanbaogo.shop.main.model.ShopRecommendVideoBean;
import com.yuanbaogo.shop.main.presenter.ShopPresenter;
import com.yuanbaogo.commonlib.router.model.SearchMerchandiseBean;
import com.yuanbaogo.shop.publics.view.GroupJoiningZoneFragment;
import com.yuanbaogo.zui.animation.DivergeViewSecond;
import com.yuanbaogo.zui.banner.adapter.MultipleTypesAdapter;
import com.yuanbaogo.zui.banner.bean.DataBean;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.scrollview.ObservableScrollView;
import com.yuanbaogo.zui.textview.ADTextView;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description: ????????????
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/2/21 1:14 PM
 * @Modifier:
 * @Modify:
 */
public class ShopFragment extends MvpBaseFragmentImpl<ShopContract.View, ShopPresenter>
        implements ShopContract.View,//View???
        ObservableScrollView.OnAnimationScrollChangeListener,//?????????ScrollView??????
        OnCallRecyclerItem,//RecyclerView item????????????
        View.OnClickListener {

    /**
     * ?????????ScrollView (??????????????????)
     */
    ObservableScrollView shopScrollView;

    /**
     * ?????????????????????
     */
    private float rlSearchMinTopMargin, rlSearchMaxTopMargin, tvTitleMaxTopMargin;

    /**
     * ???????????????????????????
     */
    private ViewGroup.MarginLayoutParams searchLayoutParams, titleLayoutParams, functionParams;

    /**
     * HeadView
     */
    RelativeLayout shopRlHeadView;

    /**
     * ?????????
     */
    RelativeLayout libHeadRlSearch;

    /**
     * ???????????????????????????????????????
     */
    ADTextView libHeadTvSearch;

    /**
     * ?????????????????????
     */
    TextView shopTvHeadLogo;

    /**
     * ???????????????????????????   ??????   ??????   ????????????   ????????????  ????????????
     */
    RelativeLayout shopRlFunction;

    /**
     * ???????????? ???????????????????????????????????????
     */
    RecyclerView shopRecyclerFunction;

    /**
     * ?????????????????????????????????
     */
    RelativeLayout shopRlViewFunction;

    /**
     * ?????????????????????????????????
     */
    View shopViewFunction;

    /**
     * ??????????????????
     */
    List<ArrayItemBean> functionArray = new ArrayList<>();

    /**
     * ???????????? ??????
     */
    private int functionHeight;

    /**
     * ???????????????
     */
    FrameLayout shopGroupJoiningZone;

    /**
     * ????????????????????? ?????????????????? ??????????????????????????????
     */
    static RecyclerView shopRecyclerRecommendLive;

    /**
     * ??????????????????????????????
     */
    static ShopRecommendLiveAdapter shopRecommendLiveAdapter;

    /**
     * ???????????????????????????
     */
    static List<ShopRecommendVideoBean> recommendLiveBean;

    /**
     * ??????????????????????????????
     */
    static List<Bitmap> mListBitmap = new ArrayList<Bitmap>();

    /**
     * ????????????????????????????????????
     */
    private int adCount = 0;

    /**
     * ????????????????????????????????????
     */
    private List<String> adArrList = new ArrayList<String>();

    /**
     * ??????
     */
    ImageView shopImgTop;

    /**
     * ???????????????
     */
    ImageView shopMovableImg1;

    /**
     * ????????????
     */
    ImageView shopMovableImg2;

    /**
     * ????????????
     */
    ImageView shopMovableImg3;

    /**
     * ??????Presenter
     */
    ShopPresenter shopPresenter = new ShopPresenter();

    /**
     * ?????????
     */
    Banner shopBannerLuckyDraw;

    /**
     * ?????????
     */
    RoundLinesIndicator shopBannerLuckyDrawIndicator;

    /**
     * ????????????
     */
    TextView shopTvRecommendLive;

    /**
     * ?????????????????????????????????
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                /**
                 * ?????????????????????????????? handler???????????????????????????
                 */
                handler.postDelayed(this, 3000);
                libHeadTvSearch.next();//?????????????????????
                libHeadTvSearch.setText(adArrList.get(adCount % adArrList.size()));//?????????????????????
                adCount++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Handler
     */
    public static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //???????????????
            super.handleMessage(msg);
            if (msg.what == 2) {
                for (int i = 0; i < shopRecyclerRecommendLive.getChildCount(); i++) {
                    Random random = new Random();
                    int n = random.nextInt(mListBitmap.size());
                    RelativeLayout layout = (RelativeLayout) shopRecyclerRecommendLive.getChildAt(i);
                    DivergeViewSecond divergeViewSecond = layout.findViewById(R.id.item_shop_recommend_live_diverge_view_second);
                    divergeViewSecond.startDiverges(n);
                }
            }
        }
    };

    @Override
    protected ShopPresenter createPresenter(Bundle savedInstanceState) {
        return shopPresenter;
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.fragment_shop;
    }

    /**
     * ????????????
     *
     * @param savedInstanceState
     */
    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopScrollView = (ObservableScrollView) findViewById(R.id.shop_scroll_view);
        shopRecyclerRecommendLive = (RecyclerView) findViewById(R.id.shop_recycler_recommend_live);
        shopGroupJoiningZone = (FrameLayout) findViewById(R.id.shop_group_joining_zone);
        shopImgTop = (ImageView) findViewById(R.id.shop_img_top);
        shopRlHeadView = (RelativeLayout) findViewById(R.id.shop_rl_head_view);
        libHeadRlSearch = (RelativeLayout) findViewById(R.id.lib_head_rl_search);
        libHeadTvSearch = (ADTextView) findViewById(R.id.lib_head_tv_search);
        shopTvHeadLogo = (TextView) findViewById(R.id.shop_tv_head_logo);
        shopRlFunction = (RelativeLayout) findViewById(R.id.shop_rl_function);
        shopRecyclerFunction = (RecyclerView) findViewById(R.id.shop_recycler_function);
        shopRlViewFunction = (RelativeLayout) findViewById(R.id.shop_rl_view_function);
        shopViewFunction = (View) findViewById(R.id.shop_view_function);
        shopMovableImg1 = (ImageView) findViewById(R.id.shop_movable_img1);
        shopMovableImg2 = (ImageView) findViewById(R.id.shop_movable_img2);
        shopMovableImg3 = (ImageView) findViewById(R.id.shop_movable_img3);
        shopTvRecommendLive = (TextView) findViewById(R.id.shop_tv_recommend_live);
        shopBannerLuckyDraw = (Banner) findViewById(R.id.shop_banner_lucky_draw);
        shopBannerLuckyDrawIndicator = (RoundLinesIndicator) findViewById(R.id.shop_banner_lucky_draw_indicator);
    }

    /**
     * ??????????????????
     *
     * @param savedInstanceState
     */
    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        libHeadRlSearch.setOnClickListener(this);//?????????
        libHeadTvSearch.setOnClickListener(this);//????????????
        shopImgTop.setOnClickListener(this);//??????
        shopMovableImg1.setOnClickListener(this);//???????????????
        shopMovableImg2.setOnClickListener(this);//????????????
        shopMovableImg3.setOnClickListener(this);//????????????
        shopTvRecommendLive.setOnClickListener(this);//????????????
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.lib_head_rl_search || id == R.id.lib_head_tv_search) {//?????????????????????
            //???????????????
            if (ClickUtils.isFastClick()) {
                return;
            }
            RouterHelper.toShopSearch(new SearchMerchandiseBean().setTag(TagParameteBean.normal)
                    .setEsGoodsName(adArrList.size() == 0 ?
                            null : adArrList.get((adCount - 1) % adArrList.size()))
            );
        } else if (id == R.id.shop_img_top) {//??????
            //???????????????
            if (ClickUtils.isFastClick()) {
                return;
            }
            shopScrollView.smoothScrollTo(0, 0);
        } else if (id == R.id.shop_movable_img1) {//???????????????
            if (!UserStore.isLogin()) {
                RouterHelper.toLogin();
                return;
            }
            if (!TextUtils.isEmpty(UserStore.getInviteCode())) {
                //???????????????
                if (ClickUtils.isFastClick()) {
                    return;
                }
                RouterHelper.toWebJs(WebUrls.inviUser
                        .replace("{inviteCode}", UserStore.getInviteCode())
                        .replace("{token}", UserStore.getToken()), true);
            } else {
                ToastView.showToast("????????????????????????????????????null");
            }
        } else if (id == R.id.shop_movable_img2) {//????????????
            RouterHelper.toShopOneCity();
        } else if (id == R.id.shop_movable_img3) {//????????????
            RouterHelper.toShopJoinGroup();
        } else if (id == R.id.shop_tv_recommend_live) {//????????????
            ChangeParms.sChangeFragment.changge(1);
        }
    }

    /**
     * ?????????
     *
     * @param savedInstanceState
     */
    @Override
    protected void initViews(Bundle savedInstanceState) {

        mPresenter.initDisplayData();//???????????????????????????ScrollView??????????????????????????????????????????????????????

        initBanner();

        mPresenter.getADSearch();//??????????????????

        mPresenter.getRecommendLive();//??????????????????

    }

    @Override
    public void onResume() {
        super.onResume();
        shopScrollView.smoothScrollTo(0, 0);
//        mPresenter.initDisplayData();//???????????????????????????ScrollView??????????????????????????????????????????????????????
    }

    /**
     * ???????????????????????????????????????????????????
     */
    public void initListener() {
        ViewTreeObserver treeObserver = shopRlFunction.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                shopRlFunction.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                functionHeight = shopRlFunction.getHeight() - 100;
                shopScrollView.setOnAnimationScrollListener(ShopFragment.this);
            }
        });
    }

    /**
     * ?????? ???????????????
     */
    public void initTopSearchLocation() {
        searchLayoutParams = (ViewGroup.MarginLayoutParams) libHeadRlSearch.getLayoutParams();
        titleLayoutParams = (ViewGroup.MarginLayoutParams) shopTvHeadLogo.getLayoutParams();
        functionParams = (ViewGroup.MarginLayoutParams) shopRlFunction.getLayoutParams();
        //???????????????????????????
        rlSearchMinTopMargin = HeadView.getStatusBarHeight(getActivity());
        //?????????????????????????????????
        rlSearchMaxTopMargin = HeadView.getStatusBarHeight(getActivity()) + 155;
        //?????????????????????????????????
        tvTitleMaxTopMargin = HeadView.getStatusBarHeight(getActivity()) + 35;
        titleLayoutParams.topMargin = (int) tvTitleMaxTopMargin;
        shopTvHeadLogo.setLayoutParams(titleLayoutParams);
        searchLayoutParams.topMargin = (int) rlSearchMaxTopMargin;
        libHeadRlSearch.setLayoutParams(searchLayoutParams);
        ViewTreeObserver vto2 = shopRlHeadView.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                shopRlHeadView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (shopRlHeadView.getHeight() == 0) {
                    functionParams.topMargin = 400;
                } else {
                    functionParams.topMargin = shopRlHeadView.getHeight();
                }
                // ????????????????????????????????????
                shopRlFunction.setLayoutParams(functionParams);
            }
        });
    }

    /**
     * ??????????????????????????? ?????????????????????
     *
     * @param scrollView
     * @param x
     * @param y
     * @param oldx
     * @param oldy
     * @param dy
     */
    @Override
    public void onAnimationScrollChanged(ObservableScrollView scrollView,
                                         int x, int y, int oldx, int oldy,
                                         float dy) {
        if (y <= 0) {
            //????????????????????????????????????
            shopRlHeadView
                    .setBackground(null);
            shopImgTop.setVisibility(View.GONE);
            shopTvHeadLogo.setVisibility(View.VISIBLE);
        } else if (y > 0 && y <= functionHeight) {
            //??????????????????banner??????????????????????????????????????????????????????????????????
            float scale = (float) y / functionHeight;
            int alpha = (int) (scale * 255);
            shopRlHeadView
                    .setBackgroundColor(Color.argb((int) alpha, 234, 85, 4));
        } else {
            //?????????banner????????????????????????
            shopRlHeadView
                    .setBackgroundColor(getResources().getColor(R.color.colorEA5504));
            if (UserStore.isLogin()) {
                shopImgTop.setVisibility(View.VISIBLE);
            }
            shopTvHeadLogo.setVisibility(View.GONE);
        }

        float searchLayoutNewTopMargin = rlSearchMaxTopMargin - dy;
        float titleNewTopMargin = (float) (tvTitleMaxTopMargin - dy * 0.5);
        if (searchLayoutNewTopMargin < rlSearchMinTopMargin) {
            searchLayoutNewTopMargin = rlSearchMinTopMargin;
        }
        float titleAlpha = 255 * titleNewTopMargin / tvTitleMaxTopMargin;
        if (titleAlpha < 0) {
            titleAlpha = 0;
        }
        shopTvHeadLogo.setTextColor(shopTvHeadLogo.getTextColors().withAlpha((int) titleAlpha));
        //?????????????????????LayoutParams ??????????????????MarginLayoutParams???????????????params???topMargin??????
        titleLayoutParams.topMargin = (int) titleNewTopMargin;
        shopTvHeadLogo.setLayoutParams(titleLayoutParams);
        searchLayoutParams.topMargin = (int) searchLayoutNewTopMargin;
        libHeadRlSearch.setLayoutParams(searchLayoutParams);
    }

    /**
     * ?????????
     */
    public void initFunction() {
        for (ArrayTools.shopFunction airlineTypeEnum : ArrayTools.shopFunction.values()) {
            if (airlineTypeEnum.isShow()) {
                functionArray.add(new ArrayItemBean()
                        .setId(airlineTypeEnum.getId())
                        .setName(airlineTypeEnum.getName())
                        .setIcon(airlineTypeEnum.getIcon())
                        .setVisibility(airlineTypeEnum.isShow()));
            }
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        shopRecyclerFunction.setLayoutManager(gridLayoutManager);
        FunctionAdapter functionAdapter = new FunctionAdapter(getActivity(),
                functionArray, R.layout.item_shop_function);
        functionAdapter.setOnCallback(this);
        shopRecyclerFunction.setAdapter(functionAdapter);
        shopRecyclerFunction.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //???????????????????????????0???????????????????????????
                int offX = recyclerView.computeHorizontalScrollOffset();
                //??????RecyclerView????????????
                int contentWidth = recyclerView.computeHorizontalScrollRange();
                //??????RecyclerView???????????????
                int superWidth = recyclerView.getWidth();
                int xInt = offX / (contentWidth - superWidth);
                int segX = xInt * (shopRlViewFunction.getWidth() - shopViewFunction.getWidth());
                RelativeLayout.LayoutParams layoutParams
                        = (RelativeLayout.LayoutParams) shopViewFunction.getLayoutParams();
                layoutParams.leftMargin = segX;
                shopViewFunction.setLayoutParams(layoutParams);
            }
        });
    }

    /**
     * ?????????  ????????????
     */
    private void initBanner() {
        shopBannerLuckyDraw.setAdapter(new MultipleTypesAdapter(getActivity(), DataBean.getShopDataVideo()))
                //???????????????????????????????????????????????????
                .setIndicator(shopBannerLuckyDrawIndicator, false)
                .setIndicatorSelectedWidth(BannerUtils.dp2px(15))
                //??????????????????
                .setBannerGalleryEffect(20, 145, 10)
                //????????????????????????
                .setLoopTime(10000)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object data, int position) {
                        if (position == 0) {//????????????
                            RouterHelper.toShopLuckyFight();
                        } else if (position == 1) {//????????????
                            RouterHelper.toYBInternational();
                        } else if (position == 2) {//????????????
                            RouterHelper.toShopOneCity();
                        }
                    }
                });
    }

    /**
     * ????????????????????????
     *
     * @param bean ????????????
     */
    @Override
    public void setADSearch(List<String> bean) {
        if (bean.size() != 0) {
            for (int i = 0; i < bean.size(); i++) {
                adArrList.add(bean.get(i));
            }
        }
        initADSearch();
    }

    /**
     * ????????????????????????
     */
    public void initADSearch() {
        if (adArrList.size() == 0) {
            for (int i = 1; i < 4; i++) {
                adArrList.add("????????????" + i);
            }
        }
        handler.postDelayed(runnable, 0);
    }

    /**
     * ?????????????????? ????????????
     *
     * @param bean ???????????????????????????
     */
    @Override
    public void setRecommendLive(List<ShopRecommendVideoBean> bean) {
        recommendLiveBean = bean;
        initRecommendLive();
    }

    /**
     * ????????????
     */
    public void initRecommendLive() {
        mListBitmap.add(((BitmapDrawable) ResourcesCompat.getDrawable(getResources(),
                R.mipmap.icon_shop_recommend_live_diverge_view_second1, null)).getBitmap());
        mListBitmap.add(((BitmapDrawable) ResourcesCompat.getDrawable(getResources(),
                R.mipmap.icon_shop_recommend_live_diverge_view_second2, null)).getBitmap());
        mListBitmap.add(((BitmapDrawable) ResourcesCompat.getDrawable(getResources(),
                R.mipmap.icon_shop_recommend_live_diverge_view_second3, null)).getBitmap());
        if (recommendLiveBean != null && recommendLiveBean.size() != 0) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
            shopRecyclerRecommendLive.setLayoutManager(gridLayoutManager);
            shopRecommendLiveAdapter = new ShopRecommendLiveAdapter(
                    getActivity(), recommendLiveBean, R.layout.item_shop_recommend_live);
            shopRecommendLiveAdapter.setOnCallback(this);
            shopRecyclerRecommendLive.setAdapter(shopRecommendLiveAdapter);
            new Thread(new MyThread()).start();
        } else {
            RelativeLayout.LayoutParams liveLayoutParams =
                    (RelativeLayout.LayoutParams) shopRecyclerRecommendLive.getLayoutParams();
            liveLayoutParams.height = 500;
            shopRecyclerRecommendLive.setLayoutParams(liveLayoutParams);
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
        if (id == R.id.item_shop_function_rl_root) {//?????? item????????????
            //???????????????
            if (ClickUtils.isFastClick()) {
                return;
            }
            mPresenter.initFunctionInfo(functionArray.get(postion).getId());
        } else if (id == R.id.item_shop_recommend_live_rl_root) {
            //???????????????
            if (ClickUtils.isFastClick()) {
                return;
            }
            mPresenter.getVodInfo(recommendLiveBean.get(postion).getBusinessId());
        } else {
            //???????????????
            if (ClickUtils.isFastClick()) {
                return;
            }
            ToastView.showToast(getActivity(), 0, getResources().getString(R.string.toast_view_in_development_msg));
        }
    }

    ArrayList<RecommendVideoBean.RowsBean> mVideoList = new ArrayList<>();

    @Override
    public void setVodInfo(RecommendVideoBean.RowsBean bean) {
        mVideoList.clear();
        if (bean != null) {
            mVideoList.add(bean);
            initVodInfo();
        } else {
            ToastView.showToast("????????????????????????????????????");
        }
    }

    @Override
    public void initVodInfo() {
        if (mVideoList != null && mVideoList.size() != 0) {
            RouterHelper.toShortVideoPlayActivity(mVideoList, 0, "external", 2);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            MyThread.pauseThread();
        } else {
            MyThread.resumeThread();
        }
    }

    /**
     * ???????????????
     */
    public void initGroupJoiningZone() {
        GroupJoiningZoneFragment groupJoiningZoneFragment = new GroupJoiningZoneFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.shop_group_joining_zone, groupJoiningZoneFragment);
        transaction.commit();
    }

    public static class MyThread implements Runnable {

        public static final Object lock = new Object();

        public static boolean pause = false;

        /**
         * ????????????????????????????????????
         */
        public static void pauseThread() {
            pause = true;
        }

        /**
         * ?????????????????????????????????????????????
         */
        public static void resumeThread() {
            pause = false;
            synchronized (lock) {
                lock.notifyAll();
            }
        }

        /**
         * ??????????????????????????????run??????????????????????????????????????????????????????????????????
         */
        void onPause() {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            while (true) {
                // ?????????????????????????????????
                while (pause) {
                    onPause();
                }
                try {
                    Thread.sleep(800);//????????????1??????????????????
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);//????????????
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Provider implements DivergeViewSecond.DivergeViewProvider {
        @Override
        public Bitmap getBitmap(Object obj) {
            return mListBitmap == null ? null : mListBitmap.get((int) obj);
        }
    }

}

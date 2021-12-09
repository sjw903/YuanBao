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
 * @Description: 商城首页
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/2/21 1:14 PM
 * @Modifier:
 * @Modify:
 */
public class ShopFragment extends MvpBaseFragmentImpl<ShopContract.View, ShopPresenter>
        implements ShopContract.View,//View层
        ObservableScrollView.OnAnimationScrollChangeListener,//自定义ScrollView动画
        OnCallRecyclerItem,//RecyclerView item点击事件
        View.OnClickListener {

    /**
     * 自定义ScrollView (滑动顶部变色)
     */
    ObservableScrollView shopScrollView;

    /**
     * 设置搜索框位置
     */
    private float rlSearchMinTopMargin, rlSearchMaxTopMargin, tvTitleMaxTopMargin;

    /**
     * 动态设置导航栏位置
     */
    private ViewGroup.MarginLayoutParams searchLayoutParams, titleLayoutParams, functionParams;

    /**
     * HeadView
     */
    RelativeLayout shopRlHeadView;

    /**
     * 搜索框
     */
    RelativeLayout libHeadRlSearch;

    /**
     * 搜索框推荐内容（上下滚动）
     */
    ADTextView libHeadTvSearch;

    /**
     * 搜索框上方标题
     */
    TextView shopTvHeadLogo;

    /**
     * 功能模块（一城一品   拼团   秒杀   元宝国际   话费充值  加油卡）
     */
    RelativeLayout shopRlFunction;

    /**
     * 功能显示 点击可以跳转相对于模块详情
     */
    RecyclerView shopRecyclerFunction;

    /**
     * 功能模块进度块（灰色）
     */
    RelativeLayout shopRlViewFunction;

    /**
     * 功能模块进度块（橙色）
     */
    View shopViewFunction;

    /**
     * 功能模块集合
     */
    List<ArrayItemBean> functionArray = new ArrayList<>();

    /**
     * 功能模块 高度
     */
    private int functionHeight;

    /**
     * 超值拼团区
     */
    FrameLayout shopGroupJoiningZone;

    /**
     * 会挑会选有本事 显示直播封面 点击可以进入观看直播
     */
    static RecyclerView shopRecyclerRecommendLive;

    /**
     * 会挑会选有本事适配器
     */
    static ShopRecommendLiveAdapter shopRecommendLiveAdapter;

    /**
     * 会挑会选有本事集合
     */
    static List<ShopRecommendVideoBean> recommendLiveBean;

    /**
     * 会挑会选动画图片集合
     */
    static List<Bitmap> mListBitmap = new ArrayList<Bitmap>();

    /**
     * 推荐搜索（上下滚动）下标
     */
    private int adCount = 0;

    /**
     * 推荐搜索（上下滚动）数据
     */
    private List<String> adArrList = new ArrayList<String>();

    /**
     * 置顶
     */
    ImageView shopImgTop;

    /**
     * 邀请新用户
     */
    ImageView shopMovableImg1;

    /**
     * 一城一品
     */
    ImageView shopMovableImg2;

    /**
     * 全名拼团
     */
    ImageView shopMovableImg3;

    /**
     * 商城Presenter
     */
    ShopPresenter shopPresenter = new ShopPresenter();

    /**
     * 轮播图
     */
    Banner shopBannerLuckyDraw;

    /**
     * 指示器
     */
    RoundLinesIndicator shopBannerLuckyDrawIndicator;

    /**
     * 更多频道
     */
    TextView shopTvRecommendLive;

    /**
     * 搜索推荐自动滚动定时器
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                /**
                 * 推荐搜索设置滚动时间 handler自带方法实现定时器
                 */
                handler.postDelayed(this, 3000);
                libHeadTvSearch.next();//自动显示下一个
                libHeadTvSearch.setText(adArrList.get(adCount % adArrList.size()));//搜索框显示内容
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
            //要做的事情
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
     * 绑定控件
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
     * 绑定点击事件
     *
     * @param savedInstanceState
     */
    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        libHeadRlSearch.setOnClickListener(this);//搜索框
        libHeadTvSearch.setOnClickListener(this);//搜索推荐
        shopImgTop.setOnClickListener(this);//置顶
        shopMovableImg1.setOnClickListener(this);//邀请新用户
        shopMovableImg2.setOnClickListener(this);//一城一品
        shopMovableImg3.setOnClickListener(this);//全名拼团
        shopTvRecommendLive.setOnClickListener(this);//更多频道
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.lib_head_rl_search || id == R.id.lib_head_tv_search) {//搜索框点击跳转
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            RouterHelper.toShopSearch(new SearchMerchandiseBean().setTag(TagParameteBean.normal)
                    .setEsGoodsName(adArrList.size() == 0 ?
                            null : adArrList.get((adCount - 1) % adArrList.size()))
            );
        } else if (id == R.id.shop_img_top) {//置顶
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            shopScrollView.smoothScrollTo(0, 0);
        } else if (id == R.id.shop_movable_img1) {//邀请新用户
            if (!UserStore.isLogin()) {
                RouterHelper.toLogin();
                return;
            }
            if (!TextUtils.isEmpty(UserStore.getInviteCode())) {
                //防暴力点击
                if (ClickUtils.isFastClick()) {
                    return;
                }
                RouterHelper.toWebJs(WebUrls.inviUser
                        .replace("{inviteCode}", UserStore.getInviteCode())
                        .replace("{token}", UserStore.getToken()), true);
            } else {
                ToastView.showToast("账号有问题？？？邀请码为null");
            }
        } else if (id == R.id.shop_movable_img2) {//一城一品
            RouterHelper.toShopOneCity();
        } else if (id == R.id.shop_movable_img3) {//全名拼团
            RouterHelper.toShopJoinGroup();
        } else if (id == R.id.shop_tv_recommend_live) {//更多频道
            ChangeParms.sChangeFragment.changge(1);
        }
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    @Override
    protected void initViews(Bundle savedInstanceState) {

        mPresenter.initDisplayData();//设置功能模块数据、ScrollView监听、超值拼团区、设置顶部搜索框位置

        initBanner();

        mPresenter.getADSearch();//获取推荐搜索

        mPresenter.getRecommendLive();//获取直播推荐

    }

    @Override
    public void onResume() {
        super.onResume();
        shopScrollView.smoothScrollTo(0, 0);
//        mPresenter.initDisplayData();//设置功能模块数据、ScrollView监听、超值拼团区、设置顶部搜索框位置
    }

    /**
     * 获取顶部轮播图高度后，设置滚动监听
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
     * 顶部 搜索框位置
     */
    public void initTopSearchLocation() {
        searchLayoutParams = (ViewGroup.MarginLayoutParams) libHeadRlSearch.getLayoutParams();
        titleLayoutParams = (ViewGroup.MarginLayoutParams) shopTvHeadLogo.getLayoutParams();
        functionParams = (ViewGroup.MarginLayoutParams) shopRlFunction.getLayoutParams();
        //布局关闭时顶部距离
        rlSearchMinTopMargin = HeadView.getStatusBarHeight(getActivity());
        //布局默认展开时顶部距离
        rlSearchMaxTopMargin = HeadView.getStatusBarHeight(getActivity()) + 155;
        //文字默认展开时顶部距离
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
                // 动态设置功能距离顶部高度
                shopRlFunction.setLayoutParams(functionParams);
            }
        });
    }

    /**
     * 搜索框背景颜色变化 搜索框动画变化
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
            //设置渐变的头部的背景颜色
            shopRlHeadView
                    .setBackground(null);
            shopImgTop.setVisibility(View.GONE);
            shopTvHeadLogo.setVisibility(View.VISIBLE);
        } else if (y > 0 && y <= functionHeight) {
            //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / functionHeight;
            int alpha = (int) (scale * 255);
            shopRlHeadView
                    .setBackgroundColor(Color.argb((int) alpha, 234, 85, 4));
        } else {
            //滑动到banner下面设置普通颜色
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
        //设置相关控件的LayoutParams 此处使用的是MarginLayoutParams，便于设置params的topMargin属性
        titleLayoutParams.topMargin = (int) titleNewTopMargin;
        shopTvHeadLogo.setLayoutParams(titleLayoutParams);
        searchLayoutParams.topMargin = (int) searchLayoutNewTopMargin;
        libHeadRlSearch.setLayoutParams(searchLayoutParams);
    }

    /**
     * 功能表
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
                //已经滚动的距离，为0时表示已处于顶部。
                int offX = recyclerView.computeHorizontalScrollOffset();
                //获取RecyclerView的总宽度
                int contentWidth = recyclerView.computeHorizontalScrollRange();
                //获取RecyclerView的显示宽度
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
     * 轮播图  画廊效果
     */
    private void initBanner() {
        shopBannerLuckyDraw.setAdapter(new MultipleTypesAdapter(getActivity(), DataBean.getShopDataVideo()))
                //在布局文件中使用指示器，这样更灵活
                .setIndicator(shopBannerLuckyDrawIndicator, false)
                .setIndicatorSelectedWidth(BannerUtils.dp2px(15))
                //添加画廊效果
                .setBannerGalleryEffect(20, 145, 10)
                //设置轮播间隔时间
                .setLoopTime(10000)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object data, int position) {
                        if (position == 0) {//全民拼团
                            RouterHelper.toShopLuckyFight();
                        } else if (position == 1) {//元宝国际
                            RouterHelper.toYBInternational();
                        } else if (position == 2) {//一城一品
                            RouterHelper.toShopOneCity();
                        }
                    }
                });
    }

    /**
     * 设置推荐搜索数据
     *
     * @param bean 推荐数据
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
     * 显示推荐搜索数据
     */
    public void initADSearch() {
        if (adArrList.size() == 0) {
            for (int i = 1; i < 4; i++) {
                adArrList.add("默认数据" + i);
            }
        }
        handler.postDelayed(runnable, 0);
    }

    /**
     * 设置会挑会选 直播列表
     *
     * @param bean 会挑会选有本事数据
     */
    @Override
    public void setRecommendLive(List<ShopRecommendVideoBean> bean) {
        recommendLiveBean = bean;
        initRecommendLive();
    }

    /**
     * 推荐直播
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
     * RecyclerView   item点击回调
     *
     * @param view
     * @param postion
     */
    @Override
    public void onCallItem(View view, int postion) {
        int id = view.getId();
        if (id == R.id.item_shop_function_rl_root) {//功能 item点击事件
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            mPresenter.initFunctionInfo(functionArray.get(postion).getId());
        } else if (id == R.id.item_shop_recommend_live_rl_root) {
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            mPresenter.getVodInfo(recommendLiveBean.get(postion).getBusinessId());
        } else {
            //防暴力点击
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
            ToastView.showToast("短视频出问题，请稍后再试");
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
     * 超值拼团区
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
         * 调用这个方法实现暂停线程
         */
        public static void pauseThread() {
            pause = true;
        }

        /**
         * 调用这个方法实现恢复线程的运行
         */
        public static void resumeThread() {
            pause = false;
            synchronized (lock) {
                lock.notifyAll();
            }
        }

        /**
         * 注意：这个方法只能在run方法里调用，不然会阻塞主线程，导致页面无响应
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
                // 让线程处于暂停等待状态
                while (pause) {
                    onPause();
                }
                try {
                    Thread.sleep(800);//线程暂停1秒，单位毫秒
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);//发送消息
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

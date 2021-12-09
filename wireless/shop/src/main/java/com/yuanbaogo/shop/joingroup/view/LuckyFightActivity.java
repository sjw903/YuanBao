package com.yuanbaogo.shop.joingroup.view;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.datacenter.local.SharePreferenceConfigImpl;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.joingroup.adapter.LuckyFightAdapter;
import com.yuanbaogo.shop.joingroup.adapter.LuckyFightTabAdapter;
import com.yuanbaogo.shop.joingroup.contract.LuckyFightContract;
import com.yuanbaogo.shop.joingroup.model.LuckDrawListBean;
import com.yuanbaogo.shop.joingroup.model.LuckFightParamsBean;
import com.yuanbaogo.shop.joingroup.model.LuckyFightBean;
import com.yuanbaogo.shop.joingroup.model.ShopInfoBean;
import com.yuanbaogo.shop.joingroup.presenter.LuckyFightPresenter;
import com.yuanbaogo.zui.dialog.PactDialogView;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;
import com.yuanbaogo.zui.dialog.model.PactBean;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: 幸运拼团
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/26/21 10:44 AM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.SHOP_LUCKY_FIGHT_ACTIVITY)
public class LuckyFightActivity extends MvpBaseActivityImpl<LuckyFightContract.View, LuckyFightPresenter>
        implements LuckyFightContract.View, View.OnClickListener, OnRefreshListener,
        OnLoadMoreListener, OnCallRecyclerItem, OnCallDialog {

    HeadView shopLuckyDrawHeadView;

    List<LuckDrawListBean> luckDrawListList;

    RecyclerView shopLuckyDrawRecyclerTab;

    LuckyFightTabAdapter luckyFightTabAdapter;

    public static Set<Integer> positionSet = new HashSet<>();

    String activityId;

    /**
     * 刷新
     */
    SmartRefreshLayout shopLuckyDrawSmart;

    /**
     * 商品列表
     */
    RecyclerView shopLuckyDrawRecycler;

    GridLayoutManager gridLayoutManager;

    LuckyFightAdapter luckyFightAdapter;

    /**
     * 商品实体类
     */
    LuckyFightBean luckyFightBean;

    /**
     * 没有数据
     */
    RelativeLayout shopLuckyDrawRlNoData;

    /**
     * 页数
     */
    int pageNum = 1;

    /**
     * 一页多少条
     */
    int pageSize = 10;

    /**
     * 总页数
     */
    int totalPage = 0;

    ImageView shopLuckyDrawImgTop;

    List<LuckyFightBean.LuckyFightRowsBean> luckyFightRowsBeans = new ArrayList<>();

    PactDialogView ruleDialogView;

    private SharePreferenceConfigImpl instance;

    /**
     * 今日开奖
     */
    LinearLayout shopLuckyDrawLlTabDrawToday;

    TextView shopLuckyDrawTvDrawToday;

    TextView shopLuckyDrawTvNoMiss;

    /**
     * 抢先预约
     */
    LinearLayout shopLuckyDrawLlTabRushReserve;

    TextView shopLuckyDrawTvRushReserve;

    TextView shopLuckyDrawTvAdvanceGoodSelect;

    /**
     * 日期标志 ： 0=今日开奖 1=抢先预约
     */
    int timeFlag = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    for (int i = 0; i < luckyFightRowsBeans.size(); i++) {
                        if (luckyFightRowsBeans.get(i).getCountdownTime() > 0) {
                            luckyFightRowsBeans.get(i).setCountdownTime(
                                    luckyFightRowsBeans.get(i).getCountdownTime() - 1000);
                        }
                    }
                    sendEmptyMessageDelayed(0, 1000);
                    luckyFightAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    handler.removeMessages(0);
                    break;
            }

        }
    };

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_lucky_fight;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopLuckyDrawHeadView = findViewById(R.id.shop_lucky_draw_head_view);
        shopLuckyDrawRecyclerTab = findViewById(R.id.shop_lucky_draw_recycler_tab);
        shopLuckyDrawSmart = findViewById(R.id.shop_lucky_draw_smart);
        shopLuckyDrawRecycler = findViewById(R.id.shop_lucky_draw_recycler);
        shopLuckyDrawRlNoData = findViewById(R.id.shop_lucky_draw_rl_no_data);
        shopLuckyDrawImgTop = findViewById(R.id.shop_lucky_draw_img_top);
        shopLuckyDrawLlTabDrawToday = findViewById(R.id.shop_lucky_draw_ll_tab_draw_today);
        shopLuckyDrawLlTabRushReserve = findViewById(R.id.shop_lucky_draw_ll_tab_rush_reserve);
        shopLuckyDrawTvDrawToday = findViewById(R.id.shop_lucky_draw_tv_draw_today);
        shopLuckyDrawTvNoMiss = findViewById(R.id.shop_lucky_draw_tv_no_miss);
        shopLuckyDrawTvRushReserve = findViewById(R.id.shop_lucky_draw_tv_rush_reserve);
        shopLuckyDrawTvAdvanceGoodSelect = findViewById(R.id.shop_lucky_draw_tv_advance_good_select);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        shopLuckyDrawHeadView.getLibHeadImgRight().setOnClickListener(this);//规则
        shopLuckyDrawHeadView.getLibHeadImgToleft().setOnClickListener(this);//分享
        shopLuckyDrawImgTop.setOnClickListener(this);
        shopLuckyDrawSmart.setOnRefreshListener(this);
        shopLuckyDrawSmart.setOnLoadMoreListener(this);
        shopLuckyDrawLlTabDrawToday.setOnClickListener(this);
        shopLuckyDrawLlTabRushReserve.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        //存储当前年月日 规则说明 需要每天都自动弹出一遍
        instance = SharePreferenceConfigImpl.getInstance(this);
        if (!DateUtils.getYMD().equals(instance.getString("rule_time", ""))) {
            initRuleDialog();
            instance.setString("rule_time", DateUtils.getYMD());
        }
        mPresenter.getLuckDrawTagList();
    }

    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_head_back_white)
                .setImgLeftIconBg(R.drawable.shape_gradient_bg_black_alpha_100)
                .setTvLeftTitle(true)
                .setTvLeftTitles("全民拼团")
                .setRlSearch(false)
                .setImgRight(true)
                .setImgRightIcon(R.mipmap.icon_head_rule_yuan)
                .setImgToleft(true)
                .setImgToleftIcon(R.mipmap.icon_head_share_yuan);
        shopLuckyDrawHeadView.setHead(headBean);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.lib_head_img_toleft) {//分享
            if (!UserStore.isLogin()) {
                RouterHelper.toLogin();
                return;
            }
            ShareBean shareBean = new ShareBean()
                    .setTitle("我要分享图片到好友");
            LuckyFightShareFragment generateMitoDialogView = new LuckyFightShareFragment(shareBean,
                    new ShopInfoBean()
                            .setGoodsName(null)
                            .setCoverImages(null)
                            .setLotteryTime(null)
                            .setGroupGoodsPrice(null)
                            .setImagesList(R.mipmap.icon_lucky_fight_list_share));
            generateMitoDialogView.show(getSupportFragmentManager(), "lucky_fight_share");
        } else if (id == R.id.shop_lucky_draw_img_top) {
            shopLuckyDrawRecycler.scrollToPosition(0);
        } else if (id == R.id.lib_head_img_right) {
            initRuleDialog();
        } else if (id == R.id.shop_lucky_draw_ll_tab_draw_today) {
            timeFlag = 0;
            initLlTab(1, 2);
            getLuckDrawList();
        } else if (id == R.id.shop_lucky_draw_ll_tab_rush_reserve) {
            timeFlag = 1;
            initLlTab(2, 1);
            getLuckDrawList();
        }
    }

    private void initLlTab(int mDrawToday, int mRushReserve) {
        shopLuckyDrawTvDrawToday.setTextColor(
                mDrawToday == 1 ? getResources().getColor(R.color.colorE03030) : getResources().getColor(R.color.color424242));
        shopLuckyDrawTvDrawToday.setTypeface(
                mDrawToday == 1 ? Typeface.defaultFromStyle(Typeface.BOLD) : Typeface.defaultFromStyle(Typeface.NORMAL));
        shopLuckyDrawTvNoMiss.setTextColor(
                mDrawToday == 1 ? getResources().getColor(R.color.colorFFFFFF) : getResources().getColor(R.color.color999999));
        shopLuckyDrawTvNoMiss.setBackground(
                mDrawToday == 1 ? getResources().getDrawable(R.drawable.shape_gradient_bg_but_50) : null);

        shopLuckyDrawTvRushReserve.setTextColor(
                mRushReserve == 1 ? getResources().getColor(R.color.colorE03030) : getResources().getColor(R.color.color424242));
        shopLuckyDrawTvRushReserve.setTypeface(
                mRushReserve == 1 ? Typeface.defaultFromStyle(Typeface.BOLD) : Typeface.defaultFromStyle(Typeface.NORMAL));
        shopLuckyDrawTvAdvanceGoodSelect.setTextColor(
                mRushReserve == 1 ? getResources().getColor(R.color.colorFFFFFF) : getResources().getColor(R.color.color999999));
        shopLuckyDrawTvAdvanceGoodSelect.setBackground(
                mRushReserve == 1 ? getResources().getDrawable(R.drawable.shape_gradient_bg_but_50) : null);
    }

    /**
     * 规则说明
     */
    private void initRuleDialog() {
        ruleDialogView = new PactDialogView();
        ruleDialogView.setCancelable(false);
        ruleDialogView.show(this.getSupportFragmentManager(), "rule");
        PactBean pactBean = new PactBean()
                .setTvTitle(true)
                .setTvTitles("规则说明")
                .setImgCancel(false)
                .setContentFileName("rule.txt")
                .setLine(true)
                .setCancel(false)
                .setDetermine(true)
                .setTvDetermines("我已知晓")
                .setType(1);
        ruleDialogView.setPact(pactBean);
        ruleDialogView.setOnCallDialog(this);
    }

    /**
     * 设置活动名称列表数据
     */
    @Override
    public void setLuckDrawTagList(List<LuckDrawListBean> bean) {
        luckDrawListList = bean;
        initLuckDrawTagList();
    }

    /**
     * 显示活动名称列表数据
     */
    @Override
    public void initLuckDrawTagList() {
        positionSet.clear();
        if (luckDrawListList != null && luckDrawListList.size() != 0) {
            activityId = luckDrawListList.get(0).getId();
            getLuckDrawList();
            positionSet.add(0);
            initRecyclerTab();
        }
    }

    /**
     * 设置tab
     */
    public void initRecyclerTab() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        shopLuckyDrawRecyclerTab.setLayoutManager(gridLayoutManager);
        luckyFightTabAdapter = new LuckyFightTabAdapter(this,
                luckDrawListList, R.layout.item_lucky_fight_tab);
        luckyFightTabAdapter.setOnCallBack(this);
        shopLuckyDrawRecyclerTab.setAdapter(luckyFightTabAdapter);
    }

    /**
     * 设置幸运大抽奖数据
     */
    @Override
    public void setLuckDrawList(LuckyFightBean bean) {
        loadFinish(true);
        luckyFightBean = bean;
        if (luckyFightBean.getTotal() % pageSize == 0) {
            totalPage = luckyFightBean.getTotal() / pageSize;
        } else {
            totalPage = (luckyFightBean.getTotal() / pageSize) + 1;
        }
        initLuckDrawList();
    }

    /**
     * 显示幸运大抽奖数据
     */
    @Override
    public void initLuckDrawList() {
        initRecycler();
    }

    private void initRecycler() {
        if (luckyFightBean != null && luckyFightBean.getRows() != null && luckyFightBean.getRows().size() != 0) {
            if (luckyFightAdapter == null) {
                luckyFightRowsBeans = luckyFightBean.getRows();
                gridLayoutManager = new GridLayoutManager(this, 2);
                shopLuckyDrawRecycler.setLayoutManager(gridLayoutManager);
                luckyFightAdapter = new LuckyFightAdapter(
                        this, luckyFightBean.getRows(), R.layout.item_shop_lucky_draw_list);
                luckyFightAdapter.setOnCallBack(this);
                shopLuckyDrawRecycler.setAdapter(luckyFightAdapter);
                handler.sendEmptyMessage(0);
            } else {
                if (pageNum == 1) {
                    luckyFightRowsBeans.clear();
                    luckyFightAdapter.setDataList(luckyFightBean.getRows());
                } else {
                    luckyFightAdapter.addAll(luckyFightBean.getRows());
                }
            }
        } else {
            if (luckyFightAdapter != null) {
                luckyFightAdapter.clear();
            }
        }
    }

    @Override
    public void onCallItem(View view, int postion) {
        if (view.getId() == R.id.item_shop_lucky_draw_rl) {
            RouterHelper.toLuckyFighInfo(luckyFightRowsBeans.get(postion).getId());
        } else if (view.getId() == R.id.shop_lucky_draw_tv_lowest_interval) {
            pageNum = 1;
            // 如果不是多选状态，则进入单选事件的业务逻辑
            if (!positionSet.contains(postion)) {
                // 选择不同的单位时取消之前选中的单位
                positionSet.clear();
            }
            addOrRemove(postion);
            timeFlag = 0;
            initLlTab(1, 2);
            getLuckDrawList();
        }
    }

    private void addOrRemove(int position) {
        if (positionSet.contains(position)) {
            // 如果包含，则撤销选择
            activityId = "1";
        } else {
            // 如果不包含，则添加
            positionSet.add(position);
            activityId = luckDrawListList.get(position).getId();
        }
        if (positionSet.size() == 0) {
            // 如果没有选中任何的item，则退出多选模式
            luckyFightTabAdapter.notifyDataSetChanged();
        } else {
            // 更新列表界面，否则无法显示已选的item
            luckyFightTabAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getLuckDrawList();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (pageNum == totalPage) {
            ToastView.showToast(this, "暂无更多数据");
            loadFinish(true);
            return;
        }
        pageNum++;
        getLuckDrawList();
    }

    /**
     * 请求接口
     */
    private void getLuckDrawList() {
        LuckFightParamsBean luckFightParameteBean = new LuckFightParamsBean().setActivityId(activityId)
                .setPageReq(new LuckFightParamsBean.LuckFightParameteReqBean().setPage(pageNum).setSize(pageSize))
                .setTimeFlag(timeFlag);
        mPresenter.getLuckDrawList(luckFightParameteBean);
    }

    protected void loadFinish(boolean successful) {
        shopLuckyDrawSmart.finishRefresh(successful);
        shopLuckyDrawSmart.finishLoadMore(successful);
    }

    @Override
    public void onCallDetermine() {
        if (ruleDialogView != null) {
            ruleDialogView.dismiss();
        }
    }
}
package com.yuanbaogo.shopcart.main.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.OrderNumBean;
import com.yuanbaogo.commonlib.utils.fragment.ChangeParms;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.shopcart.R;
import com.yuanbaogo.shopcart.main.adapter.ShopCartAdapter;
import com.yuanbaogo.shopcart.main.call.OnShoppingCartChangeListener;
import com.yuanbaogo.shopcart.main.call.ShoppingCartBiz;
import com.yuanbaogo.shopcart.main.call.ShoppingCartHttpBiz;
import com.yuanbaogo.shopcart.main.contract.ShopCartContract;
import com.yuanbaogo.shopcart.main.model.MoveFavoritesBean;
import com.yuanbaogo.shopcart.main.model.ShopCartBean;
import com.yuanbaogo.shopcart.main.model.ShoppingCartBean;
import com.yuanbaogo.shopcart.main.model.UpdateCartParametBean;
import com.yuanbaogo.shopcart.main.presenter.ShopCartPresenter;
import com.yuanbaogo.zui.dialog.ConfirmDialogView;
import com.yuanbaogo.zui.dialog.SortDialogView;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;
import com.yuanbaogo.zui.dialog.model.SkuBean;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 购物车
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/1/21 9:39 PM
 * @Modifier:
 * @Modify:
 */
public class ShopCartFragment extends MvpBaseFragmentImpl<ShopCartContract.View, ShopCartPresenter>
        implements
        OnCallDialog,
        ShopCartAdapter.OnGoodsParamCall,
        ShopCartContract.View,
        View.OnClickListener, SortDialogView.OnCallSort {

    /**
     * 购物车Presenter
     */
    ShopCartPresenter shopCartPresenter = new ShopCartPresenter();

    /**
     * 标题栏
     */
    HeadView shopCartHeadView;

    /**
     * 左上角标题
     */
    TextView libHeadTvLeftTitle;

    /**
     * 右上角管理
     */
    TextView libHeadTvRight;

    /**
     * 默认不编辑
     */
    public static boolean isEdit = false;

    /**
     * 商品列表
     */
    ExpandableListView expandableListView;

    /**
     * 购物车数据集合
     */
    List<ShoppingCartBean> mListGoods = new ArrayList<>();

    /**
     * 合计金额
     */
    String countMoney;

    /**
     * 标题 购物车(0)
     */
    String title;

    /**
     * 购物车适配器
     */
    ShopCartAdapter shopCartAdapter;

    /**
     * 暂无数据
     */
    RelativeLayout rlShoppingCartEmpty;

    /**
     * 底部订单结算栏
     */
    RelativeLayout shopCartRlBottomBar;

    /**
     * 全选
     */
    ImageView shopCartImgSelectAll;

    /**
     * 合计价格
     */
    TextView shopCartTvCountMoney;

    /**
     * 去结算按钮
     */
    TextView shopCartTvSettle;

    /**
     * 移入收藏夹
     */
    TextView shopCartTvMoveToFavorites;

    /**
     * 删除 确认弹窗
     */
    ConfirmDialogView confirmDialogView;

    /**
     * 1 外部购物车（MainActivity） 2 内部购物车（ShopCartActivity）
     */
    int type = 1;

    TextView shopCartTvLookVideo;

    TextView shopCartTvLuckyFight;

    /**
     * 存储已选中的商品
     */
    Map<String, Boolean> isSelectShop = new HashMap<>();

    public static ShopCartFragment getInstance(int type) {
        ShopCartFragment fragment = new ShopCartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected ShopCartPresenter createPresenter(Bundle savedInstanceState) {
        return shopCartPresenter;
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.fragment_shop_cart;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopCartHeadView = (HeadView) findViewById(R.id.shop_cart_head_view);
        libHeadTvLeftTitle = shopCartHeadView.getLibHeadTvLeftTitle();
        libHeadTvRight = shopCartHeadView.getLibHeadTvRight();
        expandableListView = (ExpandableListView) findViewById(R.id.shop_cart_list_view);
        rlShoppingCartEmpty = (RelativeLayout) findViewById(R.id.rlShoppingCartEmpty);
        shopCartRlBottomBar = (RelativeLayout) findViewById(R.id.shop_cart_rl_bottom_bar);
        shopCartImgSelectAll = (ImageView) findViewById(R.id.shop_cart_img_select_all);
        shopCartTvCountMoney = (TextView) findViewById(R.id.shop_cart_tv_count_money);
        shopCartTvSettle = (TextView) findViewById(R.id.shop_cart_tv_settle);
        shopCartTvMoveToFavorites = (TextView) findViewById(R.id.shop_cart_tv_move_to_favorites);
        shopCartTvLookVideo = (TextView) findViewById(R.id.shop_cart_tv_look_video);
        shopCartTvLuckyFight = (TextView) findViewById(R.id.shop_cart_tv_lucky_fight);
        type = getArguments().getInt("type");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHead();
        isEdit = false;
        initShopCartRecycler();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserStore.isLogin()) {//如果没有登录  到购物车界面不能请求接口
            mPresenter.getQueryCartGoods(UserStore.getYbCode(), false);
        } else {
            showEmpty(true);
        }
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        libHeadTvRight.setOnClickListener(this);
        shopCartTvSettle.setOnClickListener(this);
        shopCartTvMoveToFavorites.setOnClickListener(this);
        shopCartImgSelectAll.setOnClickListener(this);
        shopCartTvLookVideo.setOnClickListener(this);
        shopCartTvLuckyFight.setOnClickListener(this);
    }

    List<String> listID;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.lib_head_tv_right) {
            if (isEdit) {
                isEdit = false;
                libHeadTvRight.setText("管理");
                shopCartTvSettle.setText("去结算");
                if (mListGoods.size() != 0 && mListGoods.size() == 1) {
                    ShoppingCartBiz.checkItem(false, shopCartImgSelectAll);
                }
                shopCartTvCountMoney.setVisibility(View.VISIBLE);
                shopCartTvMoveToFavorites.setVisibility(View.GONE);
                initCountMoney(countMoney, title);
                shopCartAdapter.setSettleInfo1();
                shopCartAdapter.notifyDataSetChanged();
            } else {
                isEdit = true;
                libHeadTvRight.setText("完成");
                shopCartTvSettle.setText("删除");
                if (mListGoods.size() != 0 && mListGoods.size() == 1) {
                    ShoppingCartBiz.checkItem(mListGoods.get(0).isGroupSelected(), shopCartImgSelectAll);
                } else if (mListGoods.size() != 0 && mListGoods.size() == 2) {
                    ShoppingCartBiz.checkItem(mListGoods.get(0).isGroupSelected() && mListGoods.get(1).isGroupSelected(), shopCartImgSelectAll);
                }
                shopCartTvCountMoney.setVisibility(View.GONE);
                shopCartTvMoveToFavorites.setVisibility(View.VISIBLE);
                shopCartAdapter.notifyDataSetChanged();
            }
        } else if (id == R.id.shop_cart_tv_settle) {
            initSettle();
        } else if (id == R.id.shop_cart_tv_move_to_favorites) {
            if (ShoppingCartBiz.hasSelectedGoods(mListGoods)) {
                ToastView.showToast(mContext, "宝贝已成功移入收藏夹，您可以随时进入：个人中心-收藏夹查看宝贝的最新动态哦");
                initDetermine(2);
            } else {
                ToastView.showToast(mContext, "还没有选择宝贝哦！");
            }
        }
        if (id == R.id.shop_cart_img_select_all) {
            shopCartAdapter.isSelectAll = ShoppingCartBiz.selectAll(mListGoods, shopCartAdapter.isSelectAll, (ImageView) view);
            shopCartAdapter.setSettleInfo();
            shopCartAdapter.notifyDataSetChanged();
        } else if (id == R.id.shop_cart_tv_look_video) {//看看视频直播
            if (type == 2) {
                getActivity().finish();
            }
            ChangeParms.sChangeFragment.changge(1);
            RouterHelper.toMain();
        } else if (id == R.id.shop_cart_tv_lucky_fight) {//逛逛全民拼团
            RouterHelper.toShopLuckyFight();
        }
    }

    private void initHead() {
        HeadBean headBean = new HeadBean()
                .setRlTopNavigaBarBg(R.color.colorFFFFFF)
                .setRlSearch(false)
                .setImgLeft(type == 1 ? false : true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setImgRight(false)
                .setTvLeftTitle(true);
        shopCartHeadView.setHead(headBean);
        libHeadTvLeftTitle.setTextColor(getResources().getColor(R.color.color424242));
        libHeadTvLeftTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        libHeadTvLeftTitle.setText("购物车");
        libHeadTvRight.setVisibility(View.VISIBLE);
        libHeadTvRight.setTextColor(getResources().getColor(R.color.color424242));
        libHeadTvRight.setText("管理");
    }

    /**
     * 接口返回购物车数量
     */
    List<ShopCartBean> shopCartBeans;

    /**
     * 设置购物车数量
     *
     * @param bean
     */
    @Override
    public void setQueryCartGoods(List<ShopCartBean> bean) {
        if (shopCartBeans != null) {
            shopCartBeans.clear();
        }
        shopCartBeans = bean;
        initQueryCartGoods();
    }

    /**
     * 设置购物车数据到 自定义Model 里
     */
    @Override
    public void initQueryCartGoods() {
        requestShoppingCartList();
    }

    /**
     * 获取购物车列表的数据
     */
    private void requestShoppingCartList() {
        if (shopCartBeans != null && shopCartBeans.size() != 0) {
            mListGoods.clear();
            goodsCountS = shopCartBeans.size();
            mListGoods = ShoppingCartHttpBiz.handleOrderList(isSelectShop, shopCartBeans);
            isSelectShop.clear();
            updateListView();
        } else {
            showEmpty(true);
        }
    }

    int goodsCountS = 0;

    /**
     * 更新页面
     */
    private void updateListView() {
        shopCartAdapter.setList(mListGoods);
        shopCartAdapter.notifyDataSetChanged();
        expandAllGroup();
    }

    /**
     * 展开所有组
     */
    private void expandAllGroup() {
        for (int i = 0; i < mListGoods.size(); i++) {
            expandableListView.expandGroup(i);
        }
    }

    /**
     * 购物车列表
     */
    private void initShopCartRecycler() {
        shopCartAdapter = new ShopCartAdapter(getActivity());
        shopCartAdapter.setOnGoodsParamCall(this);
        expandableListView.setAdapter(shopCartAdapter);
        shopCartAdapter.setOnShoppingCartChangeListener(new OnShoppingCartChangeListener() {

            public void onDataChange(String selectCount, String selectMoney, String num) {
                //查询购物车商品总数量
                int goodsCount = Integer.parseInt(num);
                if (goodsCount == 0) {
                    showEmpty(true);
                } else {
                    showEmpty(false);
                }
                //合计
                countMoney = String.format(getResources().getString(R.string.count_money), selectCount, String.valueOf((Long.parseLong(selectMoney) / 100)));
                //标题
                if (goodsCount == 0) {
                    title = String.format(getResources().getString(R.string.shop_title), 0 + "");
                } else {
                    title = String.format(getResources().getString(R.string.shop_title), goodsCountS - idList.size() + "");
                }
                initCountMoney(countMoney, title);
            }

            public void onSelectItem(boolean isSelectedAll) {
                //全选
                ShoppingCartBiz.checkItem(isSelectedAll, shopCartImgSelectAll);
            }

        });
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
    }

    /**
     * 是否有数据
     *
     * @param isEmpty
     */
    public void showEmpty(boolean isEmpty) {
        if (isEmpty) {
            expandableListView.setVisibility(View.GONE);
            rlShoppingCartEmpty.setVisibility(View.VISIBLE);
            shopCartRlBottomBar.setVisibility(View.GONE);
            libHeadTvRight.setVisibility(View.GONE);
            libHeadTvLeftTitle.setText("购物车");
        } else {
            expandableListView.setVisibility(View.VISIBLE);
            rlShoppingCartEmpty.setVisibility(View.GONE);
            shopCartRlBottomBar.setVisibility(View.VISIBLE);
            libHeadTvRight.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置textview 合计 标题
     *
     * @param countMoney 金额
     * @param title      标题
     */
    private void initCountMoney(String countMoney, String title) {
        if (TextUtils.isEmpty(countMoney)) {
            return;
        }
        Spannable string = new SpannableString(countMoney);
        // 已选0件
        string.setSpan(new AbsoluteSizeSpan(40), 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        string.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.colorAAAAAA)),
                0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 合计
        string.setSpan(new AbsoluteSizeSpan(40), 6, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        string.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.color424242)),
                6, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // ￥100.00
        string.setSpan(new AbsoluteSizeSpan(30), 8, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        string.setSpan(new AbsoluteSizeSpan(60), 10, string.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        string.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.colorEA5504)),
                8, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 显示
        shopCartTvCountMoney.setText(string);
        libHeadTvLeftTitle.setText(title);
    }

    ArrayList<String> skuIdList = new ArrayList<>();

    ArrayList<OrderNumBean> numList = new ArrayList<>();

    /**
     * 结算 / 删除
     */
    private void initSettle() {
        skuIdList.clear();
        numList.clear();
        isSelectShop.clear();
        if (ShoppingCartBiz.hasSelectedGoods(mListGoods)) {
            if (isEdit) {//删除
                initDelete();
            } else {//去结算
                listID = new ArrayList<>();
                for (int i = 0; i < mListGoods.size(); i++) {
                    for (int j = 0; j < mListGoods.get(i).getGoods().size(); j++) {
                        for (int p = 0; p < mListGoods.get(i).getGoods().get(j).getPrefectures().size(); p++) {
                            boolean isSelectd = mListGoods.get(i).getGoods().get(j).getPrefectures().get(p).isChildSelected();
                            if (isSelectd) {
                                isSelectShop.put(mListGoods.get(i).getGoods().get(j).getPrefectures().get(p).getSpuId(), isSelectd);
                                listID.add(i + "");
                                OrderNumBean orderNumBean = new OrderNumBean();
                                skuIdList.add(mListGoods.get(i).getGoods().get(j).getPrefectures().get(p).getSkuId());
                                ShoppingCartBean.Goods.Prefecture prefecture = mListGoods.get(i).getGoods().get(j).getPrefectures().get(p);
                                numList.add(orderNumBean.setAreaId(prefecture.getAreaId())
                                        .setCreateBy(UserStore.getYbCode())
                                        .setCreateGoodsName(prefecture.getCreateGoodsName())
                                        .setCreateGoodsPrice(prefecture.getCreateGoodsPrice())
                                        .setGoodsCount(prefecture.getGoodsCount())
                                        .setGoodsImgUrl(prefecture.getGoodsImgUrl())
                                        .setGoodsType(prefecture.getGoodsType())
                                        .setSkuName(prefecture.getSkuName())
                                        .setSkuId(prefecture.getSkuId())
                                        .setSkuName(prefecture.getSkuName())
                                );
                            }
                        }
                    }
                }
                if (listID.size() != 0) {
                    List<String> myList = listID.stream().distinct().collect(Collectors.toList());
                    if (myList.size() != 1) {
                        ToastView.showToast(mContext, "拼团专区享受特别优惠，不可与其他商品同时结算");
                    } else {
                        RouterHelper.toConfirmOrder(2, skuIdList, numList, null, null);
                    }
                }
            }
        } else {
            ToastView.showToast(mContext, "还没有选择宝贝哦！");
        }
    }

    /**
     * 删除
     */
    private void initDelete() {
        String[] infos = ShoppingCartBiz.getShoppingCount(mListGoods);
        confirmDialogView = new ConfirmDialogView("我再想想",
                String.format(getResources().getString(R.string.count_delete), infos[0]));
        confirmDialogView.show(getActivity().getSupportFragmentManager(), "shop_cart_delete");
        confirmDialogView.setOnCallDialog(this);
    }

    @Override
    public void onCallDetermine() {
        initDetermine(1);
    }

    List<String> idList = new ArrayList<>();

    List<MoveFavoritesBean> shopCartId = new ArrayList<>();

    /**
     * 刷新购物车界面
     *
     * @param type 1 删除  2 移入收藏夹
     */
    private void initDetermine(int type) {
        for (int i = 0; i < mListGoods.size(); i++) {
            for (int j = 0; j < mListGoods.get(i).getGoods().size(); j++) {
                for (int p = 0; p < mListGoods.get(i).getGoods().get(j).getPrefectures().size(); p++) {
                    if (mListGoods.get(i).getGoods().get(j).getPrefectures().get(p).isChildSelected()) {
                        String id = mListGoods.get(i).getGoods().get(j).getPrefectures().get(p).getId();
                        idList.add(id);
                        MoveFavoritesBean moveFavoritesBean = new MoveFavoritesBean().setShopCartId(id);
                        shopCartId.add(moveFavoritesBean);
                    }
                }
            }
        }
        if (type == 1) {
            mPresenter.getDeleteGoods(idList);
        } else if (type == 2) {
            mPresenter.getMoveFavorites(shopCartId);
        }
    }

    /**
     * 删除整租
     */
    public void delGoods() {
        if (shopCartAdapter.isSelectAll) {
            mListGoods.clear();
        } else {
            for (int i = 0; i < mListGoods.size(); i++) {
                if (mListGoods.get(i).isGroupSelected()) {
                    mListGoods.remove(i);
                    break;
                }
                for (int j = 0; j < mListGoods.get(i).getGoods().size(); j++) {
                    for (int p = 0; p < mListGoods.get(i).getGoods().get(j).getPrefectures().size(); p++) {
                        if (mListGoods.get(i).getGoods().get(j).getPrefectures().get(p).isChildSelected()) {
                            mListGoods.get(i).getGoods().get(j).getPrefectures().remove(p);
                        }
                    }
                }
            }
        }
    }

    String mSkuID;
    String mShoppingCartID;
    String mSkuName;
    int mGoodsCount;
    /**
     * 更新 1 增加商品数量  2 减少商品数量  3 修改商品规格
     */
    int updateType = 1;

    /**
     * 规格
     *
     * @param view
     * @param bean
     */
    @Override
    public void onGoodsParam(View view, ShoppingCartBean.Goods.Prefecture bean) {
        viewUpdateCart = view;
        updateType = 3;
        mSkuID = bean.getSkuId();
        mShoppingCartID = bean.getId();
        mGoodsImgUrl = bean.getGoodsImgUrl();
        mUpdateGoodsPrice = bean.getUpdateGoodsPrice();
        mSkuName = bean.getSkuName();
        mGoodsCount = bean.getGoodsCount();
        mPresenter.getSku(bean.getSpuId());
    }

    SkuBean skuBean;
    String mGoodsImgUrl;
    Long mUpdateGoodsPrice;

    @Override
    public void setSku(SkuBean bean) {
        skuBean = bean;
        initSku();
    }

    @Override
    public void initSku() {
        skuBean.setImgUrl(mGoodsImgUrl)
                .setPrice(mUpdateGoodsPrice)
                .setSkuName(mSkuName)
                .setGoodsCount(mGoodsCount);
        if (sortDialogView != null) {
            sortDialogView.setBean2(skuBean);
            getChildFragmentManager().beginTransaction().remove(sortDialogView).commit();
        } else {
            sortDialogView = new SortDialogView(3, skuBean, null, null);
        }
        sortDialogView.setOnCallSort(this);
        sortDialogView.show(getChildFragmentManager(), "shop_cart");
        mPresenter.getStock(mSkuID);
        mGoodsImgUrl = "";
        mUpdateGoodsPrice = 0L;
    }

    @Override
    public void setStock(String bean) {
        skuBean.setStock(Integer.parseInt(bean));
        initStock();
    }

    SortDialogView sortDialogView;

    @Override
    public void initStock() {
        if (sortDialogView != null) {
            sortDialogView.setBean(skuBean, true);
        }
    }

    @Override
    public void setDeleteGoods(String bean) {
        if (confirmDialogView != null) {
            confirmDialogView.dismiss();
        }
        initDeleteGoods();
    }

    @Override
    public void initDeleteGoods() {
        delGoods();
        shopCartAdapter.setSettleInfo();
        shopCartAdapter.notifyDataSetChanged();
    }

    /**
     * 增加购物车数量
     *
     * @param view
     * @param bean
     */
    @Override
    public void onGoodsAdd(View view, ShoppingCartBean.Goods.Prefecture bean) {
        updateType = 1;
        viewUpdateCart = view;
        mPresenter.getUpdateCartGoodsCount(new UpdateCartParametBean()
                .setYbCode(UserStore.getYbCode())
                .setId(bean.getId())
                .setSkuId(bean.getSkuId())
                .setGoodsCount(bean.getGoodsCount() + 1)
                .setSkuName(bean.getSkuName())
                .setSpuId(bean.getSpuId())
                .setCreateGoodsPrice(bean.getCreateGoodsPrice()));
    }

    /**
     * 减少购物车数量
     *
     * @param view
     * @param bean
     */
    @Override
    public void onGoodsReduce(View view, ShoppingCartBean.Goods.Prefecture bean) {
        updateType = 2;
        viewUpdateCart = view;
        mPresenter.getUpdateCartGoodsCount(new UpdateCartParametBean()
                .setYbCode(UserStore.getYbCode())
                .setId(bean.getId())
                .setSkuId(bean.getSkuId())
                .setGoodsCount(bean.getGoodsCount() - 1)
                .setSkuName(bean.getSkuName())
                .setSpuId(bean.getSpuId())
                .setCreateGoodsPrice(bean.getCreateGoodsPrice()));
    }

    /**
     * 跳转商品详情页面
     *
     * @param view
     * @param bean
     */
    @Override
    public void onGoodsItem(View view, ShoppingCartBean.Goods.Prefecture bean) {
        //防暴力点击
        if (ClickUtils.isFastClick()) {
            return;
        }
        isSelectShop.clear();
        for (int i = 0; i < mListGoods.size(); i++) {
            for (int j = 0; j < mListGoods.get(i).getGoods().size(); j++) {
                for (int p = 0; p < mListGoods.get(i).getGoods().get(j).getPrefectures().size(); p++) {
                    boolean isSelectd = mListGoods.get(i).getGoods().get(j).getPrefectures().get(p).isChildSelected();
                    if (isSelectd) {
                        isSelectShop.put(mListGoods.get(i).getGoods().get(j).getPrefectures().get(p).getSpuId(), isSelectd);
                    }
                }
            }
        }
        RouterHelper.toShopProductDetails(bean.getSpuId(), bean.getGoodsType(), null, null, true);
    }

    ShopCartBean shopCartBean;

    View viewUpdateCart;

    @Override
    public void setUpdateCartGoodsCount(ShopCartBean bean) {
        shopCartBean = bean;
        initUpdateCartGoodsCount(true);
    }

    @Override
    public void initUpdateCartGoodsCount(boolean isThrough) {
        if (isThrough) {
            if (shopCartBean != null) {
                if (updateType == 3) {
                    sortDialogView.dismiss();
                    mPresenter.getQueryCartGoods(UserStore.getYbCode(), false);
                } else if (updateType == 1) {
                    ShoppingCartBiz.addOrReduceGoodsNum(mContext,
                            true,
                            (ShoppingCartBean.Goods.Prefecture) viewUpdateCart.getTag(),
                            ((TextView) (((View) (viewUpdateCart.getParent())).findViewById(R.id.item_shop_cart_child_tv_add))),
                            ((TextView) (((View) (viewUpdateCart.getParent())).findViewById(R.id.item_shop_cart_child_tv_num))),
                            ((TextView) (((View) (viewUpdateCart.getParent())).findViewById(R.id.item_shop_cart_child_tv_reduce))));
                    shopCartAdapter.setSettleInfo();
                    shopCartBean = null;
                } else if (updateType == 2) {
                    ShoppingCartBiz.addOrReduceGoodsNum(mContext, false,
                            (ShoppingCartBean.Goods.Prefecture) viewUpdateCart.getTag(),
                            ((TextView) (((View) (viewUpdateCart.getParent())).findViewById(R.id.item_shop_cart_child_tv_add))),
                            ((TextView) (((View) (viewUpdateCart.getParent())).findViewById(R.id.item_shop_cart_child_tv_num))),
                            ((TextView) (((View) (viewUpdateCart.getParent())).findViewById(R.id.item_shop_cart_child_tv_reduce))));
                    shopCartAdapter.setSettleInfo();
                    shopCartBean = null;
                }
            } else {
                if (updateType == 3) {
                    sortDialogView.dismiss();
                    mPresenter.getQueryCartGoods(UserStore.getYbCode(), false);
                }
            }
        } else {
            if (updateType == 1) {
                if (!isThrough) {
                    ToastView.showToast("受不了了，宝贝数量不能再加了哦");
                }
            } else if (updateType == 2) {
                if (!isThrough) {
                    ToastView.showToast("受不了了，宝贝数量不能再减了哦");
                }
            }
        }
    }

    @Override
    public void onCallSort(String skuId) {
        mPresenter.getStock(skuId);
    }

    @Override
    public void onCallAddShoppingCart(OrderNumBean bean) {

    }

    @Override
    public void onCallModifySku(OrderNumBean bean) {
        mPresenter.getUpdateCartGoodsCount(new UpdateCartParametBean()
                .setYbCode(UserStore.getYbCode())
                .setId(mShoppingCartID)
                .setSkuId(bean.getSkuId())
                .setGoodsCount(bean.getGoodsCount())
                .setSkuName(bean.getSkuName())
                .setSpuId(bean.getSpuId())
                .setCreateGoodsPrice(bean.getCreateGoodsPrice()));
    }
}

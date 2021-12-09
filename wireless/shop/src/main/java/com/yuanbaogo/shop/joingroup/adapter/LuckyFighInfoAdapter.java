package com.yuanbaogo.shop.joingroup.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.http.SslError;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.commonlib.utils.array.ArrayTools;
import com.yuanbaogo.datacenter.constant.WebUrls;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.joingroup.model.LuckyFighInfoBean;
import com.yuanbaogo.shop.productdetails.adapter.ShopProductDetailsCommentAdapter;
import com.yuanbaogo.shop.productdetails.adapter.ShopProductDetailsLabelAdapter;
import com.yuanbaogo.shop.productdetails.adapter.ShopProductDetailsParameterAdapter;
import com.yuanbaogo.shop.productdetails.adapter.ShopProductDetailsRecommendAdapter;
import com.yuanbaogo.shop.productdetails.model.CommentBean;
import com.yuanbaogo.shop.productdetails.model.DetailsModelBean;
import com.yuanbaogo.shop.productdetails.model.ProductParametersBean;
import com.yuanbaogo.shop.publics.model.RecommendBean;
import com.yuanbaogo.zui.countdown.CountdownView;
import com.yuanbaogo.zui.progress.GradientProgressBar;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.zui.search.view.SearchLayout;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.PictureParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/5/21 5:01 PM
 * @Modifier:
 * @Modify:
 */
public class LuckyFighInfoAdapter extends BaseRecycleAdapter<DetailsModelBean>
        implements View.OnClickListener, CountdownView.OnCallCountdown, OnCallRecyclerItem, ShopProductDetailsCommentAdapter.OnCallCommentImg {

    Context context;

    private int layoutID;

    private int height = 0;

    /**
     * item 回调
     */
    private OnCallRecyclerItem onCallBack;

    /**
     * 查看全部评论
     */
    private OnCallComment onCallComment;

    /**
     * 查看相关推荐
     */
    private OnCallRecommendAll onCallRecommendAll;

    /**
     * 倒计时结束 刷新页面
     */
    private OnCallCountdownEnd onCallCountdownEnd;

    /**
     * 商品信息Model
     */
    LuckyFighInfoBean luckyFighInfoBean;

    /**
     * 相关推荐Model
     */
    List<RecommendBean> recommendBeanList;

    /**
     * 评论Model
     */
    CommentBean commentListBeans;

    private OnRuleDescription onRuleDescription;

    public void setOnRuleDescription(OnRuleDescription onRuleDescription) {
        this.onRuleDescription = onRuleDescription;
    }

    public void setProductDetailsBean(LuckyFighInfoBean luckyFighInfoBean) {
        this.luckyFighInfoBean = luckyFighInfoBean;
    }

    public void setRecommendBeanList(List<RecommendBean> recommendBeanList) {
        this.recommendBeanList = recommendBeanList;
    }

    public void setCommentListBeans(CommentBean commentListBeans) {
        this.commentListBeans = commentListBeans;
    }

    public void setOnCallCountdownEnd(OnCallCountdownEnd onCallCountdownEnd) {
        this.onCallCountdownEnd = onCallCountdownEnd;
    }

    public void setOnCallRecommendAll(OnCallRecommendAll onCallRecommendAll) {
        this.onCallRecommendAll = onCallRecommendAll;
    }

    public void setOnCallComment(OnCallComment onCallComment) {
        this.onCallComment = onCallComment;
    }

    public void setOnCallBack(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public LuckyFighInfoAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        int type = mDataList.get(position).getType();
        if (type == 1) {//商品信息
            layoutID = R.layout.item_shop_lucky_figh_info_rl_info;
            return 1001;
        } else if (type == 2) {//商品评论
            layoutID = R.layout.item_shop_product_details_rl_comment;
            return 1002;
        } else if (type == 3) {//商品参数
            layoutID = R.layout.item_shop_product_details_rl_parameter;
            return 1003;
        } else if (type == 4) {//商品详情
            layoutID = R.layout.item_shop_product_details_rl_recommend;
            return 1004;
        } else if (type == 5) {//商品详情
            layoutID = R.layout.item_shop_product_details_rl_details;
            return 1005;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getLayoutId() {
        return layoutID;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == 1001) {//商品信息
            final RelativeLayout item = holder.getView(R.id.item);
            initInfo(holder);
            getMeasureHeight(item, type);
            item.setOnClickListener(this);
            item.setTag(position);
        }
        if (type == 1002) {//商品评论
            final RelativeLayout item = holder.getView(R.id.item);
            initComment(holder);
            getMeasureHeight(item, type);
            item.setOnClickListener(this);
            item.setTag(position);
        }
        if (type == 1003) {//商品参数
            final LinearLayout item = holder.getView(R.id.item);
            initParameter(holder);
            getMeasureHeight(item, type);
            item.setOnClickListener(this);
            item.setTag(position);
        }
        if (type == 1004) {//商品推荐
            final LinearLayout item = holder.getView(R.id.item);
            initRecommend(holder);
            getMeasureHeight(item, type);
            item.setOnClickListener(this);
            item.setTag(position);
        }
        if (type == 1005) {//商品详情
            final RelativeLayout item = holder.getView(R.id.item);
            initDetails(holder, type, position);
            getMeasureHeight(item, type);
            item.setOnClickListener(this);
            item.setTag(position);
        }
    }

    /**
     * 获取每个item的高度
     *
     * @param view item的跟布局
     * @param type 用于判断是那个item的高度
     */
    public void getMeasureHeight(final View view, final int type) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (listener != null) {
                    if (type == 1002 || type == 1003 || type == 1004 || type == 1005) {
                        if (height != 0) {
                            height = view.getHeight();
                            listener.setOnItemHeightListener(height, type);
                        } else {
                            height = view.getHeight();
                        }
                    } else {
                        listener.setOnItemHeightListener(view.getHeight(), type);
                    }

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }

    public interface OnItemHeightListener {
        void setOnItemHeightListener(int height, int type);
    }

    private OnItemHeightListener listener;

    public void setListener(OnItemHeightListener listener) {
        this.listener = listener;
    }

    /**
     * 商品
     */
    LinearLayout shopLuckyDrawInfoLlCount;

    RelativeLayout shopLuckyDrawInfoRlCount;

    RelativeLayout shopLuckyDrawInfoRlProgressBar;

    TextView shopLuckyDrawInfoTvNumberWinners;

    public CountdownView itemLuckyDrawInfoCountdownView;

    TextView shopLuckyDrawInfoTvHint;

    TextView shopLuckyDrawInfoTvPrice;

    /**
     * 商品信息
     *
     * @param holder
     */
    private void initInfo(BaseViewHolder holder) {
        if (luckyFighInfoBean != null) {
            TextView shopProductDetailsTvTitle = holder.getView(R.id.shop_lucky_draw_info_tv_title);
            shopProductDetailsTvTitle.setText(luckyFighInfoBean.getGoodsName());

            shopLuckyDrawInfoLlCount = holder.getView(R.id.shop_lucky_draw_info_ll_count);
            shopLuckyDrawInfoRlCount = holder.getView(R.id.shop_lucky_draw_info_rl_count);
            shopLuckyDrawInfoRlProgressBar = holder.getView(R.id.shop_lucky_draw_info_rl_progress_bar);
            shopLuckyDrawInfoTvNumberWinners = holder.getView(R.id.shop_lucky_draw_info_tv_number_winners);
            shopLuckyDrawInfoTvHint = holder.getView(R.id.shop_lucky_draw_info_tv_hint);
            shopLuckyDrawInfoTvPrice = holder.getView(R.id.shop_lucky_draw_info_tv_price);
            TextView itemLuckyDrawInfoTvNumber = holder.getView(R.id.item_lucky_draw_info_tv_number);
            TextView shopLuckyDrawInfoTvTime = holder.getView(R.id.shop_lucky_draw_info_tv_time);

            GradientProgressBar itemLuckyDrawInfoProgressBar = holder.getView(R.id.item_lucky_draw_info_progress_bar);
            itemLuckyDrawInfoProgressBar.setBgColor(R.color.colorF8CCB3);
            itemLuckyDrawInfoProgressBar.setMaxCount(luckyFighInfoBean.getLimitNumber());
            itemLuckyDrawInfoProgressBar.setCurrentCount(luckyFighInfoBean.getCurrentNumber());

            itemLuckyDrawInfoCountdownView = holder.getView(R.id.item_lucky_draw_info_countdown_view);
            itemLuckyDrawInfoCountdownView.setOnCallCountdown(this);
            itemLuckyDrawInfoCountdownView.startCountDown((int) (luckyFighInfoBean.getCountdownTime() / 1000));

            shopLuckyDrawInfoTvPrice.setText("¥ " + luckyFighInfoBean.getGroupGoodsPrice() / 100);
            itemLuckyDrawInfoTvNumber.setText(luckyFighInfoBean.getCurrentNumber() + "/" + luckyFighInfoBean.getLimitNumber());
            shopLuckyDrawInfoTvTime.setText("抽奖时间：" + DateUtils.getDateDayHourMinute(luckyFighInfoBean.getLotteryTime()));

            TextView shopLuckyDrawInfoTvReserve = holder.getView(R.id.shop_lucky_draw_info_tv_reserve);
            TextView shopLuckyDrawInfoTvLottery = holder.getView(R.id.shop_lucky_draw_info_tv_lottery);
            TextView shopLuckyDrawInfoTvShip = holder.getView(R.id.shop_lucky_draw_info_tv_ship);
            ProgressBar shopLuckyDrawInfoProgressBar = holder.getView(R.id.shop_lucky_draw_info_progress_bar);
            TextView shopLuckyDrawInfoTvNumberWinners = holder.getView(R.id.shop_lucky_draw_info_tv_number_winners);

            ImageView shopLuckyDrawInfoImgFull = holder.getView(R.id.shop_lucky_draw_info_img_full);
            if (luckyFighInfoBean.getCurrentNumber() == luckyFighInfoBean.getLimitNumber()) {
                shopLuckyDrawInfoImgFull.setVisibility(View.VISIBLE);
            } else {
                shopLuckyDrawInfoImgFull.setVisibility(View.GONE);
            }

            shopLuckyDrawInfoTvNumberWinners.setText(luckyFighInfoBean.getNumberOfWinners() + "人已中奖");

            if (luckyFighInfoBean.getCountdownTime() > 0) {
                if (luckyFighInfoBean.getActiivitiesStatus() == 1) {//未开始
                    shopLuckyDrawInfoTvReserve.setBackground(mContext.getResources().getDrawable(R.drawable.shape_gradient_bg_but_login_50));
                    shopLuckyDrawInfoProgressBar.setProgress(0);
                } else if (luckyFighInfoBean.getActiivitiesStatus() == 2) {//进行中
                    shopLuckyDrawInfoTvReserve.setBackground(mContext.getResources().getDrawable(R.drawable.shape_gradient_bg_but_login_50));
                    shopLuckyDrawInfoTvLottery.setBackground(mContext.getResources().getDrawable(R.drawable.shape_gradient_bg_but_login_50));
                    shopLuckyDrawInfoProgressBar.setProgress(50);
                }
            } else {
                shopLuckyDrawInfoTvReserve.setBackground(mContext.getResources().getDrawable(R.drawable.shape_gradient_bg_but_login_50));
                shopLuckyDrawInfoTvLottery.setBackground(mContext.getResources().getDrawable(R.drawable.shape_gradient_bg_but_login_50));
                shopLuckyDrawInfoTvShip.setBackground(mContext.getResources().getDrawable(R.drawable.shape_gradient_bg_but_login_50));
                shopLuckyDrawInfoProgressBar.setProgress(100);
                shopLuckyDrawInfoImgFull.setVisibility(View.GONE);

                shopLuckyDrawInfoRlCount.setBackground(mContext.getResources().getDrawable(R.drawable.shape_bg_aaaaaa_50));
                shopLuckyDrawInfoRlProgressBar.setVisibility(View.GONE);
                shopLuckyDrawInfoTvNumberWinners.setVisibility(View.VISIBLE);
                itemLuckyDrawInfoCountdownView.setVisibility(View.GONE);
                shopLuckyDrawInfoTvHint.setVisibility(View.VISIBLE);
                shopLuckyDrawInfoTvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                onCallCountdownEnd.onClickEnd();
            }

        }

        /**
         * 官方自营    免运费    正品保障
         */
        List<ArrayItemBean> labelArray = new ArrayList<>();
        for (ArrayTools.shopLabel airlineTypeEnum : ArrayTools.shopLabel.values()) {
            if (airlineTypeEnum.isShow()) {
                labelArray.add(new ArrayItemBean()
                        .setId(airlineTypeEnum.getId())
                        .setName(airlineTypeEnum.getName())
                        .setIcon(airlineTypeEnum.getIcon())
                        .setVisibility(airlineTypeEnum.isShow()));
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView recyclerView = holder.getView(R.id.shop_lucky_draw_info_recycler_label);
        recyclerView.setLayoutManager(linearLayoutManager);
        ShopProductDetailsLabelAdapter shopProductDetailsLabelAdapter =
                new ShopProductDetailsLabelAdapter(
                        context, labelArray,
                        R.layout.item_shop_product_details_label);
        recyclerView.setAdapter(shopProductDetailsLabelAdapter);

        SearchLayout shopProductDetailsSlDirectSale = holder.getView(R.id.shop_lucky_draw_info_sl_direct_sale);
        String[] strings = {"全民拼团"};
        initRecyclerKeyWord(shopProductDetailsSlDirectSale, strings);

        TextView shopLuckyDrawInfoTvRuleDescription = holder.getView(R.id.shop_lucky_draw_info_tv_rule_description);
        shopLuckyDrawInfoTvRuleDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //规则说明
                onRuleDescription.onClickrule();
            }
        });
    }

    /**
     * 倒计时结束了===我要刷新页面了
     */
    @Override
    public void onCallEnd() {
        shopLuckyDrawInfoRlCount.setBackground(mContext.getResources().getDrawable(R.drawable.shape_bg_aaaaaa_50));
        shopLuckyDrawInfoRlProgressBar.setVisibility(View.GONE);
        shopLuckyDrawInfoTvNumberWinners.setVisibility(View.VISIBLE);
        itemLuckyDrawInfoCountdownView.setVisibility(View.GONE);
        shopLuckyDrawInfoTvHint.setVisibility(View.VISIBLE);
        shopLuckyDrawInfoTvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        onCallCountdownEnd.onClickEnd();
    }

    private void initRecyclerKeyWord(SearchLayout shopProductDetailsSlDirectSale, String[] keywordList) {
        List<View> mViewList = new ArrayList<>();
        if (keywordList.length != 0) {
            shopProductDetailsSlDirectSale.setVisibility(View.VISIBLE);
            for (int i = 0; i < keywordList.length; i++) {
                View mContentView = LayoutInflater.from(mContext).inflate(R.layout.item_search_commodity_key_word,
                        shopProductDetailsSlDirectSale, false);
                TextView shopProductDetailsTvDirectSale = mContentView.findViewById(R.id.shop_product_details_tv_direct_sale);
                shopProductDetailsTvDirectSale.setText(keywordList[i]);
                mViewList.add(mContentView);
            }
            shopProductDetailsSlDirectSale.setChildren(mViewList);
        } else {
            shopProductDetailsSlDirectSale.setVisibility(View.GONE);
        }
    }

    /**
     * 相关评论
     *
     * @param holder
     */
    private void initComment(BaseViewHolder holder) {
        if (commentListBeans != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            RecyclerView recyclerView = holder.getView(R.id.item_shop_product_details_comment_recycler);
            recyclerView.setLayoutManager(linearLayoutManager);
            ShopProductDetailsCommentAdapter shopProductDetailsCommentAdapter = new ShopProductDetailsCommentAdapter(
                    context, commentListBeans.getCommentList(), R.layout.item_shop_product_details_comment);
            shopProductDetailsCommentAdapter.setOnCallCommentImg(this);
            recyclerView.setAdapter(shopProductDetailsCommentAdapter);

            TextView itemShopProductDetailsCommentTvNum = holder.getView(R.id.item_shop_product_details_comment_tv_num);
            itemShopProductDetailsCommentTvNum.setText("(" + commentListBeans.getTotal() + ")");

            TextView itemShopProductDetailsCommentTvAll = holder.getView(R.id.item_shop_product_details_comment_tv_all);
            if (commentListBeans.getTotal() >= 3) {
                itemShopProductDetailsCommentTvAll.setText("查看更多评论");
            } else {
                itemShopProductDetailsCommentTvAll.setText("暂无更多评论");
            }
            itemShopProductDetailsCommentTvAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (commentListBeans.getTotal() > 2) {
                        onCallComment.onClickAll();
                    }
                }
            });
        }
    }

    @Override
    public void onCallCheckImg(View view, int position, int fatherPosition) {
        List<LocalMedia> list = new ArrayList<>();
        for (int i = 0; i < commentListBeans.getCommentList().get(fatherPosition).getCommentImageUrl().length; i++) {
            LocalMedia media = new LocalMedia();
            String url = commentListBeans.getCommentList().get(fatherPosition).getCommentImageUrl()[i];
            media.setPath(url);
            list.add(media);
        }
        PictureParameter.PreviewImg(context, position, list);
    }

    /**
     * 商品参数
     *
     * @param holder
     */
    private void initParameter(BaseViewHolder holder) {
        List<ProductParametersBean> productParametersBeans = new ArrayList<>();
        if (luckyFighInfoBean != null) {
            List<LuckyFighInfoBean.LuckyFighInfoSpecBean> productDetailsAggBean
                    = luckyFighInfoBean.getSpecList();
            if (productDetailsAggBean != null
                    && productDetailsAggBean.size() != 0) {
                for (int i = 0; i < productDetailsAggBean.size(); i++) {
                    if (productDetailsAggBean.get(i).isGeneralSpec()) {
                        String[] strings = new String[productDetailsAggBean.get(i).getValueAggList().size()];
                        for (int j = 0; j < productDetailsAggBean.get(i).getValueAggList().size(); j++) {
                            strings[j] = productDetailsAggBean.get(i).getValueAggList().get(j).getSpecValue();
                        }
                        productParametersBeans.add(new ProductParametersBean()
                                .setSpecName(productDetailsAggBean.get(i).getSpecName()).setValueAggList(strings));
                    }
                }
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = holder.getView(R.id.item_shop_product_details_parameter_recycler);
        recyclerView.setLayoutManager(linearLayoutManager);
        ShopProductDetailsParameterAdapter shopProductDetailsParameterAdapter = new ShopProductDetailsParameterAdapter(
                context, productParametersBeans, R.layout.item_shop_product_details_parameter);
        recyclerView.setAdapter(shopProductDetailsParameterAdapter);
        CheckBox itemShopProductDetailsParameterCBAll = holder.getView(R.id.item_shop_product_details_parameter_cb_all);
        itemShopProductDetailsParameterCBAll.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            ToastView.showToast(context, 0, "查看全部");
                            shopProductDetailsParameterAdapter.setType(2);
                            shopProductDetailsParameterAdapter.notifyDataSetChanged();
                        } else {
                            shopProductDetailsParameterAdapter.setType(1);
                            shopProductDetailsParameterAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 相关推荐
     *
     * @param holder
     */
    private void initRecommend(BaseViewHolder holder) {
        if (recommendBeanList != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            RecyclerView recyclerView = holder.getView(R.id.item_shop_product_details_recommend_recycler);
            recyclerView.setLayoutManager(linearLayoutManager);
            ShopProductDetailsRecommendAdapter shopProductDetailsRecommendAdapter = new ShopProductDetailsRecommendAdapter(
                    context, recommendBeanList, R.layout.item_shop_product_details_recommend);
            shopProductDetailsRecommendAdapter.setOnCallRecyclerItem(this);
            recyclerView.setAdapter(shopProductDetailsRecommendAdapter);
        }

        TextView itemShopProductDetailsRecommendAll = holder.getView(R.id.item_shop_product_details_recommend_all);
        itemShopProductDetailsRecommendAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallRecommendAll.onClickRecommendAll();
            }
        });
    }

    @Override
    public void onCallItem(View view, int postion) {
        int id = view.getId();
        if (id == R.id.item_shop_product_details_recommend_rl) {
            RouterHelper.toShopProductDetails(recommendBeanList.get(postion).getSpuId(), 0, null, null, true);
        }
    }

    private void initDetails(BaseViewHolder holder, int type, int position) {
//        if (luckyFighInfoBean != null) {
//            WebView webView = holder.getView(R.id.webView);
//            WebSettings mSetting = webView.getSettings();
//            mSetting.setJavaScriptEnabled(true);
//            mSetting.setBlockNetworkImage(false);//解决图片不显示
//            mSetting.setBuiltInZoomControls(true); // 显示放大缩小
//            mSetting.setSupportZoom(false);
//            luckyFighInfoBean.setDescription(luckyFighInfoBean
//                    .getDescription().replaceAll("<img", "<img style='width:100%'"));
//            webView.loadDataWithBaseURL(null,
//                    luckyFighInfoBean.getDescription(), "text/html", "utf-8", null);
//            getMeasureHeight(webView, type);
//            webView.setOnClickListener(this);
//            webView.setTag(position);
//        }
        if (luckyFighInfoBean != null) {
            WebView webView = holder.getView(R.id.webView);
            WebChromeClient webClient = new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView webView, String s) {
                    super.onReceivedTitle(webView, s);
                }
            };
            webView.setWebChromeClient(webClient);
            if (WebUrls.goodsDetails.replace("{goodsId}", luckyFighInfoBean.getSpuId()) != null
                    && !WebUrls.goodsDetails.replace("{goodsId}", luckyFighInfoBean.getSpuId()).equals("")) {
                webView.loadUrl(WebUrls.goodsDetails.replace("{goodsId}", luckyFighInfoBean.getSpuId()));
            }
            WebSettings webSettings = webView.getSettings();
            // 让WebView能够执行javaScript
            webSettings.setJavaScriptEnabled(true);
            // 让JavaScript可以自动打开windows
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            // 设置缓存
            webSettings.setAppCacheEnabled(true);
            // 设置缓存模式,一共有四种模式
            webSettings.setCacheMode(webSettings.LOAD_NO_CACHE);
            // 设置缓存路径
            // webSettings.setAppCachePath("");
            // 支持缩放(适配到当前屏幕)
            webSettings.setSupportZoom(true);
            // 将图片调整到合适的大小
            webSettings.setUseWideViewPort(true);
            // 支持内容重新布局,一共有四种方式
            // 默认的是NARROW_COLUMNS
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            // 设置可以被显示的屏幕控制
            webSettings.setDisplayZoomControls(true);

            webSettings.setSupportMultipleWindows(true);
            // 设置默认字体大小
            webSettings.setDefaultFontSize(12);

            webSettings.setDomStorageEnabled(true);//节点缓存？？
            //在User Agent中添加" device/android"作为标记
            webSettings.setUserAgentString(webSettings.getUserAgentString() + WebUrls.device);
            webView.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return super.shouldOverrideUrlLoading(view, url);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);

                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);

                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    super.onReceivedSslError(view, handler, error);
                    handler.proceed();// 接受所有网站的证书
                }

            });
            getMeasureHeight(webView, type);
            webView.setOnClickListener(this);
            webView.setTag(position);
        }
    }

    /**
     * 查看所有评论
     */
    public interface OnCallComment {
        void onClickAll();
    }

    /**
     * 查看全部相关推荐
     */
    public interface OnCallRecommendAll {
        void onClickRecommendAll();
    }

    /**
     * 倒计时结束  刷新界面
     */
    public interface OnCallCountdownEnd {
        void onClickEnd();
    }

    /**
     * 规则说明
     */
    public interface OnRuleDescription {
        void onClickrule();
    }

}

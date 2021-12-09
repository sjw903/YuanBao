package com.yuanbaogo.shop.productdetails.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.http.SslError;
import android.os.Build;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.utils.PriceUtils;
import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.commonlib.utils.array.ArrayTools;
import com.yuanbaogo.datacenter.constant.WebUrls;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.productdetails.model.CommentBean;
import com.yuanbaogo.shop.productdetails.model.DetailBean;
import com.yuanbaogo.shop.productdetails.model.DetailsModelBean;
import com.yuanbaogo.shop.productdetails.model.ProductDetailsBean;
import com.yuanbaogo.shop.productdetails.model.ProductParametersBean;
import com.yuanbaogo.shop.publics.model.RecommendBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.zui.search.view.SearchLayout;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.PictureParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 地址列表
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/5/21 5:01 PM
 * @Modifier:
 * @Modify:
 */
public class DetailsAdapter extends BaseRecycleAdapter<DetailsModelBean>
        implements View.OnClickListener, OnCallRecyclerItem, ShopProductDetailsCommentAdapter.OnCallCommentImg {

    Context context;

    private int layoutID;

    private int height = 0;

    /**
     * item 回调
     */
    private OnCallRecyclerItem onCallBack;

    private OnCallComment onCallComment;

    private OnCallSort onCallSort;

    private OnCallRecommendAll onCallRecommendAll;

    ProductDetailsBean productDetailsBean;

    List<RecommendBean> recommendBeanList;

    /**
     * 相关推荐 是否显示
     */
    boolean isShow;

    CommentBean commentListBeans;

    DetailBean detailBean;

    public void setDetailBean(DetailBean detailBean) {
        this.detailBean = detailBean;
    }

    public void setCommentListBeans(CommentBean commentListBeans) {
        this.commentListBeans = commentListBeans;
    }

    public void setRecommendBeanList(List<RecommendBean> recommendBeanList, boolean isShow) {
        this.recommendBeanList = recommendBeanList;
        this.isShow = isShow;
    }

    public void setProductDetailsBean(ProductDetailsBean productDetailsBean) {
        this.productDetailsBean = productDetailsBean;
    }

    public void setOnCallSort(OnCallSort onCallSort) {
        this.onCallSort = onCallSort;
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

    public DetailsAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        int type = mDataList.get(position).getType();
        if (type == 1) {//商品信息
            layoutID = R.layout.item_shop_product_details_rl_info;
            return 1001;
        } else if (type == 2) {//商品评论
            layoutID = R.layout.item_shop_product_details_rl_comment;
            return 1002;
        } else if (type == 3) {//商品参数
            layoutID = R.layout.item_shop_product_details_rl_parameter;
            return 1003;
        } else if (type == 4) {//相关推荐
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
     * 商品信息
     *
     * @param holder
     */
    private void initInfo(BaseViewHolder holder) {
        if (productDetailsBean != null) {

            SearchLayout shopProductDetailsSlDirectSale = holder.getView(R.id.shop_product_details_sl_direct_sale);
//            String[] strings = {"直销特卖"};
//            initRecyclerKeyWord(shopProductDetailsSlDirectSale, strings);
//            if (productDetailsBean.getKeywordList() != null && productDetailsBean.getKeywordList().length != 0) {
//                initRecyclerKeyWord(shopProductDetailsSlDirectSale, productDetailsBean.getKeywordList());
//            }

            TextView shopProductDetailsTvDiscountedPrice = holder.getView(R.id.shop_product_details_tv_discounted_price);
            shopProductDetailsTvDiscountedPrice.setText(
                    String.format(
                            mContext.getResources().getString(R.string.app_product_details_price),
                            PriceUtils.doubleToStringNo(productDetailsBean.getMinSalePrice()) + "")
            );

            TextView shopProductDetailsTvOriginalPrice = holder.getView(R.id.shop_product_details_tv_original_price);
            shopProductDetailsTvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            shopProductDetailsTvOriginalPrice.setText(
                    String.format(
                            mContext.getResources().getString(R.string.app_product_details_price),
                            PriceUtils.doubleToStringNo(productDetailsBean.getMinLinePrice()) + "")
            );

            TextView shopProductDetailsTvDiscount = holder.getView(R.id.shop_product_details_tv_discount);
            if (productDetailsBean.getMinSalePrice() != 0 && productDetailsBean.getMinLinePrice() != 0) {
                String price = PriceUtils.div(productDetailsBean.getMinSalePrice(), productDetailsBean.getMinLinePrice(), 2);
                shopProductDetailsTvDiscount.setText(
                        String.format(
                                mContext.getResources().getString(R.string.app_product_details_discount),
                                PriceUtils.mul(price, "10", 1) + "")
                );
            } else {
                shopProductDetailsTvDiscount.setText(
                        String.format(
                                mContext.getResources().getString(R.string.app_product_details_discount),
                                0 + "")
                );
            }

            RelativeLayout shopProductDetailsRlDiscount = holder.getView(R.id.shop_product_details_rl_discount);

            if (productDetailsBean.getMinSalePrice() == productDetailsBean.getMinLinePrice()) {
                shopProductDetailsRlDiscount.setVisibility(View.GONE);
            } else {
                shopProductDetailsRlDiscount.setVisibility(View.VISIBLE);
            }

            TextView shopProductDetailsTvSales = holder.getView(R.id.shop_product_details_tv_sales);
            shopProductDetailsTvSales.setText(
                    String.format(
                            mContext.getResources().getString(R.string.app_product_details_sales),
                            productDetailsBean.getTotalStock() + "",
                            productDetailsBean.getTotalSales() + "")
            );

            TextView shopProductDetailsTvTitle = holder.getView(R.id.shop_product_details_tv_title);
            shopProductDetailsTvTitle.setText(productDetailsBean.getGoodsName());

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
            RecyclerView recyclerView = holder.getView(R.id.shop_product_details_recycler_label);
            recyclerView.setLayoutManager(linearLayoutManager);
            ShopProductDetailsLabelAdapter shopProductDetailsLabelAdapter =
                    new ShopProductDetailsLabelAdapter(
                            context, labelArray,
                            R.layout.item_shop_product_details_label);
            recyclerView.setAdapter(shopProductDetailsLabelAdapter);

            /**
             * 选择规格
             */
            ProductDetailsBean.ProductDetailsAggBean productDetailsAggBean =
                    productDetailsBean.getRevealParameter(false);
            if (productDetailsAggBean == null) {
                //TODO 不能显示
            } else {
                List<ArrayItemBean> itemBeans = productDetailsAggBean.getValueAggList().stream().map(it -> {
                    ArrayItemBean arrayItemBean = new ArrayItemBean();
                    arrayItemBean.setId(it.getSpecIndex());
                    arrayItemBean.setIcon(0);
                    arrayItemBean.setName(it.getSpecValue());
                    arrayItemBean.setImgUrl(it.getImageUrl());
                    arrayItemBean.setVisibility(true);
                    return arrayItemBean;
                }).collect(Collectors.toList());
                itemBeans.add(new ArrayItemBean()
                        .setId(itemBeans.size())
                        .setName("共" + itemBeans.size() + "种" + productDetailsAggBean.getSpecName())
                        .setIcon(0)
                        .setVisibility(true));
                RecyclerView shopProductDetailsRecyclerSort = holder.getView(R.id.shop_product_details_recycler_sort);
                LinearLayoutManager linearLayoutManagerSort = new LinearLayoutManager(context);
                linearLayoutManagerSort.setOrientation(LinearLayoutManager.HORIZONTAL);
                shopProductDetailsRecyclerSort.setLayoutManager(linearLayoutManagerSort);
                ShopProductDetailsSortAdapter shopProductDetailsSortAdapter = new ShopProductDetailsSortAdapter(
                        context, itemBeans, R.layout.item_shop_product_details_sort);
                shopProductDetailsSortAdapter.setOnCallBack(this);
                shopProductDetailsRecyclerSort.setAdapter(shopProductDetailsSortAdapter);
            }

            ImageView shopProductDetailsImgSort = holder.getView(R.id.shop_product_details_img_sort);
            shopProductDetailsImgSort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCallSort.onClickSort();
                }
            });

        }

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

    @Override
    public void onCallItem(View view, int postion) {
        int id = view.getId();
        if (id == R.id.item_shop_product_details_recommend_rl) {
            RouterHelper.toShopProductDetails(recommendBeanList.get(postion).getSpuId(), 0, null, null, true);
        } else if (id == R.id.item_shop_product_details_sort_rl) {
            onCallSort.onClickSort();
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

    /**
     * 查看商品评论图片
     *
     * @param view
     * @param position
     */
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = holder.getView(R.id.item_shop_product_details_parameter_recycler);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (productDetailsBean != null
                && productDetailsBean.getSpecificationAgg() != null
                && productDetailsBean.getSpecificationAgg().size() != 0) {
            List<ProductParametersBean> productParametersBeans = new ArrayList<>();
            for (int i = 0; i < productDetailsBean.getSpecificationAgg().size(); i++) {
                if (productDetailsBean.getSpecificationAgg().get(i).getIsGeneralSpec()) {
                    String[] strings = new String[0];
                    for (int j = 0;
                         j < productDetailsBean.getSpecificationAgg().get(i).getValueAggList().size(); j++) {
                        strings = new String[productDetailsBean.getSpecificationAgg().get(i).getValueAggList().size()];
                        strings[j] = productDetailsBean.getSpecificationAgg().get(i).getValueAggList().get(j).getSpecValue();
                    }
                    productParametersBeans.add(new ProductParametersBean()
                            .setSpecName(productDetailsBean.getSpecificationAgg().get(i).getSpecName())
                            .setValueAggList(strings));
                }
            }
            ShopProductDetailsParameterAdapter shopProductDetailsParameterAdapter = new ShopProductDetailsParameterAdapter(
                    context, productParametersBeans, R.layout.item_shop_product_details_parameter);
            recyclerView.setAdapter(shopProductDetailsParameterAdapter);
            CheckBox itemShopProductDetailsParameterCBAll = holder.getView(R.id.item_shop_product_details_parameter_cb_all);
            itemShopProductDetailsParameterCBAll.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            if (isChecked) {
                                shopProductDetailsParameterAdapter.setType(2);
                                shopProductDetailsParameterAdapter.notifyDataSetChanged();
                            } else {
                                shopProductDetailsParameterAdapter.setType(1);
                                shopProductDetailsParameterAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }

    }

    /**
     * 相关推荐
     *
     * @param holder
     */
    private void initRecommend(BaseViewHolder holder) {
        RelativeLayout itemShopProductDetailsRecommendRl = holder.getView(R.id.item_shop_product_details_recommend_rl);
        RecyclerView recyclerView = holder.getView(R.id.item_shop_product_details_recommend_recycler);
        if (isShow) {
            itemShopProductDetailsRecommendRl.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            itemShopProductDetailsRecommendRl.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }
        if (recommendBeanList != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
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

    /**
     * 设置商品详情
     *
     * @param holder
     * @param type
     * @param position
     */
    private void initDetails(BaseViewHolder holder, int type, int position) {
        if (productDetailsBean != null && productDetailsBean.getSpuId() != null) {
            WebView webView = holder.getView(R.id.webView);
            WebChromeClient webClient = new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView webView, String s) {
                    super.onReceivedTitle(webView, s);
                }
            };
            webView.setWebChromeClient(webClient);
            if (WebUrls.goodsDetails.replace("{goodsId}", productDetailsBean.getSpuId()) != null
                    && !WebUrls.goodsDetails.replace("{goodsId}", productDetailsBean.getSpuId()).equals("")) {
                webView.loadUrl(WebUrls.goodsDetails.replace("{goodsId}", productDetailsBean.getSpuId()));
            }
            WebSettings webSettings = webView.getSettings();
            // 让WebView能够执行javaScript
            webSettings.setJavaScriptEnabled(true);
            // 让JavaScript可以自动打开windows
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            // 设置缓存
            webSettings.setAppCacheEnabled(true);
            // 设置缓存模式,一共有四种模式
//            webSettings.setCacheMode(webSettings.LOAD_NO_CACHE);
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }

            webSettings.setLoadWithOverviewMode(true);

            webSettings.setGeolocationEnabled(true);

            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

            webSettings.setDatabaseEnabled(true);

            webSettings.setAllowFileAccess(true);
            webSettings.setSavePassword(true);

            webSettings.setBuiltInZoomControls(true);

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
     * 查看分类
     */
    public interface OnCallSort {
        void onClickSort();
    }

    /**
     * 查看全部相关推荐
     */
    public interface OnCallRecommendAll {
        void onClickRecommendAll();
    }

}

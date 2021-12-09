package com.yuanbaogo.shop.search.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.utils.PriceUtils;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseRowsBean;
import com.yuanbaogo.shop.publics.adapter.ShopAggAdapter;
import com.yuanbaogo.shop.publics.model.RecommendBean;
import com.yuanbaogo.zui.search.view.SearchLayout;
import com.yuanbaogo.zui.view.RadiuImageView;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.shop.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 搜索商品适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/5/21 11:15 AM
 * @Modifier:
 * @Modify:
 */
public class ShopSearchCommodityAdapter
        extends BaseRecycleAdapter<SearchMerchandiseRowsBean>
        implements View.OnClickListener {

    int layoutId;

    List<SearchMerchandiseRowsBean> mDataList;

    /**
     * item 回调
     */
    private OnCallRecyclerItem onCallBack;

    int view = 0;//0 网格  1 列表

    public void setView(int view) {
        this.view = view;
    }

    public void setOnCallBack(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public ShopSearchCommodityAdapter(Context context,
                                      List<SearchMerchandiseRowsBean> mDataList,
                                      int layoutId) {
        super(context, mDataList, layoutId);
        this.layoutId = layoutId;
        this.mDataList = mDataList;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        SearchMerchandiseRowsBean productDetailsListBean = mDataList.get(position);

        RelativeLayout itemShopSearchCommodityRl = holder.getView(R.id.item_shop_search_commodity_rl);
        itemShopSearchCommodityRl.setOnClickListener(this);
        itemShopSearchCommodityRl.setTag(position);

        RadiuImageView itemShopSearchCommodityImg = holder.getView(R.id.item_shop_search_commodity_img);
        if (view == 0) {
            //获取屏幕宽高
            int screenWidth = ScreenUtils.getScreenWidth(mContext);
            int width = screenWidth - 30;
            int item = width / 2;
            itemShopSearchCommodityImg.setLayoutParams(new RelativeLayout.LayoutParams(item, item));

        }
        if (!TextUtils.isEmpty(productDetailsListBean.getCoverImages())) {
            Glide.with(mContext).load(productDetailsListBean.getCoverImages()).into(itemShopSearchCommodityImg);
        } else {
            itemShopSearchCommodityImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_shop_search_commodity));
        }

        TextView itemShopSearchCommodityTvTitle = holder.getView(R.id.item_shop_search_commodity_tv_title);
        itemShopSearchCommodityTvTitle.setText(productDetailsListBean.getGoodsName());

        TextView itemShopSearchCommodityTvMaterial = holder.getView(R.id.item_shop_search_commodity_tv_material);
        if (!TextUtils.isEmpty(productDetailsListBean.getBrandDescription())) {
            itemShopSearchCommodityTvMaterial.setVisibility(View.VISIBLE);
            itemShopSearchCommodityTvMaterial.setText(productDetailsListBean.getBrandDescription());
        } else {
            itemShopSearchCommodityTvMaterial.setVisibility(View.GONE);
        }

        SearchLayout shopProductDetailsSlDirectSale = holder.getView(R.id.shop_product_details_sl_direct_sale);
        initRecyclerKeyWord(shopProductDetailsSlDirectSale, productDetailsListBean.getKeywordList());

        TextView shopProductDetailsTvDiscountedPrice = holder.getView(R.id.shop_product_details_tv_discounted_price);

        RelativeLayout shopProductDetailsRlDiscount=holder.getView(R.id.shop_product_details_rl_discount);

        if (productDetailsListBean.getMinSalePrice()==productDetailsListBean.getMinLinePrice()){
            shopProductDetailsRlDiscount.setVisibility(View.GONE);
        }else{
            shopProductDetailsRlDiscount.setVisibility(View.VISIBLE);
        }

        shopProductDetailsTvDiscountedPrice.setText(
                String.format(
                        mContext.getResources().getString(R.string.app_product_details_price),
                        PriceUtils.doubleToStringNo(productDetailsListBean.getMinSalePrice()) + "")
        );

        TextView shopProductDetailsTvOriginalPrice = holder.getView(R.id.shop_product_details_tv_original_price);
        shopProductDetailsTvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        shopProductDetailsTvOriginalPrice.setText(
                String.format(
                        mContext.getResources().getString(R.string.app_product_details_price),
                        PriceUtils.doubleToStringNo(productDetailsListBean.getMinLinePrice()) + "")
        );

        TextView shopProductDetailsTvDiscount = holder.getView(R.id.shop_product_details_tv_discount);
        String price = PriceUtils.div(productDetailsListBean.getMinSalePrice(), productDetailsListBean.getMinLinePrice(), 2);
        shopProductDetailsTvDiscount.setText(
                String.format(
                        mContext.getResources().getString(R.string.app_product_details_discount),
                        PriceUtils.mul(price, "10", 1))
        );

        TextView shopProductDetailsTvSales = holder.getView(R.id.shop_product_details_tv_sales);
        shopProductDetailsTvSales.setText(
                String.format(
                        mContext.getResources().getString(R.string.app_product_details_sold),
                        productDetailsListBean.getTotalSales() + "")
        );

        if (view == 0) {
            LinearLayout itemShopSearchCommodityLlAgg = holder.getView(R.id.item_shop_search_commodity_ll_agg);
            if (productDetailsListBean.getSpecificationAgg().get(0).getValueAggList().size() == 0) {
                itemShopSearchCommodityLlAgg.setVisibility(View.GONE);
                return;
            }
            itemShopSearchCommodityLlAgg.setVisibility(View.GONE);
            TextView itemShopSearchCommodityTvAgg = holder.getView(R.id.item_shop_search_commodity_tv_agg);
            itemShopSearchCommodityTvAgg.setText(String.format(
                    mContext.getResources().getString(R.string.app_product_details_color_optional),
                    productDetailsListBean.getSpecificationAgg().get(0).getValueAggList().size() + "")
            );

            RecyclerView itemShopSearchCommodityRecyclerAgg = holder.getView(R.id.item_shop_search_commodity_recycler_agg);
//            initRecyclerAgg(itemShopSearchCommodityRecyclerAgg, productDetailsListBean.getSpecificationAgg().get(0));
        }

    }

    private void initRecyclerKeyWord(SearchLayout shopProductDetailsSlDirectSale, String[] keywordList) {
        List<View> mViewList = new ArrayList<>();
        if (keywordList.length != 0) {
            shopProductDetailsSlDirectSale.setVisibility(View.GONE);
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

    private void initRecyclerAgg(RecyclerView itemShopSearchCommodityRecyclerAgg, RecommendBean.RecommendAGGBean recommendAGGBean) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        itemShopSearchCommodityRecyclerAgg.setLayoutManager(linearLayoutManager);

        ShopAggAdapter shopAggAdapter = new ShopAggAdapter(mContext,
                recommendAGGBean.getValueAggList(), R.layout.item_shop_agg);
        itemShopSearchCommodityRecyclerAgg.setAdapter(shopAggAdapter);
    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }


}

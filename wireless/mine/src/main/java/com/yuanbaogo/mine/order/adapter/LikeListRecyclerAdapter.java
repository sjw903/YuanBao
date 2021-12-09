package com.yuanbaogo.mine.order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.utils.PriceUtils;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.model.LikeListModelItem;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.zui.view.RoundImageView;

import java.util.List;

public class LikeListRecyclerAdapter extends BaseRecycleAdapter<LikeListModelItem> implements View.OnClickListener {

    private LinearLayout mLikeLlItem;
    private RoundImageView mLikeIvImage;
    private TextView mLikeTvName;
    private TextView mLikeTvSize;
    private TextView mLikeTvType;
    private TextView mLikeTvMoney;
    private TextView mLikeTvOriginal;
    private TextView mLikeTvDiscount;
    private TextView mLikeTvNum;
    private LikeListModelItem likeListModelItem;

    OnCallClick onCallClick;

    public OnCallClick getOnCallClick() {
        return onCallClick;
    }

    public void setOnCallClick(OnCallClick onCallClick) {
        this.onCallClick = onCallClick;
    }

    public LikeListRecyclerAdapter(Context context, List<LikeListModelItem> mDataList) {
        this(context, mDataList, 0);
    }

    public LikeListRecyclerAdapter(Context context, List<LikeListModelItem> mDataList, int layoutId) {
        super(context, mDataList, layoutId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_layout_order_like_item;
    }

    @SuppressLint({"SetTextI18n", "StringFormatMatches"})
    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        initView(holder);
        likeListModelItem = getDataList().get(position);
        Glide.with(mLikeIvImage).load(likeListModelItem.getCoverImages()).into(mLikeIvImage);
        mLikeTvName.setText(likeListModelItem.getCargoName());
        mLikeTvSize.setText(likeListModelItem.getDescription() + "");
        initGoodsType(likeListModelItem.getSpecialType());
        mLikeTvMoney.setText(mContext.getString(R.string.order_money,
                likeListModelItem.getMinSalePrice() / 100));
        mLikeTvOriginal.setText(mContext.getString(R.string.order_money, likeListModelItem.getMinLinePrice() / 100));
        double salePrice = likeListModelItem.getMinSalePrice() / 10f;
        double linePrice = likeListModelItem.getMinLinePrice() / 100f;
        double discounts = salePrice / linePrice;
        mLikeTvDiscount.setText(PriceUtils.doubleToStringOne(discounts) + "折");
        mLikeTvNum.setText("已售" + likeListModelItem.getTotalSales() + "件");
        mLikeLlItem.setTag(position);
    }

    private void initView(BaseViewHolder holder) {
        mLikeLlItem = holder.getView(R.id.like_ll_item);
        mLikeIvImage = holder.getView(R.id.like_iv_image);
        mLikeTvName = holder.getView(R.id.like_tv_name);
        mLikeTvSize = holder.getView(R.id.like_tv_size);
        mLikeTvType = holder.getView(R.id.like_tv_type);
        mLikeTvMoney = holder.getView(R.id.like_tv_money);
        mLikeTvOriginal = holder.getView(R.id.like_tv_original);
        mLikeTvDiscount = holder.getView(R.id.like_tv_discount);
        mLikeTvNum = holder.getView(R.id.like_tv_num);
        mLikeLlItem.setOnClickListener(this);
    }

    private void initGoodsType(int goodsType) {
        if (goodsType == 0) {
            mLikeTvType.setText("普通商品");
        } else if (goodsType == 1) {
            mLikeTvType.setText("五百专区商品");
        } else if (goodsType == 2) {
            mLikeTvType.setText("五千专区商品");
        } else if (goodsType == 3) {
            mLikeTvType.setText("五万专区商品");
        } else if (goodsType == 4) {
            mLikeTvType.setText("一城一品");
        } else if (goodsType == 5) {
            mLikeTvType.setText("秒杀商品");
        } else if (goodsType == 7) {
            mLikeTvType.setText("主题专区");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.like_ll_item) {
            // 点击商品
            onCallClick.onClickItem(getDataList().get((int) v.getTag()).getSpuId() + "",
                    getDataList().get((int) v.getTag()).getSpecialType());
        }
    }

    public interface OnCallClick {
        void onClickItem(String spuId, int specialType);
    }

}

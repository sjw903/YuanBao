package com.yuanbaogo.mine.order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.utils.PriceUtils;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.model.GoodsVOList;
import com.yuanbaogo.mine.order.model.GoodsVOListItem;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.RoundImageView;

import java.util.List;

public class OrderGoodsListRecyclerAdapter extends BaseRecycleAdapter<GoodsVOListItem> {

    private RelativeLayout mOrderDetailRlGood;
    private RoundImageView mOrderDetailIvImg;
    private TextView mOrderDetailTvName;
    private TextView mOrderDetailTvNum;
    private TextView mOrderDetailTvMoney;
    private TextView mOrderDetailTvAddCar;
    private TextView mOrderDetailTvSize;
    private boolean showAddCar = false;

    public OrderGoodsListRecyclerAdapter(Context context, List<GoodsVOListItem> mDataList) {
        this(context, mDataList, 0);
    }

    public OrderGoodsListRecyclerAdapter(Context context, List<GoodsVOListItem> mDataList, int layoutId) {
        super(context, mDataList, layoutId);
    }

    public void setShowAddCar(boolean showAddCar) {
        this.showAddCar = showAddCar;
        notifyDataSetChanged();
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_layout_order_goods_list_item;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        GoodsVOListItem goodsVOList = getDataList().get(position);
        initView(holder);
        bindData(goodsVOList);
    }

    @SuppressLint("SetTextI18n")
    private void bindData(GoodsVOListItem goodsVOList) {
        Glide.with(mOrderDetailIvImg.getContext()).load(goodsVOList.getGoodsImageUrl()).into(mOrderDetailIvImg);
        mOrderDetailTvName.setText(goodsVOList.getGoodsTitle());
        if (goodsVOList.getGoodsCount() > 0) {
            mOrderDetailTvNum.setText("x" + goodsVOList.getGoodsCount());
        } else {
            mOrderDetailTvNum.setText("x" + goodsVOList.getBuyQuantity());
        }
        if (goodsVOList.getGoodsAmount() != null) {
            mOrderDetailTvMoney.setText(mContext.getString(R.string.order_money, PriceUtils.doubleToStringNo(goodsVOList.getGoodsAmount()) + ""));
        } else {
            mOrderDetailTvMoney.setText(mContext.getString(R.string.order_money, PriceUtils.doubleToStringNo(Double.parseDouble(goodsVOList.getGoodsPrice())) + ""));
        }
        mOrderDetailTvAddCar.setOnClickListener(v -> OrderNetworkUtils.goodsAddCar(mContext, goodsVOList, successful -> {
            if (successful) {
                ToastView.showToast("加入购物车成功");
            } else {
                ToastView.showToast("加入购物车失败");
            }
        }));
        mOrderDetailRlGood.setOnClickListener(v -> {
            // 暂不知是否需要跳转到商品详情页面，如需跳转，只需加上跳转商品详情页面代码即可
        });
        if (showAddCar) {
            mOrderDetailTvAddCar.setVisibility(View.VISIBLE);
        } else {
            mOrderDetailTvAddCar.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(goodsVOList.getSkuIndexesName())) {
            mOrderDetailTvSize.setText(goodsVOList.getSkuIndexesName().trim());
        }
    }

    private void initView(BaseViewHolder holder) {
        mOrderDetailRlGood = holder.getView(R.id.order_detail_rl_good);
        mOrderDetailIvImg = holder.getView(R.id.order_detail_iv_img);
        mOrderDetailTvName = holder.getView(R.id.order_detail_tv_name);
        mOrderDetailTvNum = holder.getView(R.id.order_detail_tv_num);
        mOrderDetailTvMoney = holder.getView(R.id.order_detail_tv_money);
        mOrderDetailTvAddCar = holder.getView(R.id.order_detail_tv_add_car);
        mOrderDetailTvSize = holder.getView(R.id.order_detail_tv_size);
    }

}

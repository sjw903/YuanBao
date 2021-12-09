package com.yuanbaogo.shop.main.adapter;

import android.content.Context;
import android.graphics.PointF;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.shop.main.model.ShopRecommendVideoBean;
import com.yuanbaogo.shop.main.view.ShopFragment;
import com.yuanbaogo.zui.animation.DivergeViewSecond;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.zui.view.RadiuImageView;

import java.util.List;

/**
 * @Description: 首页 推荐直播适配器
 * @Params:
 * @Problem: 需要修改动画定时器
 * @Author: HG
 * @Date: 7/2/21 3:14 PM
 * @Modifier:
 * @Modify:
 */
public class ShopRecommendLiveAdapter extends BaseRecycleAdapter<ShopRecommendVideoBean>
        implements View.OnClickListener {

    int layoutId;

    /**
     * item 回调
     */
    private OnCallRecyclerItem onCallBack;

    /**
     * 数据
     */
    List<ShopRecommendVideoBean> datas;

    public void setOnCallback(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public ShopRecommendLiveAdapter(Context context, List<ShopRecommendVideoBean> datas, int layoutId) {
        super(context, datas, layoutId);
        this.datas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        RelativeLayout itemShopRecommendLiveRlRoot = holder.getView(R.id.item_shop_recommend_live_rl_root);
        itemShopRecommendLiveRlRoot.setOnClickListener(this);
        itemShopRecommendLiveRlRoot.setTag(position);

        RadiuImageView itemShopRecommendLiveImg = holder.getView(R.id.item_shop_recommend_live_img);
        initPX(itemShopRecommendLiveImg);
        TextView itemShopRecommendLiveTvTile = holder.getView(R.id.item_shop_recommend_live_tv_tile);
        TextView itemShopRecommendLiveTvDiscount = holder.getView(R.id.item_shop_recommend_live_tv_discount);
        TextView itemShopRecommendLiveTvPrice = holder.getView(R.id.item_shop_recommend_live_tv_price);

        RelativeLayout itemShopRecommendLiveRlFacing = holder.getView(R.id.item_shop_recommend_live_rl_facing);

        ViewTreeObserver vto = itemShopRecommendLiveImg.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                itemShopRecommendLiveImg.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                itemShopRecommendLiveRlFacing.setLayoutParams(
                        new RelativeLayout.LayoutParams(itemShopRecommendLiveImg.getWidth(),
                                itemShopRecommendLiveImg.getHeight()));
            }
        });

        itemShopRecommendLiveRlFacing.setBackgroundColor(mContext.getResources().getColor(R.color.color1AFFFFFF));

        Glide.with(mContext).load(datas.get(position).getBusinessCover()).into(itemShopRecommendLiveImg);
        if (!TextUtils.isEmpty(datas.get(position).getBusinessTitleOne())) {
            itemShopRecommendLiveTvTile.setText(datas.get(position).getBusinessTitleOne());
            itemShopRecommendLiveTvTile.setVisibility(View.VISIBLE);
        } else {
            itemShopRecommendLiveTvTile.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(datas.get(position).getBusinessTitleTwo())) {
            itemShopRecommendLiveTvDiscount.setText(datas.get(position).getBusinessTitleTwo());
            itemShopRecommendLiveTvDiscount.setVisibility(View.VISIBLE);
        } else {
            itemShopRecommendLiveTvDiscount.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(datas.get(position).getBusinessTitleThree())) {
            itemShopRecommendLiveTvPrice.setText(datas.get(position).getBusinessTitleThree());
            itemShopRecommendLiveTvPrice.setVisibility(View.VISIBLE);
        } else {
            itemShopRecommendLiveTvPrice.setVisibility(View.GONE);
        }

        DivergeViewSecond mineDivergeViewSecond = holder.getView(R.id.item_shop_recommend_live_diverge_view_second);
        ImageView itemShopRecommendLiveImgAnima = holder.getView(R.id.item_shop_recommend_live_img_anima);
        ImageView itemShopRecommendLiveImgLive = holder.getView(R.id.item_shop_recommend_live_img_live);

        Glide.with(mContext).load(R.drawable.icon_shop_recommend_live_diverge_view_second4).into(itemShopRecommendLiveImgLive);

        ScaleAnimation scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(mContext, R.anim.scale_anima_img);
        itemShopRecommendLiveImgAnima.startAnimation(scaleAnimation);

        mineDivergeViewSecond.post(new Runnable() {
            @Override
            public void run() {
                mineDivergeViewSecond.setEndPoint(new PointF(mineDivergeViewSecond.getMeasuredWidth() / 2, 0));
                mineDivergeViewSecond.setDivergeViewProvider(new ShopFragment.Provider());
            }
        });
    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }

}

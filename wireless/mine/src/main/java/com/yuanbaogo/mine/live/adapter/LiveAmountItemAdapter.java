package com.yuanbaogo.mine.live.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.yuanbaogo.datacenter.utils.YBMoneyUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.live.model.LiveAmountItem;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

public class LiveAmountItemAdapter extends BaseRecycleAdapter<LiveAmountItem> {

    private LiveAmountItem liveAmountiItem;
    private ImageView liveItemHeadImg;//头像
    private TextView liveItemUserNameTv;//用户名
    private TextView liveItemTimeTv;//时间
    private ImageView liveItemCommerceIv;//是否带货
    private TextView liveItemCharmNum;//魅力值
    private TextView liveItemWatchNum;//观看人数
    private TextView liveItemAddFansNum;//新增粉丝
    private TextView liveItemOrderNum;//订单数
    private TextView liveItemTransactionNum;//订单交易总额
    private TextView liveItemIncomeNum;//预计收入(元)
    private LinearLayout liveItemCommerceLl;

    public LiveAmountItemAdapter(Context context, List<LiveAmountItem> mDataList) {
        super(context,mDataList,0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_live_amount_item;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        initView(holder);
        liveAmountiItem = getDataList().get(position);
        if(liveAmountiItem.getBaseUserVO().getPortraitUrl() == null){
            Glide.with(mContext)
                    .load(R.mipmap.icon_video_default_head)//默认头像
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(liveItemHeadImg);
        }else {
            Glide.with(mContext)
                    .load(liveAmountiItem.getBaseUserVO().getPortraitUrl())
                    .thumbnail(0.01f)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(liveItemHeadImg);
        }
        liveItemUserNameTv.setText(liveAmountiItem.getBaseUserVO().getNickName());
        liveItemTimeTv.setText(liveAmountiItem.getTime());
        //是否带货 0：false带货    1：true未带货
        if (liveAmountiItem.getSellGoods()){
            liveItemCommerceIv.setVisibility(View.GONE);
            liveItemCommerceLl.setVisibility(View.GONE);
        }else {
            liveItemCommerceIv.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(R.mipmap.icon_commerce)
                    .into(liveItemCommerceIv);
            liveItemCommerceLl.setVisibility(View.VISIBLE);
            liveItemOrderNum.setText(liveAmountiItem.getOrderCount());
            liveItemTransactionNum.setText(YBMoneyUtil.YbPrice(liveAmountiItem.getTotalAmount()));
            liveItemIncomeNum.setText(YBMoneyUtil.YbPrice(liveAmountiItem.getCommissionAmount()));
        }
        liveItemCharmNum.setText(liveAmountiItem.getCharmValue().toString());
        liveItemWatchNum.setText(liveAmountiItem.getViewsNumber().toString());
        liveItemAddFansNum.setText(liveAmountiItem.getNewFan().toString());
    }
    private void initView(BaseViewHolder holder) {
        liveItemHeadImg  = holder.getView(R.id.live_item_head_img);
        liveItemUserNameTv = holder.getView(R.id.live_item_user_name_tv);
        liveItemTimeTv = holder.getView(R.id.live_item_time_tv);
        liveItemCommerceIv = holder.getView(R.id.live_item_commerce_iv);
        liveItemCharmNum = holder.getView(R.id.live_item_charm_num);
        liveItemWatchNum = holder.getView(R.id.live_item_watch_num);
        liveItemAddFansNum = holder.getView(R.id.live_item_add_fans_num);
        liveItemOrderNum = holder.getView(R.id.live_item_order_num);
        liveItemTransactionNum = holder.getView(R.id.live_item_transaction_num);
        liveItemIncomeNum = holder.getView(R.id.live_item_income_num);
        liveItemCommerceLl = holder.getView(R.id.live_item_commerce_ll);
    }
}

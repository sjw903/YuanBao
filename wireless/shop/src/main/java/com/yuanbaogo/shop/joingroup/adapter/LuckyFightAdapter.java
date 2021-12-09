package com.yuanbaogo.shop.joingroup.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.zui.progress.GradientProgressBar;
import com.yuanbaogo.shop.joingroup.model.LuckyFightBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.zui.view.RadiuImageView;

import java.util.List;

/**
 * @Description: 幸运拼团 商品列表
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/27/21 1:28 PM
 * @Modifier:
 * @Modify:
 */
public class LuckyFightAdapter extends BaseRecycleAdapter<LuckyFightBean.LuckyFightRowsBean>
        implements View.OnClickListener {

    int layoutId;

    List<LuckyFightBean.LuckyFightRowsBean> mDataList;

    /**
     * item 回调
     */
    private OnCallRecyclerItem onCallBack;

    public void setOnCallBack(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public LuckyFightAdapter(Context context,
                             List<LuckyFightBean.LuckyFightRowsBean> mDataList,
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
        LuckyFightBean.LuckyFightRowsBean luckyFightRowsBean = mDataList.get(position);
        RelativeLayout itemShopLuckyDrawRl = holder.getView(R.id.item_shop_lucky_draw_rl);
        itemShopLuckyDrawRl.setOnClickListener(this);
        itemShopLuckyDrawRl.setTag(position);

        RadiuImageView itemShopLuckyDrawImg = holder.getView(R.id.item_shop_lucky_draw_img);
        //获取屏幕宽高
        int screenWidth = ScreenUtils.getScreenWidth(mContext);
        int width = screenWidth - 30;
        int item = width / 2;
        itemShopLuckyDrawImg.setLayoutParams(new RelativeLayout.LayoutParams(item, item));
        if (!TextUtils.isEmpty(luckyFightRowsBean.getCoverImages())) {
            Glide.with(mContext).load(luckyFightRowsBean.getCoverImages()).into(itemShopLuckyDrawImg);
        } else {
            itemShopLuckyDrawImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_shop_search_commodity));
        }

        TextView itemShopLuckyDrawTvCountdown = holder.getView(R.id.item_shop_lucky_draw_tv_countdown);

        RelativeLayout itemShopLuckyDrawRlInfo = holder.getView(R.id.item_shop_lucky_draw_rl_info);
        TextView shopProductDetailsTvReserve = holder.getView(R.id.shop_product_details_tv_reserve);
        TextView itemShopLuckyDrawTvTitle = holder.getView(R.id.item_shop_lucky_draw_tv_title);
        TextView shopProductDetailsTvPrice = holder.getView(R.id.shop_product_details_tv_price);
        GradientProgressBar itemShopLuckyDrawProgressBar = holder.getView(R.id.item_shop_lucky_draw_progress_bar);
        TextView itemShopLuckyDrawTvNumber = holder.getView(R.id.item_shop_lucky_draw_tv_number);
        TextView itemShopLuckyDrawTvDate = holder.getView(R.id.item_shop_lucky_draw_tv_date);
        TextView itemShopLuckyDrawTvTime = holder.getView(R.id.item_shop_lucky_draw_tv_time);

        RelativeLayout itemShopLuckyDrawRlEnd = holder.getView(R.id.item_shop_lucky_draw_rl_end);
        itemShopLuckyDrawRlEnd.setLayoutParams(new RelativeLayout.LayoutParams(item, item));

        RelativeLayout itemShopLuckyDrawRlNumber = holder.getView(R.id.item_shop_lucky_draw_rl_number);
        RelativeLayout itemShopLuckyDrawRlTime = holder.getView(R.id.item_shop_lucky_draw_rl_time);

        itemShopLuckyDrawProgressBar.setMaxCount(mDataList.get(position).getLimitNumber());
        itemShopLuckyDrawProgressBar.setCurrentCount(mDataList.get(position).getCurrentNumber());

        itemShopLuckyDrawTvTitle.setText(luckyFightRowsBean.getGoodsName());
        String price = "¥" + (luckyFightRowsBean.getGroupGoodsPrice() / 100);
        Spannable string = new SpannableString(price);
        // ¥
        string.setSpan(new AbsoluteSizeSpan(30), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // 价格
        string.setSpan(new AbsoluteSizeSpan(50), 1, price.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // 显示
        shopProductDetailsTvPrice.setText(string);
        itemShopLuckyDrawTvNumber.setText(luckyFightRowsBean.getCurrentNumber() + "/"
                + luckyFightRowsBean.getLimitNumber());

        itemShopLuckyDrawTvDate.setText(DateUtils.getDateMonthDay(luckyFightRowsBean.getLotteryTime()) + "");
        itemShopLuckyDrawTvTime.setText(DateUtils.getDateHourMinute(luckyFightRowsBean.getLotteryTime()) + " 抽奖");

        itemShopLuckyDrawRlInfo.setBackground(null);
        if (mDataList.get(position).getCountdownTime() > 0) {
            itemShopLuckyDrawTvCountdown.setText(
                    countDown(Math.toIntExact(mDataList.get(position).getCountdownTime() / 1000)));
            if (UserStore.isLogin() &&mDataList.get(position).getParticipated()) {
                shopProductDetailsTvReserve.setBackground(
                        mContext.getResources().getDrawable(R.drawable.shape_gradient_bg_e4e4e4_50));
                shopProductDetailsTvReserve.setText("已预约");
                shopProductDetailsTvReserve.setTextColor(mContext.getResources().getColor(R.color.colorE03030));
            } else {
                shopProductDetailsTvReserve.setBackground(
                        mContext.getResources().getDrawable(R.drawable.shape_gradient_bg_red_50));
                shopProductDetailsTvReserve.setText("立即预约");
                shopProductDetailsTvReserve.setTextColor(mContext.getResources().getColor(R.color.colorEA5504));
            }
            itemShopLuckyDrawTvCountdown.setVisibility(View.VISIBLE);
            itemShopLuckyDrawRlEnd.setVisibility(View.GONE);
            itemShopLuckyDrawRlNumber.setVisibility(View.VISIBLE);
            itemShopLuckyDrawRlTime.setVisibility(View.VISIBLE);
        } else {
            mDataList.get(position).setActiivitiesStatus(3);
            shopProductDetailsTvReserve.setBackground(
                    mContext.getResources().getDrawable(R.drawable.shape_bg_aaaaaa_50));
            shopProductDetailsTvReserve.setText("活动结束");
            shopProductDetailsTvReserve.setTextColor(mContext.getResources().getColor(R.color.colorFFFFFF));

            itemShopLuckyDrawTvCountdown.setVisibility(View.GONE);
            itemShopLuckyDrawRlEnd.setVisibility(View.VISIBLE);
            itemShopLuckyDrawRlNumber.setVisibility(View.INVISIBLE);
            itemShopLuckyDrawRlTime.setVisibility(View.GONE);
        }

        itemShopLuckyDrawProgressBar.setBgColor(R.color.colorF8CCB3);

    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }

    /**
     * 倒计时
     */
    private String countDown(int time) {
        int countDownNum = time;
        int hours = countDownNum / 3600;
        int minute = (countDownNum % 3600) / 60;
        int second = countDownNum % 60;
        String string = String.format("%02d:%02d:%02d", hours, minute, second);
        return string;
    }

}

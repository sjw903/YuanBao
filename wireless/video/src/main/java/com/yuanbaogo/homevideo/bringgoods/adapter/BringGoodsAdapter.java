package com.yuanbaogo.homevideo.bringgoods.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.bringgoods.model.LiveGoodsListBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/12/21 5:46 PM
 * @Modifier:
 * @Modify:
 */
public class BringGoodsAdapter extends BaseRecycleAdapter<LiveGoodsListBean> {

    boolean mSelectMode;

    public interface OnClickListener {

        void onCheckedChanged();
        void onSingleCheckedChanged();

        void onItemClick(LiveGoodsListBean bean);
    }

    private OnClickListener onCall;

    public void setOnClickListener(OnClickListener onCallItem) {
        this.onCall = onCallItem;
    }

    public BringGoodsAdapter(Context context, boolean selectMode) {
        super(context);
        mSelectMode = selectMode;
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_item_bring_goods;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        LiveGoodsListBean bean = getDataList().get(position);

        RelativeLayout rl_bring_goods = holder.getView(R.id.rl_bring_goods);

        ImageView item_bring_goods_img = holder.getView(R.id.item_bring_goods_img);
        TextView item_bring_goods_tv_title = holder.getView(R.id.item_bring_goods_tv_title);
        TextView item_bring_goods_tv_info = holder.getView(R.id.item_bring_goods_tv_info);
        TextView item_bring_goods_tv_price = holder.getView(R.id.item_bring_goods_tv_price);
        CheckBox itemBringGoodsCB = holder.getView(R.id.item_bring_goods_cb);

        Glide.with(mContext)
                .load(bean.getGoodsPicture())
                .thumbnail(0.1f).into(item_bring_goods_img);
        item_bring_goods_tv_title.setText(bean.getGoodsName());
        item_bring_goods_tv_info.setText(bean.getSpecificationName());
        item_bring_goods_tv_price.setText("￥" + bean.getGoodsMoney()/100);

        if (!mSelectMode) {
            itemBringGoodsCB.setChecked(bean.isChecked());
        }

        if (mSelectMode) {
            itemBringGoodsCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    bean.setChecked(b);
                    if (onCall != null)
                        onCall.onCheckedChanged();
                }
            });
        } else{
            itemBringGoodsCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (LiveGoodsListBean bean : getDataList()) {
                        bean.setChecked(false);
                    }
                    bean.setChecked(true);
                    notifyDataSetChanged();
                    if (onCall != null)
                        onCall.onSingleCheckedChanged();
                }
            });
        }

        rl_bring_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCall != null)
                    onCall.onItemClick(bean);
            }
        });

    }


}

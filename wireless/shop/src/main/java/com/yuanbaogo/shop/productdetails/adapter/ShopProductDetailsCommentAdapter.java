package com.yuanbaogo.shop.productdetails.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.productdetails.model.CommentBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallCheckImg;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.zui.view.CircleImageView;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 商品评论适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/5/21 11:15 AM
 * @Modifier:
 * @Modify:
 */
public class ShopProductDetailsCommentAdapter extends BaseRecycleAdapter<CommentBean.CommentListBean>
        implements OnCallCheckImg {

    Context context;

    int layoutId;

    List<CommentBean.CommentListBean> mDataList;

    /**
     * 1 商品详情评论  2 全部评论
     */
    int type = 1;

    public void setType(int type) {
        this.type = type;
    }

    public ShopProductDetailsCommentAdapter(Context context, List<CommentBean.CommentListBean> mDataList, int layoutId) {
        super(context, mDataList, layoutId);
        this.context = context;
        this.layoutId = layoutId;
        this.mDataList = mDataList;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public int getItemCount() {
        if (type == 1) {
            if (mDataList.size() > 2) {
                return 2;
            }
        }
        return super.getItemCount();
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        RecyclerView itemShopProductDetailsCommentRecyclerImg = holder.getView(R.id.item_shop_product_details_comment_recycler_img);
        if (mDataList.get(position).getCommentImageUrl() != null &&
                mDataList.get(position).getCommentImageUrl().length != 0) {
            itemShopProductDetailsCommentRecyclerImg.setVisibility(View.VISIBLE);
            initCommentImg(itemShopProductDetailsCommentRecyclerImg,
                    mDataList.get(position).getCommentImageUrl(), position);
        } else {
            itemShopProductDetailsCommentRecyclerImg.setVisibility(View.GONE);
        }

        TextView itemShopProductDetailsCommentTvName = holder.getView(R.id.item_shop_product_details_comment_tv_name);
        itemShopProductDetailsCommentTvName.setText(mDataList.get(position).getUserNickName());

        TextView itemShopProductDetailsCommentTvTime = holder.getView(R.id.item_shop_product_details_comment_tv_time);
        itemShopProductDetailsCommentTvTime.setText(DateUtils.formatDate2(mDataList.get(position).getCreateTime()));

        CircleImageView itemShopProductDetailsCommentImg = holder.getView(R.id.item_shop_product_details_comment_img);
        if (!TextUtils.isEmpty(mDataList.get(position).getUserHeadImageUrl())) {
            Glide.with(mContext).load(mDataList.get(position).getUserHeadImageUrl()).into(itemShopProductDetailsCommentImg);
        } else {
            itemShopProductDetailsCommentImg.setImageResource(R.mipmap.icon_default_head);
        }
        TextView itemShopProductDetailsCommentTvContent = holder.getView(R.id.item_shop_product_details_comment_tv_content);
        if (type == 1) {
            itemShopProductDetailsCommentTvContent.setLines(2);
            itemShopProductDetailsCommentTvContent.setEllipsize(TextUtils.TruncateAt.END);
        } else if (type == 2) {

        }
        itemShopProductDetailsCommentTvContent.setText(mDataList.get(position).getCommentRemark());
    }

    private void initCommentImg(RecyclerView itemShopProductDetailsCommentRecycler, String[] commentImageUrl, int position) {
        List<String> commentImageUrlList = Arrays.asList(commentImageUrl);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
        itemShopProductDetailsCommentRecycler.setLayoutManager(gridLayoutManager);
        ShopProductDetailsCommentImgAdapter shopProductDetailsCommentImgAdapter = new ShopProductDetailsCommentImgAdapter(
                context, commentImageUrlList, R.layout.item_shop_product_details_comment_img);
        shopProductDetailsCommentImgAdapter.setOnCallCheckImg(this, position);
        itemShopProductDetailsCommentRecycler.setAdapter(shopProductDetailsCommentImgAdapter);
    }

    @Override
    public void onCallImg(View view, int position, int fatherPosition) {
        int id = view.getId();
        if (id == R.id.item_shop_product_details_comment_rl) {
            onCallCommentImg.onCallCheckImg(view, position, fatherPosition);
        }
    }

    OnCallCommentImg onCallCommentImg;

    public void setOnCallCommentImg(OnCallCommentImg onCallCommentImg) {
        this.onCallCommentImg = onCallCommentImg;
    }

    public interface OnCallCommentImg {
        void onCallCheckImg(View view, int position, int fatherPosition);
    }

    static class ShopProductDetailsCommentImgAdapter extends BaseRecycleAdapter<String> implements View.OnClickListener {

        int layoutId;

        List<String> mDataList;

        int fatherPosition;

        OnCallCheckImg onCallCheckImg;

        public void setOnCallCheckImg(OnCallCheckImg onCallCheckImg, int fatherPosition) {
            this.onCallCheckImg = onCallCheckImg;
            this.fatherPosition = fatherPosition;
        }

        public ShopProductDetailsCommentImgAdapter(Context context, List<String> mDataList, int layoutId) {
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
            RelativeLayout itemShopProductDetailsCommentRl = holder.getView(R.id.item_shop_product_details_comment_rl);
            itemShopProductDetailsCommentRl.setTag(position);
            itemShopProductDetailsCommentRl.setOnClickListener(this);

            ImageView itemShopProductDetailsCommentImg = holder.getView(R.id.item_shop_product_details_comment_img);
            Glide.with(mContext).load(mDataList.get(position)).into(itemShopProductDetailsCommentImg);
        }

        @Override
        public void onClick(View view) {
            onCallCheckImg.onCallImg(view, (int) view.getTag(), fatherPosition);
        }

    }

}

package com.yuanbaogo.shopcart.main.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.shopcart.R;
import com.yuanbaogo.shopcart.main.call.OnShoppingCartChangeListener;
import com.yuanbaogo.shopcart.main.call.ShoppingCartBiz;
import com.yuanbaogo.shopcart.main.model.ShoppingCartBean;
import com.yuanbaogo.shopcart.main.view.ShopCartFragment;
import com.yuanbaogo.zui.dialog.SortDialogView;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.RadiuImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 购物车适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/26/21 3:17 PM
 * @Modifier:
 * @Modify:
 */
public class ShopCartAdapter extends BaseExpandableListAdapter {

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 数据
     */
    private List<ShoppingCartBean> mListGoods = new ArrayList<ShoppingCartBean>();

    /**
     * 回调
     */
    private OnShoppingCartChangeListener mChangeListener;

    /**
     * 是否全选
     */
    public boolean isSelectAll = false;

    public ShopCartAdapter(Context context) {
        mContext = context;
    }

    /**
     * 设置数据
     *
     * @param mListGoods
     */
    public void setList(List<ShoppingCartBean> mListGoods) {
        this.mListGoods = mListGoods;
        setSettleInfo();
    }

    /**
     * 购物车更改监听
     *
     * @param changeListener
     */
    public void setOnShoppingCartChangeListener(OnShoppingCartChangeListener changeListener) {
        this.mChangeListener = changeListener;
    }

    /**
     * 店铺点击事件
     *
     * @return
     */
    public View.OnClickListener getAdapterListener() {
        return listener;
    }

    /**
     * 分组
     *
     * @return
     */
    public int getGroupCount() {
        return mListGoods.size();
    }

    /**
     * 子类
     *
     * @param groupPosition
     * @return
     */
    public int getChildrenCount(int groupPosition) {
        return mListGoods.get(groupPosition).getGoods().size();
    }

    public Object getGroup(int groupPosition) {
        return mListGoods.get(groupPosition);
    }

    public Object getChild(int groupPosition, int childPosition) {
        return mListGoods.get(groupPosition).getGoods().get(childPosition);
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public boolean hasStableIds() {
        return false;
    }

    /**
     * 获取分组视图
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView == null) {
            holder = new GroupViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop_cart, parent, false);
            holder.tvGroup = convertView.findViewById(R.id.tvShopNameGroup);
            holder.ivCheckGroup = convertView.findViewById(R.id.ivCheckGroup);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        //设置模块名称
        Drawable drawable = null;
        if (mListGoods.get(groupPosition).getMerID().equals("1")) {
            drawable = mContext.getResources().getDrawable(R.mipmap.icon_group_ordinary);
            holder.tvGroup.setVisibility(View.GONE);
        } else if (mListGoods.get(groupPosition).getMerID().equals("2")) {
            drawable = mContext.getResources().getDrawable(R.mipmap.icon_group_joining_zone);
            holder.tvGroup.setVisibility(View.GONE);
        } else if (mListGoods.get(groupPosition).getMerID().equals("3")) {
            drawable = null;
            holder.tvGroup.setVisibility(View.VISIBLE);
        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tvGroup.setCompoundDrawables(drawable, null, null, null);
            holder.tvGroup.setCompoundDrawablePadding(10);
        }
        holder.tvGroup.setText(mListGoods.get(groupPosition).getMerchantName());
        holder.tvGroup.setOnClickListener(listener);

        //模块全选
        ShoppingCartBiz.checkItem(mListGoods.get(groupPosition).isGroupSelected(), holder.ivCheckGroup);
        holder.ivCheckGroup.setTag(groupPosition);
        holder.ivCheckGroup.setOnClickListener(listener);

        return convertView;
    }

    /**
     * 获取子类视图
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_shop_cart_shop, parent, false);
            holder.itemShopCartChildRecycler = convertView.findViewById(R.id.item_shop_cart_child_recycler);
            holder.itemShopCartChildRl = convertView.findViewById(R.id.item_shop_cart_child_rl);
            holder.itemShopCartChildRlTop = convertView.findViewById(R.id.item_shop_cart_child_rl_top);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        //专区标题是否显示
        if (mListGoods.get(groupPosition).getMerID().equals("2")) {
            holder.itemShopCartChildRlTop.setVisibility(View.VISIBLE);
        } else {
            holder.itemShopCartChildRlTop.setVisibility(View.GONE);
        }

        ShoppingCartBean.Goods goods = mListGoods.get(groupPosition).getGoods().get(childPosition);

        //最后一个商品设置背景色
        if (childPosition == mListGoods.get(groupPosition).getGoods().size() - 1) {
            holder.itemShopCartChildRl
                    .setBackground(
                            mContext.getResources().getDrawable(R.drawable.shape_gradient_bottom_bg_white_10));
        }

        //设置商品
        initRecycler(holder.itemShopCartChildRecycler,
                goods.getPrefectures(), groupPosition, childPosition);

        return convertView;
    }

    /**
     * 设置商品
     *
     * @param itemShopCartChildRecycler
     * @param prefectures
     * @param groupPosition
     * @param childPosition
     */
    private void initRecycler(RecyclerView itemShopCartChildRecycler,
                              ArrayList<ShoppingCartBean.Goods.Prefecture> prefectures,
                              int groupPosition, int childPosition) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        itemShopCartChildRecycler.setLayoutManager(linearLayoutManager);

        ChildAdapter childAdapter = new ChildAdapter(mContext, prefectures,
                R.layout.item_shop_cart_shop_child, groupPosition, childPosition);
        itemShopCartChildRecycler.setAdapter(childAdapter);

    }

    /**
     * 商品适配器
     */
    public class ChildAdapter extends BaseRecycleAdapter<ShoppingCartBean.Goods.Prefecture> {

        List<ShoppingCartBean.Goods.Prefecture> datas;

        int layoutId;

        int groupPosition;

        int childPosition;

        public ChildAdapter(Context context, List<ShoppingCartBean.Goods.Prefecture> datas,
                            int layoutId, int groupPosition, int childPosition) {
            super(context, datas, layoutId);
            this.datas = datas;
            this.layoutId = layoutId;
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        @Override
        public int getLayoutId() {
            return layoutId;
        }

        @Override
        public void onBindItemHolder(BaseViewHolder holder, int position) {

            RelativeLayout itemShopCartChildRl = holder.getView(R.id.item_shop_cart_child_rl);
            TextView itemShopCartChildTvGoodsName = holder.getView(R.id.item_shop_cart_child_tv_goods_name);
            ImageView itemShopCartChildImgCheckGood = holder.getView(R.id.item_shop_cart_child_img_check_good);
            RadiuImageView itemShopCartChildImgGoods = holder.getView(R.id.item_shop_cart_child_img_goods);
            TextView itemShopCartChildTvGoodsParam = holder.getView(R.id.item_shop_cart_child_tv_goods_param);
            TextView itemShopCartChildTvReturn = holder.getView(R.id.item_shop_cart_child_tv_return);
            TextView itemShopCartChildTvPriceNew = holder.getView(R.id.item_shop_cart_child_tv_price_new);
            TextView itemShopCartChildTvReduce = holder.getView(R.id.item_shop_cart_child_tv_reduce);
            TextView itemShopCartChildTvAdd = holder.getView(R.id.item_shop_cart_child_tv_add);
            TextView itemShopCartChildTvNum = holder.getView(R.id.item_shop_cart_child_tv_num);
            LinearLayout itemShopCartChildLlPrice = holder.getView(R.id.item_shop_cart_child_ll_price);

            ShoppingCartBean.Goods.Prefecture prefecture = datas.get(position);

            boolean isChildSelected = prefecture.isChildSelected();
            boolean isEditing = prefecture.isEditing();
            String priceNew = "¥" + prefecture.getUpdateGoodsPrice() / 100;
            String num = prefecture.getGoodsCount() + "";
            String pdtDesc = prefecture.getSkuName();
            String goodName = prefecture.getCreateGoodsName();

            itemShopCartChildImgCheckGood.setTag(groupPosition + "," + childPosition + "," + position);
            itemShopCartChildTvGoodsName.setText(goodName);
            itemShopCartChildTvNum.setText(num);
            itemShopCartChildTvGoodsParam.setText(pdtDesc);
            Glide.with(mContext).load(prefecture.getGoodsImgUrl()).into(itemShopCartChildImgGoods);

            Spannable string = new SpannableString(priceNew);
            // ¥
            string.setSpan(new AbsoluteSizeSpan(20), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            // 价格
            string.setSpan(new AbsoluteSizeSpan(35), 1, priceNew.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            // 显示
            itemShopCartChildTvPriceNew.setText(string);

            if (Integer.parseInt(num) <= 1) {
                itemShopCartChildTvReduce.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_shop_cart_tv_reduce_num_bg_un));
            }else{
                itemShopCartChildTvReduce.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_shop_cart_tv_reduce_num_bg));
            }

            ShoppingCartBiz.checkItem(isChildSelected, itemShopCartChildImgCheckGood);


            if (ShopCartFragment.isEdit) {
                itemShopCartChildImgCheckGood.setOnClickListener(listener);
            } else {
                if (!mListGoods.get(groupPosition).getMerID().equals("3")) {
                    itemShopCartChildImgCheckGood.setOnClickListener(listener);
                }
            }
            itemShopCartChildTvAdd.setOnClickListener(listener);
            itemShopCartChildTvAdd.setTag(prefecture);

            itemShopCartChildTvReduce.setOnClickListener(listener);
            itemShopCartChildTvReduce.setTag(prefecture);

            itemShopCartChildTvGoodsParam.setOnClickListener(listener);
            itemShopCartChildTvGoodsParam.setTag(prefecture);

            if (!TextUtils.isEmpty(prefecture.getInvalidReason())) {
                itemShopCartChildTvGoodsParam.setVisibility(View.GONE);
            } else {
                itemShopCartChildTvGoodsParam.setVisibility(View.VISIBLE);
            }

            if (Integer.valueOf(num) > 1) {
                itemShopCartChildTvReduce.setTextColor(mContext.getResources().getColor(R.color.color424242));
            }

            if (mListGoods.get(groupPosition).getMerID().equals("3")) {
                itemShopCartChildLlPrice.setVisibility(View.GONE);
            } else {
                itemShopCartChildLlPrice.setVisibility(View.VISIBLE);
                itemShopCartChildRl.setOnClickListener(listener);
                itemShopCartChildRl.setTag(prefecture);
            }

        }
    }

    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    /**
     * 点击事件
     */
    View.OnClickListener listener = new View.OnClickListener() {

        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.ivCheckGroup) {
                int groupPosition3 = Integer.parseInt(String.valueOf(view.getTag()));
                isSelectAll = ShoppingCartBiz.selectGroup(mListGoods, groupPosition3);
                selectAll();
                setSettleInfo();
                notifyDataSetChanged();
            } else if (id == R.id.item_shop_cart_child_img_check_good) {//子类 cb
                String tag = String.valueOf(view.getTag());
                if (tag.contains(",")) {
                    String s[] = tag.split(",");
                    int groupPosition = Integer.parseInt(s[0]);
                    int childPosition = Integer.parseInt(s[1]);
                    int position = Integer.parseInt(s[2]);
                    isSelectAll = ShoppingCartBiz.selectOne(mListGoods, groupPosition, childPosition, position);
                    selectAll();
                    setSettleInfo();
                    notifyDataSetChanged();
                }
            } else if (id == R.id.item_shop_cart_child_tv_add) {
                onGoodsParamCall.onGoodsAdd(view, (ShoppingCartBean.Goods.Prefecture) view.getTag());
            } else if (id == R.id.item_shop_cart_child_tv_reduce) {
                onGoodsParamCall.onGoodsReduce(view, (ShoppingCartBean.Goods.Prefecture) view.getTag());
            } else if (id == R.id.tvShopNameGroup) {
                ToastView.showToast(mContext, "商铺详情，暂未实现");
            } else if (id == R.id.item_shop_cart_child_tv_goods_param) {
                onGoodsParamCall.onGoodsParam(view, (ShoppingCartBean.Goods.Prefecture) view.getTag());
            } else if (id == R.id.item_shop_cart_child_rl) {
                onGoodsParamCall.onGoodsItem(view, (ShoppingCartBean.Goods.Prefecture) view.getTag());
            }
        }
    };

    /**
     * 全选
     */
    private void selectAll() {
        if (mChangeListener != null) {
            mChangeListener.onSelectItem(isSelectAll);
        }
    }

    /**
     * 删除或者选择商品之后，需要通知结算按钮，更新自己的数据
     */
    public void setSettleInfo() {
        String[] infos = ShoppingCartBiz.getShoppingCount(mListGoods);
        if (mChangeListener != null && infos != null) {
            mChangeListener.onDataChange(infos[0], infos[1], infos[2]);
        }
    }

    public void setSettleInfo1() {
        String[] infos = ShoppingCartBiz.getShoppingCount1(mListGoods);
        if (mChangeListener != null && infos != null) {
            mChangeListener.onDataChange(
                    infos[0],
                    infos[1],
                    infos[2]);
        }
    }

    class GroupViewHolder {

        TextView tvGroup;

        ImageView ivCheckGroup;

    }

    class ChildViewHolder {

        RelativeLayout itemShopCartChildRl;

        RelativeLayout itemShopCartChildRlTop;

        RecyclerView itemShopCartChildRecycler;

    }

    OnGoodsParamCall onGoodsParamCall;

    public void setOnGoodsParamCall(OnGoodsParamCall onGoodsParamCall) {
        this.onGoodsParamCall = onGoodsParamCall;
    }

    public interface OnGoodsParamCall {

        //商品规格
        void onGoodsParam(View view, ShoppingCartBean.Goods.Prefecture bean);

        void onGoodsAdd(View view, ShoppingCartBean.Goods.Prefecture bean);

        void onGoodsReduce(View view, ShoppingCartBean.Goods.Prefecture bean);

        void onGoodsItem(View view, ShoppingCartBean.Goods.Prefecture bean);
    }

}

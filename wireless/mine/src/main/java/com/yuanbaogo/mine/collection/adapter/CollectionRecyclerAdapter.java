package com.yuanbaogo.mine.collection.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.collection.model.CollectionItem;
import com.yuanbaogo.mine.collection.model.MoveShopCartModel;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.RadiuImageView;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CollectionRecyclerAdapter extends BaseRecycleAdapter<CollectionItem> {

    private static final String TAG = "CollectionRecyclerAdapter";
    // 普通状态
    public static final int NORMAL = 1;
    // 选择状态
    public static final int SELECTED = 2;
    public HashSet<String> mSelectedItems = new HashSet<>();
    private RelativeLayout mCollectionItemRl;
    private CheckBox mCollectionItemCbSelected;
    private RadiuImageView mCollectionItemIvImg;
    private TextView mCollectionItemTvName;
    private TextView mCollectionItemTvType;
    private TextView mCollectionItemTvMoney;
    private ImageView mCollectionItemCar;
    private View mCollectionItemViewOffShelf;
    private TextView mCollectionItemTvOffShelf;
    private TextView collectionItemTvsales;
    private int flag = NORMAL;
    private OnRefreshCollection mOnRefreshCollection;

    public CollectionRecyclerAdapter(Context context, List<CollectionItem> mDataList) {
        this(context, mDataList, 0);
    }

    public CollectionRecyclerAdapter(Context context, List<CollectionItem> mDataList, int layoutId) {
        super(context, mDataList, layoutId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_layout_collection_item;
    }

    public void setOnRefreshCollection(OnRefreshCollection onRefreshCollection) {
        this.mOnRefreshCollection = onRefreshCollection;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemHolder(BaseViewHolder convertView, int position) {
        CollectionItem collectionItem = getDataList().get(position);
        mCollectionItemRl = convertView.getView(R.id.collection_item_rl);
        mCollectionItemCbSelected = convertView.getView(R.id.collection_item_cb_selected);
        mCollectionItemIvImg = convertView.getView(R.id.collection_item_iv_img);
        mCollectionItemTvName = convertView.getView(R.id.collection_item_tv_name);
        mCollectionItemTvType = convertView.getView(R.id.collection_item_tv_type);
        mCollectionItemTvMoney = convertView.getView(R.id.collection_item_tv_money);
        mCollectionItemCar = convertView.getView(R.id.collection_item_car);
        mCollectionItemViewOffShelf = convertView.getView(R.id.collection_item_view_off_shelf);
        mCollectionItemTvOffShelf = convertView.getView(R.id.collection_item_tv_off_shelf);
        collectionItemTvsales = convertView.getView(R.id.collection_item_tv_sales);
        mCollectionItemCar.setOnClickListener(v -> {
            // 加入购物车
            MoveShopCartModel moveShopCartModel = new MoveShopCartModel();
            moveShopCartModel.setAreaId(Integer.parseInt(collectionItem.getAreaId() == null ? "0" : collectionItem.getAreaId()));
            moveShopCartModel.setCartType(0);
            moveShopCartModel.setCreateBy(collectionItem.getCreateBy());
            moveShopCartModel.setCreateGoodsName(collectionItem.getGoodsName());
            moveShopCartModel.setCreateGoodsPrice(collectionItem.getSalePrice());
            moveShopCartModel.setGoodsCount(1);
            moveShopCartModel.setGoodsImgUrl(collectionItem.getImgUrl());
            moveShopCartModel.setGoodsType(collectionItem.getGoodsType());
            moveShopCartModel.setSkuId(collectionItem.getSkuId());
            moveShopCartModel.setSpuId(collectionItem.getSpuId());
            moveShopCartModel.setSkuName("");
            moveShopCart(moveShopCartModel);
        });
        if (flag == SELECTED) {
            mCollectionItemCbSelected.setVisibility(View.VISIBLE);
        } else {
            mCollectionItemCbSelected.setVisibility(View.GONE);
        }
        if (collectionItem.isIsShelf()) {
            mCollectionItemViewOffShelf.setVisibility(View.GONE);
            mCollectionItemTvOffShelf.setVisibility(View.GONE);
            mCollectionItemRl.setOnClickListener(v -> {
                // 点击进入商品详情
                RouterHelper.toShopProductDetails(collectionItem.getSpuId() + "", collectionItem.getGoodsType(), null, null, true);
            });
            mCollectionItemTvMoney.setVisibility(View.VISIBLE);
            collectionItemTvsales.setVisibility(View.VISIBLE);
        } else {
            mCollectionItemViewOffShelf.setVisibility(View.VISIBLE);
            mCollectionItemTvOffShelf.setVisibility(View.VISIBLE);
            mCollectionItemTvMoney.setVisibility(View.GONE);
            collectionItemTvsales.setVisibility(View.GONE);
        }
        mCollectionItemCbSelected.setChecked(mSelectedItems.contains(collectionItem.getId()));
        mCollectionItemCbSelected.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        mSelectedItems.add(collectionItem.getId());
                    } else {
                        mSelectedItems.remove(collectionItem.getId());
                    }
                    if (mOnRefreshCollection != null) {
                        mOnRefreshCollection.onRefresh();
                    }
                }
        );
        Glide.with(mCollectionItemIvImg).load(collectionItem.getImgUrl()).into(mCollectionItemIvImg);
        mCollectionItemTvName.setText(collectionItem.getGoodsName());
//        initGoodsType(collectionItem.getGoodsType());
        mCollectionItemTvMoney.setText("¥ " + collectionItem.getSalePrice() / 100);
        String keywordList = TextUtils.join(", ", collectionItem.getKeywordList());
        mCollectionItemTvType.setText(keywordList);
        collectionItemTvsales.setText("已售" + collectionItem.getSales() + "件");
    }

    private void initGoodsType(int goodsType) {
        if (goodsType == 0) {
            mCollectionItemTvType.setText("普通商品");
        } else if (goodsType == 1) {
            mCollectionItemTvType.setText("五百专区商品");
        } else if (goodsType == 2) {
            mCollectionItemTvType.setText("五千专区商品");
        } else if (goodsType == 3) {
            mCollectionItemTvType.setText("五万专区商品");
        } else if (goodsType == 4) {
            mCollectionItemTvType.setText("一城一品");
        } else if (goodsType == 5) {
            mCollectionItemTvType.setText("秒杀商品");
        } else if (goodsType == 7) {
            mCollectionItemTvType.setText("主题专区");
        }
    }

    public String getId(int position) {
        return getDataList().get(Math.max(position, 0)).getId();
    }

    public void moveShopCart(MoveShopCartModel model) {
        Map<String, Object> params = JSON.parseObject(JSON.toJSONString(model));
        NetWork.getInstance().post(mContext, ChildUrl.MOVE_SHOP_CART, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "moveShopCart onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL) {
                    return;
                }
                ToastView.showToast("加入购物车成功");
            }

            @Override
            public void onFailure(Throwable var1) {
                OrderNetworkUtils.disposeError(var1);
            }
        });
    }


    /**
     * 获取当前状态
     *
     * @return /
     */
    public int getFlag() {
        return flag;
    }

    /**
     * 设置当前状态
     *
     * @param flag NORMAL为普通状态  SELECTED为选择状态
     */
    public void setFlag(int flag) {
        this.flag = flag;
        notifyDataSetChanged();
    }

    /**
     * 当列表中手动刷新回调
     */
    public interface OnRefreshCollection {
        void onRefresh();
    }

}

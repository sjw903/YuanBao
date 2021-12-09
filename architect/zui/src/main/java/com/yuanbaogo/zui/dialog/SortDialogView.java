package com.yuanbaogo.zui.dialog;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.commonlib.router.model.OrderNumBean;
import com.yuanbaogo.zui.dialog.model.SkuBean;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;
import com.yuanbaogo.zui.view.SortFormatView;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 选择商品类型弹窗
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 5:01 PM
 * @Modifier:
 * @Modify:
 */
public class SortDialogView extends DialogsFragment
        implements View.OnClickListener,
        SortFormatView.OnCallSelect {

    View mView;

    /**
     * 关闭弹窗
     */
    ImageView dialogSortImgDelete;

    /**
     * 减去购买数量
     */
    TextView dialogSortTvLessNum;

    /**
     * 购买数量
     */
    TextView dialogSortTvBuyNum;

    int buyNum = 1;

    /**
     * 增加购买数量
     */
    TextView dialogSortTvAddNum;

    /**
     * 确定
     */
    Button dialogSortBut;

    /**
     * 区分确认按钮功能
     * 1 分类查看/立即购买  2 加入购物车 3 修改购物车规格  11 12 短视频带货|直播带货 立即购买
     */
    int type = 1;

    /**
     * 规格model
     */
    SkuBean skuBean;

    /**
     * 商品价格
     */
    TextView dialogSortTvPrice;

    /**
     * 商品库存
     */
    TextView dialogSortTvStock;

    /**
     * 商品图片
     */
    ImageView dialogSortImgIcon;

    /**
     * 库存
     */
    int mStock = 0;

    /**
     * 规格布局
     */
    LinearLayout dialogSortLlAddView;

    String[] mSelectString;

    String mLot;

    String mBizId;

    String skuName;

    String[] skuNameSelect;

    public void setBean(SkuBean skuBean, boolean isFirst) {
        this.skuBean = skuBean;
        mStock = skuBean.getStock();
        if (type == 3) {
            buyNum = skuBean.getGoodsCount();
            skuName = skuBean.getSkuName();
            skuNameSelect = skuName.split(",");
            if (isFirst) {
                for (int i = 0; i < skuNameSelect.length; i++) {
                    for (int j = 0; j < skuBean.getSpecList().size(); j++) {
                        for (int p = 0; p < skuBean.getSpecList().get(j).getSpecValue().size(); p++) {
                            if (skuBean.getSpecList().get(j).getSpecValue().get(p).getValue().equals(skuName.trim())) {
                                initSelect(i, skuBean.getSpecList().get(j).getSpecValue().get(p).getIndex() + "", false);
                            }
                        }
                    }
                }
            }
        } else {
            if (mStock > 0) {
                buyNum = 1;
                dialogSortTvAddNum.setBackground(getResources().getDrawable(R.mipmap.icon_shop_cart_tv_add_num_bg));
            } else {
                buyNum = 0;
                dialogSortTvAddNum.setBackground(getResources().getDrawable(R.mipmap.icon_shop_cart_tv_add_num_bg_un));
            }
        }
        Glide.with(getActivity()).load(skuBean.getImgUrl()).into(dialogSortImgIcon);
        if (isFirst){
            dialogSortTvPrice.setText("￥" + skuBean.getPrice() / 100);
        }

        dialogSortTvStock.setText("库存" + skuBean.getStock() + "件");
        if (buyNum <= 1) {
            dialogSortTvLessNum.setBackground(getResources().getDrawable(R.mipmap.icon_shop_cart_tv_reduce_num_bg_un));
        } else {
            dialogSortTvLessNum.setBackground(getResources().getDrawable(R.mipmap.icon_shop_cart_tv_reduce_num_bg));
        }
        dialogSortTvBuyNum.setText(buyNum + "");
    }

    public void setBean2(SkuBean skuBean) {
        this.skuBean = skuBean;
        dialogSortTvStock.setText("库存" + skuBean.getStock() + "件");
        mStock = skuBean.getStock();
        if (mStock > 0) {
            buyNum = 1;
        } else {
            buyNum = 0;
        }
    }

    public SortDialogView(int type, SkuBean skuBean, String mLot, String mBizId) {
        this.type = type;
        this.skuBean = skuBean;
        this.mLot = mLot;
        this.mBizId = mBizId;
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_sort_view;
    }

    @Override
    protected void intViews(View view) {
        this.mView = view;
        dialogSortImgDelete = mView.findViewById(R.id.dialog_sort_img_delete);
        dialogSortTvLessNum = mView.findViewById(R.id.dialog_sort_tv_less_num);
        dialogSortTvBuyNum = mView.findViewById(R.id.dialog_sort_tv_buy_num);
        dialogSortTvAddNum = mView.findViewById(R.id.dialog_sort_tv_add_num);
        dialogSortBut = mView.findViewById(R.id.dialog_sort_but);
        dialogSortTvPrice = mView.findViewById(R.id.dialog_sort_tv_price);
        dialogSortTvStock = mView.findViewById(R.id.dialog_sort_tv_stock);
        dialogSortImgIcon = mView.findViewById(R.id.dialog_sort_img_icon);
        dialogSortLlAddView = mView.findViewById(R.id.dialog_sort_ll_add_view);
        startUpAnimation(mView);

    }

    @Override
    protected void setTexts() {
        if (skuBean.getSpecList() != null && skuBean.getSpecList().size() != 0) {
            mSelectString = new String[skuBean.getSpecList().size()];
        }
        setBean(skuBean, true);
        addView();
    }

    @Override
    protected void setOnClicks() {
        dialogSortImgDelete.setOnClickListener(this);
        dialogSortTvLessNum.setOnClickListener(this);
        dialogSortTvAddNum.setOnClickListener(this);
        dialogSortBut.setOnClickListener(this);
    }

    public int getGravity() {
        return Gravity.BOTTOM;
    }

    /**
     * 向容器中添加规格布局
     */
    public void addView() {
        if (skuBean == null || skuBean.getSpecList() == null || skuBean.getSpecList().size() == 0) {
            return;
        }
        for (int i = 0; i < skuBean.getSpecList().size(); i++) {
            SortFormatView sortFormatView = new SortFormatView(getActivity());
            if (skuNameSelect != null && skuNameSelect.length != 0) {
                sortFormatView.setSkuSpecBean(skuBean.getSpecList().get(i), i, skuNameSelect[i]);
            } else {
                sortFormatView.setSkuSpecBean(skuBean.getSpecList().get(i), i);
            }
            sortFormatView.setOnCallSelect(this);
            // 调用一个参数的addView方法
            dialogSortLlAddView.addView(sortFormatView);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dialog_sort_img_delete) {
            startDownAnimation(mView);
        } else if (id == R.id.dialog_sort_tv_less_num) {
            if (mSelectString != null) {
                for (int i = 0; i < mSelectString.length; i++) {
                    if (TextUtils.isEmpty(mSelectString[i])) {
                        ToastView.showToast("请选择" + skuBean.getSpecList().get(i).getSpecName());
                        return;
                    }
                }
            }
            if (mStock != 0) {
                if (buyNum > 1) {//数量 大于  1
                    buyNum--;
                    dialogSortTvBuyNum.setText(buyNum + "");
                    if (buyNum > 1) {
                        dialogSortTvLessNum.setBackground(getResources().getDrawable(R.mipmap.icon_shop_cart_tv_reduce_num_bg));
                    } else {
                        dialogSortTvLessNum.setBackground(getResources().getDrawable(R.mipmap.icon_shop_cart_tv_reduce_num_bg_un));
                    }
                    if (buyNum < mStock) {
                        dialogSortTvAddNum.setBackground(getResources().getDrawable(R.mipmap.icon_shop_cart_tv_add_num_bg));
                    }
                } else {//数量 等于 1
                    ToastView.showToast(getActivity(), "受不了了，宝贝数量不能再减了哦");
                    dialogSortTvLessNum.setBackground(getResources().getDrawable(R.mipmap.icon_shop_cart_tv_reduce_num_bg_un));
                    return;
                }
            }
        } else if (id == R.id.dialog_sort_tv_add_num) {
            if (mSelectString != null) {
                for (int i = 0; i < mSelectString.length; i++) {
                    if (TextUtils.isEmpty(mSelectString[i])) {
                        ToastView.showToast("请选择" + skuBean.getSpecList().get(i).getSpecName());
                        return;
                    }
                }
            }
            if (buyNum < mStock) {//数量 小于  库存
                buyNum++;
                dialogSortTvBuyNum.setText(buyNum + "");
                if (buyNum > 1) {
                    dialogSortTvLessNum.setBackground(getResources().getDrawable(R.mipmap.icon_shop_cart_tv_reduce_num_bg));
                }
                if (buyNum >= mStock) {
                    dialogSortTvAddNum.setBackground(getResources().getDrawable(R.mipmap.icon_shop_cart_tv_add_num_bg_un));
                }
            } else {//数量 等于 库存
                ToastView.showToast(getActivity(), "受不了了，宝贝数量不能再加了哦");
                dialogSortTvAddNum.setBackground(getResources().getDrawable(R.mipmap.icon_shop_cart_tv_add_num_bg_un));
                return;
            }
        } else if (id == R.id.dialog_sort_but) {
            if (buyNum > mStock) {
                ToastView.showToast("库存不足");
                return;
            }
            initSort();
        }
    }

    ArrayList<String> skuIdList = new ArrayList<>();

    OrderNumBean orderNumBean = new OrderNumBean();

    private void initSort() {
        if (!UserStore.isLogin()) {
            RouterHelper.toLogin();
            return;
        }
        if (mSelectString != null) {
            for (int i = 0; i < mSelectString.length; i++) {
                if (TextUtils.isEmpty(mSelectString[i])) {
                    ToastView.showToast("请选择" + skuBean.getSpecList().get(i).getSpecName());
                    return;
                }
            }
        }
        ArrayList<OrderNumBean> numList = new ArrayList<>();
        orderNumBean.setGoodsCount(buyNum);
        numList.add(orderNumBean);
        if (!UserStore.isLogin() || buyNum == 0) {
            return;
        }
        if (type == 1) {
            RouterHelper.toConfirmOrder(type, skuIdList, numList, mLot, mBizId);
            dismiss();
        } else if (type == 2) {
            onCallSort.onCallAddShoppingCart(orderNumBean);
        } else if (type == 3) {
            onCallSort.onCallModifySku(orderNumBean);
        } else if (type == TagParameteBean.liveBringGoods || type == TagParameteBean.videoBringGoods) {
            RouterHelper.toConfirmOrder(type, skuIdList, numList, mLot, mBizId);
            dismiss();
        }
    }

    /**
     * 匹配所选 颜色_尺寸 请求接口获取库存
     */
    @Override
    public void onSelect(int type, String select) {
        initSelect(type, select, true);
    }

    public void initSelect(int type, String select, boolean isStock) {
        mSelectString[type] = select;
        String str = String.join("_", mSelectString);
        skuIdList = new ArrayList<>();
        for (int i = 0; i < skuBean.getSkuList().size(); i++) {
            if (str.equals(skuBean.getSkuList().get(i).getIndexes())) {
                dialogSortTvPrice.setText("￥" + skuBean.getSkuList().get(i).getSalePrice() / 100);
                skuIdList.add(skuBean.getSkuList().get(i).getSkuId());
                String skuName = Arrays.toString(skuBean.getSkuList().get(i).getSkuIndexesName());
                orderNumBean.setSkuId(skuBean.getSkuList().get(i).getSkuId())
                        .setSkuName(skuName.substring(1, skuName.length() - 1))
                        .setCreateGoodsPrice(skuBean.getSkuList().get(i).getSalePrice())
                        .setSpuId(skuBean.getSkuList().get(i).getSpuId());
                if (isStock) {
                    getStock(skuBean.getSkuList().get(i).getSkuId());
                }
            }
        }
    }

    OnCallSort onCallSort;

    public void setOnCallSort(OnCallSort onCallSort) {
        this.onCallSort = onCallSort;
    }

    public interface OnCallSort {
        void onCallSort(String skuId);

        void onCallAddShoppingCart(OrderNumBean bean);

        void onCallModifySku(OrderNumBean bean);
    }

    /**
     * 获取规格 库存
     *
     * @param skuId
     */
    public void getStock(String skuId) {
        Map<String, Object> params = new HashMap<>();
        params.put("skuId", skuId);
        NetWork.getInstance().get(getContext(),
                ChildUrl.STOCK,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        if (code == 200 && getView() != null) {
                            setBean(skuBean.setStock(Integer.parseInt(bean)), false);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {

                        }
                    }
                },false);
    }

}

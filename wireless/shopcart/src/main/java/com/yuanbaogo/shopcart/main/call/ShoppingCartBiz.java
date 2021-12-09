package com.yuanbaogo.shopcart.main.call;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanbaogo.shopcart.R;
import com.yuanbaogo.shopcart.main.model.ShopCartBean;
import com.yuanbaogo.shopcart.main.model.ShoppingCartBean;
import com.yuanbaogo.shopcart.main.utils.DecimalUtil;
import com.yuanbaogo.shopcart.main.view.ShopCartFragment;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 购物车业务
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 8:50 AM
 * @Modifier:
 * @Modify:
 */
public class ShoppingCartBiz {

    /**
     * 选择全部，点下全部按钮，改变所有商品选中状态
     */
    public static boolean selectAll(List<ShoppingCartBean> list, boolean isSelectAll, ImageView ivCheck) {
        isSelectAll = !isSelectAll;
        ShoppingCartBiz.checkItem(isSelectAll, ivCheck);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getGoods().size(); j++) {
                for (int p = 0; p < list.get(i).getGoods().get(j).getPrefectures().size(); p++) {
                    if (ShopCartFragment.isEdit) {
                        list.get(i).setGroupSelected(isSelectAll);
                        list.get(i).getGoods().get(j).getPrefectures().get(p).setChildSelected(isSelectAll);
                    } else {
                        if (list.get(i).getMerID().equals("3")) {//全选时 失效产品不能被选中
                            list.get(i).getGoods().get(j).getPrefectures().get(p).setChildSelected(false);
                            list.get(i).setGroupSelected(false);
                        } else {
                            list.get(i).getGoods().get(j).getPrefectures().get(p).setChildSelected(isSelectAll);
                            list.get(i).setGroupSelected(isSelectAll);
                        }
                    }
                }
            }
        }
        return isSelectAll;
    }

    /**
     * 族内的所有组，是否都被选中，即全选
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllGroup(List<ShoppingCartBean> list) {
        for (int i = 0; i < list.size(); i++) {
            boolean isSelectGroup = list.get(i).isGroupSelected();
            if (!isSelectGroup) {
                return false;
            }
        }
        return true;
    }

    /**
     * 组内所有子选项是否全部被选中
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllChild(List<ShoppingCartBean.Goods> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getPrefectures().size(); j++) {
                boolean isSelectGroup = list.get(i).getPrefectures().get(j).isChildSelected();
                if (!isSelectGroup) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 单选一个，需要判断整个组的标志，整个族的标志，是否被全选，取消，则
     * 除了选择全部和选择单个可以单独设置背景色，其他都是通过改变值，然后notify；
     *
     * @param list
     * @param groudPosition
     * @param childPosition
     * @return 是否选择全部
     */
    public static boolean selectOne(List<ShoppingCartBean> list, int groudPosition, int childPosition, int position) {
        boolean isSelectAll;
        boolean isSelectedOne = !(list.get(groudPosition).getGoods().get(childPosition).getPrefectures().get(position).isChildSelected());
        list.get(groudPosition).getGoods().get(childPosition).getPrefectures().get(position).setChildSelected(isSelectedOne);//单个图标的处理
        boolean isSelectCurrentGroup = isSelectAllChild(list.get(groudPosition).getGoods());
        list.get(groudPosition).setGroupSelected(isSelectCurrentGroup);//组图标的处理
        isSelectAll = isSelectAllGroup(list);
        return isSelectAll;
    }

    public static boolean selectGroup(List<ShoppingCartBean> list, int groudPosition) {
        boolean isSelectAll;
        boolean isSelected = !(list.get(groudPosition).isGroupSelected());
        list.get(groudPosition).setGroupSelected(isSelected);
        for (int i = 0; i < list.get(groudPosition).getGoods().size(); i++) {
            for (int j = 0; j < list.get(i).getGoods().get(i).getPrefectures().size(); j++) {
                list.get(groudPosition).getGoods().get(i).setChildSelected(isSelected);
            }
        }
        isSelectAll = isSelectAllGroup(list);
        return isSelectAll;
    }

    /**
     * 勾与不勾选中选项
     *
     * @param isSelect 原先状态
     * @param ivCheck
     * @return 是否勾上，之后状态
     */
    public static boolean checkItem(boolean isSelect, ImageView ivCheck) {
        if (isSelect) {
            ivCheck.setImageResource(R.mipmap.icon_cancel_check);
        } else {
            ivCheck.setImageResource(R.mipmap.icon_cancel_check_null);
        }
        return isSelect;
    }

    /**=====================上面是界面改动部分，下面是数据变化部分=========================*/

    /**
     * 获取结算信息，肯定需要获取总价和数量，但是数据结构改变了，这里处理也要变；
     *
     * @return 0=选中的商品数量；1=选中的商品总价; 2=商品总数量
     */
    public static String[] getShoppingCount(List<ShoppingCartBean> listGoods) {
        String[] infos = new String[3];
        String selectedCount = "0";
        String selectedMoney = "0";
        int selectedNum = 0;
        for (int i = 0; i < listGoods.size(); i++) {
            for (int j = 0; j < listGoods.get(i).getGoods().size(); j++) {
                for (int p = 0; p < listGoods.get(i).getGoods().get(j).getPrefectures().size(); p++) {
                    boolean isSelectd = listGoods.get(i).getGoods().get(j).getPrefectures().get(p).isChildSelected();
                    if (isSelectd) {
                        String price = listGoods.get(i).getGoods().get(j).getPrefectures().get(p).getCreateGoodsPrice() + "";
                        String num = listGoods.get(i).getGoods().get(j).getPrefectures().get(p).getGoodsCount() + "";
                        String countMoney = DecimalUtil.multiply(price, num);
                        selectedMoney = DecimalUtil.add(selectedMoney, countMoney);
                        selectedCount = DecimalUtil.add(selectedCount, "1");
                    }
                    selectedNum = selectedNum + listGoods.get(i).getGoods().get(j).getPrefectures().get(p).getGoodsCount();
                }
            }
        }
        infos[0] = selectedCount;
        infos[1] = selectedMoney;
        infos[2] = String.valueOf(selectedNum);
        return infos;
    }

    public static String[] getShoppingCount1(List<ShoppingCartBean> listGoods) {
        String[] infos = new String[3];
        String selectedCount = "0";
        String selectedMoney = "0";
        int selectedNum = 0;
        for (int i = 0; i < listGoods.size(); i++) {
            for (int j = 0; j < listGoods.get(i).getGoods().size(); j++) {
                for (int p = 0; p < listGoods.get(i).getGoods().get(j).getPrefectures().size(); p++) {
                    if (i == listGoods.size() - 1) {//全选时 失效产品不能被选中
                        listGoods.get(i).getGoods().get(j).getPrefectures().get(p).setChildSelected(false);
                    }
                    boolean isSelectd = listGoods.get(i).getGoods().get(j).getPrefectures().get(p).isChildSelected();
                    if (isSelectd) {
                        String price = listGoods.get(i).getGoods().get(j).getPrefectures().get(p).getCreateGoodsPrice() + "";
                        String num = listGoods.get(i).getGoods().get(j).getPrefectures().get(p).getGoodsCount() + "";
                        String countMoney = DecimalUtil.multiply(price, num);
                        selectedMoney = DecimalUtil.add(selectedMoney, countMoney);
                        selectedCount = DecimalUtil.add(selectedCount, "1");
                    }
                    selectedNum = selectedNum + listGoods.get(i).getGoods().get(j).getPrefectures().get(p).getGoodsCount();
                }
            }
        }
        infos[0] = selectedCount;
        infos[1] = selectedMoney;
        infos[2] = String.valueOf(selectedNum);
        return infos;
    }

    public static boolean hasSelectedGoods(List<ShoppingCartBean> listGoods) {
        String count = getShoppingCount(listGoods)[0];
        if ("0".equals(count)) {
            return false;
        }
        return true;
    }

    /**
     * 增减数量，操作通用，数据不通用
     *
     * @param context
     * @param isPlus     true:增加数据  false：减少数据
     * @param prefecture 商品信息
     * @param tvAdd      + 控件
     * @param tvNum      数量 控件
     * @param tvReduce   - 控件
     */
    public static void addOrReduceGoodsNum(Context context, boolean isPlus,
                                           ShoppingCartBean.Goods.Prefecture prefecture,
                                           TextView tvAdd, TextView tvNum, TextView tvReduce) {
        String currentNum = prefecture.getGoodsCount() + "";
        String num = "1";
        if (isPlus) {
            num = String.valueOf(Integer.parseInt(currentNum) + 1);
            tvAdd.setBackground(context.getResources().getDrawable(R.mipmap.icon_shop_cart_tv_add_num_bg));
            tvReduce.setBackground(context.getResources().getDrawable(R.mipmap.icon_shop_cart_tv_reduce_num_bg));
        } else {
            int i = Integer.parseInt(currentNum);
            if (i > 1) {
                num = String.valueOf(i - 1);
                if (Integer.valueOf(num) == 1) {
                    tvReduce.setBackground(context.getResources().getDrawable(R.mipmap.icon_shop_cart_tv_reduce_num_bg_un));
                }
            } else {
                num = "1";
                tvReduce.setBackground(context.getResources().getDrawable(R.mipmap.icon_shop_cart_tv_reduce_num_bg_un));
            }
        }
        tvNum.setText(num);
        prefecture.setGoodsCount(Integer.parseInt(num));
    }

    /**
     * 修改商品规格
     */
    public static void updateGoodsSku(ShopCartBean bean,
                                      ShoppingCartBean.Goods.Prefecture prefecture,
                                      TextView tvGoodsParam) {
        tvGoodsParam.setText(bean.getSkuName());
        prefecture.setSkuName(bean.getSkuName());
        prefecture.setSkuId(bean.getSkuId());
    }

}

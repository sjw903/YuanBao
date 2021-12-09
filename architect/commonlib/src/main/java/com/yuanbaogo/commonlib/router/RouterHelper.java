package com.yuanbaogo.commonlib.router;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yuanbaogo.commonlib.bean.RecommendVideoBean;
import com.yuanbaogo.commonlib.router.model.AddressOrderBean;
import com.yuanbaogo.commonlib.router.model.OrderNumBean;
import com.yuanbaogo.commonlib.router.model.SearchMerchandiseBean;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.router.YBRouter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/5/21 9:46 AM
 * @Modifier:
 * @Modify:
 */
public class RouterHelper {

    /**
     * 登录页
     */
    public static void toLogin() {
        ARouter.getInstance().build(YBRouter.LOGIN_ACTIVITY).navigation();
    }

    public static void toLogin(Fragment fragment, int requestCode) {
        Postcard postcard = ARouter.getInstance()
                .build(YBRouter.LOGIN_ACTIVITY);
        LogisticsCenter.completion(postcard);
        Intent intent = new Intent(fragment.getActivity(), postcard.getDestination());
        intent.putExtras(postcard.getExtras());
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 换绑手机号
     */
    public static void toChangePhone() {
        ARouter.getInstance().build(YBRouter.CHANGE_PHONE_ACTIVITY).navigation();
    }

    /**
     * 用户协议
     */
    public static void toLoginPactn() {
        ARouter.getInstance().build(YBRouter.LOGIN_PACT_ACTIVITY).navigation();
    }

    /**
     * web页
     *
     * @param url 显示url
     */
    public static void toWeb(String url) {
        ARouter.getInstance().build(YBRouter.WEB_ACTIVITY)
                .withString(YBRouter.WebActivityParams.url, url)
//                .withString(YBRouter.WebActivityParams.title, title)
//        AUTHENTICATE
                .navigation();
    }

    /**
     * 注销账户
     */
    public static void toCancellation() {
        ARouter.getInstance().build(YBRouter.CANCELLATION_ACTIVITY).navigation();
    }

    /**
     * 一城一品
     */
    public static void toShopOneCity() {
        ARouter.getInstance().build(YBRouter.SHOP_ONE_CITY_ACTIVITY).navigation();
    }

    /**
     * 秒杀
     */
    public static void toShopSpick() {
        ARouter.getInstance().build(YBRouter.SHOP_SPICK_ACTIVITY).navigation();
    }

    /**
     * 拼团
     */
    public static void toShopJoinGroup() {
        ARouter.getInstance().build(YBRouter.SHOP_JOIN_GROUP_ACTIVITY).navigation();
    }

    /**
     * 幸运拼团
     */
    public static void toShopLuckyFight() {
        ARouter.getInstance().build(YBRouter.SHOP_LUCKY_FIGHT_ACTIVITY).navigation();
    }

    /**
     * 商品详情
     * 商品SpuID  直播  商品批次  直播ID号
     *
     * @param spuId
     * @param type
     */
    public static void toShopProductDetails(String spuId, int type, String lot, String bizId, boolean isShow) {
        ARouter.getInstance().build(YBRouter.SHOP_PRODUCT_DETAILS_ACTIVITY)
                .withSerializable(YBRouter.ProductDetailsActivityParams.spuId, spuId)
                .withSerializable(YBRouter.ProductDetailsActivityParams.type, type)
                .withSerializable(YBRouter.ProductDetailsActivityParams.lot, lot)
                .withSerializable(YBRouter.ProductDetailsActivityParams.bizId, bizId)
                .withSerializable(YBRouter.ProductDetailsActivityParams.isShow, isShow)
                .navigation();
    }

    /**
     * 商品列表
     */
    public static void toShopProductDetailsList(SearchMerchandiseBean search) {
        ARouter.getInstance().build(YBRouter.SHOP_PRODUCT_DETAILS_LIST_ACTIVITY)
                .withSerializable(YBRouter.ShopSearchActivityParams.searchMerchandiseBean, search)
                .navigation();
    }

    /**
     * 商品搜索 (首页  一城一品  拼团  秒杀 元宝国际)
     *
     * @param search 搜索内容
     */
    public static void toShopSearch(SearchMerchandiseBean search) {
        ARouter.getInstance().build(YBRouter.SHOP_SEARCH_ACTIVITY)
                .withSerializable(YBRouter.ShopSearchActivityParams.searchMerchandiseBean, search)
                .navigation();
    }

    /**
     * 商品列表搜索框点击跳转搜索页面 需要回调 （五百专区  五千专区  五万专区）
     *
     * @param search 搜索内容
     */
    public static void toShopSearchResult(Activity activity, SearchMerchandiseBean search, int requestCode) {
        ARouter.getInstance().build(YBRouter.SHOP_SEARCH_ACTIVITY)
                .withSerializable(YBRouter.ShopSearchActivityParams.searchMerchandiseBean, search)
                .navigation(activity, requestCode);
    }

    /**
     * 搜索页面 返回 商品列表页面
     *
     * @param activity
     * @param search
     */
    public static void toShopProductDetailsListResult(Activity activity, SearchMerchandiseBean search) {
        Intent intent = new Intent();
        intent.putExtra("searchBean", search);
        activity.setResult(100, intent);
        activity.finish();
    }

    /**
     * 修改账号
     */
    public static void toModifyAccount() {
        ARouter.getInstance().build(YBRouter.MODIFY_ACCOUNT_ACTIVITY).navigation();
    }

    /**
     * 带货
     */
    public static void toBringGoods(Activity activity, int requestCode, boolean selectMode) {
        ARouter.getInstance().build(YBRouter.BRING_GOODS_ACTIVITY)
                .withBoolean("selectMode", selectMode)
                .navigation(activity, requestCode);
    }

    public static void toMain() {
        ARouter.getInstance().build(YBRouter.MAIN_ACTIVITY)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();
    }

    /**
     * 我的资产
     */
    public static void toAssets() {
        if (!UserStore.isLogin()) {
            toLogin();
            return;
        }
        ARouter.getInstance().build(YBRouter.ASSETS_ACTIVITY).navigation();
    }

    /**
     * 元吧积分
     */
    public static void toYBIntegral() {
        ARouter.getInstance().build(YBRouter.YB_INTEGRAL_ACTIVITY).navigation();
    }

    /**
     * 积分充值
     */
    public static void toIntegralRecharge() {
        ARouter.getInstance().build(YBRouter.INTEGRAL_RECHARGE_ACTIVITY).navigation();
    }

    /**
     * 零钱
     */
    public static void toDib() {
        ARouter.getInstance().build(YBRouter.DIB_ACTIVITY).navigation();
    }

    /**
     * 提现
     */
    public static void toWithdraw(double dib) {
        ARouter.getInstance().build(YBRouter.WITHDRAW_ACTIVITY)
                .withDouble("dib", dib)
                .navigation();
    }

    /**
     * 提现成功
     */
    public static void toWithdrawSuccess(String price, String bankName) {
        ARouter.getInstance().build(YBRouter.WITHDRAW_SUCCESS_ACTIVITY)
                .withString("price", price)
                .withString("bankName", bankName)
                .navigation();
    }

    /**
     * 绑定银行卡
     */
    public static void toBindBankCard() {
        ARouter.getInstance().build(YBRouter.BIND_BAND_CARD_ACTIVITY).navigation();
    }

    /**
     * 专区
     */
    public static void toGroupAccount(int type) {
        ARouter.getInstance().build(YBRouter.GROUP_ACCOUNT_ACTIVITY)
                .withInt(YBRouter.GroupAccountActivityParams.type, type)
                .navigation();
    }

    /**
     * 立即充值
     */
    public static void toRechargeNow(int type) {
        ARouter.getInstance().build(YBRouter.RECHARGE_NOW_ACTIVITY)
                .withInt(YBRouter.GroupAccountActivityParams.type, type)
                .navigation();
    }

    /**
     * 设置主页面
     */
    public static void toSetting() {
        ARouter.getInstance().build(YBRouter.SETTING_ACTIVITY).navigation();
    }

    /**
     * 地址管理页面
     */
    public static void toAddress() {
        if (!UserStore.isLogin()) {
            toLogin();
            return;
        }
        ARouter.getInstance().build(YBRouter.ADDRESS_ACTIVITY).navigation();
    }

    /**
     * 确认订单 到 地址管理页面
     */
    public static void toAddressRequest(Activity activity, int requestCode, String addressId) {
        if (!UserStore.isLogin()) {
            toLogin();
            return;
        }
        ARouter.getInstance().build(YBRouter.ADDRESS_ACTIVITY)
                .withBoolean(YBRouter.AddressActivityParams.isOrderFlag, true)
                .withString(YBRouter.AddressActivityParams.addressId, addressId)
                .navigation(activity, requestCode);
    }

    /**
     * 地址管理  回调到  确认订单页面
     *
     * @param activity
     * @param addressOrderBean
     */
    public static void toAddressResult(Activity activity, AddressOrderBean addressOrderBean) {
        Intent intent = new Intent();
        intent.putExtra("addressOrderBean", addressOrderBean);
        activity.setResult(200, intent);
        activity.finish();
    }

    /**
     * 支付设置
     */
    public static void toPayManager() {
        if (!UserStore.isLogin()) {
            toLogin();
            return;
        }
        ARouter.getInstance().build(YBRouter.PAY_ACTIVITY).navigation();
    }

    /**
     * 支付设置：验证手机号
     */
    public static void toPaySet() {
        ARouter.getInstance().build(YBRouter.PAY_SET_ACTIVITY).navigation();
    }

    /**
     * 支付设置：设置密码
     */
    public static void toPayPassword() {
        ARouter.getInstance().build(YBRouter.PAY_PASSWORD_ACTIVITY).navigation();
    }

    /**
     * 支付设置：设置二次密码
     */
    public static void toPayTwoPassword(String password) {
        ARouter.getInstance().build(YBRouter.PAY_TWO_PASSWORD_ACTIVITY)
                .withString(YBRouter.PayTwoPasswordActivityParams.password, password)
                .navigation();
    }

    /**
     * 发票管理
     */
    public static void toBillManager() {
        if (!UserStore.isLogin()) {
            toLogin();
            return;
        }
        ARouter.getInstance().build(YBRouter.BILL_ACTIVITY).navigation();
    }

    /**
     * 全部发票
     */
    public static void toALLBill() {
        ARouter.getInstance().build(YBRouter.ALL_BILL_ACTIVITY).navigation();
    }

    /**
     * 发票抬头管理
     */
    public static void toBillHead() {
        ARouter.getInstance().build(YBRouter.BILL_HEAD_ACTIVITY).navigation();
    }

    /**
     * 添加个人发票抬头
     */
    public static void toUpdatePersonalBillHead(String personalBill) {
        ARouter.getInstance().build(YBRouter.UPDATE_PERSONAL_HEAD_ACTIVITY)
                .withString(YBRouter.BillHeadActivityParams.billHead, personalBill)
                .navigation();
    }

    /**
     * 添加公司发票抬头
     */
    public static void toUpdateUnitBillHead(String unitBill) {
        ARouter.getInstance().build(YBRouter.UPDATE_UNIT_BILL_HEAD_ACTIVITY)
                .withString(YBRouter.BillHeadActivityParams.billHead, unitBill)
                .navigation();
    }

    /**
     * 添加抬头页面
     */
    public static void toAddBillHeadCommon() {
        ARouter.getInstance().build(YBRouter.ADD_BILL_HEAD_ACTIVITY).navigation();
    }

    /**
     * 修改或添加地址页面
     */
    public static void toUpdateAddress(String addressBean) {
        ARouter.getInstance().build(YBRouter.ADD_ADDRESS_ACTIVITY)
                .withString(YBRouter.AddressActivityParams.addressBean, addressBean)
                .navigation();
    }

    /**
     * 通用页面
     */
    public static void toCommon() {
        ARouter.getInstance().build(YBRouter.COMMON_ACTIVITY).navigation();
    }

    /**
     * 关于页面
     */
    public static void toAbout() {
        ARouter.getInstance().build(YBRouter.ABOUT_ACTIVITY).navigation();
    }

    /**
     * 账号管理页面
     */
    public static void toAccount() {
        if (!UserStore.isLogin()) {
            toLogin();
            return;
        }
        ARouter.getInstance().build(YBRouter.ACCOUNT_ACTIVITY).navigation();
    }

    /**
     * 修改手机号页面
     */
    public static void toUpdatePhone() {
        ARouter.getInstance().build(YBRouter.UPDATE_PHONE_ACTIVITY).navigation();
    }

    /**
     * 新的手机号页面
     */
    public static void toNewPhone() {
        ARouter.getInstance().build(YBRouter.NEW_PHONE_ACTIVITY).navigation();
    }

    /**
     * 购物车
     */
    public static void toShopCart() {
        ARouter.getInstance().build(YBRouter.SHOP_CART_ACTIVITY).navigation();
    }

    /**
     * 注销账号页面
     */
    public static void toCloseAccount() {
        ARouter.getInstance().build(YBRouter.CLOSE_ACCOUNT_ACTIVITY).navigation();
    }

    /**
     * 注销账号最终页面
     */
    public static void toCloseAccountFinal() {
        ARouter.getInstance().build(YBRouter.CLOSE_FINAL_ACTIVITY).navigation();
    }

    /**
     * 确认订单
     */
    public static void toConfirmOrder(int type, ArrayList<String> skuIdList, ArrayList<OrderNumBean> numList,
                                      String mLot, String mBizId) {
        ARouter.getInstance().build(YBRouter.CONFIRM_ORDER_ACTIVITY)
                .withInt(YBRouter.OrderActivityParams.type, type)
                .withStringArrayList(YBRouter.OrderActivityParams.skuIdList, skuIdList)
                .withSerializable(YBRouter.OrderActivityParams.orderNumBean, numList)
                .withSerializable(YBRouter.OrderActivityParams.lot, mLot)
                .withSerializable(YBRouter.OrderActivityParams.bizId, mBizId)
                .navigation();
    }

    /**
     * 订单列表页面
     */
    public static void toOrderList(int orderType) {
        if (!UserStore.isLogin()) {
            toLogin();
            return;
        }
        ARouter.getInstance().build(YBRouter.ORDER_ACTIVITY)
                .withInt(YBRouter.OrderActivityParams.orderType, orderType)
                .navigation();
    }

    /**
     * 退换/售后
     */
    public static void toAfterSales() {
        if (!UserStore.isLogin()) {
            toLogin();
            return;
        }
        ARouter.getInstance().build(YBRouter.AFTER_SALES_ACTIVITY).navigation();
    }


    /**
     * 完成 退换/售后详情
     */
    public static void toAfterSalesFinishDetail(String totalOrderId, String afterSalesId) {
        ARouter.getInstance().build(YBRouter.AFTER_SALES_FINISH_DETAIL_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .withString(YBRouter.OrderDetailsActivityParams.afterSalesId, afterSalesId)
                .navigation();
    }

    /**
     * 取消退换/售后详情
     */
    public static void toCancelAfterSalesDetail(String totalOrderId, String afterSalesId) {
        ARouter.getInstance().build(YBRouter.CANCEL_AFTER_SALES_DETAIL_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .withString(YBRouter.OrderDetailsActivityParams.afterSalesId, afterSalesId)
                .navigation();
    }

    /**
     * 待审核 退换/售后详情
     */
    public static void toPendingReviewAfterDetail(String totalOrderId) {
        ARouter.getInstance().build(YBRouter.PENDING_REVIEW_AFTER_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .navigation();
    }

    /**
     * 审核失败 退换/售后详情
     */
    public static void toPendingReviewFailAfterDetail(String totalOrderId, String afterSalesId) {
        ARouter.getInstance().build(YBRouter.PENDING_REVIEW_FAIL_AFTER_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .withString(YBRouter.OrderDetailsActivityParams.afterSalesId, afterSalesId)
                .navigation();
    }

    /**
     * 待寄回 退换/售后详情
     */
    public static void toSentBackAfterDetail(String totalOrderId, String afterSalesId) {
        ARouter.getInstance().build(YBRouter.SENT_BACK_AFTER_DETAIL_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .withString(YBRouter.OrderDetailsActivityParams.afterSalesId, afterSalesId)
                .navigation();
    }

    /**
     * 待退款 退换/售后详情
     */
    public static void toPendingRefundDetail(String totalOrderId, String afterSalesId) {
        ARouter.getInstance().build(YBRouter.PENDING_REFUND_DETAIL_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .withString(YBRouter.OrderDetailsActivityParams.afterSalesId, afterSalesId)
                .navigation();
    }

    /**
     * 订单搜索
     */
    public static void toOrderSearch(int orderType) {
        ARouter.getInstance().build(YBRouter.ORDER_SEARCH_ACTIVITY)
                .withInt(YBRouter.OrderActivityParams.orderType, orderType)
                .navigation();
    }

    /**
     * 订单搜索
     */
    public static void toSearchResult(String keyword, int orderType) {
        ARouter.getInstance().build(YBRouter.SEARCH_RESULT_ACTIVITY)
                .withString(YBRouter.SearchResultActivityParams.keyword, keyword)
                .withInt(YBRouter.OrderActivityParams.orderType, orderType)
                .navigation();
    }

    /**
     * 元宝国际
     */
    public static void toYBInternational() {
        ARouter.getInstance().build(YBRouter.YB_INTERNATIONAL_ACTIVITY).navigation();
    }

    /**
     * 取消详情
     */
    public static void toCancelDetail(String totalOrderId) {
        ARouter.getInstance().build(YBRouter.CANCEL_DETAIL_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .navigation();
    }

    /**
     * 取消/退款进度
     */
    public static void toRefundDetail(String totalOrderId) {
        ARouter.getInstance().build(YBRouter.REFUND_DETAIL_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .navigation();
    }

    /**
     * 等待支付详情
     */
    public static void toPayDetail(String totalOrderId, boolean updateAddressFlag) {
        ARouter.getInstance().build(YBRouter.PAY_DETAIL_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .withBoolean(YBRouter.OrderDetailsActivityParams.updateAddressFlag, updateAddressFlag)
                .navigation();
    }

    /**
     * 等待支付详情
     */
    public static void toPayDetail(String totalOrderId) {
        ARouter.getInstance().build(YBRouter.PAY_DETAIL_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .navigation();
    }


    /**
     * 交易完成详情
     */
    public static void toFinishDetail(String totalOrderId) {
        ARouter.getInstance().build(YBRouter.FINISH_DETAIL_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .navigation();
    }

    /**
     * 待收货详情
     */
    public static void toReceiptDetail(String totalOrderId) {
        ARouter.getInstance().build(YBRouter.RECEIPT_DETAIL_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .navigation();
    }

    /**
     * 评价
     */
    public static void toEvaluation(String totalOrderId) {
        ARouter.getInstance().build(YBRouter.EVALUATION_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .navigation();
    }

    /**
     * 已完成：售后服务
     */
    public static void toAfterSalesService(String totalOrderId) {
        ARouter.getInstance().build(YBRouter.AFTER_SALES_SERVICE_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .navigation();
    }

    /**
     * 已完成：退货退款
     */
    public static void toSalesReturnRefund(Activity activity, int requestCode, String totalOrderId, int pageType) {
        ARouter.getInstance().build(YBRouter.SALES_RETURN_REFUND_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .withInt(YBRouter.SalesReturnRefundActivityParams.pageType, pageType)
                .navigation(activity, requestCode);
    }

    /**
     * 已完成：退货退款  回调到  已完成订单列表页面
     *
     * @param activity
     */
    public static void toSalesReturnRefundResult(Activity activity, int resultCode) {
        activity.setResult(resultCode, new Intent());
        activity.finish();
    }

    /**
     * 待收货：申请退款
     */
    public static void toApplyRefund(String totalOrderId, int type) {
        ARouter.getInstance().build(YBRouter.APPLY_REFUND_ACTIVITY)
                .withString(YBRouter.OrderDetailsActivityParams.totalOrderId, totalOrderId)
                .withInt(YBRouter.OrderDetailsActivityParams.type, type)
                .navigation();
    }

    /**
     * 充值权益
     *
     * @param url    链接
     * @param isShow 是否显示HeadView   true=显示  false=不显示
     */
    public static void toWebJs(String url, boolean isShow) {
        ARouter.getInstance().build(YBRouter.WEB_JS_ACTIVITY)
                .withString(YBRouter.WebActivityParams.url, url)
                .withBoolean(YBRouter.WebActivityParams.isShow, isShow)
                .navigation();
    }

    /**
     * 收藏页面
     */
    public static void toCollection() {
        if (!UserStore.isLogin()) {
            toLogin();
            return;
        }
        ARouter.getInstance().build(YBRouter.COLLECTION_ACTIVITY).navigation();
    }

    /**
     * 卡券页面
     */
    public static void toCoupon() {
        if (!UserStore.isLogin()) {
            toLogin();
            return;
        }
        ARouter.getInstance().build(YBRouter.COUPON_ACTIVITY).navigation();
    }

    /**
     * 相关推荐
     */
    public static void toRecommend() {
        ARouter.getInstance().build(YBRouter.RECOMMEND_ACTIVITY).navigation();
    }

    /**
     * 幸运拼团
     *
     * @param id 大抽奖ID
     */
    public static void toLuckyFighInfo(String id) {
        ARouter.getInstance().build(YBRouter.LUCKY_FIGH_INFO_ACTIVITY)
                .withString(YBRouter.LuckyFighInfoActivityParams.id, id).navigation();
    }

    /**
     * 编辑资料
     */
    public static void toEditMine(String address, String nickName, String signature, String portraitUrl) {
        ARouter.getInstance().build(YBRouter.EDIT_MINE_ACTIVITY)
                .withString(YBRouter.EditMineActivityParams.address, address)
                .withString(YBRouter.EditMineActivityParams.nickName, nickName)
                .withString(YBRouter.EditMineActivityParams.signature, signature)
                .withString(YBRouter.EditMineActivityParams.portraitUrl, portraitUrl)
                .navigation();
    }

    /**
     * 修改昵称
     */
    public static void toUpdateName(Activity activity, int requestCode, String nickName) {
        ARouter.getInstance().build(YBRouter.UPDATE_NAME_ACTIVITY)
                .withString(YBRouter.EditMineActivityParams.nickName, nickName)
                .navigation(activity, requestCode);
    }

    /**
     * 修改昵称  回调到  用户信息页面
     *
     * @param activity
     * @param nickName
     */
    public static void toUpdateNameResult(Activity activity, int resultCode, String nickName) {
        Intent intent = new Intent();
        intent.putExtra("nickName", nickName);
        activity.setResult(resultCode, intent);
        activity.finish();
    }


    /**
     * 修改个性签名
     */
    public static void toUpdateSignature(Activity activity, int requestCode, String signature) {
        ARouter.getInstance().build(YBRouter.UPDATE_SIGNATURE_ACTIVITY)
                .withString(YBRouter.EditMineActivityParams.signature, signature)
                .navigation(activity, requestCode);
    }

    /**
     * 修改个性签名  回调到  用户信息页面
     *
     * @param activity
     * @param signature
     */
    public static void toUpdateSignatureResult(Activity activity, int resultCode, String signature) {
        Intent intent = new Intent();
        intent.putExtra("signature", signature);
        activity.setResult(resultCode, intent);
        activity.finish();
    }

    /**
     * 邀请好友
     */
    public static void toInviteFriends() {
        ARouter.getInstance().build(YBRouter.INVITE_FRIENDS_ACTIVITY).navigation();
    }

    /**
     * 邀请-推广明细-查看更多
     */
    public static void toInviteFriendsPromote() {
        ARouter.getInstance().build(YBRouter.INVITE_FRIENDS_PROMOTE_ACTIVITY).navigation();
    }

    /**
     * 我的-个人主页
     */
    public static void toMineActivity(String ybCode) {
        ARouter.getInstance().build(YBRouter.Mine_Activity)
                .withString(YBRouter.MineActivityParams.ybCode, ybCode)
                .navigation();
    }

    /**
     * 我的-个人主页-直播场次
     */
    public static void toLiveAmountActivity() {
        ARouter.getInstance().build(YBRouter.Live_Amount_Activity).navigation();
    }

    /**
     * 我的-个人主页-我的粉丝
     */
    public static void toMyFansActivity() {
        ARouter.getInstance().build(YBRouter.My_Fans_Activity).navigation();
    }

    /**
     * 商品购买成功
     */
    public static void toOrderSuccess(String price, int type) {
        ARouter.getInstance().build(YBRouter.ORDER_SUCCESS_ACTIVITY)
                .withString(YBRouter.OrderSuccessActivityParams.price, price)
                .withInt(YBRouter.OrderSuccessActivityParams.type, type)
                .navigation();
    }


    /**
     * 我的-个人主页-我的粉丝
     */
    public static void toMyFollowActivity() {
        ARouter.getInstance().build(YBRouter.My_Follow_Activity).navigation();
    }

    /**
     * 播放视频
     */
    public static void toShortVideoPlayActivity(ArrayList<RecommendVideoBean.RowsBean> mVideoList, int startPosition, String sourcetype,int type) {
        ARouter.getInstance().build(YBRouter.SHORTVIDEOPLAY)
                .withParcelableArrayList(YBRouter.ShortVideoPlayActivityParams.shortvideolistBean, mVideoList)
                .withInt(YBRouter.ShortVideoPlayActivityParams.startPosition, startPosition)
                .withString(YBRouter.ShortVideoPlayActivityParams.sourcetype, sourcetype)
                .withInt(YBRouter.ShortVideoPlayActivityParams.type, type)
                .navigation();
    }

    /**
     * 查看用户背景图片
     */
    public static void toPreviewPictureActivity(String url) {
        ARouter.getInstance().build(YBRouter.PREVIEW_PICTURE_ACTIVITY)
                .withString(YBRouter.PreviewPictureActivityParams.url, url)
                .navigation();
    }

}

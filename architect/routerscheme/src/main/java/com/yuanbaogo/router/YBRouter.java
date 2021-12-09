package com.yuanbaogo.router;

/**
 * @Description: 路由跳转路径
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/5/21 9:43 AM
 * @Modifier:
 * @Modify:
 */
public class YBRouter {

    /**
     * 短视频播放器
     */
    public static final String SHORTVIDEOPLAY = "/video/shortvideo/shortvideoplay";

    /**
     * 短视频播放器--参数传递
     */
    public interface ShortVideoPlayActivityParams {
        //搜索参数
        String shortvideolistBean = "shortvideolist";
        String startPosition = "startPosition";
        String sourcetype = "sourcetype";
        String type = "type";//1 自己的个人中心 2 他人的个人中心
    }

    /**
     * 首页
     */
    public static final String MAIN_ACTIVITY = "/app/MainActivity";

    /**
     * 登录
     */
    public static final String LOGIN_ACTIVITY = "/login/LoginActivity";

    /**
     * 换绑手机号
     */
    public static final String CHANGE_PHONE_ACTIVITY = "/login/ChangePhoneActivity";

    /**
     * 用户协议
     */
    public static final String LOGIN_PACT_ACTIVITY = "/login/LoginPactActivity";

    /**
     * web页
     */
    public static final String WEB_ACTIVITY = "/web/WebActivity";

    /**
     * 小程序活动展示界面
     */
    public static final String EVENT_ACTIVITY = "/app/WXSmallProgramActivity";

    public interface WebActivityParams {
        String url = "url";
        String title = "title";
        String isShow = "isShow";
    }

    /**
     * 商品搜索
     */
    public static final String SHOP_SEARCH_ACTIVITY = "/shop/SearchActivity";

    /**
     * 商品搜索--参数传递
     */
    public interface ShopSearchActivityParams {
        //搜索参数
        String searchMerchandiseBean = "SearchMerchandiseBean";
    }

    /**
     * 一城一品
     */
    public static final String SHOP_ONE_CITY_ACTIVITY = "/shop/OneCityOneProducctyActivity";

    /**
     * 秒杀
     */
    public static final String SHOP_SPICK_ACTIVITY = "/shop/SpickActivity";

    /**
     * 拼团
     */
    public static final String SHOP_JOIN_GROUP_ACTIVITY = "/shop/JoinGroupActivity";

    /**
     * 幸运拼团
     */
    public static final String SHOP_LUCKY_FIGHT_ACTIVITY = "/shop/LuckyFightActivity";

    /**
     * 商品详情
     */
    public static final String SHOP_PRODUCT_DETAILS_ACTIVITY = "/shop/ProductDetailsActivity";

    /**
     * 商品详情--参数传递
     */
    public interface ProductDetailsActivityParams {
        //商品id
        String spuId = "spuId";
        String type = "type";
        //直播/短视频需要传批次
        String lot = "lot";
        //业务ID(短视频ID,直播聊天室ID,场次号)
        String bizId = "bizId";
        //是否显示相关推荐
        String isShow = "isShow";
    }

    /**
     * 商品列表
     */
    public static final String SHOP_PRODUCT_DETAILS_LIST_ACTIVITY = "/shop/ProductDetailsListActivity";

    /**
     * 修改账号
     */
    public static final String MODIFY_ACCOUNT_ACTIVITY = "/mine/ModifyAccountActivity";

    /**
     * 注销账号
     */
    public static final String CANCELLATION_ACTIVITY = "/mine/CancellationActivity";

    /**
     * 带货
     */
    public static final String BRING_GOODS_ACTIVITY = "/video/BringGoodsActivity";

    /**
     * 我的资产
     */
    public static final String ASSETS_ACTIVITY = "/mine/AssetsActivity";

    /**
     * 元宝积分
     */
    public static final String YB_INTEGRAL_ACTIVITY = "/mine/YBIntegralActivity";

    /**
     * 积分充值
     */
    public static final String INTEGRAL_RECHARGE_ACTIVITY = "/mine/IntegralRechargeActivity";

    /**
     * 零钱
     */
    public static final String DIB_ACTIVITY = "/mine/DibActivity";

    /**
     * 提现
     */
    public static final String WITHDRAW_ACTIVITY = "/mine/WithdrawActivity";

    /**
     * 提现成功
     */
    public static final String WITHDRAW_SUCCESS_ACTIVITY = "/mine/WithdrawSuccessActivity";

    /**
     * 绑定银行卡
     */
    public static final String BIND_BAND_CARD_ACTIVITY = "/finance/BindBankCardActivity";

    /**
     * 专区
     */
    public static final String GROUP_ACCOUNT_ACTIVITY = "/mine/GroupAccountActivity";

    public interface GroupAccountActivityParams {
        String type = "type";
    }

    /**
     * 立即充值
     */
    public static final String RECHARGE_NOW_ACTIVITY = "/mine/RechargeNowActivity";

    /**
     * 设置主页面
     */
    public static final String SETTING_ACTIVITY = "/mine/SettingActivity";

    /**
     * 地址管理页面
     */
    public static final String ADDRESS_ACTIVITY = "/mine/AddressActivity";

    public interface AddressActivityParams {
        String addressBean = "address_bean";
        String isOrderFlag = "isOrderFlag";
        String addressId = "addressId";
    }

    /**
     * 添加地址页面
     */
    public static final String ADD_ADDRESS_ACTIVITY = "/mine/AddAddressActivity";

    /**
     * 通用页面
     */
    public static final String COMMON_ACTIVITY = "/mine/CommonActivity";

    /**
     * 关于页面
     */
    public static final String ABOUT_ACTIVITY = "/mine/AboutActivity";

    /**
     * 账号管理
     */
    public static final String ACCOUNT_ACTIVITY = "/mine/AccountActivity";

    /**
     * 修改手机号
     */
    public static final String UPDATE_PHONE_ACTIVITY = "/mine/UpdatePhoneActivity";

    /**
     * 新的手机号
     */
    public static final String NEW_PHONE_ACTIVITY = "/mine/NewPhoneActivity";

    /**
     * 注销账号
     */
    public static final String CLOSE_ACCOUNT_ACTIVITY = "/mine/CloseAccountActivity";

    /**
     * 注销账号最终页面
     */
    public static final String CLOSE_FINAL_ACTIVITY = "/mine/CloseFinalActivity";

    /**
     * 支付管理
     */
    public static final String PAY_ACTIVITY = "/mine/PayActivity";

    /**
     * 支付管理:手机验证页面
     */
    public static final String PAY_SET_ACTIVITY = "/mine/PaySetActivity";

    /**
     * 支付管理:设置密码页面
     */
    public static final String PAY_PASSWORD_ACTIVITY = "/mine/PayPasswordActivity";

    public interface PayTwoPasswordActivityParams {
        String password = "password";
    }

    /**
     * 支付管理:设置二次密码页面
     */
    public static final String PAY_TWO_PASSWORD_ACTIVITY = "/mine/PayTwoPasswordActivity";

    /**
     * 发票管理
     */
    public static final String BILL_ACTIVITY = "/mine/BillActivity";

    /**
     * 全部发票
     */
    public static final String ALL_BILL_ACTIVITY = "/mine/AllBillActivity";

    /**
     * 发票抬头管理
     */
    public static final String BILL_HEAD_ACTIVITY = "/mine/BillHeadActivity";

    /**
     * 编辑个人抬头
     */
    public static final String UPDATE_PERSONAL_HEAD_ACTIVITY = "/mine/UpdatePersonalBillHeadActivity";

    /**
     * 编辑公司抬头
     */
    public static final String UPDATE_UNIT_BILL_HEAD_ACTIVITY = "/mine/UpdateUnitBillHeadActivity";

    public interface BillHeadActivityParams {
        String billHead = "bill_head";
    }

    /**
     * 新增抬头
     */
    public static final String ADD_BILL_HEAD_ACTIVITY = "/mine/AddBillHeadActivity";

    /**
     * 购物车
     */
    public static final String SHOP_CART_ACTIVITY = "/shopcart/ShopCartActivity";

    /**
     * 确认订单
     */
    public static final String CONFIRM_ORDER_ACTIVITY = "/shopcart/ConfirmOrderActivity";

    public interface OrderActivityParams {
        String orderType = "orderType";
        //确认订单
        String skuIdList = "skuIdList";
        //购买数量
        String orderNumBean = "orderNumBean";
        //1 商品详情  2 购物车
        String type = "type";
        //批次
        String lot = "lot";
        //业务ID(短视频ID,直播聊天室ID,场次号)
        String bizId = "bizId";
    }

    /**
     * 全部订单页面
     */
    public static final String ORDER_ACTIVITY = "/mine/OrderActivity";

    /**
     * 退换/售后
     */
    public static final String AFTER_SALES_ACTIVITY = "/mine/AfterSalesActivity";

    /**
     * 退换/售后过程中详情
     */
    public static final String AFTER_SALES_PROGRESS_DETAIL_ACTIVITY = "/mine/AfterSalesProgressDetailActivity";

    /**
     * 退换/售后完成详情
     */
    public static final String AFTER_SALES_FINISH_DETAIL_ACTIVITY = "/mine/AfterSalesFinishDetailActivity";

    /**
     * 取消退换/售后完成详情
     */
    public static final String CANCEL_AFTER_SALES_DETAIL_ACTIVITY = "/mine/CancelAfterSalesActivity";

    /**
     * 待审核 退换/售后完成详情
     */
    public static final String PENDING_REVIEW_AFTER_ACTIVITY = "/mine/PendingReviewAfterActivity";

    /**
     * 审核失败 退换/售后完成详情
     */
    public static final String PENDING_REVIEW_FAIL_AFTER_ACTIVITY = "/mine/PendingReviewFailAfterActivity";

    /**
     * 待寄回 退换/售后完成详情
     */
    public static final String SENT_BACK_AFTER_DETAIL_ACTIVITY = "/mine/SentBackAfterDetailActivity";

    /**
     * 待寄回 退换/售后完成详情
     */
    public static final String PENDING_REFUND_DETAIL_ACTIVITY = "/mine/PendingRefundDetailActivity";

    public interface SearchResultActivityParams {
        String keyword = "keyword";
    }

    /**
     * 填写快递单号
     */
    public static final String SET_LOGISTICS_CODE_ACTIVITY = "/mine/SetLogisticsCodeActivity";

    /**
     * 订单搜索页面
     */
    public static final String ORDER_SEARCH_ACTIVITY = "/mine/OrderSearchActivity";

    /**
     * 搜索结果页面
     */
    public static final String SEARCH_RESULT_ACTIVITY = "/mine/SearchResultActivity";

    /**
     * 元宝国际
     */
    public static final String YB_INTERNATIONAL_ACTIVITY = "/shop/YBInternationalActivity";

    public interface OrderDetailsActivityParams {
        String totalOrderId = "totalOrderId";
        String afterSalesId = "afterSalesId";
        String updateAddressFlag = "updateAddressFlag";
        String type = "type";
    }

    /**
     * 取消/退款进度
     */
    public static final String REFUND_DETAIL_ACTIVITY = "/mine/RefundDetailActivity";

    /**
     * 取消详情
     */
    public static final String CANCEL_DETAIL_ACTIVITY = "/mine/CancelDetailActivity";

    /**
     * 等待支付详情
     */
    public static final String PAY_DETAIL_ACTIVITY = "/mine/PayDetailActivity";

    /**
     * 交易完成详情
     */
    public static final String FINISH_DETAIL_ACTIVITY = "/mine/FinishDetailActivity";

    /**
     * 待收货详情
     */
    public static final String RECEIPT_DETAIL_ACTIVITY = "/mine/ReceiptDetailActivity";

    /**
     * 已完成：发表评价
     */
    public static final String EVALUATION_ACTIVITY = "/mine/EvaluationActivity";

    /**
     * 已完成：售后服务
     */
    public static final String AFTER_SALES_SERVICE_ACTIVITY = "/mine/AfterSalesServiceActivity";

    public interface SalesReturnRefundActivityParams {
        String pageType = "pageType";
    }

    /**
     * 已完成：退货退款
     */
    public static final String SALES_RETURN_REFUND_ACTIVITY = "/mine/SalesReturnRefundActivity";

    /**
     * 待收货：申请退款
     */
    public static final String APPLY_REFUND_ACTIVITY = "/mine/ApplyRefundActivity";

    /**
     * 充值权益
     */
    public static final String WEB_JS_ACTIVITY = "/mine/WebActivity";

    /**
     * 收藏页面
     */
    public static final String COLLECTION_ACTIVITY = "/mine/CollectionActivity";

    /**
     * 卡券页面
     */
    public static final String COUPON_ACTIVITY = "/mine/CouponActivity";

    /**
     * 相关推荐
     */
    public static final String RECOMMEND_ACTIVITY = "/shop/RecommendActivity";

    /**
     * 编辑资料
     */
    public static final String EDIT_MINE_ACTIVITY = "/mine/EditMineActivity";

    public interface EditMineActivityParams {
        String portraitUrl = "portraitUrl";
        String address = "address";
        String nickName = "nickName";
        String signature = "signature";
    }

    /**
     * 修改昵称
     */
    public static final String UPDATE_NAME_ACTIVITY = "/mine/UpdateNameActivity";

    /**
     * 修改个性签名
     */
    public static final String UPDATE_SIGNATURE_ACTIVITY = "/mine/UpdateSignatureActivity";

    /**
     * 幸运拼团详情
     */
    public static final String LUCKY_FIGH_INFO_ACTIVITY = "/shop/LuckyFighInfoActivity";

    public interface LuckyFighInfoActivityParams {
        String id = "id";
    }

    /**
     * 邀请好友
     */
    public static final String INVITE_FRIENDS_ACTIVITY = "/shop/InviteFriendsActivity";

    /**
     * 邀请-推广明细-查看更多
     */
    public static final String INVITE_FRIENDS_PROMOTE_ACTIVITY = "/shop/InviteFriendsPromoteActivity";

    /**
     * 我的-个人主页
     */
    public static final String Mine_Activity = "/video/MineActivity";

    /**
     * 个人主页参数
     */
    public interface MineActivityParams {
        String ybCode = "ybCode";
    }

    /**
     * 我的-个人主页-直播场次
     */
    public static final String Live_Amount_Activity = "/mine/LiveAmountActivity";

    /**
     * 我的-个人主页-我的粉丝
     */
    public static final String My_Fans_Activity = "/mine/MyFansActivity";

    /**
     * 商品购买成功
     */
    public static final String ORDER_SUCCESS_ACTIVITY = "/shopcart/OrderSuccessActivity";

    public interface OrderSuccessActivityParams {
        String price = "price";
        String type = "type";
    }

    /**
     * 我的-个人主页-我的粉丝
     */
    public static final String My_Follow_Activity = "/mine/MyFollowActivity";

    /**
     * 查看用户背景图片
     */
    public static final String PREVIEW_PICTURE_ACTIVITY = "/video/PreviewPictureActivity";

    public interface PreviewPictureActivityParams {
        String url = "url";
    }

}

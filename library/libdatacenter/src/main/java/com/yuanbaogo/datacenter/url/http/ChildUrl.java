package com.yuanbaogo.datacenter.url.http;

/**
 * @Description: 接口文档
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/7/21 11:57 AM
 * @Modifier:
 * @Modify:
 */
public abstract class ChildUrl {

    /**
     * 商城
     */
    public static final String MALL_BUSI = "/mall-busi";

    /**
     * 支付
     */
    public static final String PAY_BUSI = "/pay-busi";

    /**
     * 直播
     */
//    public static final String ENTE_BUSI = "";
    public static final String ENTE_BUSI = "/ente-busi";

    /**
     * 用户登录
     */
//    public static final String login = "/mall-busi/buyer/user/login";
    public static final String login = "/yb-busi/login";
    public static final String checkJiYan = "/mall-busi/buyer/user/man-verify";//极验
    public static final String checkJiYan_second = "/mall-busi/buyer/user/man-second";//极验二次验证
    public static final String checkPhone = "/mall-busi/buyer/user/checkPhone";//极验二次验证
    public static final String getVerificationCode = "/register/getVerificationCode";//获取验证码
    public static final String codeLogin = "/login/codeLogin";//验证码登录
    public static final String paySignInfo = "/wechat/getPaySignInfo";

    /*
     * ================================================短视频开始================================================
     */

    public static final String getSignature = "/ente-busi/resource/vod/signature";//获取上传视频签名
    public static final String uploadVideo = "/ente-busi/resource/vod/upload";//上传视频

    public static final String getRecommendVideo = ENTE_BUSI + "/recommendation/vod/default";//短视频推荐列表
    public static final String clickLike = ENTE_BUSI + "/interactive/like/vod";//视频点赞
    public static final String clickcommentLike = ENTE_BUSI + "/interactive/like/comment";//评论点赞
    public static final String deleteVideo = ENTE_BUSI + "/resource/vod/del/";//删除视频
    public static final String unconcernVideo = ENTE_BUSI + "/resource/vod/unconcern/";//不感兴趣视频
    public static final String reportVodSubmit = ENTE_BUSI + "/report/{type}/submit";//举报
    public static final String commentlist = ENTE_BUSI + "/interactive/comment/list";//评论列表
    public static final String deletecomment = ENTE_BUSI + "/interactive/comment/delete/{commentId}";
    public static final String vodcomment = ENTE_BUSI + "/interactive/comment/vod";




    /*
     * ================================================短视频结束================================================
     */


    /**
     * 发送验证码
     */
    public static final String SEND_CODE = "/pay-busi/captcha/sendCaptcha";

    /**
     * 校验手机验证码
     */
    public static final String CHECK_CAPTCHA = "/pay-busi/captcha/checkCaptcha";

    /**
     * 修改用户手机号发送验证码
     */
    public static final String SEND_RESET_CODE = "/mall-busi/buyer/reset/send-reset-code";

    /**
     * 修改用户手机号发送验证码到新手机
     */
    public static final String SEND_NEW_RESET_CODE = "/mall-busi/buyer/reset/send-new-reset-code";

    /**
     * 获取新手机号绑定的code
     */
    public static final String NEXT_STEP_PHONE = "/mall-busi/buyer/reset/next-step";

    /**
     * 确认修改
     */
    public static final String CONFIRM_UPDATE_PHONE = "/mall-busi/buyer/reset/confirm-update";

    /**
     * 用户个人中心展示
     */
    public static final String GET_USER_INFO = "/mall-busi/buyer/center";

    /**
     * 退出登录
     */
    public static final String SIGN_OUT = "/mall-busi/buyer/user/loginOut";

    /**
     * 设置-修改用户账户支付密码
     */
    public static final String SET_PAY_PASSWORD = "/pay-busi/pay/user/updatePayPassword";

    /**
     * 获取默认收货地址
     */
    public static final String GET_DEFAULT_ADDRESS = MALL_BUSI + "/buyer/address/fetch-default-address";

    /**
     * 获取收货地址列表
     */
    public static final String GET_ADDRESS_LIST = "/mall-busi/buyer/address/list-address";
    /**
     * 删除收货地址
     */
    public static final String DELETE_ADDRESS = "/mall-busi/buyer/address/delete-address";
    /**
     * 添加或修改收货地址
     */
    public static final String ADD_ADDRESS = MALL_BUSI + "/buyer/address/add-or-update-address";

    /**
     * 修改个人信息
     */
    public static final String PERSONAL_SUBMIT = ENTE_BUSI + "/personal/personal-submit";

    /**
     * 修改头像
     */
    public static final String UPDATE_PORTRAIT = ENTE_BUSI + "/personal/update-portrait";

    /**
     * 获取新版本接口
     */
    public static final String GET_NEW_VERSION = "/mall-busi/version/new-version";

    /**
     * 修改背景
     */
    public static final String UPDATE_BG = ENTE_BUSI + "/personal/update-bg";
    /*
     * ================================================商城开始================================================
     */
    /**
     * 轮播图get  location = 1 首页顶部 2 秒杀顶部 3 拼团顶部 4 一城一品顶部 5 元宝国际顶部
     */
    public static final String MALL_BANNER = MALL_BUSI + "/operating/all-banner/{location}";

    /**
     * 商城主⻚数据get
     */
    public static final String MALL_HOME = MALL_BUSI + "/mall/page/home";

    /**
     * 推荐商品获取post attribution = 0：商品详情-次屏-相关推荐、1：拼团-销量排行、2：一城一品-首款爆推、3：一城一品-为你推荐
     */
    public static final String RECOMMEND = MALL_BUSI + "/goods/search/recommend";

    /**
     * 商品详情-商品评论
     */
    public static final String COMMENT = MALL_BUSI + "/mall/page/goods-detail/comment";

    /**
     * 根据输入框内容检索商品列表
     */
    public static final String SEARCH_MERCHANDISE = MALL_BUSI + "/goods/search/search-merchandise";

    /**
     * 根据spuid获取商品详情
     */
    public static final String SEARCH_MERCHANDISE_DETAIL = MALL_BUSI + "/goods/search/search-merchandise-detail/{spuId}";

    /**
     * 商品详情
     */
    public static final String DETAIL = MALL_BUSI + "/goods-detail/detail/{id}";

    /**
     * 商品详情-规格参数及sku
     */
    public static final String SKU = MALL_BUSI + "/mall/page/goods-detail/sku/{spuId}";

    /**
     * 商品详情-商品库存
     */
    public static final String STOCK = MALL_BUSI + "/mall/page/goods-detail/stock";

    /**
     * 推荐搜索
     */
    public static final String RECOMMENDED_SEARCH = MALL_BUSI + "/goods/search/recommended-search";

    /**
     * 查询商品是否加入收藏夹接口
     */
    public static final String FIND_FAVORITES_FLAG = MALL_BUSI + "/favorites/find-favorites-flag/{spuId}";

    /**
     * 收藏夹新增商品接口
     */
    public static final String ADD_FAVORITES = MALL_BUSI + "/favorites/add-favorites";

    /**
     * 商品详情页移除收藏夹
     */
    public static final String DELETE_FAVORITES1 = MALL_BUSI + "/favorites/delete-favorites-goods/{spuId}";

    /**
     * 订单确认信息
     */
    public static final String ORDER_CHECK = MALL_BUSI + "/goods-detail/order-check";

    /**
     * 下单
     */
    public static final String CREATE_ORDER = MALL_BUSI + "/orders/create-order";

    /**
     * 查询场馆
     */
    public static final String QUERY_VENUE = MALL_BUSI + "/venue/app-query-venue";

    /**
     * 活动名称列表
     */
    public static final String LUCK_DRAW_LIST = MALL_BUSI + "/luck-draw/luck-draw-list";

    /**
     * 分页查询幸运大抽奖
     */
    public static final String LUCK_DRAW = MALL_BUSI + "/luck-draw/page";

    /**
     * 查询商品详情
     */
    public static final String GOODS_DETAIL = MALL_BUSI + "/luck-draw/goods-detail/{id}";

    /**
     * 大抽奖订单确认信息
     */
    public static final String GOODS_INFO = MALL_BUSI + "/luck-draw/goods-info/{id}";

    /**
     * 预约大抽奖
     */
    public static final String LUCK_DRAW_SUBSCRIBE = MALL_BUSI + "/luck-draw/subscribe";

    /**
     * 是否加入该抽奖
     */
    public static final String PARTICIPATED = MALL_BUSI + "/luck-draw/participated/{id}";
    /*
     * ================================================商城结束================================================
     */

    /*
     * =============================================订单开始================================================
     */

    /**
     * 获取全部订单
     */
    public static final String GET_ALL_ORDER = "/mall-busi/orders/list-order-all";
    /**
     * 获取已取消的订单
     */
    public static final String GET_CANCEL_ORDER = "/mall-busi/orders/list-order-canceled";
    /**
     * 获取已完成的订单
     */
    public static final String GET_FINISH_ORDER = "/mall-busi/orders/list-order-completed";
    /**
     * 获取待付款的订单
     */
    public static final String GET_PAY_ORDER = "/mall-busi/orders/list-order-pendingPayment";
    /**
     * 获取待收获的订单
     */
    public static final String GET_RECEIPT_ORDER = "/mall-busi/orders/list-order-toBeReceived";
    /**
     * 猜你喜欢
     */
    public static final String GET_LIKE_LIST = "/mall-busi/goods/search/recommend";
    /**
     * 申请退款
     */
    public static final String APPLY_REFUND = "/mall-busi/after-sales/apply-sales";
    /**
     * 取消订单-已付款
     */
    public static final String CANCEL_ORDER_APPLY = "/mall-busi/orders/cancel-order-apply";
    /**
     * 获取订单详情
     */
    public static final String GET_ORDER_DETAIL = "/mall-busi/orders/get-order-information";
    /**
     * 修改订单收货地址
     */
    public static final String UPDATE_ORDER_ADDRESS = "/mall-busi/orders/update-order-address";
    /**
     * 评价商品
     */
    public static final String EVALUATION_GOODS = "/mall-busi/orders/comment";
    /**
     * 评价商品
     */
    public static final String UPLOAD_IMAGE = "/mall-busi/current/comment-image-upload";
    /**
     * 获取最新订单物流
     */
    public static final String GET_ORDER_LOGISTICS_NEW = "/mall-busi/logistics/find-latest-logistics";
    /**
     * 获取订单详情
     */
    public static final String GET_ORDER_LOGISTICS_DETAILS = "/mall-busi/logistics/find-logistics-info";
    /**
     * 查询售后记录列表接口
     */
    public static final String GET_REFUND_LIST = "/mall-busi/after-sales/find-after-sales-page";
    /**
     * 撤销申请接口
     */
    public static final String CANCEL_REFUND = "/mall-busi/after-sales/revoke-sales";
    /**
     * 确认收货接口
     */
    public static final String CONFIRM_GOODS = "/mall-busi/orders/confirm-receipt";
    /**
     * 取消订单接口
     */
    public static final String CANCEL_ORDER = "/mall-busi/orders/cancel-order";
    /**
     * 删除订单接口
     */
    public static final String DELETE_ORDER = "/mall-busi/orders/remove-order";
    /**
     * 加入购物车接口
     */
    public static final String ORDER_GOODS_ADD_CAR = "/mall-busi/shopping-cart/add-goods";
    /**
     * 查询退款信息接口
     */
    public static final String GET_REFUND_DETAIL = "/mall-busi/after-sales/find-sales-history";
    /**
     * 查询审批流程状态接口
     */
    public static final String GET_REFUND_STATUS = "/mall-busi/after-sales/find-process-status";
    /**
     * 查询收藏夹列表接口 get
     */
    public static final String GET_COLLECTION_LIST = "/mall-busi/favorites/find-favorites-page";
    /**
     * 移入购物车接口 post
     */
    public static final String MOVE_SHOP_CART = "/mall-busi/favorites/move-shopcart";
    /**
     * 删除收藏夹商品接口 post
     */
    public static final String DELETE_FAVORITES = "/mall-busi/favorites/delete-favorites";
    /**
     * 获取售后详情
     */
    public static final String GET_AFTER_SALES_DETAILS = "/mall-busi/after-sales/find-process-info";
    /**
     * 添加物流
     */
    public static final String ADD_LOGISTICS = "/mall-busi/after-sales/add-logistics";
    /**
     * APP-查询货物状态、退款原因、退货方式、快递公司接口
     */
    public static final String SELECT_LOGISTICS_COMPANY = "/mall-busi/after-sales/find-sales-basicsInfo";
    /**
     * 添加物流
     */
    public static final String GET_CANCEL_PROCESS = "/mall-busi/after-sales/find-cancel-process";
    /**
     * 多个图片上传
     */
    public static final String UPLOAD_LIST = ENTE_BUSI + "/resource/file/upload-list-by-type";
    /**
     * 是否可进入 幸运拼团
     */
    public static final String LUCK_DRAW_ENTER = MALL_BUSI + "/luck-draw/enter/{orderId}";
    /*
     * =============================================订单结束================================================
     */

    /*
     * ================================================支付开始================================================
     */
    /**
     * 查询不同类型钱包数量
     */
    public static final String GET_DIFF_TYPE_WALLET = PAY_BUSI + "/wallet/getDiffTypeWallet";

    /**
     * 查询元宝积分log
     */
    public static final String GET_COIN_POINT_LOG = PAY_BUSI + "/wallet/getCoinPointLog";

    /**
     * 查询零钱log
     */
    public static final String GET_SMALL_CHANGE_LOG = PAY_BUSI + "/wallet/getSmallChangeLog";

    /**
     *是否可以提现
     */
    public static final String CAN_WITHDRAWAL = PAY_BUSI + "/account/small-change/can-withdrawal";


    /**
     * 查询专区钱包金额、优惠券个数
     */
    public static final String FIND_AREA_INFO = MALL_BUSI + "/buyer/group/find-area-info/{areaType}";

    /**
     * 查询用户优惠券交易日志
     */
    public static final String FIND_COUPON_LOG_LIST = MALL_BUSI + "/buyer/group/find-coupon-log-list";

    /**
     * 查询账户历史记录
     */
    public static final String FIND_ACCOUNT_LOG_LIST = MALL_BUSI + "/buyer/group/find-account-log-list";

    /**
     * 查询当前类别所有下级的未使用状态优惠券
     */
    public static final String FIND_NOUSE_COUPON_LIST = MALL_BUSI + "/buyer/group/find-nouse-coupon-list/{areaType}";

    /**
     * 用户拼团预下单
     */
    public static final String PRE_RECHARGE = MALL_BUSI + "/buyer/group/pre-recharge";

    /**
     * 验证用户账户支付密码
     */
    public static final String VERIFY_PAY_PASSWORD = PAY_BUSI + "/pay/user/verifyUserPayPassword";

    /**
     * 验证用户是否设置支付密码
     */
    public static final String VERIFY_HAS_PAY_PASSWORD = "/pay-busi/pay/user/checkPayPasswordSet";

    public static final String getPayType = PAY_BUSI + "/pay/getPayType";
    public static final String getPayInfo = PAY_BUSI + "/coinpoint/buy/doPreOrders";//购买元宝积分唤起支付接口
    public static final String getbuyPayInfo = PAY_BUSI + "/group/buy/doPreOrders";//购买元宝积分唤起支付接口
    public static final String getgoodsPayInfo = PAY_BUSI + "/goods/buy/doPreOrders";//购买元宝积分唤起支付接口
    public static final String getCoinpointResult = PAY_BUSI + "/coinpoint/buy/getResult";//查询支付结果

    public static final String checkUserStatus = PAY_BUSI + "/pay/user/checkUserPayLockedStatus";//查看用户状态(冻结是true正常状态是false)

    /**
     * 查询用户各个专区钱包总额
     */
    public static final String FIND_AREA_AMOUNT = MALL_BUSI + "/buyer/group/find-area-amount";

    /**
     * 获取福利社手机充值服务以及加油服务接口url
     */
    public static final String PHONE_GASOLINE = PAY_BUSI + "/welfareSociety/recharge/phone-gasoline";

    /**
     * 获取银行卡基本信息
     */
    public static final String BANK_CARD_INFO = PAY_BUSI + "/account/bank-card-info";

    /**
     * 绑定银行卡
     */
    public static final String BIND_BANK_CARD = PAY_BUSI + "/account/bind-bank-card";

    /**
     * 请求提现
     */
    public static final String WITHDRAWAL = PAY_BUSI + "/account/small-change/withdrawal";

    /**
     * 账户绑定银行卡信息
     */
    public static final String BIND_BANK_CARD_INFO = PAY_BUSI + "/account/bind-bank-card-info";
    /*
     * ================================================支付结束================================================
     */


    /*
     * ================================================购物车开始================================================
     */
    /**
     * APP-购物车查询商品列表接口
     */
    public static final String QUERY_CART_GOODS = MALL_BUSI + "/shopping-cart/query-cart-goods";

    /**
     * APP-购物车新增商品接口
     */
    public static final String ADD_GOODS = MALL_BUSI + "/shopping-cart/add-goods";

    /**
     * APP-修改购物车商品数量、规格
     */
    public static final String UPDATE_CART_GOODS_COUNT = MALL_BUSI + "/shopping-cart/update-cart-goods-count";

    /**
     * APP-删除购物车商品
     */
    public static final String DELETE_GOODS = MALL_BUSI + "/shopping-cart/delete-goods";

    /**
     * APP-移入收藏夹
     */
    public static final String MOVE_FAVORITES = MALL_BUSI + "/shopping-cart/move-favorites";

    /**
     * 购物车下单
     */
    public static final String CART_SETTLE = MALL_BUSI + "/shopping-cart/cart-settle";
    /*
     * ================================================购物车结束================================================
     */

    /*
     * ================================================直播开始================================================
     */
    /**
     * 商城获取推荐直播
     */
    public static final String FIRST_LIVE_RECOMMEND = ENTE_BUSI + "/recommendation/live/first-live-recommendation";

    public static final String DEFAULT_LIVE_RECOMMEND = ENTE_BUSI + "/recommendation/live/default-live-recommendation";

    public static final String createLive = ENTE_BUSI + "/resource/live/create-live";


    public static final String isOpenLivingRoom = ENTE_BUSI + "/resource/user/is-open-living-room";
    public static final String authentication = ENTE_BUSI + "/resource/user/real-name-authentication";
    public static final String upload = ENTE_BUSI + "/resource/file/upload";
    public static final String uploadlistbytype = ENTE_BUSI + "/resource/file/upload-list-by-type";
    public static final String goodslist = ENTE_BUSI + "/goods/obtain-goods-list";
    public static final String cartgoodslist = ENTE_BUSI + "/goods/shopping-cart-goods-list/{liveId}";
    public static final String explainlivegoods = ENTE_BUSI + "/goods/explain-live-goods/{livingRoomId}/{liveGoodsId}";
    public static final String updatelivegoodssort = ENTE_BUSI + "/goods/update-live-goods-sort";
    public static final String closeliving = ENTE_BUSI + "/resource/live/close-living";
    public static final String getinto = ENTE_BUSI + "/resource/live/get-into";
    public static final String getout = ENTE_BUSI + "/resource/live/get-out";
    public static final String addCharm = ENTE_BUSI + "/resource/live/add-charm";
    public static final String follow = ENTE_BUSI + "/interactive/follow/follow";
    public static final String myFollowState = ENTE_BUSI + "/interactive/follow/my-follow-state";
    public static final String listcharm = ENTE_BUSI + "/resource/live/list-charm";
    public static final String liveconcern = ENTE_BUSI + "/recommendation/live/concern";
    public static final String followconcern = ENTE_BUSI + "/recommendation/vod/concern";
    public static final String personalsig = ENTE_BUSI + "/personal/personal-sig";

    /**
     * 直播带货 --  商城商品下单
     */
    public static final String GENERATE_GOODS_ORDER = ENTE_BUSI + "/transaction/generate-goods-order/for-biz/{bizId}";

    /**
     * 获取推荐短视频
     */
    public static final String LAUNCH_LIS = ENTE_BUSI + "/resource/launch/list";
    /*
     * ================================================直播结束================================================
     */

    /**
     * 我的直播场次
     */
    public static final String MY_LIVE_LIST = ENTE_BUSI + "/statistics/my-live-list";

    /**
     * 我的粉丝/关注
     */
    public static final String MY_FOLLOW_LIST = ENTE_BUSI + "/interactive/follow/my-follow-list";


    /**
     * 获取小程序邀请二维码
     */
    public static final String GET_WXCODE = MALL_BUSI + "/buyer/invite/get-wxcode";

    /*
     * ================================================短视频开始================================================
     */

    /**
     * 获取个人信息
     */
    public static final String PERSONAL_INFO = ENTE_BUSI + "/personal/personal-info";

    /**
     * 我的作品列表
     */
    public static final String MY_VOD_LIST = ENTE_BUSI + "/resource/vod/my-vod-list";

    /**
     * 我的点赞列表
     */
    public static final String LIKE_VOD_LIST = ENTE_BUSI + "/interactive/like/my-vod-list";

    /**
     * 单个短视频信息详情
     */
    public static final String VOD_INFO = ENTE_BUSI + "/resource/vod/info/{vodId}";

    /*
     * ================================================短视频结束================================================
     */
}

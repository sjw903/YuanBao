package com.yuanbaogo.mine.order.finish.refund;

import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.order.finish.refund.model.UploadListBean;
import com.yuanbaogo.mine.order.model.GoodsDetail;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 5:27 PM
 * @Modifier:
 * @Modify:
 */
public interface SalesReturnRefundContract {

    interface View extends IBaseView {
        void showOrderDetail(GoodsDetail goodsDetail);

        void returnRefundResult(boolean result);

        void showImageList(List<UploadListBean> bean);
    }

    interface Presenter extends IBasePresenter {
        void getOrderDetail(String totalOrderId);

        /**
         * 退货
         *
         * @param afterSalePrice 退款金额
         * @param des            详细描述
         * @param goodsStatus    货物状态,仅退款时传此参数,1-未收到货 2-已收到货
         * @param orderId        订单id
         * @param reasonType     退货退款原因类型,1-7天无理由退货退款 2-商品损坏/包装脏污 3-少/错商品 4-发错货 5-商品与页面描述不符 6-质量问题
         * @param salesType      售后服务类型,1 退货退款 2 退款
         * @param urlList        图片
         */
        void returnRefund(String afterSalePrice, String des, int goodsStatus, String orderId, int reasonType, int salesType, List<String> urlList);

        void uploadImageList(List<LocalMedia> localMediaList);
    }

}


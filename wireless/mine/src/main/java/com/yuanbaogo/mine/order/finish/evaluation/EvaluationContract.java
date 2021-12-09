package com.yuanbaogo.mine.order.finish.evaluation;

import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.order.finish.refund.model.UploadListBean;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.model.GoodsVOListItem;

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
public interface EvaluationContract {

    interface View extends IBaseView {
        void showEvaluationResult(boolean result);

        void showOrderDetail(GoodsDetail goodsDetail);

        void showImageList(List<UploadListBean> bean);
    }

    interface Presenter extends IBasePresenter {

        /**
         * 发表商品评价
         *
         * @param goodsVOListItemList /
         * @param serviceRating       服务评价
         * @param logisticsRating     物流评价
         * @param totalOrderId        订单id
         */
        void onEvaluation(List<GoodsVOListItem> goodsVOListItemList, float serviceRating, float logisticsRating,
                          String totalOrderId, List<String> urlList);

        void getOrderDetail(String totalOrderId);

        void uploadImageList(List<LocalMedia> localMediaList);

    }

}


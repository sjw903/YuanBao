package com.yuanbaogo.mine.setting.bill.head;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

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
public interface BillHeadContract {

    interface View extends IBaseView {

        void loadUnitBillList(List<String> billList);

        void loadPersonalBillList(List<String> billList);

        void loadFail(Throwable throwable);
    }

    interface Presenter extends IBasePresenter {

        /**
         * 加载公司抬头
         */
        void loadUnitBillList();

        /**
         * 加载个人抬头
         */
        void loadPersonalBillList();
    }

}


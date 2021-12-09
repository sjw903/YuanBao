package com.yuanbaogo.mine.groupaccount.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.groupaccount.model.GroupAccountMoneyBean;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/28/21 2:47 PM
 * @Modifier:
 * @Modify:
 */
public interface GroupAccountContract {

    interface View extends IBaseView {

        void setFindAreaInfo(GroupAccountMoneyBean bean);

        void initFindAreaInfo();

    }

    interface Presenter extends IBasePresenter {

        void getFindAreaInfo(int areaType, String ybCode);

    }

}

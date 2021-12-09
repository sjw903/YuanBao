package com.yuanbaogo.mine.live.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.live.model.LiveAmountItem;

import java.util.List;

public interface LiveAmountFragmentContract {
    interface View extends IBaseView {
        void showLiveAmount(List<LiveAmountItem> liveAmountItems);

        void loadFail(Throwable throwable);
    }

    interface Presenter extends IBasePresenter {
        void getMyLiveList(int page, String type);
    }
}

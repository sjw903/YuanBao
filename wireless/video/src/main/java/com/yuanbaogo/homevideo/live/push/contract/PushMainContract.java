package com.yuanbaogo.homevideo.live.push.contract;


import android.text.TextWatcher;

import com.yuanbaogo.homevideo.live.push.model.CreateLiveBean;
import com.yuanbaogo.homevideo.live.push.model.ExplainGoodsBean;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

import java.io.File;
import java.util.List;

public interface PushMainContract {

    interface View extends IBaseView {

        void setLiveTitleNum(int textNum);

        void startPush(String url);

        void startLive(CreateLiveBean bean);

        void setCoverImg(String thumbUrl);

        void changeTimer(int time);

        void setExplainCard(ExplainGoodsBean bean);

        void destroy();



    }

    interface Presenter extends IBasePresenter {

        void uploadPic(String path, File file, String mimeType);

        TextWatcher getTextWatcher();
        void setGoodsList(List<String> list);
        void createLive(String title);
        void toCartGoods();

    }

}

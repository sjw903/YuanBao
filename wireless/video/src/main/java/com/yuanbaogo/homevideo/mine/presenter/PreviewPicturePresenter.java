package com.yuanbaogo.homevideo.mine.presenter;

import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.remote.model.UpLoadFileBean;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.homevideo.mine.contract.PreviewPictureContract;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreviewPicturePresenter extends MvpBasePersenter<PreviewPictureContract.View>
        implements PreviewPictureContract.Presenter {

    @Override
    public void getUpdatePortrait(String path, File file, String mimeType) {
        UpLoadFileBean bean = new UpLoadFileBean();
        List<UpLoadFileBean> fileList = new ArrayList();
        bean.setFileName(file.getName());
        bean.setMediaType(mimeType);
        bean.setPath(path);
        bean.setFileKey("image");
        fileList.add(bean);
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance()
                .upLoadFile(getContext(),
                        ChildUrl.UPDATE_BG,
                        params,
                        fileList,
                        new RequestSateListener<String>() {
                            @Override
                            public void onSuccess(int code, String bean) {
                                if (code == 200 && getView() != null) {
                                    getView().setUpdatePortrait(bean);
                                }
                            }

                            @Override
                            public void onFailure(Throwable e) {
                                if (getView() != null) {

                                }
                            }
                        });
    }

}

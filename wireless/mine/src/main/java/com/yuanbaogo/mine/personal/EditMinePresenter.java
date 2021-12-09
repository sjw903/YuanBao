package com.yuanbaogo.mine.personal;

import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.remote.model.UpLoadFileBean;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.personal.model.PersonalSubmitBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditMinePresenter extends MvpBasePersenter<EditMineContract.View>
        implements EditMineContract.Presenter {

    /**
     * 修改用户信息
     *
     * @param bean
     */
    @Override
    public void getPersonalSubmit(PersonalSubmitBean bean) {
        Map<String, Object> params = new HashMap<>();
        params.put("address", bean.getAddress());
        params.put("nickName", bean.getNickName());
        params.put("signature", bean.getSignature());
        NetWork.getInstance().post(getContext(),
                ChildUrl.PERSONAL_SUBMIT,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        if (code == 200 && getView() != null) {
                            getView().setPersonalSubmit(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initPersonalSubmit();
                        }
                    }
                });
    }

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
                        ChildUrl.UPDATE_PORTRAIT,
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

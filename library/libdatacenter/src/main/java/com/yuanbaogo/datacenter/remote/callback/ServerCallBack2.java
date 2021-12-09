package com.yuanbaogo.datacenter.remote.callback;


import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.RequestConfiguration;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.network.callback.OkCallback;
import com.yuanbaogo.network.exception.HttpException;
import com.yuanbaogo.network.exception.ServiceException;
import com.yuanbaogo.network.parser.BaseParser;
import com.yuanbaogo.router.YBRouter;

import java.io.IOException;
import java.net.UnknownHostException;

public class ServerCallBack2<T> extends OkCallback<T> {
    protected RequestConfiguration mConfig;
    private RequestSateListener mListener;

    public ServerCallBack2(BaseParser<T> mParser) {
        super(mParser);
    }

    public ServerCallBack2(RequestSateListener listener, BaseParser<T> mParser) {
        super(mParser);
        this.mListener = listener;
    }

    public ServerCallBack2(BaseParser<T> mParser, RequestSateListener mListener, RequestConfiguration mConfig) {
        super(mParser);
        this.mListener = mListener;
        this.mConfig = mConfig;
    }

    public void onStart() {
        super.onStart();
        if (this.mListener != null) {
            sHandler.post(new Runnable() {
                public void run() {
                    ServerCallBack2.this.mListener.onStart();
                }
            });
        }
    }

    public void onSuccess(int codze, T t) {
    }

    public void onFailure(Throwable e) {
        if (e instanceof HttpException) {
            if (401 == ((HttpException) e).getStatusCode()) {
                UserStore.cleanUser();
                ARouter.getInstance().build(YBRouter.LOGIN_ACTIVITY).navigation();
            }else if(403 == ((HttpException) e).getStatusCode()){
            }else{
                if (!TextUtils.isEmpty(e.getMessage())) {
                    try {
                        ErrorBean object = JSON.parseObject(e.getMessage(), ErrorBean.class);
                        if (object != null) {
                            this.showMessage(e, object.getStatus(), object.getMessage());
                        }
                    } catch (Exception exception) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (!(e instanceof IOException)) {
            if (e instanceof ServiceException) {
                this.showMessage(e, "", e.getMessage());
                ServiceException se = (ServiceException) e;
//                if (se.getCode() == -1) {
//                    MessageHandlerHelper.handleMessage("40005");
//                }
            } else if (e instanceof UnknownHostException) {
                this.showMessage(e, "", "网络请求失败，请检查网络连接");
            } else {
                this.showMessage(e, "", "服务器请求异常！");
            }
        }
    }

    protected void showMessage(Throwable t, String code, String msg) {
        if (this.mListener == null || !this.mListener.onHandleMessage(t, code, msg)) {
            if (TextUtils.isEmpty(msg)) {
                ToastUtil.showToast(code);
            } else {
                ToastUtil.showToast(msg);
            }

        }

    }


}

package com.yuanbaogo.datacenter.remote.callback;


import com.yuanbaogo.datacenter.remote.RequestConfiguration;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.network.callback.OkCallback;
import com.yuanbaogo.network.parser.BaseParser;


public class ServerCallBack<T> extends OkCallback<T> {
    protected RequestConfiguration mConfig;
    private RequestSateListener mListener;

    public ServerCallBack(BaseParser<T> mParser) {
        super(mParser);
    }

    public ServerCallBack(RequestSateListener listener, BaseParser<T> mParser) {
        super(mParser);
        this.mListener = listener;
    }

    public ServerCallBack(BaseParser<T> mParser, RequestSateListener mListener, RequestConfiguration mConfig) {
        super(mParser);
        this.mListener = mListener;
        this.mConfig = mConfig;
    }

    public void onStart() {
        super.onStart();
        if (this.mListener != null) {
            sHandler.post(new Runnable() {
                public void run() {
                    ServerCallBack.this.mListener.onStart();
                }
            });
        }

    }

    public void onSuccess(int code, T t) {
    }

    public void onFailure(Throwable e) {

    }



}

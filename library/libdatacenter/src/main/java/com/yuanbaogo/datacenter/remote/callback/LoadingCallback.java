package com.yuanbaogo.datacenter.remote.callback;

import android.app.Activity;
import android.content.Context;

import com.yuanbaogo.datacenter.progress.LodingProgressDialog;
import com.yuanbaogo.datacenter.remote.RequestConfiguration;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.network.parser.BaseParser;

public class LoadingCallback<T> extends ServerCallBack2<T> {
    protected Context mContext;

    public LoadingCallback(Context context, BaseParser<T> mParser) {
        super(mParser);
        this.mContext = context;
    }

    public LoadingCallback(Context context, RequestSateListener listener, BaseParser<T> mParser) {
        super(listener, mParser);
        this.mContext = context;
    }

    public LoadingCallback(Context context, RequestSateListener listener, BaseParser<T> mParser, RequestConfiguration mConfig) {
        super(mParser, listener, mConfig);
        this.mContext = context;
    }

    public void onSuccess(int code, T t) {
        super.onSuccess(code, t);
        this.dismissProgress();
    }

    public void onFailure(Throwable e) {
        super.onFailure(e);
        this.dismissProgress();
    }

    public void onStart() {
        super.onStart();
        sHandler.post(new Runnable() {
            public void run() {
                LoadingCallback.this.showProgress();
            }
        });
    }

    public boolean showProgress() {
        if (this.mContext == null) {
            return false;
        } else if (this.mConfig != null && !this.mConfig.isShowProgress()) {
            return false;
        } else {
            if (LodingProgressDialog.getDialog() != null && LodingProgressDialog.isShowing() && this.mContext instanceof Activity) {
                LodingProgressDialog.dismiss();
            }

            LodingProgressDialog.show(this.mContext, (String)null, false, true);
            return true;
        }
    }

    public void dismissProgress() {
        if (this.mContext != null) {
            if (this.mConfig == null || this.mConfig.isShowProgress()) {
                if (LodingProgressDialog.getDialog() != null && LodingProgressDialog.isShowing()) {
                    LodingProgressDialog.dismiss();
                }

            }
        }
    }
}

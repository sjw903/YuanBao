package com.yuanbaogo.homevideo.live.push.listener;

import android.os.Bundle;

import com.tencent.live2.V2TXLivePusherObserver;

public class LivePusherObserver extends V2TXLivePusherObserver {
    private OnLivePusherListener mListener;

    public interface OnLivePusherListener {
        void onError(String msg);

        void onWarning(String msg);
    }

    public LivePusherObserver(OnLivePusherListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onError(int code, String msg, Bundle extraInfo) {
        super.onError(code, msg, extraInfo);
        if (mListener != null) mListener.onError(msg);
    }

    @Override
    public void onWarning(int code, String msg, Bundle extraInfo) {
        super.onWarning(code, msg, extraInfo);
        if (mListener != null) mListener.onWarning(msg);
    }
}

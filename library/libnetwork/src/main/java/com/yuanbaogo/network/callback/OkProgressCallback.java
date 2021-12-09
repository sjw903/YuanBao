package com.yuanbaogo.network.callback;

import android.os.Handler;
import android.os.Looper;

public abstract class OkProgressCallback {
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public OkProgressCallback() {
    }

    public void onOKProgress(final long current, final long total) {
        sHandler.post(new Runnable() {
            public void run() {
                OkProgressCallback.this.onProgress(current, total);
            }
        });
    }

    public abstract void onProgress(long var1, long var3);
}
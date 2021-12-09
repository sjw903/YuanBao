package com.yuanbaogo.network.callback;

import android.os.Handler;
import android.os.Looper;

import com.yuanbaogo.network.exception.HttpException;
import com.yuanbaogo.network.parser.BaseParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class OkCallback<T> implements Callback {
    protected static Handler sHandler = new Handler(Looper.getMainLooper());
    private BaseParser<T> mParser;

    public OkCallback(BaseParser<T> mParser) {
        if (mParser == null) {
            throw new IllegalArgumentException("Parser can't be null");
        } else {
            this.mParser = mParser;
        }
    }

    public void onFailure(Call call, final IOException e) {
        sHandler.post(new Runnable() {
            public void run() {
                OkCallback.this.onFailure(e);
            }
        });
    }

    public void onResponse(Call call, Response response) {
        try {
            final T t = this.mParser.parseResponse(response);
            final int code = this.mParser.getCode();
            final String message = this.mParser.getMessage();
            if (response.isSuccessful()) {
                sHandler.post(new Runnable() {
                    public void run() {
                        OkCallback.this.onSuccess(code, t);
                    }
                });
            } else {
                sHandler.post(new Runnable() {
                    public void run() {
                        OkCallback.this.onFailure(new HttpException(code, t.toString()));
                    }
                });
            }
        } catch (final Exception var6) {
            sHandler.post(new Runnable() {
                public void run() {
                    OkCallback.this.onFailure(var6);
                }
            });
        }

    }

    public abstract void onSuccess(int var1, T var2);

    public abstract void onFailure(Throwable var1);

    public void onStart() {
    }
}

package com.yuanbaogo.network.body;


import com.yuanbaogo.network.callback.OkProgressCallback;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BodyWrapper {
    public BodyWrapper() {
    }

    public static OkHttpClient addProgressResponseListener(OkHttpClient client, final OkProgressCallback progressCallback) {
        return client.newBuilder().addNetworkInterceptor(new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(new ResponseProgressBody(originalResponse.body(), progressCallback)).build();
            }
        }).build();
    }

    public static RequestProgressBody addProgressRequestListener(RequestBody requestBody, OkProgressCallback progressCallback) {
        return new RequestProgressBody(requestBody, progressCallback);
    }
}


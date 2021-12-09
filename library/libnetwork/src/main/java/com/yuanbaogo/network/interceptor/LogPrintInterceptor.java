package com.yuanbaogo.network.interceptor;


import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LogPrintInterceptor implements Interceptor {

    private static String TAG = "network";


    public LogPrintInterceptor() {
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.i(TAG, "===" + request.url());
        Response response = chain.proceed(request);
        Log.i(TAG, "===" + response.code() + ":" + response.message());
        Log.i(TAG, "===" + response.peekBody(1024L).string());

        return response;
    }
}

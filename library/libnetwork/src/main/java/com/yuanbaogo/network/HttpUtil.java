package com.yuanbaogo.network;


import com.yuanbaogo.network.builder.DeleteRequestBuilder;
import com.yuanbaogo.network.builder.GetRequestBuilder;
import com.yuanbaogo.network.builder.PostRequestBuilder;
import com.yuanbaogo.network.builder.PutRequestBuilder;
import com.yuanbaogo.network.interceptor.LogInterceptor;
import com.yuanbaogo.network.interceptor.LogPrintInterceptor;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

public class HttpUtil {
    private static OkHttpClient httpClient;
    private static Builder httpClientBuilder;

    public HttpUtil() {
    }

    private static OkHttpClient init() {

        synchronized(HttpUtil.class) {
            if (httpClient == null) {
                if (httpClientBuilder == null) {
                    httpClientBuilder = (new Builder()).connectTimeout(20L, TimeUnit.SECONDS).writeTimeout(20L, TimeUnit.SECONDS).readTimeout(25L, TimeUnit.SECONDS);
                }
                httpClientBuilder.addInterceptor(new LogInterceptor());
                httpClient = httpClientBuilder.build();

            }
        }

        return httpClient;
    }

    public static OkHttpClient getInstance() {
        return httpClient == null ? init() : httpClient;
    }

    public static Builder getInstanceBuilder() {
        if (httpClientBuilder == null) {
            httpClientBuilder = (new Builder()).connectTimeout(20L, TimeUnit.SECONDS).writeTimeout(20L, TimeUnit.SECONDS).readTimeout(30L, TimeUnit.SECONDS);
        }

        return httpClientBuilder;
    }

    public static void setInstance(OkHttpClient okHttpClient) {
        httpClient = okHttpClient;
    }

    public static GetRequestBuilder get(String url) {
        return (new GetRequestBuilder()).url(url);
    }

    public static PostRequestBuilder post(String url) {
        return (new PostRequestBuilder()).url(url);
    }

    public static PutRequestBuilder put(String url) {
        return (new PutRequestBuilder()).url(url);
    }

    public static DeleteRequestBuilder delete(String url) {
        return (new DeleteRequestBuilder()).url(url);
    }

    public static void cancel(Object tag) {
        Dispatcher dispatcher = getInstance().dispatcher();
        Iterator var2 = dispatcher.queuedCalls().iterator();

        Call call;
        while(var2.hasNext()) {
            call = (Call)var2.next();
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }

        var2 = dispatcher.runningCalls().iterator();

        while(var2.hasNext()) {
            call = (Call)var2.next();
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }

    }

    public static void isLog(boolean isLog) {
        if (isLog) {
            getInstanceBuilder().addInterceptor(new LogInterceptor());
        }

    }

    public static void isPrintLog(boolean isPrint) {
        if (isPrint) {
            getInstanceBuilder().addInterceptor(new LogPrintInterceptor());
        }

    }

}

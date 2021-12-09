package com.yuanbaogo.network.builder;

import android.text.TextUtils;

import com.yuanbaogo.network.HttpUtil;
import com.yuanbaogo.network.body.BodyWrapper;
import com.yuanbaogo.network.callback.OkCallback;
import com.yuanbaogo.network.callback.OkProgressCallback;

import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class RequestBuilder {
    protected Builder multipartBodyBuilder;
    protected RequestBody requestBody;
    protected OkProgressCallback reqProgressCallback;
    protected OkProgressCallback rspProgressCallback;
    protected int readTimeOut;
    protected int writeTimeOut;
    protected String url;
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected Object tag;

    public RequestBuilder() {
    }

    abstract Call enqueue(Callback var1);

    abstract Response execute() throws IOException;

    public RequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    public RequestBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public RequestBuilder addParam(String key, String value) {
        if (this.params == null) {
            this.params = new IdentityHashMap();
        }

        this.params.put(key, value);
        return this;
    }

    public RequestBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public RequestBuilder addHeader(String key, String values) {
        if (this.headers == null) {
            this.headers = new IdentityHashMap();
        }

        this.headers.put(key, values);
        return this;
    }

    public RequestBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    protected void appendHeaders(okhttp3.Request.Builder builder, Map<String, String> headers) {
        okhttp3.Headers.Builder headerBuilder = new okhttp3.Headers.Builder();
        if (headers != null && !headers.isEmpty()) {
            String key;
            String value;
            for(Iterator var4 = headers.keySet().iterator(); var4.hasNext(); headerBuilder.add(key, value)) {
                key = (String)var4.next();
                value = (String)headers.get(key);
                if (value == null) {
                    value = "";
                }
            }

            builder.headers(headerBuilder.build());
        }
    }

    protected void appendParams(okhttp3.FormBody.Builder builder, Map<String, String> params) {
        String key;
        String value;
        if (params != null && !params.isEmpty()) {
            for(Iterator var3 = params.keySet().iterator(); var3.hasNext(); builder.add(key, value)) {
                key = (String)var3.next();
                value = (String)params.get(key);
                if (value == null) {
                    value = "";
                }
            }
        }

    }

    protected String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        if (url.contains("?")) {
            sb.append(url + "&");
        } else {
            sb.append(url + "?");
        }

        if (params != null && !params.isEmpty()) {
            Iterator var4 = params.keySet().iterator();

            while(var4.hasNext()) {
                String key = (String)var4.next();
                sb.append(key).append("=").append(params.get(key) == null ? "" : (String)params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    protected void makeBody(String method, okhttp3.Request.Builder builder) {
        okhttp3.FormBody.Builder encodingBuilder;
        if (this.reqProgressCallback != null) {
            if (this.multipartBodyBuilder != null) {
                builder.method(method, BodyWrapper.addProgressRequestListener(this.multipartBodyBuilder.build(), this.reqProgressCallback));
            } else if (this.requestBody != null) {
                builder.method(method, BodyWrapper.addProgressRequestListener(this.requestBody, this.reqProgressCallback));
            } else {
                encodingBuilder = new okhttp3.FormBody.Builder();
                this.appendParams(encodingBuilder, this.params);
                builder.method(method, BodyWrapper.addProgressRequestListener(encodingBuilder.build(), this.reqProgressCallback));
            }
        } else if (this.multipartBodyBuilder != null) {
            builder.method(method, this.multipartBodyBuilder.build());
        } else if (this.requestBody != null) {
            builder.method(method, this.requestBody);
        } else {
            encodingBuilder = new okhttp3.FormBody.Builder();
            this.appendParams(encodingBuilder, this.params);
            builder.method(method, encodingBuilder.build());
        }

    }

    protected OkHttpClient cloneClient() {
        OkHttpClient okHttpClient = null;
        if (this.writeTimeOut * this.readTimeOut > 0) {
            okhttp3.OkHttpClient.Builder okHttpClientBuilder = HttpUtil.getInstance().newBuilder();
            if (this.writeTimeOut > 0) {
                okHttpClientBuilder.writeTimeout((long)this.writeTimeOut, TimeUnit.SECONDS);
            }

            if (this.readTimeOut > 0) {
                okHttpClientBuilder.readTimeout((long)this.readTimeOut, TimeUnit.SECONDS);
            }

            okHttpClient = okHttpClientBuilder.build();
        }

        if (okHttpClient == null) {
            okHttpClient = HttpUtil.getInstance();
        }

        if (this.rspProgressCallback != null) {
            okHttpClient = BodyWrapper.addProgressResponseListener(okHttpClient, this.rspProgressCallback);
        }

        return okHttpClient;
    }

    protected Call enqueue(String method, Callback callback) {
        if (TextUtils.isEmpty(this.url)) {
            throw new IllegalArgumentException("url can not be null !");
        } else {
            okhttp3.Request.Builder builder = (new okhttp3.Request.Builder()).url(this.url);
            if (this.tag != null) {
                builder.tag(this.tag);
            }

            this.appendHeaders(builder, this.headers);
            this.makeBody(method, builder);
            Request request = builder.build();
            Call call = this.cloneClient().newCall(request);
            call.enqueue(callback);
            if (callback instanceof OkCallback) {
                ((OkCallback)callback).onStart();
            }

            return call;
        }
    }

    protected Response execute(String method) throws IOException {
        if (TextUtils.isEmpty(this.url)) {
            throw new IllegalArgumentException("url can not be null !");
        } else {
            okhttp3.Request.Builder builder = (new okhttp3.Request.Builder()).url(this.url);
            if (this.tag != null) {
                builder.tag(this.tag);
            }

            this.appendHeaders(builder, this.headers);
            this.makeBody(method, builder);
            Request request = builder.build();
            Call call = this.cloneClient().newCall(request);
            return call.execute();
        }
    }
}

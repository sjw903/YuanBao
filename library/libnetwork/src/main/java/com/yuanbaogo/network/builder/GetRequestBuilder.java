package com.yuanbaogo.network.builder;

import android.text.TextUtils;

import com.yuanbaogo.network.callback.OkCallback;
import com.yuanbaogo.network.callback.OkProgressCallback;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class GetRequestBuilder extends RequestBuilder {
    public GetRequestBuilder() {
    }

    public GetRequestBuilder addRespProgressCallback(OkProgressCallback rspProgressCallback) {
        this.rspProgressCallback = rspProgressCallback;
        return this;
    }

    public GetRequestBuilder url(String url) {
        super.url(url);
        return this;
    }

    public GetRequestBuilder params(Map<String, String> params) {
        super.params(params);
        return this;
    }

    public GetRequestBuilder addParam(String key, String value) {
        super.addParam(key, value);
        return this;
    }

    public GetRequestBuilder headers(Map<String, String> headers) {
        super.headers(headers);
        return this;
    }

    public GetRequestBuilder addHeader(String key, String values) {
        super.addHeader(key, values);
        return this;
    }

    public GetRequestBuilder setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public GetRequestBuilder setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public GetRequestBuilder tag(Object tag) {
        super.tag(tag);
        return this;
    }

    public Call enqueue(Callback callback) {
        if (TextUtils.isEmpty(this.url)) {
            throw new IllegalArgumentException("url can not be null !");
        } else {
            if (this.params != null && this.params.size() > 0) {
                this.url = this.appendParams(this.url, this.params);
            }

            Builder builder = (new Builder()).url(this.url);
            if (this.tag != null) {
                builder.tag(this.tag);
            }

            this.appendHeaders(builder, this.headers);
            Request request = builder.build();
            if (callback instanceof OkCallback) {
                ((OkCallback)callback).onStart();
            }

            Call call = this.cloneClient().newCall(request);
            call.enqueue(callback);
            return call;
        }
    }

    public Response execute() throws IOException {
        if (TextUtils.isEmpty(this.url)) {
            throw new IllegalArgumentException("url can not be null !");
        } else {
            if (this.params != null && this.params.size() > 0) {
                this.url = this.appendParams(this.url, this.params);
            }

            Builder builder = (new Builder()).url(this.url);
            if (this.tag != null) {
                builder.tag(this.tag);
            }

            this.appendHeaders(builder, this.headers);
            Request request = builder.build();
            Call call = this.cloneClient().newCall(request);
            return call.execute();
        }
    }
}
package com.yuanbaogo.network.builder;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DeleteRequestBuilder extends RequestBuilder {
    protected RequestBody requestBody;

    public DeleteRequestBuilder() {
    }

    public DeleteRequestBuilder url(String url) {
        super.url(url);
        return this;
    }

    public DeleteRequestBuilder params(Map<String, String> params) {
        super.params(params);
        return this;
    }

    public DeleteRequestBuilder addParam(String key, String value) {
        super.addParam(key, value);
        return this;
    }

    public DeleteRequestBuilder headers(Map<String, String> headers) {
        super.headers(headers);
        return this;
    }

    public DeleteRequestBuilder addHeader(String key, String values) {
        super.addHeader(key, values);
        return this;
    }

    public DeleteRequestBuilder setBodyString(String body) {
        return this.setBodyString("text/plain", body);
    }

    public DeleteRequestBuilder setBodyString(String mediaType, String body) {
        this.requestBody = RequestBody.create(MediaType.parse(mediaType), body);
        return this;
    }

    public DeleteRequestBuilder tag(Object tag) {
        super.tag(tag);
        return this;
    }

    public Call enqueue(Callback callback) {
        return this.enqueue("DELETE", callback);
    }

    public Response execute() throws IOException {
        return this.execute("DELETE");
    }
}

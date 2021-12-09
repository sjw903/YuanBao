package com.yuanbaogo.network.builder;


import com.yuanbaogo.network.callback.OkProgressCallback;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PutRequestBuilder extends RequestBuilder {
    public PutRequestBuilder() {
    }

    public PutRequestBuilder url(String url) {
        super.url(url);
        return this;
    }

    public PutRequestBuilder params(Map<String, String> params) {
        super.params(params);
        return this;
    }

    public PutRequestBuilder addParam(String key, String value) {
        super.addParam(key, value);
        return this;
    }

    public PutRequestBuilder headers(Map<String, String> headers) {
        super.headers(headers);
        return this;
    }

    public PutRequestBuilder addHeader(String key, String values) {
        super.addHeader(key, values);
        return this;
    }

    public PutRequestBuilder setBodyString(String body) {
        return this.setBodyString("text/plain", body);
    }

    public PutRequestBuilder setBodyString(String mediaType, String body) {
        this.requestBody = RequestBody.create(MediaType.parse(mediaType), body);
        return this;
    }

    public PutRequestBuilder addFormDataPart(String key, String value) {
        if (this.multipartBodyBuilder == null) {
            this.multipartBodyBuilder = (new Builder()).setType(MultipartBody.FORM);
        }

        this.multipartBodyBuilder.addFormDataPart(key, value);
        return this;
    }

    public PutRequestBuilder addFormDataPart(String key, String filename, String mediaType, String file) {
        if (this.multipartBodyBuilder == null) {
            this.multipartBodyBuilder = (new Builder()).setType(MultipartBody.FORM);
        }

        this.multipartBodyBuilder.addFormDataPart(key, filename, RequestBody.create(MediaType.parse(mediaType), new File(file)));
        return this;
    }

    public PutRequestBuilder setBodyBytes(byte[] content) {
        return this.setBodyBytes((String)null, content);
    }

    public PutRequestBuilder setBodyBytes(String mediaType, byte[] content) {
        MediaType media = null;
        if (mediaType == null) {
            media = null;
        } else {
            media = MediaType.parse(mediaType);
        }

        this.requestBody = RequestBody.create(media, content);
        return this;
    }

    public PutRequestBuilder setBodyFile(File file) {
        return this.setBodyFile((String)null, file);
    }

    public PutRequestBuilder setBodyFile(String mediaType, File file) {
        MediaType media = null;
        if (mediaType == null) {
            media = null;
        } else {
            media = MediaType.parse(mediaType);
        }

        this.requestBody = RequestBody.create(media, file);
        return this;
    }

    public PutRequestBuilder addReqProgressCallback(OkProgressCallback reqProgressCallback) {
        this.reqProgressCallback = reqProgressCallback;
        return this;
    }

    public PutRequestBuilder setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public PutRequestBuilder setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public PutRequestBuilder tag(Object tag) {
        super.tag(tag);
        return this;
    }

    public Call enqueue(Callback callback) {
        return this.enqueue("PUT", callback);
    }

    public Response execute() throws IOException {
        return this.execute("PUT");
    }
}

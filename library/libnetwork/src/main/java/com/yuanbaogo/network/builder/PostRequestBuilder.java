package com.yuanbaogo.network.builder;


import com.yuanbaogo.network.callback.OkProgressCallback;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostRequestBuilder extends RequestBuilder {
    public PostRequestBuilder() {
    }

    public PostRequestBuilder url(String url) {
        super.url(url);
        return this;
    }

    public PostRequestBuilder params(Map<String, String> params) {
        super.params(params);
        return this;
    }

    public PostRequestBuilder addParam(String key, String value) {
        super.addParam(key, value);
        return this;
    }

    public PostRequestBuilder headers(Map<String, String> headers) {
        super.headers(headers);
        return this;
    }

    public PostRequestBuilder addHeader(String key, String values) {
        super.addHeader(key, values);
        return this;
    }

    public PostRequestBuilder tag(Object tag) {
        super.tag(tag);
        return this;
    }

    public PostRequestBuilder addFormDataPart(String key, String value) {
        if (this.multipartBodyBuilder == null) {
            this.multipartBodyBuilder = (new Builder()).setType(MultipartBody.FORM);
        }

        this.multipartBodyBuilder.addFormDataPart(key, value);
        return this;
    }

    public PostRequestBuilder addFormDataParts(Map<String, Object> params) {
        if (this.multipartBodyBuilder == null) {
            this.multipartBodyBuilder = (new Builder()).setType(MultipartBody.FORM);
        }
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                this.multipartBodyBuilder.addFormDataPart(key, params.get(key)+"");
            }
        }
        return this;
    }

    public PostRequestBuilder addFormDataPart(String key, String filename, String mediaType, String file) {
        if (this.multipartBodyBuilder == null) {
            this.multipartBodyBuilder = (new Builder()).setType(MultipartBody.FORM);
        }

        this.multipartBodyBuilder.addFormDataPart(key, filename, RequestBody.create(MediaType.parse(mediaType), new File(file)));
        return this;
    }

    public PostRequestBuilder setBodyString(String body) {
        return this.setBodyString("text/plain", body);
    }

    public PostRequestBuilder setBodyString(String mediaType, String body) {
        this.requestBody = RequestBody.create(MediaType.parse(mediaType), body);
        return this;
    }

    public PostRequestBuilder setBodyBytes(byte[] content) {
        return this.setBodyBytes((String)null, content);
    }

    public PostRequestBuilder setBodyBytes(String mediaType, byte[] content) {
        MediaType media = null;
        if (mediaType == null) {
            media = null;
        } else {
            media = MediaType.parse(mediaType);
        }

        this.requestBody = RequestBody.create(media, content);
        return this;
    }

    public PostRequestBuilder setBodyFile(File file) {
        return this.setBodyFile((String)null, file);
    }

    public PostRequestBuilder setBodyFile(String mediaType, File file) {
        MediaType media = null;
        if (mediaType == null) {
            media = null;
        } else {
            media = MediaType.parse(mediaType);
        }

        this.requestBody = RequestBody.create(media, file);
        return this;
    }

    public PostRequestBuilder setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public PostRequestBuilder setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public PostRequestBuilder addReqProgressCallback(OkProgressCallback reqProgressCallback) {
        this.reqProgressCallback = reqProgressCallback;
        return this;
    }

    public Call enqueue(Callback callback) {
        return this.enqueue("POST", callback);
    }

    public Response execute() throws IOException {
        return this.execute("POST");
    }
}

package com.yuanbaogo.datacenter.remote.helper;

import android.content.Context;

import com.yuanbaogo.datacenter.remote.callback.ServerCallBack;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.remote.parser.DataModelParser;
import com.yuanbaogo.network.HttpUtil;
import com.yuanbaogo.network.builder.PostRequestBuilder;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class UpLoadHelper<T> {


    private Class<T> entityClass;

    public void upLoadFile(Context context, String url, Map<String, String> headers, Map<String, String> params,
                           String fileKey, String filename, String mediaType, String filePath, final RequestSateListener<T> listener) {


        Type type = getClass().getGenericSuperclass();
        if(type instanceof Class){
            this.entityClass = (Class<T>) type;

        }

        ServerCallBack callBack = new ServerCallBack<T>(listener, new DataModelParser(entityClass)) {
            @Override
            public void onSuccess(int code, T output) {
                super.onSuccess(code, output);
                listener.onSuccess(code, output);
            }

            @Override
            public void onFailure(Throwable e) {
                super.onFailure(e);
                listener.onFailure(e);
            }
        };

        PostRequestBuilder prb = HttpUtil.post(url)
                .tag(context);

        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                prb.addHeader(key, headers.get(key));
            }
        }

        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                prb.addFormDataPart(key, params.get(key));
            }
        }

        prb.addFormDataPart(fileKey, filename, mediaType, filePath);

        prb.enqueue(callBack);

    }

}

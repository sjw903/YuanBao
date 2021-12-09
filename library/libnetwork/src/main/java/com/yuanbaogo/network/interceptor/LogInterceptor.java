package com.yuanbaogo.network.interceptor;

import com.yuanbaogo.libbase.baseutil.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LogInterceptor implements Interceptor {
    private static String TAG = "network";
    public LogInterceptor() {
    }

    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        long t1 = System.currentTimeMillis();
        Response response = chain.proceed(request);
        long t2 = System.currentTimeMillis();//收到响应的时间
        //获取requestBody
        String requestString = "";
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            requestString = buffer.readUtf8();
        }

        //获取responseBody
        ResponseBody responseBody = response.peekBody(10240 * 10240);

        if (response.isSuccessful()) {

            LogUtil.logBigData(TAG, String.format("[--> {请求方法:%s} \n{返回码:%s} \n{请求地址:%s} \n(请求时间:%s:ms)]", request.method(), response.code(), request.url(), (t2 - t1)));
            LogUtil.logBigData(TAG, String.format("[--> 请求头: {%s}]", request.headers()));
            LogUtil.logBigData(TAG, String.format("[--> 请求体: %s]", requestString));
            LogUtil.logBigData(TAG, String.format("[<-- 返回数据: %s]\n", responseBody.string()));

        } else {
            LogUtil.logBigData(TAG, String.format("[--> {请求方法:%s} {返回码:%s} {请求地址:%s} ({请求时间%s}ms)]", request.method(), response.code(), request.url(), (t2 - t1)));
            LogUtil.logBigData(TAG, String.format("[--> 请求头: {%s}]", request.headers()));
            LogUtil.logBigData(TAG, String.format("[--> 请求体: %s]", requestString));
            LogUtil.logBigData(TAG, String.format("[<-- 返回信息: %s]\n", responseBody.string()));
        }

        return response;
    }
}

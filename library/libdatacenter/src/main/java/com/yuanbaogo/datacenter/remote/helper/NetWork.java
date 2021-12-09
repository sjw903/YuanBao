package com.yuanbaogo.datacenter.remote.helper;

import android.content.Context;

import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.callback.LoadingCallback;
import com.yuanbaogo.datacenter.remote.callback.ServerCallBack2;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.remote.model.UpLoadFileBean;
import com.yuanbaogo.datacenter.remote.parser.DataArrayParser;
import com.yuanbaogo.datacenter.remote.parser.DataModelParser;
import com.yuanbaogo.datacenter.remote.parser.DataStringParser;
import com.yuanbaogo.datacenter.remote.request.JsonBuildUtil;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.url.http.RootUrl;
import com.yuanbaogo.libbase.baseutil.SystemUtil;
import com.yuanbaogo.network.HttpUtil;
import com.yuanbaogo.network.builder.GetRequestBuilder;
import com.yuanbaogo.network.builder.PostRequestBuilder;
import com.yuanbaogo.network.callback.OkCallback;
import com.yuanbaogo.network.parser.BaseParser;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NetWork<T> {

    private static NetWork netWork;

    static Context context;

    private static NetWork init() {
        synchronized (NetWork.class) {
            if (netWork == null) {
                netWork = new NetWork();
            }
        }
        return netWork;
    }

    public static NetWork getInstance() {
        return netWork == null ? init() : netWork;
    }


    String temp = "http://192.168.1.99:7706";
    String tempx = "http://192.168.1.221:10092";


    public void post(Context context, String url, Map<String, Object> params, final RequestSateListener<T> listener) {
        post(context, url, params, null, listener, false);
    }

    public void post(Context context, String url, Map<String, Object> params, final RequestSateListener<T> listener, boolean isLoading) {
        post(context, url, params, null, listener, isLoading);
    }

    public void post(Object requestObj, Context context, String url, final RequestSateListener<T> listener) {
        post(context, url, null, requestObj, listener, false);
    }

    private void post(Context context, String url, Map<String, Object> params, Object requestObj, final RequestSateListener<T> listener, boolean isLoading) {
        this.context = context;
        BaseParser parser = getParser(listener);
        if (parser == null) {
            return;
        }
        String inputReq = "";
        if (url.equals(ChildUrl.VERIFY_PAY_PASSWORD) || url.equals(ChildUrl.SET_PAY_PASSWORD)) {
            parser.setSpy(false); //返回值暂时不做加密
            inputReq = JsonBuildUtil.getSpyParam(params, requestObj);
        } else {
            inputReq = JsonBuildUtil.getParam(params, requestObj);
        }

        OkCallback callBack;
        if (isLoading) {
            callBack = new LoadingCallback<T>(context, listener, parser) {
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
        } else {
            callBack = new ServerCallBack2<T>(listener, parser) {
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
        }

//        HttpUtil.post(temp + url)
        HttpUtil.post(RootUrl.WEB_ROOT + url)
                .tag(context)
                .headers(getHeader())
                .setBodyString("application/json;charset=UTF-8", inputReq)
                .enqueue(callBack);
    }

    public void postForm(Context context, String url, Map<String, Object> params, final RequestSateListener<T> listener) {
        this.context = context;
        BaseParser parser = getParser(listener);
        if (parser == null) {
            return;
        }
        LoadingCallback callBack = new LoadingCallback<T>(context, listener, parser) {
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

//        HttpUtil.post(temp + url)
        HttpUtil.post(RootUrl.WEB_ROOT + url)
                .tag(context)
                .headers(getHeader())
                .addFormDataParts(params)
                .enqueue(callBack);
    }

    public void get(Context context, String url, Map<String, String> params, final RequestSateListener<T> listener) {
        get(context, url, params, listener, false);
    }

    public void get(Context context, String url, Map<String, String> params, final RequestSateListener<T> listener, boolean isLoading) {
        this.context = context;
        BaseParser parser = getParser(listener);
        if (parser == null) {
            return;
        }
        OkCallback callBack;
        if (isLoading) {
            callBack = new LoadingCallback<T>(context, listener, parser) {
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
        } else {
            callBack = new ServerCallBack2<T>(listener, parser) {
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
        }


//        GetRequestBuilder builder = HttpUtil.get(temp + url);
        GetRequestBuilder builder = HttpUtil.get(RootUrl.WEB_ROOT + url);

        if (null == params || params.isEmpty()) {
            builder.tag(context).headers(getHeader()).enqueue(callBack);
        } else {
            builder.tag(context)
                    .params(params)
                    .headers(getHeader())
                    .enqueue(callBack);
        }
    }

    public void upLoadFile(Context context, String url, Map<String, String> params, List<UpLoadFileBean> fileList,
                           final RequestSateListener<T> listener) {
        this.context = context;
        BaseParser parser = getParser(listener);
        if (parser == null) {
            return;
        }
        LoadingCallback callBack = new LoadingCallback<T>(context, listener, parser) {
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
//        PostRequestBuilder prb = HttpUtil.post(temp + url)
        PostRequestBuilder prb = HttpUtil.post(RootUrl.WEB_ROOT + url)
                .tag(context);
//        if (headers != null && headers.size() > 0) {
//            Set<String> keys = headers.keySet();
//            for (String key : keys) {
//                prb.addHeader(key, headers.get(key));
//            }
//        }
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                prb.addFormDataPart(key, params.get(key));
            }
        }
        if (fileList != null && fileList.size() > 0) {
            for (int i = 0; i < fileList.size(); i++) {
                prb.addFormDataPart(fileList.get(i).getFileKey(),
                        fileList.get(i).getFileName(),
                        fileList.get(i).getMediaType(),
                        fileList.get(i).getPath());
            }
//            for (UpLoadFileBean bean : fileList) {
//                prb.addFormDataPart(bean.getFileKey(), bean.getFileName(), bean.getMediaType(), bean.getPath());
//            }
        }
        prb.headers(getHeader());
        prb.enqueue(callBack);
    }


    private static Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("AUTHENTICATE", UserStore.getToken());
        headers.put("terminal", "android");
        headers.put("version", SystemUtil.getVersionName(context) + "");
        return headers;
    }

    public BaseParser getParser(Object o) {
        BaseParser parser = null;
        try {
            Type type = ((ParameterizedType) o.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            if (type.toString().startsWith("java.util.List")) {
                Object actualType = null;
                if (type instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) type;
                    // 得到泛型里的class类型对象
                    Class<?> actualTypeArgument = (Class<?>) pt.getActualTypeArguments()[0];
                    actualType = actualTypeArgument.newInstance();
                    parser = new DataArrayParser(actualType.getClass());
                }
            } else {
                Class<T> entityClass = (Class<T>) type;
                if (String.class.equals(entityClass)) {
                    parser = new DataStringParser();
                } else {
                    T clazz = entityClass.newInstance();
                    parser = new DataModelParser((Class<T>) clazz.getClass());
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return parser;
    }

}

package com.zz.supervision.net;

import com.zz.lib.commonlib.utils.CacheUtility;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2018/5/17.
 */

public class CommonParamInterceptor implements Interceptor {

    private Map<String, Object> headerMaps = new TreeMap<>();

    public CommonParamInterceptor(Map<String, Object> headerMaps) {
        this.headerMaps = headerMaps;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if (CacheUtility.isLogin()) {
            if (request.method().equals("GET")) {
                //添加公共参数
                HttpUrl httpUrl = request.url()
                        .newBuilder()
                        .addQueryParameter("token", CacheUtility.getToken())
                        .build();
                request = request.newBuilder().url(httpUrl).build();
            } else if (request.method().equals("POST")) {
                if (request.body() instanceof FormBody) {
                    FormBody.Builder bodyBuilder = new FormBody.Builder();
                    FormBody formBody = (FormBody) request.body();

                    //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
                    for (int i = 0; i < formBody.size(); i++) {
                        bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                    formBody = bodyBuilder
                            .addEncoded("token", CacheUtility.getToken())
                            .build();
                    request = request.newBuilder().post(formBody).build();
                }
                return chain.proceed(request);
            }
        }
        return chain.proceed(request);
    }
}



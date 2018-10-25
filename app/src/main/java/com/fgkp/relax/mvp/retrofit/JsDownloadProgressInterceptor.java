package com.fgkp.relax.mvp.retrofit;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 大文件下载 拦截器
 */
public class JsDownloadProgressInterceptor implements Interceptor {
    private JsDownloadListener listener;

    public JsDownloadProgressInterceptor(JsDownloadListener listener){
        this.listener = listener;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        //断点续传
//        Request request = chain.request().newBuilder().addHeader("Range" , "bytes=" + startpos + "-").build();
//        Response response = chain.proceed(request);
//        return response.newBuilder()
//                .body(new JsResponseBody(originalResponse.body(), listener))
//                .build();

        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .body(new JsResponseBody(response.body(), listener))
                .build();


    }
}

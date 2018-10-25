package com.fgkp.relax.mvp.retrofit;

/**
 * Created by Administrator on 2018/9/28.
 */

public interface JsDownloadListener {
    void onStartDownload();

    void onProgress(int progress);

    void onFinishDownload();

    void onFail(String errorInfo);
}

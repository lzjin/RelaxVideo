package com.fgkp.relax.mvp.retrofit;

import android.app.ProgressDialog;
import android.content.Context;

import com.fgkp.relax.mvp.custom.ProgressDialogFragment;
import com.fgkp.relax.mvp.utils.MLog;
import com.fgkp.relax.mvp.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 2018/9/27.
 *  二次封装
 */

public abstract  class ProgressSubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private ProgressDialog dialog;
    private ProgressDialogFragment dialogFragment;

    public ProgressSubscriber(Context mContext) {
        this.mContext = mContext;
    }

    public ProgressSubscriber(Context mContext, ProgressDialog dialog) {
        this.mContext = mContext;
        this.dialog = dialog;
    }

    public ProgressSubscriber(Context mContext, ProgressDialogFragment dialogFragment) {
        this.mContext = mContext;
        this.dialogFragment = dialogFragment;
    }



    @Override
    public void onCompleted() {
        MLog.i("test","--------onCompleted");
        if (dialog != null) {
            dialog.dismiss();
        }
        if(dialogFragment != null){
            dialogFragment.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        MLog.i("test","------error------错误信息:"+e.getMessage());
        if (dialog != null) {
            dialog.dismiss();
        }
        if(dialogFragment != null){
            dialogFragment.dismiss();
        }
        if(e instanceof UnknownHostException) {
            ToastUtil.showShort(mContext, "网络连接失败，请检查网络设置");
            _onError("网络不可用");
        }
        else if (e instanceof SocketTimeoutException){
            ToastUtil.showShort(mContext, "网络连接超时");
            _onError("超时");
        }
        else if (e instanceof ConnectException) {
            ToastUtil.showShort(mContext, "网络连接失败");
            _onError("失败");
        }
        else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int responseCode = httpException.code();
            if (responseCode >= 400 && responseCode <= 417) {
                ToastUtil.showShort(mContext, "访问地址异常，请稍后重试");
            } else if (responseCode >= 500 && responseCode <= 505) {
                ToastUtil.showShort(mContext, "服务器繁忙");
            } else {
                ToastUtil.showShort(mContext, "网络连接异常");
            }
        }
        else {
            ToastUtil.showShort(mContext, "网络连接异常");
            _onError("异常");
        }

    }

    @Override
    public void onNext(T t) {
        _onNext(t);
        if (dialog != null) {
            dialog.dismiss();
        }
        if(dialogFragment != null){
            dialogFragment.dismiss();
        }
    }

    protected abstract void _onNext(Object t);

    protected abstract void _onError(String message);



}

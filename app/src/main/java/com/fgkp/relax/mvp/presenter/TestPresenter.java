package com.fgkp.relax.mvp.presenter;

import android.content.Context;
import android.widget.ProgressBar;

import com.fgkp.relax.mvp.base.BasePresenter;
import com.fgkp.relax.mvp.model.TestModelImpl;
import com.fgkp.relax.mvp.view.ITestView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lzj on 2018/10/4.
 * 测试
 */

public class TestPresenter extends BasePresenter<ITestView> {

    private TestModelImpl testModel;

    public TestPresenter(Context context) {
        this.testModel = new TestModelImpl(context);
    }

    /**
     * 1
     * get 返回bean
     */
    public void getBean(String token, Map<String,String> map){
        //加载中。。。
        getView().onLoading();
        testModel.getBean(token,map, new TestModelImpl.OnResultListener() {
            @Override
            public void onSuccess(Object o) {
                //解绑，不用回调了
                if (getView()!=null)
                    getView().loginSuccess(o);
            }

            @Override
            public void onFailure(String msg) {
                //解绑，不用回调了
                if (getView()!=null)
                    getView().onError(msg);
            }
        });
    }

    /**
     * 2
     * get 返回json
     */
    public void getJson(String token ,Map<String,String> map){
        //加载中。。。
        getView().onLoading();
        testModel.getJson(token,map, new TestModelImpl.OnResultListener() {
            @Override
            public void onSuccess(Object o) {
                //解绑，不用回调了
                if (getView()!=null)
                    getView().loginSuccess(o);
            }

            @Override
            public void onFailure(String msg) {
                //解绑，不用回调了
                if (getView()!=null)
                    getView().onError(msg);
            }
        });
    }
    /**
     * 3
     * post 返回bean
     */
    public void postBean(String token ,Map<String,String> map){
        //加载中。。。
        getView().onLoading();
        testModel.postBean(token,map, new TestModelImpl.OnResultListener() {
            @Override
            public void onSuccess(Object o) {
                //解绑，不用回调了
                if (getView()!=null)
                    getView().loginSuccess(o);
            }

            @Override
            public void onFailure(String msg) {
                //解绑，不用回调了
                if (getView()!=null)
                    getView().onError(msg);
            }
        });
    }
    /**
     * 4
     * post 返回bean
     */
    public void postJson(String token ,Map<String,String> map){
        //加载中。。。
        getView().onLoading();
        testModel.postJson(token,map, new TestModelImpl.OnResultListener() {
            @Override
            public void onSuccess(Object o) {
                //解绑，不用回调了
                if (getView()!=null)
                    getView().loginSuccess(o);
            }

            @Override
            public void onFailure(String msg) {
                //解绑，不用回调了
                if (getView()!=null)
                    getView().onError(msg);
            }
        });
    }

    /**
     * 5
     * get 下载
     */
    public void  getDowns(String url, String path, String name, ProgressBar progressBar){
        //加载中。。。
        getView().onLoading();
        testModel.getDownloadFiles(url,path, name,progressBar,new TestModelImpl.OnResultListener() {
            @Override
            public void onSuccess(Object o) {
                //解绑，不用回调了
                if (getView()!=null)
                    getView().loginSuccess(o);
            }

            @Override
            public void onFailure(String msg) {
                //解绑，不用回调了
                if (getView()!=null)
                    getView().onError(msg);
            }
        });
    }
    /**
     * 6
     * get 下载 带进度条
     */
    public void  getDown(String url, String path, String name, ProgressBar progressBar){
        //加载中。。。
        getView().onLoading();
        testModel.getDownloadFile(url,path, name,progressBar);
    }
    /**
     * 6
     * post 上传
     */
    public void  postUp(String token, String imgpath){
        //加载中。。。
        getView().onLoading();
        testModel.upLoadImg(token,imgpath, new TestModelImpl.OnResultListener() {
            @Override
            public void onSuccess(Object o) {
                //解绑，不用回调了
                if (getView()!=null)
                    getView().loginSuccess(o);
            }

            @Override
            public void onFailure(String msg) {
                //解绑，不用回调了
                if (getView()!=null)
                    getView().onError(msg);
            }
        });
    }





}
